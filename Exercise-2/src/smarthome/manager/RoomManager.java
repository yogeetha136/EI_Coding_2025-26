package smarthome.manager;

import java.util.List;
import java.util.ArrayList;

import smarthome.model.Device;
import smarthome.model.Room;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    // Example Enum for Room Names
public enum RoomName {
    LIVING_ROOM,
    KITCHEN,
    BEDROOM,
    HALLWAY,
    BATHROOM
}
// Example Enum for Room Types
public enum RoomType {
    COMMUNAL_AREA,
    PRIVATE_AREA,
    SERVICE_AREA,
    STORAGE
}
        private static int roomID = 1;
        static List<Room> rooms = new ArrayList<>();
        private static Map<Integer, List<Integer>> roomToUser = new HashMap<>();

        private static Map<Integer, List<Integer>> deviceToRoom = new HashMap<>();
        public static void addDeviceToRoom(int roomID, List<Integer> deviceID){
            deviceToRoom.put(roomID,new ArrayList<>());
            deviceToRoom.get(roomID).addAll(deviceID);
        }

        public static void getDeviceIdsInRoom() {
           deviceToRoom.forEach((key, valueList) -> 
            System.out.println("Key: " + key + ", Values: " + valueList)
        );
    }
         public static void addUserToRoom(int roomID, List<Integer> userIDList){
        Room room = findRoomById(roomID);
        if (room == null) {
            System.out.println("ERROR: Room ID " + roomID + " not found.");
            return;
        }

        // Initialize the list if the room is new to the map
        roomToUser.putIfAbsent(roomID, new ArrayList<>());
        
        // Add new user IDs if they don't already exist in the list
        for (int userID : userIDList) {
            if (UserManager.findUserById(userID) == null) {
                 System.out.println("Warning: User ID " + userID + " not found and was skipped.");
                 continue;
            }
            if (!roomToUser.get(roomID).contains(userID)) {
                roomToUser.get(roomID).add(userID);
                System.out.println("User " + userID + " added to Room " + roomID + ".");
            } else {
                System.out.println("User " + userID + " already has access to Room " + roomID + ".");
            }
        }
    }

        public static void getUserIdsInRoom() {
           roomToUser.forEach((key, valueList) -> 
            System.out.println("Key: " + key + ", Values: " + valueList)
        );
    }
        public static void listRooms(){
            if(rooms.isEmpty()){
                System.out.println("room is not yet added");
            }
            else{
                for(int i=0; i<rooms.size(); i++){
                    System.out.println(rooms.get(i).getRoomID());
                    System.out.println(rooms.get(i).getRoomName());
                    System.out.println(rooms.get(i).getRoomType());
                }
            }
        }
        public static void addRooms(String roomName, String roomType){
        if (!isRoomNameValid(roomName)) {
            System.out.println("ERROR: Invalid Room Name. Please use one of the available options.");
            listAvailableRoomOptions(); 
            return;
        }

        if (!isRoomTypeValid(roomType)) {
            System.out.println("ERROR: Invalid Room Type. Please use one of the available options.");
            listAvailableRoomOptions();
            return;
        }
        rooms.add(new Room(roomID, roomName, roomType));
        System.out.println("room added successfully");
        roomID++;
        }
