package SupplierModelTesting;
import Model.Supplier;
import org.junit.jupiter.api.*;
public class SupplierSettersTesting {
    static Supplier supplier;

    @BeforeAll
    public static void createSupplier(){
        supplier = new Supplier();
    }
    @BeforeEach
    public void setSupplier(){
        supplier.setSupplierName("Adonis");
        supplier.setSupplierAddress("75, Beverly Hills");
        supplier.setSupplierPhone("+1 0744 6364");
        supplier.setSupplierCountry("USA");
        supplier.setSupplierCategory("Tyres");
    }
    @Test
    public void testSupplier(){
        Assertions.assertEquals("Adonis", supplier.supplierName);
        Assertions.assertEquals("75, Beverly Hills", supplier.supplierAddress);
        Assertions.assertEquals("+1 0744 6364", supplier.supplierPhone);
        Assertions.assertEquals("USA", supplier.supplierCountry);
        Assertions.assertEquals("Tyres", supplier.supplierCategory);
    }
    @AfterEach
    public void cleanData(){
        supplier.supplierName = null;
        supplier.supplierAddress = null;
        supplier.supplierPhone = null;
        supplier.supplierCountry = null;
        supplier.supplierCategory = null;
    }
    @AfterAll
    public static void deleteSupplier(){
        supplier = null;
    }
}
