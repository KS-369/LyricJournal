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
    // instance variables to store entry data
    private String songTitle;    // title of the song
    private String artist;       // artist or band name
    private String lyricText;    // the actual lyric text being saved
    private String userNote;     // user's personal note about the lyric
    private String dateAdded;    // date when entry was created (format: yyyy-MM-dd)

    /*
     * Primary constructor for creating new lyric entries
     * Automatically sets the current date as dateAdded
     * 
     * Inputs:
     *
     * songTitle - The title of the song (input: non-null String)
     * artist - The artist or band name (input: non-null String)
     * lyricText - The lyric text to save (input: non-null String)
     * userNote - User's personal note (input: String, can be empty)
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

    /*
     * Secondary constructor for editing existing entries
     * Preserves the original creation date when updating entry details
     * 
     * Inputs:
     *
     * songTitle - Updated song title (input: non-null String)
     * artist - Updated artist name (input: non-null String)  
     * lyricText - Updated lyric text (input: non-null String)
     * userNote - Updated user note (input: String, can be empty)
     * dateAdded - Original creation date (input: String in yyyy-MM-dd format)
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
        this.dateAdded = dateAdded; //preserve original date for edits
    }

    // getter methods - provide read access to private instance variables
    
    /*
     * gets the song title
     * returns string containing the song title (output: non-null String)
     */
    public String getSongTitle() { 
        return songTitle; 
    }
    
    /**
     * gets the artist name
     * string containing the artist name (output: non-null String)
     */
    public String getArtist() { 
        return artist; 
    }
    
    /*
     * gets the lyric text
     * returns string containing the saved lyric text (output: non-null String)
     */
    public String getLyricText() { 
        return lyricText; 
    }
    
    /*
     * gets the user's note
     * Returns string containing user's note, may be empty (output: String, possibly empty)
     */
    public String getUserNote() { 
        return userNote; 
    }
    
    /*
     * gets the date when entry was added
     * returns string containing date in yyyy-MM-dd format (output: formatted date string)
     */
    public String getDateAdded() { 
        return dateAdded; 
    }

    // setter methods - provide controlled write access for editing functionality
    
    /*
     * updates the song title
     * Input: songTitle - New song title (input: non-null String)
     * Output: Updates internal songTitle field
     */
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    /*
     * updates the artist name  
     * Input: artist - New artist name (input: non-null String)
     * Output: Updates internal artist field
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * updates the lyric text
     * Input: lyricText - New lyric text (input: non-null String)
     * Output: Updates internal lyricText field
     */
    public void setLyricText(String lyricText) {
        this.lyricText = lyricText;
    }

    /*
     * updates the user's note
     * Input: userNote - New user note (input: String, can be empty)
     * Output: Updates internal userNote field
     */
    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    /*
     * provides a formatted string representation of the lyric entry
     * used for display and debugging purposes
     * 
     * returns formatted string with all entry details (output: multi-line formatted string)
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