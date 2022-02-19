package controller;

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
import static model.Inventory.lookupProduct;

public class MainScreenController implements Initializable {

    /**
     * A text field to search the productTable
     * */
    @FXML
    public TextField queryProducts;

    /**
     * A text field to search the partTable
     * */
    public TextField queryParts;

    /**
     * Table that holds allParts from inventory
     * */
    @FXML
    public TableView partTable;

    /**
     * Table column with the parts id
     * */
    @FXML
    public TableColumn partIDColumn;

    /**
     * Table column with the parts name
     * */
    @FXML
    public TableColumn partNameColumn;

    /**
     * Table column with the parts current stock
     * */
    @FXML
    public TableColumn partInventoryLevelColumn;

    /**
     * Table column with the parts cost
     * */
    @FXML
    public TableColumn partCostColumn;

    /**
     * Table that holds allProducts from inventory
     * */
    @FXML
    public TableView productTable;

    /**
     * Table column with the products id
     * */
    @FXML
    public TableColumn productIDColumn;

    /**
     * Table column with the products name
     * */
    @FXML
    public TableColumn productNameColumn;

    /**
     * Table column with the products current stock
     * */
    @FXML
    public TableColumn productInventoryLevelColumn;

    /**
     * Table column with the products cost
     * */
    @FXML
    public TableColumn productCostColumn;


    /**
     * Product of the selected product in a table
     * */
    @FXML
    private static Product selectedProduct;

    /**Returns the selected product
     * @return selectedProduct the current selected product
     * */
    @FXML
    public static Product returnProductToMod(){
        return selectedProduct;
    }

    /**
     * A boolean to test if it's the first time
     * addTestDate has been run
     * */
    @FXML
    private static boolean firstTime = true;

    /**
     * Function to add test data
     * */
    @FXML
    private void addTestData(){
        if(!firstTime){
            return;
        }
        ObservableList<Part> p ;
        firstTime = false;
        Part rim = new InHouse(1, "Rim One", 50, 500, 50, 550, 5);
        Inventory.addPart(rim);

        Part outRim = new Outsourced(2, "RimsCo Rim", 42, 125, 25, 600, "RimsCo");
        Inventory.addPart(outRim);

    }

    /**
     * Initializes controller and populates the tables and columns
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Initialized");

        addTestData();

        partTable.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        productTable.setItems(Inventory.getAllProducts());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**Query's the partTable table for what
     * you have filled the search bar with
     * @param actionEvent when enter is pressed parts are searched
     * */
    @FXML
    public void getResultsParts(ActionEvent actionEvent){
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
                partTable.setItems(Inventory.getAllParts());
                queryParts.setText("");
                return;
            }
        }

        partTable.setItems(parts);
    }

    /**Query's the productTable table for what
     * you have filled the search bar with
     * @param actionEvent when enter is pressed products are searched
     * */
    @FXML
    public void getResultsProducts(ActionEvent actionEvent) {
        String a = queryProducts.getText();

        ObservableList<Product> products = lookupProduct(a);

        if(products.size() == 0){
            try {
                int id = Integer.parseInt(a);
                Product pro = lookupProduct(id);
                if(pro != null) {
                    products.add(pro);
                }
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("No products found");
                alert.showAndWait();
                productTable.setItems(Inventory.getAllProducts());
                queryParts.setText("");
                return;
            }
        }

        productTable.setItems(products);
    }

    /**Takes you to add a part screen
     * @param actionEvent takes you to AddPart.fxml
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void toAddPartScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**Takes you to modify the current
     * part you have selected in the
     * partTable
     * @param actionEvent takes you to ModifyPart.fxml
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();

        if(!(partTable.getSelectionModel().isEmpty())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPart.fxml"));
                Parent root = loader.load();
                ModifyPartController modifyPartController = loader.getController();
                modifyPartController.setPart(selectedPart);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                System.out.println("Error loading modify part screen");
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("No part selected to modify.");
            alert.showAndWait();
        }
    }

    /**Takes you to add a product screen
     * @param actionEvent takes you to AddProduct.fxml
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void toAddProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**Takes you to modify the current
     * product you have selected in the
     * productTable
     * @param actionEvent takes you to ModifyProduct.fxml
     * @throws IOException from FXMLLoader
     * */
    @FXML
    public void toModifyProductScreen(ActionEvent actionEvent) throws IOException {
        selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if(!(productTable.getSelectionModel().isEmpty())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyProduct.fxml"));
                Parent root = loader.load();
                ModifyProductController modifyProductController = loader.getController();
                modifyProductController.setProduct(selectedProduct);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                System.out.println("Error loading modify product screen");
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("No part selected to modify.");
            alert.showAndWait();
        }
    }

    /**Deletes a part from the partTable table
     * @param actionEvent when clicked a part is removed from partTable
     * */
    @FXML
    public void deletePart(ActionEvent actionEvent){
        if(!(partTable.getSelectionModel().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected row?");
            alert.setTitle("Error Dialog");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("No part selected to delete.");
            alert.showAndWait();
        }
    }

    /**Deletes a product from the productTable table
     * @param actionEvent when clicked a part is removed from productTable
     * */
    @FXML
    public void deleteProduct(ActionEvent actionEvent){
        if(!(productTable.getSelectionModel().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected row?");
            alert.setTitle("Error Dialog");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
                ObservableList<Part> p = selectedProduct.getAllAssociatedParts();
                if(p.isEmpty()) {
                    Inventory.deleteProduct(selectedProduct);
                }
                else {
                    Alert newAlert = new Alert(Alert.AlertType.WARNING);
                    newAlert.setTitle("Error Dialog");
                    newAlert.setContentText("The product selected has associated parts.");
                    newAlert.showAndWait();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("No product selected to delete.");
            alert.showAndWait();
        }
    }

    /**Quit button to close the program
     * @param actionEvent when clicked program is closed
     * */
    @FXML
    public void quitButton(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
