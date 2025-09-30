# Strategy Pattern: Example

## Overview

This project demonstrates the **Strategy Design Pattern** in Java.  
The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.  
This allows the algorithm (strategy) to vary independently from the clients that use it.

In this example:
- The `Order` class is the **Context** that requires a payment method.
- The `PayStrategy` interface defines the **Strategy** for payment processing.
- `PayByPayPal` and `PayByCreditCard` are **Concrete Strategies** that implement different payment methods.
- The `Main` class acts as the **Client** that selects a strategy (payment method) at runtime and uses it to process the order.

---

## Project Structure

| File | Description |
| :--- | :--- |
| **strategies/PayStrategy.java** | The **Strategy interface**. Declares `pay()` and `collectPaymentDetails()`. |
| **strategies/PayByPayPal.java** | A **Concrete Strategy**. Implements payment via PayPal with dummy verification. |
| **strategies/PayByCreditCard.java** | A **Concrete Strategy**. Implements payment via Credit Card with dummy funds check. |
| **strategies/CreditCard.java** | A helper class representing a credit card with balance, number, expiry, and CVV. |
| **order/Order.java** | The **Context class**. Holds order cost and delegates payment detail collection to the chosen strategy. |
| **Main.java** | The **Client**. Lets users choose products and a payment strategy, then processes the order. |

---

## Expected Flow

1. **Product Selection**:  
   - The user selects one or more products from a menu.  
   - The total order cost is accumulated.

2. **Strategy Selection (Payment Method)**:  
   - User chooses between:
     - `1` → PayPal  
     - `2` → Credit Card  

3. **Payment Details Collection**:  
   - For **PayPal**: prompts for email and password, validates with dummy database.  
   - For **Credit Card**: prompts for card number, expiry date, and CVV.

4. **Order Processing & Payment Execution**:  
   - If credentials are valid and sufficient funds exist, payment succeeds.  
   - Otherwise, it fails with an appropriate message.

---

## How to Run

1. Ensure you have **Java** installed on your machine.
2. **Compile** all the Java files:
    ```bash
    javac Main.java order/*.java strategies/*.java
    ```
3. **Run** the program:
    ```bash
    java Main
    ```

---
