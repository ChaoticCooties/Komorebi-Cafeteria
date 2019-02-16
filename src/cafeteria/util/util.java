package cafeteria.util;

import cafeteria.javafx.main.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class util {

    //Text File Locations
    public static final String USER_DATA_LOC = "./src/resources/data/user.txt";
    public static final String MENU_DATA_LOC = "./src/resources/data/menu.txt";
    public static final String ORDER_DATA_LOC = "./src/resources/data/order.txt";

    public static final String ICON_LOCATION = "/resources/icon.png";
    public static final String currentTimeString = LocalDate.now().toString();

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_LOCATION));
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }

            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            Preferences preferences = Preferences.getPreferences();
            scene.getStylesheets().add(preferences.getTheme());
            stage.showAndWait();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static void sendAlert(String title, String message) {
        //sends JavaFX alert most likely for debugging
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
