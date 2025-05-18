package se.kth.iv1350.pointofsale.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.pointofsale.DTO.*;
import se.kth.iv1350.pointofsale.exceptions.CantReachDatabaseException;
import se.kth.iv1350.pointofsale.exceptions.ConectionFailException;
import se.kth.iv1350.pointofsale.integration.*;
import se.kth.iv1350.pointofsale.model.Payment;
import se.kth.iv1350.pointofsale.model.Register;
import se.kth.iv1350.pointofsale.model.Sale;
import se.kth.iv1350.pointofsale.util.SaleLogHandler;
/***************************************************************************************************
 * detta är kontrollen som kommer hantera allt gällande pågående köp i systemet. 
 * 
 * @author Tomas Ålund
 */
public class Controller {
    private final double INITIAL_BALENCE = 1000;
    private AccountingSystem accountingSystem;
    private DiscountDatabase discountDatabase;
    private InventorySystem inventorySystem;
    private Printer printer;
    private Register register;
    private Sale currentSale;
    private SaleLogHandler logger;
    private List<TotalRevenueObserver> observers = new ArrayList<TotalRevenueObserver>();

    /**
     * skapar en instans av controllen med med följande referenser till externa system
     * @param accountingSystem det externa bokförings systemet som behandlar bokföring a köp
     * @param discountDatabase det externa rea databasen som behandlar gällande reor
     * @param inventorySystem det externa lager systemet som hanterar affärens lager
     * @param printer skrivaren som skriver ut kvitton
     * @throws IOExcepion om det inte gick att skapa en logger korrekt
     */
    public Controller(AccountingSystem accountingSystem, DiscountDatabase discountDatabase,
                                InventorySystem inventorySystem, Printer printer) throws IOException
    {
        this.accountingSystem = accountingSystem;
        this.discountDatabase = discountDatabase;
        this.inventorySystem = inventorySystem;
        this.printer = printer;
        this.logger = new SaleLogHandler();
        this.register = new Register(INITIAL_BALENCE);
    }
    /**
     * startar en ny Sale 
     */
    public void startSale(){
        this.currentSale = new Sale();
    }
    /**
     * metoden kommer att lägga till det givna antalet av den skannade varan till köpet
     * @param scanedId är den skanade varas id.
     * @param amount är antalet av den skannade varan.
     * @return informationen av vad som skannats samt en totalkostnad och moms. om varan inte 
     *         fanns retuneras NULL.
     * @throws ConectionFailException om det inte gick att lägga till den scanade vara
     * */
    public SaleDTO addToSale(int scanedId, int amount) throws CantReachDatabaseException{
        try {
            ItemDTO currentItem = inventorySystem.lookUpItem(scanedId, amount);
            SaleDTO currentSaleInfo = currentSale.addItem(currentItem);
            return currentSaleInfo;
        }
        catch (ConectionFailException exception){
            String logMessage = "Cant connect to the database at IP: "+exception.getIpOfDatabase()
                + " from system at IP: "+exception.getIpOfCaller();
            logger.logException(logMessage, exception);
            throw new CantReachDatabaseException("Cant reach the Inventory databas");
        }
    }
    /**
     * lägger till en observatör till observatörlistan
     * @param observer observatören som ska läggas till
     */
    public void addRevenueObserver(TotalRevenueObserver observer){
        this.observers.add(observer);
        accountingSystem.addRevenueObserver(observer);
    }
    /**
     * lägger till en lista av observatörer till listan av observatörer
     * @param observers listan av observatörer som ska läggas till
     */
    public void addListOfRevenueObserver(List<TotalRevenueObserver> observers){
        for (TotalRevenueObserver observerToAdd : observers) {
            this.observers.add(observerToAdd);
            this.accountingSystem.addRevenueObserver(observerToAdd);
        }
    }
    /**
     * kommer att applicera de gilltiga reoran på köpet, gilltiga reor tas fram från senare 
     * klasser.
     * @param customerId är kundens medlems nr.
     * @return uppdaterade ett uppdaterat pris efter reorna.
     */
    public SaleDTO applyDiscount(int customerId){
        ReciptDTO currentRecipt = currentSale.getReciptDTO();
        DiscountDTO validDiscount = discountDatabase.lookupDiscount(currentRecipt, customerId);
        return currentSale.applyDiscounts(validDiscount);
    }
    /**
     * kommer att betala för för köpet samt att skriva ut ett kvitto och uppdatera nödvändiga
     * externa system
     * @param paydAmount summan av de kontanter som det betalades med
     * @return ett fullständigt kvitto
     */
    public ReciptDTO pay(double paydAmount){
        Payment currentPayment = new Payment(paydAmount);
        ReciptDTO currentRecipt = currentSale.pay(currentPayment);
        register.updateBalance(currentPayment);
        printer.printRecipt(currentRecipt);
        accountingSystem.bookkeepSale(currentRecipt);
        inventorySystem.updateInventory(currentRecipt);
        return currentRecipt;
    }
}