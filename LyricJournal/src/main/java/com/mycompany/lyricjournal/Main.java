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

/**
 * Main class serves as the entry point for the LyricJournal desktop application.
 * Follows the standard Java application pattern with a static main method.
 * Initializes the GUI by creating the login/registration screen.
 * 
 * Application Flow:
 * 1. User sees login/registration screen (LoginRegistrationGUI)
 * 2. After successful authentication, main application window opens (LyricJournalMainGUI)
 * 3. User can add, view, edit, and search their lyric entries
 * 
 * Example execution: java -cp target/classes com.mycompany.lyricjournal.Main
 */
public class Main {
    /**
     * Application entry point - launches the GUI application
     * Creates and displays the login/registration window
     * 
     * @param args Command line arguments (input: String array, not used in this application)
     * Output: Opens LoginRegistrationGUI window for user interaction
     * 
     * Example: Main.main(new String[]{}) launches the application
     */
    public static void main(String[] args) {
        // Launch the login/registration screen as the initial GUI component
        // LoginRegistrationGUI constructor handles window creation and display
        // After successful login, LoginRegistrationGUI will create LyricJournalMainGUI
        new LoginRegistrationGUI();
        
        // Note: Application continues running via GUI event dispatch thread
        // Main thread completes here, but application stays alive due to GUI components
    }
}