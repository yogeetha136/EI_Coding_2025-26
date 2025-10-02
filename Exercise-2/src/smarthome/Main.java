import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import manager.UserManager;
import manager.RoomManager;
import manager.DeviceManager;
import model.User;
import model.Trigger;
import manager.Scheduler;
import util.SmartHomeLogger;

class Main{
    // Constant for the exit menu option.
    private final static int EXIT_CHOICE = 0;

    // Tracks the currently logged-in user for permission checks.
    private static User currentUser = null;

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int moduleChoice = -1; // Initialize to a non-exit value

        // Start the background scheduler for timed tasks.
        Scheduler.startScheduler();

        // Main application loop
        do{
            System.out.println("------------------------------------");
            System.out.println("        SMART HOME MODULES          ");
            System.out.println("------------------------------------");
            System.out.println("1. User Module");
            System.out.println("2. Room Module");
            System.out.println("3. Device Module");
            System.out.println("0. Exit Application");
            System.out.print("Select a module: ");

            try {
                // Check if the next input is an integer
                if (!scanner.hasNextInt()) {
                    System.err.println("ERROR: Invalid input for module selection. Please enter a number (0-3).");
                    scanner.next(); 
                    continue;
                }
                moduleChoice = scanner.nextInt();
                scanner.nextLine(); 

            } catch (Exception e) {
                System.err.println("CRITICAL ERROR during input: " + e.getMessage());
                continue;
            }
            
            if (moduleChoice == EXIT_CHOICE) {
                Scheduler.stopScheduler();
                break;
            }

            // Route user input to the correct module.
            switch(moduleChoice){
                case 1:
                    //User Module Menu
                    System.out.println("\n--- User Module ---");
                    System.out.println("1. List the users");
                    System.out.println("2. Add users");
                    System.out.println("3. Get user by ID");
                    System.out.println("4. User login");
                    System.out.print("Select Option: ");
                    
                    int userChoice = -1;
                    try {
                        if (!scanner.hasNextInt()) {
                            System.err.println("ERROR: Invalid input for User Module option. Returning to main menu.");
                            scanner.next();
                            break;
                        }
                        userChoice = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: Please enter a numeric option.");
                        scanner.nextLine();
                        break;
                    }

                    switch(userChoice){
                        case 1:
                            UserManager.listUsers(); 
                            break;
                        case 2:
                            // Add User Logic
                            System.out.print("User Name: ");
                            String userName = scanner.nextLine();
                            System.out.print("Email ID: ");
                            String emailID = scanner.nextLine();
                            System.out.print("Password: ");
                            String passWord = scanner.nextLine();
                            UserManager.addUser(userName, emailID, passWord);
                            SmartHomeLogger.info("Main", "New user added: " + userName + " (" + emailID + ")");
                            break;
                        case 3:
                            // Get User By ID Logic
                            System.out.print("User ID: ");
                            try {
                                if (!scanner.hasNextInt()) {
                                    System.err.println("ERROR: Invalid User ID. Must be a number.");
                                    scanner.next();
                                    break;
                                }
                                int userID = scanner.nextInt();
                                scanner.nextLine();
                                UserManager.getUser(userID);
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for User ID.");
                                scanner.nextLine();
                            }
                            break;
                        case 4:
                            // User Login Logic
                            System.out.print("Email: ");
                            String email = scanner.nextLine();
                            System.out.print("Password: ");
                            String password = scanner.nextLine();
                            currentUser = UserManager.login(email, password);
                            if (currentUser != null) {
                                SmartHomeLogger.info("Main", "User " + currentUser.getUserID() + " (" + currentUser.getUserName() + ") logged in."); // <<< USER LOGIN LOG
                            }
                            break;
                        default:
                            System.out.println("Invalid User Module option.");
                            break;
                    }
                    break;

                case 2:
                    //Room Module Menu
                    System.out.println("\n--- Room Module ---");
                    System.out.println("1. List the rooms");
                    System.out.println("2. Add rooms");
                    System.out.println("3. Get room by ID");
                    System.out.println("4. Give User permissions to rooms");
                    System.out.println("5. List users in a room by ID");
                    System.out.println("6. Give devices access to rooms");
                    System.out.println("7. List devices in a room by ID");
                    System.out.println("8. Turn ON/OFF devices in a room");
                    System.out.print("Select Option: "); 
                    
                    int roomChoice = -1;
                    try {
                        if (!scanner.hasNextInt()) {
                            System.err.println("ERROR: Invalid input for Room Module option. Returning to main menu.");
                            scanner.next();
                            break;
                        }
                        roomChoice = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: Please enter a numeric option.");
                        scanner.nextLine();
                        break;
                    }
                    
                    String roomType;
                    String roomName;
                    int roomID = -1;
                    int userID = -1;
                    // Get current user's ID for authorization; -1 if not logged in.
                    int controllingUserID = (currentUser != null) ? currentUser.getUserID() : -1;
                    
                    // Pre-check for actions requiring login (4 through 8).
                    if (roomChoice >= 4 && currentUser == null) {
                        System.err.println("AUTHORIZATION ERROR: You must be logged in to perform option " + roomChoice + ".");
                        break;
                    }

                    switch(roomChoice){
                        case 1:
                            RoomManager.listRooms();
                            break;
                        case 2:
                            // Add Room Logic
                            RoomManager.listAvailableRoomOptions();
                            System.out.print("Room Name: ");
                            roomName = scanner.nextLine();
                            System.out.print("Room Type: ");
                            roomType = scanner.nextLine();
                            RoomManager.addRooms(roomName, roomType);
                            SmartHomeLogger.info("Main", "New room added: " + roomName + " (" + roomType + ")");
                            break;
                        case 3:
                            // Get Room By ID Logic
                            System.out.print("Enter the room ID: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid Room ID. Must be a number.");
                                scanner.next();
                                break;
                            }
                            roomID = scanner.nextInt();
                            scanner.nextLine();
                            RoomManager.getRoom(roomID); 
                            break;
                        case 4:
                            // Add User Permissions to Room
                            System.out.print("Room ID to add users to: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid Room ID.");
                                scanner.next();
                                break;
                            }
                            roomID = scanner.nextInt();
                            
                            System.out.print("Number of User IDs to add: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid count.");
                                scanner.next();
                                break;
                            }
                            int userCount = scanner.nextInt();
                            scanner.nextLine(); 
                            
