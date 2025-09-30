
import editor.Editor;
import listeners.EmailNotificationListener;
import listeners.LogOpenListener;

public class Main {
    public static void main(String[] args) {
        Editor editor = new Editor();

        // Listener for "open" event (logging)
        editor.events.subscribe("open", new LogOpenListener("app_log.txt"));
        
        // Listener for "save" event (email notification)
        editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

        System.out.println("\n--- Testing events ---\n");
        try {
            // This triggers the LogOpenListener
            editor.openFile("my_document.txt");
            
            // This triggers the EmailNotificationListener
            editor.saveFile();
            
            // Unsubscribe the log listener and test again
            LogOpenListener logListener = new LogOpenListener("another_log.txt");
            editor.events.subscribe("open", logListener);
            editor.events.unsubscribe("open", logListener);
            
            System.out.println("\n--- Testing after unsubscribe ---\n");
            editor.openFile("new_document.txt"); 
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}