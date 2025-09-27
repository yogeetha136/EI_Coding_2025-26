package smarthome.manager;

import java.util.List;
import java.util.ArrayList;
import smarthome.model.Room;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {
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
        public static void addUserToRoom(int roomID, List<Integer> userID){
            roomToUser.put(roomID,new ArrayList<>());
            roomToUser.get(roomID).addAll(userID);
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
        rooms.add(new Room(roomID, roomName, roomType));
        System.out.println("room added successfully");
        roomID++;
        }
        public static void getUser(int roomID){
        //To Do: check using stream instead of looping
        for(int i=0; i<rooms.size(); i++){
            if(rooms.get(i).getRoomID() == roomID){
                System.out.println(rooms.get(i).getRoomID());
                System.out.println(rooms.get(i).getRoomName());
                System.out.println(rooms.get(i).getRoomType());                
            }
            else{
                System.out.println("The room with this room ID not exists.");
                break;
            }
        }

    }

    
}
