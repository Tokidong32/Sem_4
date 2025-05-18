package se.kth.iv1350.pointofsale.integration;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pointofsale.DTO.DiscountDTO;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
import se.kth.iv1350.pointofsale.model.Sale;

public class DiscountDatabaseTest {
    private DiscountDatabase instanceUnderTest;

    @BeforeEach
    void setUp() {
        instanceUnderTest = DiscountDatabase.getInstance();
    }

    @AfterEach
    void tearDown() {
        instanceUnderTest = null;
    }

    @Test
    /**
     * testar om databasen hittar reor med medlems nummeret
     */
    void testLookupCustomerIdDiscount(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        ReciptDTO fakRecipt = fakeSale.getReciptDTO();
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(fakRecipt, 1);
        double expectedResult = 0.50;
        if(testDiscont.getDiscountFromCustomerId()!=expectedResult){
            fail("fel rea "+testDiscont.getDiscountFromCustomerId()+" != "+expectedResult);
        }
    }
    @Test
    /**
     * testar om databasen behandlar ett kunds Id som inte finns databasen på rättsett
     */
    void testLookupNonCustomerDiscount(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        ReciptDTO fakRecipt = fakeSale.getReciptDTO();
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(fakRecipt, 2);
        double expectedResult = 0.00;
        if(testDiscont.getDiscountFromCustomerId()!=expectedResult){
            fail("fel rean ska vara 0");
        }
    }
    @Test
    /**
     * testar om kvittot är null
     */
    void testLookupWithNullRecipt(){
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(null, 2);
        double expectedResult = 0.00;
        if(testDiscont.getDiscountFromTotalPrice() != expectedResult){
            fail("fel rean ska vara 0");
        }
    }
    @Test
    /**
    * testar om databasen hittar en existerande rea på en vara
    */
    void testLookupItemDiscount(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        ReciptDTO fakRecipt = fakeSale.getReciptDTO();
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(fakRecipt, 2);
        double expectedResult = 5.00;
        if(testDiscont.getSumFromItems() != expectedResult){
            fail("fel rean ska vara " + expectedResult + ". inte " + testDiscont.getSumFromItems());
        }
    }
    @Test
    /**
     * testar att varan som inte är rea på behandlas korrekt
     */
    void testLookupNonItemDiscount(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(2,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        ReciptDTO fakRecipt = fakeSale.getReciptDTO();
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(fakRecipt, 2);
        double expectedResult = 0.00;
        if(testDiscont.getSumFromItems() != expectedResult){
            fail("fel rean ska vara " + expectedResult + ". inte " + testDiscont.getSumFromItems());
        }
    }
    @Test
    /**
     * testar om rean på totalpriset hittas i databasen
     */
    void testLookupWithTotalPrice(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",20));
        ReciptDTO fakRecipt = fakeSale.getReciptDTO();
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(fakRecipt, 2);
        double expectedResult = 0.05;
        if(testDiscont.getDiscountFromTotalPrice() != expectedResult){
            fail("fel rean ska vara " + expectedResult + ". inte " + testDiscont.getDiscountFromTotalPrice());
        }
    }
    @Test
    /**
     * testar om total priset inte når upp till spärren 
     */
    void testLookupWithLowTotalPrice(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        ReciptDTO fakRecipt = fakeSale.getReciptDTO();
        DiscountDTO testDiscont = instanceUnderTest.lookupDiscount(fakRecipt, 2);
        double expectedResult = 0.00;
        if(testDiscont.getDiscountFromTotalPrice() != expectedResult){
            fail("fel rean ska vara " + expectedResult + ". inte " + testDiscont.getDiscountFromTotalPrice());
        }
    }
}