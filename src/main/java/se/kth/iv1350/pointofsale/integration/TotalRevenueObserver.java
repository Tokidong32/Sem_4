package se.kth.iv1350.pointofsale.integration;
/**
 * gränssnittet för observer klassen som har uppsikt över totala inkomsten
 * 
 * @author Tomas Ålund
 */
public interface TotalRevenueObserver {

    /**
     * skriver ut den totala inkomsten av alla köp 
     */
    void printTotalRevenue(double otalRevenue);
}
