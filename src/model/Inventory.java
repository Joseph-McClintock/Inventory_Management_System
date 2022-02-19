package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    /**
     * List of all the parts
     * */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all the products
     * */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds a part to allParts
     * @param newPart the part that's added
     * */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Adds a product to allParts
     * @param newProduct the product that's added
     * */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Lists all the parts
     * @return allParts in inventory
     * */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Lists all the products
     * @return allProducts in inventory
     * */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Looks up a part by its id
     * @param partId the parts id
     * @return p which is the parts found
     * */
    public static Part lookupPart(int partId){

        for (Part p : allParts) {
            if (p.getPartId() == partId) {
                return p;
            }
        }
        return null;
    }

    /** Looks up a product by its id
     * @param productId the products id
     * @return p which is the products found
     * */
    public static Product lookupProduct(int productId){
        for (Product pro : allProducts) {
            if (pro.getId() == productId) {
                return pro;
            }
        }
        return null;
    }

    /** Looks up a part by its name
     * @param partName the parts name
     * @return namedParts which is the parts found
     * */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for(Part p : allParts){
            if(p.getPartName().toLowerCase().contains(partName.toLowerCase())){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /** Looks up a product by its name
     * @param productName the products name
     * @return namedProducts which is the products found
     * */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for(Product pro : allProducts){
            if(pro.getName().toLowerCase().contains(productName.toLowerCase())){
                namedProducts.add(pro);
            }
        }
        return namedProducts;
    }

    /** Updates a part by the selected parts id
     * @param index the id of the part
     * @param selectedPart the part that is updated
     * */
    public static void updatePart(int index, Part selectedPart){
        allParts.removeIf(part -> part.getPartId() == index);
        allParts.add(selectedPart);
    }

    /** Updates a part by the selected products id
     * @param index the id of the product
     * @param selectedProduct the product that is updated
     * */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.removeIf(product -> product.getId() == index);
        allProducts.add(selectedProduct);
    }

    /** Deletes the selected part
     * @param selectedPart the selected part
     * @return true if part is found
     * */
    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Deletes the selected product
     * @param selectedProduct the selected product
     * @return true if product is found
     * */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
}
