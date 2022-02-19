package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.util.Optional;

public class AddPartController  {

    /**
     * The toggle group for the radio buttons
     * */
    @FXML
    public ToggleGroup toggleGroup;

    /**
     * The inHouse radio button
     * */
    @FXML
    public RadioButton inHouse;

    /**
     * The outsourced radio button
     * */
    @FXML
    public RadioButton outsourced;

    /**
     * MachineId or Company name label
     * changes depending on what radio
     * button is selected
     * */
    @FXML
    public Label toggleChange;

    /**
     * Text field to add machineId or company name
     * */
    @FXML
    public TextField AddMachineID;

    /**
     * Text field to add part name
     * */
    @FXML
    public TextField AddPartName;

    /**
     * Text field to add current stock
     * */
    @FXML
    public TextField AddPartInventory;

    /**
     * Text field to add the parts price
     * */
    @FXML
    public TextField AddPartPrice;

    /**
     * Text field to add the parts maximum stock
     * */
    @FXML
    public TextField AddPartMax;

    /**
     * Text field to add the parts minimum stock
     * */
    @FXML
    public TextField AddPartMin;

    /**
     * Text field to add the parts ID
     * */
    @FXML
    public TextField AddPartID;

    /** When the inHouse radio button is selected
     * the toggleChange label and addMachineID
     * text field are updated
     * @param actionEvent inHouse radio button is selected
     * */
    @FXML
    public void onInHouse(ActionEvent actionEvent) {
        toggleChange.setText("Machine ID");
        AddMachineID.setPromptText("Add Machine ID");
    }

    /** When the outsourced radio button is selected
     * the toggleChange label and addMachineID
     * text field are updated
     * @param actionEvent outsourced radio button is selected
     * */
    @FXML
    public void onOutsourced(ActionEvent actionEvent) {
        toggleChange.setText("Company Name");
        AddMachineID.setPromptText("Add Company Name");
    }

    /** Saves the and adds a new part to allParts
     * in inventory
     * @param actionEvent Save button is clicked
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void savePartHandler(ActionEvent actionEvent) throws IOException {

        ObservableList<Part> allParts = Inventory.getAllParts();
        int ID = 1;
        for (Part p : allParts) {
            if (p.getPartId() > ID) {
                ID = p.getPartId();
            }
        }
        boolean exit = false;

        try {

            if (Integer.parseInt(AddPartInventory.getText()) < 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory can not be less than zero.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(AddPartMin.getText()) > Integer.parseInt(AddPartMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(AddPartInventory.getText()) > Integer.parseInt(AddPartMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(AddPartInventory.getText()) < Integer.parseInt(AddPartMin.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than inventory.");
                alert.showAndWait();
            }
            else {
                AddPartID.setText(String.valueOf(++ID));
                int id = Integer.parseInt(AddPartID.getText());
                String name = AddPartName.getText();
                double price = Double.parseDouble(AddPartPrice.getText());
                int inventory = Integer.parseInt(AddPartInventory.getText());
                int max = Integer.parseInt(AddPartMax.getText());
                int min = Integer.parseInt(AddPartMin.getText());

                if (inHouse.isSelected()) {
                    int machineID = Integer.parseInt(AddMachineID.getText());
                    InHouse addInHousePart = new InHouse(id, name, price, inventory, min, max, machineID);
                    Inventory.addPart(addInHousePart);
                    exit = true;
                } else if (outsourced.isSelected()) {
                    String companyName = AddMachineID.getText();
                    Outsourced addOutSourcedPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                    Inventory.addPart(addOutSourcedPart);
                    exit = true;
                }
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
     * if you want to cancel the part and delete all the data
     * you have entered.
     * @param actionEvent Cancels the current part
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void cancelPartHandler(ActionEvent actionEvent) throws IOException {
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
}
