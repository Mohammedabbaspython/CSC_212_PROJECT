public class SharedRide extends Ride implements ISharedRide {
    private IBST<Integer, IRider> participants;

    public SharedRide(int rideId, String pickupLocation, String dropoffLocation, ITimeSlot timeSlot, IDriver driver) {
        super(rideId, pickupLocation, dropoffLocation, timeSlot, driver);

        participants = new BST<Integer, IRider>();
    }

    @Override
    public boolean hasRider(int riderId) {
        return participants.findKey(riderId);
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants.inOrder();
    }

    @Override
    public boolean addParticipant(IRider rider) {
        return participants.insert(rider.getId(), rider);
    }

    @Override
    public boolean removeParticipantById(int riderId) {
        return participants.removeKey(riderId); 
    }

    @Override
    public boolean isEmpty() {
        return participants.empty();
    }

    @Override
    public String toString() {
        return String.format("SharedRide [%d] ", participants.size()) + super.toString();
    }

}
