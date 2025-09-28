package smarthome.manager;

import java.util.ArrayList;
import java.util.List;
import smarthome.model.ConcreteDevice;
import smarthome.model.Device; 

public class DeviceManager {
        public enum DeviceName {
        LIGHT_BULB,
        THERMOSTAT,
        DOOR_LOCK,
        SECURITY_CAMERA,
        SMART_PLUG
    }

    public enum DeviceType {
        ACTUATOR,
        SENSOR,
        CONTROLLER
    }
        private static int deviceID = 1;
        public static List<Device> devices = new ArrayList<>();
        public static void listDevices(){
            if(!devices.isEmpty()){
                for(int i=0; i<devices.size(); i++){
                    System.out.println(devices.get(i).getDeviceID());
                    System.out.println(devices.get(i).getDeviceName());
                    System.out.println(devices.get(i).getDeviceType());
                }
            }
            else{
                System.out.println("device is not yet added");

            }
        }
        public static void listAvailableDeviceOptions() {
        System.out.println("\n--- Available Device Names (Case Insensitive) ---");
        for (DeviceName name : DeviceName.values()) {
            System.out.println("- " + name.name());
        }

        System.out.println("\n--- Available Device Types (Case Insensitive) ---");
        for (DeviceType type : DeviceType.values()) {
            System.out.println("- " + type.name());
        }
        System.out.println(); 
    }
    
    public static void addDevices(String deviceName, String deviceType){
        
        if (!isDeviceNameValid(deviceName)) {
            System.out.println("ERROR: Invalid Device Name. Please use one of the available options.");
            listAvailableDeviceOptions();
            return;
        }

        if (!isDeviceTypeValid(deviceType)) {
            System.out.println("ERROR: Invalid Device Type. Please use one of the available options.");
            listAvailableDeviceOptions();
            return;
        }

        devices.add(new ConcreteDevice(deviceID, deviceName, deviceType));
        System.out.println("Device added successfully: ID " + deviceID + ", Name: " + deviceName + ", Type: " + deviceType);
        deviceID++;
    }
public static void getDevice(int deviceID){
    boolean found = false;
    for(int i = 0; i < devices.size(); i++){
        if(devices.get(i).getDeviceID() == deviceID){
            System.out.println("Device found:");
            System.out.println("ID: " + devices.get(i).getDeviceID());
            System.out.println("Name: " + devices.get(i).getDeviceName());
            System.out.println("Type: " + devices.get(i).getDeviceType());
            found = true;
            break; 
        }
    }
    if(!found){
        System.out.println("The device with this device ID not exists.");
    }
}
public static Device findDeviceById(int id) {
        for (Device device : devices) {
            if (device.getDeviceID() == id) {
                return device;
            }
        }
        return null;
    }

public static void getDeviceStatus(int deviceID) {
    Device device = findDeviceById(deviceID);
    if (device != null) {
        device.getStatus();
    } else {
        System.out.println("Device ID " + deviceID + " not found.");
    }
}
    private static boolean isDeviceNameValid(String deviceName) {
        for (DeviceName name : DeviceName.values()) {
            if (name.name().equalsIgnoreCase(deviceName)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isDeviceTypeValid(String deviceType) {
        for (DeviceType type : DeviceType.values()) {
            if (type.name().equalsIgnoreCase(deviceType)) {
                return true;
            }
        }
        return false;
    }
}
