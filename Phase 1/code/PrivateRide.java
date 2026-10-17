public class PrivateRide extends Ride implements IPrivateRide {
    private IRider rider;


    public PrivateRide(IDateTime pickupDateTime, IDateTime dropoffDateTime, String pickupLocation,
            String dropoffLocation, IDriver driver, IRider rider) {
        super(pickupDateTime, dropoffDateTime, pickupLocation, dropoffLocation, driver);
        this.rider = rider;
    }

    public IRider getRider() {
        return rider;
    }

    public void setRider(IRider rider) {
        this.rider = rider;
    }

    @Override
    public boolean hasRider(int riderId) {
        if (rider == null)
            return false;
        return  riderId == rider.getId();
    }

    public boolean hasRiderByFullName(String riderFullName) {
        if (rider == null){
            return false;
        }

        return rider.getName().equals(riderFullName);
    }

    @Override
    public String toString() {
        return "PrivateRide " + super.toString();
    }

}
