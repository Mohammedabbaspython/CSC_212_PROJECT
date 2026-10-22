import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static RideSharingSystem rideSystem = new RideSharingSystem();

    public static void main(String args[]) {
        // loading
        rideSystem.loadDriversFromCSV("Phase 1/drivers_30.csv");
        rideSystem.loadRidersFromCSV("Phase 1/riders_100.csv");
        rideSystem.loadRidesFromCSV("Phase 1/rides_40.csv");

        String option;
        do {
            printMenu();
            System.out.print("Enter option: ");
            option = input.nextLine();
            switch (option) {
                case "1":
                    printRiders();
                    break;

                case "2":
                    // Search for riders by home city
                    searchRidersByHomeCity();
                    break;

                case "3":
                    // Search for riders by name
                    searchRidersByName();
                    break;

                case "4":
                    // Add a rider
                    addRider();
                    break;

                case "5":
                    // List all drivers
                    printDrivers();
                    break;

                case "6":
                    // Search for drivers by vehicle type
                    searchDriversByVehicleType();
                    break;
                case "7":
                    // Add a driver
                    addDriver();
                    break;
                case "8":
                    // List all rides alphabetically by pickup location.
                    printRides(rideSystem.getAllRidesAlphabetically());
                    break;
                case "9":
                    // Search for rides by pickup location.
                    System.out.print("Enter pickup location: ");
                    printRides(rideSystem.searchRidesByPickupLocation(input.nextLine()));
                    break;
                case "10":
                    // Search for rides by rider name.
                    System.out.print("Enter Rider Name: ");
                    printRides(rideSystem.searchRidesByRiderName(input.nextLine()));
                    break;
                case "11":
                    // List the participants of a shared ride by pickup location.
                    System.out.print("Enter pickup location: ");
                    printSharedRidesParticipants(rideSystem.getSharedRideParticipants(input.nextLine()));
                    break;
                case "12":
                    // Add a ride
                    addRide();
                    break;
                case "0":
                    System.out.println("Exiting the program....");
                    break;
                default: {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } while (!option.equals("0"));
        input.close();
    }

    private static void printRides(LinkedList<IRide> allRides) {
        if (allRides.empty()) {
            System.out.println("There are no scheduled rides.");
            return;
        }

        System.out.println("Rides: ");

        allRides.findfirst();

        while (!allRides.last()) {
            System.out.println(allRides.retrieve());
            allRides.findnext();
        }

        System.out.println(allRides.retrieve());
    }

    private static void printSharedRidesParticipants(LinkedList<IRider> riders) {
        if (riders.empty()) {
            System.out.println("No Shared rides participants found.");
            return;
        }

        riders.findfirst();

        while (!riders.last()) {
            System.out.println(riders.retrieve());
            riders.findnext();
        }

        System.out.println(riders.retrieve());
    }

    private static void printMenu() {
        System.out.println("1. List all riders");
        System.out.println("2. Search for riders by home city");
        System.out.println("3. Search for riders by name");
        System.out.println("4. Add a rider");
        System.out.println("5. List all drivers");
        System.out.println("6. Search for drivers by vehicle type");
        System.out.println("7. Add a driver");
        System.out.println("8. List all rides alphabetically by pickup location.");
        System.out.println("9. Search for rides by pickup location.");
        System.out.println("10. Search for rides by rider name.");
        System.out.println("11. List the participants of a shared ride by pickup location.");
        System.out.println("12. Add a ride");
    }

    private static void printRiders() {
        System.out.println("Here is the list of all the riders in the system: ");
        LinkedList<IRider> allRiders = rideSystem.getAllRiders();
        if (allRiders.empty()) {
            System.out.println("There is no riders.");
        } else {
            allRiders.findfirst();
            while (!allRiders.last()) {
                System.out.println(allRiders.retrieve().toString());
                allRiders.findnext();
            }
            System.out.println(allRiders.retrieve().toString());
        }
    }

    private static void searchRidersByHomeCity() {
        System.out.println("Enter Home City: ");
        String homeCity = input.nextLine();
        LinkedList<IRider> foundHomeCityRider = rideSystem.searchRidersByHomeCity(homeCity);
        if (foundHomeCityRider.empty()) {
            System.out.println("There is no riders.");
        } else {
            foundHomeCityRider.findfirst();
            while (!foundHomeCityRider.last()) {
                System.out.println(foundHomeCityRider.retrieve().toString());
                foundHomeCityRider.findnext();
            }
            System.out.println(foundHomeCityRider.retrieve().toString());
        }
    }

    private static void addRider() {
        System.out.print("Enter Rider ID: ");

        int id = takeIntInput();
        input.nextLine();
        System.out.print("Enter Rider Name: ");
        String RiderName = input.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = input.nextLine();
        System.out.print("Enter Email: ");
        String email = input.nextLine();
        System.out.print("Enter Home City: ");
        String RiderHomeCity = input.nextLine();
        try {
            IRider newRider = new Rider(id, RiderName, phoneNumber, email, RiderHomeCity);
            if (rideSystem.addRider(newRider)) {
                System.out.println("Rider added successfully.");
            } else {
                System.out.println("Failed to add rider.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchRidersByName() {
        System.out.println("Enter Name: ");
        String name = input.nextLine();
        LinkedList<IRider> foundRiderName = rideSystem.searchRidersByName(name);
        if (foundRiderName.empty()) {
            System.out.println("There is no riders.");
        } else {
            foundRiderName.findfirst();
            while (!foundRiderName.last()) {
                System.out.println(foundRiderName.retrieve().toString());
                foundRiderName.findnext();
            }
            System.out.println(foundRiderName.retrieve().toString());
        }
    }

    private static void addRide() {
        // success
        boolean success = false;

        // ride type
        System.out.println("Ride types:\n1-Private Ride\n2-Shared Ride.");
        int rideType = takeChoiceInput(2);

        // pickup/dropoff datetime
        IDateTime pickupDateTime;
        IDateTime dropoffDateTime;

        while (true) {
            try {
                System.out.println("Enter pickup time (the format is MM/DD/YYYY HH:MM): ");
                pickupDateTime = new DateTime(input.nextLine());

                System.out.println("Enter dropoff time (the format is MM/DD/YYYY HH:MM): ");
                dropoffDateTime = new DateTime(input.nextLine());

                break;
            } catch (IllegalArgumentException e) {
                System.out.println("invalid date time format try again");
                continue;
            }
        }

        // pickup/dropoff location
        System.out.println("Enter pickup location: ");
        String pickupLocation = input.nextLine();

        System.out.println("Enter dropoff location: ");
        String dropoffLocation = input.nextLine();

        // driver id
        System.out.println("Enter driver ID: ");
        int driverId = takeIntInput();

        if (rideType == 1) {
            // rider id
            System.out.println("Enter rider ID: ");
            int riderId = takeIntInput();

            // schedule private ride
            success = rideSystem.schedulePrivateRide(pickupLocation, pickupDateTime, dropoffDateTime, dropoffLocation,
                    riderId, driverId);
        } else {
            // riders ids
            System.out.println("Enter the number of riders: ");
            int nriders = takePositiveIntInput();
            int[] ridersIds = new int[nriders];

            for (int i = 0; i < nriders; i++) {
                ridersIds[i] = takeIntInput();
            }

            success = rideSystem.scheduleSharedRide(pickupLocation, pickupDateTime, dropoffDateTime, dropoffLocation,
                    ridersIds, driverId);
        }

        if (success) {
            System.out.println("Ride Scheduled Successfully!");
        } else {
            System.out.println("Ride Scheduling Failed!");
        }
    }

    // handles input
    public static int takeIntInput() {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                input.nextLine(); // clear invalid input
            }
        }
    }

    private static int takeChoiceInput(int numOfOptions) {
        while (true) {
            System.out.print("Choose an option: ");

            int choice = takeIntInput();

            if (choice >= 1 && choice <= numOfOptions) {
                return choice;
            }

            System.out.println(
                    "\tInvalid input. Please enter an integer between 1 and " + numOfOptions);
        }
    }

    private static int takePositiveIntInput() {
        while (true) {
            int value = takeIntInput();

            if (value > 0) {
                return value;
            }

            System.out.println("Invalid input. Please enter a positive integer.");
        }
    }

    private static void printDrivers() {
        System.out.println("Here is the list of all the Drivers in the system: ");
        LinkedList<IDriver> allDrivers = rideSystem.getAllDrivers();
        if (allDrivers.empty()) {
            System.out.println("There is no Drivers.");
        } else {
            allDrivers.findfirst();
            while (!allDrivers.last()) {
                System.out.println(allDrivers.retrieve().toString());
                allDrivers.findnext();
            }
            System.out.println(allDrivers.retrieve().toString());
        }
    }

    private static void searchDriversByVehicleType() {
        System.out.println("Enter VehicleType (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
        try {
            VehicleType vehicleType = VehicleType.valueOf(input.nextLine().trim().toUpperCase());// Converts String to
                                                                                                 // Enum
            LinkedList<IDriver> foundVehicleTypeDriver = rideSystem.searchDriversByVehicleType(vehicleType);
            if (foundVehicleTypeDriver.empty()) {
                System.out.println("There are no drivers with this vehicle type.");
            } else {
                foundVehicleTypeDriver.findfirst();
                while (!foundVehicleTypeDriver.last()) {
                    System.out.println(foundVehicleTypeDriver.retrieve().toString());
                    foundVehicleTypeDriver.findnext();
                }
                System.out.println(foundVehicleTypeDriver.retrieve().toString());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type entered.");
        }
    }

    private static void addDriver() {
        System.out.print("Enter Driver ID: ");

        int id = takeIntInput();
        input.nextLine();
        System.out.print("Enter Driver Name: ");
        String DriverName = input.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = input.nextLine();
        System.out.print("Enter Vehicle Plate: ");
        String vehiclePlate = input.nextLine();
        System.out.print("Enter Vehicle Type (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
        VehicleType vehicleType = VehicleType.valueOf(input.nextLine().trim().toUpperCase()); // Converts String to Enum
        try {
            IDriver newDriver = new Driver(id, DriverName, phoneNumber, vehiclePlate, vehicleType);
            if (rideSystem.addDriver(newDriver)) {
                System.out.println("Driver added successfully.");
            } else {
                System.out.println("Failed to add driver.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
