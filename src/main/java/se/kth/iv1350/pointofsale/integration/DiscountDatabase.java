package se.kth.iv1350.pointofsale.integration;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.DiscountDTO;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;

/***************************************************************************************************
 * Denna klass har koll på alla giltiga rabbater som kan apliceras i systemet
 * 
 * @author Tomas Ålund
 */

public class DiscountDatabase {
    private static final DiscountDatabase currentInstance = new DiscountDatabase();
    private List<DiscountCustomer> discountCustomers;
    private List<DiscountItem> discountItems;
    private List<DiscountTotPrice> discountTotPrices;

    /**
     * Konstruktorn som skapar listor med giltiga reor
     */
    private DiscountDatabase(){
        discountCustomers = new ArrayList<DiscountCustomer>();
        discountItems = new ArrayList<DiscountItem>();
        discountTotPrices = new ArrayList<DiscountTotPrice>();
        
        addDiscountCustomer(1,0.50);
        addDiscountItem(1,5.00);
        addDiscountTotPrices(200,0.05);
    }
    /**
     * hämtar en referens till den ända instansen av DiscountDatabase
     * @return referens till databasen
     */
    public static DiscountDatabase getInstance(){
        return currentInstance;
    }
    /**
     * metoden letar upp giltiga reor beroende på det köpet som skickas med
     * @param recipt är det sluttgiltiga köpet
     * @param customerId är kundens medlems nummer
     * @return alla gilltiga reor kunden uppfyllde
     */
    public DiscountDTO lookupDiscount(ReciptDTO recipt, int customerId){
        DiscountDTO validDiscounts;
        double validItemDiscount = 0.0;
        double validTotPriceDiscount = 0.0;
        double validCustomerDiscount = 0.0;
        if (recipt != null) {
            validItemDiscount = lookupItemDiscounts(recipt);
            validTotPriceDiscount = lookupTotPriceDiscount(recipt);
        }
        validCustomerDiscount = lookupCustomerDiscount(customerId);
        validDiscounts = new DiscountDTO(validItemDiscount, validTotPriceDiscount, validCustomerDiscount);
        return validDiscounts;
    }
    private double lookupTotPriceDiscount(ReciptDTO recipt) {
        double foundDiscountProcent = 0.0;
        double currentTotalPrice = recipt.getTotalPrice();
        for (DiscountTotPrice currentDiscountTotPrice : discountTotPrices) {
            if(currentDiscountTotPrice.totalPrice >= currentTotalPrice){
                break;
            }
            else{
                foundDiscountProcent = currentDiscountTotPrice.discountForTotal;
            }
        }
        return foundDiscountProcent;
    }
    private double lookupItemDiscounts(ReciptDTO recipt) {
        double foundDiscountSum = 0.0;
        List<ItemDTO> boughtItems = recipt.getListOfItems();
        if(boughtItems != null){
            for (ItemDTO currentItem : boughtItems) {
                int currentItemId = currentItem.getId();
                for (DiscountItem discountedItem : discountItems) {
                    if(discountedItem.itemId == currentItemId){
                        foundDiscountSum += discountedItem.discountOnItem * currentItem.getAmount();
                        break;
                    }
                }
            }
        }
        return foundDiscountSum;
    }
    private double lookupCustomerDiscount(int customerId) {
        double foundCustomerProcent = 0.0;
        for (DiscountCustomer currentDiscountCustomer : discountCustomers) {
            if(currentDiscountCustomer.getId() == customerId){
                foundCustomerProcent = currentDiscountCustomer.getDiscount();
                break;
            }
        }
        return foundCustomerProcent;
    }
    private void addDiscountCustomer(int customerId, double discount){
        DiscountCustomer discountCustomer = new DiscountCustomer(customerId, discount);
        discountCustomers.add(discountCustomer);
    }
    private void addDiscountItem(int itemId, double discount){
        DiscountItem discountItem = new DiscountItem(itemId, discount);
        discountItems.add(discountItem);
    }
    private void addDiscountTotPrices(double totalPrice, double discount){
        DiscountTotPrice discountTotPrice = new DiscountTotPrice(totalPrice, discount);
        discountTotPrices.add(discountTotPrice);
    }
    
    private class DiscountCustomer {
        private int customerId;
        private double discountProcent;

        DiscountCustomer(int customerId, double discountProcent) {
            this.customerId = customerId;
            this.discountProcent = discountProcent;
        }
        public int getId(){
            return this.customerId;
        }
        public double getDiscount(){
            return this.discountProcent;
        }

    }  
    private class DiscountItem {
        private int itemId;
        private double discountOnItem;
    
        public DiscountItem(int itemId, double discountOnItem) {
            this.itemId = itemId;
            this.discountOnItem = discountOnItem;
        }
    }   
    private class DiscountTotPrice {
        private double totalPrice;
        private double discountForTotal;
    
        public DiscountTotPrice(double totalPrice, double discountForTotal) {
            this.totalPrice = totalPrice;
            this.discountForTotal = discountForTotal;
        }
    }
}
