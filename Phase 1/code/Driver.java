public class Driver extends Person implements IDriver {
    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType) {
        super(id, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }

    @Override
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    @Override
    public void setVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || vehiclePlate.length() != 7) {
            throw new IllegalArgumentException("Vehicle plate must be 3 uppercase letters followed by 4 digits (e.g., ABC1234).");
        }
        for (int i = 0; i < 3; i++) {
            char c = vehiclePlate.charAt(i);
            if (c < 'A' || c > 'Z') {
                throw new IllegalArgumentException("First 3 characters of plate must be uppercase letters A-Z.");
            }
        }
        for (int i = 3; i < 7; i++) {
            char c = vehiclePlate.charAt(i);
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("Last 4 characters of plate must be digits 0-9.");
            }
        }
        this.vehiclePlate = vehiclePlate;
    }

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public void setVehicleType(VehicleType vehicleType) {
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }
        this.vehicleType = vehicleType;
    }

    @Override
    public int compareTo(IDriver other) {
        return this.getId() - other.getId();
    }


    @Override
    public String toString() {
        return String.format("Driver [ID=%d, Name=%s, Phone=%s, Plate=%s, VehicleType=%s]",
                getId(), getName(), getPhoneNumber(), vehiclePlate, vehicleType);
    }
}
