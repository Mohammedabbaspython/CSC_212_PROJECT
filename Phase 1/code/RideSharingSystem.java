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
        String filePath = "riders_100.csv";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

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
        String filePath = "riders_100.csv";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(",");

                // riders.add(new Driver(Integer.parseInt(data[0]), data[1], data[3], data[2],
                // data[4]));

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
        String filePath = "riders_100.csv";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(",");

                IRide ride;

                if (data[0].equals("SHARED")) {
                    // ride = new PrivateRide(new DateTime(data[2]), new DateTime(data[3]), null)
                } else if (data[0].equals("PRIVATE")) {

                } else {
                    continue;
                }

                rides.addRide(ride);

                line = br.readLine();
            }

        } catch (IOException e) {
            return false;
        }
        return true;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRider'");
    }

    @Override
    public boolean removeDriver(int driverId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeDriver'");
    }

    @Override
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
            String dropoffLocation, int riderId, int driverId) {

        IDriver driver = searchDriverById(driverId);
        IRider rider = searchRiderById(riderId);

        if (driver != null || rider != null || pickupTime.compareTo(dropoffTime) >= 0
                || riderHasRideConflict(pickupTime, dropoffTime, riderId)
                || driverHasRideConflict(pickupTime, dropoffTime, driverId)) {
            return false;
        }
        rides.addRide(new PrivateRide(pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver, rider));
        return true;
    }

    @Override
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
            String dropoffLocation, int[] riderIds, int driverId) {
        IDriver driver = searchDriverById(driverId);
        IRider rider;

        if (driver != null || pickupTime.compareTo(dropoffTime) >= 0
                || driverHasRideConflict(pickupTime, dropoffTime, driverId)) {
            return false;
        }
        
        SharedRide ride = new SharedRide(pickupTime, dropoffTime, pickupLocation, dropoffLocation, driver);

        for (int riderid: riderIds) {
            rider = searchRiderById(riderid)
            if (rider == null || riderHasRideConflict(pickupTime, dropoffTime, riderid)) {
                return false;    
            }
            
            ride.addParticipant(rider);
        }

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
        if ((pickupDateTime.compareTo(other.getPickupTime()) >= 0
                && pickupDateTime.compareTo(other.getDropoffTime()) <= 0)
                || (dropoffDateTime.compareTo(other.getPickupTime()) >= 0
                        && dropoffDateTime.compareTo(other.getDropoffTime()) <= 0)) {
            return true;
        }
        return false;
    }

    private boolean riderHasRideConflict(IDateTime pickupDateTime, IDateTime dropoffDateTime, int riderId) {
        LinkedList<IRide> allRides = getAllRidesAlphabetically();

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
