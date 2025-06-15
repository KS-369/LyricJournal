/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal.View;

/**
 *
 * @author Kalli-Ann
 */

// LoginRegistrationGUI - Main entry point for user authentication

// Provides login/registration interface with input validation
// Implements user-friendly mode switching between login and registration


import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main authentication window class
 * Efficiently manages login/register using single form
 * Implements secure password handling and comprehensive input validation
 */
public class LoginRegistrationGUI extends JFrame {
    // Input: User interface components for credential entry
    private JTextField usernameField; // single-line input for username
    private JPasswordField passwordField; // secure password input with masking
    private JButton loginButton, registerButton, switchModeButton; // action buttons
    private JLabel titleLabel; // dynamic title that changes with mode
    
    // mode tracking using single boolean
    private boolean isLoginMode = true; // True = login, False = register

    /*
     * Constructor: Creates and displays the authentication window
     * Sets up dual-mode interface and handles first-time user experience
     * 
     * Example usage:
     * new LoginRegistrationGUI(); // displays login window and handles all user interaction
     */
    public LoginRegistrationGUI() {
        setTitle("LyricJournal - Login");
        setSize(400, 300);                         
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());           

        // create main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        // dynamic title that updates based on current mode
        titleLabel = new JLabel("Welcome to LyricJournal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        // username input field with label
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        usernameField = new JTextField(20); // Input: username entry
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        // secure password input field
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        passwordField = new JPasswordField(20); // Input: secure password entry
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        //action buttons panel - efficiently shows/hides based on mode
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        switchModeButton = new JButton("Need to register?");
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        mainPanel.add(buttonPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(switchModeButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // initialize UI for login mode
        updateUIForMode();

        // event handling with method references
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        switchModeButton.addActionListener(e -> switchMode());

        // enter key handling for better user experience - submits current form
        passwordField.addActionListener(e -> {
            if (isLoginMode) handleLogin();
            else handleRegister();
        });

        // first-time user experience - shows welcome message and switches to register
        if (UserManager.isFirstRun()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Welcome to LyricJournal!\n\n" +
                    "This appears to be your first time using the application.\n" +
                    "Please register a new account to get started.\n\n" +
                    "Your lyrics and notes will be saved securely on your device.",
                    "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                switchToRegisterMode();
            });
        }

        setVisible(true);
    }

    /*
     * Switches interface to registration mode
     * Efficiently updates UI state and clears sensitive data
     */
    private void switchToRegisterMode() {
        if (isLoginMode) {
            isLoginMode = false;
            updateUIForMode();
            clearSensitiveFields(); // security: clear existing input
            usernameField.requestFocus();
        }
    }

    /*
     * Updates UI components based on current mode (login/register)
     * Efficiently manages button visibility and text without recreating components
     * 
     * single method to handle both modes
     */
    private void updateUIForMode() {
        if (isLoginMode) {
            // configure for login mode
            setTitle("LyricJournal - Login");
            titleLabel.setText("Login to LyricJournal");
            loginButton.setVisible(true);
            registerButton.setVisible(false);
            switchModeButton.setText("Need to register?");
        } else {
            // configure for registration mode
            setTitle("LyricJournal - Register");
            titleLabel.setText("Register for LyricJournal");
            loginButton.setVisible(false);
            registerButton.setVisible(true);
            switchModeButton.setText("Already have an account?");
        }
    }

    /**
     * Toggles between login and registration modes
     * Efficiently clears sensitive data when switching modes
     */
    private void switchMode() {
        isLoginMode = !isLoginMode;         
        updateUIForMode();
        clearSensitiveFields(); // security: clear when mode changes
    }

    /**
     * Clears username and password fields for security
     */
    private void clearSensitiveFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    /*
     * Handles user login attempt with comprehensive validation
     * Input: Username and password from form fields
     * Output: Either launches main application or shows error message
     * 
     * Example flow:
     * User enters "john" and "password123" -> validates -> launches app if valid
     */
    private void handleLogin() {
        // Input: Extract credentials safely
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // validate input completeness
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // authenticate using secure UserManager validation
        if (UserManager.validateLogin(username, password)) {
            // Output: Successful login - create user and launch main app
            User user = new User(username, password);
            UserDataController.loadUserEntries(user); // load existing user data
            
            new LyricJournalMainGUI(user); // launch main application
            dispose(); // close login window
        } else {
            // Output: Login failed - show error and clear sensitive data
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password. Please try again.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText(""); // security: clear password
            passwordField.requestFocus();
        }
    }

    /*
     * Handles new user registration with comprehensive validation
     * Input: Username and password from form fields
     * Output: Either creates account and launches app, or shows validation errors
     * 
     * Implements efficient validation rules:
     * - Username minimum 3 characters
     * - Password minimum 4 characters  
     * - Username uniqueness check
     * 
     * Example: User enters "newuser" and "pass123" -> validates -> creates account
     */
    private void handleRegister() {
        // Input: Extract and clean user input
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // validate input completeness
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // validate username length - prevents database issues
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username must be at least 3 characters long.", 
                "Invalid Username", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // validate password strength - basic security requirement
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters long.", 
                "Invalid Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // check username availability - efficient uniqueness validation
        if (UserManager.userExists(username)) {
            JOptionPane.showMessageDialog(this, 
                "Username '" + username + "' is already taken.\nPlease choose a different username.", 
                "Username Already Taken", JOptionPane.WARNING_MESSAGE);
            usernameField.selectAll(); // Efficient text selection for re-entry
            usernameField.requestFocus();
            return;
        }

        // attempt user registration
        if (UserManager.registerUser(username, password)) {
            // Output: Successful registration
            JOptionPane.showMessageDialog(this, 
                "Registration successful! Welcome to LyricJournal, " + username + "!", 
                "Registration Complete", JOptionPane.INFORMATION_MESSAGE);
            
            // create new user and launch main application
            User user = new User(username, password);
            new LyricJournalMainGUI(user);
            dispose(); // Close login window
        } else {
            // Output: Registration failed
            JOptionPane.showMessageDialog(this, 
                "Registration failed. Please try again.", 
                "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}