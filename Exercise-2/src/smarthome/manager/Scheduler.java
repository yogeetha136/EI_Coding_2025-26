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

    private static class ScheduledTask {
        int deviceID;
        String time;    
        String command; 
        boolean executedToday = false; 

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

    public static void addSchedule(int deviceID, String time, String command) {
        // Validation logic for DeviceManager, command, and time format goes here
        
        ScheduledTask newTask = new ScheduledTask(deviceID, time, command);
        tasks.add(newTask);
        System.out.println("Schedule added successfully: " + newTask.toString());
    }

    public static void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdownNow(); 
        }
    }

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

    public static void startScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return; 
        }
        
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
        scheduler.scheduleAtFixedRate(Scheduler::checkSchedules, 5, 60, TimeUnit.SECONDS);
        
        System.out.println("Scheduler started. Checking tasks every 60 seconds.");
    }

    private static void checkSchedules() {
        LocalTime now = LocalTime.now();
        String currentTime = now.format(TIME_FORMATTER);

        if (now.getHour() == 0 && now.getMinute() == 1) {
            tasks.forEach(task -> task.executedToday = false);
            System.out.println("[SCHEDULER] Daily execution flags reset.");
        }

        for (ScheduledTask task : tasks) {
            if (task.time.equals(currentTime) && !task.executedToday) {
                executeTask(task);
            }
        }
    }

    private static void executeTask(ScheduledTask task) {
        Device targetDevice = DeviceManager.findDeviceById(task.deviceID);

        if (targetDevice != null) {
            System.out.println("SCHEDULE ACTIVATED: Time " + task.time + " reached. Executing: " + task.command + "(" + task.deviceID + ")");
            
            if (task.command.equalsIgnoreCase("TurnOn")) {
                targetDevice.turnOn();
            } else if (task.command.equalsIgnoreCase("TurnOff")) {
                targetDevice.turnOff();
            }
            task.executedToday = true;
        } else {
            System.out.println("SCHEDULE ERROR: Target Device ID " + task.deviceID + " not found. Task skipped.");
        }
    }
}