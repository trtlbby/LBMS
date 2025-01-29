package com.example.testdb;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDateTime;
/*
@Author: Earl Lawrence P. Bacsain
*/
public class UserManagementViewController {

    public Button btnDashboard;
    public Button btnLogout;
    public Button btnAddUser;
    public Button btnEdit;
    public Button btnDelete;
    public TableView<UserAccount> tblUserAccountList;
    public TableColumn<UserAccount, String> colUserID;
    public TableColumn<UserAccount, String> colFullname;
    public TableColumn<UserAccount, String> colAge;
    public TableColumn<UserAccount, String> colGender;
    public TableColumn<UserAccount, String> colStatus;
    public TableColumn<UserAccount, String> colDateCreated;
    public Button btnSearch;
    public TextField txtfieldSearch;


    public void initialize() {populateUserAccountList();}

    public void populateUserAccountList(){
        tblUserAccountList.getColumns().clear();
        tblUserAccountList.getItems().clear();

        try {
            ObservableList<UserAccount> observableUserAccountList = FXCollections.observableArrayList();
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM User_Account";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                UserAccount userAccount = new UserAccount(rs.getString("user_ID"),
                                                            rs.getString("lastname"),
                                                            rs.getString("firstname"),
                                                            rs.getString("middlename"),
                                                            rs.getString("age"),
                                                            rs.getString("gender"),
                                                            rs.getString("status"),
                                                            rs.getTimestamp("date_created").toLocalDateTime());
                observableUserAccountList.add(userAccount);
            }

            colUserID.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
            colFullname.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullname()));
            colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colDateCreated.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().getDatecreated()));

            tblUserAccountList.getColumns().addAll(colUserID, colFullname, colAge,
                                                    colGender, colStatus, colDateCreated);
            tblUserAccountList.setItems(observableUserAccountList);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error:\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onClickAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserManagementAddView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        stage.setTitle("Add User");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        stage.showAndWait();

        this.populateUserAccountList();
    }

    public void onClickEdit(ActionEvent actionEvent) throws IOException {
        if(tblUserAccountList.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must select a user first");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserManagementEditView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        stage.setTitle("Edit User Account Details");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());

        UserManagementEditViewController controller = loader.getController();
        UserAccount selectedAccount = tblUserAccountList.getSelectionModel().getSelectedItem();
        controller.initData(selectedAccount);

        stage.showAndWait();

        this.populateUserAccountList();
    }

    public void onClickDelete(ActionEvent actionEvent) {
        System.out.println("NO USAGE YET");
    }

    public void onClickDashboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.centerOnScreen();
        currentStage.show();
    }

    public void onClickLogout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Log out Successful");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.centerOnScreen();
        currentStage.show();
    }

    public void onClickSearch(ActionEvent actionEvent) {
        tblUserAccountList.getColumns().clear();
        tblUserAccountList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<UserAccount> observableUserAccountList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM user_account" +
                    " WHERE CONCAT(lastname, ' ', firstname, ' ', middlename, ' ', username) LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                UserAccount userAccount = new UserAccount(rs.getString("user_ID"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("age"),
                        rs.getString("gender"),
                        rs.getString("status"),
                        rs.getTimestamp("date_created").toLocalDateTime());
                observableUserAccountList.add(userAccount);
            }

            colUserID.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
            colFullname.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullname()));
            colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colDateCreated.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().getDatecreated()));

            tblUserAccountList.getColumns().addAll(colUserID, colFullname, colAge,
                    colGender, colStatus, colDateCreated);
            tblUserAccountList.setItems(observableUserAccountList);

        } catch(SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error is there\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        tblUserAccountList.getColumns().clear();
        tblUserAccountList.getItems().clear();

        String searchKey = txtfieldSearch.getText().trim();
        ObservableList<UserAccount> observableUserAccountList = FXCollections.observableArrayList();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT * FROM user_account" +
                    " WHERE CONCAT(lastname, ' ', firstname, ' ', middlename, ' ', username) LIKE '%"+searchKey+"%'";
            PreparedStatement preparedStatement = CONN.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                UserAccount userAccount = new UserAccount(rs.getString("user_ID"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("age"),
                        rs.getString("gender"),
                        rs.getString("status"),
                        rs.getTimestamp("date_created").toLocalDateTime());
                observableUserAccountList.add(userAccount);
            }

            colUserID.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
            colFullname.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullname()));
            colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colDateCreated.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().getDatecreated()));

            tblUserAccountList.getColumns().addAll(colUserID, colFullname, colAge,
                    colGender, colStatus, colDateCreated);
            tblUserAccountList.setItems(observableUserAccountList);

        } catch(SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error is there\n" + e.getMessage());
            alert.showAndWait();
        }
    }

}

