package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * Controller class that provides control logic for modify part window in application
 *
 * @author Wyatt Brock
 */
public class ModifyPartMenuController {



    Stage stage;

    Parent scene;

    private int partCurrentIndex = 0;

    /**
     * InHouse radio button
     */
    @FXML
    private RadioButton modPartInHouseRadBtn;

    /**
     * Outsourced radio button
     */
    @FXML
    private RadioButton modPartOutsourceRadBtn;

    /**
     * Machine ID or Company Name label
     */
    @FXML
    private Label modPartMachIdOrCompNameLbl;

    /**
     * Part ID text field
     */
    @FXML
    private TextField modPartIdTxt;

    /**
     * Part name text field
     */
    @FXML
    private TextField modPartNameTxt;

    /**
     * Part inventory text field
     */
    @FXML
    private TextField modPartInvTxt;

    /**
     * Part price/cost text field
     */
    @FXML
    private TextField modPartPriceTxt;

    /**
     * Part max text field
     */
    @FXML
    private TextField modPartMaxTxt;

    /**
     * Part min text field
     */
    @FXML
    private TextField modPartMinTxt;

    /**
     * Machine ID or Company Name text field
     */
    @FXML
    private TextField modPartMachIdOrCompNameTxt;


    /**
     * Method to allow for quick setting and loading of scenes
     *
     * @param event action when button is clicked
     * @param source Given source for desired scene
     * @throws IOException FXMLLoader
     */
    public void sceneLoader(ActionEvent event, String source) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource(source));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /**
     * Updates part in inventory and loads up MainMenuController
     *
     * Text fields are validated with alerts thrown when data is not entered or invalid
     *
     * @param event Save button action
     * @throws IOException from FXMLLoader
     */
    public void onActionSavePart(ActionEvent event) throws IOException {
        try
        {
            int newPartId = Integer.parseInt(modPartIdTxt.getText());
            String newPartName = modPartNameTxt.getText();
            int newPartInv = Integer.parseInt(modPartInvTxt.getText());
            double newPartPrice = Double.parseDouble(modPartPriceTxt.getText());
            int newPartMax = Integer.parseInt(modPartMaxTxt.getText());
            int newPartMin = Integer.parseInt(modPartMinTxt.getText());
            int newPartMachineId;
            String newPartCompanyName;

            //Throw alert if modified min is greater than modified max
            if(newPartMin > newPartMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Min and Max values");
                alert.setContentText("Minimum value cannot be greater than maximum value");
                alert.showAndWait();
                return;
            }

            //Throw alert if modified inventory is greater than max or less than min values
            if(newPartMin > newPartInv || newPartMax < newPartInv){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Inventory discrepancy");
                alert.setContentText("Inventory should be within min and max values");
                alert.showAndWait();
                return;
            }

            //Switch modified part to In-house if radio button selected
            if(modPartInHouseRadBtn.isSelected()){
                System.out.println("part is in-house");
                newPartMachineId = Integer.parseInt(modPartMachIdOrCompNameTxt.getText());
                InHouse modPart = new InHouse(newPartId, newPartName, newPartPrice, newPartInv, newPartMin, newPartMax, newPartMachineId);
                Inventory.updatePart(partCurrentIndex, modPart);
            }

            //Swtich modified part to outsourced if radio button is selected
            if(modPartOutsourceRadBtn.isSelected()){
                System.out.println("part is outsourced");
                newPartCompanyName = modPartMachIdOrCompNameTxt.getText();
                Outsourced modPart = new Outsourced(newPartId, newPartName, newPartPrice, newPartInv, newPartMin, newPartMax, newPartCompanyName);
                Inventory.updatePart(partCurrentIndex, modPart);
            }

            //Throw alert if neither radio button is selected
            if(!modPartOutsourceRadBtn.isSelected() && !modPartInHouseRadBtn.isSelected()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Part type not selected");
                alert.setContentText("Please indicate whether part is in-house or outsourced");
                alert.showAndWait();
                return;
            }

            sceneLoader(event, "/brock/finalproject/MainMenu.fxml");

        }

        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Incorrect input");
            alert.setContentText("Incorrect input value has been entered, please check values.");
            alert.showAndWait();
        }

    }

    /**
     * Loads MainMenuController
     *
     * @param event Cancel button action
     * @throws IOException from FXMLLoader
     */
    public void onActionReturnToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/brock/finalproject/MainMenu.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }


    /**
     * Send data to autofill spaces for selected part
     *
     * @param selectedIndex Index of selected part from table
     * @param selectedPart Part selected from table
     */
    public void sendPart(int selectedIndex, Part selectedPart){
        partCurrentIndex = selectedIndex;

        if(selectedPart instanceof InHouse){
            modPartInHouseRadBtn.setSelected(true);
            modPartMachIdOrCompNameTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        else{
            modPartOutsourceRadBtn.setSelected(true);
            modPartMachIdOrCompNameLbl.setText("Company Name:");
            modPartMachIdOrCompNameTxt.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }


        modPartIdTxt.setText(String.valueOf(selectedPart.getId()));
        modPartNameTxt.setText(selectedPart.getName());
        modPartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        modPartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modPartMinTxt.setText(String.valueOf(selectedPart.getMin()));
        modPartMaxTxt.setText((String.valueOf(selectedPart.getMax())));


    }

    /**
     * Change bottom label to Machine ID if part is In-house
     *
     * @param event In-house radio button action
     */
    public void onActionChangePartToInHouse(ActionEvent event) {
        if(modPartInHouseRadBtn.isSelected())
            modPartMachIdOrCompNameLbl.setText("Machine ID");
    }


    /**
     * Change bottom label to Company Name if part is outsourced
     *
     * @param event Outsourced radio button action
     */
    public void onActionChangePartToOutsourced(ActionEvent event) {
        if(modPartOutsourceRadBtn.isSelected())
            modPartMachIdOrCompNameLbl.setText("Company Name");
    }
}
