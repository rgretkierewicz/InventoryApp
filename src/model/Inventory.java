package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used for creating, modifying, and accessing data for part and product lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    private static int partsIdCounter = 1;

    /**
     * @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId the part id to search
     * @return part if found, null if part not found
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        System.out.println("No search results found for a part with an id of: " + partId);
        return null;
    }

    /**
     * @param productId the product id to search
     * @return product if found, null if product not found
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        System.out.println("No search results found for a product with an id of: " + productId);
        return null;
    }

    /**
     * @param partName the part name to search
     * @return all parts containing part name if found, null if no parts are found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        if(!(filteredParts.isEmpty())) {
            filteredParts.clear();
        }

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                filteredParts.add(part);
            }
        }

        if (filteredParts.isEmpty()) {
            System.out.println("No search results found for a part name containing \"" + partName + "\"");
            return allParts;
        }

        else {
            return filteredParts;
        }
    }

    /**
     * @param productName the product name to search
     * @return all products containing product name if found, null if no products are found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        if(!(filteredProducts.isEmpty())) {
            filteredProducts.clear();
        }

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        if (filteredProducts.isEmpty()) {
            System.out.println("No search results found for a product name containing \"" + productName + "\"");
            return allProducts;
        }

        else {
            return filteredProducts;
        }
    }


    /**
     * @param index the index of the part to update
     * @param selectedPart the updated part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * @param index the index of the product to update
     * @param newProduct the updated product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart the part to delete
     * @return true if the part is found and removed, false if the part is not found
     */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart == null) {
            return false;
        }
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * @param selectedProduct the product to delete
     * @return true if the product is found and removed, false if the product is not found
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct == null) {
            return false;
        }
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * @return all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @return the filtered parts
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    /**
     * @return the filtered products
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * @return the id created for a part
     */
    public static int createPartID() {
        return partsIdCounter++;
    }

}
