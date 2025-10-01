# Proxy Pattern: Example

## Overview

This project demonstrates the **Proxy Design Pattern** in Java.  
The Proxy pattern provides a **surrogate or placeholder** for another object to control access to it.  

In this example:
- The `DocumentService` interface defines the contract for reading sensitive documents.
- The `RealDocumentService` is the **Real Subject** that simulates costly operations such as loading and reading a large document.
- The `AccessControlProxy` is the **Proxy** that:
  - **Controls access** based on user roles.
  - **Performs lazy initialization**, ensuring the real document is only loaded when authorized access is granted.
- The client (`Main`) interacts only with the `DocumentService` interface, unaware whether it’s dealing with the proxy or the real service.

---

## Project Structure

| File | Description |
| :--- | :--- |
| **document_access/DocumentService.java** | The **Subject interface**. Declares the `readDocument()` operation. |
| **document_access/RealDocumentService.java** | The **Real Subject**. Implements costly document loading and reading operations. |
| **proxy/AccessControlProxy.java** | The **Proxy**. Checks user roles for access control and lazily initializes the real service. |
| **Main.java** | The **Client**. Requests document reads without knowing whether it’s interacting with the proxy or the real service. |

---

## Expected Output

When running the program, two scenarios are demonstrated:

1. **Authorized user (ADMIN)**:  
   - On first access, the real document service is **initialized** (with a 2-second delay).  
   - On second access, the proxy reuses the already initialized service, so it’s faster.

2. **Unauthorized user (VIEWER)**:  
   - The proxy denies access instantly, **without initializing** the costly real service.


---

## How to Run

1. Ensure you have **Java** installed on your machine.
2. **Compile** all the Java files:
    ```bash
    javac Main.java document_access/*.java proxy/*.java
    ```
3. **Run** the Main class:
    ```bash
    java Main
    ```

---
