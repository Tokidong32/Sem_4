package se.kth.iv1350.pointofsale.integration;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;

/***************************************************************************************************
 * denna klass bokför alla köp gjorda i systemet
 * 
 * @author Tomas Ålund
 */
public class AccountingSystem {
    private static final AccountingSystem currentInstance = new AccountingSystem();
    private List<ReciptDTO> reciptDatabase;    
    private List<TotalRevenueObserver> observers = new ArrayList<TotalRevenueObserver>();
    /**
     * skapar en instans av bokföringsprogramet
     */
    private AccountingSystem (){
        reciptDatabase = new ArrayList<ReciptDTO>();
    }
    public static AccountingSystem getInstance(){
        return currentInstance;
    }
    /**
     * lägger till en observatör till observatörlistan
     * @param observer observatören som ska läggas till
     */
    public void addRevenueObserver(TotalRevenueObserver observer){
        this.observers.add(observer);
    }
    /**
     * lägger till en lista av observatörer till listan av observatörer
     * @param observers listan av observatörer som ska läggas till
     */
    public void addListOfRevenueObserver(List<TotalRevenueObserver> observers){
        for (TotalRevenueObserver observerToAdd : observers) {
            this.observers.add(observerToAdd);
        }
    }
    /**
     * sparar det med följande kvittot i databasen
     * @param currentRecipt
     */
    public void bookkeepSale(ReciptDTO currentRecipt) {
        reciptDatabase.add(currentRecipt);
        notifiObservers();
    }
    private double getTotalRevenue(){
        double totalRevenue = 0;
        for (ReciptDTO currentRecipt : reciptDatabase) {
            totalRevenue += currentRecipt.getTotalPrice();
        }
        return totalRevenue;
    }
    private void notifiObservers(){
        for (TotalRevenueObserver observer : observers) {
            observer.printTotalRevenue(getTotalRevenue());
        }
    }
    /**
     * används vid testnig när ett kvitto läggs till, kan behövas senar i utväcklingen
     * @return storleken på listan
     */
    int getAmountOfSales(){
        return reciptDatabase.size();
    }

}
