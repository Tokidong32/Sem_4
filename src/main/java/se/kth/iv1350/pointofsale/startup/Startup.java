package se.kth.iv1350.pointofsale.startup;
import java.io.IOException;

import se.kth.iv1350.pointofsale.controller.Controller;
import se.kth.iv1350.pointofsale.integration.AccountingSystem;
import se.kth.iv1350.pointofsale.integration.DiscountDatabase;
import se.kth.iv1350.pointofsale.integration.InventorySystem;
import se.kth.iv1350.pointofsale.integration.Printer;
import se.kth.iv1350.pointofsale.view.TotalRevenueFileOutput;
import se.kth.iv1350.pointofsale.view.TotalRevenueView;
import se.kth.iv1350.pointofsale.view.View;
/***************************************************************************************************
 * Detta är klassen Startup som kommer att initsiera alla vitala klasser för att resterande av 
 * systemet ska fungera som tänkt
 * 
 * @author Tomas Ålund
*/
public class Startup {
    /**
     * main metoden som startar upp hela systemet
     * @param args denna metod kommer inte använda några argument
     */
    public static void main(String[] args) {
        try{
        AccountingSystem accountingSystem = AccountingSystem.getInstance();
        InventorySystem inventorySystem = InventorySystem.getInstance();
        DiscountDatabase discountDatabase = DiscountDatabase.getInstance();
        Printer printer = new Printer();
        Controller controller = new Controller(accountingSystem, discountDatabase, 
                                                                        inventorySystem, printer);
        
        View view = new View(controller);
        TotalRevenueView revenueView = new TotalRevenueView();
        TotalRevenueFileOutput revenueFileWriter = new TotalRevenueFileOutput();
        controller.addRevenueObserver(revenueFileWriter);
        controller.addRevenueObserver(revenueView);
        view.runHardCodeFlow();
        }
        catch(IOException exception){
            System.exit(1);
        }
    }
    
}
