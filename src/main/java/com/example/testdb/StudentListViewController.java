package com.example.testdb;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @Author: Earl Lawrence P. Bacsain
 **/
public class StudentListViewController {
    public TableView<Student> tblStudentList;
    public TableColumn<Student, String> colID;
    public TableColumn<Student, String> colName;
    public TableColumn<Student, String> colPurpose;
    public TableColumn<Student, String> colCollege;
    public Button btnCompute;
    public Button btnAdd;
    public Button btnBack;
    public Utils utils;
    public TextField txtfieldSearch;
    public Button btnSearch;

    public void initialize() {
        //TO-DO
        utils = new Utils();
        populateStudentList();
    }

    public void populateStudentList() {
        tblStudentList.getColumns().clear();
        tblStudentList.getItems().clear();

        try {
            ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM student_attendance";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Student student = new Student(rs.getString("stud_num"),
                        rs.getString("name"),
                        rs.getString("purpose"),
                        rs.getString("college_ID"));
                studentObservableList.add(student);
            }

            colID.setCellValueFactory(new PropertyValueFactory<>("stud_num"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPurpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
            colCollege.setCellValueFactory(new PropertyValueFactory<>("college_ID"));

            tblStudentList.getColumns().addAll(colID, colName, colPurpose, colCollege);
            tblStudentList.setItems(studentObservableList);

        } catch (SQLException e) {
            Utils.alertHandler(Alert.AlertType.ERROR, "Error in" + e);
        }
    }


    public void onClickCompute(ActionEvent actionEvent) {
    }

    public void onClickAdd(ActionEvent actionEvent) throws IOException {
        utils.modalStage(actionEvent, "StudentAttendanceView", "Student Attendance");
        this.populateStudentList();
    }

    public void onClickBack(ActionEvent actionEvent) throws IOException {
        utils.currentStage(actionEvent, "DashboardView", "Dashboard");
    }

    public void onClickSearch(ActionEvent actionEvent) {
        tblStudentList.getColumns().clear();
        tblStudentList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM student_attendance" +
                    " WHERE CONCAT(stud_num, ' ', name, ' ', purpose, " +
                    "' ', college_ID) LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Student student = new Student(rs.getString("stud_num"),
                        rs.getString("name"),
                        rs.getString("purpose"),
                        rs.getString("college_ID"));
                studentObservableList.add(student);
            }

            colID.setCellValueFactory(new PropertyValueFactory<>("stud_num"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPurpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
            colCollege.setCellValueFactory(new PropertyValueFactory<>("college_ID"));

            tblStudentList.getColumns().addAll(colID, colName, colPurpose, colCollege);
            tblStudentList.setItems(studentObservableList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error is there\n" + e.getMessage());
            alert.showAndWait();
        }
    }
}
