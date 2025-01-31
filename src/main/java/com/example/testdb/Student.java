package com.example.testdb;

public class Student {
    private String stud_num, name, purpose, college_ID;

    public Student(String student_ID, String name, String purpose, String college_ID){
        this.stud_num = student_ID;
        this.name = name;
        this.purpose = purpose;
        this.college_ID = college_ID;
    }

    public String getStud_num() {
        return stud_num;
    }

    public void setStud_num(String stud_num) {
        this.stud_num = stud_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCollege_ID() {
        return college_ID;
    }

    public void setCollege_ID(String college_ID) {
        this.college_ID = college_ID;
    }
}
