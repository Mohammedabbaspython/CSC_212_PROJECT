public class DriverList implements IDriverList {
    private final LinkedList<IDriver> list;
    private int count;

    public DriverList() {
        this.list = new LinkedList<IDriver>();
        this.count = 0;
    }

    @Override
    public boolean add(IDriver driver) {
        if (driver == null) {
            return false;
        }
        // Check duplicate ID or duplicate vehicle plate
        if (findById(driver.getId()) != null || findByVehiclePlate(driver.getVehiclePlate()) != null) {
            return false;
        }

        if (list.empty()) {
            list.insert(driver);
            count++;
            return true;
        }


        list.findFirst();
        
        while (!list.last()) {
            if (driver.compareTo(list.retrieve()) < 0) {
                IDriver temp = list.retrieve();
                list.update(driver);
                list.insert(temp);
                count++;
                return true;
            }
            list.findNext();
        }
        if (driver.compareTo(list.retrieve()) < 0) {
                IDriver temp = list.retrieve();
                list.update(driver);
                list.insert(temp);
                count++;
                return true;
            }
        else{

        list.insert(driver);
        count++;
        return true;}
    }

    @Override
    public IDriver findById(int driverId) {
        if (list.empty()) {
            return null;
        }
        list.findFirst();
        while (!list.last()) {
            IDriver d = list.retrieve();
            if (d.getId() == driverId) {
                return d;
            }
            list.findNext();
        }
        IDriver d = list.retrieve();
        return (d.getId() == driverId) ? d : null;
    }

    @Override
    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        if (fullName == null || list.empty()) {
            return result;
        }
        list.findFirst();
        while (!list.last()) {
            IDriver d = list.retrieve();
            if (d.getName().equals(fullName)) {
                result.insert(d);
            }
            list.findNext();
        }
        IDriver d = list.retrieve();
        if (d.getName().equals(fullName)) {
            result.insert(d);
        }
        return result;
    }

    @Override
    public IDriver findByVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || list.empty()) {
            return null;
        }
        list.findFirst();
        while (!list.last()) {
            IDriver d = list.retrieve();
            if (d.getVehiclePlate().equals(vehiclePlate)) {
                return d;
            }
            list.findNext();
        }
        IDriver d = list.retrieve();
        return d.getVehiclePlate().equals(vehiclePlate) ? d : null;
    }

    @Override
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();
        if (vehicleType == null || list.empty()) {
            return result;
        }
        list.findFirst();
        while (!list.last()) {
            IDriver d = list.retrieve();
            if (d.getVehicleType() == vehicleType) {
                result.insert(d);
            }
            list.findNext();
        }
        IDriver d = list.retrieve();
        if (d.getVehicleType() == vehicleType) {
            result.insert(d);
        }
        return result;
    }

    @Override
    public LinkedList<IDriver> getAll() {
        LinkedList<IDriver> copy = new LinkedList<IDriver>();
        if (list.empty()) {
            return copy;
        }
        list.findFirst();
        while (!list.last()) {
            copy.insert(list.retrieve());
            list.findNext();
        }
        copy.insert(list.retrieve());
        return copy;
    }

    @Override
    public boolean removeById(int driverId) {
        if (list.empty()) {
            return false;
        }
        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getId() == driverId) {
                list.remove();
                count--;
                return true;
            }
            list.findNext();
        }
        if (list.retrieve().getId() == driverId) {
            list.remove();
            count--;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeByVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || list.empty()) {
            return false;
        }
        String target = vehiclePlate.trim();
        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getVehiclePlate().equals(target)) {
                list.remove();
                count--;
                return true;
            }
            list.findNext();
        }
        if (list.retrieve().getVehiclePlate().equals(target)) {
            list.remove();
            count--;
            return true;
        }
        return false;
    }

    @Override
    public int removeByName(String fullName) {
        if (fullName == null || list.empty()) {
            return 0;
        }
        int removedCount = 0;
        int remainingToInspect = count;
        list.findFirst();

        while (remainingToInspect > 0 && !list.empty()) {
            remainingToInspect--;
            boolean wasLast = list.last();
            if (list.retrieve().getName().equals(fullName)) {
                list.remove();
                count--;
                removedCount++;
                if (wasLast) {
                    break;
                }
            } else {
                if (!wasLast) {
                    list.findNext();
                }
            }
        }
        return removedCount;
    }

    @Override
    public int removeByVehicleType(VehicleType vehicleType) {
        if (vehicleType == null || list.empty()) {
            return 0;
        }
        int removedCount = 0;
        int remainingToInspect = count;
        list.findFirst();

        while (remainingToInspect > 0 && !list.empty()) {
            remainingToInspect--;
            boolean wasLast = list.last();
            if (list.retrieve().getVehicleType() == vehicleType) {
                list.remove();
                count--;
                removedCount++;
                if (wasLast) {
                    break;
                }
            } else {
                if (!wasLast) {
                    list.findNext();
                }
            }
        }
        return removedCount;
    }

    @Override
    public int size() {
        return count;
    }
}
