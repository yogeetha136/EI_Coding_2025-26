# Adapter Pattern: Example

## Overview

This project demonstrates the **Adapter Design Pattern** in Java.  
The Adapter pattern allows **incompatible interfaces to work together** by acting as a bridge between them.

In this example:
- The client (`Main`) expects to use the `MusicApp` interface (the **Target**).
- The old implementation (`OldMP3Player`) has a different method signature (`startPlaying`) and cannot be used directly.
- The `MP3PlayerAdapter` acts as an **Adapter**, converting the `MusicApp` interface’s `playSong()` method calls into the `OldMP3Player`'s `startPlaying()` calls.
- This allows the old MP3 player to be used seamlessly with the modern `MusicApp` interface.

---

## Project Structure

| File | Description |
| :--- | :--- |
| **src/App/MusicApp.java** | The **Target** interface expected by the client. Declares the `playSong()` method. |
| **src/Player/OldMP3Player.java** | The **Adaptee** class. Contains an incompatible method `startPlaying()` that needs adapting. |
| **src/Adapter/MP3PlayerAdapter.java** | The **Adapter** class. Implements `MusicApp` and internally calls `OldMP3Player`’s `startPlaying()` method. |
| **Main.java** | The client code that demonstrates using both a modern `MusicApp` and the old `OldMP3Player` through the adapter. |

---

## Expected Output

When running the program, you’ll see how the **new streaming service** works directly and how the **old MP3 player** is used through the adapter:


---

## How to Run

1. Ensure you have **Java** installed on your machine.
2. **Compile** all the Java files:
    ```bash
    javac Main.java src/App/*.java src/Adapter/*.java src/Player/*.java
    ```
3. **Run** the Main class:
    ```bash
    java Main
    ```

---
