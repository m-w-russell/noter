package mrsoftware.noter.gui;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.input.KeyCode;
import mrsoftware.noter.services.InputParser;
import mrsoftware.noter.services.FileManager;
import java.time.LocalTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.io.IOException;

public class MainController {

    @FXML
    private TextField fileName;

    @FXML
    private TextArea inputArea;

    @FXML
    private Button loadButton;

    @FXML
    private Button renderButton;

    @FXML
    private Button saveButton;

    @FXML
    private WebView webView;



    private InputParser inputParser;

    private FileManager fileManager;

    private Stage thisStage;

    private String titleString;


    public void init(Stage stage) {
        thisStage = stage;
        titleString = thisStage.getTitle();
        inputParser = new InputParser();
        fileManager = new FileManager();

        webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/style.css").toString());
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (ObservableValue<? extends Worker.State> observable,
                 Worker.State oldValue,
                 Worker.State newValue) -> {
                    if( newValue != Worker.State.SUCCEEDED ) {
                        return;
                    }

                    webView.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
                } );



    }

    @FXML
    void updateOutput(ActionEvent event) {
        System.out.println("UPDATING");
        String content = inputArea.getText();
        webView.getEngine().loadContent(inputParser.parseText(content));

    }

    @FXML
    void saveDocument(ActionEvent event) {
        fileManager.makeFile(fileName.getText());
        fileManager.saveData(inputArea.getText(), fileName.getText());
        thisStage.setTitle(titleString + " | Saved: " + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute());
    }

    @FXML
    void loadDocument(ActionEvent event) {
        try {
            inputArea.setText(fileManager.loadData(fileName.getText()));
        } catch (IOException e) {
            System.out.println(e);
        }
        titleString = "NOTER - " + fileName.getText();
        thisStage.setTitle(titleString);
        updateOutput(new ActionEvent());
    }

}
