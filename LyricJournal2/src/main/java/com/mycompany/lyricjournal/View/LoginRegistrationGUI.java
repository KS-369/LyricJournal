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
import com.mycompany.lyricjournal.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginRegistrationGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, switchModeButton;
    private JLabel titleLabel;
    private boolean isLoginMode = true;

    public LoginRegistrationGUI() {
        setTitle("LyricJournal - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        titleLabel = new JLabel("Welcome to LyricJournal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        // Username field
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        // Password field
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        // Buttons panel
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

        // Initially show login mode
        updateUIForMode();

        // Button actions
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        switchModeButton.addActionListener(e -> switchMode());

        // Enter key handling
        passwordField.addActionListener(e -> {
            if (isLoginMode) handleLogin();
            else handleRegister();
        });

        // Show first-time user message if this is the first run
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

    private void switchToRegisterMode() {
        if (isLoginMode) {
            isLoginMode = false;
            updateUIForMode();
            usernameField.setText("");
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }

    private void updateUIForMode() {
        if (isLoginMode) {
            setTitle("LyricJournal - Login");
            titleLabel.setText("Login to LyricJournal");
            loginButton.setVisible(true);
            registerButton.setVisible(false);
            switchModeButton.setText("Need to register?");
        } else {
            setTitle("LyricJournal - Register");
            titleLabel.setText("Register for LyricJournal");
            loginButton.setVisible(false);
            registerButton.setVisible(true);
            switchModeButton.setText("Already have an account?");
        }
    }

    private void switchMode() {
        isLoginMode = !isLoginMode;
        updateUIForMode();
        usernameField.setText("");
        passwordField.setText("");
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate credentials using UserManager
        if (UserManager.validateLogin(username, password)) {
            User user = new User(username, password);
            UserDataController.loadUserEntries(user);
            
            // Launch main application
            new LyricJournalMainGUI(user);
            dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password. Please try again.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText(""); // Clear password field
            passwordField.requestFocus();
        }
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username must be at least 3 characters long.", 
                "Invalid Username", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters long.", 
                "Invalid Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if username is already taken
        if (UserManager.userExists(username)) {
            JOptionPane.showMessageDialog(this, 
                "Username '" + username + "' is already taken.\nPlease choose a different username.", 
                "Username Already Taken", JOptionPane.WARNING_MESSAGE);
            usernameField.selectAll();
            usernameField.requestFocus();
            return;
        }

        // Register the new user
        if (UserManager.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, 
                "Registration successful! Welcome to LyricJournal, " + username + "!", 
                "Registration Complete", JOptionPane.INFORMATION_MESSAGE);
            
            // Create new user and launch main application
            User user = new User(username, password);
            new LyricJournalMainGUI(user);
            dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(this, 
                "Registration failed. Please try again.", 
                "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
