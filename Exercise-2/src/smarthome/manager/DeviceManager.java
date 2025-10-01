package manager;

import java.util.ArrayList;
import java.util.List;
import model.ConcreteDevice;
import model.Device;
import model.SensorDevice;
import model.Trigger;

public class DeviceManager {
    public enum DeviceName {
        LIGHT_BULB,
        THERMOSTAT,
        DOOR_LOCK,
        SECURITY_CAMERA,
    }

    public enum DeviceType {
        ACTUATOR,
        SENSOR,
        CONTROLLER
    }

    private static int deviceID = 1;
    public static List<Device> devices = new ArrayList<>();


    public static void listDevices() {
        if (devices.isEmpty()) {
            System.out.println("No devices have been added yet.");
        } else {
            System.out.println("\n--- Registered Devices ---");
            for (Device device : devices) {
                System.out.printf("ID: %d | Name: %s | Type: %s\n",
                        device.getDeviceID(), device.getDeviceName(), device.getDeviceType());
            }
        }
    }


     //Prints the available device names and types from the Enums.
  
    public static void listAvailableDeviceOptions() {
        System.out.println("\n--- Available Device Names ---");
        for (DeviceName name : DeviceName.values()) {
            System.out.println("- " + name.name());
        }

        System.out.println("\n--- Available Device Types ---");
        for (DeviceType type : DeviceType.values()) {
            System.out.println("- " + type.name());
        }
        System.out.println();
    }


    public static void addDevices(String deviceName, String deviceType) {
        if (deviceName == null || deviceName.trim().isEmpty() || deviceType == null || deviceType.trim().isEmpty()) {
            System.out.println("ERROR: Device name and type cannot be empty.");
            return;
        }

        if (!isDeviceNameValid(deviceName)) {
            System.out.println("ERROR: Invalid Device Name '" + deviceName + "'. Please use one of the available options.");
            listAvailableDeviceOptions();
            return;
        }

        if (!isDeviceTypeValid(deviceType)) {
            System.out.println("ERROR: Invalid Device Type '" + deviceType + "'. Please use one of the available options.");
            listAvailableDeviceOptions();
            return;
        }

        // Determine which concrete class to instantiate
        String typeUpper = deviceType.toUpperCase();
        String nameUpper = deviceName.toUpperCase();

        if (typeUpper.equals(DeviceType.SENSOR.name()) || nameUpper.equals(DeviceName.THERMOSTAT.name())) {
            devices.add(new SensorDevice(deviceID, deviceName, deviceType));
        } else {
            devices.add(new ConcreteDevice(deviceID, deviceName, deviceType));
        }

        System.out.println("SUCCESS: Device added. ID " + deviceID + ", Name: " + deviceName + ", Type: " + deviceType);
        deviceID++;
    }


    public static void getDevice(int id) {
        Device device = findDeviceById(id);
        if (device != null) {
            System.out.println("\n--- Device Found ---");
            System.out.println("ID: " + device.getDeviceID());
            System.out.println("Name: " + device.getDeviceName());
            System.out.println("Type: " + device.getDeviceType());
        } else {
            System.out.println("ERROR: The device with ID " + id + " does not exist.");
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
            System.out.println("\n--- Device Status (ID " + deviceID + ") ---");
            device.getStatus();
        } else {
            System.out.println("ERROR: Device ID " + deviceID + " not found.");
        }
    }

  
    public static void setDeviceSensorValue(int deviceID, String stateType, double newValue) {
        Device device = findDeviceById(deviceID);

        if (device == null) {
            System.out.println("ERROR: Device ID " + deviceID + " not found.");
            return;
        }

        // Check if the device is a SensorDevice using instanceof
        if (device instanceof SensorDevice) {
            // Safe cast after checking
            SensorDevice sensor = (SensorDevice) device;
            sensor.setSensorValue(stateType, newValue);
            System.out.println("SUCCESS: Device " + deviceID + " (" + sensor.getDeviceName() +
                               ") state updated: " + stateType + " is now " + newValue);

            // CORE TRIGGER EXECUTION STEP
            Trigger.evaluateTriggers(deviceID, stateType, newValue);

        } else {
            System.out.println("ERROR: Device " + deviceID + " (" + device.getDeviceName() + 
                               ") is a " + device.getDeviceType() + " and is not a sensor capable of updating " + stateType + ".");
        }
    }

 

    private static boolean isDeviceNameValid(String deviceName) {
        if (deviceName == null) return false;
        for (DeviceName name : DeviceName.values()) {
            if (name.name().equalsIgnoreCase(deviceName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDeviceTypeValid(String deviceType) {
        if (deviceType == null) return false;
        for (DeviceType type : DeviceType.values()) {
            if (type.name().equalsIgnoreCase(deviceType)) {
                return true;
            }
        }
        return false;
    }
}