/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Model;

/**
 *
 * @author Kalli-Ann
 */

// LyricEntry Model Class

// Represents a single lyric entry in the journal with song information and user notes

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * LyricEntry class represents a single lyric entry containing song details,
 * lyric text, user notes, and creation date. This class follows the Model
 * pattern in MVC architecture.
 * 
 * Example usage:
 * LyricEntry entry = new LyricEntry("Bohemian Rhapsody", "Queen", 
 *                                   "Is this the real life?", "Amazing opening line");
 */
public class LyricEntry {
    // Instance variables to store entry data
    private String songTitle;    // Title of the song
    private String artist;       // Artist or band name
    private String lyricText;    // The actual lyric text being saved
    private String userNote;     // User's personal note about the lyric
    private String dateAdded;    // Date when entry was created (format: yyyy-MM-dd)

    /**
     * Primary constructor for creating new lyric entries
     * Automatically sets the current date as dateAdded
     * 
     * @param songTitle The title of the song (input: non-null String)
     * @param artist The artist or band name (input: non-null String)
     * @param lyricText The lyric text to save (input: non-null String)
     * @param userNote User's personal note (input: String, can be empty)
     * 
     * Output: Creates a new LyricEntry with current date
     * 
     * Example: new LyricEntry("Yesterday", "The Beatles", "Yesterday all my troubles seemed so far away", "Nostalgic song")
     */
    public LyricEntry(String songTitle, String artist, String lyricText, String userNote) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.lyricText = lyricText;
        this.userNote = userNote;
        // Automatically set current date in ISO format (yyyy-MM-dd)
        this.dateAdded = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Secondary constructor for editing existing entries
     * Preserves the original creation date when updating entry details
     * 
     * @param songTitle Updated song title (input: non-null String)
     * @param artist Updated artist name (input: non-null String)  
     * @param lyricText Updated lyric text (input: non-null String)
     * @param userNote Updated user note (input: String, can be empty)
     * @param dateAdded Original creation date (input: String in yyyy-MM-dd format)
     * 
     * Output: Creates LyricEntry with preserved original date
     * 
     * Example: new LyricEntry("Yesterday", "Beatles", "Yesterday all my troubles...", "Classic", "2024-01-15")
     */
    public LyricEntry(String songTitle, String artist, String lyricText, String userNote, String dateAdded) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.lyricText = lyricText;
        this.userNote = userNote;
        this.dateAdded = dateAdded; // Preserve original date for edits
    }

    // Getter methods - provide read access to private instance variables
    
    /**
     * Gets the song title
     * @return String containing the song title (output: non-null String)
     */
    public String getSongTitle() { 
        return songTitle; 
    }
    
    /**
     * Gets the artist name
     * @return String containing the artist name (output: non-null String)
     */
    public String getArtist() { 
        return artist; 
    }
    
    /**
     * Gets the lyric text
     * @return String containing the saved lyric text (output: non-null String)
     */
    public String getLyricText() { 
        return lyricText; 
    }
    
    /**
     * Gets the user's note
     * @return String containing user's note, may be empty (output: String, possibly empty)
     */
    public String getUserNote() { 
        return userNote; 
    }
    
    /**
     * Gets the date when entry was added
     * @return String containing date in yyyy-MM-dd format (output: formatted date string)
     */
    public String getDateAdded() { 
        return dateAdded; 
    }

    // Setter methods - provide controlled write access for editing functionality
    
    /**
     * Updates the song title
     * @param songTitle New song title (input: non-null String)
     * Output: Updates internal songTitle field
     */
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    /**
     * Updates the artist name  
     * @param artist New artist name (input: non-null String)
     * Output: Updates internal artist field
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Updates the lyric text
     * @param lyricText New lyric text (input: non-null String)
     * Output: Updates internal lyricText field
     */
    public void setLyricText(String lyricText) {
        this.lyricText = lyricText;
    }

    /**
     * Updates the user's note
     * @param userNote New user note (input: String, can be empty)
     * Output: Updates internal userNote field
     */
    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    /**
     * Provides a formatted string representation of the lyric entry
     * Used for display and debugging purposes
     * 
     * @return Formatted string with all entry details (output: multi-line formatted string)
     * 
     * Example output:
     * "Yesterday by The Beatles
     * "Yesterday all my troubles seemed so far away"
     * Note: Nostalgic song
     * Date: 2024-01-15"
     */
    @Override
    public String toString() {
        return songTitle + " by " + artist + "\n\"" + lyricText + "\"\nNote: " + userNote + "\nDate: " + dateAdded;
    }
}