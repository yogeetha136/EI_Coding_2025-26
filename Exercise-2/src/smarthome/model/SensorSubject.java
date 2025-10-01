package model;
import Interface.SensorObserver;
public interface SensorSubject {
    void registerObserver(SensorObserver observer);
    void unregisterObserver(SensorObserver observer);
    void notifyObservers(int deviceID, String stateType, double newValue);
}