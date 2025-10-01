package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Device;

public class Scheduler {

    // Helper class to store a single scheduled task
    private static class ScheduledTask {
        int deviceID;
        String time;    // Format "HH:mm" (e.g., "06:00")
        String command; // "TurnOn" or "TurnOff"
        boolean executedToday = false; // Flag to prevent execution multiple times a day

        public ScheduledTask(int deviceID, String time, String command) {
            this.deviceID = deviceID;
            this.time = time;
            this.command = command;
        }


        @Override
        public String toString() {
            return String.format("{device: %d, time: \"%s\", command: \"%s\"}", deviceID, time, command);
        }
    }

    private static final List<ScheduledTask> tasks = new ArrayList<>();
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static ScheduledExecutorService scheduler;

    /**
     * Adds a new scheduled task.
     */
    public static void addSchedule(int deviceID, String time, String command) {
        if (DeviceManager.findDeviceById(deviceID) == null) {
            System.out.println("ERROR: Device ID " + deviceID + " not found. Cannot add schedule.");
            return;
        }
        if (!command.equalsIgnoreCase("TurnOn") && !command.equalsIgnoreCase("TurnOff")) {
            System.out.println("ERROR: Invalid command. Use 'TurnOn' or 'TurnOff'.");
            return;
        }
        
        // Basic time format validation (HH:mm)
        try {
            LocalTime.parse(time, TIME_FORMATTER);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid time format. Use HH:mm (e.g., 14:30).");
            return;
        }

        ScheduledTask newTask = new ScheduledTask(deviceID, time, command);
        tasks.add(newTask);
        System.out.println("Schedule added successfully: " + newTask.toString());
    }
    public static void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdownNow(); 
        }
    }

    /**
     * Lists all current scheduled tasks.
     */
    public static void listSchedules() {
        if (tasks.isEmpty()) {
            System.out.println("No scheduled tasks set.");
            return;
        }
        System.out.println("\n--- Current Scheduled Tasks ---");
        for (ScheduledTask task : tasks) {
            System.out.println(task.toString());
        }
    }

    /**
     * Initializes and starts the background scheduling check.
     * This should be called once when the application starts.
     */
    public static void startScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return; // Already running
        }
        
        // Use a single thread executor to run the check every minute
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
        // Run the check every minute (60 seconds) after an initial 5-second delay
        scheduler.scheduleAtFixedRate(Scheduler::checkSchedules, 5, 60, TimeUnit.SECONDS);
        System.out.println("Scheduler started. Checking tasks every 60 seconds.");
    }

    /**
     * The task that runs periodically to check if any schedule time is reached.
     */
    private static void checkSchedules() {
        LocalTime now = LocalTime.now();
        String currentTime = now.format(TIME_FORMATTER);

        System.out.println("\n[SCHEDULER] Current Time: " + currentTime + " | Checking " + tasks.size() + " tasks...");

        // Reset execution flags at midnight (or shortly after)
        if (now.getHour() == 0 && now.getMinute() == 1) {
            tasks.forEach(task -> task.executedToday = false);
            System.out.println("[SCHEDULER] Daily execution flags reset.");
        }

        for (ScheduledTask task : tasks) {
            // Check if current time matches the scheduled time AND it hasn't run today
            if (task.time.equals(currentTime) && !task.executedToday) {
                executeTask(task);
            }
        }
    }

    /**
     * Executes the specific device action.
     */
    private static void executeTask(ScheduledTask task) {
        Device targetDevice = DeviceManager.findDeviceById(task.deviceID);

        if (targetDevice != null) {
            System.out.println("SCHEDULE ACTIVATED: Time " + task.time + " reached. Executing: " + task.command + "(" + task.deviceID + ")");
            
            if (task.command.equalsIgnoreCase("TurnOn")) {
                targetDevice.turnOn();
            } else if (task.command.equalsIgnoreCase("TurnOff")) {
                targetDevice.turnOff();
            }
            task.executedToday = true; // Mark as executed for today
        } else {
            System.out.println("SCHEDULE ERROR: Target Device ID " + task.deviceID + " not found. Task skipped.");
        }
    }
}