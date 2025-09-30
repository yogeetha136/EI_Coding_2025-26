# Factory Method Pattern: Example

## Overview

This project demonstrates the **Factory Method Design Pattern** in Java.  
The Factory Method pattern defines an **interface for creating objects**, but lets subclasses decide which class to instantiate. It allows a class to defer instantiation to subclasses.

In this example:
- The abstract creator (`Logistics`) declares the factory method (`createVehicle`).
- Concrete creators (`RoadLogistics`) override the factory method to produce specific products (`Car`, `Truck`).
- The client (`Main`) interacts with the creator (`Logistics`) without knowing the exact concrete class of the product.

---

## Project Structure

| File | Description |
| :--- | :--- |
| **InterfaceVehicle/Vehicle.java** | Product interface that declares the `deliver()` method implemented by concrete vehicles. |
| **vehicles/Car.java** | Concrete product that implements `Vehicle`. Represents delivery of **small items** by car. |
| **vehicles/Truck.java** | Concrete product that implements `Vehicle`. Represents delivery of **large cargo** by truck. |
| **logistics/Logistics.java** | Abstract creator class that declares the factory method `createVehicle()`. It also defines the `planDelivery()` method that uses the product returned by the factory. |
| **logistics/RoadLogistics.java** | Concrete creator that overrides `createVehicle()` to return a `Truck`. |
| **Main.java** | Client class that uses the `Logistics` abstraction to plan and execute a delivery simulation. |

---

## Expected Output

When you run the program, the `RoadLogistics` creator produces a `Truck` and executes the delivery.  
Expected console output:

---

## How to Run

1. Ensure you have **Java** installed on your machine.
2. **Compile** all the Java files:
    ```bash
    javac Main.java InterfaceVehicle/*.java vehicles/*.java logistics/*.java
    ```
3. **Run** the Main class:
    ```bash
    java Main
    ```

---