                            List<Integer> userIDList = new ArrayList<>();
                            System.out.println("Enter the User IDs one by one:");
                            for(int i=0; i<userCount; i++){
                                System.out.print("User ID "+(i+1)+": ");
                                try {
                                    if (scanner.hasNextInt()) {
                                        userID = scanner.nextInt();
                                        userIDList.add(userID);
                                    } else {
                                        System.err.println("ERROR: Invalid User ID, skipping.");
                                        scanner.next(); 
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("ERROR: Invalid User ID format, skipping.");
                                    scanner.nextLine();
                                }
                            }
                            scanner.nextLine(); 
                            RoomManager.addUserToRoom(roomID, userIDList); 
                            SmartHomeLogger.info("Main", "Permissions updated: Added users " + userIDList + " to Room ID " + roomID);
                            break;
                        
                        case 5:
                            // List users in a room
                            System.out.print("User ID: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid User ID.");
                                scanner.next();
                                break;
                            }
                            userID = scanner.nextInt();
                            scanner.nextLine();
                            RoomManager.listUserIdsInRooms(userID); 
                            break;
                            
                        case 6:
                            // Give devices access to rooms
                            System.out.print("Room ID to assign devices to: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid Room ID.");
                                scanner.next();
                                break;
                            }
                            int roomNo = scanner.nextInt();
                            scanner.nextLine();
                            
                            // Check if the current user has authorization to modify the room.
                            if (!RoomManager.isUserAuthorizedForRoom(roomNo, controllingUserID)) {
                                System.err.println("AUTHORIZATION ERROR: You do not have permission to assign devices to Room " + roomNo + ".");
                                break;
                            }
                            
                            System.out.print("Number of Device IDs to add: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid count.");
                                scanner.next();
                                break;
                            }
                            int deviceCount = scanner.nextInt();
                            scanner.nextLine();
                            
                            System.out.println("Enter the Device IDs one by one:");
                            List<Integer> deviceNo = new ArrayList<>();
                            for(int i=0; i<deviceCount; i++){
                                System.out.print("Device ID "+(i+1)+": ");
                                try {
                                    if (scanner.hasNextInt()) {
                                        int device = scanner.nextInt();
                                        deviceNo.add(device);
                                    } else {
                                        System.err.println("ERROR: Invalid Device ID, skipping.");
                                        scanner.next(); 
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("ERROR: Invalid Device ID format, skipping.");
                                    scanner.nextLine();
                                }
                            }
                            scanner.nextLine(); 
                            RoomManager.addDeviceToRoom(roomNo, deviceNo);
                            SmartHomeLogger.info("Main", "Room update: Added devices " + deviceNo + " to Room ID " + roomNo);
                            break;
                            
                        case 7:
                            // List devices in a room
                            System.out.print("Room ID to list devices: ");
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid Room ID.");
                                scanner.next();
                                break;
                            }
                            int roomID_list = scanner.nextInt();
                            scanner.nextLine();
                            
                            // Check user permission before listing devices.
                            if (!RoomManager.isUserAuthorizedForRoom(roomID_list, controllingUserID)) {
                                System.err.println("AUTHORIZATION ERROR: You do not have permission to view devices in Room " + roomID_list + ".");
                                break;
                            }
                            RoomManager.getDeviceIdsInRoom(roomID_list); 
                            break;
                            
                        case 8:
                            // Turn ON/OFF devices in a room
                            // Authorization check already done above.
                            
                            // List rooms and devices the current user can control.
                            RoomManager.listUserRoomsAndDevices(controllingUserID); 

                            System.out.print("Room ID to control: ");
                            int roomIdToControl = -1;
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid input for Room ID.");
                                scanner.nextLine(); 
                                break;
                            }
                            roomIdToControl = scanner.nextInt();
                            scanner.nextLine(); 
                            
                            // Check if room exists.
                            if(RoomManager.findRoomById(roomIdToControl) == null) {
                                System.err.println("ERROR: Room ID " + roomIdToControl + " does not exist.");
                                break;
                            }
                            
                            // Final check for user control permission on the selected room.
                            if (!RoomManager.isUserAuthorizedForRoom(roomIdToControl, controllingUserID)) {
                                System.err.println("ACCESS DENIED: You do not have permission to control devices in Room " + roomIdToControl + ".");
                                break;
                            }
                            
                            RoomManager.listRoomDeviceStatuses(roomIdToControl);

                            System.out.print("Enter the Device ID you wish to turn ON/OFF: ");
                            int deviceIdToControl = -1;
                            if (!scanner.hasNextInt()) {
                                System.err.println("ERROR: Invalid input for Device ID.");
                                scanner.nextLine();
                                break;
                            }
                            deviceIdToControl = scanner.nextInt();
                            scanner.nextLine();

                            // Check if the target device exists system-wide.
                            if (DeviceManager.findDeviceById(deviceIdToControl) == null) {
                                System.err.println("ERROR: Device ID " + deviceIdToControl + " does not exist in the system.");
                                break;
                            }

                            System.out.print("Enter action to perform on Device " + deviceIdToControl + " (ON/OFF): ");
                            String action = scanner.nextLine().trim().toUpperCase();
                            
                            if (!action.equals("ON") && !action.equals("OFF")) {
                                System.err.println("ERROR: Invalid action. Must be 'ON' or 'OFF'.");
                                break;
                            }

                            List<Integer> singleDeviceList = new ArrayList<>();
                            singleDeviceList.add(deviceIdToControl);

                            RoomManager.controlSpecificDevicesInRoom(roomIdToControl, singleDeviceList, action, controllingUserID);
                            break;
                        default:
                            System.out.println("Invalid Room Module option.");
                            break;
                    }
                    break;
                
                case 3:
                    //Device Module Menu
                    System.out.println("\n--- Device Module ---");
                    System.out.println("1. List the devices");
                    System.out.println("2. Add devices");
                    System.out.println("3. Get device by ID");
                    System.out.println("4. Get device status");
                    System.out.println("5. Add Automated Trigger");
                    System.out.println("6. Update Sensor Reading & Check Triggers");
                    System.out.println("7. Add Schedule Task");
                    System.out.println("8. List Scheduled Tasks");
                    System.out.print("Select Option: ");
                    
                    int deviceChoice = -1;
                    try {
                        if (!scanner.hasNextInt()) {
                            System.err.println("ERROR: Invalid input for Device Module option. Returning to main menu.");
                            scanner.next();
                            break;
                        }
                        deviceChoice = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: Please enter a numeric option.");
                        scanner.nextLine();
                        break;
                    }
                    
                    int deviceID = -1;
                   
                    switch(deviceChoice){
                        case 1:
                            DeviceManager.listDevices();
                            break;
                        case 2:
                            // Add Device Logic
                            DeviceManager.listAvailableDeviceOptions();
                            System.out.print("Device Name: ");
                            String deviceName = scanner.nextLine();
                            System.out.print("Device Type: ");
                            String deviceType = scanner.nextLine();
                            DeviceManager.addDevices(deviceName, deviceType);
                            SmartHomeLogger.info("Main", "New device added: " + deviceName + " (" + deviceType + ")");
                            break;
                        case 3:
                            // Get Device By ID Logic
                            System.out.print("Device ID: ");
                            try {
                                if (!scanner.hasNextInt()) {
                                    System.err.println("ERROR: Invalid Device ID. Must be a number.");
                                    scanner.next();
                                    break;
                                }
                                deviceID = scanner.nextInt();
                                scanner.nextLine();
                                DeviceManager.getDevice(deviceID);
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for Device ID.");
                                scanner.nextLine();
                            }
                            break;
                        case 4:
                            // Get Device Status Logic
                            System.out.print("Device ID to get status: ");
                            try {
                                if (!scanner.hasNextInt()) {
                                    System.err.println("ERROR: Invalid Device ID. Must be a number.");
                                    scanner.next();
                                    break;
                                }
                                deviceID = scanner.nextInt(); 
                                scanner.nextLine();
                                DeviceManager.getDeviceStatus(deviceID);
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for Device ID.");
                                scanner.nextLine();
                            }
                            break;
                        case 5:
                            // Add Automated Trigger Logic
                            System.out.println("--- Add Automated Trigger ---");
                            
                            System.out.print("Device ID to MONITOR (e.g: Thermostat ID): ");
                            int monitorId = -1;
                            try {
                                if (!scanner.hasNextInt()) {
                                    System.err.println("ERROR: Invalid Device ID.");
                                    scanner.next();
                                    break;
                                }
                                monitorId = scanner.nextInt();
                                scanner.nextLine(); 
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for Monitor ID.");
                                scanner.nextLine();
                                break;
                            }
                            
                            System.out.print("Condition Type (e.g: temperature): ");
                            String type = scanner.nextLine();
                            
                            System.out.print("Operator (>, <, ==): ");
                            String op = scanner.nextLine();
                            
                            System.out.print("Target Value (e.g: 75.0): ");
                            double value = -1.0;
                            try {
                                if (!scanner.hasNextDouble()) {
                                    System.err.println("ERROR: Invalid value. Must be a number (e.g., 75.0).");
                                    scanner.next();
                                    break;
                                }
                                value = scanner.nextDouble();
                                scanner.nextLine(); 
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for Target Value.");
                                scanner.nextLine();
                                break;
                            }

                            System.out.print("Action (e.g., turnOff(1) or turnOn(2)): ");
                            String action = scanner.nextLine();

                            // Delegate trigger creation to the Trigger model/manager.
                            Trigger.addTrigger(monitorId, type, op, value, action);
                            System.out.println("Added new trigger for Device " + monitorId);
                            SmartHomeLogger.info("Main", "New trigger added for Device " + monitorId + ". Condition: " + type + " " + op + " " + value + " -> Action: " + action);
                            break;
                        case 6: 
                            // Update Sensor Reading Logic
                            System.out.println("--- Update Sensor Reading ---");
                            
                            System.out.print("Enter ID of sensor device to update: ");
                            int sensorId = -1;
                            try {
                                if (!scanner.hasNextInt()) {
                                    System.err.println("ERROR: Invalid Sensor ID.");
                                    scanner.next();
                                    break;
                                }
                                sensorId = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for Sensor ID.");
                                scanner.nextLine();
                                break;
                            }
                            
                            System.out.print("Enter state type (e.g., temperature, humidity): ");
                            String stateType = scanner.nextLine();
                            
                            System.out.print("Enter new value: ");
                            double newValue = -1.0;
                            try {
                                if (!scanner.hasNextDouble()) {
                                    System.err.println("ERROR: Invalid value. Must be a number.");
                                    scanner.next();
                                    break;
                                }
                                newValue = scanner.nextDouble();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for New Value.");
                                scanner.nextLine();
                                break;
                            }
                            
                            // Update the sensor value and automatically check for triggered actions.
                            DeviceManager.setDeviceSensorValue(sensorId, stateType, newValue);
                            System.out.println("Updated Sensor " + sensorId + " reading to " + newValue);
                            SmartHomeLogger.info("Main", "Sensor update: Device " + sensorId + " set to " + stateType + "=" + newValue + ". Triggers are now being checked.");
                            break;

                        case 7:
                            // Add Scheduled Task Logic
                            System.out.println("--- Add Scheduled Task ---");
                            
                            System.out.print("Enter target Device ID: ");
                            int targetId = -1;
                            try {
                                if (!scanner.hasNextInt()) {
                                    System.err.println("ERROR: Invalid Device ID.");
                                    scanner.next();
                                    break;
                                }
                                targetId = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.err.println("ERROR: Invalid input for Target ID.");
                                scanner.nextLine();
                                break;
                            }
                            
                            System.out.print("Enter time (HH:mm, e.g., 08:30): ");
                            String time = scanner.nextLine();
                            
                            System.out.print("Enter command (TurnOn or TurnOff): ");
                            String command = scanner.nextLine();
                            
                            // Add the task to the Scheduler.
                            Scheduler.addSchedule(targetId, time, command);
                            System.out.println("Added scheduled task for Device " + targetId + " at " + time);
                            SmartHomeLogger.info("Main", "New scheduled task added for Device " + targetId + " at " + time + " to " + command);
                            break;
                            
                        case 8:
                            // List all active scheduled tasks.
                            Scheduler.listSchedules();
                            break;
                        default:
                            System.out.println("Invalid Device Module option.");
                            break;
                    }
                    break;
                default:
                    System.out.println("Invalid module choice. Please select 1, 2, 3, or 0 to exit.");
                    break;
            }
        } while(moduleChoice != EXIT_CHOICE);
        
        scanner.close();
    }
}