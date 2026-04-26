package com.snake.ui;

// By Israel Kayode
// Student Number: 3167486
//
// Milestone 2 - leaderboard screen opened from the main menu.
// Milestone 3 - highlight the current session row and always show a Back button.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.snake.model.Leaderboard;

/**
 * Shows every stored score in a read-only table.
 * Highlights the row that matches the most recently added entry so the
 * player can immediately spot where their game ended up on the board.
 */
public class LeaderboardPanel extends JPanel {

    private static final String[] COLUMNS = {"Rank", "Name", "Score", "Difficulty"};
    private static final Color HIGHLIGHT_BG = new Color(0x4f, 0xc3, 0xf7);
    private static final Color HIGHLIGHT_FG = Color.BLACK;
    private static final Color ROW_BG = new Color(0x1a, 0x1a, 0x2e);

    private final Leaderboard leaderboard;
    private final DefaultTableModel tableModel;
    private final JTable table;

    // tracks which row should be highlighted as "this session's score"
    private int highlightedRow = -1;

    public LeaderboardPanel(Leaderboard leaderboard, Runnable onBack) {
        this.leaderboard = leaderboard;
        this.tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
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

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(Color.WHITE);
        table.setBackground(ROW_BG);
        table.setGridColor(new Color(0x3d, 0x6b, 0x34));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(ROW_BG);
        table.getTableHeader().setForeground(Color.WHITE);

        // custom renderer paints the highlighted row in a distinct colour
        DefaultTableCellRenderer highlightRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (row == highlightedRow) {
                    c.setBackground(HIGHLIGHT_BG);
                    c.setForeground(HIGHLIGHT_FG);
                } else {
                    c.setBackground(ROW_BG);
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };
        for (int i = 0; i < COLUMNS.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(highlightRenderer);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(ROW_BG);
        scroll.setPreferredSize(new Dimension(520, 280));
        add(scroll, BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.setBackground(Color.BLACK);

        JButton clear = new JButton("Clear");
        clear.setFont(new Font("Arial", Font.PLAIN, 16));
        clear.addActionListener(e -> {
            leaderboard.clear();
            highlightedRow = -1;
            refresh();
        });
        south.add(clear);

        // back button always present, no-op if onBack is null so tests can construct without a router
        JButton back = new JButton("Back to menu");
        back.setFont(new Font("Arial", Font.PLAIN, 16));
        back.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
        south.add(back);

        add(south, BorderLayout.SOUTH);

        refresh();
    }

    /**
     * Re-reads Leaderboard.getEntries() and rebuilds rows. Rank is just row order.
     */
    public void refresh() {
        tableModel.setRowCount(0);
        List<Leaderboard.Entry> entries = leaderboard.getEntries();
        int rank = 1;
        for (Leaderboard.Entry e : entries) {
            tableModel.addRow(new Object[]{rank++, e.name, e.score, e.difficulty});
        }
        if (table != null) table.repaint();
    }

    /**
     * Marks one row as the current session score, used to highlight where
     * the player ended up immediately after a game ends.
     *
     * @param name the player name to find
     * @param score the score to match
     */
    public void highlightSession(String name, int score) {
        highlightedRow = -1;
        List<Leaderboard.Entry> entries = leaderboard.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            Leaderboard.Entry e = entries.get(i);
            if (e.name.equals(name) && e.score == score) {
                highlightedRow = i;
                break;
            }
        }
        if (table != null) table.repaint();
    }

    /**
     * Returns which row is currently highlighted, or -1 if none.
     * Exposed for tests so they can verify the highlight logic without a visible window.
     */
    public int getHighlightedRow() {
        return highlightedRow;
    }
}
