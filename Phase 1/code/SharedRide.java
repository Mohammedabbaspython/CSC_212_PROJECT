public class SharedRide extends Ride implements ISharedRide{
    private LinkedList<IRider> participants;

    @Override
    public boolean hasRider(int riderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasRider'");
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    @Override
    public boolean addParticipant(IRider rider) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addParticipant'");
    }

    @Override
    public boolean removeParticipantById(int riderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeParticipantById'");
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }
     
}
