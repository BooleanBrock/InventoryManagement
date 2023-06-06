package brock.finalproject;

//Wyatt Brock
//Student ID: 010518994

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.io.IOException;

/**
 * The Inventory Management Program implements an application for managing the inventory of
 * products and the parts that make those products. These parts can either be made in-house or outsourced.
 * The parts that make the products can be associated to the product. Searchbars are implemented to make parts
 * and products easy to find by name or ID.
 *
 * JAVADOCS LOCATION: Navigate to brock.finalproject.Javadocs/brock/Javadocs (3rd folder down, just under .mvn) and all javadocs for the project can be found there.
 *
 * FUTURE ENCHANCEMENT: A future improvement would include extra menus for assigning how many parts are being dedicated to a product
 * and subtracting the amount of parts being used up from the inventory of those parts.
 *
 * @author Wyatt Brock
 */
public class Main extends Application {
    /**The start method creates the initial FXML stage and sets the scene.
     *
     * @param stage stage
     * @throws IOException IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("/brock/finalproject/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    /**The main method is the beginning entry point of the application
     * @param args args
     */
    public static void main(String[] args) {

        //Initial sample parts and products
        Inventory.addPart(new InHouse(1, "The Last Crusade", 59.99, 50, 1, 80, 42));
        Inventory.addPart(new InHouse(3, "Raiders of the Lost Ark", 29.99, 15, 1, 35, 27));
        Inventory.addPart(new InHouse(9, "The Temple of Doom", 19.99, 15, 1, 35, 27));

        Inventory.addPart(new Outsourced(37, "Revenge of the Sith", 30.99, 100, 3, 200, "Lucas Arts"));
        Inventory.addPart(new Outsourced(28, "Empire Strikes Back", 50.99, 100, 3, 200, "Lucas Arts"));
        Inventory.addPart(new Outsourced(19, "Return of the Jedi", 50.99, 100, 3, 200, "George Movies"));

        Inventory.addProduct(new Product(105, "Star Wars", 600.00, 30, 10, 50));
        Inventory.addProduct(new Product(112, "Indiana Jones", 79.99, 500, 2, 600));

        //Add sample created product
        int randomNumber = (int) (Math.random() * 1000);
        int  productId = randomNumber;
        Product blockbuster = new Product(productId, "Jaws", 39.99, 40, 1, 80);
        Inventory.addProduct(blockbuster);

        launch(args);
    }
}