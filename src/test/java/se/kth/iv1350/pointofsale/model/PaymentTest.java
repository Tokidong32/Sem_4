package se.kth.iv1350.pointofsale.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    private Payment instanceUnderTest;

    @BeforeEach
    void setUp(){
        instanceUnderTest = new Payment(100);
    }
    @AfterEach
    void tearDown(){
        instanceUnderTest = null;
    }
    @Test
    /**
     * testar om det går att få tillbaka växeln
     */
    void testCalculateChange(){
        assertEquals(50, instanceUnderTest.calculateChange(50),
        "korrekt växel går inte att få ut");
    }
    @Test
    /**
     * testar om det går att hämta växeln
     */
    void testGetChange(){
        instanceUnderTest.calculateChange(50);
        assertEquals(50.0 , instanceUnderTest.getChange(),
        "Det gick inte att hämta växelen");
    }
    @Test
    /**
     * testar om det går att hämta vad som betalats
     */
    void testGetPaydAmount(){
        assertEquals(100, instanceUnderTest.getPaydAmount(), 
        "Det gick inte att hämta vad som betalats");
    }
}
