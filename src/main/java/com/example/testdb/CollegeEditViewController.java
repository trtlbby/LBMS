package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CollegeEditViewController {

    public TextField txtFieldId;
    public TextField txtFieldName;
    public TextField txtFieldLocation;
    public Button btnUpdate;
    public Button btnCancel;

    public void initialize(){
        //TODO
    }

    void initData(College college){
        txtFieldId.setText(college.getCollege_ID());
        txtFieldName.setText(college.getCollege_name());
        txtFieldLocation.setText(college.getLocation());
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        if(txtFieldId.getText().trim().isEmpty()){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Id must not be empty");
            return;
        }
        if(txtFieldName.getText().trim().isEmpty()){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Name must not be empty");
            return;
        }
        if(txtFieldLocation.getText().trim().isEmpty()){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Location must not be empty");
            return;
        }
        if(txtFieldId.getText().length() > 10){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Character must not exceed the limit 10");
            return;
        }
        if(txtFieldName.getText().length() > 50){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Character must not exceed the limit 50");
            return;
        }
        if(txtFieldLocation.getText().length() > 15){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Character must not exceed the limit 15");
            return;
        }

        College college = new College(txtFieldId.getText().trim().replace("C-", ""),
                txtFieldName.getText().trim(),
                txtFieldLocation.getText().trim());

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "UPDATE college SET college_ID = ?, college_name = ?, location = ?" +
                    " WHERE college_ID = ?";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setString(1, college.getCollege_ID());
            ps.setString(2, college.getCollege_name());
            ps.setString(3, college.getLocation());
            ps.setString(4, college.getCollege_ID());
            ps.executeUpdate();

            Utils.alertHandler(Alert.AlertType.CONFIRMATION, "Updated successfully");

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();

        } catch (SQLException e){
            Utils.alertHandler(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void onClickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
