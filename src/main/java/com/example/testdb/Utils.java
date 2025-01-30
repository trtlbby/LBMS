package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
/*
@Author: Earl Lawrence P. Bacsain
*/
public class Utils {

    public static void alertHandler(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    public void currentStage(ActionEvent actionEvent, String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile+".fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.centerOnScreen();
        currentStage.setTitle(title);
        currentStage.show();
    }

    public void modalStage(ActionEvent actionEvent, String fxmlFile, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile+".fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        stage.showAndWait();
    }

    public void closeStage(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
