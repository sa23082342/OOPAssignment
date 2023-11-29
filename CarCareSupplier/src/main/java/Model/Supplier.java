package Model;

import Control.SupplierController;
import DatabaseLayer.SupplierDatabase;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Supplier {
    private int supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierCountry;
    private String supplierCategory;
    private SupplierController supplierController;

    public Supplier(String supplierName, String supplierAddress, String supplierPhone, String supplierCountry, String supplierCategory) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
        this.supplierCountry = supplierCountry;
        this.supplierCategory = supplierCategory;
    }
    public Supplier(){}
    public String getSupplierName() {
        return supplierName;
    }
    public String getSupplierAddress() {
        return supplierAddress;
    }
    public String getSupplierPhone() {
        return supplierPhone;
    }
    public String getSupplierCountry() {
        return supplierCountry;
    }
    public String getSupplierCategory() {
        return supplierCategory;
    }
    public int getSupplierID() {
        return supplierID;
    }
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }
    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }
    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }
    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public int addToDatabase() throws SQLException {
        String insertQuery = "INSERT INTO Suppliers (SupplierName, SupplierAddress, SupplierPhone, SupplierCategory, SupplierCountry) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SupplierDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, this.supplierName);
            preparedStatement.setString(2, this.supplierAddress);
            preparedStatement.setString(3, this.supplierPhone);
            preparedStatement.setString(4, this.supplierCategory);
            preparedStatement.setString(5, this.supplierCountry);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedID = generatedKeys.getInt(1);
                        setSupplierID(generatedID);

                        if (supplierController != null) {
                            supplierController.addSupplier(this);
                        }
                        return generatedID;
                    }
                }
            } else {
                throw new SQLException("Failed to add Supplier");
            }
        }
        return 0;
    }
    public void deleteFromDatabase() throws SQLException {
        if (supplierID != 0) {
            String deleteQuery = "DELETE FROM Suppliers WHERE SupplierID = ?";

            try (Connection connection = SupplierDatabase.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                preparedStatement.setInt(1, this.supplierID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected < 0) {
                    throw new SQLException("Failed to delete Supplier");
                }
            }
        } else {
            throw new SQLException("Supplier ID is not set");
        }
    }
    public void updateDatabase() throws SQLException {
        String updateQuery = "UPDATE Suppliers SET SupplierName=?, SupplierAddress=?, SupplierPhone=?, SupplierCategory=?, SupplierCountry=? WHERE SupplierID=?";

        try (Connection connection = SupplierDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, this.supplierName);
            preparedStatement.setString(2, this.supplierAddress);
            preparedStatement.setString(3, this.supplierPhone);
            preparedStatement.setString(4, this.supplierCategory);
            preparedStatement.setString(5, this.supplierCountry);
            preparedStatement.setInt(6, this.supplierID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected <= 0) {
                throw new SQLException("Failed to update Supplier");
            } else {
                System.out.println("Supplier updated successfully in the database");
            }
        }
    }
    public DefaultTableModel getSupplierTableModel() {
        String selectQuery = "SELECT * FROM Suppliers";
        DefaultTableModel model = new DefaultTableModel();

        try (Connection connection = SupplierDatabase.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(selectQuery)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();

            String[] col = new String[cols];
            for (int i = 0; i < cols; i++) {
                col[i] = rsmd.getColumnName(i + 1);
            }

            model.setColumnIdentifiers(col);

            while (rs.next()) {
                String supplierID = rs.getString(1);
                String supplierName = rs.getString(2);
                String supplierAddress = rs.getString(3);
                String supplierPhone = rs.getString(4);
                String supplierCategory = rs.getString(5);
                String supplierCountry = rs.getString(6);

                String[] row = {supplierID, supplierName, supplierAddress, supplierPhone, supplierCategory, supplierCountry};
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving supplier data: " + e.getMessage());
        }

        return model;
    }
}
