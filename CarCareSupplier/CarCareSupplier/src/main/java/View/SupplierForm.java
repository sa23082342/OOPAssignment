package View;

import Control.SupplierController;
import Model.Supplier;

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

public class SupplierForm extends JFrame {
    private JPanel BackPanel;
    private JLabel SupplierNameLabel;
    private JLabel SupplierAddressLabel;
    private JLabel SupplierPhoneLabel;
    private JLabel SupplierCountryLabel;
    private JTextField SupplierNameField;
    private JTextField SupplierAddressField;
    private JTextField SupplierPhoneField;
    private JTextField SupplierCategoryField;
    private JButton ADDBtn;
    private JTextField SupplierCountryField;
    private JButton DeleteBtn;
    private JLabel SupplierIDLabel;
    private JTextField SupplierIDField;
    private JButton UpdateBtn;
    private JButton DisplayTableBtn;
    private SupplierController suppliercontroller;
    private final ArrayList<Supplier> supplierlist;
    int suppliercount;
    private Supplier selectedSupplier;
    public SupplierForm() {
        supplierlist = new ArrayList<>();
        suppliercontroller = new SupplierController(supplierlist);
        suppliercount = 0;

        ADDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierName = SupplierNameField.getText();
                String supplierAddress = SupplierAddressField.getText();
                String supplierPhone = SupplierPhoneField.getText();
                String supplierCountry = SupplierCountryField.getText();
                String supplierCategory = SupplierCategoryField.getText();

                String url = "jdbc:mysql://localhost:3306/CarCare";
                String username = "root";
                String password = "";

                if (supplierName.isEmpty() || supplierAddress.isEmpty() || supplierPhone.isEmpty() || supplierCountry.isEmpty() || supplierCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill out the Supplier details", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);

                    String insertQuery = "INSERT INTO Suppliers (SupplierName, SupplierAddress, SupplierPhone, SupplierCategory, SupplierCountry) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, supplierName);
                    preparedStatement.setString(2, supplierAddress);
                    preparedStatement.setString(3, supplierPhone);
                    preparedStatement.setString(4, supplierCategory);
                    preparedStatement.setString(5, supplierCountry);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                            if (generatedKeys.next()) {
                                int generatedID = generatedKeys.getInt(1);
                                Supplier newSupplier = new Supplier(supplierName, supplierAddress, supplierPhone, supplierCountry, supplierCategory);
                                newSupplier.setSupplierID(generatedID);
                                suppliercontroller.addSupplier(newSupplier);
                                supplierlist.add(newSupplier);

                                SupplierIDField.setText(String.valueOf(generatedID));
                                JOptionPane.showMessageDialog(BackPanel, "Supplier Added Successfully. Supplier ID: " + generatedID, "Success", JOptionPane.INFORMATION_MESSAGE);

                                SupplierNameField.setText("");
                                SupplierAddressField.setText("");
                                SupplierPhoneField.setText("");
                                SupplierCountryField.setText("");
                                SupplierCategoryField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(BackPanel, "Failed to retrieve Supplier ID", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(BackPanel, "Failed to add Supplier", "Error", JOptionPane.ERROR_MESSAGE);
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
                int SupplierID = Integer.parseInt(SupplierIDField.getText());

                String url = "jdbc:mysql://localhost:3306/CarCare";
                String username = "root";
                String password = "";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();

                    int result = JOptionPane.showConfirmDialog(BackPanel, "Sure you want to delete the Supplier?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION)
                    {
                        statement.executeUpdate("DELETE FROM Suppliers WHERE SupplierID=" + SupplierID);
                        suppliercontroller.removeSupplier(SupplierID);
                        supplierlist.remove(SupplierID);
                        JOptionPane.showMessageDialog(null, "Supplier Deleted!");
                        SupplierIDField.setText("");
                    }
                    else if(result == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(null, "Supplier not Deleted!");
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
                System.out.println("Supplier Update Form");
                SupplierUpdateForm window2 = new SupplierUpdateForm(suppliercontroller);
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
                SupplierDetailsTable window3 = new SupplierDetailsTable();
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
        SupplierForm ui = new SupplierForm();
        ui.setContentPane(ui.BackPanel);
        ui.setTitle("Car Care");
        ui.setSize(600, 600);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
