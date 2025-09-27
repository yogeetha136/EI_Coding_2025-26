package smarthome.model;
import smarthome.model.Device;

public class ConcreteDevice extends Device{
        public ConcreteDevice(int deviceID, String deviceName, String deviceType) {
        super(deviceID, deviceName, deviceType);
    }
    @Override
    public void createDevice() {
        System.out.println("Concrete Device: " + getDeviceName() + " (" + getDeviceType() + ") is created.");
    }
    
}
