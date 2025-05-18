package se.kth.iv1350.pointofsale.model;
import java.time.LocalDateTime;

import se.kth.iv1350.pointofsale.DTO.*;
/***************************************************************************************************
 * denna klass representerar köpet i systemet genom att uppdatera kvittot för köpet, hanter de 
 * inkomande varorna och lägger till dem till kvittot.
 * 
 * @author Tomas Ålund
 */

public class Sale {
    private Recipt currentRecipt;

    /**
     * konstruktorn som kommer att skapa ett kvitto i Sale
     */
    public Sale(){
        this.currentRecipt = new Recipt(createTimeStamp());
    }
    private LocalDateTime createTimeStamp(){
        return LocalDateTime.now();
    }
    /**
     * lägger till det spesifika antalet av den skannade varan till kvittot.
     * 
     * @param itemToAdd innehåller den skannade varan med antalet och beskrivming.
     * @return information om köpet med löpande pris och moms. 
     */
    public SaleDTO addItem(ItemDTO itemToAdd){
        SaleDTO saleInfo = null;
        if(itemToAdd != null){
            ItemDTO addedItem = currentRecipt.addItem(itemToAdd);
            saleInfo = new SaleDTO(currentRecipt.getTotalPrice(), currentRecipt.getTotalTax(), addedItem);
        }
        return saleInfo;
    }
    /**
     * skapar en DTO av det nuvarande kvittot
     * @return Kvittot som en DTO
     */
    public ReciptDTO getReciptDTO() {
        return currentRecipt.getReciptDTO();
    }
    /**
     * applicera de gilltiga reor på totalpriset
     * @param validDiscounts innehåller de gilltiga reor
     * @return uppdaterad SaleInfo som en SaleDTO
     */
    public SaleDTO applyDiscounts(DiscountDTO validDiscounts) {
        if(validDiscounts != null){
        currentRecipt.applyDiscountSum(validDiscounts.getSumFromItems());
        currentRecipt.applyDiscountPercentages(validDiscounts.getDiscountFromCustomerId());
        currentRecipt.applyDiscountPercentages(validDiscounts.getDiscountFromTotalPrice());
        }
        return new SaleDTO(currentRecipt.getTotalPrice(), currentRecipt.getTotalTax());
        
    }
    /**
     * behandlar betalningen av köpet
     * @param currentPayment innehåller beloppet som köpet betalades med 
     * @return ett fullständigt kvitto som en DTO
     */
    public ReciptDTO pay(Payment currentPayment) {
        ReciptDTO fianalRecipt = null;
        if(currentPayment != null){
            double change = currentPayment.calculateChange(currentRecipt.getTotalPrice());
            currentRecipt.setPaydAmount(currentPayment.getPaydAmount());
            currentRecipt.setChange(change);
            fianalRecipt = currentRecipt.getReciptDTO();
        }
        return fianalRecipt;
    }
}
