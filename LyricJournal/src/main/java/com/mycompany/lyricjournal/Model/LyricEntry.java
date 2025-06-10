/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Model;

/**
 *
 * @author Kalli-Ann
 */


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LyricEntry {
    private String songTitle;
    private String artist;
    private String lyricText;
    private String userNote;
    private String dateAdded;

    // Original constructor for new entries
    public LyricEntry(String songTitle, String artist, String lyricText, String userNote) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.lyricText = lyricText;
        this.userNote = userNote;
        this.dateAdded = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Constructor for editing existing entries (preserves date)
    public LyricEntry(String songTitle, String artist, String lyricText, String userNote, String dateAdded) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.lyricText = lyricText;
        this.userNote = userNote;
        this.dateAdded = dateAdded;
    }

    // Getters
    public String getSongTitle() { 
        return songTitle; 
    }
    
    public String getArtist() { 
        return artist; 
    }
    
    public String getLyricText() { 
        return lyricText; 
    }
    
    public String getUserNote() { 
        return userNote; 
    }
    
    public String getDateAdded() { 
        return dateAdded; 
    }

    // Setters for editing
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setLyricText(String lyricText) {
        this.lyricText = lyricText;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    @Override
    public String toString() {
        return songTitle + " by " + artist + "\n\"" + lyricText + "\"\nNote: " + userNote + "\nDate: " + dateAdded;
    }
}