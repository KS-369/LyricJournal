/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.Model;

/**
 *
 * @author Kalli-Ann
 */


import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<LyricEntry> entries;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.entries = new ArrayList<>();
    }

    public void addEntry(LyricEntry entry) {
        entries.add(entry);
    }

    public ArrayList<LyricEntry> getEntries() {
        return entries;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public ArrayList<LyricEntry> searchEntries(String keyword) {
        ArrayList<LyricEntry> results = new ArrayList<>();
        for (LyricEntry entry : entries) {
            if (entry.getSongTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                entry.getArtist().toLowerCase().contains(keyword.toLowerCase()) ||
                entry.getLyricText().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(entry);
            }
        }
        return results;
    }
}
