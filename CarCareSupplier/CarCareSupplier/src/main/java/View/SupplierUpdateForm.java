package View;

import Control.SupplierController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JButton;

public class SupplierUpdateForm extends JFrame {
    private JPanel contentPane;
    private SupplierController supplierController;
    public SupplierUpdateForm(SupplierController supplierController) {
        this.supplierController = supplierController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel SupplierIDLabel = new JLabel("Supplier ID ");
        SupplierIDLabel.setBounds(50,10,100,40);
        contentPane.add(SupplierIDLabel);

        JTextField SupplierIDField = new JTextField();
        SupplierIDField.setBounds(150, 15, 250, 30);
        contentPane.add(SupplierIDField);

        JLabel SupplierNameLabel = new JLabel("Supplier Name ");
        SupplierNameLabel.setBounds(50,50,100,40);
        contentPane.add(SupplierNameLabel);

        JTextField SupplierNameField = new JTextField();
        SupplierNameField.setBounds(150, 55, 250, 30);
        contentPane.add(SupplierNameField);

        JLabel SupplierAddressLabel = new JLabel("Supplier Address ");
        SupplierAddressLabel.setBounds(50,90,140,40);
        contentPane.add(SupplierAddressLabel);

        JTextField SupplierAddressField = new JTextField();
        SupplierAddressField.setBounds(160, 95, 240, 30);
        contentPane.add(SupplierAddressField);

        JLabel SupplierPhoneLabel = new JLabel("Supplier Phone ");
        SupplierPhoneLabel.setBounds(50,130,140,40);
        contentPane.add(SupplierPhoneLabel);

        JTextField SupplierPhoneField = new JTextField();
        SupplierPhoneField.setBounds(160, 135, 240, 30);
        contentPane.add(SupplierPhoneField);

        JLabel SupplierCountryLabel = new JLabel("Supplier Country ");
        SupplierCountryLabel.setBounds(50,170,140,40);
        contentPane.add(SupplierCountryLabel);

        JTextField SupplierCountryField = new JTextField();
        SupplierCountryField.setBounds(160, 175, 240, 30);
        contentPane.add(SupplierCountryField);

        JLabel SupplierCategoryLabel = new JLabel("Supplier Category ");
        SupplierCategoryLabel.setBounds(50,210,140,40);
        contentPane.add(SupplierCategoryLabel);

        JTextField SupplierCategoryField = new JTextField();
        SupplierCategoryField.setBounds(165, 215, 235, 30);
        contentPane.add(SupplierCategoryField);


        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(120, 270, 150, 30);
        contentPane.add(updateBtn);

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int supplierID = Integer.parseInt(SupplierIDField.getText());
                String supplierName = SupplierNameField.getText();
                String supplierAddress = SupplierAddressField.getText();
                String supplierPhone = SupplierPhoneField.getText();
                String supplierCountry = SupplierCountryField.getText();
                String supplierCategory = SupplierCategoryField.getText();

                String url = "jdbc:mysql://localhost:3306/CarCare";
                String username = "root";
                String password = "";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);

                    String updateQuery = "UPDATE Suppliers SET SupplierName=?, SupplierAddress=?, SupplierPhone=?, SupplierCategory=?, SupplierCountry=? WHERE SupplierID=?";

                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, supplierName);
                    preparedStatement.setString(2, supplierAddress);
                    preparedStatement.setString(3, supplierPhone);
                    preparedStatement.setString(4, supplierCategory);
                    preparedStatement.setString(5, supplierCountry);
                    preparedStatement.setInt(6, supplierID);
                    int rowsAffected = preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(contentPane, "Supplier details Updated");
                    supplierController.updateSupplier(supplierID, supplierName, supplierAddress, supplierPhone, supplierCountry, supplierCategory);

                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(contentPane, "SQL Error: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(contentPane, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

        });

        JButton returnBtn = new JButton("Return");
        returnBtn.setBounds(270, 270, 150, 30);
        contentPane.add(returnBtn);

        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Returned to main page");
                dispose();
                SupplierForm mainWindow = new SupplierForm();
                mainWindow.setVisible(true);
            }
        });
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateBtn.setForeground(Color.BLUE);
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateBtn.setForeground(null);
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
        returnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                returnBtn.setForeground(Color.BLUE);
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                returnBtn.setForeground(null);
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
    }
}