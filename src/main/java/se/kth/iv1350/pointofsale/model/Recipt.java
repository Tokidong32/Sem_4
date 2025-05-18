package se.kth.iv1350.pointofsale.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
/***************************************************************************************************
 * detta är skrivaren som skriver ut alla kviton som genomförs i systemet
 * 
 * @author Tomas Ålund
 */
public class Recipt {
    private double totalPrice;
    private double totalVat;
    private LocalDateTime timeStamp;
    private List<Item> listOfItems;
    private double paydAmount;
    private double change;
    private AddItemStrategyFaktory strategyFaktory = AddItemStrategyFaktory.getInstance();
    /**
     * konstruktorn som skapar en instans av ett kvitto med den nuvarande tiden 
     * @param timeStamp nuvarande tid
     */
    Recipt(LocalDateTime timeStamp){
        this.timeStamp = timeStamp;
        this.listOfItems = new ArrayList<Item>();
    }
    /**
     * Metoden lägger till en givna vara i kvittits lista med varor, om den givana varan
     * redan har lagts till ökas dess antal bara. slutligen uppdateras löpande pris och 
     * moms
     * 
     * @param itemToAdd representerar varan som har scanats och ska läggas till
     * @return infon om den tillaggda varan
     */
    ItemDTO addItem(ItemDTO itemToAdd){
        ItemDTO addedItem = null;
        AddItemStrategys bestStrategy = strategyFaktory.getStrategy(listOfItems,itemToAdd);
        addedItem = bestStrategy.addItemDTO(listOfItems,itemToAdd);

        updateTotalPrice(itemToAdd.getPrice(), itemToAdd.getAmount());
        updateTotalVat(itemToAdd.getTax(), itemToAdd.getPrice(), itemToAdd.getAmount());
        return addedItem;
    }
    private void updateTotalPrice(double priceOfItem, int amount){
        this.totalPrice += priceOfItem * amount;
    }
    private void updateTotalVat(double itemsTax, double priceOfItem, int amount){
        this.totalVat += (itemsTax * priceOfItem) * amount;
    }
    /**
     * hämtar totala priset
     * @return tatala priset
     */
    double getTotalPrice(){
        return this.totalPrice;
    }
    /**
     * hämta tatala momsen
     * @return totala momsen
     */
    double getTotalTax(){
        return this.totalVat;
    }
    /**
     * hämtar kvitot som en DTO
     * @return kvittot som DTO
     */
    ReciptDTO getReciptDTO(){
        List<ItemDTO> listOfItemsDTO = convertToItemDTO();
        return new ReciptDTO(totalPrice, totalVat, timeStamp, listOfItemsDTO, paydAmount, change);
    }
    private List<ItemDTO> convertToItemDTO(){
        List<ItemDTO> listOfItemsDTO = new ArrayList<ItemDTO>();
        
        for (Item currentItem : listOfItems) {
            listOfItemsDTO.add(currentItem.convertToDTO());
        }
        return listOfItemsDTO;
    }
    /**
     * uppdaterar total priset med summan från rean av tilllaggda varor
     * @param sumFromItems summan som ska dras bort
     */
    void applyDiscountSum(double sumFromItems) {
        totalPrice -= sumFromItems;
    }
    /**
     * uppdaterar totala pricet med en procentsats
     * @param discountPercentages hur många procent rea
     */
    void applyDiscountPercentages(double discountPercentages) {
        totalPrice = totalPrice * (1.0 - discountPercentages);
    }
    /**
     * sparar det betalda värdet
     * @param paydAmount det betalda värdet
     */
    void setPaydAmount(double paydAmount) {
        this.paydAmount = paydAmount;
    }
    /**
     * spara växeln
     * @param change växeln
     */
    void setChange(double change) {
        this.change = change;
    }
}
