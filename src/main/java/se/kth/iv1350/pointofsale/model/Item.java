package se.kth.iv1350.pointofsale.model;
import se.kth.iv1350.pointofsale.DTO.ItemDTO;
/***************************************************************************************************
 * detta är klassen som representer en enkild sort av vara eller varor.
 * 
 * @author Tomas Ålund
 */
public class Item {
    private int id;
    private double price;
    private double tax;
    private String name;
    private String description;
    private int amount;
    /**
     * skapar en ny vara med hjälp av primitiva data
     * @param id varans id
     * @param price varans pris
     * @param tax varans moms
     * @param name varas namn
     * @param description varans bekrivning
     * @param amount antalet av denna vara
     */
    public Item(int id, double price, double tax, String name, String description, int amount){
        this.id = id;
        this.price = price;
        this.tax = tax;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }
    /**
     * skapar en identisk vara från en DTO 
     * @param itemDTO varan som en DTO
     */
    public Item(ItemDTO itemDTO){
        this.id = itemDTO.getId();
        this.price = itemDTO.getPrice();
        this.tax = itemDTO.getTax();
        this.name = itemDTO.getName();
        this.description = itemDTO.getDescription();
        this.amount = itemDTO.getAmount();
    }
    /**
     * hämtar varans id
     * @return varans id
     */
    public int getId(){
        return this.id;
    }
    /**
     * hämtar varans pris
     * @return varans pris
     */
    public double getPrice(){
        return this.price;
    }
    /**
     * hämtar varans moms
     * @return varans moms
     */
    public double getTax(){
        return this.tax;
    }
    /**
     * hämtar varans namn
     * @return varans namn
     */
    public String getName(){
        return this.name;
    }
    /**
     * hämtar varans beskrivning
     * @return varans beskrivning
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * hämtar antalet av varan
     * @return varans antal
     */
    public int getAmount(){
        return this.amount;
    }
    /**
     * ändrar varans antal 
     * @param amount det nya antalet
     */
    public void setAmount(int amount) {
        if(amount < 0)
            amount = 0;
        this.amount = amount;
    }
    /**
     * skapar en DTO av nuvarande vara
     * @return en DTO som beskriver nuvarande vara
     */
    public ItemDTO convertToDTO(){
        return new ItemDTO(id,price,tax,name,description,amount);   
    }
    /**
     * jämför om den givna varan är inistanser av samma vara som denna vara
     * @param item den andra varan som ska jämföras
     * @return true om det var samma vara annars false
     */
    @Override
    public boolean equals(Object objectToCompear){
        if (this == objectToCompear) 
            return true;
        if (objectToCompear == null || this.getClass() != objectToCompear.getClass())
            return false;

        Item otherItem = (Item) objectToCompear;
        return this.id == otherItem.getId() && this.name.equals(otherItem.getName());
    }
}
