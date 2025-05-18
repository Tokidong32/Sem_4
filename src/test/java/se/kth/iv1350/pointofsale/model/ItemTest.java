package se.kth.iv1350.pointofsale.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pointofsale.DTO.ItemDTO;

public class ItemTest {
    private Item instanceUnderTest;

    @BeforeEach
    void setUp(){
        instanceUnderTest = new Item(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",20);
    }
    @AfterEach
    void tearDown(){
        instanceUnderTest = null;
    }
    @Test
    /**
     * testar om det går att ändra varans antal
     */
    void testSetAmount(){
        instanceUnderTest.setAmount(3);
        assertEquals(3, instanceUnderTest.getAmount(), "antalet har inte ändrats");
    }
    @Test
    /**
     * testar att ändra antalet till ett negativt värde
     */
    void testSetNegAmount(){
        instanceUnderTest.setAmount(-3);
        assertEquals(0, instanceUnderTest.getAmount(),
        "kan inte finnas negativa antal varor");
    }
    @Test
    /**
     * testar om det går att få tillbaka den en ItemDTO av nuvarande vara
     */
    void tastConvertToDTO(){
        ItemDTO actualDTO = instanceUnderTest.convertToDTO();
        assertEquals(instanceUnderTest.getName(), actualDTO.getName());
    }
    






}
