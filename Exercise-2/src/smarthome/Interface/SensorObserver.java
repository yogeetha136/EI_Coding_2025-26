package Interface;

public interface SensorObserver {
    void update(int deviceID, String stateType, double newValue);
}