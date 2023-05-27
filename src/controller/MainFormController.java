package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the first screen shown to the user, the main form.
 * This class shows table views populated with the current inventory of parts and products.
 * There are paths from this class to all other scenes of the application.
*/
public class MainFormController implements Initializable {
    @FXML
    public TableView<Part> partsTable;
    @FXML
    public TableColumn<Part, Integer> partIdCol;
    @FXML
    public TableColumn<Part, String> partNameCol;
    @FXML
    public TableColumn<Part, Integer> partStockCol;
    @FXML
    public TableColumn<Part, Double> partPriceCol;
    @FXML
    public TableView<Product> productsTable;
    @FXML
    public TableColumn<Product, Integer> productIdCol;
    @FXML
    public TableColumn<Product, String> productNameCol;
    @FXML
    public TableColumn<Product, Integer> productStockCol;
    @FXML
    public TableColumn<Product, Double> productPriceCol;
    @FXML
    public TextField productSearch;
    @FXML
    public TextField partSearch;
    Stage stage;
    Parent scene;

    /**
     * This method changes the scene to the AddPart scene.
     * @param event The add button is clicked beneath the parts TableView.
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        SceneSwitcher AddPartScene = new SceneSwitcher();
        AddPartScene.buttonSwitchScene("/view/AddPart.fxml", event);
    }

    /**
     * This method changes the scene to the ModifyPart scene.
     * @param event The modify button is clicked beneath the parts TableView.
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

        else {
            System.out.println("No selection made. Please select a part from the table to be modified.");
        }
    }

    /**
     * This method removes a selected part from the inventory.
     * @param event The delete button is clicked beneath the parts TableView.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part partForDelete = partsTable.getSelectionModel().getSelectedItem();

        if (partForDelete == null) {
            System.out.println("No selection made. Please select a part from the table to be deleted.");
        }

        else {
            Alert confirmDeletePart = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeletePart.setTitle("Part Deletion Confirmation");
            confirmDeletePart.setContentText("Please confirm you would like to delete the part: \"" + partForDelete.getName() + "\".");

            Optional<ButtonType> result = confirmDeletePart.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                Inventory.deletePart(partForDelete);
                System.out.println("Part deletion successful.");
            }
        }
    }


    /** This method changes the scene to the AddProduct scene.
     * @param event The add button is clicked beneath the products TableView.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        SceneSwitcher AddProductScene = new SceneSwitcher();
        AddProductScene.buttonSwitchScene("/view/AddProduct.fxml", event);
    }

    /** This method changes the scene to the ModifyProduct scene.
     * @param event The modify button is clicked beneath the products TableView.
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        if (productsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(productsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

        else {
            System.out.println("No selection made. Please select a product from the table to be modified.");
        }

    }


    /** This method removes a selected product from the inventory.
     * @param event The delete button is clicked beneath the products TableView.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product productForDelete = productsTable.getSelectionModel().getSelectedItem();

        if (productForDelete == null) {
            System.out.println("No selection made. Please select a product from the table to be deleted.");
        }

        else if (!((productForDelete.getAllAssociatedParts()).isEmpty())) {
            Alert productDeleteError = new Alert(Alert.AlertType.ERROR);
            productDeleteError.setTitle("Product Deletion Error");
            productDeleteError.setContentText("Products with associated parts cannot be deleted. Please make a valid selection and try again.");
            productDeleteError.showAndWait();
        }

        else {
            Alert confirmDeleteProduct = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeleteProduct.setTitle("Product Deletion Confirmation");
            confirmDeleteProduct.setContentText("Please confirm you would like to delete the product: \"" + productForDelete.getName() + "\".");

            Optional<ButtonType> result = confirmDeleteProduct.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                Inventory.deleteProduct(productForDelete);

                System.out.println("Product deletion successful.");
            }
        }
    }


    /** This method takes in user input and searches all parts for a match.
     * @param event The enter button is clicked on the search text-field above the parts TableView.
     */
    @FXML
    void onActionSearchPart(ActionEvent event) {
        try {
            int thisPartSearch = Integer.parseInt(partSearch.getText());

            Part returnedPart = Inventory.lookupPart(thisPartSearch);

            if (returnedPart != null) {
                partsTable.getSelectionModel().select(returnedPart);
            }
        }

        catch (NumberFormatException ex) {
            String thisPartSearch = partSearch.getText();

            partsTable.setItems(Inventory.lookupPart(thisPartSearch));
        }
    }

    /** This method takes in user input and searches all product for a match.
     * @param event The enter button is clicked on the search text-field above the products TableView.
     */
    @FXML
    void onActionSearchProduct(ActionEvent event){
        try {
            int thisProductSearch = Integer.parseInt(productSearch.getText());
            Product returnedProduct = Inventory.lookupProduct(thisProductSearch);

            if (returnedProduct != null) {
                productsTable.getSelectionModel().select(returnedProduct);
            }
        }

        catch (NumberFormatException ex) {
            String thisProductSearch = productSearch.getText();

            productsTable.setItems(Inventory.lookupProduct(thisProductSearch));
        }
    }


    /**
     * This method terminates the application.
     * @param event The exit button is clicked.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This is the initialize method. This method will populate the TableViews with data.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
       partsTable.setItems(Inventory.getAllParts());
       productsTable.setItems(Inventory.getAllProducts());

       partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

       productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
