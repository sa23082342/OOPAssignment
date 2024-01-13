// ReportView.java (views package)
package views;

import controllers.ReportController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class ReportView extends JFrame {
    private JTextArea reportTextArea;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JPanel buttonPanel;
    private ReportController controller;

    public ReportView(ReportController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Monthly Report Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel monthlyReportPanel = new JPanel(new BorderLayout());
        monthlyReportPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel selectionPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        yearComboBox = new JComboBox<>(getYears());
        monthComboBox = new JComboBox<>(getMonths());

        selectionPanel.add(new JLabel("Year:"));
        selectionPanel.add(yearComboBox);
        selectionPanel.add(new JLabel("Month:"));
        selectionPanel.add(monthComboBox);

        monthlyReportPanel.add(selectionPanel, BorderLayout.NORTH);

        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(reportTextArea);

        monthlyReportPanel.add(scrollPane, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton generateReportButton = createStyledButton("Generate Monthly Report");
        JButton printReportButton = createStyledButton("Print Report");
        JButton saveAsPdfButton = createStyledButton("Save as PDF");

        buttonPanel.add(generateReportButton);
        buttonPanel.add(printReportButton);
        buttonPanel.add(saveAsPdfButton);

        monthlyReportPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(monthlyReportPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GradientPaint gradient = new GradientPaint(0, 0, new Color(52, 152, 219), 0, button.getHeight(), new Color(41, 128, 185));
        button.setOpaque(true);
        button.setBackground(new Color(52, 152, 219));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(31, 97, 141), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });
        return button;
    }

    private void handleButtonClick(String buttonName) {
        switch (buttonName) {
            case "Generate Monthly Report":
                String selectedYear = (String) yearComboBox.getSelectedItem();
                int selectedMonth = monthComboBox.getSelectedIndex() + 1;
                controller.generateMonthlyReport(this, selectedYear, selectedMonth);
                break;
            case "Print Report":
                printReport();
                break;
            case "Save as PDF":
                saveReportAsPdf();
                break;
        }
    }

    private void printReport() {
        try {
            reportTextArea.print();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while printing the report.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveReportAsPdf() {
        try {
            // ... (unchanged code for saving as PDF)
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while saving report as PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getMonthName(int monthIndex) {
        String[] months = getMonths();
        return months[monthIndex - 1];
    }

    public void updateReportTextArea(String report, String profitLabel) {
        reportTextArea.setText("");
        reportTextArea.append(profitLabel + "\n\n");
        reportTextArea.append(report);
    }

    private String[] getYears() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[10];
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        return years;
    }

    private String[] getMonths() {
        String[] months = new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return months;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReportController controller = new ReportController();
            ReportView reportView = new ReportView(controller);
            controller.connectToDatabase(); // Connect to the database
            // Additional initialization or setup if needed
            reportView.setVisible(true);
        });
    }
}
