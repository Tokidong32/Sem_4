package se.kth.iv1350.pointofsale.integration;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
import se.kth.iv1350.pointofsale.model.Sale;

public class PrinterTest {
    private Printer printer;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        printer = new Printer();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        printer = null;
    }

    @Test
    /**
     * testar att kvitot kan skrivas ut 
     */
    void testPrintRecipt(){
        Sale fakeSale = new Sale();
        fakeSale.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",1));
        ReciptDTO fakeRecipt = fakeSale.getReciptDTO();
        String expectedRow = "Hamer                       1 x   20:50     20:50 SEK";
        printer.printRecipt(fakeRecipt);
        String recipt = outContent.toString();
        if(!recipt.contains(expectedRow)){
            fail("felacktig utskrift av kvittot");
        }
    }
    @Test
    /**
     * testar om det går att skriva ut null
     */
    void testPrintNull(){
        printer.printRecipt(null);
        String output = outContent.toString();
        assertTrue(output.isEmpty(), "Inget bör skrivas ut vid null");
    }
}
