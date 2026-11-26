import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RideSharingSystem implements IRideSharingSystem {
    // Riders
    private IBST<Integer, IRider> ridersByID;

    private IBST<String, IBST<Integer, IRider>> ridersByNameIndex;
    private NonUniqueIndexHelper<String, Integer, IRider> ridersByName;

    private IBST<String, IBST<Integer, IRider>> ridersByCityIndex;
    private NonUniqueIndexHelper<String, Integer, IRider> ridersByCity;

    // Drivers
    private IBST<Integer, IDriver> driversByID;

    private IBST<String, IDriver> driversByPlate;

    private IBST<VehicleType, IBST<Integer, IDriver>> driversByTypeIndex;
    private NonUniqueIndexHelper<VehicleType, Integer, IDriver> driversByType;

    // Rides
    private IBST<Integer, IRide> ridesByID;

    private IBST<String, IBST<Integer, IRide>> ridesByPickupLocationIndex;
    private NonUniqueIndexHelper<String, Integer, IRide> ridesByPickupLocation;

    private IBST<IDateTime, IBST<Integer, IRide>> ridesByPickupTimeIndex;
    private NonUniqueIndexHelper<IDateTime, Integer, IRide> ridesByPickupTime;

    private int ridesIDCounter;

    public RideSharingSystem() {
        // Riders
        ridersByID = new BST<Integer, IRider>();

        ridersByNameIndex = new BST<String, IBST<Integer, IRider>>();
        ridersByName = new NonUniqueIndexHelper<String, Integer, IRider>(ridersByNameIndex);

        ridersByCityIndex = new BST<String, IBST<Integer, IRider>>();
        ridersByCity = new NonUniqueIndexHelper<String, Integer, IRider>(ridersByCityIndex);

        // Drivers
        driversByID = new BST<Integer, IDriver>();

        driversByPlate = new BST<String, IDriver>();

        driversByTypeIndex = new BST<VehicleType, IBST<Integer, IDriver>>();
        driversByType = new NonUniqueIndexHelper<VehicleType, Integer, IDriver>(driversByTypeIndex);

        // Rides
        ridesByID = new BST<Integer, IRide>();

        ridesByPickupLocationIndex = new BST<String, IBST<Integer, IRide>>();
        ridesByPickupLocation = new NonUniqueIndexHelper<String, Integer, IRide>(ridesByPickupLocationIndex);

        ridesByPickupTimeIndex = new BST<IDateTime, IBST<Integer, IRide>>();
        ridesByPickupTime = new NonUniqueIndexHelper<IDateTime, Integer, IRide>(ridesByPickupTimeIndex);

        ridesIDCounter = 0;
    }

    // CSV Load methods
    @Override
    public int loadRidersFromCSV(String ridersFilePath) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(ridersFilePath));

            String line = br.readLine();

            while (line != null) {
                try {
                    String[] data = line.split(",");
                    if (addRider(new Rider(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]))) {
                        count++;
                    }

                } catch (Exception e) {
                    // skip faulty line
                }

                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            return -1;
        }
        return count;
    }

    @Override
    public int loadDriversFromCSV(String driversFilePath) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(driversFilePath));

            String line = br.readLine();

            while (line != null) {

                try {
                    String[] data = line.split(",");
                    VehicleType vType;

                    switch (data[4]) {
                        case "SEDAN":
                            vType = VehicleType.SEDAN;
                            break;

                        case "LUXURY_SEDAN":
                            vType = VehicleType.LUXURY_SEDAN;
                            break;

                        case "SUV":
                            vType = VehicleType.SUV;
                            break;

                        case "VAN":
                            vType = VehicleType.VAN;
                            break;

                        default:
                            vType = null;
                    }

                    if (addDriver(new Driver(Integer.parseInt(data[0]), data[1], data[2], data[3], vType))) {
                        count++;
                    }

                } catch (Exception e) {
                    // skip faulty line
                }

                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            return -1;
        }

        return count;
    }

    @Override
    public int loadRidesFromCSV(String ridesFilePath) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(ridesFilePath));

            String line = br.readLine();
            IDateTime pickupDateTime, dropoffDateTime;
            ITimeSlot timeSlot;
            int rideId;

            while (line != null) {

                try {
                    String[] data = line.split(",");

                    pickupDateTime = new DateTime(data[2]);
                    dropoffDateTime = new DateTime(data[3]);
                    timeSlot = new TimeSlot(pickupDateTime, dropoffDateTime);

                    int driverId = Integer.parseInt(data[5]);

                    if (data[0].equals("SHARED")) {
                        String[] ridersIdsRow = data[6].split(";");
                        int[] ridersIds = new int[ridersIdsRow.length];
                        for (int i = 0; i < ridersIdsRow.length; i++) {
                            ridersIds[i] = Integer.parseInt(ridersIdsRow[i]);
                        }

                        rideId = scheduleSharedRide(data[1], data[4], timeSlot, ridersIds, driverId);
                    } else if (data[0].equals("PRIVATE")) {
                        rideId = schedulePrivateRide(data[1], data[4], timeSlot, Integer.parseInt(data[6]), driverId);
                    } else {
                        rideId = -1;
                    }

                    if (rideId != -1) {
                        count++;
                    }
                } catch (Exception e) {
                    // skip faulty line
                }

                line = br.readLine();
            }

            br.close();
            return count;

        } catch (IOException e) {
            return -1;
        }
    }

    // Rider methods
    @Override
    public boolean addRider(IRider rider) {
        if (rider == null) {
            return false;
        }
        if (ridersByID.findKey(rider.getId())) {
            return false;
        }
        ridersByID.insert(rider.getId(), rider);
        ridersByName.add(rider.getName(), rider.getId(), rider);
        ridersByCity.add(rider.getHomeCity(), rider.getId(), rider);
        return true;
    }

    @Override
    public IRider searchRiderById(int riderId) {
        if (ridersByID.findKey(riderId)) {
            return ridersByID.retrieve();
        }
        return null;
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        IBST<Integer, IRider> inner = ridersByName.get(fullName);
        if (inner == null) {
            return new LinkedList<IRider>();
        }
        return inner.inOrder();

    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        IBST<Integer, IRider> inner = ridersByCity.get(homeCity);
        if (inner == null) {
            return new LinkedList<IRider>();
        }
        return inner.inOrder();
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        return ridersByID.inOrder();
    }

    @Override
    public boolean removeRider(int riderId) {
        IRider rider = searchRiderById(riderId);
        if (rider == null) {
            return false;
        }

        // updating schedules and ride indexes
        LinkedList<IRide> rides = rider.getSchedule().inOrder();
        IRide ride;

        if (!rides.empty()) {

            rides.findFirst();

            while (!rides.last()) {
                ride = rides.retrieve();
                if (ride instanceof IPrivateRide) {
                    cancelRide(ride.getRideId());
                } else if (ride instanceof ISharedRide) {
                    ((ISharedRide) ride).removeParticipantById(riderId);
                    if (((ISharedRide) ride).isEmpty()) {
                        cancelRide(ride.getRideId());
                    }
                }

                rides.findNext();
            }
            ride = rides.retrieve();
            if (ride instanceof IPrivateRide) {
                cancelRide(ride.getRideId());
            } else if (ride instanceof ISharedRide) {
                ((ISharedRide) ride).removeParticipantById(riderId);
                if (((ISharedRide) ride).isEmpty()) {
                    cancelRide(ride.getRideId());
                }
            }
        }

        // updating rider indexes
        ridersByID.removeKey(riderId);
        ridersByCity.remove(rider.getHomeCity(), riderId);
        ridersByName.remove(rider.getName(), riderId);

        return true;
    }

    // Driver methods
    @Override
    public boolean addDriver(IDriver driver) {
        return drivers.add(driver);
    }

    @Override
    public IDriver searchDriverById(int driverId) {
        return drivers.findById(driverId);
    }

    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return drivers.findByVehicleType(vehicleType);

    }

    @Override
    public LinkedList<IDriver> getAllDrivers() {
        return drivers.getAll();

    }

    @Override
    public boolean removeDriver(int driverId) {
        IDriver driver = searchDriverById(driverId);
        if (driver == null) {
            return false;
        }

        LinkedList<IRide> rides = driver.getSchedule().inOrder();

        if (!rides.empty()) {
            rides.findFirst();
            while (!rides.last()) {
                cancelRide(rides.retrieve().getRideId());
                rides.findNext();
            }
            cancelRide(rides.retrieve().getRideId());

        }

        driversByID.removeKey(driverId);
        driversByType.remove(driver.getVehicleType(), driverId);
        driversByPlate.removeKey(driver.getVehiclePlate());

        return true;
    }

    // Ride methods
    @Override
    public int schedulePrivateRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int riderId,
            int driverId) {

        IDriver driver = searchDriverById(driverId);
        IRider rider = searchRiderById(riderId);

        if (pickupLocation == null || dropoffLocation == null || driver == null || rider == null || timeSlot == null
                || rider.getSchedule().findKey(timeSlot) || driver.getSchedule().findKey(timeSlot)) {
            return -1;
        }
        // id starts from 1
        IRide ride = new PrivateRide(++ridesIDCounter, pickupLocation, dropoffLocation, timeSlot, rider, driver);

        // adding to ride BSTs
        ridesByID.insert(ridesIDCounter, ride);
        ridesByPickupLocation.add(pickupLocation, ridesIDCounter, ride);
        ridesByPickupTime.add(timeSlot.getStart(), ridesIDCounter, ride);

        // updating schedules
        driver.getSchedule().insert(timeSlot, ride);
        rider.getSchedule().insert(timeSlot, ride);

        return ridesIDCounter;
    }

    @Override
    public int scheduleSharedRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int[] riderIds,
            int driverId) {
        IDriver driver = searchDriverById(driverId);
        IRider rider;

        if (pickupLocation == null || dropoffLocation == null || riderIds == null || riderIds.length < 2
                || driver == null || timeSlot == null || driver.getSchedule().findKey(timeSlot)) {
            return -1;
        }

        SharedRide ride = new SharedRide(ridesIDCounter + 1, pickupLocation, dropoffLocation, timeSlot, driver);

        for (int riderid : riderIds) {
            rider = searchRiderById(riderid);
            if (rider == null || rider.getSchedule().findKey(timeSlot) || !ride.addParticipant(rider)) {
                return -1;
            }
        }

        // updating schedules
        for (int riderid : riderIds) {
            rider = searchRiderById(riderid);

            rider.getSchedule().insert(timeSlot, ride);
        }

        driver.getSchedule().insert(timeSlot, ride);

        ridesIDCounter++;

        // adding to ride BSTs
        ridesByID.insert(ridesIDCounter, ride);
        ridesByPickupLocation.add(pickupLocation, ridesIDCounter, ride);
        ridesByPickupTime.add(timeSlot.getStart(), ridesIDCounter, ride);

        return ridesIDCounter;
    }

    @Override
    public boolean cancelRide(int rideId) {
        // check that ride exist
        if (!ridesByID.findKey(rideId)) {
            return false;
        }

        IRide ride = ridesByID.retrieve();
        ITimeSlot timeSlot = ride.getTimeSlot();
        IRider rider;
        LinkedList<IRider> riders;
        IDriver driver = ride.getDriver();

        // remove from schedules
        driver.getSchedule().removeKey(timeSlot);

        if (ride instanceof PrivateRide) {
            rider = ((PrivateRide) ride).getRider();
            rider.getSchedule().removeKey(timeSlot);
        } else if (ride instanceof SharedRide) {
            riders = ((SharedRide) ride).getParticipants();

            if (!riders.empty()) {
                riders.findFirst();
                while (!riders.last()) {
                    rider = riders.retrieve();
                    rider.getSchedule().removeKey(timeSlot);
                    riders.findNext();
                }

                rider = riders.retrieve();
                rider.getSchedule().removeKey(timeSlot);
            }
        }

        // remove from indexes
        ridesByID.removeKey(rideId);
        ridesByPickupLocation.remove(ride.getPickupLocation(), rideId);
        ridesByPickupTime.remove(timeSlot.getStart(), rideId);

        return true;
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        IBST<Integer, IRide> rides = ridesByPickupLocation.get(pickupLocation);
        if (rides == null) {
            return new LinkedList<IRide>();
        }
        return rides.inOrder();
    }

    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        IBST<Integer, IRide> rides = new BST<Integer, IRide>();

        IBST<Integer, IRider> ridersBST = ridersByName.get(riderName);

        if (ridersBST == null) {
            return rides.inOrder();
        }

        LinkedList<IRider> riders = ridersBST.inOrder();
        LinkedList<IRide> riderSchedule;
        IRide ride;

        if (riders.empty()) {
            return rides.inOrder();
        }

        riders.findFirst();
        while (!riders.last()) {
            riderSchedule = riders.retrieve().getSchedule().inOrder();
            if (!riderSchedule.empty()) {
                riderSchedule.findFirst();
                while (!riderSchedule.last()) {
                    ride = riderSchedule.retrieve();
                    rides.insert(ride.getRideId(), ride);
                    riderSchedule.findNext();
                }
                ride = riderSchedule.retrieve();
                rides.insert(ride.getRideId(), ride);
            }
            riders.findNext();
        }

        riderSchedule = riders.retrieve().getSchedule().inOrder();
        if (!riderSchedule.empty()) {
            riderSchedule.findFirst();
            while (!riderSchedule.last()) {
                ride = riderSchedule.retrieve();
                rides.insert(ride.getRideId(), ride);
                riderSchedule.findNext();
            }
            ride = riderSchedule.retrieve();
            rides.insert(ride.getRideId(), ride);
        }

        return rides.inOrder();
    }

    @Override
    public LinkedList<IRider> getSharedRideParticipants(int rideId) {
        LinkedList<IRider> riders = new LinkedList<IRider>();
        if (!ridesByID.findKey(rideId)) {
            return riders;
        }

        IRide ride = ridesByID.retrieve();

        if (ride instanceof SharedRide) {
            riders = ((ISharedRide) ride).getParticipants();
        }

        return riders;
    }

    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        LinkedList<IRide> rides = new LinkedList<IRide>();

        LinkedList<IBST<Integer, IRide>> bsts = ridesByPickupLocationIndex.inOrder();

        if (bsts.empty()) {
            return rides;
        }

        bsts.findFirst();
        while (!bsts.last()) {
            rides.insertLinkedListAtEnd(bsts.retrieve().inOrder());
            bsts.findNext();
        }
        rides.insertLinkedListAtEnd(bsts.retrieve().inOrder());

        return rides;
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupTimeRange(IDateTime from, IDateTime to) {
        LinkedList<IRide> rides = new LinkedList<IRide>();

        if (from == null || to == null) {
            return rides;
        }
        
        LinkedList<IBST<Integer, IRide>> rideBSTs = ridesByPickupTimeIndex.rangeSearch(from, to);

        if (rideBSTs.empty()) {
            return rides;
        }

        rideBSTs.findFirst();
        while (!rideBSTs.last()) {
            rides.insertLinkedListAtEnd(rideBSTs.retrieve().inOrder());
            rideBSTs.findNext();
        }
        rides.insertLinkedListAtEnd(rideBSTs.retrieve().inOrder());

        return rides;
    }

    @Override
    public LinkedList<IDriver> findAvailableDrivers(ITimeSlot timeSlot, VehicleType vehicleType) {
        // TODO Auto-generated method stub
        return null;
    }

    // tree statistics
    @Override
    public int nbKeyCompRiderById(int riderId) {
        return ridersByID.nbKeyComp(riderId);
    }

    @Override
    public int indexSize(IndexName index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int indexHeight(IndexName index) {
        // TODO Auto-generated method stub
        return 0;
    }

}
