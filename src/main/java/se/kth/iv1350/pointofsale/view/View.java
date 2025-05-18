package se.kth.iv1350.pointofsale.view;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;
import se.kth.iv1350.pointofsale.DTO.SaleDTO;
import se.kth.iv1350.pointofsale.controller.Controller;
import se.kth.iv1350.pointofsale.exceptions.CantReachDatabaseException;
import se.kth.iv1350.pointofsale.exceptions.NoItemFoundException;
/***************************************************************************************************
 * klassen representerar användar gränssnittet, i detta fall är den hardcodeed för test möjlighet
 * 
 * @author Tomas Ålund
 */
public class View{
    private Controller controller;
    private static final double CONVERT_TO_PERCENT = 100.0;

    public View(Controller controller){
        this.controller = controller;
    }
    public void runHardCodeFlow(){
        startSale();
        addItem(-1, 1);
        addItem(3, 1);

        addItem(1, 1);
        addItem(1, 2);
        addItem(2, 1);
        applyDiscounts(1);
        pay(200);

        startSale();
        addItem(1, 1);
        addItem(1, 2);
        addItem(2, 1);
        pay(200);

        startSale();
        addItem(2, 10);
        pay(1200);
    }
    private void startSale(){
        controller.startSale();
    }
    private void addItem(int scanedId, int enterdAmount){
        try {
            SaleDTO currentSaleInfo = controller.addToSale(scanedId, enterdAmount);
            printSoldItemInfo(currentSaleInfo.getItemDTO(), enterdAmount);
            printRunningInfo(currentSaleInfo);
        } catch (NoItemFoundException exception) {
            System.out.println("--- !!! INVALID ID : "+ exception.getscanedId() +" !!! ---");
            System.out.println("Please try whit other items");
        }
        catch (CantReachDatabaseException exception){
            System.out.println("--- !!! CANT CONNECT TO INVENTORY DATABASE !!! ---");
            System.out.println("Contact mantinance");
        }
    }
    private void printRunningInfo(SaleDTO saleInfo) {
        System.out.printf("Running Total: %.2f SEK%n", saleInfo.getRunningTotalPrice());
        System.out.printf("VAT : %.2f SEK%n%n", saleInfo.getRunningTatalTax());
    }
    private void printSoldItemInfo(ItemDTO item, int amount) {
        System.out.printf("Add %d item with item id %d%n", amount, item.getId());
        System.out.printf("Item ID : %d%n", item.getId());
        System.out.printf("Item Name : %s%n", item.getName());
        System.out.printf("Item Price : %.2f SEK%n", item.getPrice());
        System.out.printf("Item VAT : %d%%%n", (int)(item.getTax() * CONVERT_TO_PERCENT));
        System.out.printf("Item Description : %s%n%n", item.getDescription());
    }
    private void applyDiscounts(int customerId){
        SaleDTO currentSaleInfo = controller.applyDiscount(customerId);
        printRunningInfo(currentSaleInfo);
    }
    private void pay(double paydAmount){
        ReciptDTO recipt = controller.pay(paydAmount);
        System.out.printf("Change to give to the customer : %.2f%n", recipt.getChange());
    }

}