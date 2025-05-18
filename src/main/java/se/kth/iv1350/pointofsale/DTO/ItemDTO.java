package se.kth.iv1350.pointofsale.DTO;
/***************************************************************************************************
 * detta är DTO klassen för en vara och håller information om denna vara
 * 
 * @author Tomas Ålund
 */
public final class ItemDTO {
    private final int id;
    private final double price;
    private final double tax;
    private final String name;
    private final String description;
    private final int amount;

    /**
     * skapar en instans med den medflöjande datat 
     * @param id varans id
     * @param price varans pris
     * @param tax varans moms procent sats
     * @param name  varans namn
     * @param description varans beskrivning
     * @param amount antalet exemplar av samma vara
     */
    public ItemDTO(int id, double price, double tax, String name, String description, int amount){
        this.id = id;
        this.price = price; 
        this.tax = tax;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }
    /**
     * hämta id
     * @return id
    */
    public int getId(){
        return this.id;
    }
    /**
     * hämta priset
     * @return priset
     */
    public double getPrice(){
        return this.price;
    }
    /**
     * hämta moms procenten
     * @return momsen
     */
    public double getTax(){
        return this.tax;
    }
    /**
     * hämta namnet
     * @return namnet
     */
    public String getName(){
        return this.name;
    }
    /**
     * hämta varans beskrivning
     * @return beskrivningen
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * hämta antalet
     * @return antalet
     */
    public int getAmount(){
        return this.amount;
    }
}
