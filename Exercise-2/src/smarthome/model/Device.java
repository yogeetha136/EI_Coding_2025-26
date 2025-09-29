package smarthome.model;
import smarthome.Interface.DeviceInterface;

public abstract class Device implements DeviceInterface{
    private int deviceID;
    private String deviceName;
    private String deviceType;
    private boolean status = false;
    //abstract method
      abstract void createDevice();

        public Device(int deviceID, String deviceName, String deviceType){
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.deviceType = deviceType;

    }


    public void setDeviceID(int deviceID){
        this.deviceID = deviceID;
    }
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }
    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }
    public int getDeviceID(){
        return deviceID;
    }
    public String getDeviceName(){
        return deviceName;
    }
    public String getDeviceType(){
        return deviceType;
    }

    public boolean getPing(){
        return true;
    }
    
        @Override
    public void turnOn() {
        this.status = true;
        System.out.println(deviceName + " is turned ON.");
    }

        @Override
    public void turnOff() {
        this.status = false;
        System.out.println(deviceName + " is turned OFF.");
    }

    @Override 
    public String getStatus() { 
        return "ID: " + deviceID + " | Name: " + deviceName + " | Type: " + deviceType + " | Status: " + (status ? "ON" : "OFF");
    }
}
