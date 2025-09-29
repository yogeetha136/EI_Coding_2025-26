package smarthome.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smarthome.manager.DeviceManager;

public class Trigger {

    // Helper class to store a single trigger rule
    private static class TriggerRule {
        String conditionType; // e.g., "temperature"
        String operator;      // e.g., ">"
        double value;         // e.g., 75
        String action;        // e.g., "turnOff(1)"

        public TriggerRule(String conditionType, String operator, double value, String action) {
            this.conditionType = conditionType;
            this.operator = operator;
            this.value = value;
            this.action = action;
        }

        @Override
        public String toString() {
            return String.format("condition: \"%s %s %s\", action: \"%s\"", 
                                 conditionType, operator, value, action);
        }
    }

    // Map: Key = Device ID being MONITORED (e.g., Thermostat ID), Value = List of rules for that device
    private static final Map<Integer, List<TriggerRule>> deviceTriggers = new HashMap<>();

    /**
     * Adds a new trigger rule to a monitoring device.
     * @param monitoringDeviceId The ID of the device whose state is being checked (e.g., Thermostat ID).
     * @param conditionType The type of condition (e.g., "temperature").
     * @param operator The comparison operator (e.g., ">", "<", "==").
     * @param value The value to compare against (e.g., 75).
     * @param action The action to execute (e.g., "turnOff(1)").
     */
    public static void addTrigger(int monitoringDeviceId, String conditionType, 
                                  String operator, double value, String action) {
        
        // Basic Validation: Check if the monitoring device exists
        if (DeviceManager.findDeviceById(monitoringDeviceId) == null) {
            System.out.println("ERROR: Monitoring Device ID " + monitoringDeviceId + " not found. Cannot add trigger.");
            return;
        }

        TriggerRule newRule = new TriggerRule(conditionType, operator, value, action);
        
        // Add rule to the map. Use computeIfAbsent for clean list initialization.
        deviceTriggers.computeIfAbsent(monitoringDeviceId, k -> new ArrayList<>()).add(newRule);

        System.out.println("Trigger added successfully. Device " + monitoringDeviceId + " monitored.");
        System.out.println("Automated Triggers: " + getDeviceTriggers(monitoringDeviceId));
    }
    
    /**
     * Lists all triggers for a specific monitoring device.
     */
    public static String getDeviceTriggers(int monitoringDeviceId) {
        List<TriggerRule> rules = deviceTriggers.get(monitoringDeviceId);
        if (rules == null || rules.isEmpty()) {
            return "No automated triggers set for device " + monitoringDeviceId;
        }
        return rules.toString();
    }
    
    /**
     * Placeholder method to show where the execution logic would go.
     * In a real system, this would be called whenever a device's state changes.
     */
    public static void evaluateTriggers(int monitoringDeviceId, String stateType, double currentStateValue) {
        List<TriggerRule> rules = deviceTriggers.get(monitoringDeviceId);
        
        if (rules == null || rules.isEmpty()) {
            return; // No triggers to check
        }

        System.out.println("\n--- Evaluating Triggers for Device " + monitoringDeviceId + " (Current " + stateType + ": " + currentStateValue + ") ---");
        
        for (TriggerRule rule : rules) {
            if (rule.conditionType.equalsIgnoreCase(stateType)) {
                boolean conditionMet = checkCondition(currentStateValue, rule.operator, rule.value);
                
                if (conditionMet) {
                    System.out.println("TRIGGER ACTIVATED: Condition met: " + rule.conditionType + " " + rule.operator + " " + rule.value);
                    executeAction(rule.action);
                }
            }
        }
    }

    private static boolean checkCondition(double currentValue, String operator, double targetValue) {
        switch (operator) {
            case ">": return currentValue > targetValue;
            case "<": return currentValue < targetValue;
            case "==": return currentValue == targetValue;
            default: return false;
        }
    }

    private static void executeAction(String action) {
        // Simple parsing for the action format: "turnOff(X)" or "turnOn(X)"
        try {
            if (action.startsWith("turnOff(") || action.startsWith("turnOn(")) {
                
                // Get the target Device ID from inside the parentheses
                String idStr = action.substring(action.indexOf('(') + 1, action.indexOf(')'));
                int targetDeviceId = Integer.parseInt(idStr);
                
                Device targetDevice = DeviceManager.findDeviceById(targetDeviceId);

                if (targetDevice != null) {
                    if (action.startsWith("turnOff")) {
                        targetDevice.turnOff();
                    } else if (action.startsWith("turnOn")) {
                        targetDevice.turnOn();
                    }
                } else {
                    System.out.println("ACTION FAILED: Target device ID " + targetDeviceId + " not found for action: " + action);
                }
            } else {
                System.out.println("ACTION FAILED: Unknown action format: " + action);
            }
        } catch (Exception e) {
            System.out.println("ACTION ERROR: Could not parse or execute action: " + action);
        }
    }
}