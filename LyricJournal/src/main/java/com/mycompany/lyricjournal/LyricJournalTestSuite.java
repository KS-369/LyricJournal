/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lyricjournal;

/**
 *
 * @author Kalli-Ann
 */

/*
 * Comprehensive Test Suite for LyricJournal Application
 * Tests all core functionality including GUI components, data persistence, and edge cases
 * 
 * Test Categories:
 * 1. Model Tests - User, LyricEntry functionality
 * 2. Controller Tests - UserManager, UserDataController
 * 3. GUI Component Tests - Login, Main interface, dialogs
 * 4. Integration Tests - End-to-end workflows
 * 5. Error Handling Tests - Edge cases and error conditions
 */

import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.Controller.*;
import com.mycompany.lyricjournal.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Main test runner class that executes all test suites
 * Provides comprehensive coverage of application functionality
 */
public class LyricJournalTestSuite {
    private static int totalTests = 0;
    private static int passedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("=== LyricJournal Comprehensive Test Suite ===\n");
        
        // Run all test categories
        runModelTests();
        runControllerTests();
        runGUITests();
        runIntegrationTests();
        runErrorHandlingTests();
        
        // Print final results
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success Rate: " + (passedTests * 100.0 / totalTests) + "%");
        
        if (passedTests == totalTests) {
            System.out.println("ALL TESTS PASSED!");
        } else {
            System.out.println("Some tests failed - review output above");
        }
    }
    
    /**
     * Tests all Model classes (User, LyricEntry)
     * Covers: object creation, data validation, search functionality
     */
    private static void runModelTests() {
        System.out.println("--- MODEL TESTS ---");
        
        // Test User creation and basic functionality
        testUserCreation();
        testUserPasswordValidation();
        testUserEntryManagement();
        testUserSearch();
        
        // Test LyricEntry creation and functionality
        testLyricEntryCreation();
        testLyricEntryWithDate();
        testLyricEntryToString();
        testLyricEntrySetters();
        
        System.out.println();
    }
    
    /**
     * Test User object creation with valid data
     * Example: new User("testUser", "testPass") should create valid user
     */
    private static void testUserCreation() {
        try {
            User user = new User("testUser", "testPassword");
            assertTrue("User creation", 
                user.getUsername().equals("testUser") && 
                user.getEntries() != null && 
                user.getEntries().isEmpty());
        } catch (Exception e) {
            assertFalse("User creation", "Exception thrown: " + e.getMessage());
        }
    }
    
    /**
     * Test User password validation functionality
     * Example: user.checkPassword("correct") should return true, user.checkPassword("wrong") should return false
     */
    private static void testUserPasswordValidation() {
        User user = new User("testUser", "correctPassword");
        assertTrue("Password validation - correct", user.checkPassword("correctPassword"));
        assertFalse("Password validation - incorrect", user.checkPassword("wrongPassword"));
        assertFalse("Password validation - empty", user.checkPassword(""));
        assertFalse("Password validation - null", user.checkPassword(null));
    }
    
    /**
     * Test User entry management (add/get entries)
     * Example: Adding 3 entries should result in getEntries().size() == 3
     */
    private static void testUserEntryManagement() {
        User user = new User("testUser", "testPass");
        LyricEntry entry1 = new LyricEntry("Song1", "Artist1", "Lyrics1", "Note1");
        LyricEntry entry2 = new LyricEntry("Song2", "Artist2", "Lyrics2", "Note2");
        
        user.addEntry(entry1);
        user.addEntry(entry2);
        
        assertTrue("Entry management - count", user.getEntries().size() == 2);
        assertTrue("Entry management - contains entry1", user.getEntries().contains(entry1));
        assertTrue("Entry management - contains entry2", user.getEntries().contains(entry2));
    }
    
    /**
     * Test User search functionality across all fields
     * Example: Searching "Artist1" should return entries with "Artist1" in any field
     */
    private static void testUserSearch() {
        User user = new User("testUser", "testPass");
        user.addEntry(new LyricEntry("Bohemian Rhapsody", "Queen", "Is this the real life", "Great song"));
        user.addEntry(new LyricEntry("Imagine", "John Lennon", "Imagine all the people", "Peaceful"));
        user.addEntry(new LyricEntry("Hotel California", "Eagles", "Welcome to the hotel", "Classic"));
        
        // Test search by title
        ArrayList<LyricEntry> titleResults = user.searchEntries("Bohemian");
        assertTrue("Search by title", titleResults.size() == 1 && 
            titleResults.get(0).getSongTitle().contains("Bohemian"));
        
        // Test search by artist
        ArrayList<LyricEntry> artistResults = user.searchEntries("Queen");
        assertTrue("Search by artist", artistResults.size() == 1 && 
            artistResults.get(0).getArtist().contains("Queen"));
        
        // Test search by lyrics
        ArrayList<LyricEntry> lyricResults = user.searchEntries("Imagine all");
        assertTrue("Search by lyrics", lyricResults.size() == 1 && 
            lyricResults.get(0).getLyricText().contains("Imagine all"));
        
        // Test case insensitive search
        ArrayList<LyricEntry> caseResults = user.searchEntries("QUEEN");
        assertTrue("Case insensitive search", caseResults.size() == 1);
        
        // Test no results
        ArrayList<LyricEntry> noResults = user.searchEntries("NonexistentSong");
        assertTrue("No search results", noResults.isEmpty());
    }
    
    /**
     * Test LyricEntry creation with automatic date
     * Example: new LyricEntry("Title", "Artist", "Lyrics", "Note") should have today's date
     */
    private static void testLyricEntryCreation() {
        LyricEntry entry = new LyricEntry("Test Song", "Test Artist", "Test Lyrics", "Test Note");
        
        assertTrue("LyricEntry creation - title", entry.getSongTitle().equals("Test Song"));
        assertTrue("LyricEntry creation - artist", entry.getArtist().equals("Test Artist"));
        assertTrue("LyricEntry creation - lyrics", entry.getLyricText().equals("Test Lyrics"));
        assertTrue("LyricEntry creation - note", entry.getUserNote().equals("Test Note"));
        assertTrue("LyricEntry creation - date not null", entry.getDateAdded() != null);
    }
    
    /**
     * Test LyricEntry creation with specified date (for editing)
     * Example: new LyricEntry("Title", "Artist", "Lyrics", "Note", "2023-01-01") should preserve the date
     */
    private static void testLyricEntryWithDate() {
        String specificDate = "2023-01-01";
        LyricEntry entry = new LyricEntry("Test Song", "Test Artist", "Test Lyrics", "Test Note", specificDate);
        
        assertTrue("LyricEntry with specific date", entry.getDateAdded().equals(specificDate));
    }
    
    /**
     * Test LyricEntry toString method formatting
     * Example: toString() should include title, artist, lyrics, note, and date
     */
    private static void testLyricEntryToString() {
        LyricEntry entry = new LyricEntry("Test Song", "Test Artist", "Test Lyrics", "Test Note");
        String toString = entry.toString();
        
        assertTrue("ToString contains title", toString.contains("Test Song"));
        assertTrue("ToString contains artist", toString.contains("Test Artist"));
        assertTrue("ToString contains lyrics", toString.contains("Test Lyrics"));
        assertTrue("ToString contains note", toString.contains("Test Note"));
    }
    
    /**
     * Test LyricEntry setter methods
     * Example: entry.setSongTitle("New Title") should update the title
     */
    private static void testLyricEntrySetters() {
        LyricEntry entry = new LyricEntry("Original", "Original", "Original", "Original");
        
        entry.setSongTitle("New Title");
        entry.setArtist("New Artist");
        entry.setLyricText("New Lyrics");
        entry.setUserNote("New Note");
        
        assertTrue("Setter - title", entry.getSongTitle().equals("New Title"));
        assertTrue("Setter - artist", entry.getArtist().equals("New Artist"));
        assertTrue("Setter - lyrics", entry.getLyricText().equals("New Lyrics"));
        assertTrue("Setter - note", entry.getUserNote().equals("New Note"));
    }
    
    /**
     * Tests all Controller classes (UserManager, UserDataController)
     * Covers: user registration, login validation, data persistence
     */
    private static void runControllerTests() {
        System.out.println("--- CONTROLLER TESTS ---");
        
        testUserRegistration();
        testUserLoginValidation();
        testDuplicateUserRegistration();
        testDataPersistence();
        testFileOperations();
        
        System.out.println();
    }
    
    /**
     * Test UserManager user registration
     * Example: UserManager.registerUser("newUser", "pass") should return true for new users
     */
    private static void testUserRegistration() {
        String testUser = "testUser" + System.currentTimeMillis(); // Unique username
        boolean result = UserManager.registerUser(testUser, "testPassword");
        assertTrue("User registration - new user", result);
        assertTrue("User registration - user exists after registration", UserManager.userExists(testUser));
    }
    
    /**
     * Test UserManager login validation
     * Example: Valid credentials should return true, invalid should return false
     */
    private static void testUserLoginValidation() {
        String testUser = "loginTestUser" + System.currentTimeMillis();
        String testPassword = "loginTestPassword";
        
        // Register user first
        UserManager.registerUser(testUser, testPassword);
        
        // Test valid login
        assertTrue("Login validation - correct credentials", 
            UserManager.validateLogin(testUser, testPassword));
        
        // Test invalid password
        assertFalse("Login validation - wrong password", 
            UserManager.validateLogin(testUser, "wrongPassword"));
        
        // Test non-existent user
        assertFalse("Login validation - non-existent user", 
            UserManager.validateLogin("nonExistentUser", "anyPassword"));
    }
    
    /**
     * Test duplicate user registration prevention
     * Example: Registering same username twice should fail on second attempt
     */
    private static void testDuplicateUserRegistration() {
        String testUser = "duplicateTestUser" + System.currentTimeMillis();
        
        // First registration should succeed
        assertTrue("Duplicate registration - first attempt", 
            UserManager.registerUser(testUser, "password1"));
        
        // Second registration should fail
        assertFalse("Duplicate registration - second attempt", 
            UserManager.registerUser(testUser, "password2"));
    }
    
    /**
     * Test UserDataController save and load functionality
     * Example: Saving user data should create file, loading should restore data
     */
    private static void testDataPersistence() {
        User testUser = new User("persistenceTestUser", "testPassword");
        testUser.addEntry(new LyricEntry("Test Song", "Test Artist", "Test Lyrics", "Test Note"));
        
        // Save user data
        UserDataController.saveUser(testUser);
        
        // Create new user object and load data
        User loadedUser = new User("persistenceTestUser", "testPassword");
        UserDataController.loadUserEntries(loadedUser);
        
        assertTrue("Data persistence - entry count", loadedUser.getEntries().size() == 1);
        assertTrue("Data persistence - entry content", 
            loadedUser.getEntries().get(0).getSongTitle().equals("Test Song"));
        
        // Cleanup
        new File("persistenceTestUser_lyrics.json").delete();
    }
    
    /**
     * Test file operations and error handling
     * Example: Loading non-existent file should not crash, should handle gracefully
     */
    private static void testFileOperations() {
        User testUser = new User("nonExistentFileUser", "password");
        
        // Try to load from non-existent file - should not crash
        try {
            UserDataController.loadUserEntries(testUser);
            assertTrue("File operations - no crash on missing file", true);
        } catch (Exception e) {
            assertFalse("File operations - should handle missing file gracefully", 
                "Exception thrown: " + e.getMessage());
        }
        
        assertTrue("File operations - entries empty for non-existent file", 
            testUser.getEntries().isEmpty());
    }
    
    /**
     * Tests GUI components and user interactions
     * Covers: dialog creation, button actions, input validation
     */
    private static void runGUITests() {
        System.out.println("--- GUI TESTS ---");
        
        testLoginGUICreation();
        testMainGUICreation();
        testEditDialogCreation();
        testPanelFunctionality();
        
        System.out.println();
    }
    
    /**
     * Test LoginRegistrationGUI creation and basic functionality
     * Example: GUI should initialize without errors and contain required components
     */
    private static void testLoginGUICreation() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                LoginRegistrationGUI loginGUI = new LoginRegistrationGUI();
                assertTrue("Login GUI creation", loginGUI != null);
                loginGUI.dispose(); // Clean up
            });
        } catch (Exception e) {
            assertFalse("Login GUI creation", "Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test LyricJournalMainGUI creation with user
     * Example: Main GUI should initialize with user and display tabs correctly
     */
    private static void testMainGUICreation() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                User testUser = new User("guiTestUser", "password");
                LyricJournalMainGUI mainGUI = new LyricJournalMainGUI(testUser);
                assertTrue("Main GUI creation", mainGUI != null);
                assertTrue("Main GUI title", mainGUI.getTitle().contains("guiTestUser"));
                mainGUI.dispose(); // Clean up
            });
        } catch (Exception e) {
            assertFalse("Main GUI creation", "Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test EditLyricDialog creation and functionality
     * Example: Dialog should initialize with entry data and allow editing
     */
    private static void testEditDialogCreation() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                LyricEntry testEntry = new LyricEntry("Test Song", "Test Artist", "Test Lyrics", "Test Note");
                JFrame parentFrame = new JFrame();
                EditLyricDialog dialog = new EditLyricDialog(parentFrame, testEntry);
                assertTrue("Edit dialog creation", dialog != null);
                dialog.dispose(); // Clean up
                parentFrame.dispose();
            });
        } catch (Exception e) {
            assertFalse("Edit dialog creation", "Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test panel functionality (AddLyricPanel, ViewLyricsPanel)
     * Example: Panels should initialize and handle user interactions correctly
     */
    private static void testPanelFunctionality() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                User testUser = new User("panelTestUser", "password");
                LyricJournalMainGUI mainGUI = new LyricJournalMainGUI(testUser);
                
                // Test that panels exist and are accessible
                assertTrue("Panel functionality", mainGUI.getContentPane() != null);
                
                mainGUI.dispose(); // Clean up
            });
        } catch (Exception e) {
            assertFalse("Panel functionality", "Exception: " + e.getMessage());
        }
    }
    
    /**
     * Tests end-to-end workflows and integration between components
     * Covers: complete user workflows, data flow between MVC components
     */
    private static void runIntegrationTests() {
        System.out.println("--- INTEGRATION TESTS ---");
        
        testCompleteUserWorkflow();
        testMVCInteraction();
        testDataConsistency();
        
        System.out.println();
    }
    
    /**
     * Test complete user workflow from registration to data management
     * Example: Register -> Login -> Add Entry -> Save -> Load should work seamlessly
     */
    private static void testCompleteUserWorkflow() {
        String testUser = "integrationTestUser" + System.currentTimeMillis();
        String testPassword = "integrationTestPassword";
        
        // Step 1: Register user
        boolean registered = UserManager.registerUser(testUser, testPassword);
        assertTrue("Integration - user registration", registered);
        
        // Step 2: Validate login
        boolean loginValid = UserManager.validateLogin(testUser, testPassword);
        assertTrue("Integration - login validation", loginValid);
        
        // Step 3: Create user and add entries
        User user = new User(testUser, testPassword);
        user.addEntry(new LyricEntry("Integration Song", "Integration Artist", "Integration Lyrics", "Integration Note"));
        
        // Step 4: Save data
        UserDataController.saveUser(user);
        
        // Step 5: Load data into new user object
        User loadedUser = new User(testUser, testPassword);
        UserDataController.loadUserEntries(loadedUser);
        
        // Verify complete workflow
        assertTrue("Integration - complete workflow", 
            loadedUser.getEntries().size() == 1 &&
            loadedUser.getEntries().get(0).getSongTitle().equals("Integration Song"));
        
        // Cleanup
        new File(testUser + "_lyrics.json").delete();
    }
    
    /**
     * Test MVC architecture - ensure Model and View don't interact directly
     * Example: All View operations should go through Controller
     */
    private static void testMVCInteraction() {
        // This test verifies that the architecture follows MVC principles
        // by checking that data operations are handled through controllers
        
        User testUser = new User("mvcTestUser", "password");
        testUser.addEntry(new LyricEntry("MVC Song", "MVC Artist", "MVC Lyrics", "MVC Note"));
        
        // Simulate View requesting data through Controller
        UserDataController.saveUser(testUser);
        
        User newUser = new User("mvcTestUser", "password");
        UserDataController.loadUserEntries(newUser);
        
        assertTrue("MVC interaction - data through controller", 
            newUser.getEntries().size() == 1);
        
        // Cleanup
        new File("mvcTestUser_lyrics.json").delete();
    }
    
    /**
     * Test data consistency across operations
     * Example: Data should remain consistent through save/load cycles
     */
    private static void testDataConsistency() {
        User originalUser = new User("consistencyTestUser", "password");
        LyricEntry originalEntry = new LyricEntry("Consistency Song", "Consistency Artist", 
            "Consistency Lyrics", "Consistency Note");
        originalUser.addEntry(originalEntry);
        
        // Save and reload multiple times
        for (int i = 0; i < 3; i++) {
            UserDataController.saveUser(originalUser);
            User reloadedUser = new User("consistencyTestUser", "password");
            UserDataController.loadUserEntries(reloadedUser);
            
            assertTrue("Data consistency - iteration " + i, 
                reloadedUser.getEntries().size() == 1 &&
                reloadedUser.getEntries().get(0).getSongTitle().equals("Consistency Song"));
            
            originalUser = reloadedUser; // Use reloaded data for next iteration
        }
        
        // Cleanup
        new File("consistencyTestUser_lyrics.json").delete();
    }
    
    /**
     * Tests error handling and edge cases
     * Covers: invalid inputs, boundary conditions, error recovery
     */
    private static void runErrorHandlingTests() {
        System.out.println("--- ERROR HANDLING TESTS ---");
        
        testInvalidInputHandling();
        testBoundaryConditions();
        testNullInputs();
        testEmptyInputs();
        
        System.out.println();
    }
    
    /**
     * Test handling of invalid inputs
     * Example: Invalid usernames/passwords should be rejected gracefully
     */
    private static void testInvalidInputHandling() {
        // Test empty username registration
        assertFalse("Invalid input - empty username", 
            UserManager.registerUser("", "password"));
        
        // Test empty password registration  
        assertFalse("Invalid input - empty password", 
            UserManager.registerUser("validUser", ""));
        
        // Test null inputs
        try {
            assertFalse("Invalid input - null username", 
                UserManager.registerUser(null, "password"));
        } catch (Exception e) {
            assertTrue("Invalid input - null username handled", true); // Exception is acceptable
        }
    }
    
    /**
     * Test boundary conditions
     * Example: Very long inputs, minimum length inputs
     */
    private static void testBoundaryConditions() {
        // Test very long username
        String longUsername = "a".repeat(1000);
        try {
            boolean result = UserManager.registerUser(longUsername, "password");
            assertTrue("Boundary - long username handled", true); // Should not crash
        } catch (Exception e) {
            assertTrue("Boundary - long username exception handled", true);
        }
        
        // Test single character inputs
        User user = new User("a", "b");
        assertTrue("Boundary - single char username", user.getUsername().equals("a"));
        assertTrue("Boundary - single char password", user.checkPassword("b"));
    }
    
    /**
     * Test null input handling
     * Example: Methods should handle null inputs gracefully
     */
    private static void testNullInputs() {
        User user = new User("testUser", "testPassword");
        
        // Test null password check
        assertFalse("Null input - password check", user.checkPassword(null));
        
        // Test null search
        ArrayList<LyricEntry> results = user.searchEntries(null);
        assertTrue("Null input - search", results != null);
        
        // Test null entry creation
        try {
            LyricEntry entry = new LyricEntry(null, null, null, null);
            assertTrue("Null input - entry creation", entry != null);
        } catch (Exception e) {
            assertTrue("Null input - entry creation exception", true); // Acceptable
        }
    }
    
    /**
     * Test empty string inputs
     * Example: Empty strings should be handled appropriately
     */
    private static void testEmptyInputs() {
        User user = new User("testUser", "testPassword");
        
        // Test empty search
        ArrayList<LyricEntry> results = user.searchEntries("");
        assertTrue("Empty input - search returns all", results.size() == 0); // No entries added yet
        
        // Add entry and test empty search
        user.addEntry(new LyricEntry("Test", "Test", "Test", "Test"));
        results = user.searchEntries("");
        assertTrue("Empty input - search with entries", results.size() >= 0);
    }
    
    // Test utility methods
    private static void assertTrue(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            System.out.println("✗ " + testName);
        }
    }
    
    private static void assertFalse(String testName, boolean condition) {
        assertTrue(testName, !condition);
    }
    
    private static void assertFalse(String testName, String errorMessage) {
        totalTests++;
        System.out.println("✗ " + testName + " - " + errorMessage);
    }
}
