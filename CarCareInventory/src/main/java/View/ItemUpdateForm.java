package View;

import Control.InventoryController;
import Model.Inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JButton;

public class ItemUpdateForm extends JFrame {
    private JPanel contentPane;
    private InventoryController inventoryController;
    public ItemUpdateForm(InventoryController inventoryController) {
        this.inventoryController = inventoryController;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel ItemIDLabel = new JLabel("Item ID ");
        ItemIDLabel.setBounds(50,10,100,40);
        contentPane.add(ItemIDLabel);

        JTextField ItemIDField = new JTextField();
        ItemIDField.setBounds(150, 15, 250, 30);
        contentPane.add(ItemIDField);

        JLabel ItemNameLabel = new JLabel("Item Name ");
        ItemNameLabel.setBounds(50,50,100,40);
        contentPane.add(ItemNameLabel);

        JTextField ItemNameField = new JTextField();
        ItemNameField.setBounds(150, 55, 250, 30);
        contentPane.add(ItemNameField);

        JLabel ItemPriceLabel = new JLabel("Item Price ");
        ItemPriceLabel.setBounds(50,90,140,40);
        contentPane.add(ItemPriceLabel);

        JTextField ItemPriceField = new JTextField();
        ItemPriceField.setBounds(160, 95, 240, 30);
        contentPane.add(ItemPriceField);

        JLabel ItemQuantityLabel = new JLabel("Item Quantity ");
        ItemQuantityLabel.setBounds(50,130,140,40);
        contentPane.add(ItemQuantityLabel);

        JTextField ItemQuantityField = new JTextField();
        ItemQuantityField.setBounds(160, 135, 240, 30);
        contentPane.add(ItemQuantityField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(120, 270, 150, 30);
        contentPane.add(updateBtn);

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ItemID = Integer.parseInt(ItemIDField.getText());
                String ItemName = ItemNameField.getText();
                String ItemPrice = ItemPriceField.getText();
                String ItemQuantity = ItemQuantityField.getText();

                String url = "jdbc:mysql://localhost:3306/carCare";
                String username = "root";
                String password = "";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);

                    String updateQuery = "UPDATE Inventory SET ItemName=?, ItemPrice=?, ItemQuantity=? WHERE ItemID=?";

                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, ItemName);
                    preparedStatement.setString(2, ItemPrice);
                    preparedStatement.setString(3, ItemQuantity);
                    preparedStatement.setInt(4, ItemID);
                    int rowsAffected = preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(contentPane, "Item details Updated");
                    inventoryController.updateInventory(ItemID, ItemName, Double.parseDouble(ItemPrice), Integer.parseInt(ItemQuantity));

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
                InventoryForm mainWindow = new InventoryForm();
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