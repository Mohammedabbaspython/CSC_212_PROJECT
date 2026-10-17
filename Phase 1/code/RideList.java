public class RideList implements IRideList {
    private LinkedList<IRide> rides;

    public RideList() {
        rides = new LinkedList<IRide>();
    }

    @Override
    public boolean addRide(IRide ride) {
        if (ride == null || hasRide(ride.getRideId())) {
            return false;
        }
        if (rides.empty()) {
            rides.insert(ride);
        } else {
            rides.findfirst();

            IRide tmp;

            while (!rides.last()) {
                if (ride.compareTo(rides.retrieve()) < 0) {
                    tmp = rides.retrieve();
                    rides.update(ride);
                    rides.insert(tmp);
                    return true;
                }
                rides.findnext();
            }

            if (ride.compareTo(rides.retrieve()) < 0) {
                tmp = rides.retrieve();
                rides.update(ride);
                rides.insert(tmp);
            } else {
                rides.insert(ride);
            }
        }

        return true;
    }

    @Override
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<IRide>();

        if (rides.empty()) {
            return result;
        }

        rides.findfirst();

        while (!rides.last()) {
            if (rides.retrieve().getPickupLocation().equals(pickupLocation)) {
                result.insert(rides.retrieve());
            }
            rides.findnext();
        }

        if (rides.retrieve().getPickupLocation().equals(pickupLocation)) {
            result.insert(rides.retrieve());
        }

        return result;
    }

    @Override
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        LinkedList<IRide> result = new LinkedList<IRide>();

        if (rides.empty()) {
            return result;
        }

        rides.findfirst();

        while (!rides.last()) {
            if (((Ride) rides.retrieve()).hasRiderByFullName(riderFullName)) {
                result.insert(rides.retrieve());
            }
            rides.findnext();
        }

        if (((Ride) rides.retrieve()).hasRiderByFullName(riderFullName)) {
            result.insert(rides.retrieve());
        }

        return result;
    }

    @Override
    public LinkedList<IRide> getAllAlphabetically() {
        LinkedList<IRide> result = new LinkedList<IRide>();

        if (rides.empty()) {
            return result;
        }

        rides.findfirst();

        while (!rides.last()) {
            result.insert(rides.retrieve());
            rides.findnext();
        }

        result.insert(rides.retrieve());

        return result;
    }

    @Override
    public boolean removeRideById(int rideId) {
        if (!hasRide(rideId)) {
            return false;
        }

        rides.findfirst();
        while (!rides.last()) {
            if (rides.retrieve().getRideId() == rideId) {
                rides.remove();
                return true;
            }
            rides.findnext();
        }
        rides.remove();

        return true;
    }

    @Override
    public int size() {
        return rides.size();
    }

    boolean hasRide(int rideId) {
        if (rides.empty()) {
            return false;
        }

        rides.findfirst();
        while (!rides.last()) {
            if (rides.retrieve().getRideId() == rideId) {
                return true;
            }
            rides.findnext();
        }

        if (rides.retrieve().getRideId() == rideId) {
            return true;
        }

        return false;
    }
}
