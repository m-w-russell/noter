package mrsoftware.noter.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class MainWindow extends Application {
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
    

        Parent root = baseLoader.load();

        MainController baseController = baseLoader.getController();
        baseController.init(primaryStage);



        primaryStage.setTitle("NOTER");
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/icon_old.png")));
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F5) {
                baseController.updateOutput(new ActionEvent());
            }
            if (e.getCode() == KeyCode.F1) {
                baseController.saveDocument(new ActionEvent());
            }
        });
    }

    public static void launchWrapper(String [] args) {
        launch(args);
    }


}