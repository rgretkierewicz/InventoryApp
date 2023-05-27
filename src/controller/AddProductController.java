package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class takes in entered data and creates a new product. */
public class AddProductController implements Initializable {
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;
    @FXML
    private TableColumn<Part, String> assocPartNameCol;
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;
    @FXML
    private TableColumn<Part, Integer> assocPartStockCol;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String>  partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Product, Integer> partStockCol;
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableView<Part> assocPartsTable;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField stockTxt;
    @FXML
    private TextField partSearch;
    private ObservableList<Part> assocPartsList = FXCollections.observableArrayList();

    /**
     * This method adds a selected part to the product's list of associated parts.
     * @param event The add button is clicked.
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectedPart =  allPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            assocPartsList.add(selectedPart);
        }
    }

    /**
     * This method returns the application to the MainForm scene.
     * @param event The cancel button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        SceneSwitcher MainFormScene = new SceneSwitcher();
        MainFormScene.buttonSwitchScene("/view/MainForm.fxml", event);
    }

    /**
     * This method removes a selected part from the product's list of associated parts.
     * @param event The remove button is clicked.
     */
    @FXML
    void onActionRemoveAssocPart(ActionEvent event) {
        Part selectedPart = assocPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            assocPartsList.remove(selectedPart);
        }
    }


    /**
     * This method creates a new product from the entered data. The application then returns to the MainForm scene.
     * @param event The save button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = Product.createProductID();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(stockTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if (min > max) {
                Alert minInputError = new Alert(Alert.AlertType.ERROR);
                minInputError.setTitle("Invalid Input Error");
                minInputError.setContentText("The minimum value cannot be greater than the maximum value. Please enter valid values.");
                minInputError.showAndWait();
            }

            else if (!(stock <= max && stock >= min)) {
                Alert stockInputError = new Alert(Alert.AlertType.ERROR);
                stockInputError.setTitle("Invalid Input Error");
                stockInputError.setContentText("The inventory value must be greater than the minimum and less than the maximum. Please enter valid values.");
                stockInputError.showAndWait();
            }

            else {
                Product newProduct = new Product(id, name, price, stock, min, max);

                newProduct.getAllAssociatedParts().addAll(assocPartsList);

                Inventory.addProduct(newProduct);

                SceneSwitcher MainFormScene = new SceneSwitcher();
                MainFormScene.buttonSwitchScene("/view/MainForm.fxml", event);
            }
        }

        catch (NumberFormatException e) {
            Alert invalidInputError = new Alert(Alert.AlertType.ERROR);
            invalidInputError.setTitle("Invalid Input Error");
            invalidInputError.setContentText("Invalid data type entered. " + e.getMessage() + ". Please enter a valid value.");
            invalidInputError.showAndWait();
        }
    }

    /**
     * This method takes in user input and searches all parts for a match.
     * @param event The enter button is clicked on the search text-field.
     */
    @FXML
    void onActionSearchPart(ActionEvent event) {
        try {
            int thisPartSearch = Integer.parseInt(partSearch.getText());
            Part returnedPart = Inventory.lookupPart(thisPartSearch);

            if (returnedPart != null) {
                allPartsTable.getSelectionModel().select(returnedPart);
            }
        }

        catch (NumberFormatException ex) {
            String thisPartSearch = partSearch.getText();

            allPartsTable.setItems(Inventory.lookupPart(thisPartSearch));
        }
    }


    /**
     * This is the initialize method.
     * This method will populate the TableViews with data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        assocPartsTable.setItems(assocPartsList);

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartStockCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

}
