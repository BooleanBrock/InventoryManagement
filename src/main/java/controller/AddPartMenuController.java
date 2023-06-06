package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *Controller class that gives control logic for add part window within application
 *
 * @author Wyatt Brock
 */
public class AddPartMenuController implements Initializable {

    Stage stage;

    Parent scene;

    /**
     * Part ID text field
     */
    @FXML
    private TextField partIdTxt;

    /**
     * Part name text field
     */
    @FXML
    private TextField nameTxt;

    /**
     * Part inventory text field
     */
    @FXML
    private TextField invTxt;

    /**
     * Part cost text field
     */
    @FXML
    private TextField priceCostTxt;

    /**
     * Part max inventory level text field
     */
    @FXML
    private TextField maxTxt;

    /**
     * For in-house, Machine ID label, for outsourced, Company Name label
     */
    @FXML
    private Label inOrOutLab;

    /**
     * Part machine ID or Company Name text field
     */
    @FXML
    private TextField machIdOrCompNameTxt;

    /**
     * Part minimum inventory text field
     */
    @FXML
    private TextField minTxt;

    /**
     * The in-house radio button
     */
    @FXML
    private RadioButton inHouseBtn;

    /**
     * The outsourced radio button
     */
    @FXML
    private RadioButton outsourcedBtn;

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
     * Set text at bottom of add part menu to Machine Id for In-house part
     *
     * @param event InHouse radio button
     */
    @FXML
    public void onActionInHousePart(ActionEvent event)
    {
        inOrOutLab.setText("Machine ID");
    }


    /**
     * Set text at bottom of add part menu to Company Name for Outsourced part
     *
     * @param event Outsourced radio button
     */
    @FXML
    public void onActionOutsourcedPart(ActionEvent event)
    {
        inOrOutLab.setText("Company Name");
    }


    /**
     * Save part with user entry and generated ID
     *
     * @param event Save button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException
    {
        try
        {
            int random = (int)(Math.random() * 100);
            int id = random;
            String name = nameTxt.getText();
            int inv = Integer.parseInt(invTxt.getText());
            double priceCost = Double.parseDouble(priceCostTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int machineId = 0;
            String companyName;

            //Throw error if min value is greater than max
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Min and Max values");
                alert.setContentText("Minimum value cannot be greater than maximum value");
                alert.showAndWait();
                return;
            }

            //Throw error if inventory is less than min value or greater than max value
            if(min > inv || max < inv){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Inventory discrepancy");
                alert.setContentText("Inventory should be within min and max values");
                alert.showAndWait();
                return;
            }

            //Implement instance of In-house part using machine ID
            if(inHouseBtn.isSelected()){
                System.out.println("part is in-house");
                machineId = Integer.parseInt(machIdOrCompNameTxt.getText());
                Inventory.addPart(new InHouse(id, name, priceCost, inv, min, max, machineId));
            }

            //Implement instance of Outsourced part using Company Name
            if(outsourcedBtn.isSelected()){
                System.out.println("part is outsourced");
                companyName = machIdOrCompNameTxt.getText();
                Inventory.addPart(new Outsourced(id, name, priceCost, inv, min, max, companyName));
            }

            //Throw error if part is not specified to be in-house or outsourced
            if(!outsourcedBtn.isSelected() && !inHouseBtn.isSelected()){
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
     * @param event Cancel button action
     * @throws IOException from FXMLLoader
     */
    public void onActionReturnToMainMenu(ActionEvent event) throws IOException {
       sceneLoader(event, "/brock/finalproject/MainMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
