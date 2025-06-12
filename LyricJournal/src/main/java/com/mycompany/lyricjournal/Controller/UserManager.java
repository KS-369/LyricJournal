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

/**
 * UserManager class manages user accounts and authentication for the LyricJournal application.
 * Provides static methods for user registration, login validation, and persistent storage.
 * Uses JSON file storage and HashMap for efficient user lookup operations.
 * 
 * Example usage:
 * boolean success = UserManager.registerUser("newuser", "password123");
 * boolean valid = UserManager.validateLogin("newuser", "password123");
 */
public class UserManager {
    // Class constants for file management
    private static final String USERS_FILE = "users.json";           // JSON file storing user accounts
    private static final Gson gson = new GsonBuilder()               // JSON serializer with pretty printing
            .setPrettyPrinting()
            .create();
    
    // Static data structure for in-memory user storage
    // HashMap provides O(1) average lookup time for user authentication
    private static Map<String, String> users = new HashMap<>();     // username -> password mapping
    
    // Static initializer block - loads users when class is first accessed
    static {
        loadUsers();  // Load existing users from file on startup
    }
    
    /**
     * Checks if a username already exists in the system
     * Uses case-insensitive comparison to prevent similar usernames
     * 
     * @param username Username to check (input: String, case-insensitive)
     * @return true if username exists, false otherwise (output: boolean)
     * 
     * Example: if (UserManager.userExists("JohnDoe")) { // username taken }
     */
    public static boolean userExists(String username) {
        // Convert to lowercase for case-insensitive comparison
        // HashMap.containsKey() is O(1) average time complexity
        return users.containsKey(username.toLowerCase());
    }
    
    /**
     * Validates user login credentials against stored accounts
     * Performs case-insensitive username lookup with exact password match
     * 
     * @param username Username to validate (input: String, case-insensitive)
     * @param password Password to validate (input: String, case-sensitive)
     * @return true if credentials are valid, false otherwise (output: boolean)
     * 
     * Example: if (UserManager.validateLogin("user", "pass")) { // allow login }
     */
    public static boolean validateLogin(String username, String password) {
        // Retrieve stored password for username (case-insensitive lookup)
        String storedPassword = users.get(username.toLowerCase());
        
        // Validate: user exists AND password matches exactly
        return storedPassword != null && storedPassword.equals(password);
    }
    
    /**
     * Registers a new user account in the system
     * Prevents duplicate usernames and automatically saves to persistent storage
     * 
     * @param username Desired username (input: String, will be stored lowercase)
     * @param password User's password (input: String, stored as-is)
     * @return true if registration successful, false if username taken (output: boolean)
     * 
     * Example: 
     * if (UserManager.registerUser("newuser", "secure123")) {
     *     // Registration successful, user can now login
     * } else {
     *     // Username already taken
     * }
     */
    public static boolean registerUser(String username, String password) {
        // Check if username already exists (prevents duplicates)
        if (userExists(username)) {
            return false; // Registration failed - username already taken
        }
        
        // Add new user to in-memory storage (lowercase for consistency)
        users.put(username.toLowerCase(), password);
        
        // Persist changes to file immediately
        saveUsers();
        
        return true; // Registration successful
    }
    
    /**
     * Loads user accounts from JSON file into memory
     * Creates default admin account if no users file exists
     * Handles file I/O errors gracefully by maintaining empty user map
     * 
     * Input: Reads from USERS_FILE (users.json)
     * Output: Populates static users HashMap
     */
    private static void loadUsers() {
        File file = new File(USERS_FILE);
        
        // Handle first-time setup - create default admin user
        if (!file.exists()) {
            users.put("admin", "admin");  // Default account for initial setup
            saveUsers();                  // Create the users file
            return;
        }
        
        // Attempt to load existing users from JSON file
        try (FileReader reader = new FileReader(file)) {
            // Use Gson TypeToken for generic type safety with HashMap
            Type userMapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> loadedUsers = gson.fromJson(reader, userMapType);
            
            // Replace current users map if loading successful
            if (loadedUsers != null) {
                users = loadedUsers;
            }
        } catch (IOException e) {
            // Log error but continue with empty/default user map
            System.err.println("Error loading users: " + e.getMessage());
            // Keep existing users map (may be empty or contain defaults)
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
            // Convert HashMap to pretty-printed JSON and write to file
            gson.toJson(users, writer);
        } catch (IOException e) {
            // Log error - data remains in memory but not persisted
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    /**
     * Gets array of all registered usernames for administrative purposes
     * Returns copy of usernames to prevent external modification of user data
     * 
     * @return Array of all usernames (output: String[] with all registered usernames)
     * 
     * Example: 
     * String[] allUsers = UserManager.getAllUsernames();
     * // Returns ["admin", "user1", "user2", ...]
     */
    public static String[] getAllUsernames() {
        // Convert Set to Array - creates defensive copy
        return users.keySet().toArray(new String[0]);
    }
    
    /**
     * Checks if this is the first run of the application
     * Used to determine if welcome message should be shown
     * 
     * @return true if no users file exists, false otherwise (output: boolean)
     * 
     * Example: if (UserManager.isFirstRun()) { showWelcomeMessage(); }
     */
    public static boolean isFirstRun() {
        return !new File(USERS_FILE).exists();  // Simple file existence check
    }
}