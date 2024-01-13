package View;

import Control.SupplierController;
import DatabaseLayer.SupplierDatabase;
import Model.Supplier;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import javax.naming.CommunicationException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class SupplierDetailsTable extends JFrame {
    private JPanel contentPane;
    private JTextField supplierSearchField;
    private JButton searchBtn;
    private JTable supplierDetailsTable;
    private DefaultTableModel model;
    private boolean isFirstClick = true;
    private SupplierController supplierController;

    public SupplierDetailsTable(SupplierController controller) {
        this.supplierController = controller;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1200, 900);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        supplierSearchField = new JTextField();
        supplierSearchField.setBounds(298, 30, 600, 50);
        supplierSearchField.setFont(new Font("Arial", Font.PLAIN, 16));
        supplierSearchField.setForeground(Color.GRAY);
        supplierSearchField.setText("Please enter a supplier id");
        contentPane.add(supplierSearchField);

        supplierSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (supplierSearchField.getText().equals("Please enter a supplier id")) {
                    supplierSearchField.setText("");
                    supplierSearchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (supplierSearchField.getText().isEmpty()) {
                    supplierSearchField.setForeground(Color.GRAY);
                    supplierSearchField.setText("Please enter a supplier id");
                }
            }
        });

        searchBtn = new JButton("Search Suppliers");
        searchBtn.setBounds(900, 30, 250, 50);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(searchBtn);
        searchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                searchBtn.setForeground(new java.awt.Color(0, 150, 0));
                searchBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                searchBtn.setFont(new Font("Arial", Font.BOLD, 16));
                searchBtn.setForeground(null);
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final String URL =  SupplierDatabase.getDatabaseURL() ;
                final String USERNAME = SupplierDatabase.getDatabaseUserName();
                final String PASSWORD = SupplierDatabase.getDatabasePassword();

                String inputText = supplierSearchField.getText().trim();

                if (inputText.equals("Please enter a supplier id")) {
                    JOptionPane.showMessageDialog(contentPane,"Please enter a valid supplier id");
                    return;
                }

                int supplierID = Integer.parseInt(inputText);

                String selectQuery = "SELECT * FROM Suppliers WHERE SupplierID = ?";

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                    preparedStatement.setInt(1, supplierID);

                    ResultSet rs = preparedStatement.executeQuery();

                    if (rs.next()) {
                        int foundSupplierID = rs.getInt("SupplierID");
                        String supplierName = rs.getString("SupplierName");
                        String supplierAddress = rs.getString("SupplierAddress");
                        String supplierPhone = rs.getString("SupplierPhone");
                        String supplierCountry = rs.getString("SupplierCountry");
                        String supplierCategory = rs.getString("SupplierCategory");

                        Supplier supplier = new Supplier(supplierName, supplierAddress, supplierPhone, supplierCountry, supplierCategory);
                        supplier.setSupplierID(foundSupplierID);

                        model = new DefaultTableModel(new Object[][]{{supplier.getSupplierID(), supplier.getSupplierName(), supplier.getSupplierAddress(),
                                supplier.getSupplierPhone(), supplier.getSupplierCountry(), supplier.getSupplierCategory()}},
                                new String[]{"ID", "Name", "Address", "Phone", "Country", "Category"});
                        supplierDetailsTable.setModel(model);
                    } else {
                        JOptionPane.showMessageDialog(contentPane,"Supplier Not Existing");
                        System.out.println("No supplier found with ID: " + supplierID);
                    }
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(contentPane, exception);
                }
            }
        });

        supplierDetailsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(supplierDetailsTable);
        scrollPane.setBounds(300, 100, 850, 650);
        contentPane.add(scrollPane);
        supplierDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JButton displayBtn = new JButton("Display Suppliers");
        displayBtn.setBounds(25, 300, 250, 60);
        contentPane.add(displayBtn);
        displayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                displayBtn.setForeground(new java.awt.Color(0, 150, 0));
                displayBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                displayBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                displayBtn.setForeground(null);
            }
        });

        displayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                displayBtn.setForeground(new java.awt.Color(0, 150, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                displayBtn.setForeground(null);
            }
        });

        displayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = supplierController.getSupplierTableModel();
                supplierDetailsTable.setModel(model);
                isFirstClick = false;
                System.out.println("Displayed supplier details");
            }
        });

        JButton returnBtn = new JButton("Return");
        returnBtn.setBounds(25, 400, 250, 60);
        contentPane.add(returnBtn);
        returnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                returnBtn.setForeground(new java.awt.Color(0, 150, 0));
                returnBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                returnBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                returnBtn.setForeground(null);
            }
        });

        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Returned to main page");
                dispose();
                SupplierForm mainWindow = new SupplierForm();
                mainWindow.setVisible(true);
            }
        });
        returnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                returnBtn.setForeground(Color.RED);
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                returnBtn.setForeground(null);
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
    }
    public static void main(String[] args) {
            SupplierController supplierController = new SupplierController(new ArrayList<>());
            SupplierDetailsTable ui = new SupplierDetailsTable(supplierController);
            ui.setTitle("Supplier Details Table");
            ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ui.setVisible(true);
    }
}
