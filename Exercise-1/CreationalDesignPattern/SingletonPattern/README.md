# Singleton Pattern:Example

## Overview

This project demonstrates the **Singleton Design Pattern** using Java's lazy initialization method. The Singleton pattern ensures that a class has **only one instance** and provides a single, global access point to that instance.

This specific example also includes a 1-second delay inside the constructor to **emulate a slow initialization process** for demonstration purposes.

---

## Project Structure

| File | Description |
| :--- | :--- |
| **Singleton.java** | The core class that implements the Singleton pattern. Its constructor is **private**, and the single instance is accessed via the static `getInstance()` method. |
| **Main.java** | A client class that demonstrates the pattern. It calls `getInstance()` twice with different values (**"FOO"** and **"BAR"**) to confirm that the same instance is returned both times. |

---

## Expected Output

Since the Singleton pattern ensures only one instance is ever created, the second call to `getInstance("BAR")` will **reuse** the first instance. Therefore, the output will show the value set by the **first call** ("FOO").

---

## How to Run

1.  Ensure you have **Java** installed on your machine.
2.  Save the code as `Singleton.java` and `Main.java` in the same directory.
3.  **Compile** the Java files:
    ```bash
    javac SingletonExample/Singleton.java Main.java
    ```
4.  **Run** the Main class:
    ```bash
    java Main
    ```