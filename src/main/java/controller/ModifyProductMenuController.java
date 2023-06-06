package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *Controller that provides control logic for modify product window in application
 *
 * LOGIC ERROR:
 * A logical error occurred when if(!associatedParts.contains(PartToProduct) was used to check for an association between a part
 * and a product and add a new association if one was not found. When the product was selected to be modified, its associated parts were not saved
 * and did not appear in the associated parts table. This was corrected by altering the if statement to be if(PartToProduct != associatedParts).
 * This corrected the logic error and allowed the application to check the list of part objects to see if the association could be added. This error section can
 * be found under the  onActionSaveModifiedProduct method of this class.
 *
 * @author Wyatt Brock
 */
public class ModifyProductMenuController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * Product ID text field
     */
    @FXML private TextField modProdIdTxt;

    /**
     * Product name text field
     */
    @FXML private TextField modProdNameTxt;

    /**
     * Product inventory text field
     */
    @FXML private TextField modProdInvTxt;

    /**
     * Product price/cost text field
     */
    @FXML private TextField modProdPriceTxt;

    /**
     * Product max text field
     */
    @FXML private TextField modProdMaxTxt;

    /**
     * Product min text field
     */
    @FXML private TextField modProdMinTxt;

    /**
     * Searchbar text field for all parts table
     */
    @FXML private TextField modifyAssociatedPartsSearch;

    /**
     * Table displaying all parts
     */
    @FXML private TableView modifyAllPartsTableView;

    /**
     * Table displaying all associated parts
     */
    @FXML private TableView modifyAssociatedPartsTableView;

    /**
     * Column for IDs of all parts in table
     */
    @FXML private TableColumn<?, ?> modAllPartsIdCol;

    /**
     * Column for names of all parts in table
     */
    @FXML private TableColumn<?, ?> modyAllPartsNameCol;

    /**
     * Column for inventory of all parts in table
     */
    @FXML private TableColumn<?, ?> modAllPartsInvCol;

    /**
     * Column for price/cost of all parts in table
     */
    @FXML private TableColumn<?, ?> modAllPartsPriceCol;

    /**
     * Column for IDs of associated parts in table
     */
    @FXML private TableColumn<?, ?> modAssociatedPartIdCol;

    /**
     * Column for names of associated parts in table
     */
    @FXML private TableColumn<?, ?> modAssociatedPartNameCol;

    /**
     * Column for inventory of associated parts in table
     */
    @FXML private TableColumn<?, ?> modAssociatedPartInvCol;

    /**
     * Column for price/cost of associated parts in table
     */
    @FXML private TableColumn<?, ?> modAssociatedPartPriceCol;

    /**
     * List of all associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int currentIndex = 0;

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
     * Updates product in inventory and loads up MainMenuController
     *
     * Text fields are validated with alerts thrown when data is not entered or invalid
     *
     * @param event Save button action
     * @throws IOException from FXMLLoader
     */
    public void onActionSaveModifiedProduct(ActionEvent event) throws IOException {
        try {

            int newProdId = Integer.parseInt(modProdIdTxt.getText());
            String newProdName = modProdNameTxt.getText();
            int newProdInv = Integer.parseInt(modProdInvTxt.getText());
            double newProdPrice = Double.parseDouble(modProdPriceTxt.getText());
            int newProdMax = Integer.parseInt(modProdMaxTxt.getText());
            int newProdMin = Integer.parseInt(modProdMinTxt.getText());

            //Throw alert if modified product min value is greater than max
            if (newProdMin > newProdMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Incorrect min and max values");
                alert.setContentText("Minimum value cannot be greater than maximum value");
                alert.showAndWait();
                return;

             //Throw alert if modified product inventory greater than max or less than miv values
            } else if (newProdMin > newProdInv || newProdMax < newProdInv) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Inventory discrepancy");
                alert.setContentText("Inventory should be within min and max values");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(newProdId, newProdName, newProdPrice, newProdInv, newProdMin, newProdMax);

            //update product in inventory
            if(newProduct != associatedParts){
                Inventory.updateProduct(currentIndex, newProduct);
            }

            //Update any newly associated or unassociated parts to product
            for(Part PartToProduct : associatedParts){
                if(PartToProduct != associatedParts) {
                    newProduct.addAssociatedPart(PartToProduct);
                }
            }

            sceneLoader(event, "/brock/finalproject/MainMenu.fxml");

        } catch (NumberFormatException e) {
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

        sceneLoader(event, "/brock/finalproject/MainMenu.fxml");
    }

    /**
     * Add association between part and product
     *
     * @param actionEvent Add association button action
     */
    public void onActionAddAssociatedPart(ActionEvent actionEvent) {

        Part modifyAssociatedPart = (Part) modifyAllPartsTableView.getSelectionModel().getSelectedItem();

        //Throw error if no part is selected to add association
        if(modifyAssociatedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please select part from top table to associate to product");
            alert.showAndWait();
        }

        //Add association and display it on associated parts table
        else if(!associatedParts.contains(modifyAssociatedPart)){
            associatedParts.add(modifyAssociatedPart);
            modifyAssociatedPartsTableView.setItems(associatedParts);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Duplicate Association");
            alert.setContentText("This part is already associated to this product");
            alert.showAndWait();
        }
    }

    /**
     * Remove association between part and product
     *
     * @param event Remove association button action
     */
    public void onActionDeleteAssociatedPart(ActionEvent event) {

        Part partAssociation = (Part) modifyAssociatedPartsTableView.getSelectionModel().getSelectedItem();

        //Throw alert if no part selected to remove association
        if(partAssociation == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please select part from bottom table to remove association to product");
            alert.showAndWait();
        }

        //Remove association on selected part
        if(associatedParts.contains(partAssociation)){
            associatedParts.remove(partAssociation);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Association Not Found");
            alert.setContentText("This part is not associated to this product.");
            alert.showAndWait();
        }
    }



    /**
     * Send data to autofill spaces for selected product
     *
     * @param selectedIndex Index of selected product from table
     * @param selectedProduct Product selected from table
     */
    public void sendProduct(int selectedIndex, Product selectedProduct){

        currentIndex = selectedIndex;
        modProdIdTxt.setText(String.valueOf(selectedProduct.getId()));
        modProdNameTxt.setText(selectedProduct.getName());
        modProdInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        modProdPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        modProdMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        modProdMaxTxt.setText((String.valueOf(selectedProduct.getMax())));

        for(Part matchingPart: selectedProduct.getAllAssociatedParts()){
            associatedParts.add(matchingPart);
        }
    }


    /**
     * Search for part in all parts table using given user text entry
     *
     * @param actionEvent Searchbar text entry action
     */
    public void onActionSearchModifyAssociatedParts(ActionEvent actionEvent) {

        String userEntry = modifyAssociatedPartsSearch.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(userEntry);

        try{
            while(searchResults.size() == 0){
                int userIdEntry = Integer.parseInt(userEntry);
                searchResults.add(Inventory.lookupPart(userIdEntry));
            }

            modifyAllPartsTableView.setItems(searchResults);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Found");
            alert.setContentText("No part could be found that matched this entry");
            alert.showAndWait();
        }
    }

    /**
     * Update table of associated parts
     */
    public void updateAssociationTable(){
        modifyAssociatedPartsTableView.setItems(associatedParts);
    }

    /**
     * update table of all parts
     */
    public void updateAllPartsTable(){
        modifyAllPartsTableView.setItems(Inventory.getAllParts());
    }

    /**
     * Controller intializes tp display all parts and all associated parts to their respecting tables
     *
     * @param url Location to resolve relative path to root, or null if unknown
     * @param resourceBundle used to find location of root object, or null if none localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyAllPartsTableView.setItems(Inventory.getAllParts());

        modAllPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modyAllPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modAllPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modAllPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyAssociatedPartsTableView.setItems(associatedParts);

        modAssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modAssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modAssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modAssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        updateAssociationTable();
        updateAllPartsTable();
    }
}
