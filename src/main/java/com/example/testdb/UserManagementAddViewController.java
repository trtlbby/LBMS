package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

//TODO: Data Truncation in Date or age format,
// why is it wrong what to change?
// It said that format is wrong
//DONE!

/*
@Author: Earl Lawrence P. Bacsain
*/

public class UserManagementAddViewController {
    private HashPassword hashPassword; //for hash password
    //in here I accidentally use txtfield suffix rather than txtField,
    // take note of this
    public TextField txtfieldUserID;
    public TextField txtfieldFirstname;
    public TextField txtfieldMiddlename;
    public TextField txtfieldLastname;
    public RadioButton radiobtnMale;
    public RadioButton radiobtnFemale;
    public CheckBox checkboxIsActive;
    public DatePicker datepickerBirthdate;
    public TextField textfieldUsername;
    public PasswordField txtfieldPassword;
    public PasswordField txtfieldConfirmPassword;
    public Button btnSave;
    public Button btnClear;
    public Button btnExit;
    public ToggleGroup togglegroupGender;

    public void initialize() {
        hashPassword = new HashPassword();
        generateId();
    }

    //TODO: Add checker for the character length. Must not exceed the Varchar(length)
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
        if(textfieldUsername.getText().trim().isEmpty()) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setContentText("Username must be filled");
            e.show();
            return;
        }
        if(textfieldUsername.getText().length() > 20){
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
                                                    textfieldUsername.getText().trim(),
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
            String sql = "INSERT INTO User_Account" +
                    " Values(?,?,?,?,?,?,?,?,?,?,?)";
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
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Added Successfully");
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
        textfieldUsername.clear();
        txtfieldPassword.clear();
        txtfieldConfirmPassword.clear();
        datepickerBirthdate.setValue(null);
        togglegroupGender.getSelectedToggle().setSelected(false);
    }

    public void onClickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
    private void generateId() {
        String newId = "";
        try {
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT MAX(user_ID) + 1 AS new_ID FROM User_Account";
            PreparedStatement ps = CONN.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                newId = rs.getString("new_ID");
            }

            txtfieldUserID.setText(newId);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Doesn't work\n" + e.getMessage());
            alert.show();
        }
    }
}
