package se.kth.iv1350.pointofsale.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class RegisterTest {
    private Register instanceUnderTest;

    @BeforeEach
    void setUp(){
        instanceUnderTest = new Register(1000);
    }
    @AfterEach
    void tearDown(){
        instanceUnderTest = null;
    }
    /**
     * testar om det går att hämta nuvarande saldot
     */
    @Test
    void testGetBalance(){
        assertEquals(1000, instanceUnderTest.getCurrentBalance(),
        "gick inte att hämta nuvarande saldo");
    }
    /**
     * testar om det går att uppdatera saldot
     */
    @Test
    void testUpdateBalance(){
        Payment fakePayment = new Payment(100);
        fakePayment.calculateChange(100);
        instanceUnderTest.updateBalance(fakePayment);
        assertEquals(1100, instanceUnderTest.getCurrentBalance(),
        "saldot uppdateras på fel sätt");
    }
    /**
     * testar att uppdatera saldot som är DOUBLE_MAX med DOUBLE_MAX
     */
    @Test
    void testUpdateMaxBalance(){
        instanceUnderTest = new Register(Double.MAX_VALUE);
        Payment fakePayment = new Payment(Double.MAX_VALUE);
        fakePayment.calculateChange(Double.MAX_VALUE);
        instanceUnderTest.updateBalance(fakePayment);
        assertEquals(Double.MAX_VALUE, instanceUnderTest.getCurrentBalance(),
        "Saldot ska stanna på max");
    }
}
