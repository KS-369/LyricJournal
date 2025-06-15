/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.View;

/**
 *
 * @author Kalli-Ann
 */

// LyricJournalMainGUI - Primary application interface after user authentication

// Manages tabbed interface for adding and viewing lyric entries
// Implements efficient data persistence and user session management
// Provides centralized access to all application features through clean tabbed design


import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main application window class - central hub for all user interactions
 * Efficiently coordinates between different panels using tabbed interface
 * Manages user session data and provides auto-save functionality
 */
public class LyricJournalMainGUI extends JFrame {
    // INPUT: User session data
    private User currentUser;                       // current authenticated user with their lyric entries
    
    // UI COMPONENTS: Efficient tab-based organization
    private JTabbedPane tabbedPane;                // main container for application tabs
    private AddLyricPanel addLyricPanel;           // panel for creating new lyric entries  
    private ViewLyricsPanel viewLyricsPanel;       // panel for viewing/editing existing entries

    /**
     * Constructor: Creates main application window for authenticated user
     * INPUT: user - authenticated User object containing credentials and existing entries
     * 
     * Sets up efficient tabbed interface and loads user's existing data
     * Provides menu system for file operations and session management
     * 
     * Example usage:
     * User authenticatedUser = new User("johnDoe", "password");
     * new LyricJournalMainGUI(authenticatedUser); // Opens main app for user "johnDoe"
     */
    public LyricJournalMainGUI(User user) {
        this.currentUser = user;
        setTitle("LyricJournal - " + user.getUsername());    // personalized window title
        setSize(800, 600);                                
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);                         // center on screen

        // create efficient tabbed interface - reduces memory usage vs multiple windows
        tabbedPane = new JTabbedPane();
        
        // initialize functional panels with user context
        addLyricPanel = new AddLyricPanel(currentUser, this);
        viewLyricsPanel = new ViewLyricsPanel(currentUser);
        
        // add tabs with descriptive names for clear navigation
        tabbedPane.addTab("Add Lyric", addLyricPanel);
        tabbedPane.addTab("View Lyrics", viewLyricsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);

        //  create menu system for file operations
        setupMenuSystem();

        // load existing user data efficiently on startup
        UserDataController.loadUserEntries(currentUser);
        refreshViewPanel();                                  // update display with loaded data

        setVisible(true);
    }

    /*
     * Sets up application menu system with file operations and session management
     * Provides efficient access to save and logout functionality
     * Uses lambda expressions for clean event handling
     */
    private void setupMenuSystem() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
//        JMenuItem saveItem = new JMenuItem("Save All");
        JMenuItem logoutItem = new JMenuItem("Logout");
        
        // Save all functionality
//        saveItem.addActionListener(e -> {
//            UserDataController.saveUser(currentUser);        // OUTPUT: Save user data to file
//            JOptionPane.showMessageDialog(this, "Data saved successfully!");
//        });
        
        // logout with confirmation - prevents accidental data loss
        logoutItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", "Logout", 
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                UserDataController.saveUser(currentUser);    // auto-save before logout
                dispose();                                   // close main window
                new LoginRegistrationGUI();                  // return to login screen
            }
        });
        
//        fileMenu.add(saveItem);
//        fileMenu.addSeparator();

        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /*
     * Refreshes the view panel to display updated lyric entries
     * Output: Updates ViewLyricsPanel with current user data
     * 
     * Called after adding new entries or loading saved data
     * 
     * Example: After adding new lyric, call refreshViewPanel() to update display
     */
    public void refreshViewPanel() {
        viewLyricsPanel.refreshEntries();
    }

    /**
     * Switches interface to View Lyrics tab programmatically
     * Output: Updates active tab to show user's lyric collection
     * 
     * Provides efficient navigation after completing actions (like adding lyrics)
     * Improves user workflow by automatically showing results
     * 
     * Example: After successfully adding lyric, switch to view tab to show new entry
     */
    public void switchToViewTab() {
        tabbedPane.setSelectedIndex(1);                      // index 1 = View Lyrics tab
    }
}