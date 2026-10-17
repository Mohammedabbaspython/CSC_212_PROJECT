public class SharedRide extends Ride implements ISharedRide {
    private LinkedList<IRider> participants;

    public SharedRide(IDateTime pickupDateTime, IDateTime dropoffDateTime, String pickupLocation,
            String dropoffLocation, IDriver driver) {
        super(pickupDateTime, dropoffDateTime, pickupLocation, dropoffLocation, driver);

        participants = new LinkedList<IRider>();
    }

    @Override
    public boolean hasRider(int riderId) {
        if (participants.empty()) {
            return false;
        }

        participants.findfirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                return true;
            }
            participants.findnext();
        }

        if (participants.retrieve().getId() == riderId) {
            return true;
        }

        return false;
    }

    @Override
    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    @Override
    public boolean addParticipant(IRider rider) {
        if (rider == null || hasRider(rider.getId())) {
            return false;
        }

        participants.insert(rider);
        return true;
    }

    @Override
    public boolean removeParticipantById(int riderId) {
        if (participants.empty()) {
            return false;
        }

        participants.findfirst();
        while (!participants.last()) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                return true;
            }
            participants.findnext();
        }

        if (participants.retrieve().getId() == riderId) {
            participants.remove();
            return true;
        }

        return false;
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
