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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditLyricDialog extends JDialog {
    private JTextField titleField, artistField;
    private JTextArea lyricArea, noteArea;
    private JButton saveButton, cancelButton;
    private boolean confirmed = false;
    private LyricEntry originalEntry;
    private LyricEntry updatedEntry;

    public EditLyricDialog(Frame parent, LyricEntry entry) {
        super(parent, "Edit Lyric Entry", true);
        this.originalEntry = entry;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Song title
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Song Title:"), gbc);
        titleField = new JTextField(entry.getSongTitle(), 25);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);

        // Artist
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel("Artist:"), gbc);
        artistField = new JTextField(entry.getArtist(), 25);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(artistField, gbc);

        // Lyric text
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        inputPanel.add(new JLabel("Lyric Text:"), gbc);
        lyricArea = new JTextArea(entry.getLyricText(), 4, 25);
        lyricArea.setLineWrap(true);
        lyricArea.setWrapStyleWord(true);
        lyricArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane lyricScroll = new JScrollPane(lyricArea);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.5;
        inputPanel.add(lyricScroll, gbc);

        // User note
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;
        inputPanel.add(new JLabel("Your Note:"), gbc);
        noteArea = new JTextArea(entry.getUserNote() != null ? entry.getUserNote() : "", 3, 25);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane noteScroll = new JScrollPane(noteArea);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 0.3;
        inputPanel.add(noteScroll, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Cancel");
        
        saveButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());

        // Close dialog with ESC key
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Focus on title field
        SwingUtilities.invokeLater(() -> titleField.requestFocus());
    }

    private void saveChanges() {
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String lyric = lyricArea.getText().trim();
        String note = noteArea.getText().trim();

        if (title.isEmpty() || artist.isEmpty() || lyric.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in Song Title, Artist, and Lyric Text fields.", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create updated entry preserving the original date
        updatedEntry = new LyricEntry(title, artist, lyric, note, originalEntry.getDateAdded());
        
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public LyricEntry getUpdatedEntry() {
        return updatedEntry;
    }
}