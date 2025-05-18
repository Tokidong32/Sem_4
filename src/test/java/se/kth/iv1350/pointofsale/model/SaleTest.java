package se.kth.iv1350.pointofsale.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pointofsale.DTO.DiscountDTO;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
import se.kth.iv1350.pointofsale.DTO.SaleDTO;

public class SaleTest {
    private Sale testedSale;

    @BeforeEach
    void setUp() {
        testedSale = new Sale();
    }
    @AfterEach
    void tearDown() {
        testedSale = null;
    }
    /**
     * testar om det går att lägga till en vara i nuvarande köp
     */
    @Test
    void testAddNewItem() {
        ItemDTO fakItemDTO = new ItemDTO(1,20.50,0.06,"Hamer",
                                    "A steel head with a wooden handel.",1);
        testedSale.addItem(fakItemDTO);
        assertTrue(testedSale.getReciptDTO().getListOfItems().size() > 0 , 
        "längden på listan ska vara större än noll om det finns några varor i den");
    }
    /**
     * testar att SaleDTOn uppdateras korrekt
     */
    @Test
    void testGetSaleDTO() {
        ItemDTO fakItemDTO = new ItemDTO(1,20.50,0.06,"Hamer",
                                    "A steel head with a wooden handel.",1);
        SaleDTO DTOToCheck = testedSale.addItem(fakItemDTO);
        assertEquals(20.50 , DTOToCheck.getRunningTotalPrice(),"Running total uppdateras inte korrekt");
    }
    /**
     * testar om det går att lägga till ett null objekt
     */
    @Test
    void testAddNullItem(){
        SaleDTO fakeSaleInfo = testedSale.addItem(null);
        assertNull(fakeSaleInfo,"om null kommer in kommer null ut");
    }
    /**
     * testar om det går att hämta ReciptDTO
     */
    @Test
    void testGetReciptDTO(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        assertNotNull(testedSale.getReciptDTO(),"det gick inte att hämta kvittot");
    }
    
    /**
     * testar att DTO representerar gällande kvitto
     */
    @Test
    void testGetReciptDTOEquals(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        ReciptDTO reciptToCompear = testedSale.getReciptDTO();
        double expectedResult = 20.5 * 10;
        assertEquals(expectedResult ,reciptToCompear.getTotalPrice() , 
        "det är olika data i DTOn och kvittot");
    }
    /**
     * testar om det går att aplecera rea från specifika varor
     */
    @Test 
    void testApplyDiscount(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        DiscountDTO fakeDiscount = new DiscountDTO(100, 0, 0);
        SaleDTO returnedSale = testedSale.applyDiscounts(fakeDiscount);
        assertEquals(205-100, returnedSale.getRunningTotalPrice(),  
        "fel applicering av applicering av rea");
    }
    /**
     * testar om det går att applicera en rea som procentsats
     */
    @Test
    void testApplyDiscountProcent(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        DiscountDTO fakeDiscount = new DiscountDTO(0, 0.5, 0);
        SaleDTO returnedSale = testedSale.applyDiscounts(fakeDiscount);
        double expectedResult = 20.50 * 10 * 0.5;
        assertEquals(expectedResult, returnedSale.getRunningTotalPrice());
    }
    /**
     * testar att applicera null som rea
     */
    @Test
    void testApplyNullDiscount(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        SaleDTO returnedSale = testedSale.applyDiscounts(null);
        double expectedResult = 20.50 * 10;
        assertEquals(expectedResult, returnedSale.getRunningTotalPrice(), 
        "om det kommer in ett null element ska ingen rea appliceras");
    }

    /**
     * testar om det går att genomföra en betalning
     */
    @Test
    void testPay(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        assertNotNull(testedSale.pay(new Payment(1000)),
        "Det ska komma tillbaka en DTO om betalningen gått igenom");
    }
    /**
     * tastar att betalningen korrekt gått igenom
     */
    @Test
    void testPaymentPassedCorrekt(){
        testedSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        Payment fakePayment = new Payment(1000);
        ReciptDTO fakerecipt = testedSale.pay(fakePayment);
        assertEquals(fakePayment.getChange(), fakerecipt.getChange(),
        "växeln ska vara uträknad för att det köpet ska ha behandlats korrekt");
    }
}
