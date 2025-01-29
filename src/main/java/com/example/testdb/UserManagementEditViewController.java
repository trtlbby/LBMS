package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
/*
@Author: Earl Lawrence P. Bacsain
*/
public class UserManagementEditViewController {
    HashPassword hashPassword;

    public TextField txtfieldUserID;
    public TextField txtfieldFirstname;
    public TextField txtfieldMiddlename;
    public TextField txtfieldLastname;
    public RadioButton radiobtnMale;
    public ToggleGroup togglegroupGender;
    public RadioButton radiobtnFemale;
    public CheckBox checkboxIsActive;
    public DatePicker datepickerBirthdate;
    public TextField txtfieldUsername;
    public PasswordField txtfieldPassword;
    public PasswordField txtfieldConfirmPassword;
    public Button btnSave;
    public Button btnClear;
    public Button btnExit;

    public void initialize() {
        hashPassword = new HashPassword();

    }

    void initData(UserAccount userAccount) {
        txtfieldUserID.setText(userAccount.getUser_ID());
        txtfieldFirstname.setText(userAccount.getFirstname());
        txtfieldMiddlename.setText(userAccount.getMiddlename());
        txtfieldLastname.setText(userAccount.getLastname());
        txtfieldUsername.setText(userAccount.getUsername());
        // Handle birthdate
        if (userAccount.getBirthdate() != null) {
            datepickerBirthdate.setValue(userAccount.getBirthdate());
        } else {
            datepickerBirthdate.setValue(null); // Clear the DatePicker if no birthdate is available
        }
        //populate the gender as well
        if (userAccount.getGender() != null) {
            if (userAccount.getGender().equalsIgnoreCase("Male")) {
                radiobtnMale.setSelected(true);
            } else if (userAccount.getGender().equalsIgnoreCase("Female")) {
                radiobtnFemale.setSelected(true);
            }
        }
    }

    public void onClickSave(ActionEvent actionEvent) {
        if (txtfieldUserID.getText().trim().isEmpty()){
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
        if(txtfieldFirstname.getText().length() > 30){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character in First name must not exceed the limit 30");
            e.show();
            return;
        }
        if (txtfieldMiddlename.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Middlename must not be empty");
            e.show();
            return;
        }
        if(txtfieldMiddlename.getText().length() > 30){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character in Middle name must not exceed the limit 30");
            e.show();
            return;
        }
        if (txtfieldLastname.getText().trim().isEmpty()){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Lastname must not be empty");
            e.show();
            return;
        }
        if(txtfieldLastname.getText().length() > 30){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character in Last name must not exceed the limit 30");
            e.show();
            return;
        }
        if (datepickerBirthdate.getValue() == null){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Birth date must be picked");
            e.show();
            return;
        }
        if (togglegroupGender.getSelectedToggle() == null){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("User must select a gender");
            e.show();
            return;
        }
        if(txtfieldUsername.getText().trim().isEmpty()) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Username must be filled");
            e.show();
            return;
        }
        if(txtfieldUsername.getText().length() > 20){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Character in Username must not exceed the limit 20");
            e.show();
            return;
        }
        if(txtfieldPassword.getText().trim().isEmpty()) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Password must be filled");
            e.show();
            return;
        }
        if(txtfieldPassword.getText().length() > 12 || txtfieldPassword.getText().length() < 8){
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Maximum password characters is 12 and minimum of 8");
            e.show();
            return;
        }
        if(!txtfieldConfirmPassword.getText().trim().equals(txtfieldPassword.getText().trim())) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Password doesn't match");
            e.show();
            return;
        }

        //calculate age first
        String age = String.valueOf(Period.between(datepickerBirthdate.getValue(),
                LocalDate.now()).getYears());
        //date creation automated timestamp
        LocalDateTime datecreated = LocalDateTime.now();
        //hash password here
        String hashpw = hashPassword.hashPassword(txtfieldPassword.getText().trim());

        UserAccount userAccount = new UserAccount(txtfieldUserID.getText().trim(),
                txtfieldUsername.getText().trim(),
                hashpw,
                txtfieldLastname.getText().trim(),
                txtfieldFirstname.getText().trim(),
                txtfieldMiddlename.getText().trim(),
                age,
                ((RadioButton)togglegroupGender.getSelectedToggle()).getText().trim(),
                checkboxIsActive.isSelected() ? "1" : "0",
                datecreated
        );
        //this will get the birthdate separately.
        userAccount.setBirthdate(datepickerBirthdate.getValue());
        //code to add the data to the database
        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "UPDATE user_account" +
                    " SET user_ID = ?, username = ?, password = ?, lastname = ?, firstname = ?," +
                    " middlename = ?, birthdate = ?, age = ?, gender = ?, status = ?, date_created = ?" +
                    " WHERE user_ID = ?";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setString(1, userAccount.getUser_ID());
            ps.setString(2, userAccount.getUsername());
            ps.setString(3, userAccount.getPassword());
            ps.setString(4, userAccount.getLastname());
            ps.setString(5, userAccount.getFirstname());
            ps.setString(6, userAccount.getMiddlename());
            ps.setString(7, String.valueOf(userAccount.getBirthdate()));
            ps.setString(8, userAccount.getAge());
            ps.setString(9, userAccount.getGender());
            ps.setString(10, userAccount.getStatus());
            ps.setString(11, String.valueOf(userAccount.getDatecreated()));
            ps.setString(12, userAccount.getUser_ID());
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Updated Successfully");
            alert.showAndWait();

            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error:\n" + e.getMessage());
            alert.show();
        }
    }

    public void onClickClear(ActionEvent actionEvent) {
        txtfieldFirstname.clear();
        txtfieldMiddlename.clear();
        txtfieldLastname.clear();
        txtfieldUsername.clear();
        txtfieldPassword.clear();
        txtfieldConfirmPassword.clear();
        datepickerBirthdate.setValue(null);
        togglegroupGender.getSelectedToggle().setSelected(false);
    }

    public void onClickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
