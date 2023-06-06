package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Provides an inventory of Parts and Products
 *
 * Gives constant data for application
 *
 * @author Wyatt Brock
 */
public class Inventory {

    /**
     * List of all part objects
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all product objects
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * @return list of all part objects
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * @return list of all product objects
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * Add part to list of parts
     *
     * @param newPart Part object to add
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Add part to list of parts
     *
     * @param newProduct Part object to add
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * Update Part object
     *
     * @param index Selected index
     * @param selectedPart Part object to be updated
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update Product object
     *
     * @param index Selected index
     * @param selectedProduct Product object to be updated
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Remove Part object from list of parts
     *
     * @param selectedPart Selected index
     * @return boolean indicating status of part removal
     */
    public static boolean deletePart(Part selectedPart) {return allParts.remove(selectedPart);}

    /**
     * Remove Product object from list of products
     *
     * @param selectedProduct Selected index
     * @return boolean indicating status of product removal
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Find Part object from list of parts by ID
     *
     * @param partId The part ID
     * @return Part object if located, otherwise null
     */
    public static Part lookupPart(int partId) {

        Part placeholder = null;
        for (Part part : allParts) {
            if (partId == part.getId()) {
                placeholder = part;
            }
        }
        return placeholder;
    }

    /**
     * Find Product object from list of products by ID
     *
     * @param productId The product ID
     * @return Product object if found, otherwise null
     */
    public static Product lookupProduct(int productId) {

        Product placeholder = null;

        for (Product product : allProducts) {
            if (productId == product.getId()) {
                placeholder = product;
            }
        }
        return placeholder;
    }

    /**
     *Find Part object from list of parts by name
     *
     * @param partName The name of the part
     * @return The part object, otherwise null
     */
    public static ObservableList lookupPart(String partName) {
        ObservableList<Part> foundPart = FXCollections.observableArrayList();

        if (partName.length() == 0) {
            foundPart = allParts;
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().toUpperCase().contains(partName.toUpperCase())) {
                    foundPart.add(allParts.get(i));
                }
            }
        }
        return foundPart;
    }

    /**
     * Find the Product object from list of products by name
     *
     * @param productName Name of the product
     * @return Product object, otherwise null
     */
    public static ObservableList lookupProduct(String productName) {
        ObservableList<Product> searchProduct = FXCollections.observableArrayList();

        if (productName.length() == 0) {
            searchProduct = allProducts;
        } else {

            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName().toUpperCase().contains(productName.toUpperCase())) {
                    searchProduct.add(allProducts.get(i));
                }
            }
        }
        return searchProduct;
    }
}



















