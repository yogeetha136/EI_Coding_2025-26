package manager;

import java.util.List;
import java.util.ArrayList;

import model.Device;
import model.DeviceProxy;
import model.Room;
import java.util.HashMap;
import java.util.Map;



public class RoomManager {
    //Room Names
    public enum RoomName {
        LIVING_ROOM,
        KITCHEN,
        BEDROOM,
        HALLWAY,
        BATHROOM
    }
    //Room Types
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
    
    //Device Assignment Methods

    public static void addDeviceToRoom(int roomID, List<Integer> deviceID) {
        if (findRoomById(roomID) == null) {
            System.err.println("ERROR: Room ID " + roomID + " not found. Cannot assign devices.");
            return;
        }
        
        try {
            deviceToRoom.putIfAbsent(roomID, new ArrayList<>());
            deviceToRoom.get(roomID).addAll(deviceID);
            System.out.println("Devices added to Room " + roomID + ".");
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to add devices to room map. " + e.getMessage());
        }
    }

    public static void getDeviceIdsInRoom(int roomID_list) {
        try {
            List<Integer> deviceIds = RoomManager.deviceToRoom.get(roomID_list);

            if (deviceIds != null && !deviceIds.isEmpty()) {
                System.out.println("Devices in Room " + roomID_list + ": " + deviceIds);
            } else {
                if (findRoomById(roomID_list) == null) {
                    System.out.println("Room ID " + roomID_list + " does not exist.");
                } else {
                    System.out.println("No devices found in Room ID " + roomID_list + ".");
                }
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to retrieve device IDs. " + e.getMessage());
        }
    }
    
    // User Permission Methods

    public static void addUserToRoom(int roomID, List<Integer> userIDList) {
        Room room = findRoomById(roomID);
        if (room == null) {
            System.err.println("ERROR: Room ID " + roomID + " not found.");
            return;
        }

        try {
            // Initialize the list if the room is new to the map
            roomToUser.putIfAbsent(roomID, new ArrayList<>());
            
            for (int userID : userIDList) {
                if (manager.UserManager.findUserById(userID) == null) { 
                     System.err.println("Warning: User ID " + userID + " not found in system and was skipped.");
                     continue;
                }
                
                if (!roomToUser.get(roomID).contains(userID)) {
                    roomToUser.get(roomID).add(userID);
                    System.out.println("User " + userID + " added to Room " + roomID + ".");
                } else {
                    System.out.println("User " + userID + " already has access to Room " + roomID + ".");
                }
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to assign users to room. " + e.getMessage());
        }
    }

    public static void getUserIdsInRoom() {
        try {
            if (roomToUser.isEmpty()) {
                System.out.println("No user permissions assigned yet.");
                return;
            }
            roomToUser.forEach((key, valueList) -> 
                System.out.println("Key: " + key + ", Values: " + valueList)
            );
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to list room user permissions. " + e.getMessage());
        }
    }
    
    //Room Listing and Creation Methods

    public static void listRooms() {
        try {
            if (rooms.isEmpty()) {
                System.out.println("room is not yet added");
            } else {
                for (int i = 0; i < rooms.size(); i++) {
                    System.out.println("--- Room " + (i + 1) + " ---"); 
                    System.out.println("ID: " + rooms.get(i).getRoomID());
                    System.out.println("Name: " + rooms.get(i).getRoomName());
                    System.out.println("Type: " + rooms.get(i).getRoomType());
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("CRITICAL ERROR: Error accessing room list. " + e.getMessage());
        }
    }

    public static void addRooms(String roomName, String roomType) {
        if (!isRoomNameValid(roomName)) {
            System.err.println("ERROR: Invalid Room Name. Please use one of the available options.");
            listAvailableRoomOptions();
            return;
        }

        if (!isRoomTypeValid(roomType)) {
            System.err.println("ERROR: Invalid Room Type. Please use one of the available options.");
            listAvailableRoomOptions();
            return;
        }
        
        try {
            rooms.add(new Room(roomID, roomName.toUpperCase(), roomType.toUpperCase()));
            System.out.println("room added successfully with ID: " + roomID);
            roomID++;
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to create or add room. " + e.getMessage());
        }
    }

    public static void getRoom(int roomID) {
        boolean found = false;
        try {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoomID() == roomID) {
                    System.out.println("Room found:");
                    System.out.println("ID: " + rooms.get(i).getRoomID());
                    System.out.println("Name: " + rooms.get(i).getRoomName());
                    System.out.println("Type: " + rooms.get(i).getRoomType());
                    found = true;
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("CRITICAL ERROR: Error accessing room list while searching. " + e.getMessage());
        }

        if (!found) {
            System.out.println("The room with this room ID not exists.");
        }
    }

    public static Room findRoomById(int id) {
        try {
            for (Room room : rooms) {
                if (room.getRoomID() == id) {
                    return room;
                }
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Error searching rooms. " + e.getMessage());
        }
        return null;
    }

public static void controlSpecificDevicesInRoom(int roomID, List<Integer> specificDeviceIds, String action, int userID) {

    if (findRoomById(roomID) == null) {
        System.err.println("ERROR: Control failed. Room ID " + roomID + " not found.");
        return;
    }

    if (!action.equalsIgnoreCase("ON") && !action.equalsIgnoreCase("OFF")) {
        System.err.println("ERROR: Invalid action. Use 'ON' or 'OFF'.");
        return;
    }

    System.out.println("\nExecuting action: " + action + " on specific devices by User " + userID + " in Room " + roomID + "...");

    try {
        for (int deviceID : specificDeviceIds) {
            // device in room check

            Device realDevice = manager.DeviceManager.findDeviceById(deviceID); 
            
            if (realDevice != null) {
                
                // CORE PROXY STEP: Wrap the real device in a proxy!
                DeviceProxy proxy = new DeviceProxy(realDevice, roomID, userID);

                if (action.equalsIgnoreCase("ON")) {
                    proxy.turnOn(); // The proxy handles the auth check before calling realDevice.turnOn()
                } else {
                    proxy.turnOff(); // The proxy handles the auth check before calling realDevice.turnOff()
                }
            } else {
                System.err.println("Warning: Device ID " + deviceID + " not found in DeviceManager. Skipping.");
            }
        }
    } catch (Exception e) {
        System.err.println("CRITICAL ERROR: Error during device control execution. " + e.getMessage());
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
        
        try {
            for (int deviceID : deviceIds) {
                manager.DeviceManager.getDeviceStatus(deviceID); 
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Error listing device statuses. " + e.getMessage());
        }
    }

    public static void listUserRoomsAndDevices(int currentUserID) {
        System.out.println("\n--- Rooms and Devices Accessible by User " + currentUserID + " ---");

        boolean hasAccess = false;

        if (rooms.isEmpty()) {
             System.out.println("No rooms have been added to the system yet.");
             return;
        }
        
        try {
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
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Error listing user rooms and devices. " + e.getMessage());
        }

        if (!hasAccess) {
            System.out.println("You have no access permissions to any rooms yet.");
        }
    }
    
    //Helper Methods

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
    
    // Validation methods

    private static boolean isRoomNameValid(String roomName) {
        if (roomName == null) return false;
        try {
            RoomName.valueOf(roomName.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean isRoomTypeValid(String roomType) {
        if (roomType == null) return false;
        try {
            RoomType.valueOf(roomType.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void listUserIdsInRooms(int currentUserID) { 
         System.out.println("\n--- Rooms and Their Permitted User IDs (Filtered by User " + currentUserID + ") ---");

         boolean userHasRoomAccess = false;

         try {
             for (Map.Entry<Integer, List<Integer>> entry : roomToUser.entrySet()) {
                 int roomID = entry.getKey();
                 List<Integer> userIDs = entry.getValue();

                 if (userIDs.contains(currentUserID)) {
                     System.out.println("Room ID: " + roomID + ", User IDs: " + userIDs);
                     userHasRoomAccess = true;
                 }
             }
         } catch (Exception e) {
             System.err.println("CRITICAL ERROR: Error listing user permissions. " + e.getMessage());
         }

         if (!userHasRoomAccess) {
              System.out.println("User " + currentUserID + " has not been granted permission to any rooms yet.");
         }
    }

    public static boolean isUserAuthorizedForRoom(int roomID, int userID) {
        try {
            List<Integer> userIDs = roomToUser.get(roomID);
            return userIDs != null && userIDs.contains(userID);
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Authorization check failed. " + e.getMessage());
            return false;
        }
    }
}