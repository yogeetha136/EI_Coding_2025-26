package smarthome.model;
public class Room {
    private int roomID;
    private String roomName;
    private String roomType;
    public Room(int roomID, String roomName, String roomType){
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public void setRoomID(int roomID){
        this.roomID = roomID;
    }
    public void setRoomName(String roomName){
        this.roomName = roomName;
    }
    public void setRoomType(String roomType){
        this.roomType = roomType;
    }
    public int getRoomID(){
        return roomID;
    }
    public String getRoomName(){
        return roomName;
    }
    public String getRoomType(){
        return roomType;
    }


}
