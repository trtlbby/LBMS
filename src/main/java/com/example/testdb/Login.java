package com.example.testdb;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private HashPassword hashPassword = new HashPassword();

    public boolean loginValidation(String username, String password) {
        try{
            //Establish connection first
            Connection CONN = JDBCConnector.connection();
            String sql = "SELECT password" +
                    " FROM user_account" +
                    " WHERE username = ?";
            PreparedStatement ps = CONN.prepareStatement(sql);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String hashedPassword = rs.getString("password");
                return hashPassword.verifyPassword(password, hashedPassword);
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: " + e.getMessage());
            alert.show();
        }
        return false;
    }
}
