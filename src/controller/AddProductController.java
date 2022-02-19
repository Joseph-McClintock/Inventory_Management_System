package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.lookupPart;

public class AddProductController implements Initializable {

    /**
     * Text field to add the products name
     * */
    @FXML
    public TextField AddProductName;

    /**
     * Text field to add the products current stock
     * */
    @FXML
    public TextField AddProductInventory;

    /**
     * Text field to add the products price
     * */
    @FXML
    public TextField AddProductPrice;

    /**
     * Text field to add the products maximum stock
     * */
    @FXML
    public TextField AddProductMax;

    /**
     * Text field to add the products minimum stock
     * */
    @FXML
    public TextField AddProductMin;

    /**
     * Text field to add the products id
     * */
    @FXML
    public TextField AddProductID;

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
     * Table column with the parts inventory level
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
     * associated with the new product
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
     * A text field to search the partsToAdd
     *  table
     * */
    @FXML
    public TextField queryParts;

    /**
     * A list of the products associated parts
     * */
    @FXML
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /** FUTURE ENHANCEMENT
     *
     * Saves and adds a new product to allProducts
     * in inventory
     *
     * A future enhancement could be to add product descriptions
     * that are filled with what the product is and why certain parts
     * are associated with it.
     *
     * @param actionEvent Save button is clicked
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void saveProductHandler(ActionEvent actionEvent) throws IOException {

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        int ID = 0;
        for (Product pro : allProducts) {
            if (pro.getId() > ID) {
                ID = pro.getId();
            }
        }
        boolean exit = false;

        try {

            if (Integer.parseInt(AddProductInventory.getText()) < 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory can not be less than zero.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(AddProductMin.getText()) > Integer.parseInt(AddProductMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(AddProductInventory.getText()) > Integer.parseInt(AddProductMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(AddProductInventory.getText()) < Integer.parseInt(AddProductMin.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than inventory.");
                alert.showAndWait();
            }
            else {
                AddProductID.setText(String.valueOf(++ID));
                int id = Integer.parseInt(AddProductID.getText());
                String name = AddProductName.getText();
                double price = Double.parseDouble(AddProductPrice.getText());
                int inventory = Integer.parseInt(AddProductInventory.getText());
                int max = Integer.parseInt(AddProductMax.getText());
                int min = Integer.parseInt(AddProductMin.getText());
                Product addPro = new Product(id, name, price, inventory, min, max);
                for(Part p : associatedParts){
                    addPro.addAssociatedPart(p);
                }
                Inventory.addProduct(addPro);
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
     * if you want to cancel the product and delete all the data
     * you have entered.
     * @param actionEvent Cancels the current product
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

    /**
     * Initializes controller and populates the tables and columns
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsToAdd.setItems(Inventory.getAllParts());

        partsToAddID.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partsToAddName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partsToAddInv.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partsToAddCost.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        addedPartsID.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addedPartsName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addedPartsInv.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addedPartsCost.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

    }

    /**Adds a part from the partsToAdd table to the
     * addedParts table
     * @param actionEvent when clicked a part is added to
     *                    the associatedParts list
     * */
    @FXML
    public void addAssociatedPartController(ActionEvent actionEvent) {
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
                associatedParts.remove(selectedPart);
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
