package com.example.testdb;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnector {
    //declaration of variables for mysql connection
    private static final String URL = "jdbc:mysql://localhost:3306/LBMS_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "SQL#EarlWork092004";
    private static Connection CONN;

     protected static Connection connection(){
        try {
            CONN = DriverManager.getConnection(URL, USER, PASSWORD);
            Alert e = new Alert(Alert.AlertType.CONFIRMATION);
        } catch (Exception e) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Access Denied \n" + e.getMessage());
            System.out.println(e.getMessage());
            err.show();
        }
        return CONN;
    }
}
