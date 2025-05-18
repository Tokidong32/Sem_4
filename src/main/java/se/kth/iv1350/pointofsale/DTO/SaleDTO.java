package se.kth.iv1350.pointofsale.DTO;
/***************************************************************************************************
 * denna DTO klass innehåller informationen av vad som skannats och ska visas för kunden 
 * 
 * @author Tomas Ålund
 */
public final class SaleDTO {
    private final double runningTotalPrice;
    private final double runningTotalTax;
    private final ItemDTO addedItem;

    /**
     * skapar en instanse av DTOn
     * @param runningTotalPrice nuvarande tatala priset
     * @param runningTotalTax   nuvarande moms
     * @param addedItem         den tillagdda varan
     */
    public SaleDTO(double runningTotalPrice, double runningTotalTax, ItemDTO addedItem){
        this.addedItem = addedItem;
        this.runningTotalPrice = runningTotalPrice;
        this.runningTotalTax = runningTotalTax;
    }
    /**
     * skapar en instans utan en tillagd vara
     * @param runningTotalPrice nuvarande total pris
     * @param runningTotalTax   nuvarade moms
     */
    public SaleDTO(double runningTotalPrice, double runningTotalTax) {
        this.runningTotalTax = runningTotalTax;
        this.runningTotalPrice = runningTotalPrice;
        this.addedItem = null;
    }
    /**
     * hämtar det nuvarande totala priset
     * @return nuvarande pris
     */
    public double getRunningTotalPrice(){
        return this.runningTotalPrice;
    }
    /**
     * hämta nuvarande moms
     * @return nuvarande moms
     */
    public double getRunningTatalTax(){
        return this.runningTotalTax;
    }
    /**
     * hämtar den tillaggda varan
     * @return den tillagda vara
     */
    public ItemDTO getItemDTO(){
        return this.addedItem;
    }
}
