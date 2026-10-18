public class RiderList implements IRiderList {
    private LinkedList<IRider> riders = new LinkedList<IRider>();

    public boolean add(IRider rider) {
        if (rider == null) {
            return false;
        }

        if (findById(rider.getId()) != null) {
            return false;
        }
        if (findByEmail(rider.getEmail()) != null) {
            return false;
        }
        if (riders.empty()) {
            riders.insert(rider);
            return true;
        }
        riders.findfirst();
        while (!riders.last()) {
            if (rider.compareTo(riders.retrieve()) < 0) {
                IRider tmp = riders.retrieve();
                riders.update(rider);
                riders.insert(tmp);
                return true;
            }
            riders.findnext();
        }
        if (rider.compareTo(riders.retrieve()) < 0) {
            IRider tmp = riders.retrieve();
            riders.update(rider);
            riders.insert(tmp);

        } else {
            riders.insert(rider);
        }
        return true;
    }

    public IRider findById(int riderId) {
        if (riders.empty()) {
            return null;
        }
        riders.findfirst();
        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId) {
                return riders.retrieve();
            }
            riders.findnext();

        }
        if (riders.retrieve().getId() == riderId) {
            return riders.retrieve();

        }
        return null;
    }

    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        if (riders.empty()) {
            return result;
        }

        riders.findfirst();
        while (!riders.last()) {
            if (riders.retrieve().getName().equals(fullName)) {
                result.insert(riders.retrieve());
            }
            riders.findnext();
        }
        if (riders.retrieve().getName().equals(fullName)) {
            result.insert(riders.retrieve());
        }
        return result;
    }

    public IRider findByEmail(String email) {
        if (riders.empty()) {
            return null;
        }
        riders.findfirst();
        while (!riders.last()) {
            if (riders.retrieve().getEmail().equals(email)) {
                return riders.retrieve();
            }
            riders.findnext();

        }
        if (riders.retrieve().getEmail().equals(email)) {
            return riders.retrieve();

        }
        return null;
    }

    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        if (riders.empty()) {
            return result;
        }

        riders.findfirst();
        while (!riders.last()) {
            if (riders.retrieve().getHomeCity().equals(homeCity)) {
                result.insert(riders.retrieve());
            }
            riders.findnext();
        }
        if (riders.retrieve().getHomeCity().equals(homeCity)) {
            result.insert(riders.retrieve());
        }
        return result;
    }

    public LinkedList<IRider> getAll() {
        return riders;
    }

    public boolean removeById(int riderId) {
        if (riders.empty()) {
            return false;
        }
        riders.findfirst();
        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId) {
                riders.remove();
                return true;
            }
            riders.findnext();
        }
        if (riders.retrieve().getId() == riderId) {
            riders.remove();
            return true;
        }
        return false;
    }

    public boolean removeByEmail(String email) {
        if (riders.empty()) {
            return false;
        }
        riders.findfirst();
        while (!riders.last()) {
            if (riders.retrieve().getEmail().equals(email)) {
                riders.remove();
                return true;
            }
            riders.findnext();

        }
        if (riders.retrieve().getEmail().equals(email)) {
            riders.remove();
            return true;
        }
        return false;
    }

    public int removeByName(String fullName) {
        int count = 0;
        riders.findfirst();
        while (!riders.empty()) {
            if (riders.retrieve().getName().equals(fullName)) {
                count++;
                riders.remove();
            } else {
                if (riders.last()) {
                    break;
                }
                riders.findnext();
            }
        }
        return count;
    }

    public int removeByHomeCity(String homeCity) {
        int count = 0;
        riders.findfirst();
        while (!riders.empty()) {
            if (riders.retrieve().getHomeCity().equals(homeCity)) {
                count++;
                riders.remove();

            } else {
                if (riders.last()) {
                    break;
                }
                riders.findnext();
            }
        }
        return count;
    }

    public int size() {
        return riders.size();
    }

}
