package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InventoryDetailsTable extends JFrame {
    private JPanel contentPane;
    String url = "jdbc:mysql://localhost:3306/carCare";
    String username = "root";
    String password = "";
    private boolean isFirstClick = true;
    private DefaultTableModel model;

    public InventoryDetailsTable() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 580);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTable Supplierdetailstable = new JTable();
        Supplierdetailstable.setBounds(170, 20, 800, 500);
        contentPane.add(Supplierdetailstable);

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
                    String query = "SELECT * FROM inventory";
                    ResultSet rs = st.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    if (isFirstClick) {
                        int cols = rsmd.getColumnCount();
                        String[] colname = new String[cols];
                        for (int i = 0; i < cols; i++) {
                            colname[i] = rsmd.getColumnName(i + 1);
                        }

                        model = new DefaultTableModel();
                        model.setColumnIdentifiers(colname);

                        Supplierdetailstable.setModel(model);
                    }

                    if (!isFirstClick) {
                        model.setRowCount(0);
                    }

                    String ItemID, ItemName, ItemPrice, ItemQuantity;
                    while (rs.next()) {
                        ItemID = rs.getString(1);
                        ItemName = rs.getString(2);
                        ItemPrice = rs.getString(3);
                        ItemQuantity = rs.getString(4);
                        String[] row = {ItemID, ItemName, ItemPrice, ItemQuantity};
                        model.addRow(row);
                    }

                    st.close();
                    connection.close();
                    System.out.println("Displayed Item Details");

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
                InventoryForm mainWindow = new InventoryForm();
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
