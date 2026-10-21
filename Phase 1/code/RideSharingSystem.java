import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RideSharingSystem implements IRideSharingSystem {

    private IRideList rides;
    private IDriverList drivers;
    private IRiderList riders;

    public RideSharingSystem() {
        rides = new RideList();
        drivers = new DriverList();
        riders = new RiderList();
    }

    @Override
    public boolean addRider(IRider rider) {
        return riders.add(rider);
    }

    @Override
    public IRider searchRiderById(int riderId) {
        return riders.findById(riderId);
    }

    @Override
    public IRider searchRiderByEmail(String Email) {
        return riders.findByEmail(Email);
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

    // later
    @Override
    public boolean loadRidersFromCSV(String ridersFilePath) {
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
    public boolean loadDriversFromCSV(String driversFilePath) {
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

                    case "VAN":
                        vType = VehicleType.VAN;
                        break;

                    default:
                        return false;
                }

                try {
                    drivers.add(new Driver(Integer.parseInt(data[0]), data[1], data[3], data[2], vType));

                } catch (IllegalArgumentException e) {
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
    public boolean loadRidesFromCSV(String ridesFilePath) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(ridesFilePath));

            String line = br.readLine();
            IDateTime pickupDateTime, dropoffDateTime;

            while (line != null) {
                String[] data = line.split(",");

                IRide ride;

                try {
                    pickupDateTime = new DateTime(data[2]);
                    dropoffDateTime = new DateTime(data[3]);
                } catch (IllegalArgumentException e) {
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
                    return false;
                }

                line = br.readLine();
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    // endlater
    @Override
    public IDriver searchDriverById(int driverId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchDriverById'");
    }

    @Override
    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchDriverByVehiclePlate'");
    }

    @Override
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchDriversByVehicleType'");
    }

    @Override
    public LinkedList<IDriver> getAllDrivers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDrivers'");
    }

    @Override
    public boolean removeRider(int riderId) {
        IRider rider = searchRiderById(riderId);
        if (rider == null) {
            return false;
        }

        LinkedList<IRide> frides = rides.getAllAlphabetically();
        if (!frides.empty()) {

            frides.findfirst();

            while (!frides.last()) {
                if (frides.retrieve() instanceof PrivateRide && frides.retrieve().hasRider(riderId)) {
                    rides.removeRideById(frides.retrieve().getRideId());
                } else if (frides.retrieve() instanceof SharedRide && frides.retrieve().hasRider(riderId)) {
                    ((SharedRide) frides.retrieve()).removeParticipantById(riderId);
                    if (((SharedRide) frides.retrieve()).isEmpty()) {
                        rides.removeRideById(frides.retrieve().getRideId());
                    }
                }

                frides.findnext();
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

    @Override
    public boolean removeDriver(int driverId) {
        IDriver driver = searchDriverById(driverId);
        if (driver == null) {
            return false;
        }

        LinkedList<IRide> frides = rides.getAllAlphabetically();
        if (!frides.empty()) {
            frides.findfirst();
            while (!frides.last()) {
                if (frides.retrieve().getDriver().getId() == driverId) {
                    rides.removeRideById(frides.retrieve().getRideId());

                }
                frides.findnext();
            }
            if (frides.retrieve().getDriver().getId() == driverId) {
                rides.removeRideById(frides.retrieve().getRideId());
            }
        }

        drivers.removeById(driverId);

        return true;
    }

    @Override
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
            String dropoffLocation, int riderId, int driverId) {

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
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
            String dropoffLocation, int[] riderIds, int driverId) {
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
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rides.findByPickupLocation(pickupLocation);
    }

    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rides.findByRiderName(riderName);
    }

    @Override
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        LinkedList<IRide> frides = searchRidesByPickupLocation(pickupLocation);

        LinkedList<IRider> tmpRiders = new LinkedList<IRider>();
        IRiderList result = new RiderList();

        IRide ride;

        if (frides.empty()) {
            return result.getAll();
        }

        frides.findfirst();

        while (!frides.last()) {
            ride = frides.retrieve();
            if (ride instanceof SharedRide) {
                tmpRiders = ((SharedRide) ride).getParticipants();

                tmpRiders.findfirst();
                while (!tmpRiders.last()) {
                    result.add(tmpRiders.retrieve());
                    tmpRiders.findnext();
                }
                result.add(tmpRiders.retrieve());
            }
            frides.findnext();
        }

        ride = frides.retrieve();

        if (ride instanceof SharedRide) {
            tmpRiders = ((SharedRide) ride).getParticipants();

            tmpRiders.findfirst();
            while (!tmpRiders.last()) {
                result.add(tmpRiders.retrieve());
                tmpRiders.findnext();
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
    public boolean addDriver(IDriver driver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDriver'");
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

        allRides.findfirst();

        while (!allRides.last()) {
            if (allRides.retrieve().hasRider(riderId)
                    && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
                return true;
            }
            allRides.findnext();
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

        allRides.findfirst();

        while (!allRides.last()) {
            if (allRides.retrieve().getDriver().getId() == driverId
                    && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
                return true;
            }
            allRides.findnext();
        }
        if (allRides.retrieve().getDriver().getId() == driverId
                && rideOverlaps(pickupDateTime, dropoffDateTime, allRides.retrieve())) {
            return true;
        }
        return false;
    }

}
