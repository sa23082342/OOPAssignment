package View;

import Control.SupplierController;
import Model.Supplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
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
    private JLabel SupplierCategoryLabel;
    private JButton OrdersBtn;
    private JButton SuppliersBtn;
    private JButton EmployeesBtn;
    private JButton ReportsBtn;
    private JButton AssignJobsBtn;
    private JButton InventoryBtn;
    private SupplierController supplierController;
    private final ArrayList<Supplier> supplierList;
    int suppliercount;
    private Supplier selectedSupplier;

    public SupplierForm() {
        supplierList = new ArrayList<>();
        supplierController = new SupplierController(supplierList);

        SupplierIDField.setFont( new Font("Arial", Font.PLAIN, 15));
        SupplierIDField.setPreferredSize(new Dimension(200, 40));
        SupplierIDLabel.setFont(new Font("Arial", Font.BOLD, 20));

        SupplierNameField.setPreferredSize(new Dimension(200, 40));
        SupplierNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        SupplierNameField.setFont( new Font("Arial", Font.PLAIN, 16));

        SupplierAddressField.setPreferredSize(new Dimension(200, 40));
        SupplierAddressLabel.setFont(new Font("Arial", Font.BOLD, 20));
        SupplierAddressField.setFont( new Font("Arial", Font.PLAIN, 16));

        SupplierPhoneField.setPreferredSize(new Dimension(200, 40));
        SupplierPhoneLabel.setFont(new Font("Arial", Font.BOLD, 20));
        SupplierPhoneField.setFont( new Font("Arial", Font.PLAIN, 16));

        SupplierCountryField.setPreferredSize(new Dimension(200, 40));
        SupplierCountryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        SupplierCountryField.setFont( new Font("Arial", Font.PLAIN, 16));

        SupplierCategoryField.setPreferredSize(new Dimension(200, 40));
        SupplierCategoryLabel .setFont(new Font("Arial", Font.BOLD, 20));
        SupplierCategoryField.setFont( new Font("Arial", Font.PLAIN, 16));

        ADDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierName = SupplierNameField.getText();
                String supplierAddress = SupplierAddressField.getText();
                String supplierPhone = SupplierPhoneField.getText();
                String supplierCountry = SupplierCountryField.getText();
                String supplierCategory = SupplierCategoryField.getText();

                Supplier newSupplier = new Supplier(supplierName, supplierAddress, supplierPhone, supplierCountry, supplierCategory);

                if (supplierName.isEmpty() || supplierAddress.isEmpty() || supplierPhone.isEmpty() || supplierCountry.isEmpty() || supplierCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill out the Supplier details", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int generatedID = newSupplier.addToDatabase();

                    JOptionPane.showMessageDialog(BackPanel, "Supplier Added Successfully. Supplier ID: " + generatedID, "Success", JOptionPane.INFORMATION_MESSAGE);

                    SupplierIDField.setText(String.valueOf(generatedID));

                    SupplierNameField.setText(null);
                    SupplierAddressField.setText(null);
                    SupplierPhoneField.setText(null);
                    SupplierCountryField.setText(null);
                    SupplierCategoryField.setText(null);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(BackPanel, "Failed to add Supplier: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ADDBtn.setPreferredSize(new Dimension(200, 60));
        ADDBtn.setFont(new Font("Arial", Font.BOLD, 16));

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
                String idText = SupplierIDField.getText();

                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please enter a valid Supplier ID", "Error", JOptionPane.ERROR_MESSAGE);

                }
                try {
                    int supplierID = Integer.parseInt(idText);
                    Supplier supplier = new Supplier();
                    supplier.setSupplierID(supplierID);

                    int result = JOptionPane.showConfirmDialog(BackPanel, "Are you Sure you want to delete the Supplier?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        supplier.deleteFromDatabase();

                        supplierController.removeSupplier(supplierID);
                        Supplier supplierToRemove = null;
                        for (Supplier newsupplier : supplierList) {
                            if (newsupplier.getSupplierID() == supplierID) {
                                supplierToRemove = newsupplier;
                                break;
                            }
                        }
                        if (supplierToRemove != null) {
                            supplierList.remove(supplierToRemove);
                        }

                        JOptionPane.showMessageDialog(null, "Supplier Deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        SupplierIDField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Supplier not Deleted!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(BackPanel, "Please enter a valid Supplier ID", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null, "Error deleting supplier: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        DeleteBtn.setPreferredSize(new Dimension(200, 60));
        DeleteBtn.setFont(new Font("Arial", Font.BOLD, 16));

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
                SupplierUpdateForm window2 = new SupplierUpdateForm(supplierController);
                window2.setVisible(true);
            }
        });

        UpdateBtn.setPreferredSize(new Dimension(200, 60));
        UpdateBtn.setFont(new Font("Arial", Font.BOLD, 16));

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
                SupplierDetailsTable window3 = new SupplierDetailsTable(supplierController);
                window3.setVisible(true);
            }
        });

        DisplayTableBtn.setPreferredSize(new Dimension(200, 60));
        DisplayTableBtn.setFont(new Font("Arial", Font.BOLD, 16));

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

        OrdersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        OrdersBtn.setPreferredSize(new Dimension(200, 60));
        OrdersBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        OrdersBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                OrdersBtn.setForeground(new java.awt.Color(0, 150, 0));
                OrdersBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                OrdersBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                OrdersBtn.setForeground(null);
            }
        });

        SuppliersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You are Currently in the Suppliers Management Page !","Manage Suppliers", 1);

                SupplierNameField.setText(null);
                SupplierAddressField.setText(null);
                SupplierPhoneField.setText(null);
                SupplierCountryField.setText(null);
                SupplierCategoryField.setText(null);
                SupplierIDField.setText(null);
            }
        });
        SuppliersBtn.setPreferredSize(new Dimension(200, 60));
        SuppliersBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        SuppliersBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                SuppliersBtn.setForeground(new java.awt.Color(0, 150, 0));
                SuppliersBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                SuppliersBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                SuppliersBtn.setForeground(null);
            }
        });

        InventoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        InventoryBtn.setPreferredSize(new Dimension(240, 60));
        InventoryBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        InventoryBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                InventoryBtn.setForeground(new java.awt.Color(0, 150, 0));
                InventoryBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                InventoryBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                InventoryBtn.setForeground(null);
            }
        });

        EmployeesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        EmployeesBtn.setPreferredSize(new Dimension(200, 60));
        EmployeesBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        EmployeesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                EmployeesBtn.setForeground(new java.awt.Color(0, 150, 0));
                EmployeesBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                EmployeesBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                EmployeesBtn.setForeground(null);
            }
        });

        AssignJobsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        AssignJobsBtn.setPreferredSize(new Dimension(200, 60));
        AssignJobsBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        AssignJobsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                AssignJobsBtn.setForeground(new java.awt.Color(0, 150, 0));
                AssignJobsBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                AssignJobsBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                AssignJobsBtn.setForeground(null);
            }
        });

        ReportsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ReportsBtn.setPreferredSize(new Dimension(200, 60));
        ReportsBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        ReportsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                ReportsBtn.setForeground(new java.awt.Color(0, 150, 0));
                ReportsBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                ReportsBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                ReportsBtn.setForeground(null);
            }
        });
    }

    public static void main(String[] args) {
        SupplierForm ui = new SupplierForm();
        ui.setContentPane(ui.BackPanel);
        ui.setTitle("Supplier Management");
        ui.setSize(1400, 1000);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
