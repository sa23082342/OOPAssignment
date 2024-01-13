package serviceLayer;

import Control.SupplierController;
import DatabaseLayer.SupplierDatabase;
import Model.Supplier;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class SupplierService {

    public int addSupplierToDatabase(Supplier supplier) throws SQLException {
        String insertQuery = "INSERT INTO Suppliers (SupplierName, SupplierAddress, SupplierPhone, SupplierCategory, SupplierCountry) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = SupplierDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, supplier.getSupplierName());
            preparedStatement.setString(2, supplier.getSupplierAddress());
            preparedStatement.setString(3, supplier.getSupplierPhone());
            preparedStatement.setString(4, supplier.getSupplierCategory());
            preparedStatement.setString(5, supplier.getSupplierCountry());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedID = generatedKeys.getInt(1);
                        supplier.setSupplierID(generatedID);

                        SupplierController supplierController = supplier.getSupplierController();
                        if (supplierController != null) {
                            supplierController.addSupplier(supplier);
                        }
                        return generatedID;
                    } else {
                        throw new SQLException("Failed to get generated keys after adding Supplier");
                    }
                }
            } else {
                throw new SQLException("Failed to add Supplier");
            }
        }
    }


    public void deleteSupplierFromDatabase(Supplier supplier) throws SQLException {
        if (supplier.getSupplierID() != 0) {
            String deleteQuery = "DELETE FROM Suppliers WHERE SupplierID = ?";

            try (Connection connection = SupplierDatabase.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                preparedStatement.setInt(1, supplier.getSupplierID());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected < 0) {
                    throw new SQLException("Failed to delete Supplier");
                }
            }
        } else {
            throw new SQLException("Supplier ID is not set");
        }
    }

    public void updateDatabase(Supplier supplier) throws SQLException {
        String updateQuery = "UPDATE Suppliers SET SupplierName=?, SupplierAddress=?, SupplierPhone=?, SupplierCategory=?, SupplierCountry=? WHERE SupplierID=?";

        try (Connection connection = SupplierDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, supplier.getSupplierName());
            preparedStatement.setString(2, supplier.getSupplierAddress());
            preparedStatement.setString(3, supplier.getSupplierPhone());
            preparedStatement.setString(4, supplier.getSupplierCategory());
            preparedStatement.setString(5, supplier.getSupplierCountry());
            preparedStatement.setInt(6, supplier.getSupplierID());

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
            return null; // Return null in case of an exception
        }

        return model;
    }
}
