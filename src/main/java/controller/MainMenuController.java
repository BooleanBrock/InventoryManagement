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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides control logic for main menu window of application
 *
 * @author Wyatt Brock
 */
public class MainMenuController implements Initializable {


    Stage stage;

    Parent scene;

    /**
     * Searchbar textfield for parts table
     */
    @FXML
    private TextField partSearchBox;

    /**
     * Searchbar textfield for products table
     */
    @FXML
    private TextField productSearchBox;

    /**
     * Table for parts
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Column for part ID
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /**
     * Column for part name
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * Column for part inventory
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /**
     * Column for part price/cost
     */
    @FXML
    private TableColumn<Part, Double> partPriceCostCol;

    /**
     * Table for products
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * Column for product ID
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    /**
     * Column for product name
     */
    @FXML
    private TableColumn<Product, String> productNameCol;

    /**
     * Column for product inventory
     */
    @FXML
    private TableColumn<Product, Integer> productInvCol;

    /**
     * Column for product price/cost
     */
    @FXML
    private TableColumn<Product, Double> productPriceCostCol;


    /**
     * Loads AddPartMenuController
     *
     * @param event Add button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        sceneLoader(event, "/brock/finalproject/AddPartMenu.fxml");
    }

    /**
     * Loads ModifyPartMenuController and pulls info for selected part
     *
     * @param event Modify button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/brock/finalproject/ModifyPartMenu.fxml"));
            loader.load();

            ModifyPartMenuController MPartController = loader.getController();
            MPartController.sendPart(partTableView.getSelectionModel().getSelectedIndex(), partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

        catch (NullPointerException e) {
            warningBox("No item selected", "Please select an item to modify");
        }
    }


    /**
     * Method for searchbar to search for part in parts table
     *
     * @param event Search button action
     */
    @FXML
    public void onActionSearchPart(ActionEvent event) {

        String userEntry = partSearchBox.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(userEntry);

        try {
            while(foundParts.size() == 0) {

                int foundPartId = Integer.parseInt(userEntry);
                foundParts.add(Inventory.lookupPart(foundPartId));
        }
            partTableView.setItems(foundParts);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Match not found");
            alert.setContentText("Unable to find matching part");
            alert.showAndWait();
        }
    }


    /**
     * Remove part from parts table
     *
     * @param event Delete button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) throws IOException {


        if (partTableView.getSelectionModel().isEmpty()) {
            warningBox("Error: No item selected", "Please select an item to delete.");
        }
        else if(confirmBox("Confirmation", "Are you sure you want to delete this item?")){
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
        }

    }


    /**
     * Method to quickly implement confirmation alerts
     *
     * @param title Title of confirmation alert
     * @param content Content of confirmation alert
     * @return True if okay button selected, false if cancel button selected
     */
    static boolean confirmBox(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     *Method to quickly implement warning alerts
     *
     * @param title Title for warning alert
     * @param content Content for warning alert
     */
    static void warningBox(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }

    /**
     * Method to allow for quick setting and loading of scenes
     *
     * @param event Any button that leads to another window action
     * @param source Path provided to function
     * @throws IOException from FXMLLoader
     */
     public void sceneLoader(ActionEvent event, String source) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource(source));

        stage.setScene(new Scene(scene));

        stage.show();
    }


    /**
     * Load AddProductMenuController
     *
     * @param event Add button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException{
        sceneLoader(event, "/brock/finalproject/AddProductMenu.fxml");
    }


    /**
     * Load ModifyProductMenuController and pull selected product data
     *
     * @param event Modify button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException{

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/brock/finalproject/ModifyProductMenu.fxml"));
            loader.load();

            ModifyProductMenuController MProdController = loader.getController();
            MProdController.sendProduct(productTableView.getSelectionModel().getSelectedIndex(), productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

        catch (NullPointerException e) {

            warningBox("No item selected", "Please select a product to modify.");
        }
    }

    /**
     * Implement search function using searchbar above product table
     *
     * @param actionEvent Searchbar entry action
     */
    @FXML
    public void onActionSearchProduct(ActionEvent actionEvent) {
        String userProduct = productSearchBox.getText();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(userProduct);

        try {
            while(foundProducts.size() == 0) {

                int foundProductId = Integer.parseInt(userProduct);
                foundProducts.add(Inventory.lookupProduct(foundProductId));
            }
            productTableView.setItems(foundProducts);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Match not found");
            alert.setContentText("Unable to find matching product");
            alert.showAndWait();
        }

    }

    /**
     * Delete selected product from products table
     *
     * @param event Delete button action
     * @throws IOException from FXMLLoader
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException{
        if (productTableView.getSelectionModel().isEmpty()) {
            warningBox("Error: No item selected", "Please select an item to delete.");
        }

        //Throw error if product to be deleted still has associated parts

        else if(!productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
            warningBox("Error: Parts Associated to Product", "All associations to this part must be deleted before the product may be deleted.");
        }
        else if(confirmBox("Confirmation", "Are you sure you want to delete this item?")){
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(selectedProduct);
        }

    }


    /**
     * Exit application
     *
     * @param event Exit button action
     */
    @FXML
    void onActionExit(ActionEvent event) {
        warningBox("Exit", "Are you sure you want to exit?");
        System.exit(0);
    }


    /**
     * Intiliaze MainMenuController and display all parts and all products on their own tables
     *
     * @param url Location to resolve relative path to root, or null if unknown
     * @param resourceBundle used to find location of root object, or null if none localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}


