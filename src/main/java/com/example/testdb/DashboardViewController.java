package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
/*
@Author: Earl Lawrence P. Bacsain
*/
public class DashboardViewController {

    public Button btnStudent;
    public Button btnUserManagement;
    public Button btnLibraryCard;
    public Button btnCollegeList;
    public Button btnLibraryStaff;
    public Tab tabStatistic;
    public Tab tabMenu;
    Utils utils;

    public void initialize(){
        utils = new Utils();
    }

    public void onClickStudent(ActionEvent actionEvent) throws IOException {
        utils.currentStage(actionEvent, "StudentListView", "Student List");
    }

    public void onClickUserManagement(ActionEvent actionEvent) throws IOException {
        utils.currentStage(actionEvent, "UserManagementView", null);
    }

    public void onClickLibraryCard(ActionEvent actionEvent) {
        //TODO
    }

    public void onClickCollegeList(ActionEvent actionEvent) throws IOException {
        utils.currentStage(actionEvent, "CollegeListView", null);
    }

    public void onClickLibraryStaff(ActionEvent actionEvent) throws IOException {
        utils.currentStage(actionEvent, "LibraryStaffListView", null);
    }
}
