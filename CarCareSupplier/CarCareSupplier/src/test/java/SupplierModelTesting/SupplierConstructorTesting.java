package SupplierModelTesting;
import Model.Supplier;
import org.junit.jupiter.api.*;
public class SupplierConstructorTesting {
    static Supplier supplier;

    @BeforeAll
    public static void createSupplier(){
        supplier = new Supplier();
    }
    @BeforeEach
    public void setSupplier(){
        supplier.supplierName = "Adonis";
        supplier.supplierAddress = "75, Beverly Hills";
        supplier.supplierPhone = "+1 0734 3323";
        supplier.supplierCountry = "USA";
        supplier.supplierCategory = "Engine";
    }
    @Test
    public void testSupplier(){
        Assertions.assertEquals("Adonis", supplier.supplierName);
        Assertions.assertEquals("75, Beverly Hills", supplier.supplierAddress);
        Assertions.assertEquals("+1 0734 3323", supplier.supplierPhone);
        Assertions.assertEquals("USA", supplier.supplierCountry);
        Assertions.assertEquals("Engine", supplier.supplierCategory);
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
