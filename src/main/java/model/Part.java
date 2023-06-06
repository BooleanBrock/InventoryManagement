package model;
//Supplied class Part.java by WGU

/**
 * Models an abstract class Part from which Outsourced and InHouse inherit
 *
 * @author Western Governor's University
 */
public abstract class Part {

    /**
     * Part id
     */
    private int id;

    /**
     * Part name
     */
    private String name;

    /**
     * Part price
     */
    private double price;

    /**
     * Part stock
     */
    private int stock;

    /**
     * Part minimum
     */
    private int min;

    /**
     * Part max
     */
    private int max;

    /**Constructor for new instance of Part
     *
     * @param id the id for this part
     * @param name the name for this part
     * @param price the price for this part
     * @param stock the stock for this part
     * @param min the min for this part
     * @param max the max for this part
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Getters and setters
    /**
     * @return the id
     **/
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }



}
