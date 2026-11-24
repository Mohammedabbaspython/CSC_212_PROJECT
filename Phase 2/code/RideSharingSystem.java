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

    public RideSharingSystem() {
        // Riders
        ridersByID = new BST<Integer, IRider>();

        ridersByNameIndex = new BST<String, IBST<Integer, IRider>>();
        ridersByName = new NonUniqueIndexHelper<String, Integer, IRider>(
                ridersByNameIndex);

        ridersByCityIndex = new BST<String, IBST<Integer, IRider>>();
        ridersByCity = new NonUniqueIndexHelper<String, Integer, IRider>(
                ridersByCityIndex);

        // Drivers
        driversByID = new BST<Integer, IDriver>();

        driversByPlate = new BST<String, IDriver>();

        driversByTypeIndex = new BST<VehicleType, IBST<Integer, IDriver>>();
        driversByType = new NonUniqueIndexHelper<VehicleType, Integer, IDriver>(
                driversByTypeIndex);

        // Rides
        ridesByID = new BST<Integer, IRide>();

        ridesByPickupLocationIndex = new BST<String, IBST<Integer, IRide>>();
        ridesByPickupLocation = new NonUniqueIndexHelper<String, Integer, IRide>(
                ridesByPickupLocationIndex);

        ridesByPickupTimeIndex = new BST<IDateTime, IBST<Integer, IRide>>();
        ridesByPickupTime = new NonUniqueIndexHelper<IDateTime, Integer, IRide>(
                ridesByPickupTimeIndex);
    }

    // CSV Load methods
    @Override
    public int loadRidersFromCSV(String ridersFilePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ridersFilePath));

            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(",");

                riders.add(new Rider(Integer.parseInt(data[0]), data[1], data[3], data[2], data[4]));

                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public int loadDriversFromCSV(String driversFilePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(driversFilePath));

            String line = br.readLine();

            while (line != null) {
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
                        return false;
                }

                try {
                    drivers.add(new Driver(Integer.parseInt(data[0]), data[1], data[2], data[3], vType));

                } catch (IllegalArgumentException e) {
                    br.close();
                    return false;
                }

                line = br.readLine();
            }
            br.close();

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public int loadRidesFromCSV(String ridesFilePath) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(ridesFilePath));

            String line = br.readLine();
            IDateTime pickupDateTime, dropoffDateTime;

            while (line != null) {
                String[] data = line.split(",");

                try {
                    pickupDateTime = new DateTime(data[2]);
                    dropoffDateTime = new DateTime(data[3]);
                } catch (IllegalArgumentException e) {
                    br.close();
                    return false;
                }
                int driverId = Integer.parseInt(data[5]);

                if (data[0].equals("SHARED")) {
                    String[] ridersIdsRow = data[6].split(";");
                    int[] ridersIds = new int[ridersIdsRow.length];
                    for (int i = 0; i < ridersIdsRow.length; i++) {
                        ridersIds[i] = Integer.parseInt(ridersIdsRow[i]);
                    }

                    scheduleSharedRide(data[1], pickupDateTime, dropoffDateTime, data[4], ridersIds, driverId);
                } else if (data[0].equals("PRIVATE")) {
                    schedulePrivateRide(data[1], pickupDateTime, dropoffDateTime, data[4], Integer.parseInt(data[6]),
                            driverId);
                } else {
                    br.close();
                    return false;
                }

                line = br.readLine();
            }

            br.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    // Rider methods
    @Override
    public boolean addRider(IRider rider) {
        return riders.add(rider);
    }

    @Override
    public IRider searchRiderById(int riderId) {
        return riders.findById(riderId);
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riders.findByName(fullName);
    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riders.findByHomeCity(homeCity);
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        return riders.getAll();
    }

    @Override
    public boolean removeRider(int riderId) {
        IRider rider = searchRiderById(riderId);
        if (rider == null) {
            return false;
        }

        LinkedList<IRide> frides = rides.getAllAlphabetically();
        if (!frides.empty()) {

            frides.findFirst();

            while (!frides.last()) {
                if (frides.retrieve() instanceof PrivateRide && frides.retrieve().hasRider(riderId)) {
                    rides.removeRideById(frides.retrieve().getRideId());
                } else if (frides.retrieve() instanceof SharedRide && frides.retrieve().hasRider(riderId)) {
                    ((SharedRide) frides.retrieve()).removeParticipantById(riderId);
                    if (((SharedRide) frides.retrieve()).isEmpty()) {
                        rides.removeRideById(frides.retrieve().getRideId());
                    }
                }

                frides.findNext();
            }

            if (frides.retrieve() instanceof PrivateRide && frides.retrieve().hasRider(riderId)) {
                rides.removeRideById(frides.retrieve().getRideId());
            } else if (frides.retrieve() instanceof SharedRide && frides.retrieve().hasRider(riderId)) {
                ((SharedRide) frides.retrieve()).removeParticipantById(riderId);
                if (((SharedRide) frides.retrieve()).isEmpty()) {
                    rides.removeRideById(frides.retrieve().getRideId());
                }
            }
        }
        riders.removeById(riderId);

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

        LinkedList<IRide> frides = rides.getAllAlphabetically();
        if (!frides.empty()) {
            frides.findFirst();
            while (!frides.last()) {
                if (frides.retrieve().getDriver().getId() == driverId) {
                    rides.removeRideById(frides.retrieve().getRideId());

                }
                frides.findNext();
            }
            if (frides.retrieve().getDriver().getId() == driverId) {
                rides.removeRideById(frides.retrieve().getRideId());
            }
        }

        drivers.removeById(driverId);

        return true;
    }

    // Ride methods
    @Override
    public int schedulePrivateRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int riderId,
            int driverId) {

        IDriver driver = searchDriverById(driverId);
        IRider rider = searchRiderById(riderId);

        if (driver == null || rider == null || pickupTime.compareTo(dropoffTime) >= 0
                || riderHasRideConflict(pickupTime, dropoffTime, riderId)
                || driverHasRideConflict(pickupTime, dropoffTime, driverId)) {
            return false;
        }

        IRide ride = new PrivateRide(pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver, rider);

        rides.addRide(ride);

        driver.getRideHistory().insert(ride);
        rider.getRideHistory().insert(ride);

        return true;
    }

    @Override
    public int scheduleSharedRide(String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, int[] riderIds,
            int driverId) {
        IDriver driver = searchDriverById(driverId);
        IRider rider;

        if (driver == null || pickupTime.compareTo(dropoffTime) >= 0
                || driverHasRideConflict(pickupTime, dropoffTime, driverId)) {
            return false;
        }

        SharedRide ride = new SharedRide(pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);

        for (int riderid : riderIds) {
            rider = searchRiderById(riderid);
            if (rider == null || riderHasRideConflict(pickupTime, dropoffTime, riderid)) {
                return false;
            }

            ride.addParticipant(rider);
        }

        for (int riderid : riderIds) {
            rider = searchRiderById(riderid);

            rider.getRideHistory().insert(ride);
        }

        driver.getRideHistory().insert(ride);

        rides.addRide(ride);
        return true;
    }

    @Override
    public boolean cancelRide(int rideId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rides.findByPickupLocation(pickupLocation);
    }

    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rides.findByRiderName(riderName);
    }

    @Override
    public LinkedList<IRider> getSharedRideParticipants(int rideId) {
        LinkedList<IRide> frides = searchRidesByPickupLocation(pickupLocation);

        LinkedList<IRider> tmpRiders = new LinkedList<IRider>();
        IRiderList result = new RiderList();

        IRide ride;

        if (frides.empty()) {
            return result.getAll();
        }

        frides.findFirst();

        while (!frides.last()) {
            ride = frides.retrieve();
            if (ride instanceof SharedRide) {
                tmpRiders = ((SharedRide) ride).getParticipants();

                tmpRiders.findFirst();
                while (!tmpRiders.last()) {
                    result.add(tmpRiders.retrieve());
                    tmpRiders.findNext();
                }
                result.add(tmpRiders.retrieve());
            }
            frides.findNext();
        }

        ride = frides.retrieve();

        if (ride instanceof SharedRide) {
            tmpRiders = ((SharedRide) ride).getParticipants();

            tmpRiders.findFirst();
            while (!tmpRiders.last()) {
                result.add(tmpRiders.retrieve());
                tmpRiders.findNext();
            }
            result.add(tmpRiders.retrieve());
        }

        return result.getAll();
    }

    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rides.getAllAlphabetically();
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupTimeRange(IDateTime from, IDateTime to) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LinkedList<IDriver> findAvailableDrivers(ITimeSlot timeSlot, VehicleType vehicleType) {
        // TODO Auto-generated method stub
        return null;
    }

    // tree statistics
    @Override
    public int nbKeyCompRiderById(int riderId) {
        // TODO Auto-generated method stub
        return 0;
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

    // helper methods
    private boolean rideOverlaps(IDateTime pickupDateTime, IDateTime dropoffDateTime, IRide other) {
        if (pickupDateTime.compareTo(other.getDropoffTime()) < 0
                && other.getPickupTime().compareTo(dropoffDateTime) < 0) {
            return true;
        }
        return false;
    }

    private boolean riderHasRideConflict(IDateTime pickupDateTime, IDateTime dropoffDateTime, int riderId) {
        LinkedList<IRide> allRides = getAllRidesAlphabetically();

        if (allRides.empty()) {
            return false;
        }

        allRides.findFirst();

        while (!allRides.last()) {
            if (allRides.retrieve().hasRider(riderId)
                    && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
                return true;
            }
            allRides.findNext();
        }

        if (allRides.retrieve().hasRider(riderId)
                && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
            return true;
        }

        return false;
    }

    private boolean driverHasRideConflict(IDateTime pickupDateTime, IDateTime dropoffDateTime, int driverId) {
        LinkedList<IRide> allRides = getAllRidesAlphabetically();

        if (allRides.empty()) {
            return false;
        }

        allRides.findFirst();

        while (!allRides.last()) {
            if (allRides.retrieve().getDriver().getId() == driverId
                    && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
                return true;
            }
            allRides.findNext();
        }
        if (allRides.retrieve().getDriver().getId() == driverId
                && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
            return true;
        }
        return false;
    }

}
