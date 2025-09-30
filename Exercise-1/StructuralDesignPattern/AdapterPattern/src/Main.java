import Adapter.MP3PlayerAdapter;
import App.MusicApp;
import Player.OldMP3Player;


public class Main {
    
    // This client method only knows how to use the 'MusicApp' interface (the Target)
    static void startMusic(MusicApp player) {
        player.playSong("Bohemian Rhapsody");
    }

    public static void main(String[] args) {
        // --- 1. Direct Target Implementation ---
        MusicApp newStreamer = new MusicApp() {
            @Override
            public void playSong(String songTitle) {
                System.out.println("New Streaming Service: Playing song: " + songTitle);
            }
        };

        // --- 2. Adaptee Implementation ---
        OldMP3Player oldPlayer = new OldMP3Player();
        
        // --- 3. Adaptation ---
        // Wrap the old player with the adapter so it can be treated as a MusicApp
        MusicApp adaptedPlayer = new MP3PlayerAdapter(oldPlayer);

        System.out.println("--- New Player Test (Direct Fit) ---");
        startMusic(newStreamer); // Uses the new, compatible interface

        System.out.println("\n--- Old Player via Adapter Test ---");
        startMusic(adaptedPlayer); // Uses the old player through the Adapter
    }
}
