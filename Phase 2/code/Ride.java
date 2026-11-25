public abstract class Ride implements IRide {
    protected ITimeSlot timeSlot;

    protected String pickupLocation;
    protected String dropoffLocation;

    protected IDriver driver;

    protected int rideId;


    public Ride(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IDriver driver) {
        this.timeSlot = timeSlot;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.driver = driver;

        this.rideId = rideId;
    }

    // getters
    public ITimeSlot getTimeSlot() {
        return timeSlot;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public IDriver getDriver() {
        return driver;
    }

    public int getRideId() {
        return rideId;
    }
    
    // toString()
    @Override
    public String toString() {
        return "Ride [rideId=" + rideId + ", pickupDateTime=" + timeSlot.getStart() + ", dropoffDateTime=" + timeSlot.getEnd() + ", pickupLocation="
                + pickupLocation + ", dropoffLocation=" + dropoffLocation + ", driver=" + driver + "]";
    }

    @Override
    public int compareTo(IRide other) {
        return Integer.compare(rideId, other.getRideId());
    }
}
