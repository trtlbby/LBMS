package com.example.testdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollegeListController {
    @FXML
    public TextField txtfieldSearch;
    @FXML
    public Button btnSearch;
    @FXML
    public TableView<College> tblCollegeList;
    @FXML
    public TableColumn<College, String> colCollegeId;
    @FXML
    public TableColumn<College, String> colLocation;
    @FXML
    public TableColumn<College, String> colCollegeName;
    @FXML
    public Button btnNew;
    @FXML
    public Button btnClose;
    @FXML
    public Button btnEdit;
    Utils utils;

    public void initialize(){
        populateCollegeList();
        utils = new Utils();
    }

    private void populateCollegeList(){
        tblCollegeList.getColumns().clear();
        tblCollegeList.getItems().clear();

        try {
            ObservableList<College> observableCollegeList = FXCollections.observableArrayList();
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM College";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                College college = new College("C-" + rs.getString("college_ID"),
                                                rs.getString("college_name"),
                                                rs.getString("location"));
                observableCollegeList.add(college);
            }

            colCollegeId.setCellValueFactory(new PropertyValueFactory<>("college_ID"));
            colCollegeName.setCellValueFactory(new PropertyValueFactory<>("college_name"));
            colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

            tblCollegeList.getColumns().addAll(colCollegeId, colCollegeName, colLocation);
            tblCollegeList.setItems(observableCollegeList);

        } catch (SQLException e) {
            Utils.alertHandler(Alert.AlertType.ERROR, e.getMessage());
        }
    }


    public void onClickNew(ActionEvent actionEvent) throws IOException {
        utils.modalStage(actionEvent, "CollegeAddView", "Add College");

        this.populateCollegeList();
    }

    //Close method for closing the form
    //call a stage then first we must know where the close button is
    //using the getScene getWindow. When we have its location, we store it to the object stage
    // we then use the stage object for .close()
    public void onClickClose(ActionEvent actionEvent) throws IOException {
        utils.currentStage(actionEvent, "DashboardView", "Dashboard");
    }
    public void onClickEdit(ActionEvent actionEvent) throws IOException {
        if(tblCollegeList.getSelectionModel().getSelectedItem() == null){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "You must select a College first");
            return;
        }

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CollegeEditView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        stage.setTitle("Edit College");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        CollegeEditViewController controller = loader.getController();
        College selectedCollege = tblCollegeList.getSelectionModel().getSelectedItem();
        controller.initData(selectedCollege);

        stage.showAndWait();
        this.populateCollegeList();
    }

    public void onKeyPressed(KeyEvent actionEvent) {
        tblCollegeList.getColumns().clear();
        tblCollegeList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<College> observableCollegeList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM College" +
                    " WHERE college_name LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                College college = new College("C-" + rs.getString("college_ID"),
                        rs.getString("college_name"),
                        rs.getString("location"));
                observableCollegeList.add(college);
            }

            colCollegeId.setCellValueFactory(new PropertyValueFactory<>("college_ID"));
            colCollegeName.setCellValueFactory(new PropertyValueFactory<>("college_name"));
            colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

            tblCollegeList.getColumns().addAll(colCollegeId, colCollegeName, colLocation);
            tblCollegeList.setItems(observableCollegeList);

        } catch (SQLException e) {
            Utils.alertHandler(Alert.AlertType.ERROR, e.getMessage());
        }
    }
    public void onClickSearch(ActionEvent actionEvent) {

        tblCollegeList.getColumns().clear();
        tblCollegeList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<College> observableCollegeList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM College" +
                    " WHERE college_name LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                College college = new College("C-" + rs.getString("college_ID"),
                        rs.getString("college_name"),
                        rs.getString("location"));
                observableCollegeList.add(college);
            }

            colCollegeId.setCellValueFactory(new PropertyValueFactory<>("college_ID"));
            colCollegeName.setCellValueFactory(new PropertyValueFactory<>("college_name"));
            colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

            tblCollegeList.getColumns().addAll(colCollegeId, colCollegeName, colLocation);
            tblCollegeList.setItems(observableCollegeList);

        } catch (SQLException e) {
            Utils.alertHandler(Alert.AlertType.ERROR, e.getMessage());
        }
    }
}
