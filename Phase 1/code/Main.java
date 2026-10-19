import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        RideSharingSystem riders = new RideSharingSystem();
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
                    LinkedList<IRider> allRiders = riders.getAllRiders();
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
                    LinkedList<IRider> foundHomeCityRider = riders.searchRidersByHomeCity(homeCity);
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
                    LinkedList<IRider> foundRiderName = riders.searchRidersByName(name);
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
                        if (riders.addRider(newRider)) {
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
                    break;
                case "9":
                    // Search for rides by pickup location.
                    break;
                case "10":
                    // Search for rides by rider name.
                    break;
                case "11":
                    // List the participants of a shared ride by pickup location.
                    break;
                case "12":
                    // Add a ride
                    break;
                case "0":
                    System.out.println("Exiting the program....");
                    break;
                default:{
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } while (!option.equals("0"));

    }

}
