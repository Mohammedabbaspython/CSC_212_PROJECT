public class PrivateRide extends Ride implements IPrivateRide {
    private IRider rider;


    public PrivateRide(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, 
        IRider rider, IDriver driver) {
        super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);

        this.rider = rider;
    }

    public IRider getRider() {
        return rider;
    }

    @Override
    public boolean hasRider(int riderId) {
        if (rider == null)
            return false;
        return  riderId == rider.getId();
    }

    @Override
    public String toString() {
        return "PrivateRide " + super.toString();
    }

}
