# Observer Pattern: Example

## Overview

This project demonstrates the **Observer Design Pattern** in Java.  
The Observer pattern defines a **one-to-many dependency** between objects so that when one object (the **Subject**) changes state, all its dependents (**Observers**) are notified and updated automatically.

In this example:
- The `Editor` acts as the **Subject** that publishes events (`open`, `save`).
- The `EventManager` manages subscriptions and notifications for events.
- The `EventListener` interface defines the Observer contract.
- `LogOpenListener` and `EmailNotificationListener` are **Concrete Observers** that react to events:
  - Logging the file operation to a log file.
  - Sending an email notification.
- The client (`Main`) subscribes and unsubscribes observers to test the event system.

---

## Project Structure

| File | Description |
| :--- | :--- |
| **listeners/EventListener.java** | The **Observer interface**. Defines the `update()` method. |
| **listeners/LogOpenListener.java** | A **Concrete Observer**. Logs file operations into a log file. |
| **listeners/EmailNotificationListener.java** | A **Concrete Observer**. Sends an email when a file is saved. |
| **publisher/EventManager.java** | The **Publisher/Subject** that maintains observers and notifies them of events. |
| **editor/Editor.java** | The core editor class (Subject) that triggers events (`open`, `save`). |
| **Main.java** | The **Client**. Demonstrates subscribing, unsubscribing, and triggering events. |

---

## Expected Output

When running the program, you’ll see how observers are notified when the editor performs actions:


- Notice that after unsubscribing the `LogOpenListener`, the log message no longer appears when opening the new file.

---

## How to Run

1. Ensure you have **Java** installed on your machine.
2. **Compile** all the Java files:
    ```bash
    javac Main.java editor/*.java publisher/*.java listeners/*.java
    ```
3. **Run** the Main class:
    ```bash
    java Main
    ```

---
