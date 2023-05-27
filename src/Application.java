import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *  This class launches the application .
 */
public class Application extends javafx.application.Application {

    /**
     * This is the start method. This will launch the first screen of the application.
     * @param stage The primary stage, the first scene of the application is shown here.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * FUTURE ENHANCEMENT: When in the ModifyProduct or ModifyPart screens, there will be an "On Mouse Hover" event
     * popup for the text-fields. This popup will show the user the previously saved value for that field.
     * This will be useful for reference when a new value has been added to the field but a user would like to
     * reference the previous value. With the current user interface, if a user wanted to reference previous values
     * while they are modifying a part or product the only way to do that would be to return to the main form scene
     * and click modify product again, losing any data that they had begun to enter in their fields.
     *
     * Javadoc files located in the Software1_Pa zip, in the Javadoc folder: Software1_PA.zip\Software1_PA\JavaDoc
     */
    public static void main(String[] args) {
        launch(args);
    }

}