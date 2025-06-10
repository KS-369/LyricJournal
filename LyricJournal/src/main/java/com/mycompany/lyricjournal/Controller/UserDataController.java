/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Controller;

/**
 *
 * @author Kalli-Ann
 */


import com.mycompany.lyricjournal.Model.*;

import com.google.gson.*; // import gson
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserDataController {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveUser(User user) {
        String filename = user.getUsername() + "_lyrics.json";
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(user.getEntries(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserEntries(User user) {
        String filename = user.getUsername() + "_lyrics.json";
        File file = new File(filename);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            Type entryListType = new TypeToken<ArrayList<LyricEntry>>() {}.getType();
            ArrayList<LyricEntry> entries = gson.fromJson(reader, entryListType);
            if (entries != null) {
                // Clear existing entries first to prevent duplicates!
                user.getEntries().clear();
                for (LyricEntry entry : entries) {
                    user.addEntry(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}