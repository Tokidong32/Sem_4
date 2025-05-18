package se.kth.iv1350.pointofsale.model;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;

public class ExistingItemStrategy implements AddItemStrategys{
    /**
     * skapar en instans av metoden ExistingItemStratagy
     * @author Tomas Ålund
     */
    ExistingItemStrategy(){
    }
    /**
     * lägger till en vara som redan finns i varolistan
     * @param listOfItems listan somvaran ska läggas till i
     * @param itemToAdd varan som ska läggas till
     * @return data om den tilllaggda varan
     */
    @Override
    public ItemDTO addItemDTO(List<Item> listOfItems, ItemDTO itemToAdd){
        ItemDTO addedItemDTO = null;
        for (Item item : listOfItems) {
            if(item.getId() == itemToAdd.getId()){
                item.setAmount(item.getAmount() + itemToAdd.getAmount());
                addedItemDTO = item.convertToDTO();
            }
        }
        return addedItemDTO;
    }
}
