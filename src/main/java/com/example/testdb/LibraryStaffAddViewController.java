package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
@Author: Earl Lawrence P. Bacsain
*/
public class LibraryStaffAddViewController {

    public TextField txtfieldStaffID;
    public TextField txtfieldFirstname;
    public TextField txtfieldMiddlename;
    public TextField txtfieldLastname;
    public ToggleGroup togglegroupRole;
    public TextField txtfieldContactNo;
    public TextField txtfieldEmail;
    public Button btnSave;
    public Button btnClear;
    public Button btnExit;
    public ComboBox<String> comboSection;
    public RadioButton radiobtnStudAssist;
    public RadioButton radiobtnLibrarian;

    public void initialize() {
        populateComboBox();
        generateId();
    }

    public void populateComboBox(){
        comboSection.getItems().clear();

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT section_ID, section_name FROM library_section";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comboSection.getItems().add(rs.getString("section_ID")+
                        "-"+rs.getString("section_name"));
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: "+ e.getMessage());
            alert.showAndWait();
        }
    }

    public void onClickSave(ActionEvent actionEvent) {
        if (txtfieldStaffID.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("ID must not be empty");
            e.show();
            return;
        }
        if (txtfieldFirstname.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Firstname must not be empty");
            e.show();
            return;
        }
        if (txtfieldMiddlename.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Middlename must not be empty");
            e.show();
            return;
        }
        if (txtfieldLastname.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Lastname must not be empty");
            e.show();
            return;
        }
        if(txtfieldEmail.getText().trim().isEmpty()) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Email must not be empty");
            e.show();
            return;
        }
        if(!txtfieldEmail.getText().trim().contains("@")
            || !txtfieldEmail.getText().trim().contains("unc.edu.ph")) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Invalid Email address! Check for: @" +
                    "unc.edu.ph");
            e.show();
            return;
        }
        if (togglegroupRole.getSelectedToggle() == null){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Staff needs a role. Choose one.");
            e.show();
            return;
        }
        if (txtfieldContactNo.getText().trim().isEmpty()) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Enter a contact number");
            e.show();
            return;
        }
        if (txtfieldContactNo.getText().length() > 11) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Contact number exceeded! " +
                    "11 digits number only");
            e.show();
            return;
        }
        if (comboSection.getSelectionModel().getSelectedItem() == null) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("No section selected");
            e.show();
            return;
        }

        String selectedSection = comboSection.getSelectionModel().getSelectedItem();
        String section_ID = selectedSection.substring(0, selectedSection.indexOf("-"));
        LibraryStaff libraryStaff = new LibraryStaff(txtfieldStaffID.getText().trim(),
                txtfieldLastname.getText().trim(),
                txtfieldFirstname.getText().trim(),
                txtfieldMiddlename.getText().trim(),
                txtfieldEmail.getText().trim(),
                ((RadioButton)togglegroupRole.getSelectedToggle()).getText().trim(),
                txtfieldContactNo.getText().trim(),
                section_ID);

        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "INSERT INTO library_staff " +
                    "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setString(1, libraryStaff.getStaff_ID());
            ps.setString(2, libraryStaff.getLastname());
            ps.setString(3, libraryStaff.getFirstname());
            ps.setString(4, libraryStaff.getMiddlename());
            ps.setString(5, libraryStaff.getEmail());
            ps.setString(6, libraryStaff.getRole());
            ps.setString(7, libraryStaff.getContactNo());
            ps.setString(8, libraryStaff.getSection_ID());
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Added Successfully");
            alert.showAndWait();

            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Doesn't work\n" + e.getMessage());
            alert.show();
        }
    }

    public void onClickClear(ActionEvent actionEvent) {
        txtfieldFirstname.clear();
        txtfieldMiddlename.clear();
        txtfieldLastname.clear();
        txtfieldEmail.clear();
        togglegroupRole.getSelectedToggle().setSelected(false);
        txtfieldContactNo.clear();
    }

    public void onClickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    private void generateId(){
        String newId = "";
        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT MAX(staff_ID) + 1 AS new_ID FROM library_staff";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                newId = rs.getString("new_ID");
            }

            txtfieldStaffID.setText(newId);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Doesn't work\n" + e.getMessage());
            alert.show();
        }
    }

}
