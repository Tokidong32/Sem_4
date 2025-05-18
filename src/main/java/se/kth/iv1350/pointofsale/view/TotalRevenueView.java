package se.kth.iv1350.pointofsale.view;
import se.kth.iv1350.pointofsale.integration.TotalRevenueObserver;

public class TotalRevenueView implements TotalRevenueObserver {

    /**
     * skriver ut den totala inkomsten av alla köp 
     */
    public void printTotalRevenue(double totalRevenue){
        System.out.println("----- TOTAL REVENUE MADE -----");
        System.out.printf("%20.2f SEK%n",totalRevenue);
        System.out.println("------------------------------");
    }
}
