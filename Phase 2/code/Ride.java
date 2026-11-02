public abstract class Ride implements IRide {
    protected IDateTime pickupDateTime;
    protected IDateTime dropoffDateTime;

    protected String pickupLocation;
    protected String dropoffLocation;

    protected IDriver driver;

    protected int rideId;

    protected static int rideCount;

    public Ride(IDateTime pickupDateTime, IDateTime dropoffDateTime, String pickupLocation, String dropoffLocation,
            IDriver driver) {
        this.pickupDateTime = pickupDateTime;
        this.dropoffDateTime = dropoffDateTime;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.driver = driver;

        rideId = rideCount++;
    }

    // getters
    public IDateTime getPickupTime() {
        return pickupDateTime;
    }

    public IDateTime getDropoffTime() {
        return dropoffDateTime;
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

    // setters
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setDriver(IDriver driver) {
        this.driver = driver;
    }

    abstract public boolean hasRiderByFullName(String riderFullName);
    
    // toString()
    @Override
    public String toString() {
        return "Ride [pickupDateTime=" + pickupDateTime + ", dropoffDateTime=" + dropoffDateTime + ", pickupLocation="
                + pickupLocation + ", dropoffLocation=" + dropoffLocation + ", driver=" + driver
                + ", rideId=" + rideId + "]";
    }

    @Override
    public int compareTo(IRide other) {
        return this.getPickupLocation().compareTo(other.getPickupLocation());
    }
}
