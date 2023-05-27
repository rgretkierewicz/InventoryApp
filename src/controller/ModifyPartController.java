package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;


/** This class takes in entered data and modifies an existing part. */
public class ModifyPartController {
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
    @FXML
    private TextField changingTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private ToggleGroup modifyPartTG;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField stockTxt;
    @FXML
    private Label changingLabel;
    private Part thisPart = null;

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
     * This method updates the part with the entered data. The application then returns to the MainForm scene.
     * @param event The save button is clicked.
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(partIdTxt.getText());
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(stockTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

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
                    int machineId = Integer.parseInt(changingTxt.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);

                    int index = -1;

                    for (Part part : Inventory.getAllParts()) {
                        index++;

                        if (part.getId() == id) {
                            Inventory.updatePart(index, newPart);
                        }
                    }
                }

                if (outsourcedRBtn.isSelected()) {
                    String companyName = changingTxt.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);

                    int index = -1;

                    for (Part part : Inventory.getAllParts()) {
                        index++;

                        if (part.getId() == id) {
                            Inventory.updatePart(index, newPart);
                        }
                    }
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
     * This method changes the last text-field label to Company Name. If the part being
     *  modified is an Outsourced part, the text-field is populated with the saved Company Name.
     * @param event The Outsourced radio button selected.
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        changingLabel.setText("Company Name");

        if (!(thisPart instanceof Outsourced)) {
            changingTxt.clear();
        }

        else {
            changingTxt.setText(((Outsourced) thisPart).getCompanyName());
        }
    }

    /**
     * This method changes the last text-field label to Machine ID. If the part being
     *  modified is an InHouse part, the text-field is populated with the saved Machine ID.
     * @param event The InHouse radio button is selected.
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        changingLabel.setText("Machine ID");

        if (!(thisPart instanceof InHouse)) {
            changingTxt.clear();
        }

        else {
            changingTxt.setText(String.valueOf(((InHouse) thisPart).getMachineId()));
        }
    }

    /**
     * This method receives the part selected in the MainForm scene and populates the ModifyPart
     *  scene with that part's current data.
     * @param part The part to be modified.
     */
    public void sendPart(Part part) {
        thisPart = part;

        partIdTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        stockTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));

        if (thisPart instanceof InHouse) {
            changingTxt.setText(String.valueOf((((InHouse) part).getMachineId())));
            inHouseRBtn.setSelected(true);
        }

        if (thisPart instanceof Outsourced) {
            changingTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            outsourcedRBtn.setSelected(true);
        }
    }

}
