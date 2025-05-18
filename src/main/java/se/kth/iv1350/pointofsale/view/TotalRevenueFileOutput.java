package se.kth.iv1350.pointofsale.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import se.kth.iv1350.pointofsale.integration.TotalRevenueObserver;

public class TotalRevenueFileOutput implements TotalRevenueObserver {
    private static final String LOG_FILE_NAME = "Total-Revenue-File.txt";
    private PrintWriter logFile;

    /**
     * skapar en instans av observatören som sktiver till en fil
     * @throws IOException om det inte gick att öppna en ström till filen
     */
    public TotalRevenueFileOutput() throws IOException {
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
    }

    /**
     * skriver ut den totala inkomsten av alla köp 
     */
    public void printTotalRevenue(double totalRevenue){
        logFile.println("----- TOTAL REVENUE MADE -----");
        logFile.printf("%20.2f SEK%n",totalRevenue);
        logFile.println("------------------------------");
    }
}
