package se.kth.iv1350.pointofsale.integration;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
import se.kth.iv1350.pointofsale.exceptions.ConectionFailException;
import se.kth.iv1350.pointofsale.exceptions.NoItemFoundException;
import se.kth.iv1350.pointofsale.model.Item;
/***************************************************************************************************
 * detta är systemet som håller koll på lager varorna, den har tillgång till informationen för varje 
 * vara så som saldo och pris.
 * 
 * @author Tomas Ålund
 */
public class InventorySystem {
    private static final InventorySystem currentInstance = new InventorySystem();
    private List<Item> storedItems;
    
    /**
     * konstroktorn som kommer att skapa objektet och lägga till ett "test lager"
     */
    private InventorySystem(){
        storedItems = new ArrayList<Item>();
        addToStorage(1,20.50,0.06,"Hamer",
                                        "A steel head with a wooden handel.",200);
        addToStorage(2,120.00,0.12,"Nails",
                                                "A pack of 100 steel nails.",100);
    }
    private void addToStorage(int id, double price, double tax, String name,
                                                                     String description, int amount){
        storedItems.add(new Item(id, price, tax, name,description, amount));
    }
    public static InventorySystem getInstance(){
        return currentInstance;
    }
    /**
     * Metdoen tar emot ett Id och ett antal, vidare kollar den upp om det finns en Item 
     * i lagret. 
     * @param scanedId den skanade varans id
     * @param amount antalet av den skanade varan
     * @return scanedItem
     * @throws NoItemFoundExeption om ingen vara hittades
     * @throws ConectionFailException om det inte gick att ansluta till servern
     * 
     */
    public ItemDTO lookUpItem(int scanedId, int amount) throws NoItemFoundException, ConectionFailException{
        if(scanedId == -1){
            throw new ConectionFailException(
                                    "Cant connect to the Inventory database",
                                                    "192.168.1.1","192.168.1.2");
        }
        ItemDTO scanedItem = null;
        for (Item item : storedItems){
            if(item.getId() == scanedId){
                scanedItem = new ItemDTO(item.getId(),item.getPrice(),item.getTax(),item.getName()
                                                                    ,item.getDescription(),amount);
                break;
            }
        }
        if(scanedItem == null){
            throw new NoItemFoundException(scanedId);
        }
        return scanedItem;
    }
    /**
     * kommer att uppdatera lagret genom att subtrahera antalet av de köpta varorna från
     * lager saldot
     * @param currentRecipt det slutgilltiga kvittot
     * @return void
     */
    public void updateInventory(ReciptDTO currentRecipt) {
        for (ItemDTO soldItem : currentRecipt.getListOfItems()) {
            updateItem(soldItem);
        }
    }
    private void updateItem(ItemDTO soldItem){
        for (Item storedItem : storedItems) {
            if(soldItem.getId() == storedItem.getId()){
                if(storedItem.getAmount() - soldItem.getAmount() >= 0)
                    storedItem.setAmount(storedItem.getAmount() - soldItem.getAmount());
                else
                    storedItem.setAmount(0);
            }
        }
    }
}
