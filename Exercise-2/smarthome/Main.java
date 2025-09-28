package smarthome;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import smarthome.manager.UserManager;
import smarthome.manager.RoomManager;
import smarthome.manager.DeviceManager;
import smarthome.model.User;

class Main{
private final static int EXIT_CHOICE = 0;
private static User currentUser = null;
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int moduleChoice;
            
        do{
        System.out.println("1.User Module");
        System.out.println("2.Room Module");
        System.out.println("3.Device Module");
        moduleChoice = scanner.nextInt();
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
                        System.out.println("enter the userIDS to give permission to the room"+roomID+":");
                        List<Integer> userIDList = new ArrayList<>();
                        for(int i=0; i<userCount; i++){
                            userID = scanner.nextInt();
                            userIDList.add(userID);
                            RoomManager.addUserToRoom(roomID, userIDList);
                        }
                        break;
                    case 5:
                        RoomManager.getUserIdsInRoom();
                        break;
                    case 6:
                        System.out.println("enter the room ID:");
                        int RoomNo = scanner.nextInt();
                        System.out.println("count of devices going to add for the particualr room");
                        int deviceCount = scanner.nextInt();
                        System.out.println("enter the deviceIDS to add to the room"+RoomNo+":");
                        List<Integer> deviceNo = new ArrayList<>();
                        for(int i=0; i<deviceCount; i++){
                            int device = scanner.nextInt();
                            deviceNo.add(device);
                            RoomManager.addDeviceToRoom(RoomNo, deviceNo);
                        }
                        break;
                    case 7:
                        RoomManager.getDeviceIdsInRoom();
                        break;
                    case 8:
    if (currentUser == null) {
        System.out.println("ERROR: You must be logged in to control devices.");
        break;
    }
    
    int controllingUserID = currentUser.getUserID(); 

    RoomManager.listUserRoomsAndDevices(controllingUserID); 

    System.out.print("Enter the Room ID you wish to control: ");
    if (!scanner.hasNextInt()) {
        System.out.println("Invalid input for Room ID.");
        scanner.nextLine(); 
        break;
    }
    int roomIdToControl = scanner.nextInt();
    scanner.nextLine(); 

    if(RoomManager.findRoomById(roomIdToControl) == null) {
        System.out.println("Room ID " + roomIdToControl + " does not exist.");
        break;
    }

    
    System.out.print("Enter the Device ID you wish to turn ON/OFF: ");
    if (!scanner.hasNextInt()) {
        System.out.println("Invalid input for Device ID.");
        scanner.nextLine(); 
        break;
    }
    int deviceIdToControl = scanner.nextInt();
    scanner.nextLine(); 

    if (DeviceManager.findDeviceById(deviceIdToControl) == null) {
        System.out.println("ERROR: Device ID " + deviceIdToControl + " does not exist in the system.");
        break;
    }
    RoomManager.listRoomDeviceStatuses(roomIdToControl);

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
                }
                break;
            
        }
    }while(moduleChoice != EXIT_CHOICE);
    }
}