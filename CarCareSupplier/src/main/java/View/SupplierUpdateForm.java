package View;

import Control.SupplierController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SupplierUpdateForm extends JFrame {
    private JPanel contentPane;
    private SupplierController supplierController;
    public SupplierUpdateForm(SupplierController supplierController) {
        this.supplierController = supplierController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 600);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel supplierIDLabel = new JLabel("Supplier ID ");
        supplierIDLabel.setBounds(80, 20, 100, 40);
        contentPane.add(supplierIDLabel);
        supplierIDLabel.setPreferredSize(new Dimension(200, 40));
        supplierIDLabel.setFont( new Font("Arial", Font.PLAIN, 16));

        JTextField supplierIDField = new JTextField();
        supplierIDField.setBounds(220, 20, 280, 40);
        contentPane.add(supplierIDField);
        supplierIDField.setPreferredSize(new Dimension(200, 40));
        supplierIDField.setFont( new Font("Arial", Font.PLAIN, 16));


        JLabel supplierNameLabel = new JLabel("Supplier Name ");
        supplierNameLabel.setBounds(80, 80, 150, 40);
        contentPane.add(supplierNameLabel);
        supplierNameLabel.setPreferredSize(new Dimension(200, 40));
        supplierNameLabel.setFont( new Font("Arial", Font.PLAIN, 16));

        JTextField supplierNameField = new JTextField();
        supplierNameField.setBounds(220, 80, 280, 40);
        contentPane.add(supplierNameField);
        supplierNameField.setPreferredSize(new Dimension(200, 40));
        supplierNameField.setFont( new Font("Arial", Font.PLAIN, 16));

        JLabel supplierAddressLabel = new JLabel("Supplier Address ");
        supplierAddressLabel.setBounds(80, 140, 140, 40);
        contentPane.add(supplierAddressLabel);
        supplierAddressLabel.setPreferredSize(new Dimension(200, 40));
        supplierAddressLabel.setFont( new Font("Arial", Font.PLAIN, 16));

        JTextField supplierAddressField = new JTextField();
        supplierAddressField.setBounds(220, 140, 280, 40);
        contentPane.add(supplierAddressField);
        supplierAddressField.setPreferredSize(new Dimension(200, 40));
        supplierAddressField.setFont( new Font("Arial", Font.PLAIN, 16));

        JLabel supplierPhoneLabel = new JLabel("Supplier Phone ");
        supplierPhoneLabel.setBounds(80, 200, 140, 40);
        contentPane.add(supplierPhoneLabel);
        supplierPhoneLabel.setPreferredSize(new Dimension(200, 40));
        supplierPhoneLabel.setFont( new Font("Arial", Font.PLAIN, 16));

        JTextField supplierPhoneField = new JTextField();
        supplierPhoneField.setBounds(220, 200, 280, 40);
        contentPane.add(supplierPhoneField);
        supplierPhoneField.setPreferredSize(new Dimension(200, 40));
        supplierPhoneField.setFont( new Font("Arial", Font.PLAIN, 16));

        JLabel supplierCountryLabel = new JLabel("Supplier Country ");
        supplierCountryLabel.setBounds(80, 260, 140, 40);
        contentPane.add(supplierCountryLabel);
        supplierCountryLabel.setPreferredSize(new Dimension(200, 40));
        supplierCountryLabel.setFont( new Font("Arial", Font.PLAIN, 16));

        JTextField supplierCountryField = new JTextField();
        supplierCountryField.setBounds(220, 260, 280, 40);
        contentPane.add(supplierCountryField);
        supplierCountryField.setPreferredSize(new Dimension(200, 40));
        supplierCountryField.setFont( new Font("Arial", Font.PLAIN, 16));

        JLabel supplierCategoryLabel = new JLabel("Supplier Category ");
        supplierCategoryLabel.setBounds(80, 320, 140, 40);
        contentPane.add(supplierCategoryLabel);
        supplierCategoryLabel.setPreferredSize(new Dimension(200, 40));
        supplierCategoryLabel.setFont( new Font("Arial", Font.PLAIN, 16));

        JTextField supplierCategoryField = new JTextField();
        supplierCategoryField.setBounds(220, 325, 280, 40);
        contentPane.add(supplierCategoryField);
        supplierCategoryField.setFont( new Font("Arial", Font.PLAIN, 16));

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(80, 400, 200, 50);
        contentPane.add(updateBtn);
        updateBtn.setFont( new Font("Arial", Font.BOLD, 16));

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierID = supplierIDField.getText();
                String supplierName = supplierNameField.getText();
                String supplierAddress = supplierAddressField.getText();
                String supplierPhone = supplierPhoneField.getText();
                String supplierCountry = supplierCountryField.getText();
                String supplierCategory = supplierCategoryField.getText();

                if(supplierID == null){
                    JOptionPane.showMessageDialog(contentPane, "Please enter a valid Supplier ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        supplierController.updateSupplier(Integer.parseInt(supplierID), supplierName, supplierAddress, supplierPhone, supplierCountry, supplierCategory);
                        JOptionPane.showMessageDialog(contentPane, "Supplier details updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(contentPane, "Please enter a valid Supplier ID", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        JButton returnBtn = new JButton("Return");
        returnBtn.setBounds(300, 400, 200, 50);
        contentPane.add(returnBtn);
        returnBtn.setFont( new Font("Arial", Font.BOLD, 16));

        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Returned to the main page");
                dispose();
                SupplierForm mainWindow = new SupplierForm();
                mainWindow.setVisible(true);
            }
        });

        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateBtn.setForeground(Color.GREEN);
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateBtn.setForeground(null);
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        SupplierUpdateForm ui = new SupplierUpdateForm(supplierController);
        ui.setContentPane(ui.contentPane);
        ui.setTitle("Supplier Management");
        ui.setSize(600, 500);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setVisible(true);
    }
}
