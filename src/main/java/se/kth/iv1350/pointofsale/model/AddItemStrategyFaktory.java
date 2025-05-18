package se.kth.iv1350.pointofsale.model;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
/**
 * factory klassen som väljer ock skapar vilken strategi som ska användas när man lägger till en vara
 * 
 * @author Tomas Ålund
 */
public class AddItemStrategyFaktory {
    private static final AddItemStrategyFaktory currentInstance = new AddItemStrategyFaktory();

    private AddItemStrategyFaktory(){ 
    }
    /**
     * hämtar en refferens till den ända instansen av fabriken
     * @return nuvarande instans
     */
    static AddItemStrategyFaktory getInstance(){
        return currentInstance;
    }
    /**
     * väljer en den bästa strategin för hur man lägger till en vara 
     * @param listOfItems Listan som varan ska läggas till i
     * @param itemToAdd varan som ska läggas till
     * @return vilken medod som ska användas för bästa resultat
     */
    AddItemStrategys getStrategy(List<Item> listOfItems, ItemDTO itemToAdd){
        Item serchedItem = new Item(itemToAdd);
        if(listOfItems.contains(serchedItem)){
            return new ExistingItemStrategy();
        }
        else{
            return new NewItemSrategy();
        }
    }
}   
