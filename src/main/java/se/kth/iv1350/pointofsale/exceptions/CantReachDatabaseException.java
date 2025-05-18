package se.kth.iv1350.pointofsale.exceptions;
/**
 * Detta är ett förelder undantag 
 * 
 * @author Tomas Ålund
 */
public class CantReachDatabaseException extends Exception{

    /**
     * skapar en instans av ett CantReachDatabaseException
     * 
     * @param message innehåller ett meddelande om felet
     */
    public CantReachDatabaseException(String message){
        super(message);
    }
}   
