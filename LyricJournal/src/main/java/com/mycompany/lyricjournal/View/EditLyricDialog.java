/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.View;

/**
 *
 * @author Kalli-Ann
 */

// EditLyricDialog - Modal dialog for editing existing lyric entries

// Provides a user-friendly interface to modify song details while preserving original date

        
import com.mycompany.lyricjournal.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



// class for editing lyric entries

public class EditLyricDialog extends JDialog {
    // Input: GUI components for user data entry
    private JTextField titleField, artistField; // single-line text inputs for basic song info
    private JTextArea lyricArea, noteArea; // multi-line text areas for longer content
    private JButton saveButton, cancelButton; // action buttons for user choices
    
    // Output: Result tracking variables  
    private boolean confirmed = false; // returns true if user saved changes
    private LyricEntry originalEntry;  // Input: stores original entry data for reference
    private LyricEntry updatedEntry;   // Output: stores modified entry data after editing

    /*
     * Constructor: Creates edit dialog for specified lyric entry
     * Inputs: 
     * parent - parent frame for positioning
     * entry - LyricEntry object containing current data to edit
     * 
     * Example usage:
     * EditLyricDialog dialog = new EditLyricDialog(mainFrame, selectedEntry);
     * dialog.setVisible(true);
     * if (dialog.isConfirmed()) {
     *     LyricEntry updated = dialog.getUpdatedEntry();
     * }
     */
    public EditLyricDialog(Frame parent, LyricEntry entry) {
        super(parent, "Edit Lyric Entry", true);
        this.originalEntry = entry;
        
        
        setSize(500, 400);
        setLocationRelativeTo(parent); // center on parent
        setLayout(new BorderLayout());

        // create input panel using GridBagLayout for precise component positioning
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // consistent spacing

        // song title input field - pre-populated with existing data
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Song Title:"), gbc);
        titleField = new JTextField(entry.getSongTitle(), 25); // Input: current title
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);

        // artist input field - maintains existing artist data
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Artist:"), gbc);
        artistField = new JTextField(entry.getArtist(), 25); // Input: current artist
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(artistField, gbc);

        // lyric text area - expandable for longer lyrics
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputPanel.add(new JLabel("Lyric Text:"), gbc);
        lyricArea = new JTextArea(entry.getLyricText(), 4, 25); // Input: current lyrics
        lyricArea.setLineWrap(true); // efficient text wrapping
        lyricArea.setWrapStyleWord(true); // word-boundary wrapping to ensure word doesn't get cut
        lyricArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane lyricScroll = new JScrollPane(lyricArea);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.5;
        inputPanel.add(lyricScroll, gbc);

        // user note area
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;
        inputPanel.add(new JLabel("Your Note:"), gbc);
        
        noteArea = new JTextArea(entry.getUserNote(), 3, 25);
        
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane noteScroll = new JScrollPane(noteArea);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.3;
        inputPanel.add(noteScroll, gbc);

        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Cancel");
        
        saveButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // event handling
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());

        
        // Focus management for better user experience
        
        // titleField.requestFocus()

        // Puts the text cursor in the title field
        // User can immediately start typing without clicking first
        // The field gets highlighted/selected visually

        // SwingUtilities.invokeLater()

        // Delays the focus request until after the dialog is fully constructed
        // Ensures the GUI is completely ready before trying to focus
        
        SwingUtilities.invokeLater(() -> titleField.requestFocus());
    }

    /*
     * Validates input and saves changes to create updated entry
     * Input: User-entered data from form fields
     * Output: Sets confirmed flag and creates updatedEntry object
     * 
     * Validation ensures data integrity and provides user feedback
     * Efficient string trimming prevents whitespace-only entries
     */
    private void saveChanges() {
        // Input: Extract and clean user input data
        String title = titleField.getText().trim(); // remove leading/trailing whitespace
        String artist = artistField.getText().trim();
        String lyric = lyricArea.getText().trim();
        String note = noteArea.getText().trim();

        // validate required fields
        if (title.isEmpty() || artist.isEmpty() || lyric.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in Song Title, Artist, and Lyric Text fields.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Output: Create updated entry preserving original date
        updatedEntry = new LyricEntry(title, artist, lyric, note, originalEntry.getDateAdded());
        
        confirmed = true; // Output: Signal successful completion
        dispose(); // Close dialog after successful save
    }

    /**
     * Returns whether user confirmed changes
     * Output: boolean indicating if changes were saved
     * 
     * Example: if (dialog.isConfirmed()) { processChanges(); }
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Returns the updated lyric entry with user modifications
     * Output: LyricEntry object containing modified data, or null if cancelled
     * 
     * Example: LyricEntry updated = dialog.getUpdatedEntry();
     */
    public LyricEntry getUpdatedEntry() {
        return updatedEntry;
    }
}