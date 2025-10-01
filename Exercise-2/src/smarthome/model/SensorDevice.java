package model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import Interface.SensorObserver;

    
 

public class SensorDevice extends Device implements SensorSubject{

    private final List<SensorObserver> observers = new ArrayList<>(); 
    private final Map<String, Double> sensorState = new HashMap<>();

    public SensorDevice(int deviceID, String deviceName, String deviceType) {
        super(deviceID, deviceName, deviceType);
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
   //SENSORSUBJECT METHODS
    @Override
    public void registerObserver(SensorObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(SensorObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int deviceID, String stateType, double newValue) {
        for (SensorObserver observer : observers) {
            //The observers are updated!
            observer.update(deviceID, stateType, newValue); 
        }
    }

    
    // Core logic: call notifyObservers after state changes
    public void setSensorValue(String stateType, double value) {
        this.sensorState.put(stateType.toLowerCase(), value);
        //Notify all listeners!
        notifyObservers(getDeviceID(), stateType, value); 
    }
 @Override
 void createDevice() {
        System.out.println("Concrete Device: " + getDeviceName() + " (" + getDeviceType() + ") is created.");
 }
}