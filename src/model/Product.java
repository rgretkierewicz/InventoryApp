package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used for creating, modifying, and accessing data for product objects.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public static int productsIdCounter = 1000;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
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

    /**
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart the part to delete
     * @return true if delete is successful, false if delete has failed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (selectedAssociatedPart == null) {
            return false;
        }

        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * @return list of all associated parts for a product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * @return the id created for a product
     */
    public static int createProductID() {
        return (productsIdCounter++);
    }

}

