import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        RideSharingSystem rideSystem = new RideSharingSystem();
        // loading
        rideSystem.loadDriversFromCSV(null);
        rideSystem.loadRidersFromCSV(null);
        rideSystem.loadRidesFromCSV(null);
        
        Scanner input = new Scanner(System.in);
        String option;
        do {
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
            System.out.print("Enter option: ");
            option = input.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Here is the list all the riders in the system: ");
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
                    break;
                case "2":
                    // Search for riders by home city
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
                    break;
                case "3":
                    // Search for riders by name
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
                    break;
                case "4":
                    // Add a rider
                    boolean enter = true;
                    int id = 0;
                    while (enter) {
                        try {
                            System.out.print("Enter Rider ID: ");
                            id = input.nextInt();
                            enter = false;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Try again");
                            input.nextLine();
                        }
                    }
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
                    break;
                case "5":
                    // List all drivers
                    break;
                case "6":
                    // Search for drivers by vehicle type
                    break;
                case "7":
                    // Add a driver
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
}
