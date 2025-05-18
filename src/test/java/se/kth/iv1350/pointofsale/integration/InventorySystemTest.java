package se.kth.iv1350.pointofsale.integration;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.exceptions.ConectionFailException;
import se.kth.iv1350.pointofsale.exceptions.NoItemFoundException;
import se.kth.iv1350.pointofsale.model.Item;
import se.kth.iv1350.pointofsale.model.Sale;

public class InventorySystemTest {
    private InventorySystem inventorySystem;

    @BeforeEach
    void setup(){
        inventorySystem = InventorySystem.getInstance();
    }
    @AfterEach
    void tearDown(){
        inventorySystem = null;
    }
    @Test
    /**
     * testar att det går att hämta en vara
     */
    void testLookupItem(){
        try{
            ItemDTO fetchtItem = inventorySystem.lookUpItem(1, 1);
            String expectedResult = "Hamer";
            assertEquals(expectedResult, fetchtItem.getName(),"varan hämtas inte korrekt");
        }
        catch(ConectionFailException e){
            fail("kan inte ansluta till database");
        }
        
    }
    @Test
    /**
     * testar att rätt undantag skapas när en vara inte finns
     */
    void testLookupNonItem(){
        try {
            inventorySystem.lookUpItem(3, 1);
            fail("undantaget slängdes inte korrekt");
        } catch (NoItemFoundException e){
            assertEquals(3, e.getscanedId(), "undantaget innehåller fel info");
        } catch (ConectionFailException e){
            fail("kan inte ansluta till database");
        }
    }
    @Test
    /**
     * testar att att det går att kasta ett undantag när databasen inte går att nås
     */
    void testNoConectionToDataBase(){
        try {
            inventorySystem.lookUpItem(-1, 1);
            fail("undantaget slängdes inte korrekt");
        } catch (NoItemFoundException e){
            fail("fel undantag har kastats");
        } catch (ConectionFailException e){
            assertEquals("192.168.1.1", e.getIpOfDatabase(),"inte rätt information i undantaget");
        }
    }
    @Test
    /**
     * testar om det går att uppdatera lagret
     */
    void testUpdateInventory(){
        try{
            Field field = InventorySystem.class.getDeclaredField("storedItems");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Item> storedItems = (List<Item>) field.get(inventorySystem);
            
            int fakeAmount = 10;
            int expectedResult = storedItems.get(0).getAmount() - fakeAmount;
            Sale fakeSale = new Sale();
            fakeSale.addItem(inventorySystem.lookUpItem(1, 10));
            inventorySystem.updateInventory(fakeSale.getReciptDTO());
            
            
            assertEquals(expectedResult,storedItems.get(0).getAmount(),
                                            "Det gick inte att uppdatera saldot");
            
        }
        catch(Exception e){
            fail("det gick inte att utvärdera då det ett kastades oväntat undantag");
        }
    }
    @Test
    /**
     * testar att de andra varorna inte uppdateras
     */
    void testNotUpdateNonSoldItem() {
        try{
            Field field = InventorySystem.class.getDeclaredField("storedItems");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Item> storedItems = (List<Item>) field.get(inventorySystem);
            
            int expectedResult = storedItems.get(0).getAmount();

            Sale fakeSale = new Sale();
            fakeSale.addItem(inventorySystem.lookUpItem(1, 10));
            inventorySystem.updateInventory(fakeSale.getReciptDTO());

            assertNotEquals(expectedResult,storedItems.get(0).getAmount(),
                                    "saldot uppdaterades vilket det inte ska göra");
        
        }catch(Exception e){
            fail("det gick inte att utvärdera då det ett kastades oväntat undantag");
        }
    }

}