public static void getUser(int roomID){ 

    boolean found = false;
    for(int i = 0; i < rooms.size(); i++){
        if(rooms.get(i).getRoomID() == roomID){
            System.out.println("Room found:");
            System.out.println("ID: " + rooms.get(i).getRoomID());
            System.out.println("Name: " + rooms.get(i).getRoomName());
            System.out.println("Type: " + rooms.get(i).getRoomType());
            found = true;
            break; 
        }
    }
    if(!found){
        System.out.println("The room with this room ID not exists.");
    }
}

    public static Room findRoomById(int id) {
        for (Room room : rooms) {
            if (room.getRoomID() == id) {
                return room;
            }
        }
        return null;
    }
 // Updated: To ensure the devices being controlled belong to the room.
    public static void controlSpecificDevicesInRoom(int roomID, List<Integer> specificDeviceIds, String action, int userID) {
        if (!isUserAuthorizedForRoom(roomID, userID)) {
             System.out.println("ERROR: User " + userID + " does not have permission to control devices in Room " + roomID + ".");
             return;
        }
        
        // ... rest of the method logic remains the same ...
        if (!action.equalsIgnoreCase("ON") && !action.equalsIgnoreCase("OFF")) {
             System.out.println("Invalid action. Use 'ON' or 'OFF'.");
             return;
        }

        List<Integer> roomDevices = deviceToRoom.get(roomID);

        if (roomDevices == null || roomDevices.isEmpty()) {
             System.out.println("Room ID " + roomID + " has no devices assigned.");
             return;
        }

        System.out.println("\nExecuting action: " + action + " on specific devices by User " + userID + " in Room " + roomID + "...");

        for (int deviceID : specificDeviceIds) {
             if (!roomDevices.contains(deviceID)) {
                 System.out.println("Warning: Device ID " + deviceID + " is not in Room " + roomID + " and was skipped.");
                 continue;
             }
             
             Device device = DeviceManager.findDeviceById(deviceID);
             if (device != null) {
                 if (action.equalsIgnoreCase("ON")) {
                     device.turnOn();
                 } else { 
                     device.turnOff();
                 }
             } else {
                 System.out.println("Warning: Device ID " + deviceID + " not found in DeviceManager.");
             }
        }
        
        System.out.println("\nFinal Status of all devices in Room " + roomID + " (including controlled devices):");
        listRoomDeviceStatuses(roomID);
    }

    public static void listRoomDeviceStatuses(int roomID) {
        List<Integer> deviceIds = deviceToRoom.get(roomID);

        if (deviceIds == null || deviceIds.isEmpty()) {
            System.out.println("Room ID " + roomID + " has no devices assigned.");
            return;
        }

        System.out.println("\n--- Current Device Statuses in Room " + roomID + " ---");
        for (int deviceID : deviceIds) {
            DeviceManager.getDeviceStatus(deviceID); 
        }
    }


 // Updated: Only shows rooms and devices the user has permission for.
    public static void listUserRoomsAndDevices(int currentUserID) {
        System.out.println("\n--- Rooms and Devices Accessible by User " + currentUserID + " ---");
        
        boolean hasAccess = false;

        if (rooms.isEmpty()) {
             System.out.println("No rooms have been added to the system yet.");
             return;
        }

        for (Room room : rooms) {
            int roomID = room.getRoomID();
            
            // SECURITY CHECK: Only display rooms the current user is authorized for
            if (isUserAuthorizedForRoom(roomID, currentUserID)) {
                System.out.println("- Room ID " + roomID + ": " + room.getRoomName() + " (" + room.getRoomType() + ")");
                hasAccess = true;
                
                List<Integer> deviceIds = deviceToRoom.get(roomID);
                if (deviceIds != null && !deviceIds.isEmpty()) {
                    System.out.print("Devices: ");
                    for (int i = 0; i < deviceIds.size(); i++) {
                        System.out.print(deviceIds.get(i) + (i < deviceIds.size() - 1 ? ", " : ""));
                    }
                    System.out.println();
                } else {
                    System.out.println("Devices: (None)");
                }
            }
        }
        
        if (!hasAccess) {
            System.out.println("You have no access permissions to any rooms yet.");
        }
    }
    public static void listAvailableRoomOptions() {
    System.out.println("\n--- Available Room Names ---");
    for (RoomName name : RoomName.values()) {
        System.out.println("- " + name.name());
    }

    System.out.println("\n--- Available Room Types ---");
    for (RoomType type : RoomType.values()) {
        System.out.println("- " + type.name());
    }
    System.out.println(); 
}
    private static boolean isRoomNameValid(String roomName) {
        for (RoomName name : RoomName.values()) {
            if (name.name().equalsIgnoreCase(roomName)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isRoomTypeValid(String roomType) {
        for (RoomType type : RoomType.values()) {
            if (type.name().equalsIgnoreCase(roomType)) {
                return true;
            }
        }
        return false;
    }

    
    // Updated: Only shows rooms the user has permission for.
    public static void getUserIdsInRoom(int currentUserID) {
        System.out.println("\n--- Rooms and Their Permitted User IDs (Filtered by User " + currentUserID + ") ---");

        boolean userHasRoomAccess = false;
        
        // Loop through all rooms to find those the current user has access to
        for (Map.Entry<Integer, List<Integer>> entry : roomToUser.entrySet()) {
            int roomID = entry.getKey();
            List<Integer> userIDs = entry.getValue();

            if (userIDs.contains(currentUserID)) {
                System.out.println("Room ID: " + roomID + ", User IDs: " + userIDs);
                userHasRoomAccess = true;
            }
        }
        if (!userHasRoomAccess) {
             System.out.println("User " + currentUserID + " has not been granted permission to any rooms yet.");
        }
    }
    
    // New Method: Checks if a specific user has permission to access a specific room
    public static boolean isUserAuthorizedForRoom(int roomID, int userID) {
        List<Integer> userIDs = roomToUser.get(roomID);
        return userIDs != null && userIDs.contains(userID);
    }
    
   
    
   
    
}
