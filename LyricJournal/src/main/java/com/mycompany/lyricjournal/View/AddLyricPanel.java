/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.View;

/**
 *
 * @author Kalli-Ann
 */

// * AddLyricPanel - GUI component for adding new lyric entries to the journal
// * 
// * This panel provides a user-friendly interface for users to input song information
// * including title, artist, lyric text, and personal notes. It validates input
// * and integrates with the main application to save and display new entries.
// * 
// * Example usage:
// * User fills in:
// * - Song Title: "Imagine" 
// * - Artist: "John Lennon"
// * - Lyric Text: "Imagine all the people living life in peace"
// * - Note: "This line always gives me hope"
// * Then clicks "Add Lyric" to save the entry

import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddLyricPanel extends JPanel {
    // input components for user data entry
    private JTextField titleField, artistField; // single-line text inputs for song title and artist
    private JTextArea lyricArea, noteArea; // multi-line text areas for lyrics and notes
    private JButton addButton, clearButton; // action buttons for adding entry and clearing form
    private User currentUser; // reference to current logged-in user
    private LyricJournalMainGUI parentFrame; // reference to parent window for callbacks

    /*
     * Constructor - Creates and initializes the Add Lyric panel
     * 
     * Sets up the GUI layout with input fields, labels, and buttons.
     * Configures event handlers for user interactions.
     * 
     * Example: new AddLyricPanel(userObject, mainGUIObject)
     * Creates a panel where user can input song data and save it to userObject's entries
     * 
     * Inputs:
     *
     * user - The current User object to add entries to
     * parent - The main GUI window for navigation callbacks
     */
    public AddLyricPanel(User user, LyricJournalMainGUI parent) {
        // initialize object references
        this.currentUser = user;
        this.parentFrame = parent;
        
        // set up main panel layout with border padding
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // create title section at top of panel
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Add New Lyric Entry");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // create main input form using GridBagLayout for precise component positioning
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // add padding around components

        // song title input row
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Song Title:"), gbc);
        titleField = new JTextField(30);  // 30 character width
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);

        // artist input row
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Artist:"), gbc);
        artistField = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(artistField, gbc);

        // lyric text input area (multi-line with scroll)
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;  // Align label to top-left
        inputPanel.add(new JLabel("Lyric Text:"), gbc);
        lyricArea = new JTextArea(4, 30);  // 4 rows, 30 columns
        lyricArea.setLineWrap(true);       // Enable word wrapping
        lyricArea.setWrapStyleWord(true);  // Wrap at word boundaries
        lyricArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane lyricScroll = new JScrollPane(lyricArea);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(lyricScroll, gbc);

        // user note input area (smaller than lyric area)
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Your Note:"), gbc);
        noteArea = new JTextArea(3, 30);  // 3 rows for notes
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane noteScroll = new JScrollPane(noteArea);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(noteScroll, gbc);

        // button panel at bottom
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Lyric");
        clearButton = new JButton("Clear All");
        
        // set consistent button sizes for professional appearance
        addButton.setPreferredSize(new Dimension(120, 35));
        clearButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        // assemble all panels into main layout
        add(titlePanel, BorderLayout.NORTH); // title at top
        add(inputPanel, BorderLayout.CENTER); // form in center (takes most space)
        add(buttonPanel, BorderLayout.SOUTH); // buttons at bottom

        // configure button event handlers
        addButton.addActionListener(e -> addLyric()); // save entry when Add clicked
        clearButton.addActionListener(e -> clearFields()); // clear form when clear button clicked

        // enable Enter key navigation between fields
        titleField.addActionListener(e -> artistField.requestFocus());
        artistField.addActionListener(e -> lyricArea.requestFocus());
    }

    /*
     * addLyric - Validates input and creates new lyric entry
     * 
     * Performs input validation to ensure required fields are filled,
     * creates a new LyricEntry object, adds it to the user's collection,
     * provides user feedback, and navigates to view the new entry.
     * 
     * Example execution:
     * User enters: Title="Yesterday", Artist="Beatles", Lyric="Yesterday, all my troubles seemed so far away"
     * Method validates all required fields are filled, creates LyricEntry object,
     * adds to user's entries, shows success message, clears form, refreshes view panel
     * 
     * Output: Success message displayed, form cleared, user navigated to view tab
     */
    private void addLyric() {
        // extract and trim whitespace from all input fields
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String lyric = lyricArea.getText().trim();
        String note = noteArea.getText().trim();

        // validate required fields (note is optional)
        if (title.isEmpty() || artist.isEmpty() || lyric.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in Song Title, Artist, and Lyric Text fields.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;  // exit method if validation fails
        }

        // create new entry object with current date automatically set
        LyricEntry entry = new LyricEntry(title, artist, lyric, note);
        
        // add entry to user's collection
        currentUser.addEntry(entry);
        
        UserDataController.saveUser(currentUser);
        
        // provide positive feedback to user
        JOptionPane.showMessageDialog(this, "Lyric added successfully!");
        
        // clear form for next entry
        clearFields();
        
        // update view panel to show new entry and switch to view tab
        parentFrame.refreshViewPanel();
        parentFrame.switchToViewTab();
    }

    /*
     * clearFields - Resets all input fields to empty state
     * 
     * Clears all text from input fields
     * 
     * Example: User clicks "Clear All" button after partially filling form
     * Result: All fields become empty, cursor moves to Song Title field
     * 
     * Output: All text fields cleared, focus on titleField for next entry
     */
    private void clearFields() {
        // clear all input fields
        titleField.setText("");
        artistField.setText("");
        lyricArea.setText("");
        noteArea.setText("");
        
        // return focus to first field for immediate typing
        titleField.requestFocus();
    }
}