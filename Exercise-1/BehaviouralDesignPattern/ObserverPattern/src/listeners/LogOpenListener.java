package listeners;
import java.io.File;

// A Concrete Observer: writes to a log file
public class LogOpenListener implements EventListener {
    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Log to " + log.getName() + ": File " + file.getName() + " was " + eventType);
    }
}