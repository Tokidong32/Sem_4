package se.kth.iv1350.pointofsale.integration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import se.kth.iv1350.pointofsale.DTO.ItemDTO;
import se.kth.iv1350.pointofsale.DTO.ReciptDTO;

/***************************************************************************************************
 * detta är skrivaren som skriver ut alla kviton som genomförs i systemet
 * 
 * @author Tomas Ålund
 */
public class Printer {
    /**
     * metoden kommer att skriva ut kvitot till terminale på ett fint format
     * @param currentRecipt kvittot att skriva ut
     */
    public void printRecipt(ReciptDTO currentRecipt) {
        if(!(currentRecipt instanceof ReciptDTO))
            return;

        System.out.printf("-------------------- Begin receipt --------------------%n");
        System.out.printf("Time of Sale : %s %n", formatTime(currentRecipt.getTimeStamp()));
        for (ItemDTO currentItem : currentRecipt.getListOfItems()) {
            printItemToRecipt(currentItem);
        }
        System.out.printf("%nTotal : %s SEK%n", formatAsPrice(currentRecipt.getTotalPrice()));
        System.out.printf("VAT : %s SEK%n%n", formatAsPrice(currentRecipt.getTotalVat()));
        System.out.printf("Cash : %s SEK%n", formatAsPrice(currentRecipt.getPaydAmount()));
        System.out.printf("Change : %s SEK%n", formatAsPrice(currentRecipt.getChange()));
        System.out.printf("--------------------- End receipt ---------------------%n%n");
    }
    private void printItemToRecipt(ItemDTO item){
        System.out.printf("%-24s %4d x %7s %9s SEK%n", item.getName(),
        item.getAmount(), formatAsPrice(item.getPrice()),formatAsPrice(item.getAmount()*item.getPrice()));
    }
    private String formatAsPrice(double price){
        String formattedPrice = "";
        formattedPrice = formattedPrice + (int) price;
        formattedPrice = formattedPrice + ":";
        int oren =  (int) ((price - (int) price) * 100);
        formattedPrice = formattedPrice + ((oren == 0)? "00": oren);
        return formattedPrice.toString();
    }
    /**
     * @param time
     * @return
     */
    private String formatTime(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return time.format(formatter);
    }
}
