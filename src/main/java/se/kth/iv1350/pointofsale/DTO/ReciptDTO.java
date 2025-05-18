package se.kth.iv1350.pointofsale.DTO;
import java.time.LocalDateTime;
import java.util.List;
/***************************************************************************************************
 * detta är DTO klassen för att transportera Datan från nuvarande kvitto
 * 
 * @author Tomas Ålund
 */
public final class ReciptDTO {
    private final double totalPrice;
    private final double totalVat;
    private final LocalDateTime timeStamp;
    private final List<ItemDTO> listOfItems;
    private final double paydAmount;
    private final double change;

    /**
     * skapar en instans av DTOn av medföljande data
     * @param totalPrice    totala priset på köpet
     * @param totalVat      totala momsen i priset
     * @param timeStamp     tiden köpet startade
     * @param listOfItems   listan av alla köpta varor
     * @param paydAmount    värdet som kunden betalade
     * @param change        värdet att som kunden ska få tillbaka
     */
    public ReciptDTO(double totalPrice, double totalVat, LocalDateTime timeStamp, List<ItemDTO> listOfItems,
                                                                            double paydAmount, double change){
        this.totalPrice = totalPrice;
        this.totalVat = totalVat;
        this.timeStamp = timeStamp;
        this.listOfItems = listOfItems;
        this.paydAmount = paydAmount;
        this.change = change;
    }
    /**
     * Hämtar det totala priset.
     *
     * @return Det totala priset.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Hämtar den totala momsen för köpet.
     *
     * @return Den totala momsen.
     */
    public double getTotalVat() {
        return totalVat;
    }

    /**
     * Hämtar tidsstämpeln när försäljningen genomfördes.
     *
     * @return Tidpunkten för försäljningen.
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * Hämtar listan med alla sålda varor.
     *
     * @return En lista av med de sålda varor.
     */
    public List<ItemDTO> getListOfItems() {
        return listOfItems;
    }

    /**
     * Hämtar det belopp som kunden har betalat.
     *
     * @return Betalat belopp.
     */
    public double getPaydAmount() {
        return paydAmount;
    }

    /**
     * Hämtar växeln som kunden får tillbaka.
     *
     * @return Växeln i kronor.
     */
    public double getChange() {
        return change;
    }
}
