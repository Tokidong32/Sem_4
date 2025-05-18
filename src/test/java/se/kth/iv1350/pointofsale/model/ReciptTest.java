package se.kth.iv1350.pointofsale.model;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;

public class ReciptTest {
    private Recipt testedRecipt;

    @BeforeEach
    public void setUp(){
        testedRecipt = new Recipt(LocalDateTime.now());
    }
    @AfterEach
    void tearDown(){
        testedRecipt = null;
    }
    /**
     * testar om att det går att lägga till nya varor som inte finns innan
     */
    @Test
    void testAddNewItem(){
        ItemDTO fakeItemDTO = new ItemDTO(1,20.50,0.06,"Hamer",
                                "A steel head with a wooden handel.",1);
        testedRecipt.addItem(fakeItemDTO);
        ItemDTO firstItem = testedRecipt.getReciptDTO().getListOfItems().get(0);
        if(firstItem==null){
            fail("no item added");
        }
    }
    /**
     * testar om den tillagda varan redan finns att antalet ökar korrekt
     */
    @Test
    void testAddSameItem(){
        ItemDTO fakeItemDTO = new ItemDTO(1,20.50,0.06,"Hamer",
                                "A steel head with a wooden handel.",1);
        testedRecipt.addItem(fakeItemDTO);
        testedRecipt.addItem(fakeItemDTO);
        ItemDTO testedItem = testedRecipt.getReciptDTO().getListOfItems().get(0);
        int expectedResult = 2;
        if(testedItem.getAmount() != expectedResult){
            fail("did not sum up the amounts");
        }
    }
    /**
     * testar om det går att få en DTO av Recipten
     */
    @Test
    void testGetReciptDTO(){
        testedRecipt.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        assertNotNull(testedRecipt.getReciptDTO(),"det mot tog inget objekt");
    }
    /**
     * testar att DTO representerar gällande kvitto
     */
    @Test
    void testGetReciptDTOEquals(){
        testedRecipt.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        ReciptDTO reciptToCompear = testedRecipt.getReciptDTO();
        assertEquals(testedRecipt.getTotalPrice(), reciptToCompear.getTotalPrice(), 
        "det är olika data i DTOn och kvittot");
    }
    /*
     * testar om det går att dra av en summa från total priset.
     */
    @Test
    void testApplyDiscountSum(){
        testedRecipt.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        testedRecipt.applyDiscountSum(50.0);
        double expectedResult = 20.50 * 10 - 50;
        assertEquals(expectedResult,testedRecipt.getTotalPrice(),
        "Summan drogs inte av korrekt");
    }
    /**
     * testar att inget dras bort om suman är 0
     */
    @Test
    void testApplyDiscountZeroSum(){
        testedRecipt.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        testedRecipt.applyDiscountSum(0.0);
        double expectedResult = 20.50 * 10 ;
        assertEquals(expectedResult ,testedRecipt.getTotalPrice(),
        "priset ändrades när det inte ska");
    }

    /**
     * tedtar om det att dra bort en given procent av priset
     */
    @Test
    void testApplyDiscountProcent(){
        testedRecipt.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        testedRecipt.applyDiscountPercentages(0.30);
        double expectedResult = (20.50 * 10) * (1 - 0.3);
        assertEquals(expectedResult, testedRecipt.getTotalPrice(),
        "rean aplicerades felaktigt");
    }
    /**
     * testar att inget dras bort om procenten är 0
     */
    @Test
    void testApplyDiscountZeroProcent(){
        testedRecipt.addItem(new ItemDTO(1,20.50,0.06,"Hamer",
        "A steel head with a wooden handel.",10));
        testedRecipt.applyDiscountPercentages(0.0);
        double expectedResult = 20.50 * 10;
        assertEquals(expectedResult ,testedRecipt.getTotalPrice(),
        "priset ändrades när det inte ska");
    }

}
