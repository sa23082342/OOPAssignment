package View;

import Control.InventoryController;
import Model.Inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class InventoryForm extends JFrame {
    private JPanel BackPanel;
    private JLabel ItemNameLabel;
    private JLabel ItemPriceLabel;
    private JLabel ItemQuantityLabel;
    private JTextField ItemNameField;
    private JTextField ItemPriceField;
    private JTextField ItemQuantityField;
    private JButton ADDBtn;
    private JButton DeleteBtn;
    private JLabel ItemIDLabel;
    private JTextField ItemIDField;
    private JButton UpdateBtn;
    private JButton DisplayTableBtn;
    private InventoryController inventorycontroller;
    private final ArrayList<Inventory> inventory;
    int itemcount;
    private Inventory selectedinventory;
    public InventoryForm() {
        inventory = new ArrayList<>();
        inventorycontroller = new InventoryController(inventory);
        itemcount = 0;

        ADDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ItemName = ItemNameField.getText();
                String ItemPrice = ItemPriceField.getText();
                String ItemQuantity = ItemQuantityField.getText();
                String ItemId = ItemIDField.getText();

                String url = "jdbc:mysql://localhost:3306/carCare";
                String username = "root";
                String password = "";

                if (ItemName.isEmpty() || ItemPrice.isEmpty() || ItemQuantity.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill out the Item details", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);

                    String insertQuery = "INSERT INTO Inventory (ItemName, ItemPrice, ItemQuantity) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, ItemName);
                    preparedStatement.setString(2, ItemPrice);
                    preparedStatement.setString(3, ItemQuantity);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                            if (generatedKeys.next()) {
                                int generatedID = generatedKeys.getInt(1);
                                Inventory newInventory = new Inventory(ItemName, Double.parseDouble(ItemPrice) , Integer.parseInt(ItemQuantity));
                                newInventory.setItemID(generatedID);
                                inventorycontroller.addInventory(newInventory);
                                inventory.add(newInventory);

                                ItemIDField.setText(String.valueOf(generatedID));
                                JOptionPane.showMessageDialog(BackPanel, "Item Added Successfully. Item ID: " + generatedID, "Success", JOptionPane.INFORMATION_MESSAGE);

                                ItemNameField.setText("");
                                ItemPriceField.setText("");
                                ItemQuantityField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(BackPanel, "Failed to retrieve Item ID", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(BackPanel, "Failed to add Item", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    preparedStatement.close();
                    connection.close();

                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        });
        ADDBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                ADDBtn.setForeground(new java.awt.Color(0, 150, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                ADDBtn.setForeground(null);
            }
        });
        DeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ItemID = Integer.parseInt(ItemIDField.getText());

                String url = "jdbc:mysql://localhost:3306/carCare";
                String username = "root";
                String password = "";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();

                    int result = JOptionPane.showConfirmDialog(BackPanel, "Sure you want to delete the Item?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION)
                    {
                        statement.executeUpdate("DELETE FROM Inventory WHERE ItemID=" + ItemID);
                        inventorycontroller.removeInventory(ItemID);
                        inventory.remove(ItemID);
                        JOptionPane.showMessageDialog(null, "Item Deleted!");
                        ItemIDField.setText("");
                    }
                    else if(result == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(null, "Item not Deleted!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Canceled!");
                    }
                    statement.close();
                    connection.close();
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        });
        DeleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                DeleteBtn.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                DeleteBtn.setForeground(null);
            }
        });
        UpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Item Update Form");
                ItemUpdateForm window2 = new ItemUpdateForm(inventorycontroller);
                window2.setVisible(true);
            }
        });
        UpdateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                UpdateBtn.setForeground(new java.awt.Color(0, 150, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                UpdateBtn.setForeground(null);
            }
        });

        DisplayTableBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryDetailsTable window3 = new InventoryDetailsTable();
                window3.setVisible(true);
            }
        });
        DisplayTableBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                DisplayTableBtn.setForeground(new java.awt.Color(0, 150, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                DisplayTableBtn.setForeground(null);
            }
        });
    }

    public static void main(String[] args) {
        InventoryForm ui = new InventoryForm();
        ui.setContentPane(ui.BackPanel);
        ui.setTitle("Car Care");
        ui.setSize(600, 600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
