package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmartHomeLogger {

    private static final String LOG_FILE = "smart_home_log.txt";
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    public static void log(String level, String source, String message) {
        // Format: [TIMESTAMP] [LEVEL] [SOURCE] - MESSAGE
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = String.format("[%s] [%-5s] [%s] - %s%n", timestamp, level.toUpperCase(), source, message);

        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(logEntry);
        } catch (IOException e) {
            System.err.println("CRITICAL LOGGING ERROR: Could not write to log file: " + e.getMessage());
        }
    }
    
    public static void info(String source, String message) {
        log("INFO", source, message);
    }
    public static void error(String source, String message) {
        log("ERROR", source, message);
    }
}