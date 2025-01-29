package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollegeAddViewController {

    @FXML
    public TextField txtFieldId;
    @FXML
    public TextField txtFieldName;
    @FXML
    public TextField txtFieldLocation;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnCancel;


    public void initialize(){
        generateId();
    }

    public void onClickAdd(ActionEvent actionEvent) {
        if(txtFieldId.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("ID must not be empty");
            e.show();
            return;
        }
        if(txtFieldName.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Name must not be empty");
            e.show();
            return;
        }
        if(txtFieldLocation.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Location must not be empty");
            e.show();
            return;
        }
        if(txtFieldId.getText().length() > 10){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character must not exceed the limit 10");
            e.show();
            return;
        }
        if(txtFieldName.getText().length() > 50){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character in Name must not exceed the limit 50");
            e.show();
            return;
        }
        if(txtFieldLocation.getText().length() > 15){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character in location must not exceed the limit 15");
            e.show();
            return;
        }

        College college = new College(txtFieldId.getText().trim().replace("C-", ""),
                                        txtFieldName.getText().trim(),
                                        txtFieldLocation.getText().trim());

        //code to add the data to the database=
        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "INSERT INTO College" +
                    " VALUES(?,?,?)";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setString(1, college.getCollege_ID());
            ps.setString(2, college.getCollege_name());
            ps.setString(3, college.getLocation());
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Added Successfully");
            alert.showAndWait();

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();

        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Doesn't work\n" + e.getMessage());
            alert.show();
        }

    }

    public void onClickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void generateId(){
        String newId = "";
        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT MAX(college_ID) + 1 AS new_ID FROM College";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                newId = "C-"+rs.getString("new_ID");
            }

            txtFieldId.setText(newId);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Doesn't work\n" + e.getMessage());
            alert.show();
        }
    }
}
