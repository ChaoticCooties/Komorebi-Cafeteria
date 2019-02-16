package cafeteria.javafx.main;

import cafeteria.util.Preferences;
import cafeteria.util.util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/cafeteria/javafx/login/login.fxml"));

        Scene scene = new Scene(root);
        Preferences preferences = Preferences.getPreferences();
        scene.getStylesheets().add(preferences.getTheme());
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Komorebi Cafeteria");

        util.setStageIcon(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
