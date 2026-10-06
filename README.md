# CSC_212_PROJECT

Set 1 (Rides and Complex Logic)
- Classes: IRide, Ride, IPrivateRide, PrivateRide, ISharedRide, SharedRide, IRideList, RideList (with alphabetical sorting).
- System Logic: schedulePrivateRide and scheduleSharedRide (preventing time conflicts), loadRidesFromCSV.
- Cascade Deletions: Delete associated rides when a rider or driver is removed.
- Menu Options: 8-12 (List/Search rides, add rides).

Set 2 (Riders and Core Linked List)
- Classes: IPerson, Person, IRider, Rider, IRiderList, RiderList.
- Core Data Structure: Build the custom Node<T> and generic LinkedList<T> classes from scratch.
- System Logic: loadRidersFromCSV, addRider, searchRider methods.
- Menu Options: 1-4 (List/Search riders, add riders).
- Report: Big-O analysis for all Rider and generic LinkedList methods.

Set 3 (Drivers, Time Utilities and Final Report)
- Classes: IDriver, Driver, VehicleType, IDateTime, DateTime (strict chronological compareTo logic).
- Data Structures: IDriverList, DriverList.
- System Logic: loadDriversFromCSV, addDriver, searchDriver methods.
- Menu Options: Set up the main while-loop, and do options 5-7 (List/Search drivers, add drivers).
- Report: Big-O analysis for Driver/DateTime methods and compile the final PDF report.
