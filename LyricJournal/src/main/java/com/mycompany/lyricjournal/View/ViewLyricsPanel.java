/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.View;

/**
 *
 * @author Kalli-Ann
 */

// * ViewLyricsPanel - GUI component for displaying and managing saved lyric entries
// * 
// * This panel provides functionality to view all saved lyric entries, search through them,
// * and perform edit/delete operations. Uses efficient ArrayList for data storage and
// * dynamic JPanel creation for scalable entry display.
// * 
// * Example usage:
// * Panel displays all user's saved lyrics in scrollable format
// * User can search for "love" to filter entries containing that word
// * Each entry shows with edit/delete buttons for management
// * Search example: "beatles" returns all entries with "Beatles" in title/artist/lyric
// *

import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewLyricsPanel extends JPanel {
    // Core data and UI components
    private User currentUser;                    // Reference to current logged-in user
    private JTextField searchField;              // Input field for search queries
    private JPanel entriesPanel;                 // Container for all lyric entry displays
    private JScrollPane scrollPane;              // Scrollable container for large entry lists
    private ArrayList<LyricEntry> currentEntries; // Currently displayed entries (all or filtered)

    /**
     * Constructor - Creates and initializes the View Lyrics panel
     * 
     * Sets up the search interface and scrollable entry display area.
     * Loads and displays all existing entries from the user's collection.
     * Uses BorderLayout for optimal space utilization.
     * 
     * Example: new ViewLyricsPanel(userObject)
     * Creates panel showing all lyrics saved by userObject, with search functionality
     * 
     * @param user - The current User object whose entries to display and manage
     */
    public ViewLyricsPanel(User user) {
        // Initialize user reference
        this.currentUser = user;
        
        // Set up main panel layout with padding
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create search interface at top of panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);  // 20 character width for search input
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        
        // Add search components in logical order
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        // Create scrollable entries display area
        entriesPanel = new JPanel();
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS)); // Vertical stacking
        scrollPane = new JScrollPane(entriesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show scrollbar
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling speed

        // Assemble main layout
        add(searchPanel, BorderLayout.NORTH);   // Search controls at top
        add(scrollPane, BorderLayout.CENTER);   // Scrollable entries take center space

        // Configure event handlers for user interactions
        searchButton.addActionListener(e -> performSearch());     // Execute search on button click
        showAllButton.addActionListener(e -> showAllEntries());   // Reset to show all entries
        
        // Enable Enter key for search (better UX)
        searchField.addActionListener(e -> performSearch());

        // Load and display all entries on initialization
        refreshEntries();
    }

    /**
     * refreshEntries - Reloads and displays all user entries
     * 
     * Public method called by parent components when entry data changes.
     * Ensures the display stays synchronized with the underlying data.
     * 
     * Example: Called after user adds new entry or edits existing one
     * Result: Display refreshes to show current state of all entries
     * 
     * Output: All current user entries displayed in panel
     */
    public void refreshEntries() {
        showAllEntries();
    }

    /**
     * showAllEntries - Displays complete list of user's lyric entries
     * 
     * Resets any active search filters and shows all entries.
     * Clears search field and updates display with full entry collection.
     * Uses efficient ArrayList reference assignment for performance.
     * 
     * Example: User has 50 saved lyrics, clicks "Show All"
     * Result: All 50 entries displayed, search field cleared
     * 
     * Output: Complete entry list displayed, search field empty
     */
    private void showAllEntries() {
        // Get reference to user's complete entry collection
        currentEntries = currentUser.getEntries();
        
        // Update display with all entries
        displayEntries(currentEntries);
        
        // Clear search field to indicate no filter active
        searchField.setText("");
    }

    /**
     * performSearch - Filters entries based on search query
     * 
     * Takes user's search input and filters entries containing the query
     * in title, artist, or lyric text. Handles empty queries gracefully.
     * Uses case-insensitive matching for better user experience.
     * 
     * Example: User types "love" in search field
     * Result: Only entries containing "love" (case-insensitive) in any field are shown
     * 
     * Output: Filtered entry list displayed based on search criteria
     */
    private void performSearch() {
        // Get and clean search query
        String query = searchField.getText().trim();
        
        // Handle empty search by showing all entries
        if (query.isEmpty()) {
            showAllEntries();
            return;
        }
        
        // Use User class search method for consistent filtering logic
        currentEntries = currentUser.searchEntries(query);
        
        // Update display with filtered results
        displayEntries(currentEntries);
    }

    /**
     * displayEntries - Renders list of entries in the panel
     * 
     * Efficiently creates visual components for each entry.
     * Handles empty result sets with user-friendly messaging.
     * Uses dynamic panel creation for scalability.
     * 
     * Example: Called with ArrayList containing 3 search results
     * Result: 3 entry panels created and displayed with spacing
     * 
     * @param entries - ArrayList of LyricEntry objects to display
     * Output: Visual representation of all entries in scrollable area
     */
    private void displayEntries(ArrayList<LyricEntry> entries) {
        // Clear existing display to prevent duplicates
        entriesPanel.removeAll();
        
        // Handle empty results with informative message
        if (entries.isEmpty()) {
            JLabel noEntriesLabel = new JLabel("No entries found.");
            noEntriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noEntriesLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
            entriesPanel.add(noEntriesLabel);
        } else {
            // Create visual panel for each entry
            for (int i = 0; i < entries.size(); i++) {
                LyricEntry entry = entries.get(i);
                JPanel entryPanel = createEntryPanel(entry);
                entriesPanel.add(entryPanel);
                entriesPanel.add(Box.createVerticalStrut(10)); // Add spacing between entries
            }
        }
        
        // Refresh display to show changes
        entriesPanel.revalidate();
        entriesPanel.repaint();
    }

    /**
     * createEntryPanel - Builds visual component for single lyric entry
     * 
     * Creates a comprehensive display panel with all entry information,
     * formatted for readability and including management buttons.
     * Uses efficient layout managers for responsive design.
     * 
     * Example: Given entry with title="Imagine", artist="John Lennon"
     * Result: Panel with formatted title/artist header, lyric text, note, date, edit/delete buttons
     * 
     * @param entry - LyricEntry object to create panel for
     * @return JPanel containing formatted entry display with action buttons
     * Output: Complete visual representation of single lyric entry
     */
    private JPanel createEntryPanel(LyricEntry entry) {
        // Main panel with raised border for visual separation
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);

        // Header section with song title and artist
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(entry.getSongTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel artistLabel = new JLabel("by " + entry.getArtist());
        artistLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        artistLabel.setForeground(Color.GRAY);
        artistLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding for spacing

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(artistLabel, BorderLayout.EAST);

        // Main lyric text display area
        JTextArea lyricArea = new JTextArea(entry.getLyricText());
        lyricArea.setEditable(false);      // Read-only display
        lyricArea.setLineWrap(true);       // Handle long lyrics gracefully
        lyricArea.setWrapStyleWord(true);  // Wrap at word boundaries
        lyricArea.setFont(new Font("Arial", Font.PLAIN, 13));
        lyricArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        lyricArea.setBackground(panel.getBackground()); // Match panel background

        // Optional user note display (only if note exists)
        JTextArea noteArea = null;
        if (entry.getUserNote() != null && !entry.getUserNote().trim().isEmpty()) {
            noteArea = new JTextArea("Note: " + entry.getUserNote());
            noteArea.setEditable(false);
            noteArea.setLineWrap(true);
            noteArea.setWrapStyleWord(true);
            noteArea.setFont(new Font("Arial", Font.ITALIC, 12));
            noteArea.setForeground(Color.BLUE);    // Distinguish notes with color
            noteArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            noteArea.setBackground(panel.getBackground());
        }

        // Bottom section with date and action buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel dateLabel = new JLabel("Added: " + entry.getDateAdded());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLabel.setForeground(Color.GRAY);

        // Action buttons for entry management
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        bottomPanel.add(dateLabel, BorderLayout.WEST);     // Date on left
        bottomPanel.add(buttonPanel, BorderLayout.EAST);   // Buttons on right

        // Assemble content panel with main entry information
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(lyricArea, BorderLayout.CENTER);
        if (noteArea != null) {
            contentPanel.add(noteArea, BorderLayout.SOUTH);
        }

        // Final panel assembly
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Configure button event handlers
        editButton.addActionListener(e -> editEntry(entry));     // Open edit dialog
        deleteButton.addActionListener(e -> deleteEntry(entry)); // Confirm and delete entry

        return panel;
    }

    /**
     * editEntry - Opens edit dialog for modifying existing entry
     * 
     * Launches modal dialog with current entry data pre-filled.
     * Handles user confirmation and updates entry if changes confirmed.
     * Automatically saves changes and refreshes display for immediate feedback.
     * 
     * Example: User clicks "Edit" on "Yesterday" by Beatles entry
     * Result: Dialog opens with current data, user can modify, changes saved if confirmed
     * 
     * @param entry - LyricEntry object to edit
     * Output: Entry modified in user's collection if user confirms changes
     */
    private void editEntry(LyricEntry entry) {
        // Create and show modal edit dialog
        EditLyricDialog dialog = new EditLyricDialog((Frame) SwingUtilities.getWindowAncestor(this), entry);
        dialog.setVisible(true);
        
        // Process changes if user confirmed modifications
        if (dialog.isConfirmed()) {
            LyricEntry updatedEntry = dialog.getUpdatedEntry();
            
            // Replace old entry with updated version in user's collection
            currentUser.getEntries().set(currentUser.getEntries().indexOf(entry), updatedEntry);
            
            // Persist changes immediately for data safety
            UserDataController.saveUser(currentUser);
            
            // Refresh display to show updated entry
            refreshEntries();
        }
    }

    /**
     * deleteEntry - Removes entry after user confirmation
     * 
     * Shows confirmation dialog with entry details to prevent accidental deletion.
     * Removes entry from user's collection if deletion confirmed.
     * Auto-saves changes and provides user feedback.
     * 
     * Example: User clicks "Delete" on unwanted entry
     * Result: Confirmation dialog shown, entry removed if user confirms, success message displayed
     * 
     * @param entry - LyricEntry object to delete
     * Output: Entry removed from user's collection if user confirms deletion
     */
    private void deleteEntry(LyricEntry entry) {
        // Show detailed confirmation dialog to prevent accidents
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this lyric entry?\n\n" +
            "\"" + entry.getSongTitle() + "\" by " + entry.getArtist(),
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        // Process deletion if user confirmed
        if (choice == JOptionPane.YES_OPTION) {
            // Remove entry from user's collection
            currentUser.getEntries().remove(entry);
            
            // Persist changes immediately
            UserDataController.saveUser(currentUser);
            
            // Refresh display to reflect deletion
            refreshEntries();
            
            // Provide positive feedback
            JOptionPane.showMessageDialog(this, "Entry deleted successfully.");
        }
    }
}