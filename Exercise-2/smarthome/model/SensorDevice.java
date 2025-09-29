package smarthome.model;

import java.util.HashMap;
import java.util.Map;

public class SensorDevice extends Device{

    private final Map<String, Double> sensorState = new HashMap<>();

    public SensorDevice(int deviceID, String deviceName, String deviceType) {
        super(deviceID, deviceName, deviceType);
    }

    public void setSensorValue(String stateType, double value) {
        this.sensorState.put(stateType.toLowerCase(), value);
    }


    public double getSensorValue(String stateType) {
        return sensorState.getOrDefault(stateType.toLowerCase(), 0.0);
    }
    
 @Override
public String getStatus() {
StringBuilder status = new StringBuilder(super.getStatus()); 
if (!sensorState.isEmpty()) {
status.append(" | Readings: ").append(sensorState.toString()); }
 return status.toString();
}

 @Override
 void createDevice() {
    // TODO Auto-generated method stub
        System.out.println("Concrete Device: " + getDeviceName() + " (" + getDeviceType() + ") is created.");
 }
}