package se.kth.iv1350.pointofsale.model;
/***************************************************************************************************
 * detta är skrivaren som skriver ut alla kviton som genomförs i systemet
 * 
 * @author Tomas Ålund
 */
public class Register {
    private double currentBalance;
    /*
     * detta skapar objektet med hjälp av en initiala balans
     * @param double initBalance. den initiala balansen
     */
    public Register(double initBalance){
        this.currentBalance = initBalance;
    }
    /**
     * uppdatera den nuvarande saldot med den inkomande betalningen kan inte överskrida 
     * DOUBLE_MAX
     * @param currentPayment den inkomande betalningen
     */
    public void updateBalance(Payment currentPayment) {

        if(currentBalance + currentPayment.getPaydAmount() - currentPayment.getChange() < 
        Double.MAX_VALUE){
        currentBalance += currentPayment.getPaydAmount() - currentPayment.getChange();
        }
        else
            currentBalance = Double.MAX_VALUE;
    }
    /**
     * används vid testnig, kan behövas senar i utväcklingen
     * @return nuvarande saldo i kassan
     */
    double getCurrentBalance(){
        return currentBalance;
    }
}