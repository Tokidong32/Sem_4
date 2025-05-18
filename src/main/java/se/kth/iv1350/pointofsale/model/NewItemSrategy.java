package se.kth.iv1350.pointofsale.model;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;

/**
 * klassen som är metoden för att lägga till en vara i en lista som inte innehåller den givna varan
 */
public class NewItemSrategy implements AddItemStrategys{
    /**
     * skapar en ny instans AddItemSrategy. 
     */
    NewItemSrategy(){
    }
    /**
     * lägger till en ny vara till listan
     * @param listOfItems listan som varan ska läggs till i
     * @param itemToAdd varan som ska läggas till
     * @return data om den tillaggda varan
     */
    @Override
    public ItemDTO addItemDTO(List<Item> listOfItems, ItemDTO itemToAdd){
        listOfItems.add(new Item(itemToAdd));
        return itemToAdd;
    }
}
