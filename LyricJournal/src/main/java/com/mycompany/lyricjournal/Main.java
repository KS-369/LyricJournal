/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lyricjournal;

/**
 *
 * @author Kalli-Ann
 */

// Main Application Entry Point
 
// Launches the LyricJournal desktop application


import com.mycompany.lyricjournal.View.*;

/*
 * Main class serves as the entry point for the LyricJournal desktop application.
 * Initializes the GUI by creating the login/registration screen.
 * 
 * Application Flow:
 * 1. User sees login/registration screen (LoginRegistrationGUI)
 * 2. After successful authentication, main application window opens (LyricJournalMainGUI)
 * 3. User can add, view, edit, and search their lyric entries
 *
 */
public class Main {
    /*
     * Application entry point - launches the GUI application
     * Creates and displays the login/registration window
     * 
     * Input: args - Command line arguments (input: String array, not used in this application)
     * Output: Opens LoginRegistrationGUI window for user interaction
     * 
     * Example: Main.main(new String[]{}) launches the application
     */
    public static void main(String[] args) {
        // launch the login/registration screen as the initial GUI component
        // LoginRegistrationGUI constructor handles window creation and display
        // after successful login, LoginRegistrationGUI will create LyricJournalMainGUI
        new LoginRegistrationGUI();
        
    }
}