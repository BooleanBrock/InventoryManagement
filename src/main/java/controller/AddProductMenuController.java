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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *Controller class that gives control logic for add product window within application
 *
 * @author Wyatt Brock
 */
public class AddProductMenuController implements Initializable {

    Stage stage;
    Parent scene;
    /**
     * Product ID for the product
     */
    @FXML
    private TextField prodIdTxt;

    /**
     * Name for the product
     */
    @FXML
    private TextField prodNameTxt;

    /**
     * Inventory for the product
     */
    @FXML
    private TextField prodInvTxt;

    /**
     * Price/cost for the product
     */
    @FXML
    private TextField prodPriceCostTxt;

    /**
     * Max inventory level for the product
     */
    @FXML
    private TextField prodMaxTxt;

    /**
     * Min inventory level for the product
     */
    @FXML
    private TextField prodMinTxt;

    /**
     * Searchbar text field for all parts table at top right of screen
     */
    @FXML
    private TextField associatedPartSearchBar;

    /**
     * Table for the parts that are associated to the product
     */
    @FXML
    private TableView<Part> associatedPartTableView;

    /**
     * Column for the price of the associated part
     */
    @FXML
    private TableColumn<?, ?> associatedPartPriceCol;

    /**
     * Column for the inventory of the associated part
     */
    @FXML
    private TableColumn<?, ?> associatedPartInvCol;

    /**
     * Column for the name of the associated part
     */
    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    /**
     * Column for the ID of the associated part
     */
    @FXML
    private TableColumn<?, ?> associatedPartIdCol;

    /**
     * Table for all parts
     */
    @FXML
    private TableView allPartsTableView;

    /**
     * Column for names of all parts
     */
    @FXML
    private TableColumn<?, ?> allPartsNameCol;

    /**
     * Column for inventory of all parts
     */
    @FXML
    private TableColumn<?, ?> allPartsInvCol;

    /**
     * Column for price/cost of all parts
     */
    @FXML
    private TableColumn<?, ?> allPartsPriceCol;

    /**
     * Column for ID of all parts
     */
    @FXML
    private TableColumn<?, ? >allPartsIdCol;

    /**
     * List of all parts that are associated to a product
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();


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
     * Save product with user entry and all associations to product
     *
     * @param event Save button action
     */
    public void onActionSaveProduct(ActionEvent event) {

        try
        {

            int randomNumber = (int) (Math.random() * 1000);
            int prodId = randomNumber;

            String prodName = prodNameTxt.getText();
            int prodInventory = Integer.parseInt(prodInvTxt.getText());
            double prodCostPrice = Double.parseDouble(prodPriceCostTxt.getText());
            int prodMax = Integer.parseInt(prodMaxTxt.getText());
            int prodMin = Integer.parseInt(prodMinTxt.getText());

            //Throw error if min value is greater than max
            if(prodMin > prodMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Incorrect min and max values");
                alert.setContentText("Minimum value cannot be greater than maximum value");
                alert.showAndWait();
                return;
            }

            //Throw error if inventory is greater than max value or less than min value
            if(prodMin > prodInventory || prodMax < prodInventory){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: Inventory discrepancy");
                alert.setContentText("Inventory should be within min and max values");
                alert.showAndWait();
                return;
            }

            //Check for all associated parts and add them to the product
            Product product = new Product(prodId, prodName, prodCostPrice, prodInventory, prodMin, prodMax);
            for(Part matchPart: associatedParts){
                if(matchPart != associatedParts){
                    product.addAssociatedPart(matchPart);
                }
            }

            Inventory.addProduct(product);

            sceneLoader(event, "/brock/finalproject/MainMenu.fxml");
        }

        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Incorrect input");
            alert.setContentText("Incorrect input value has been entered, please check all input values.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Return to main menu screen when cancel button is clicked
     *
     * @param event Cancel button action
     * @throws IOException from FXMLLoader
     */
    public void onActionReturnToMainMenu(ActionEvent event) throws IOException {
        sceneLoader(event, "/brock/finalproject/MainMenu.fxml");
    }


    /**
     * Remove association between part and product
     *
     * @param actionEvent Remove association button action
     */
    public void onActionDeleteAssociatedPart(ActionEvent actionEvent) {
        Part deletePart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if(deletePart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Part Selected");
            alert.setContentText("Please select a part to delete from the parts list.");
            alert.showAndWait();
        } else if (associatedParts.contains(deletePart)) {
            associatedParts.remove(deletePart);
            associatedPartTableView.setItems(associatedParts);
        }
    }


    /**
     * Add association between part and product
     *
     * @param actionEvent Add association button action
     */
    public void onActionAddAssociatedPart(ActionEvent actionEvent) {
        Part desiredPart = (Part) allPartsTableView.getSelectionModel().getSelectedItem();

        if(desiredPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: No Part Selected");
            alert.setContentText("Please select a part from the list to associate to the product.");
            alert.showAndWait();
        }
        else if(!associatedParts.contains(desiredPart)){
            associatedParts.add(desiredPart);
            associatedPartTableView.setItems(associatedParts);
        }
    }


    /**
     * Method to search for parts to add associations
     *
     * @param event Searchbar entry action
     */
    public void onActionSearchAssociatedParts(ActionEvent event) {
        //Get user-entered info
        String userSearch = associatedPartSearchBar.getText();

        //Find user-requested info using name
        ObservableList<Part> searchedParts = Inventory.lookupPart(userSearch);

        //Also attempt to find info using int entered by user
        try
        {
            while(searchedParts.size() == 0){
                int userSearchId = Integer.parseInt(userSearch);
                searchedParts.add(Inventory.lookupPart(userSearchId));
            }

            allPartsTableView.setItems(searchedParts);

        }

        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Not Found");
            alert.setContentText("This part could not be found.");
            alert.showAndWait();
        }
    }


    /**
     * Controller intializes tp display all parts and all associated parts to their respecting tables
     *
     * @param url Location to resolve relative path to root, or null if unknown
     * @param resourceBundle used to find location of root object, or null if none localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsTableView.setItems(Inventory.getAllParts());

        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.setItems(associatedParts);

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


}
