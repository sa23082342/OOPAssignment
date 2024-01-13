package Control;

import Model.Supplier;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
public class SupplierController {
    private ArrayList<Supplier> suppliers;
    public SupplierController(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    public void addSupplier(Supplier newSupplier) {
        suppliers.add(newSupplier);
    }
    public void updateSupplier(int supplierID, String supplierName, String supplierAddress, String supplierPhone, String supplierCountry, String supplierCategory) {

        Supplier supplier = new Supplier();
        if (supplierID > 0 ) {
            supplier.setSupplierID(supplierID);
            supplier.setSupplierName(supplierName);
            supplier.setSupplierAddress(supplierAddress);
            supplier.setSupplierPhone(supplierPhone);
            supplier.setSupplierCountry(supplierCountry);
            supplier.setSupplierCategory(supplierCategory);

            try {
                supplier.updateDatabase();
                System.out.println("Supplier updated successfully in the controller");
            } catch (SQLException e) {
                System.out.println("Error updating supplier in the controller: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Supplier not found with ID " + supplierID);
        }
    }
    public void removeSupplier(int supplierID) {
        Supplier supplierToRemove = findSupplierByID(supplierID);
        if (supplierToRemove != null) {
            suppliers.remove(supplierToRemove);
        } else {
            System.out.println("Error: Supplier not found with ID " + supplierID);
        }
    }
    public Supplier findSupplierByID(int supplierID) {
        System.out.println("Searching for supplier with ID: " + supplierID);

        for (Supplier supplier : suppliers) {
            System.out.println("Checking supplier with ID: " + supplier.getSupplierID());
            if (supplier.getSupplierID() == supplierID) {
                System.out.println("Supplier found!");
                return supplier;
            }
        }
        System.out.println("Supplier not found with ID: " + supplierID);
        return null;
    }
    public DefaultTableModel getSupplierTableModel() {
        Supplier supplierModel = new Supplier();
        return supplierModel.getSupplierTableModel();
    }
}
