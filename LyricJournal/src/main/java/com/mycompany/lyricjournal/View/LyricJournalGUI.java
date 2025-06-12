/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.View;

/**
 *
 * @author Kalli-Ann
 */


import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LyricJournalGUI extends JFrame {
    private JTextField titleField, artistField, lyricField, noteField, searchField;
    private JTextArea displayArea;
    private User currentUser;
    

    

    public LyricJournalGUI(User user) {
        this.currentUser = user;
        setTitle("LyricJournal - " + user.getUsername());
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        titleField = new JTextField();
        artistField = new JTextField();
        lyricField = new JTextField();
        noteField = new JTextField();
        JButton addButton = new JButton("Add Lyric");

        inputPanel.add(new JLabel("Song Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Artist:"));
        inputPanel.add(artistField);
        inputPanel.add(new JLabel("Lyric:"));
        inputPanel.add(lyricField);
        inputPanel.add(new JLabel("Your Note:"));
        inputPanel.add(noteField);
        inputPanel.add(addButton);

        // Center Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Bottom Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save All");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(saveButton);

        // Add panels
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String artist = artistField.getText();
            String lyric = lyricField.getText();
            String note = noteField.getText();

            if (!title.isEmpty() && !artist.isEmpty() && !lyric.isEmpty()) {
                LyricEntry entry = new LyricEntry(title, artist, lyric, note);
                currentUser.addEntry(entry);
                displayArea.append(entry + "\n\n");
                titleField.setText("");
                artistField.setText("");
                lyricField.setText("");
                noteField.setText("");
            }
        });

        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            displayArea.setText("");
            for (LyricEntry entry : currentUser.searchEntries(query)) {
                displayArea.append(entry + "\n\n");
            }
        });

        saveButton.addActionListener(e -> {
            UserDataController.saveUser(currentUser);
            JOptionPane.showMessageDialog(this, "Data saved!");
        });

        // Load entries on startup
        UserDataController.loadUserEntries(currentUser);
        for (LyricEntry entry : currentUser.getEntries()) {
            displayArea.append(entry + "\n\n");
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        // Temporary login screen
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        User user = new User(username, password);
        new LyricJournalGUI(user);
    }
}


