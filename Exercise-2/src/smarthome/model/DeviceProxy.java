package model;


import Interface.DeviceInterface;
import manager.RoomManager;

public class DeviceProxy implements DeviceInterface {
    
    private final Device realDevice;
    private final int roomID;
    private final int userID; // Store the user attempting the action

    public DeviceProxy(Device realDevice, int roomID, int userID) {
        this.realDevice = realDevice;
        this.roomID = roomID;
        this.userID = userID;
    }

    // --- Access Control Logic for Mutating Methods (turnOn/turnOff) ---

    private boolean isAuthorized() {
        return RoomManager.isUserAuthorizedForRoom(roomID, userID);
    }
    
    @Override
    public void turnOn() {
        if (isAuthorized()) {
            // Forward the request to the real device
            realDevice.turnOn();
        } else {
            System.err.println("PROXY DENIED: User " + userID + 
                               " is unauthorized to turn ON Device " + realDevice.getDeviceID() + 
                               " in Room " + roomID + ".");
        }
    }

    @Override
    public void turnOff() {
        if (isAuthorized()) {
            // Forward the request to the real device
            realDevice.turnOff();
        } else {
            System.err.println("PROXY DENIED: User " + userID + 
                               " is unauthorized to turn OFF Device " + realDevice.getDeviceID() + 
                               " in Room " + roomID + ".");
        }
    }

    // --- Direct Forwarding Logic for Read-Only Methods ---    
    @Override
    public String getStatus() {
        return realDevice.getStatus();
    }

    @Override
    public boolean getPing() {
        return realDevice.getPing();
    }
}