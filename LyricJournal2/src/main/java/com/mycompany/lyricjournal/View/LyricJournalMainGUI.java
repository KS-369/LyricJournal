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

public class LyricJournalMainGUI extends JFrame {
    private User currentUser;
    private JTabbedPane tabbedPane;
    private AddLyricPanel addLyricPanel;
    private ViewLyricsPanel viewLyricsPanel;

    public LyricJournalMainGUI(User user) {
        this.currentUser = user;
        setTitle("LyricJournal - " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create panels
        addLyricPanel = new AddLyricPanel(currentUser, this);
        viewLyricsPanel = new ViewLyricsPanel(currentUser);
        
        // Add tabs
        tabbedPane.addTab("Add Lyric", addLyricPanel);
        tabbedPane.addTab("View Lyrics", viewLyricsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save All");
        JMenuItem logoutItem = new JMenuItem("Logout");
        
        saveItem.addActionListener(e -> {
            UserDataController.saveUser(currentUser);
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        });
        
        logoutItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", "Logout", 
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                UserDataController.saveUser(currentUser);
                dispose();
                new LoginRegistrationGUI();
            }
        });
        
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Load existing entries
        UserDataController.loadUserEntries(currentUser);
        refreshViewPanel();

        setVisible(true);
    }

    public void refreshViewPanel() {
        viewLyricsPanel.refreshEntries();
    }

    public void switchToViewTab() {
        tabbedPane.setSelectedIndex(1);
    }
}