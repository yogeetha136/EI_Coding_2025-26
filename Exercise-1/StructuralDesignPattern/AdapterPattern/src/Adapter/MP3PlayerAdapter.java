package Adapter;

import App.MusicApp;
import Player.OldMP3Player;


public class MP3PlayerAdapter implements MusicApp {
    private OldMP3Player mp3Player;

    public MP3PlayerAdapter(OldMP3Player mp3Player) {
        this.mp3Player = mp3Player;
    }

    // This is the required 'Target' method
    @Override
    public void playSong(String songTitle) {
        System.out.println("Adapter: Converting 'playSong' to 'startPlaying'...");
        // Call the Adaptee's method
        mp3Player.startPlaying(songTitle);
    }
}