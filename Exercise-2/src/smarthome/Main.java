
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import manager.UserManager;
import manager.RoomManager;
import manager.DeviceManager;
import model.User;
import model.Trigger;
import manager.Scheduler;

class Main{
private final static int EXIT_CHOICE = 0;
private static User currentUser = null;
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int moduleChoice;
        Scheduler.startScheduler();

        do{
 
        System.out.println("1.User Module");
        System.out.println("2.Room Module");
        System.out.println("3.Device Module");
        moduleChoice = scanner.nextInt();
                       if (moduleChoice == EXIT_CHOICE) {
                System.out.println("Shutting down scheduler and exiting...");
                Scheduler.stopScheduler();
                break;                     
            }
        switch(moduleChoice){
            case 1:
                System.out.println("1. List the users");
                System.out.println("2. Add users");
                System.out.println("3. Get user by ID");
                System.out.println("4. User login");
                int userChoice = scanner.nextInt();
                switch(userChoice){
                    case 1:
                        UserManager.listUsers(); 
                        break;
                    case 2:
                        System.out.println("enter user name:");
                        String userName = scanner.next();
                        System.out.println("enter user email:");
                        String emailID = scanner.next();
                        System.out.println("enter user password:");
                        String passWord = scanner.next();
                        UserManager.addUser(userName, emailID, passWord);
                        break;
                    case 3:
                        System.out.println("enter the user ID:");
                        int userID = scanner.nextInt();
                        UserManager.getUser(userID);
                        break;
                    case 4:
                        System.out.print("Enter your email: ");
                        String email = scanner.next();
                        System.out.print("Enter your password: ");
                        String password = scanner.next();
                        currentUser = UserManager.login(email, password);
                        break;
                }
                break;
            case 2:
                System.out.println("1. List the rooms");
                System.out.println("2. Add rooms");
                System.out.println("3. Get room by ID");
                System.out.println("4.Give User permissions to rooms");
                System.out.println("5.List the room with user permission using room ID");
                System.out.println("6.Give devices access to rooms");
                System.out.println("7.List the room with devices using room ID");
                System.out.println("8.Turn ON/OFF devices in a room");   
                int roomChoice = scanner.nextInt();
                String roomType;
                String roomName;
                int roomID;
                int userID;
                int controllingUserID = (currentUser != null) ? currentUser.getUserID() : -1;
                if (roomChoice >= 4 && currentUser == null) {
                    System.out.println("ERROR: You must be logged in to perform this action.");
                    break;
                }
                switch(roomChoice){
                    case 1:
                        RoomManager.listRooms();
                        break;
                    case 2:
                        RoomManager.listAvailableRoomOptions();
                        System.out.println("enter room name:");
                        roomName = scanner.next();
                        System.out.println("enter room type:");
                        roomType = scanner.next();
                        RoomManager.addRooms(roomName, roomType);
                        break;
                    case 3:
                        System.out.println("enter the room ID:");
                        roomID = scanner.nextInt();
                        RoomManager.getUser(roomID);
                        break;
                    case 4:
                        System.out.println("enter the room ID:");
                        roomID = scanner.nextInt();
                        System.out.println("count of users going to add for the particualr room");
                        int userCount = scanner.nextInt();
                        List<Integer> userIDList = new ArrayList<>();
                        System.out.println("enter the userIDS to give permission to the room"+roomID+":");
                        for(int i=0; i<userCount; i++){
                            userID = scanner.nextInt();
                            userIDList.add(userID);
                        }
                        RoomManager.addUserToRoom(roomID, userIDList); 
                        break;
                    
                    case 5:
                        RoomManager.getUserIdsInRoom(controllingUserID);
                        break;
                        
                    case 6:
                        System.out.println("enter the room ID:");
                        int RoomNo = scanner.nextInt();
                        
                        if (!RoomManager.isUserAuthorizedForRoom(RoomNo, controllingUserID)) {
                            System.out.println("ERROR: You do not have permission to assign devices to Room " + RoomNo + ".");
                            break;
                        }
                        
                        System.out.println("count of devices going to add for the particualr room");
                        int deviceCount = scanner.nextInt();
                        System.out.println("enter the deviceIDS to add to the room"+RoomNo+":");
                        List<Integer> deviceNo = new ArrayList<>();
                        for(int i=0; i<deviceCount; i++){
                            int device = scanner.nextInt();
                            deviceNo.add(device);
                        }
                        RoomManager.addDeviceToRoom(RoomNo, deviceNo);
                        break;
                        
                    case 7:
                        System.out.print("Enter the Room ID to list its devices:");
                        int roomID_list = scanner.nextInt();
                        
                        if (!RoomManager.isUserAuthorizedForRoom(roomID_list, controllingUserID)) {
                            System.out.println("ERROR: You do not have permission to view devices in Room " + roomID_list + ".");
                            break;
                        }
                        RoomManager.getDeviceIdsInRoom(); 
                        break;
                        
                    case 8:
                        if (currentUser == null) { 
                            System.out.println("ERROR: You must be logged in to control devices.");
                            break;
                        }
                        
                        RoomManager.listUserRoomsAndDevices(controllingUserID); 

                        System.out.print("Enter the Room ID you wish to control: ");
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input for Room ID.");
                            scanner.nextLine(); 
                            break;
                        }
                        int roomIdToControl = scanner.nextInt();
                        scanner.nextLine(); 
                        RoomManager.listRoomDeviceStatuses(roomIdToControl);

                        if(RoomManager.findRoomById(roomIdToControl) == null) {
                            System.out.println("Room ID " + roomIdToControl + " does not exist.");
                            break;
                        }
                        
                        if (!RoomManager.isUserAuthorizedForRoom(roomIdToControl, controllingUserID)) {
                            System.out.println("ACCESS DENIED: You do not have permission to control devices in Room " + roomIdToControl + ".");
                            break;
                        }
                        

                        System.out.print("Enter the Device ID you wish to turn ON/OFF: ");
                        int deviceIdToControl = scanner.nextInt();
                        scanner.nextLine(); 

                        if (DeviceManager.findDeviceById(deviceIdToControl) == null) {
                            System.out.println("ERROR: Device ID " + deviceIdToControl + " does not exist in the system.");
                            break;
                        }

                        System.out.print("Enter action to perform on Device " + deviceIdToControl + " (ON/OFF): ");
                        String action = scanner.next();
                        scanner.nextLine(); 

                        List<Integer> singleDeviceList = new ArrayList<>();
                        singleDeviceList.add(deviceIdToControl);

                        RoomManager.controlSpecificDevicesInRoom(roomIdToControl, singleDeviceList, action, controllingUserID);
                        break;

                }
                break;
        
            case 3:
                System.out.println("1. List the devices");
                System.out.println("2. Add devices");
                System.out.println("3. Get device by ID");
                System.out.println("4. Get device status");
                System.out.println("5. Add Automated Trigger");
                System.out.println("6. Update Sensor Reading & Check Triggers");
                System.out.println("7. Add Schedule Task");
                System.out.println("8. List Scheduled Tasks");

                int deviceID;
             
                int deviceChoice = scanner.nextInt();
                switch(deviceChoice){
                    case 1:
                        DeviceManager.listDevices();
                        break;
                    case 2:
                    DeviceManager.listAvailableDeviceOptions();
                        System.out.println("enter device name:");
                        String deviceName = scanner.next();
                        System.out.println("enter device type:");
                        String deviceType = scanner.next();
                        DeviceManager.addDevices(deviceName, deviceType);
                        break;
                    case 3:
                        System.out.println("enter the device ID:");
                        deviceID = scanner.nextInt();
                        DeviceManager.getDevice(deviceID);
                        break;
                    case 4:
                        System.out.println("Enter the Device ID to get status:");
                        deviceID = scanner.nextInt(); 
                        scanner.nextLine();
                        DeviceManager.getDeviceStatus(deviceID);
                        break;
                    case 5:
                        System.out.println("--- Add Automated Trigger ---");
                        System.out.print("Enter ID of device to MONITOR (e.g., Thermostat ID): ");
                        int monitorId = scanner.nextInt();
                        scanner.nextLine(); 
                        
                        System.out.print("Enter condition type (e.g., temperature): ");
                        String type = scanner.next();
                        
                        System.out.print("Enter operator (>, <, ==): ");
                        String op = scanner.next();
                        
                        System.out.print("Enter target value (e.g., 75.0): ");
                        double value = scanner.nextDouble();
                        scanner.nextLine(); 

                        System.out.print("Enter action (e.g., turnOff(1) or turnOn(2)): ");
                        String action = scanner.next();
                        scanner.nextLine();

                        Trigger.addTrigger(monitorId, type, op, value, action);
                        
                        break;
                    case 6: 
                        System.out.println("--- Update Sensor Reading ---");
                        System.out.print("Enter ID of sensor device to update: ");
                        int sensorId = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Enter state type (e.g., temperature, humidity): ");
                        String stateType = scanner.next();
                        
                        System.out.print("Enter new value: ");
                        double newValue = scanner.nextDouble();
                        scanner.nextLine();
                        
                        DeviceManager.setDeviceSensorValue(sensorId, stateType, newValue);
                        break;

                case 7:
                    System.out.println("--- Add Scheduled Task ---");
                    System.out.print("Enter target Device ID: ");
                    int targetId = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Enter time (HH:mm, e.g., 08:30): ");
                    String time = scanner.next();
                    
                    System.out.print("Enter command (TurnOn or TurnOff): ");
                    String command = scanner.next();
                    
                    Scheduler.addSchedule(targetId, time, command);
                    break;
                    
                case 8:
                    Scheduler.listSchedules();
                    break;
                }
                break;
            
        }
    }while(moduleChoice != EXIT_CHOICE);
    }
}