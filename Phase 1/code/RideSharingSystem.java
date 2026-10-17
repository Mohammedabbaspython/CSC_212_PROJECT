public class RideSharingSystem implements IRideSharingSystem {

    @Override
    public boolean loadRidersFromCSV(String ridersFilePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadRidersFromCSV'");
    }

    @Override
    public boolean loadDriversFromCSV(String driversFilePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadDriversFromCSV'");
    }

    @Override
    public boolean loadRidesFromCSV(String ridesFilePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadRidesFromCSV'");
    }

    @Override
    public boolean addRider(IRider rider) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRider'");
    }

    @Override
    public boolean addDriver(IDriver driver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDriver'");
    }

    @Override
    public IRider searchRiderById(int riderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchRiderById'");
    }

    @Override
    public IRider searchRiderByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchRiderByEmail'");
    }

    @Override
    public LinkedList<IRider> searchRidersByName(String fullName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchRidersByName'");
    }

    @Override
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchRidersByHomeCity'");
    }

    @Override
    public LinkedList<IRider> getAllRiders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRiders'");
    }

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'schedulePrivateRide'");
    }

    @Override
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
            String dropoffLocation, int[] riderIds, int driverId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleSharedRide'");
    }

    @Override
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchRidesByPickupLocation'");
    }

    @Override
    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchRidesByRiderName'");
    }

    @Override
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSharedRideParticipants'");
    }

    @Override
    public LinkedList<IRide> getAllRidesAlphabetically() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRidesAlphabetically'");
    }
    
}
