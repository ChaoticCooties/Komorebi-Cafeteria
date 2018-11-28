package Gym;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

public class Main extends Application {

    //Initialize two doubles for x&y screen offsets
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));

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
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("Gym/images/gymlogo.png"));
        primaryStage.show();

        //animateFX animation (check external library for guides)
        new FadeIn(root).play();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
