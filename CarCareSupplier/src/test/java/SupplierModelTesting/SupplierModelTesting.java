package SupplierModelTesting;
import Model.Supplier;
import org.junit.jupiter.api.*;
public class SupplierModelTesting {
    static Supplier supplier;

    @BeforeAll
    public static void createSupplier(){
        supplier = new Supplier();
    }
    @BeforeEach
    public void setSupplier(){
        supplier.setSupplierName("Adonis");
        supplier.setSupplierAddress("75, Beverly Hills");
        supplier.setSupplierPhone("+1 0734 3323");
        supplier.setSupplierCountry("USA");
        supplier.setSupplierCategory("Engine");
    }
    @Test
    public void testSupplier(){
        Assertions.assertEquals("Adonis", supplier.getSupplierName());
        Assertions.assertEquals("75, Beverly Hills", supplier.getSupplierAddress());
        Assertions.assertEquals("+1 0734 3323", supplier.getSupplierPhone());
        Assertions.assertEquals("USA", supplier.getSupplierCountry());
        Assertions.assertEquals("Engine", supplier.getSupplierCategory());
    }
    @AfterEach
    public void cleanData(){
        supplier.setSupplierName(null);
        supplier.setSupplierAddress(null);
        supplier.setSupplierPhone(null);
        supplier.setSupplierCountry(null);
        supplier.setSupplierCategory(null);
    }
    @AfterAll
    public static void deleteSupplier(){
        supplier = null;
    }
}
