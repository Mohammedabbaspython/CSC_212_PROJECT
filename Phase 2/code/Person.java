public abstract class Person implements IPerson {
    private String name;
    private String phoneNumber;
    private IBST<ITimeSlot, IRide> schedule = new BST<ITimeSlot, IRide>();
    private int id;

    public Person(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
            }

        }

        this.phoneNumber = phoneNumber;
    }

    public IBST<ITimeSlot , IRide> getSchedule() {
        return schedule;
    }
    public String toString() {
        return "ID: " + getId() + ", Name: " + getName() + ", Phone: " + getPhoneNumber();
    }

}
