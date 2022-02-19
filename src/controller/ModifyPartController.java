package controller;

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

public class ModifyPartController  {

    /**
     * The toggle group for the radio buttons
     * */
    @FXML
    public ToggleGroup toggleGroup;

    /**
     * The inHouse radio button
     * */
    @FXML
    public RadioButton inHouseMod;

    /**
     * The outsourced radio button
     * */
    @FXML
    public RadioButton outsourcedMod;

    /**
     * MachineId or Company name label
     * changes depending on what radio
     * button is selected
     * */
    @FXML
    public Label toggleChange;

    /**
     * Text field to edit part name
     * */
    @FXML
    public TextField modifyPartName;

    /**
     * Text field to edit the parts price
     * */
    @FXML
    public TextField modifyPartPrice;

    /**
     * Text field to edit current stock
     * */
    @FXML
    public TextField modifyPartInventory;

    /**
     * Text field to edit the parts maximum stock
     * */
    @FXML
    public TextField modifyPartMax;

    /**
     * Text field to edit machineId or company name
     * */
    @FXML
    public TextField modifyMachineID;

    /**
     * Text field to add the parts minimum stock
     * */
    @FXML
    public TextField modifyPartMin;

    /**
     * Text field to add the parts ID
     * */
    @FXML
    public TextField modifyPartID;

    /** When the inHouse radio button is selected
     * the toggleChange label and addMachineID
     * text field are updated
     * @param actionEvent inHouse radio button is selected
     * */
    @FXML
    public void onInHouseMod(ActionEvent actionEvent) {
        toggleChange.setText("Machine ID");
        modifyMachineID.setPromptText("Add Machine ID");
    }

    /** When the outsourced radio button is selected
     * the toggleChange label and addMachineID
     * text field are updated
     * @param actionEvent outsourced radio button is selected
     * */
    @FXML
    public void onOutsourcedMod(ActionEvent actionEvent) {
        toggleChange.setText("Company Name");
        modifyMachineID.setPromptText("Add Company Name");
    }

    /** Saves the new part and removes the old one
     * and then adds the new part to allParts
     * in inventory
     * @param actionEvent Save button is clicked
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void saveModPartHandler(ActionEvent actionEvent) throws IOException {
        boolean exit = false;

        try {

            if (Integer.parseInt(modifyPartInventory.getText()) < 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory can not be less than zero.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(modifyPartMin.getText()) > Integer.parseInt(modifyPartMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(modifyPartInventory.getText()) > Integer.parseInt(modifyPartMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory is larger than max parts.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(modifyPartInventory.getText()) < Integer.parseInt(modifyPartMin.getText())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min parts is larger than inventory.");
                alert.showAndWait();
            }
            else {
                int id = Integer.parseInt(modifyPartID.getText());
                String name = modifyPartName.getText();
                double price = Double.parseDouble(modifyPartPrice.getText());
                int inventory = Integer.parseInt(modifyPartInventory.getText());
                int min = Integer.parseInt(modifyPartMin.getText());
                int max = Integer.parseInt(modifyPartMax.getText());

                if (inHouseMod.isSelected()) {
                    int machineID = Integer.parseInt(modifyMachineID.getText());
                    InHouse modInHousePart = new InHouse(id, name, price, inventory, min, max, machineID);
                    Inventory.updatePart(id, modInHousePart);
                    exit = true;
                } else if (outsourcedMod.isSelected()) {
                    String companyName = modifyMachineID.getText();
                    Outsourced modOutSourcedPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                    Inventory.updatePart(id, modOutSourcedPart);
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
     * if you want to cancel the part and delete all the new
     * data you have entered.
     * @param actionEvent Cancels editing the current part
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void cancelPartHandler(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all the data you have modified! Do you want to continue?");
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


    /**RUNTIME ERROR
     *
     * Sets all the text fields to their appropriate data
     *
     * I was having trouble checking if the part was
     * inHouse of outsourced when I was trying to modify it.
     * So instead of using initialize I used the setPart
     * function below, so I could carry over the data
     * from the MainScreenController to here. Also, I had trouble
     * changing the radio buttons to their appropriate data.
     * I tried using the onInHouseMod and onOutsourcedMod functions,
     * but just chose to set the data in the below function.
     *
     * @param selectedPart the part you selected from MainScreenController
     */
    @FXML
    public void setPart(Part selectedPart) {

        if(selectedPart instanceof InHouse){
            inHouseMod.setSelected(true);
            toggleChange.setText("Machine ID");
            modifyMachineID.setPromptText("Add Machine ID");
            InHouse inhouse = (InHouse)selectedPart;
            modifyPartID.setText(Integer.toString(inhouse.getPartId()));
            modifyPartName.setText(inhouse.getPartName());
            modifyPartPrice.setText(Double.toString(inhouse.getPartPrice()));
            modifyPartInventory.setText(Integer.toString(inhouse.getPartStock()));
            modifyPartMax.setText(Integer.toString(inhouse.getPartMax()));
            modifyPartMin.setText(Integer.toString(inhouse.getPartMin()));
            modifyMachineID.setText(Integer.toString(inhouse.getMachineId()));

        } else if(selectedPart instanceof Outsourced){
            outsourcedMod.setSelected(true);
            toggleChange.setText("Company Name");
            modifyMachineID.setPromptText("Add Company Name");
            Outsourced outsourced = (Outsourced)selectedPart;
            modifyPartID.setText(Integer.toString(outsourced.getPartId()));
            modifyPartName.setText(outsourced.getPartName());
            modifyPartPrice.setText(Double.toString(outsourced.getPartPrice()));
            modifyPartInventory.setText(Integer.toString(outsourced.getPartStock()));
            modifyPartMax.setText(Integer.toString(outsourced.getPartMax()));
            modifyPartMin.setText(Integer.toString(outsourced.getPartMin()));
            modifyMachineID.setText(outsourced.getCompanyName());
        }
    }
}
