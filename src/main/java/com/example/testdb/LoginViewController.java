package com.example.testdb;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginViewController {
    private Login login;

    public TextField txtfieldUsername;
    public TextField txtfieldPassword;
    public Button btnLogin;
    Utils utils;

    public void initialize() {
        login = new Login();
        utils = new Utils();
    }

    public void onClickLogin(ActionEvent actionEvent) throws IOException {
        String username = txtfieldUsername.getText().trim();
        String password = txtfieldPassword.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            Utils.alertHandler(Alert.AlertType.WARNING,
                    "Username and Password are Required");
            return;
        }

        if(login.loginValidation(username, password)) {
            Utils.alertHandler(Alert.AlertType.INFORMATION,
                    "Login Successful");

            utils.currentStage(actionEvent, "DashboardView", "Dashboard");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Login Failed. Check your username and password");
            alert.show();
        }
    }
}
