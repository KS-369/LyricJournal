/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Controller;

/**
 *
 * @author Kalli-Ann
 */

// UserManager Controller Class

// Handles user registration, authentication, and persistent storage of user accounts


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/*
 * UserManager class manages user accounts and authentication for the LyricJournal application.
 * Provides static methods for user registration, login validation, and data storage.
 * Uses JSON file storage and HashMap for efficient user lookup operations.
 * 
 * Example usage:
 * boolean success = UserManager.registerUser("newuser", "password123");
 * boolean valid = UserManager.validateLogin("newuser", "password123");
 */
public class UserManager {
    // class constants for file management
    private static final String USERS_FILE = "users.json"; // JSON file storing user accounts
    private static final Gson gson = new GsonBuilder() // JSON serializer with pretty printing (pretty printing makes it easier to read)
            .setPrettyPrinting()
            .create();
    
    // HashMap provides O(1) average lookup time for user authentication (worst case is O(n))
    private static Map<String, String> users = new HashMap<>();     // username -> password mapping
    
    // static initializer block - loads users when class is first accessed
    static {
        loadUsers();  // load existing users from file on startup
    }
    
    /*
     * Checks if a username already exists in the system
     * Uses case-insensitive comparison to prevent similar usernames
     * 
     *
     * Input: username - Username to check (input: String, case-insensitive)
     * Output: Returns true if username exists, false otherwise (output: boolean)
     * 
     * Example: if (UserManager.userExists("JohnDoe")) { // username taken }
     */
    public static boolean userExists(String username) {
        // convert to lowercase for case-insensitive comparison
        // HashMap.containsKey() is O(1) time complexity
        return users.containsKey(username.toLowerCase());
    }
    
    /*
     * Validates user login credentials against stored accounts
     * Performs case-insensitive username lookup with exact password match
     * 
     * Inputs:
     *
     * username - Username to validate (input: String, case-insensitive)
     * username - password Password to validate (input: String, case-sensitive)
     * Output: returns true if credentials are valid, false otherwise (output: boolean)
     * 
     * Example: if (UserManager.validateLogin("user", "pass")) { // allow login }
     */
    public static boolean validateLogin(String username, String password) {
        // retrieve stored password for username (case-insensitive lookup)
        String storedPassword = users.get(username.toLowerCase());
        
        // validate: user exists AND password matches exactly
        return storedPassword != null && storedPassword.equals(password);
    }
    
    /*
     * Registers a new user account in the system
     * Prevents duplicate usernames and automatically saves to persistent storage
     * 
     * Inputs:
     *
     * username - Desired username (input: String, will be stored lowercase)
     * password - User's password (input: String, stored as-is)
     *
     * Output: Returns true if registration successful, false if username taken (output: boolean)
     * 
     * Example: 
     * if (UserManager.registerUser("newuser", "secure123")) {
     *     // Registration successful, user can now login
     * } else {
     *     // Username already taken
     * }
     */
    public static boolean registerUser(String username, String password) {
        // check if username already exists (prevents duplicates)
        if (userExists(username)) {
            return false; // registration failed - username already taken
        }
        
        // ddd new user to in-memory storage (lowercase for consistency)
        users.put(username.toLowerCase(), password);
        
        // save changes to file immediately
        saveUsers();
        
        return true; // registration successful
    }
    
    /*
     * Loads user accounts from JSON file into memory
     * Creates default admin account if no users file exists
     * Handles file I/O errors gracefully by maintaining empty user map
     * 
     * Input: Reads from USERS_FILE (users.json)
     * Output: Populates static users HashMap
     */
    private static void loadUsers() {
        File file = new File(USERS_FILE);
        
        // handle first-time setup - create default admin user
        if (!file.exists()) {
            users.put("admin", "admin");  // default account for initial setup
            saveUsers();                  // create the users file
            return;
        }
        
        // attempt to load existing users from JSON file
        try (FileReader reader = new FileReader(file)) {
            // use Gson TypeToken for generic type safety with HashMap
            
            // Java erases generics at runtime - gson.fromJson(reader, Map.class) 
            // would return Map<Object,Object> instead of Map<String,String>

            // TypeToken captures the full generic type info at compile time
            Type userMapType = new TypeToken<Map<String, String>>() {}.getType();
            
            // now Gson knows to create Map<String,String> specifically, not just Map
            Map<String, String> loadedUsers = gson.fromJson(reader, userMapType);
            
            
            // replace current users map if loading successful
            if (loadedUsers != null) {
                users = loadedUsers;
            }
        } catch (IOException e) {
            // log error but continue with empty/default user map
            System.err.println("Error loading users: " + e.getMessage());
        }
    }
    
    /**
     * Saves current user accounts to JSON file for persistence
     * Handles file I/O errors gracefully with error logging
     * 
     * Input: Static users HashMap
     * Output: Writes to USERS_FILE (users.json) in pretty-printed JSON format
     */
    private static void saveUsers() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            // convert HashMap to pretty-printed JSON and write to file
            gson.toJson(users, writer);
        } catch (IOException e) {
            // log error - data remains in memory but not saved
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    /*
     * Gets array of all registered usernames for administrative purposes
     * Returns copy of usernames to prevent external modification of user data
     * 
     * Returns array of all usernames (output: String[] with all registered usernames)
     * 
     * Example: 
     * String[] allUsers = UserManager.getAllUsernames();
     * // Returns ["admin", "user1", "user2", ...]
     */
    public static String[] getAllUsernames() {
        // convert set to array - creates defensive copy (prevents accidental modification of user database)
        return users.keySet().toArray(new String[0]);
    }
    
    /*
     * Checks if this is the first run of the application
     * Used to determine if welcome message should be shown
     * 
     * Returns true if no users file exists, false otherwise (output: boolean)
     * 
     * Example: if (UserManager.isFirstRun()) { showWelcomeMessage(); }
     */
    public static boolean isFirstRun() {
        return !new File(USERS_FILE).exists();  // file existence check
    }
}