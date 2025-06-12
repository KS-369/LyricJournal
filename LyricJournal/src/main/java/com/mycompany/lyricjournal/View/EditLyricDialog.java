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
// Uses efficient dialog pattern to minimize memory usage and provide immediate feedback
        
import com.mycompany.lyricjournal.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Modal dialog class for editing lyric entries
 * Implements efficient form validation and data preservation strategies
 */
public class EditLyricDialog extends JDialog {
    // INPUT: GUI components for user data entry
    private JTextField titleField, artistField;     // Single-line text inputs for basic song info
    private JTextArea lyricArea, noteArea;          // Multi-line text areas for longer content
    private JButton saveButton, cancelButton;       // Action buttons for user choices
    
    // OUTPUT: Result tracking variables  
    private boolean confirmed = false;              // Returns true if user saved changes
    private LyricEntry originalEntry;               // Input: stores original entry data for reference
    private LyricEntry updatedEntry;                // Output: stores modified entry data after editing

    /**
     * Constructor: Creates edit dialog for specified lyric entry
     * INPUT: parent - parent frame for modal positioning
     * INPUT: entry - LyricEntry object containing current data to edit
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
        
        // Efficient sizing - fixed dimensions prevent unnecessary redraws
        setSize(500, 400);
        setLocationRelativeTo(parent);  // Center on parent for better UX
        setLayout(new BorderLayout());  // Efficient layout manager for dialog structure

        // Create input panel using GridBagLayout for precise component positioning
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Consistent spacing

        // Song title input field - pre-populated with existing data
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Song Title:"), gbc);
        titleField = new JTextField(entry.getSongTitle(), 25);  // INPUT: current title
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);

        // Artist input field - maintains existing artist data
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Artist:"), gbc);
        artistField = new JTextField(entry.getArtist(), 25);    // INPUT: current artist
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(artistField, gbc);

        // Lyric text area - expandable for longer lyrics
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputPanel.add(new JLabel("Lyric Text:"), gbc);
        lyricArea = new JTextArea(entry.getLyricText(), 4, 25); // INPUT: current lyrics
        lyricArea.setLineWrap(true);        // Efficient text wrapping
        lyricArea.setWrapStyleWord(true);   // Word-boundary wrapping
        lyricArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane lyricScroll = new JScrollPane(lyricArea);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.5;
        inputPanel.add(lyricScroll, gbc);

        // User note area - handles null values efficiently
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;
        inputPanel.add(new JLabel("Your Note:"), gbc);
        // INPUT: safely handles null notes with ternary operator for efficiency
        noteArea = new JTextArea(entry.getUserNote() != null ? entry.getUserNote() : "", 3, 25);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane noteScroll = new JScrollPane(noteArea);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.3;
        inputPanel.add(noteScroll, gbc);

        // Button panel with consistent sizing for professional appearance
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Cancel");
        
        saveButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Efficient event handling - direct method references
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());

        // Keyboard shortcuts for better UX - ESC key closes dialog
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Focus management for better user experience
        SwingUtilities.invokeLater(() -> titleField.requestFocus());
    }

    /**
     * Validates input and saves changes to create updated entry
     * INPUT: User-entered data from form fields
     * OUTPUT: Sets confirmed flag and creates updatedEntry object
     * 
     * Validation ensures data integrity and provides user feedback
     * Efficient string trimming prevents whitespace-only entries
     */
    private void saveChanges() {
        // INPUT: Extract and clean user input data
        String title = titleField.getText().trim();     // Remove leading/trailing whitespace
        String artist = artistField.getText().trim();
        String lyric = lyricArea.getText().trim();
        String note = noteArea.getText().trim();

        // Validate required fields - efficient early return pattern
        if (title.isEmpty() || artist.isEmpty() || lyric.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in Song Title, Artist, and Lyric Text fields.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;  // Early return saves processing time
        }

        // OUTPUT: Create updated entry preserving original date (efficient data preservation)
        updatedEntry = new LyricEntry(title, artist, lyric, note, originalEntry.getDateAdded());
        
        confirmed = true;  // OUTPUT: Signal successful completion
        dispose();         // Close dialog after successful save
    }

    /**
     * Returns whether user confirmed changes
     * OUTPUT: boolean indicating if changes were saved
     * 
     * Example: if (dialog.isConfirmed()) { processChanges(); }
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Returns the updated lyric entry with user modifications
     * OUTPUT: LyricEntry object containing modified data, or null if cancelled
     * 
     * Example: LyricEntry updated = dialog.getUpdatedEntry();
     */
    public LyricEntry getUpdatedEntry() {
        return updatedEntry;
    }
}