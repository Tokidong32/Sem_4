package se.kth.iv1350.pointofsale.model;
/***************************************************************************************************
 * detta är klassen som representerar en betalning 
 * 
 * @author Tomas Ålund
 */
public class Payment {
    private double paydAmount;
    private double change;
    /**
     * skapar en ny instans av betalningen
     * @param paydAmount    värdet som kunden betalat
     */
    public Payment(double paydAmount){
        this.paydAmount = paydAmount;
    }
    /**
     * räkanar ut växeln som ska ges tillbaka till kunden
     * @param totalPrice priset som ska betalas
     * @return växeln
     */
    double calculateChange(double totalPrice) {
        this.change = paydAmount - totalPrice;
        return this.change;
    }
    /**
     * hämta vad som betalats
     * @return vad som betals
     */
    double getPaydAmount() {
        return paydAmount;
    }
    /**
     * hämta den uträknade växeln
     * @return växeln
     */
    double getChange() {
        return change;
    }
}
