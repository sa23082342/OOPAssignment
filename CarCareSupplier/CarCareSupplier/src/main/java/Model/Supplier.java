package Model;

public class Supplier {
    public int supplierID;
    public String supplierName;
    public String supplierAddress;
    public String supplierPhone;
    public String supplierCountry;
    public String supplierCategory;
    public Supplier(String supplierName, String supplierAddress, String supplierPhone, String supplierCountry, String supplierCategory) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
        this.supplierCountry = supplierCountry;
        this.supplierCategory = supplierCategory;
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
    public Supplier() {}
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
}
