package smarthome;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import smarthome.manager.UserManager;
import smarthome.model.Room;
import smarthome.manager.RoomManager;
import smarthome.manager.DeviceManager;

class Main{
private final static int EXIT_CHOICE = 0;
    public static void main(String args[]){
        int moduleChoice;
        do{
        System.out.println("1.User Module");
        System.out.println("2.Room Module");
        System.out.println("3.Device Module");
        Scanner scanner = new Scanner(System.in);
        moduleChoice = scanner.nextInt();
        switch(moduleChoice){
            case 1:
                System.out.println("1. List the users");
                System.out.println("2. Add users");
                System.out.println("3. Get user by ID");
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
                }
                break;
            case 2:
                System.out.println("1. List the rooms");
                System.out.println("2. Add rooms");
                System.out.println("3. Get room by ID");
                System.out.println("4.Give User permissions to rooms");
                System.out.println("5.List the room with user permission using room ID");
                int roomChoice = scanner.nextInt();
                switch(roomChoice){
                    case 1:
                        RoomManager.listRooms();
                        break;
                    case 2:
                        System.out.println("enter room name:");
                        String roomName = scanner.next();
                        System.out.println("enter room type:");
                        String roomType = scanner.next();
                        RoomManager.addRooms(roomName, roomType);
                        break;
                    case 3:
                        System.out.println("enter the room ID:");
                        int roomID = scanner.nextInt();
                        RoomManager.getUser(roomID);
                        break;
                    case 4:
                        System.out.println("enter the room ID:");
                        int roomNo = scanner.nextInt();
                        System.out.println("count of users going to add for the particualr room");
                        int count = scanner.nextInt();
                        System.out.println("enter the userIDS to give permission to the room"+roomNo+":");
                        List<Integer> userNo = new ArrayList<>();
                        for(int i=0; i<count; i++){
                            int user = scanner.nextInt();
                            userNo.add(user);
                            RoomManager.addUserToRoom(roomNo, userNo);
                        }
                        break;
                    case 5:
                        RoomManager.getUserIdsInRoom();
                        break;
                }
                break;
        
            case 3:
                System.out.println("1. List the devices");
                System.out.println("2. Add devices");
                System.out.println("3. Get device by ID");
                int deviceChoice = scanner.nextInt();
                switch(deviceChoice){
                    case 1:
                        DeviceManager.listDevices();
                        break;
                    case 2:
                        System.out.println("enter device name:");
                        String deviceName = scanner.next();
                        System.out.println("enter device type:");
                        String deviceType = scanner.next();
                        DeviceManager.addDevices(deviceName, deviceType);
                        break;
                    case 3:
                        System.out.println("enter the device ID:");
                        int deviceID = scanner.nextInt();
                        DeviceManager.getDevice(deviceID);
                        break;
                }
        }
    }while(moduleChoice != EXIT_CHOICE);
    }
}