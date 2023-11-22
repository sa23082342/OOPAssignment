package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SupplierDetailsTable extends JFrame {
    private JPanel contentPane;
    String url = "jdbc:mysql://localhost:3306/CarCare";
    String username = "root";
    String password = "";
    private boolean isFirstClick = true;
    private DefaultTableModel model;
    public SupplierDetailsTable() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 580);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTable Supplierdetailstable = new JTable();
        JScrollPane scrollPane = new JScrollPane(Supplierdetailstable);
        scrollPane.setBounds(170, 20, 800, 500);
        contentPane.add(scrollPane);
        Supplierdetailstable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JButton displayBtn = new JButton("Display Suppliers");
        displayBtn.setBounds(10, 250, 150, 30);
        contentPane.add(displayBtn);

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
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement st = connection.createStatement();
                    String query = "SELECT * FROM Suppliers";
                    ResultSet rs = st.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    if (isFirstClick) {
                        int cols = rsmd.getColumnCount();
                        String[] col = new String[cols];
                        for (int i = 0; i < cols; i++) {
                            col[i] = rsmd.getColumnName(i + 1);
                        }

                        model = new DefaultTableModel();
                        model.setColumnIdentifiers(col);

                        Supplierdetailstable.setModel(model);
                    }

                    if (!isFirstClick) {
                        model.setRowCount(0);
                    }
                    String SupplierID, SupplierName, SupplierAddress, SupplierPhone, SupplierCategory, SupplierCountry;
                    while (rs.next()) {
                        SupplierID = rs.getString(1);
                        SupplierName = rs.getString(2);
                        SupplierAddress = rs.getString(3);
                        SupplierPhone = rs.getString(4);
                        SupplierCategory = rs.getString(5);
                        SupplierCountry = rs.getString(6);
                        String[] row = {SupplierID, SupplierName, SupplierAddress, SupplierPhone, SupplierCategory, SupplierCountry};
                        model.addRow(row);
                    }

                    st.close();
                    connection.close();
                    System.out.println("Displayed supplier details");

                    isFirstClick = false;
                } catch (ClassNotFoundException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton returnBtn = new JButton("Return");
        returnBtn.setBounds(10, 280, 150, 30);
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
}
