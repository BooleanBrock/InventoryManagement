package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models a product and its associated parts
 *
 * @author Wyatt Brock
 */
public class Product {

    /**
     * List of part objects associated to product object
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Product id
     */
    private int id;

    /**
     * Product name
     */
    private String name;

    /**
     * Product price
     */
    private double price;

    /**
     * Product stock
     */
    private int stock;

    /**
     * Product min
     */
    private int min;

    /**
     * Product max
     */
    private int max;

    /**Constructor for new instance of Product
     *
     * @param id the id for this product
     * @param name the name for this product
     * @param price the price for this product
     * @param stock the stock for this product
     * @param min the min for this product
     * @param max the max for this product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Getters and setters

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param part Part object
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart Part object
     * @return boolean stating status of Part object removal from list of associated parts
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return list of part objects associated to product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
