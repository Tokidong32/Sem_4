package se.kth.iv1350.pointofsale.exceptions;
/**
 * är ett checked Exeption som kastas för att indikera att det inte gick att hitta en specifik vara
 * 
 * @author Tomas Ålund
 */
public final class NoItemFoundException extends RuntimeException {
    private final int scanedId;

    /**
     * skapar en instans av undantaget för en specifik vara
     * @param scanedId  ID på varan som inte kunde hittas
     */
    public NoItemFoundException(int scanedId){
        this.scanedId = scanedId;
    }
    /**
     * hämtar id som inte kunde hittas
     * @return
     */
    public int getscanedId(){
        return scanedId; 
    }
    
}
