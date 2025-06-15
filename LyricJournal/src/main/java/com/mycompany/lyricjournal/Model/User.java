/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Model;

/**
 *
 * @author Kalli-Ann
 */

// User Model Class

// Represents a user account with authentication and lyric entry management

import java.util.ArrayList;

/*
 * User class represents a registered user account in the LyricJournal application.
 * Each user has credentials and maintains their own collection of lyric entries.
 * Implements user authentication and entry management functionality.
 * 
 * Example usage:
 * User user = new User("john_doe", "secure123");
 * user.addEntry(new LyricEntry("Title", "Artist", "Lyrics", "Note"));
 */
public class User {
    // instance variables for user account data
    private String username;                        // unique username for login
    private String password;                        // user's password (stored as plain text for simplicity)
    private ArrayList<LyricEntry> entries;         // collection of user's lyric entries

    /*
     * Constructor creates a new user account with empty entry collection
     * 
     * Inputs:
     *
     * username - unique identifier for the user (input: non-null, non-empty String)
     * password - user's authentication password (input: non-null String)
     * 
     * Output: creates User object with empty entries ArrayList
     * 
     * Example: new User("music_lover", "mypassword123")
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.entries = new ArrayList<>();  // initialize empty list - efficient for dynamic sizing
    }

    /*
     * adds a new lyric entry to the user's collection
     * uses ArrayList.add() for O(n) worst case time complexity
     * 
     * Input: - entry LyricEntry to add to collection (input: non-null LyricEntry object)
     * Output: Entry is appended to entries ArrayList
     * 
     * Example: user.addEntry(new LyricEntry("Imagine", "John Lennon", "Imagine all the people", "Peaceful message"))
     */
    public void addEntry(LyricEntry entry) {
        entries.add(entry);  // ArrayList provides efficient insertion at end
    }

    /*
     * gets the user's complete collection of lyric entries
     * returns ArrayList containing all user's lyric entries (output: ArrayList<LyricEntry>)
     */
    public ArrayList<LyricEntry> getEntries() {
        return entries;
    }

    /*
     * gets the username for this account
     * 
     * returns the user's username (output: non-null String)
     */
    public String getUsername() {
        return username;
    }

    /*
     * Validates a password attempt against stored password
     * Uses simple string comparison - in production would use hashing
     * 
     * Input: inputPassword - Password attempt to validate (input: String to check)
     * returns true if password matches, false otherwise (output: boolean)
     * 
     * Example: if (user.checkPassword("mypassword123")) { // login successful }
     */
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    /*
     * Searches through user's entries for keyword matches
     * Performs case-insensitive search across song title, artist, and lyric text
     * Uses linear search O(n) - appropriate for typical collection sizes
     * 
     * Input: keyword - Search term to look for (input: String, case-insensitive)
     * Returns ArrayList of matching entries (output: ArrayList<LyricEntry> with 0 or more matches)
     * 
     * Example: 
     * ArrayList<LyricEntry> results = user.searchEntries("love");
     * // Returns all entries containing "love" in title, artist, or lyrics
     */
    public ArrayList<LyricEntry> searchEntries(String keyword) {
        ArrayList<LyricEntry> results = new ArrayList<>();  // new list for search results
        
        // convert search keyword to lowercase for case-insensitive matching
        String lowerKeyword = keyword.toLowerCase();
        
        // linear search through all entries - O(n) complexity
        for (LyricEntry entry : entries) {
            // check if keyword appears in any of the searchable fields
            if (entry.getSongTitle().toLowerCase().contains(lowerKeyword) ||
                entry.getArtist().toLowerCase().contains(lowerKeyword) ||
                entry.getLyricText().toLowerCase().contains(lowerKeyword)) {
                
                results.add(entry);  // add matching entry to results
            }
        }
        return results;  // return collection of matches (may be empty)
    }
}