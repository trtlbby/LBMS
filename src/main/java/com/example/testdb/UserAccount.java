package com.example.testdb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserAccount {
    private String user_ID, status, username, password, lastname,
            firstname, middlename, gender, age;
    private LocalDateTime date_created;
    private LocalDate birthdate;

    public UserAccount(String user_ID, String username, String password,
                       String lastname, String firstname, String middlename, String age,
                       String gender, String status, LocalDateTime date_created)
    {
        this.user_ID = user_ID;
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.date_created = date_created;
    }
    //constructor without username and password
    public UserAccount(String user_ID, String lastname, String firstname,
                       String middlename, String age,
                       String gender, String status,
                       LocalDateTime date_created)
    {
        this.user_ID = user_ID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.date_created = date_created;
    }

    public UserAccount() {}

    public String getFullname() {
        return (lastname != null ? lastname : "") + ", " +
                (firstname != null ? firstname : "") + " " +
                (middlename != null ? middlename : "");
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getGender() {
        return gender;
    }

    public String getDatecreated() {
        return date_created != null ?
                date_created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
    }

    public String getAge() {
        return age;
    }

    public LocalDate getBirthdate() {
        return birthdate != null ? birthdate : null;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
