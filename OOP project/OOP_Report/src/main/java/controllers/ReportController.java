// ReportController.java (controllers package)
package controllers;

import models.ReportModel;
import views.ReportView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ReportController {
    private ReportModel model;
    private Connection connection;

    public ReportController() {
        this.model = new ReportModel();
    }

    public void connectToDatabase() {
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/OOP_Report";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle connection failure
        }
    }

    public void disconnectFromDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle disconnection failure
        }
    }

    public double getTotalSales(String year, int month) {
        try {
            return model.fetchTotalSales(connection, year, month);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle fetching total sales failure
            return 0.0;
        }
    }

    public double getTotalExpenses(String year, int month) {
        try {
            return model.fetchTotalExpenses(connection, year, month);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle fetching total expenses failure
            return 0.0;
        }
    }

    public double calculateProfit(double totalSales, double totalExpenses) {
        return model.calculateProfit(totalSales, totalExpenses);
    }

    public void generateMonthlyReport(ReportView view, String year, int month) {
        try {
            double totalSales = getTotalSales(year, month);
            double totalExpenses = getTotalExpenses(year, month);
            double profit = calculateProfit(totalSales, totalExpenses);

            // Build the report string
            StringBuilder report = new StringBuilder();
            report.append("Monthly Report for ").append(view.getMonthName(month)).append(" ").append(year).append(":\n\n");
            report.append("Total Sales: $").append(totalSales).append("\n");
            report.append("Total Expenses: $").append(totalExpenses).append("\n");
            report.append("Profit: $").append(profit).append("\n");

            // Adding a label to indicate profit or loss
            String profitLabel = (profit >= 0) ? "Profit: $" + profit : "Loss: $" + Math.abs(profit);

            view.updateReportTextArea(report.toString(), profitLabel);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
