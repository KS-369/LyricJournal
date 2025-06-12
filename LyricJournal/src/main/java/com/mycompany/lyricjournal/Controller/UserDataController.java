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

// Handles persistent storage and loading of user's lyric entries using JSON serialization

import com.mycompany.lyricjournal.Model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * UserDataController manages the persistence layer for user's lyric entries.
 * Provides static methods to save and load lyric collections to/from JSON files.
 * Each user has a separate JSON file named "{username}_lyrics.json".
 * Uses Gson library for efficient JSON serialization/deserialization.
 * 
 * Example usage:
 * UserDataController.saveUser(currentUser);      // Save all entries to file
 * UserDataController.loadUserEntries(currentUser); // Load entries from file
 */
public class UserDataController {
    // Static Gson instance with pretty printing for readable JSON files
    // Pretty printing makes files human-readable for debugging but uses more storage
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()  // Format JSON with indentation and line breaks
            .create();

    /**
     * Saves all of a user's lyric entries to their personal JSON file
     * Creates or overwrites the user's data file with current entries
     * File naming convention: "{username}_lyrics.json"
     * 
     * @param user User object containing entries to save (input: User with populated entries list)
     * Output: Creates/updates JSON file with serialized lyric entries
     * 
     * Example: 
     * User user = new User("john", "password");
     * user.addEntry(new LyricEntry("Title", "Artist", "Lyrics", "Note"));
     * UserDataController.saveUser(user); // Creates "john_lyrics.json"
     */
    public static void saveUser(User user) {
        // Generate filename based on username for data separation
        String filename = user.getUsername() + "_lyrics.json";
        
        // Use try-with-resources for automatic file closure
        try (FileWriter writer = new FileWriter(filename)) {
            // Serialize only the entries ArrayList (not entire User object)
            // This keeps the JSON file focused and reduces storage requirements
            gson.toJson(user.getEntries(), writer);
        } catch (IOException e) {
            // Print stack trace for debugging - in production would use logging framework
            e.printStackTrace();
        }
    }

    /**
     * Loads lyric entries from JSON file into a User object
     * Replaces any existing entries in the User's collection
     * Creates empty collection if file doesn't exist (graceful handling)
     * 
     * @param user User object to populate with loaded entries (input: User object, entries will be replaced)
     * Output: User's entries ArrayList is populated with loaded data
     * 
     * Example:
     * User user = new User("john", "password");
     * UserDataController.loadUserEntries(user); // Loads from "john_lyrics.json"
     * ArrayList<LyricEntry> entries = user.getEntries(); // Now contains loaded entries
     */
    public static void loadUserEntries(User user) {
        // Generate filename matching the save format
        String filename = user.getUsername() + "_lyrics.json";
        File file = new File(filename);
        
        // Handle case where user has no saved data yet (first time use)
        if (!file.exists()) {
            return; // Exit gracefully - user will have empty entries list
        }

        // Attempt to load and deserialize the JSON file
        try (FileReader reader = new FileReader(file)) {
            // Use TypeToken to handle generic ArrayList<LyricEntry> deserialization
            // This is required because of Java's type erasure with generics
            Type entryListType = new TypeToken<ArrayList<LyricEntry>>() {}.getType();
            
            // Deserialize JSON back into ArrayList<LyricEntry>
            ArrayList<LyricEntry> entries = gson.fromJson(reader, entryListType);
            
            // Validate that deserialization was successful
            if (entries != null) {
                // Clear existing entries to prevent duplicates on repeated loads
                // ArrayList.clear() is O(n) but necessary for data consistency
                user.getEntries().clear();
                
                // Add each loaded entry using the User's addEntry method
                // This maintains proper encapsulation and any future validation logic
                for (LyricEntry entry : entries) {
                    user.addEntry(entry);  // O(1) amortized per addition
                }
            }
        } catch (IOException e) {
            // Print stack trace for debugging file I/O issues
            // In production, would use proper logging and possibly show user-friendly error
            e.printStackTrace();
        }
    }
}