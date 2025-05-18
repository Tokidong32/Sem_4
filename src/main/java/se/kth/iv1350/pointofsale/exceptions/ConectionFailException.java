package se.kth.iv1350.pointofsale.exceptions;
/**
 * är ett unchecked Exeption som kasttas när det inte gick att ansluta till servern med
 * tid för felet och addresserna till servern och addressen för systemet.
 * 
 */
public final class ConectionFailException extends CantReachDatabaseException {
    private final String ipOfDatabase;
    private final String ipOfCaller;

    /** skapar en instanse av ConectionFailException som 
     * 
     * @param message medelande om vad som gick fel
     * @param ipOfDatabase ip addressen till serven som försöktes anropas
     * @param ipOfCaller ip addressen från systemet som försöker anropa databassen
     */
    public ConectionFailException(String message, String ipOfDatabase, String ipOfCaller){
        super(message);
        this.ipOfDatabase = ipOfDatabase;
        this.ipOfCaller = ipOfCaller;
    }
    /**
     * hämtar ip adressen till servern
     * @return servens ip adress
     */
    public String getIpOfDatabase() {
        return ipOfDatabase;
    }
    /**
     * hämtar ip adressen till systemet som försöker anropa serven
     * @return ip adressen till systemet
     */
    public String getIpOfCaller() {
        return ipOfCaller;
    }
}
