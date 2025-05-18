package se.kth.iv1350.pointofsale.controller;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
import se.kth.iv1350.pointofsale.DTO.SaleDTO;
import se.kth.iv1350.pointofsale.exceptions.CantReachDatabaseException;
import se.kth.iv1350.pointofsale.exceptions.NoItemFoundException;
import se.kth.iv1350.pointofsale.integration.AccountingSystem;
import se.kth.iv1350.pointofsale.integration.DiscountDatabase;
import se.kth.iv1350.pointofsale.integration.InventorySystem;
import se.kth.iv1350.pointofsale.integration.Printer;
import se.kth.iv1350.pointofsale.model.Register;

public class ControllerTest {

    private Controller contr;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        try {
            AccountingSystem accountingSystem = AccountingSystem.getInstance();
            DiscountDatabase discountDatabase = DiscountDatabase.getInstance();
            InventorySystem inventorySystem = InventorySystem.getInstance();
            Printer printer = new Printer();
            contr = new Controller(accountingSystem, discountDatabase, inventorySystem, printer);
            System.setOut(new PrintStream(outContent));
            contr.startSale();
        } catch (Exception e) {
            fail("!! setup failed !!");
        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        contr = null;
    }
    /**
     * testar om det går att starta en Sale
     */
    @Test
    void testStartSale(){
        try{
        SaleDTO fakeSale = contr.addToSale(1, 1);
        assertNotNull(fakeSale, "fakeSale ska inte vara null");
        }catch(CantReachDatabaseException e){
            fail("Det gick inte att lägga till rätt vara");
        }
    }
    /**
     * testar att vi kan lägga till varor
     */
    @Test
    void testAddingValidItem() {
        try{
            contr.addToSale(1, 1);
            ReciptDTO fakeReciptDTO = contr.pay(100);
            assertTrue(fakeReciptDTO.getListOfItems().size() > 0,
                                             "det har inte laggts till en vara på korrekt sätt");
        }catch(Exception e){
            fail("det ska inte kasstas några undantag");
        }
    }
    /**
     * testar att det inte går att lägga till varor som inte finns i lagret
     */
    @Test
    void testAddInvalidItem() {
        try{
            contr.addToSale(3, 1);
            fail("det kastades inte något undantas");
        }catch(NoItemFoundException e){
            assertEquals(3, e.getscanedId(), "undantaget har fel information");
        }catch(Exception e){
            fail("ett annat undantag har kastats");
        }
    }
    /**
     * testar om det fångas rätt undantag vid fel med databasen
     */
    @Test  
    void testCatchConectionFailException(){
        try {
            contr.addToSale(-1, 1);
            fail("det kastades inte något undantag");
        } 
        catch (CantReachDatabaseException e) {
            assertEquals("Cant reach the Inventory databas",
                                         e.getMessage(), "fel medelande in undantaget");
        }
        catch (Exception e){
            fail("fel undantag har kastasts");
        }
    }
    /**
     * testar om rean apliceras på köpet
     */
    @Test
    void testApplyDiscount(){
        try{
            SaleDTO orgSale = contr.addToSale(1, 1);
            SaleDTO uppdateedSale = contr.applyDiscount(1);
            if(uppdateedSale.getRunningTotalPrice() == orgSale.getRunningTotalPrice() ){
                fail("ingen aplicerad rea");
            }
        }catch(Exception e){
            fail("det kastades ett oväntat undantag");
        }
    }
    /**
     * testar om köpet kan betalas
     */
    @Test
    void testPay(){
        try{
            contr.addToSale(1, 1);
            ReciptDTO fakeRecipt = contr.pay(200);
            assertNotNull(fakeRecipt, "retur värdet ska vara ett ReciptDTO");
        }catch(Exception e){
            fail("det kastades ett oväntat undantag");
        }
    }
    /**
     * tastar att betalningen korrekt gått igenom
     */
    @Test
    void testPaymentPassedCorrekt(){
        try{
            contr.addToSale(1, 10);
            ReciptDTO fakerecipt = contr.pay(1000);
            assertEquals(1000 - 20.5 * 10, fakerecipt.getChange(),
            "växeln ska vara uträknad för att det köpet ska ha behandlats korrekt");
        }catch(Exception e){
            fail("det kastades ett oväntat undantag");
        }
    }
    /**
     * testar om saldot i kassan har uppdaterats. 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @Test
    void testPaymentPassedToRegister() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        try{
            contr.addToSale(1, 10);
            double paydAmount = 300;
            ReciptDTO fakeRecipt = contr.pay(paydAmount);
            
            Field registerField = Controller.class.getDeclaredField("register");
            registerField.setAccessible(true);
            Register registerInstance = (Register) registerField.get(contr);

            Field balanceField = Register.class.getDeclaredField("currentBalance");
            balanceField.setAccessible(true);
            double actualBalance = (double) balanceField.get(registerInstance);

            double expectedBalance = 1000 +(paydAmount - fakeRecipt.getChange());
            assertEquals(expectedBalance, actualBalance,
            "Kassans saldo uppdaterades inte korrekt.");
        }catch(Exception e){

        }
    }
}