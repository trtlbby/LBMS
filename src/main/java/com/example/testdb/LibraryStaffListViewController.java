package com.example.testdb;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
/**
 * @Author: Earl Lawrence P. Bacsain
 **/
public class LibraryStaffListViewController {

    public TextField txtfieldSearch;
    public Button btnSearch;
    public TableView<LibraryStaff> tblLibraryStaffList;
    public TableColumn<LibraryStaff, String> colStaffID;
    public TableColumn<LibraryStaff, String> colStaffName;
    public TableColumn<LibraryStaff, String> colEmail;
    public TableColumn<LibraryStaff, String> colRole;
    public TableColumn<LibraryStaff, String> colContactno;
    public TableColumn<LibraryStaff, String> colSection;
    public Button btnNew;
    public Button btnEdit;
    public Button btnClose;

    public void initialize() { populateLibraryStaffList();}

    private void populateLibraryStaffList() {
        tblLibraryStaffList.getColumns().clear();
        tblLibraryStaffList.getItems().clear();

        try {
            ObservableList<LibraryStaff> staffObservableList = FXCollections.observableArrayList();
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM library_staff";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                LibraryStaff libraryStaff = new LibraryStaff(rs.getString("staff_ID"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("contactNo"),
                        rs.getString("section_ID"));
                staffObservableList.add(libraryStaff);
            }

            colStaffID.setCellValueFactory(new PropertyValueFactory<>("staff_ID"));
            colStaffName.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullname()));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colContactno.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
            colSection.setCellValueFactory(new PropertyValueFactory<>("section_ID"));

            tblLibraryStaffList.getColumns().addAll(colStaffID, colStaffName, colEmail,
                    colRole, colContactno, colSection);
            tblLibraryStaffList.setItems(staffObservableList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error is there\n" + e.getMessage());
            alert.showAndWait();
        }


    }

    public void onClickNew(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryStaffAddView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        stage.setTitle("Add Staff");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        stage.showAndWait();

        this.populateLibraryStaffList();
    }

    public void onClickEdit(ActionEvent actionEvent) throws IOException {
        if(tblLibraryStaffList.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a user first");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryStaffEditView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        stage.setTitle("Edit User Account Details");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        LibraryStaffEditViewController controller = loader.getController();
        LibraryStaff selectedStaff = tblLibraryStaffList.getSelectionModel().getSelectedItem();
        controller.initData(selectedStaff);

        stage.showAndWait();

        this.populateLibraryStaffList();
    }

    public void onClickClose(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.centerOnScreen();
        currentStage.show();
    }
    public void onKeyPressed(KeyEvent keyEvent) {
        tblLibraryStaffList.getColumns().clear();
        tblLibraryStaffList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<LibraryStaff> staffObservableList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM library_staff" +
                    " WHERE CONCAT(lastname, ' ', firstname, ' ', middlename, " +
                    "' ', role, ' ', staff_ID) LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                LibraryStaff libraryStaff = new LibraryStaff(rs.getString("staff_ID"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("contactNo"),
                        rs.getString("section_ID"));
                staffObservableList.add(libraryStaff);
            }

            colStaffID.setCellValueFactory(new PropertyValueFactory<>("staff_ID"));
            colStaffName.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullname()));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colContactno.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
            colSection.setCellValueFactory(new PropertyValueFactory<>("section_ID"));

            tblLibraryStaffList.getColumns().addAll(colStaffID, colStaffName, colEmail,
                    colRole, colContactno, colSection);
            tblLibraryStaffList.setItems(staffObservableList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error is there\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onClickSearch(ActionEvent actionEvent) {
        tblLibraryStaffList.getColumns().clear();
        tblLibraryStaffList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<LibraryStaff> staffObservableList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM library_staff" +
                    " WHERE CONCAT(lastname, ' ', firstname, ' ', middlename, " +
                    "' ', role, ' ', staff_ID) LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                LibraryStaff libraryStaff = new LibraryStaff(rs.getString("staff_ID"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("contactNo"),
                        rs.getString("section_ID"));
                staffObservableList.add(libraryStaff);
            }

            colStaffID.setCellValueFactory(new PropertyValueFactory<>("staff_ID"));
            colStaffName.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullname()));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colContactno.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
            colSection.setCellValueFactory(new PropertyValueFactory<>("section_ID"));

            tblLibraryStaffList.getColumns().addAll(colStaffID, colStaffName, colEmail,
                    colRole, colContactno, colSection);
            tblLibraryStaffList.setItems(staffObservableList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error is there\n" + e.getMessage());
            alert.showAndWait();
        }
    }
}
