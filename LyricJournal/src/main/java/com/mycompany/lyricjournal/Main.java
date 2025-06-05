/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lyricjournal;

/**
 *
 * @author Kalli-Ann
 */

import com.mycompany.lyricjournal.Model.*;
import com.mycompany.lyricjournal.View.*;
import com.mycompany.lyricjournal.Controller.*;

public class Main {
    public static void main(String[] args) {
        // Temporary login prompt (can be replaced later with a proper login GUI)
        String username = javax.swing.JOptionPane.showInputDialog("Enter username:");
        String password = javax.swing.JOptionPane.showInputDialog("Enter password:");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Login cancelled or invalid.");
            return;
        }

        User user = new User(username, password);
        UserDataController.loadUserEntries(user);
        new LyricJournalGUI(user);  // Launch GUI with the logged-in user
    }
}
