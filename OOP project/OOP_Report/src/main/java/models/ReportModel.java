// ReportModel.java (models package)
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportModel {
    public double fetchTotalSales(Connection connection, String year, int month) throws Exception {
        String sql = "SELECT total_price FROM customers WHERE YEAR(order_date) = ? AND MONTH(order_date) = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, year);
            preparedStatement.setInt(2, month);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                double totalSales = 0.0;
                while (resultSet.next()) {
                    totalSales += resultSet.getDouble("total_price");
                }
                return totalSales;
            }
        }
    }

    public double fetchTotalExpenses(Connection connection, String year, int month) throws Exception {
        String sql = "SELECT amount FROM suppliers WHERE YEAR(supplier_date) = ? AND MONTH(supplier_date) = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, year);
            preparedStatement.setInt(2, month);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                double totalExpenses = 0.0;
                while (resultSet.next()) {
                    totalExpenses += resultSet.getDouble("amount");
                }
                return totalExpenses;
            }
        }
    }

    public double calculateProfit(double totalSales, double totalExpenses) {
        return totalSales - totalExpenses;
    }
}
