package SupplierControllerTesting;

import Control.SupplierController;
import Model.Supplier;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class SupplierControllerTesting {
    static SupplierController supplierController;
    static ArrayList<Supplier> suppliers;
    static Supplier supplier;

    @BeforeAll
    public static void createSupplierController() {
        suppliers = new ArrayList<>();
        supplierController = new SupplierController(suppliers);
    }

    @BeforeEach
    public void setSupplierController() {
        supplier = new Supplier();
        supplier.setSupplierName("Adonis");
        supplier.setSupplierAddress("75, Beverly Hills");
        supplier.setSupplierPhone("+1 2837 2837");
        supplier.setSupplierCountry("USA");
        supplier.setSupplierCategory("Tyres");
    }

    @Test
    public void testSupplierController() {
        Supplier supplier1 = new Supplier(supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierPhone(), supplier.getSupplierCountry(), supplier.getSupplierCategory());
        suppliers.add(supplier1);

        Assertions.assertTrue(suppliers.contains(supplier1));
        Assertions.assertEquals(1, suppliers.size());
    }

    @AfterEach
    public void cleanData() {
        supplier.setSupplierName(null);
        supplier.setSupplierAddress(null);
        supplier.setSupplierPhone(null);
        supplier.setSupplierCountry(null);
        supplier.setSupplierCategory(null);
    }

    @AfterAll
    public static void deleteSupplierController() {
        suppliers = null;
        supplier = null;
        supplierController = null;
    }
}
