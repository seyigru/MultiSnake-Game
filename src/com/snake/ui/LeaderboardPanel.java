package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Milestone 2 — leaderboard screen opened from the main menu.
// Ekene's Leaderboard class already stores and sorts entries; this panel only reads
// getEntries() and paints rows so the player can see rank, name, score, and difficulty.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.snake.model.Leaderboard;

/**
 * Shows every stored score in a read-only table. 
 */
public class LeaderboardPanel extends JPanel {

    private static final String[] COLUMNS = {"Rank", "Name", "Score", "Difficulty"};

    private final Leaderboard leaderboard;
    private final DefaultTableModel tableModel;


    public LeaderboardPanel(Leaderboard leaderboard, Runnable onBack) {
        this.leaderboard = leaderboard;
        this.tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Table is display-only; editing would not write back to Leaderboard anyway.
                return false;
            }
        };

        setLayout(new BorderLayout(8, 8));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("Leaderboard");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.GREEN);
        add(title, BorderLayout.NORTH);

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(0x1a, 0x1a, 0x2e));
        table.setGridColor(new Color(0x3d, 0x6b, 0x34));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0x1a, 0x1a, 0x2e));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(0x1a, 0x1a, 0x2e));
        scroll.setPreferredSize(new Dimension(520, 280));
        add(scroll, BorderLayout.CENTER);

        if (onBack != null) {
            JButton back = new JButton("Back to menu");
            back.setFont(new Font("Arial", Font.PLAIN, 16));
            back.addActionListener(e -> onBack.run());
            JPanel south = new JPanel();
            south.setBackground(Color.BLACK);
            south.add(back);
            add(south, BorderLayout.SOUTH);
        }

        refresh();
    }

    /**
     * Re-reads {@link Leaderboard#getEntries()} and rebuilds rows. Rank is just row order (1, 2, 3…)
     * because the list is already sorted descending by score inside Leaderboard.addEntry.
     */
    public void refresh() {
        tableModel.setRowCount(0);
        List<Leaderboard.Entry> entries = leaderboard.getEntries();
        int rank = 1;
        for (Leaderboard.Entry e : entries) {
            tableModel.addRow(new Object[]{rank++, e.name, e.score, e.difficulty});
        }
    }
}
