package editor;

import publisher.EventManager;
import java.io.File;

public class Editor {
    public EventManager events;
    private File file;

    public Editor() {
        // Editor can notify about "open" and "save" events
        this.events = new EventManager("open", "save");
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        System.out.println("Editor: Opening file " + filePath);
        // Notify all subscribers of the "open" event
        events.notify("open", file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            System.out.println("Editor: Saving file " + file.getName());
            // Notify all subscribers of the "save" event
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }
}
