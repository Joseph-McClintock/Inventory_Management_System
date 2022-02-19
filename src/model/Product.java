package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * A products id
     * */
    private int id;

    /**
     * A products name
     * */
    private String name;

    /**
     * A products price
     * */
    private double price;

    /**
     * A products current stock
     * */
    private int stock;

    /**
     * A products maximum stock
     * */
    private int min;

    /**
     * A products minimum stock
     * */
    private int max;

    /** Constructor of a new Part
     * @param id a products id
     * @param name a products name
     * @param price a products price
     * @param stock a products current stock
     * @param min a products minimum stock
     * @param max a products maximum stock
     * */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**Setter for id
     * @param id of a product
     * */
    public void setId(int id){
        this.id = id;
    }

    /**Setter for name
     * @param name of a product
     * */
    public void setName(String name){
        this.name = name;
    }

    /**Setter for price
     * @param price of a product
     * */
    public void setPrice(double price){
        this.price = price;
    }

    /**Setter for stock
     * @param stock of a product
     * */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**Setter for min
     * @param min of a product
     * */
    public void setMin(int min){
        this.min = min;
    }

    /**Setter for max
     * @param max of a product
     * */
    public void setMax(int max){
        this.max = max;
    }

    //getters

    /**Getter for id
     * @return id of product
     * */
    public int getId(){
        return id;
    }

    /**Getter for name
     * @return name of product
     * */
    public String getName(){
        return name;
    }

    /**Getter for price
     * @return price of product
     * */
    public double getPrice(){
        return price;
    }

    /**Getter for stock
     * @return stock of product
     * */
    public int getStock(){
        return stock;
    }

    /**Getter for min
     * @return min of product
     * */
    public int getMin(){
        return min;
    }

    /**Getter for max
     * @return max of product
     * */
    public int getMax(){
        return max;
    }

    /** Adds associated parts to associatedParts
     * @param part the part being added
     * */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** Deletes an associated part in associatedParts
     * @param selectedAssociatedPart the selected associated part
     * @return true the part is found
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Displays all associated parts of a product
     * @return associatedParts which is all the parts
     * associated with a product
     * */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
