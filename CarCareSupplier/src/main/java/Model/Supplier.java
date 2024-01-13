package Model;

import Control.SupplierController;
import serviceLayer.SupplierService;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Supplier {
    private int supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierCountry;
    private String supplierCategory;
    private SupplierController supplierController;
    private final SupplierService supplierService;

    public Supplier(String supplierName, String supplierAddress, String supplierPhone, String supplierCountry, String supplierCategory) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
        this.supplierCountry = supplierCountry;
        this.supplierCategory = supplierCategory;
        this.supplierService = new SupplierService();
    }

    public SupplierController getSupplierController() {
        return supplierController;
    }

    public void setSupplierController(SupplierController supplierController) {
        this.supplierController = supplierController;
    }
    public Supplier() {
        this.supplierService = new SupplierService();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public String getSupplierCountry() {
        return supplierCountry;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public int addToDatabase() throws SQLException {
        return supplierService.addSupplierToDatabase(this);
    }

    public void deleteFromDatabase() throws SQLException {
        supplierService.deleteSupplierFromDatabase(this);
    }

    public void updateDatabase() throws SQLException {
        supplierService.updateDatabase(this);
    }

    public DefaultTableModel getSupplierTableModel() {
        return supplierService.getSupplierTableModel();
    }
}
