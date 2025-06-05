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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddLyricPanel extends JPanel {
    private JTextField titleField, artistField;
    private JTextArea lyricArea, noteArea;
    private JButton addButton, clearButton;
    private User currentUser;
    private LyricJournalMainGUI parentFrame;

    public AddLyricPanel(User user, LyricJournalMainGUI parent) {
        this.currentUser = user;
        this.parentFrame = parent;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Add New Lyric Entry");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Song title
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Song Title:"), gbc);
        titleField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);

        // Artist
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Artist:"), gbc);
        artistField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(artistField, gbc);

        // Lyric text
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputPanel.add(new JLabel("Lyric Text:"), gbc);
        lyricArea = new JTextArea(4, 30);
        lyricArea.setLineWrap(true);
        lyricArea.setWrapStyleWord(true);
        lyricArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane lyricScroll = new JScrollPane(lyricArea);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(lyricScroll, gbc);

        // User note
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Your Note:"), gbc);
        noteArea = new JTextArea(3, 30);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane noteScroll = new JScrollPane(noteArea);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(noteScroll, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Lyric");
        clearButton = new JButton("Clear All");
        
        addButton.setPreferredSize(new Dimension(120, 35));
        clearButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        // Add panels to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addLyric());
        clearButton.addActionListener(e -> clearFields());

        // Enter key support for text fields
        titleField.addActionListener(e -> artistField.requestFocus());
        artistField.addActionListener(e -> lyricArea.requestFocus());
    }

    private void addLyric() {
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String lyric = lyricArea.getText().trim();
        String note = noteArea.getText().trim();

        if (title.isEmpty() || artist.isEmpty() || lyric.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in Song Title, Artist, and Lyric Text fields.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LyricEntry entry = new LyricEntry(title, artist, lyric, note);
        currentUser.addEntry(entry);
        
        JOptionPane.showMessageDialog(this, "Lyric added successfully!");
        clearFields();
        parentFrame.refreshViewPanel();
        parentFrame.switchToViewTab();
    }

    private void clearFields() {
        titleField.setText("");
        artistField.setText("");
        lyricArea.setText("");
        noteArea.setText("");
        titleField.requestFocus();
    }
}
