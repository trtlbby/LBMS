module com.example.testdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.testdb to javafx.fxml;
    exports com.example.testdb;

    requires javafx.graphics;
    requires jbcrypt;
    requires java.desktop;
}