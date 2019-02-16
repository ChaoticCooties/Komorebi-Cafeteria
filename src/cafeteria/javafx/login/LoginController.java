package cafeteria.javafx.login;

import cafeteria.data.User;
import cafeteria.util.Preferences;
import cafeteria.util.util;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cafeteria.data.RecordMethods.login;
import static cafeteria.data.RecordMethods.search;
import static cafeteria.util.util.USER_DATA_LOC;
import static cafeteria.util.util.sendAlert;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();

        if (login(uname, pword)) {
            String role = search(uname, 0, 3, USER_DATA_LOC);
            String name = search(uname, 0, 1, USER_DATA_LOC);
            User customer = new User(uname, name, pword, role);
            closeStage();
            loadMain();
        } else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
            sendAlert("Wrong Username/Password", "Wrong credentials, please try again");
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleRegisterLinkAction(ActionEvent event) {
        loadRegister();

    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/cafeteria/javafx/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Komorebi Cafeteria");
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            Preferences preferences = Preferences.getPreferences();
            scene.getStylesheets().add(preferences.getTheme());
            stage.show();
            util.setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadRegister() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/cafeteria/javafx/register/register.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Register Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            Preferences preferences = Preferences.getPreferences();
            scene.getStylesheets().add(preferences.getTheme());
            stage.showAndWait();
            util.setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
