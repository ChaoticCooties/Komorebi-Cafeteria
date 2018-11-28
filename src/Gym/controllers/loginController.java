package Gym;

import animatefx.animation.FadeIn;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable{

    @FXML
    private Label lbl_close;

    @FXML
    private Button btn_signIn;

    public void pressButtonAction(MouseEvent event) {
        if(event.getSource() == lbl_close){
            System.exit(0);
        } else if (event.getSource() == btn_signIn){
            loadStage("fxml/Home.fxml");
        }
    }

    //Offsets for the Menu items
    private double xOffset = 0;
    private double yOffset = 0;

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/gym/images/gymlogo.png"));
            stage.initStyle(StageStyle.TRANSPARENT);

            //Grab root here
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            //Move the application
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            new FadeIn(root).play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
