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
public class LibraryStaffEditViewController {

    public TextField txtfieldStaffID;
    public TextField txtfieldFirstname;
    public TextField txtfieldMiddlename;
    public TextField txtfieldLastname;
    public TextField txtfieldEmail;
    public ToggleGroup togglegroupRole;
    public TextField txtfieldContactNo;
    public ComboBox<String> comboSection;
    public Button btnUpdate;
    public Button btnClear;
    public Button btnExit;
    public RadioButton radiobtnStudAssist;
    public RadioButton radiobtnLibrarian;

    public void initialize() {
        populateComboBox();
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

    void initData(LibraryStaff libraryStaff){
        txtfieldStaffID.setText(libraryStaff.getStaff_ID());
        txtfieldLastname.setText(libraryStaff.getLastname());
        txtfieldFirstname.setText(libraryStaff.getFirstname());
        txtfieldMiddlename.setText(libraryStaff.getMiddlename());
        txtfieldEmail.setText(libraryStaff.getEmail());
        if (libraryStaff.getRole() != null) {
            if (libraryStaff.getRole().equalsIgnoreCase("Librarian")) {
                radiobtnLibrarian.setSelected(true);
            } else if (libraryStaff.getRole().equalsIgnoreCase("Student Assistant")) {
                radiobtnStudAssist.setSelected(true);
            }
        }
        txtfieldContactNo.setText(libraryStaff.getContactNo());
        comboSection.setValue(libraryStaff.getSection_ID());
    }

    public void onClickUpdate(ActionEvent actionEvent) {
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
            String sql = "UPDATE library_staff" +
                    " SET staff_ID = ?, lastname = ?, firstname = ?," +
                    " middlename = ?, email = ?, role = ?, contactNo = ?," +
                    " section_ID = ? WHERE staff_ID = ?";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setString(1, libraryStaff.getStaff_ID());
            ps.setString(2, libraryStaff.getLastname());
            ps.setString(3, libraryStaff.getFirstname());
            ps.setString(4, libraryStaff.getMiddlename());
            ps.setString(5, libraryStaff.getEmail());
            ps.setString(6, libraryStaff.getRole());
            ps.setString(7, libraryStaff.getContactNo());
            ps.setString(8, libraryStaff.getSection_ID());
            ps.setString(9, libraryStaff.getStaff_ID());
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Updated Successfully");
            alert.showAndWait();

            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Editing does not work\n" + e.getMessage());
            alert.show();
        }
    }

    public void onClickClear(ActionEvent actionEvent) {
    }

    public void onClickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
