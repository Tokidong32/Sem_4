package se.kth.iv1350.pointofsale.DTO;
/***************************************************************************************************
 * detta är en DTO som håller de validerade reor
 * 
 * @author Tomas Ålund
 */
public final class DiscountDTO {
    private final double sumFromItems;
    private final double discountFromTotalPrice;
    private final double discountFromCustomerId;

    public DiscountDTO(double sumFromItems, double discountFromTotalPrice, double discountFromCustomerId){
        this.sumFromItems = sumFromItems;
        this.discountFromCustomerId = discountFromCustomerId;
        this.discountFromTotalPrice = discountFromTotalPrice;
    }
    /**
     * Hämtar den totala summan från alla artiklar utan moms.
     *
     * @return Summan av artikelpriser.
     */
    public double getSumFromItems() {
        return this.sumFromItems;
    }

    /**
     * Hämtar rabattprocenten baserat på det totala priset.
     *
     * @return Rabatt i procent från totalpris.
     */
    public double getDiscountFromTotalPrice() {
        return this.discountFromTotalPrice;
    }

    /**
     * Hämtar rabattprocenten som kunden har baserat på sitt kundID.
     *
     * @return Rabatt i procent från kund-ID.
     */
    public double getDiscountFromCustomerId() {
        return this.discountFromCustomerId;
    }
}
