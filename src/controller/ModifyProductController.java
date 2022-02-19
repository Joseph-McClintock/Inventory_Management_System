package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Optional;

import static model.Inventory.lookupPart;

public class ModifyProductController {

    /**
     * A text field to search the partsToAdd
     *  table
     * */
    @FXML
    public TextField queryParts;

    /**
     * Text field to edit the products id
     * */
    @FXML
    public TextField modProductID;

    /**
     * Text field to edit the products name
     * */
    @FXML
    public TextField modProductName;

    /**
     * Text field to edit the products price
     * */
    @FXML
    public TextField modProductPrice;

    /**
     * Text field to edit the products current stock
     * */
    @FXML
    public TextField modProductInventory;

    /**
     * Text field to edit the products maximum stock
     * */
    @FXML
    public TextField modProductMax;

    /**
     * Text field to edit the products minimum stock
     * */
    @FXML
    public TextField modProductMin;

    /**
     * Table that holds allParts from inventory
     * */
    @FXML
    public TableView partsToAdd;

    /**
     * Table column with the parts id
     * */
    @FXML
    public TableColumn partsToAddID;

    /**
     * Table column with the parts name
     * */
    @FXML
    public TableColumn partsToAddName;

    /**
     * Table column with the parts current stock
     * */
    @FXML
    public TableColumn partsToAddInv;

    /**
     * Table column with the parts cost
     * */
    @FXML
    public TableColumn partsToAddCost;

    /**
     * Table that holds all the parts
     * associated with the product you are editing
     * */
    @FXML
    public TableView addedParts;

    /**
     * Table column with the added parts id
     * */
    @FXML
    public TableColumn addedPartsID;

    /**
     * Table column with the added parts name
     * */
    @FXML
    public TableColumn addedPartsName;

    /**
     * Table column with the added parts current stock
     * */
    @FXML
    public TableColumn addedPartsInv;

    /**
     * Table column with the added parts cost
     * */
    @FXML
    public TableColumn addedPartsCost;

    /**
     * The product you selected from MainScreenController
     * */
    @FXML
    private Product modPro = MainScreenController.returnProductToMod();

    /**
     * A list of the products associated parts
     * */
    @FXML
    private ObservableList<Part> associatedParts = modPro.getAllAssociatedParts();

    /**Sets all the text fields to their appropriate data
     * @param selectedProduct the product you selected from MainScreenController
     */
    @FXML
    public void setProduct(Product selectedProduct) {

        modProductID.setText(Integer.toString(selectedProduct.getId()));
        modProductName.setText(selectedProduct.getName());
        modProductPrice.setText(Double.toString(selectedProduct.getPrice()));
        modProductInventory.setText(Integer.toString(selectedProduct.getStock()));
        modProductMax.setText(Integer.toString(selectedProduct.getMax()));
        modProductMin.setText(Integer.toString(selectedProduct.getMin()));

        partsToAdd.setItems(Inventory.getAllParts());

        partsToAddID.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partsToAddName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partsToAddInv.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partsToAddCost.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        addedParts.setItems(selectedProduct.getAllAssociatedParts());

        addedPartsID.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addedPartsName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addedPartsInv.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addedPartsCost.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

    }

    /** Saves the new product and removes the old one
     * and then adds the new product to allProducts
     * in inventory
     * @param actionEvent Save button is clicked
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void saveProductHandler(ActionEvent actionEvent) throws IOException {
        boolean exit = false;

        try {

            if (Integer.parseInt(modProductInventory.getText()) < 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory can not be less than zero.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(modProductMin.getText()) > Integer.parseInt(modProductMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(modProductInventory.getText()) > Integer.parseInt(modProductMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(modProductInventory.getText()) < Integer.parseInt(modProductMin.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than inventory.");
                alert.showAndWait();
            }
            else {
                int id = Integer.parseInt(modProductID.getText());
                String name = modProductName.getText();
                double price = Double.parseDouble(modProductPrice.getText());
                int inventory = Integer.parseInt(modProductInventory.getText());
                int max = Integer.parseInt(modProductMax.getText());
                int min = Integer.parseInt(modProductMin.getText());
                modPro = new Product(id, name, price, inventory, min, max);
                for(Part p : associatedParts){
                    modPro.addAssociatedPart(p);
                }
                Inventory.updateProduct(id, modPro);
                associatedParts.removeAll();
                exit = true;
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The data you entered is invalid");
            alert.showAndWait();
        }
        if(exit) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**When clicked you are prompted with a CONFIRMATION alert
     * if you want to cancel the product and delete all the new
     * data you have entered.
     * @param actionEvent Cancels editing the current part
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void cancelProductHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all the data you entered! Do you want to continue?");
        alert.setTitle("Error Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**Adds a part from the partsToAdd table to the
     * addedParts table
     * @param actionEvent when clicked a part is added to
     *                    the associatedParts list
     * */
    @FXML
    public void addAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsToAdd.getSelectionModel().getSelectedItem();
        if(partsToAdd.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("No part selected to add.");
            alert.showAndWait();
        }
        else{
            associatedParts.add(selectedPart);
            addedParts.setItems(associatedParts);
        }
    }

    /**Removes a part from the addedParts table
     * @param actionEvent when clicked a part is removed from associatedParts list
     * */
    @FXML
    public void removeAssociatedPart(ActionEvent actionEvent) {
        if(!(addedParts.getSelectionModel().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected row?");
            alert.setTitle("Error Dialog");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Part selectedPart = (Part) addedParts.getSelectionModel().getSelectedItem();
                modPro.deleteAssociatedPart(selectedPart);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("No part selected to delete.");
            alert.showAndWait();
        }
    }

    /**Query's the partsToAdd table for what
     * you have filled the search bar with
     * @param actionEvent when enter is pressed parts are searched
     * */
    @FXML
    public void getResultsParts(ActionEvent actionEvent) {
        String q = queryParts.getText();

        ObservableList<Part> parts = lookupPart(q);

        if(parts.size() == 0){
            try {
                int id = Integer.parseInt(q);
                Part p = lookupPart(id);
                if(p != null) {
                    parts.add(p);
                }
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("No products found");
                alert.showAndWait();
                partsToAdd.setItems(Inventory.getAllParts());
                queryParts.setText("");
                return;
            }
        }

        partsToAdd.setItems(parts);
    }
}
