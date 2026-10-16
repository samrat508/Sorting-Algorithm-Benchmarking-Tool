/**
 * File: Report.java
 * Author: Samrat Shakya
 * Course: CMSC451
 * Assignment: PROJECT 1
 * Date: 2/3/26
 *
 * Description:
 * Reads a user-selected data file, computes summary statistics
 * (averages and coefficients of variation), and displays the
 * results in a formatted JTable GUI.
 */


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report {

    public static void main(String[] args) {
        // File chooser
        JFileChooser chooser = new JFileChooser(".");
        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            System.exit(0);
        }

        File selectedFile = chooser.getSelectedFile();
        String filename = selectedFile.getName();

        // Read and process data
        List<Object[]> tableRows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split("\\s+");
                if (tokens.length != 81) { // size + 40 counts + 40 times = 81 tokens
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                long size = Long.parseLong(tokens[0]);

                double[] counts = new double[40];
                double[] times = new double[40];

                for (int i = 0; i < 40; i++) {
                    counts[i] = Double.parseDouble(tokens[1 + 2 * i]);
                    times[i]  = Double.parseDouble(tokens[2 + 2 * i]);
                }

                double avgCount = average(counts);
                double cvCount  = coefficientOfVariation(counts) * 100;
                double avgTime  = average(times);
                double cvTime   = coefficientOfVariation(times) * 100;

                tableRows.add(new Object[]{
                        size,
                        String.format("%.0f", avgCount),
                        String.format("%.2f%%", cvCount),
                        String.format("%.2f", avgTime),
                        String.format("%.2f%%", cvTime)
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error reading file:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // ────────────────────────────────────────────────────────────────
        // Table setup
        String[] columnNames = {
                "Size",
                "Avg Count",
                "Coef Count",
                "Avg Time",
                "Coef Time"
        };

        Object[][] data = tableRows.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Enable solid grid lines everywhere
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setIntercellSpacing(new Dimension(1, 1)); // ensures visible separation

        // Row height for data
        table.setRowHeight(28);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(230, 230, 230));     // light grey
        header.setForeground(Color.BLACK);

        // Custom header renderer: grey bg + solid border + minimal padding + centered
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);

                label.setBackground(new Color(230, 230, 230));
                label.setOpaque(true);

                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),      // solid black border
                        BorderFactory.createEmptyBorder(6, 4, 6, 4)       // minimal padding
                ));
                return label;
            }
        };

        // Apply custom renderer to every header cell
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Center-align data cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // No reordering, auto-resize
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        // Frame
        JFrame frame = new JFrame(filename);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(850, 420);
        frame.setMinimumSize(new Dimension(750, 320));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static double average(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        return values.length > 0 ? sum / values.length : 0;
    }

    private static double coefficientOfVariation(double[] values) {
        if (values.length <= 1) return 0;
        double mean = average(values);
        if (mean == 0) return 0;

        double sumSqDiff = 0;
        for (double v : values) {
            double diff = v - mean;
            sumSqDiff += diff * diff;
        }
        double stdDev = Math.sqrt(sumSqDiff / (values.length - 1));
        return stdDev / mean;
    }
}