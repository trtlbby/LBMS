package com.example.testdb;


//this is the database model - LBMS_DB it contains the replica of previously
//created database
public class College {
    private String college_ID;
    private String college_name;
    private String location;

    public College (String college_ID, String college_name, String location){
        this.college_ID = college_ID;
        this.college_name = college_name;
        this.location = location;
    }

    public String getCollege_ID() {
        return college_ID;
    }
    public String getCollege_name() {
        return college_name;
    }
    public String getLocation(){
        return location;
    }
}
