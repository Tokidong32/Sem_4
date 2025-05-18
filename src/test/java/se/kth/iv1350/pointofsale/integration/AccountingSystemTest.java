package se.kth.iv1350.pointofsale.integration;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pointofsale.DTO.ReciptDTO;

public class AccountingSystemTest {
    private AccountingSystem testAcccountingSystem;
    
    @BeforeEach
    public void setUp() {
        testAcccountingSystem = AccountingSystem.getInstance();
    }
    @AfterEach
    public void tearDown() {
        testAcccountingSystem = null;
    }
    /**
     * Testar att en försäljning startats och kan hanteras utan fel
     */
    @Test
    public void testBookkeepSale() {
        int expectedResult = testAcccountingSystem.getAmountOfSales() + 1;
        ReciptDTO fakeReciptDTO = new ReciptDTO(0, 0,
                                     null, null, 0, 0);
        testAcccountingSystem.bookkeepSale(fakeReciptDTO);
        assertEquals(expectedResult, testAcccountingSystem.getAmountOfSales(),
                                                        "kvittot har inte lagts till");
    }
}
