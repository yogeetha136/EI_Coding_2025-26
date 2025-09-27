package smarthome.manager;

import java.util.List;
import java.util.ArrayList;
import smarthome.model.Room;

public class RoomManager {
        private static int roomID = 1;
        static List<Room> rooms = new ArrayList<>();
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
