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
import java.util.ArrayList;

public class ViewLyricsPanel extends JPanel {
    private User currentUser;
    private JTextField searchField;
    private JPanel entriesPanel;
    private JScrollPane scrollPane;
    private ArrayList<LyricEntry> currentEntries;

    public ViewLyricsPanel(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        // Entries panel with scroll
        entriesPanel = new JPanel();
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(entriesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        searchButton.addActionListener(e -> performSearch());
        showAllButton.addActionListener(e -> showAllEntries());
        
        // Enter key for search
        searchField.addActionListener(e -> performSearch());

        // Initialize with all entries
        refreshEntries();
    }

    public void refreshEntries() {
        showAllEntries();
    }

    private void showAllEntries() {
        currentEntries = currentUser.getEntries();
        displayEntries(currentEntries);
        searchField.setText("");
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            showAllEntries();
            return;
        }
        
        currentEntries = currentUser.searchEntries(query);
        displayEntries(currentEntries);
    }

    private void displayEntries(ArrayList<LyricEntry> entries) {
        entriesPanel.removeAll();
        
        if (entries.isEmpty()) {
            JLabel noEntriesLabel = new JLabel("No entries found.");
            noEntriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noEntriesLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
            entriesPanel.add(noEntriesLabel);
        } else {
            for (int i = 0; i < entries.size(); i++) {
                LyricEntry entry = entries.get(i);
                JPanel entryPanel = createEntryPanel(entry, i);
                entriesPanel.add(entryPanel);
                entriesPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        entriesPanel.revalidate();
        entriesPanel.repaint();
    }

    private JPanel createEntryPanel(LyricEntry entry, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);

        // Header with title and artist
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(entry.getSongTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel artistLabel = new JLabel("by " + entry.getArtist());
        artistLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        artistLabel.setForeground(Color.GRAY);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(artistLabel, BorderLayout.EAST);

        // Lyric text
        JTextArea lyricArea = new JTextArea(entry.getLyricText());
        lyricArea.setEditable(false);
        lyricArea.setLineWrap(true);
        lyricArea.setWrapStyleWord(true);
        lyricArea.setFont(new Font("Arial", Font.PLAIN, 13));
        lyricArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        lyricArea.setBackground(panel.getBackground());

        // Note (if exists)
        JTextArea noteArea = null;
        if (entry.getUserNote() != null && !entry.getUserNote().trim().isEmpty()) {
            noteArea = new JTextArea("Note: " + entry.getUserNote());
            noteArea.setEditable(false);
            noteArea.setLineWrap(true);
            noteArea.setWrapStyleWord(true);
            noteArea.setFont(new Font("Arial", Font.ITALIC, 12));
            noteArea.setForeground(Color.BLUE);
            noteArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            noteArea.setBackground(panel.getBackground());
        }

        // Date and buttons panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel dateLabel = new JLabel("Added: " + entry.getDateAdded());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLabel.setForeground(Color.GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        editButton.setPreferredSize(new Dimension(70, 25));
        deleteButton.setPreferredSize(new Dimension(70, 25));
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        bottomPanel.add(dateLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add components to main panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(lyricArea, BorderLayout.CENTER);
        if (noteArea != null) {
            contentPanel.add(noteArea, BorderLayout.SOUTH);
        }

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        editButton.addActionListener(e -> editEntry(entry, index));
        deleteButton.addActionListener(e -> deleteEntry(entry, index));

        return panel;
    }

    private void editEntry(LyricEntry entry, int index) {
        EditLyricDialog dialog = new EditLyricDialog((Frame) SwingUtilities.getWindowAncestor(this), entry);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            LyricEntry updatedEntry = dialog.getUpdatedEntry();
            currentUser.getEntries().set(currentUser.getEntries().indexOf(entry), updatedEntry);
            refreshEntries();
        }
    }

    private void deleteEntry(LyricEntry entry, int index) {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this lyric entry?\n\n" +
            "\"" + entry.getSongTitle() + "\" by " + entry.getArtist(),
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            currentUser.getEntries().remove(entry);
            refreshEntries();
            JOptionPane.showMessageDialog(this, "Entry deleted successfully.");
        }
    }
}
