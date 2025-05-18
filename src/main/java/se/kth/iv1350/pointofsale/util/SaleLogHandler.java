package se.kth.iv1350.pointofsale.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * loggklass som kan logga undantag till en textfil
 * @author Tomas Ålund
 */
public class SaleLogHandler {
    private static final String LOG_FILE_NAME = "Sale-log.txt";
    private PrintWriter logFile;

    public SaleLogHandler() throws IOException {
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
    }

    /**
    * Skriver en logg fil för varje undantag
    *
    * @param exception undantaget att loggas
    */
    public void logException(String messageFromSystem, Exception exception) {
        logFile.println(messageFromSystem);
        String logMessage = "Exception was thrown at time: " + formatedTime();
        logFile.println(logMessage);
        exception.printStackTrace(logFile);
    }
    /**
     * skapar en formaterad tid
     * @return tiden som en sträng
     */
    private String formatedTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
