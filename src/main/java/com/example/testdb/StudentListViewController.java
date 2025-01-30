package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

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

    public void initialize() {
        //TO-DO
        utils = new Utils();
    }

    public void populateStudentList() {
        tblStudentList.getColumns().clear();
        tblStudentList.getItems().clear();

    }


    public void onClickCompute(ActionEvent actionEvent) {
    }

    public void onClickAdd(ActionEvent actionEvent) throws IOException {
        utils.modalStage(actionEvent, "StudentAttendanceView", "Student Attendance");
    }

    public void onClickBack(ActionEvent actionEvent) {
        utils.closeStage(btnBack);
    }
}
