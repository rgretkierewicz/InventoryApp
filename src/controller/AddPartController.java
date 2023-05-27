package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class takes in entered data and creates a new part. */
public class AddPartController implements Initializable {
    @FXML
    private ToggleGroup addPartTG;
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private TextField changingFieldTxt;
    @FXML
    private RadioButton outsourcedRBtn;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField stockTxt;
    @FXML
    private Label changingLabel;

    /**
     * This method changes the last text-field label to Company Name.
     * @param event The Outsourced radio button selected.
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        changingLabel.setText("Company Name");
    }

    /**
     * This method changes the last text-field label to Machine ID.
     * @param event The InHouse radio button is selected.
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        changingLabel.setText("Machine ID");
    }

    /**
     * This method returns the user to the MainForm scene.
     * @param event The cancel button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        SceneSwitcher MainFormScene = new SceneSwitcher();
        MainFormScene.buttonSwitchScene("/view/MainForm.fxml", event);
    }

    /**
     * This method creates a new part from the entered data. The application then returns to the MainForm scene.
     * @param event The save button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Inventory.createPartID();
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
                if (inHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(changingFieldTxt.getText());

                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                }

                if (outsourcedRBtn.isSelected()) {
                    String companyName = changingFieldTxt.getText();

                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }

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
     * This is the initialize method.
     * This method will select the InHouse button.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRBtn.setSelected(true);
    }

}





