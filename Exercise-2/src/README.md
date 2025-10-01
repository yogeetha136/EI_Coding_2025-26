# Smart Home Management System

##  Overview
This project simulates a **Smart Home Management System** implemented in **Java**.  
It models **users**, **rooms**, and various **smart devices** (actuators and sensors) while incorporating advanced features like:

- **Scheduling**
- **Automated Triggers**

The system is built using core **Object-Oriented Design Patterns** for modularity, scalability, and maintainability:

- **Proxy Pattern (`DeviceProxy`)** → Enforces access control (users can only interact with devices in authorized rooms).
- **Observer Pattern (`SensorDevice` & `Trigger`)** → Enables automation; sensors notify triggers, which execute actions automatically.
- **Singleton Pattern (`Trigger`)** → Ensures a single automation manager instance exists globally.
- **Factory/Abstract Factory (`DeviceManager`)** → Creates devices dynamically (`ConcreteDevice` or `SensorDevice`).

---

## Project Structure

SmartHomeSystem
├── Interface/
│ ├── DeviceInterface.java // Basic device controls (turnOn, turnOff, getStatus)
│ └── SensorObserver.java // Observer interface (update method)
│
├── model/
│ ├── User.java // User data model
│ ├── Room.java // Room data model
│ ├── Device.java // Abstract device class (implements DeviceInterface)
│ ├── ConcreteDevice.java // Standard device (e.g., Light Bulb, Door Lock)
│ ├── SensorDevice.java // Sensor device (e.g., Thermostat)
│ ├── SensorSubject.java // Sensor subject interface (register, unregister, notify)
│ ├── DeviceProxy.java // Proxy for access control
│ └── Trigger.java // Singleton automation manager (implements SensorObserver)
│
├── manager/
│ ├── UserManager.java // User creation, login, retrieval
│ ├── RoomManager.java // Room management & user authorization
│ ├── DeviceManager.java // Device creation, assignment, and updates
│ └── Scheduler.java // Time-based task manager
│
└── Main.java // Entry point for running the system


---

## How to Run

### 1. Create a `Main.java`
Example demonstration of the system:

```java
import manager.*;
import model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Smart Home System Initialization ---");

        // 1. Add Users
        UserManager.addUser("Alice", "alice@home.com", "Secure@123"); // ID 1
        UserManager.addUser("Bob", "bob@home.com", "Secure@123");     // ID 2
        User loggedInUser = UserManager.login("alice@home.com", "Secure@123");
        
        // 2. Add Room
        RoomManager.addRoom("Living Room", "COMMUNAL_AREA"); // ID 1

        // 3. Assign User 1 to Room 1
        RoomManager.assignUserToRoom(1, 1);
        System.out.println("\n" + RoomManager.isUserAuthorizedForRoom(1, 1)); // true

        // 4. Add Devices
        DeviceManager.addDevices("LIGHT_BULB", "ACTUATOR");   // ID 1
        DeviceManager.addDevices("THERMOSTAT", "SENSOR");     // ID 2 (SensorDevice)

        // 5. Assign Devices to Room
        RoomManager.addDeviceToRoom(1, List.of(1, 2));

        System.out.println("\n--- Automated Trigger (Observer Pattern) ---");

        // 6. Trigger Rule: If Thermostat > 75 → turnOff Light Bulb
        Trigger.addTrigger(2, "temperature", ">", 75.0, "turnOff(1)");

        // 7. Sensor Update (Below Threshold - No Action)
        DeviceManager.setDeviceSensorValue(2, "temperature", 70.0);

        // 8. Sensor Update (Above Threshold - Trigger Fires)
        DeviceManager.setDeviceSensorValue(2, "temperature", 80.0);

        System.out.println("Light Bulb Status: " + DeviceManager.findDeviceById(1).getStatus());

        System.out.println("\n--- Access Control (Proxy Pattern) ---");

        // 9. Proxy with Authorized User (User 1)
        Device lightBulb = DeviceManager.findDeviceById(1);
        DeviceProxy authorizedProxy = new DeviceProxy(lightBulb, 1, 1);
        authorizedProxy.turnOn(); // SUCCESS

        // 10. Proxy with Unauthorized User (User 2)
        DeviceProxy unauthorizedProxy = new DeviceProxy(lightBulb, 1, 2);
        unauthorizedProxy.turnOff(); // DENIED

        System.out.println("\n--- Scheduler Demo ---");
        Scheduler.addSchedule(1, "12:00", "TurnOff");
        Scheduler.listSchedules();
    }
}
```

### 2. Compile
    ```bash
javac Interface/*.java model/*.java manager/*.java Main.java
    ```

### 3. Run
    ```bash
    java Main
    ```
