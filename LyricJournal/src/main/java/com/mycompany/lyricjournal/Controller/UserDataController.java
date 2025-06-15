/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Controller;

/**
 *
 * @author Kalli-Ann
 */

// UserDataController Class

// Handles storage and loading of user's lyric entries using JSON serialization

import com.mycompany.lyricjournal.Model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
 * UserDataController manages the data for user's lyric entries.
 * Provides static methods to save and load lyric collections to/from JSON files.
 * Each user has a separate JSON file named "{username}_lyrics.json".
 * Uses Gson library for efficient JSON serialization/deserialization.
 * 
 * Example usage:
 * UserDataController.saveUser(currentUser);      // save all entries to file
 * UserDataController.loadUserEntries(currentUser); // load entries from file
 */
public class UserDataController {
    // static Gson instance with pretty printing for readable JSON files
    // pretty printing makes files human-readable for debugging but uses more storage
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()  // format JSON with indentation and line breaks
            .create();

    /*
     * Saves all of a user's lyric entries to their personal JSON file
     * Creates or overwrites the user's data file with current entries
     * File naming convention: "{username}_lyrics.json"
     * 
     * Input: user - User object containing entries to save (input: User with populated entries list)
     * Output: Creates/updates JSON file with serialized lyric entries
     * 
     * Example: 
     * User user = new User("john", "password");
     * user.addEntry(new LyricEntry("Title", "Artist", "Lyrics", "Note"));
     * UserDataController.saveUser(user); // creates "john_lyrics.json"
     */
    public static void saveUser(User user) {
        // generate filename based on username for data separation
        String filename = user.getUsername() + "_lyrics.json";
        
        // use try-with-resources for automatic file closure
        try (FileWriter writer = new FileWriter(filename)) {
            // serialize only the entries ArrayList (not entire User object)
            // this keeps the JSON file focused and reduces storage requirements
            gson.toJson(user.getEntries(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Loads lyric entries from JSON file into a User object
     * Replaces any existing entries in the User's collection
     * Creates empty collection if file doesn't exist
     * 
     * Input: user - User object to populate with loaded entries (input: User object, entries will be replaced)
     * Output: User's entries ArrayList is populated with loaded data
     * 
     * Example:
     * User user = new User("john", "password");
     * UserDataController.loadUserEntries(user); // loads from "john_lyrics.json"
     * ArrayList<LyricEntry> entries = user.getEntries(); // now contains loaded entries
     */
    public static void loadUserEntries(User user) {
        // generate filename matching the save format
        String filename = user.getUsername() + "_lyrics.json";
        File file = new File(filename);
        
        // handle case where user has no saved data yet (first time use)
        if (!file.exists()) {
            return; // exit gracefully - user will have empty entries list
        }

        // attempt to load and deserialize the JSON file
        try (FileReader reader = new FileReader(file)) {
            // use TypeToken to handle generic ArrayList<LyricEntry> deserialization
            // required because of Java's type erasure with generics
            Type entryListType = new TypeToken<ArrayList<LyricEntry>>() {}.getType();
            
            // deserialize JSON back into ArrayList<LyricEntry>
            ArrayList<LyricEntry> entries = gson.fromJson(reader, entryListType);
            
            // validate that deserialization was successful
            if (entries != null) {
                // clear existing entries to prevent duplicates on repeated loads
                // ArrayList.clear() is O(n)
                user.getEntries().clear();
                
                // add each loaded entry using the User's addEntry method
                // this maintains proper encapsulation and any future validation logic
                for (LyricEntry entry : entries) {
                    user.addEntry(entry);  // O(n) worst case
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}