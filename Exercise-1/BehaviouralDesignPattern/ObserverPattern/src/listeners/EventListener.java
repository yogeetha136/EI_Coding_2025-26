package listeners;

import java.io.File;

// The Observer interface
public interface EventListener {
    void update(String eventType, File file);
}
