import java.util.*;

abstract class Device implements DeviceInterface{
    private int deviceID;
    private String deviceName;
    private String deviceType;

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
    abstract void createDevice();

    public boolean getPing(){
        return true;
    }

    
}
