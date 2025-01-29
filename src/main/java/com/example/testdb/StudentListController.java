package com.example.testdb;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentListController {

    public TextField txtfieldStudentID;
    public TextField txtfieldName;
    public ComboBox<String> comboCollege;
    public CheckBox checkStudy;
    public CheckBox checkMeeting;
    public CheckBox checkLeisure;
    public CheckBox checkResearch;
    public CheckBox checkNapping;
    public CheckBox checkInternet;
    public CheckBox checkBorrowBook;
    public CheckBox checkGroupwork;
    public TextField txtfieldOthers;

    public void initialize(){
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
    private void populateStudentList(){

    }
}
