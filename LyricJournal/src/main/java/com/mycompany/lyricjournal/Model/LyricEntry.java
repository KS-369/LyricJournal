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

    public LyricEntry(String songTitle, String artist, String lyricText, String userNote) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.lyricText = lyricText;
        this.userNote = userNote;
        this.dateAdded = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    @Override
    public String toString() {
        return songTitle + " by " + artist + "\n\"" + lyricText + "\"\nNote: " + userNote + "\nDate: " + dateAdded;
    }
}
