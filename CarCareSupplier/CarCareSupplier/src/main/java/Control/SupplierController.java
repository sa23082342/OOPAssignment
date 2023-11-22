package Control;

import Model.Supplier;

import java.util.ArrayList;
public class SupplierController {
    private ArrayList<Supplier> suppliers;
    Supplier supplier;
    public SupplierController(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    public void addSupplier(Supplier newSupplier) {
        suppliers.add(newSupplier);
    }
    public void updateSupplier(int index, String supplierName, String supplierAddress, String supplierPhone, String supplierCountry, String supplierCategory) {
        if (index >= 0 && index < suppliers.size()) {
            if (supplierName != null) {
                supplier.setSupplierName(supplierName);
            }
            if (supplierAddress != null) {
                supplier.setSupplierAddress(supplierAddress);
            }
            if (supplierPhone != null) {
                supplier.setSupplierPhone(supplierPhone);
            }
            if (supplierCountry != null) {
                supplier.setSupplierCountry(supplierCountry);
            }
            if (supplierCategory != null) {
                supplier.setSupplierCategory(supplierCategory);
            }
        } else {
            System.out.println("Error");
        }
    }
    public void removeSupplier(int index) {
        if (index >= 0 && index < suppliers.size()) {
            suppliers.remove(index);
        }
    }
}
