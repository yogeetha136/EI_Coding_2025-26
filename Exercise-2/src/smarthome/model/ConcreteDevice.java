package model;
import util.SmartHomeLogger;


public class ConcreteDevice extends Device{
        public ConcreteDevice(int deviceID, String deviceName, String deviceType) {
        super(deviceID, deviceName, deviceType);
    }
    @Override
    public void createDevice() {
        String msg = "Concrete Device: " + getDeviceName() + " (" + getDeviceType() + ") is created.";
        System.out.println(msg);
        SmartHomeLogger.info("ConcreteDevice", msg);    }
    
}
