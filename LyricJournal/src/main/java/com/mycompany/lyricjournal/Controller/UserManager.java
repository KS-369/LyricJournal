/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Controller;

/**
 *
 * @author Kalli-Ann
 */

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USERS_FILE = "users.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    // Store username -> password mapping
    private static Map<String, String> users = new HashMap<>();
    
    static {
        loadUsers();
    }
    
    public static boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }
    
    public static boolean validateLogin(String username, String password) {
        String storedPassword = users.get(username.toLowerCase());
        return storedPassword != null && storedPassword.equals(password);
    }
    
    public static boolean registerUser(String username, String password) {
        if (userExists(username)) {
            return false; // Username already taken
        }
        
        users.put(username.toLowerCase(), password);
        saveUsers();
        return true; // Registration successful
    }
    
    private static void loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            // Create default admin user for testing
            users.put("admin", "admin");
            saveUsers();
            return;
        }
        
        try (FileReader reader = new FileReader(file)) {
            Type userMapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> loadedUsers = gson.fromJson(reader, userMapType);
            if (loadedUsers != null) {
                users = loadedUsers;
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            // Keep default empty map
        }
    }
    
    private static void saveUsers() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    // Method to get all registered usernames (for admin purposes)
    public static String[] getAllUsernames() {
        return users.keySet().toArray(new String[0]);
    }
    
    // Method to check if this is the first run (no users file exists)
    public static boolean isFirstRun() {
        return !new File(USERS_FILE).exists();
    }
}
