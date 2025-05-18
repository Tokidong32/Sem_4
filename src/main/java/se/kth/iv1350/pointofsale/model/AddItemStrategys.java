package se.kth.iv1350.pointofsale.model;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
/**
 * Strategi gränssnitet för hur en algoritmklass för att läggatill en vara ska se ut
 * @author Tomas Ålund
 */
public interface AddItemStrategys {
    /**
     * lägger till den givna varan i listan
     * @param listOfItems
     * @param itemToAdd
     * @return
     */
    public ItemDTO addItemDTO(List<Item> listOfItems  ,ItemDTO itemToAdd);
}
