package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
* @Author: Earl Lawrence P. Bacsain
**/
public class StudentAttendanceViewController {

    public TextField txtfieldStudentID;
    public TextField txtfieldName;
    public ComboBox<String> comboCollege;
    public TextField txtfieldOthers;
    public Button btnSave;
    public Button btnClose;
    public Utils utils;
    public ToggleGroup radioPurpose;
    public RadioButton radiobtnStudy;
    public RadioButton radiobtnCollab;
    public RadioButton radiobtnBorrow;
    public RadioButton radiobtnMeeting;
    public RadioButton radiobtnResearch;
    public RadioButton radiobtnInternet;
    public RadioButton radiobtnGroupwork;
    public RadioButton radiobtnOthers;

    public void initialize(){
        utils = new Utils();
        populateComboBox();
    }
    public void populateComboBox(){
        comboCollege.getItems().clear();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT college_ID, college_name FROM College";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comboCollege.getItems().add(rs.getString("college_ID")+
                        "-"+rs.getString("college_name"));
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: "+ e.getMessage());
            alert.showAndWait();
        }
    }


    public void onClickSave(ActionEvent actionEvent) {
        String selectedCollege = comboCollege.getSelectionModel().getSelectedItem();
        String college_ID = selectedCollege.substring(0, selectedCollege.indexOf("-"));
        Student student = new Student(txtfieldStudentID.getText().trim(),
                txtfieldName.getText().trim(),
                ((RadioButton)radioPurpose.getSelectedToggle()).getText().trim(),
                college_ID);
        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "INSERT INTO student_attendance (stud_num, name, purpose, college_ID) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setString(1, student.getStud_num());
            ps.setString(2, student.getName());
            ps.setString(3, student.getPurpose());
            ps.setString(4, student.getCollege_ID());
            ps.executeUpdate();

            Utils.alertHandler(Alert.AlertType.CONFIRMATION, "Added Successfully");
        } catch (SQLException e) {
            Utils.alertHandler(Alert.AlertType.ERROR, "Error: "+ e);
        }
    }





    public void onClickClose(ActionEvent actionEvent) {
        utils.closeStage(btnClose);
    }
}
