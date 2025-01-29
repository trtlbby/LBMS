package com.example.testdb;
/*
@Author: Earl Lawrence P. Bacsain
*/
public class LibraryStaff {
    private String staff_ID;
    private String lastname;
    private String firstname;
    private String middlename;
    private String email;
    private String role;
    private String contactNo;
    private String section_ID;

    LibraryStaff(String staff_ID, String lastname, String firstname,
                 String middlename, String email, String role,
                 String contactNo, String section_ID){
        this.staff_ID = staff_ID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.email = email;
        this.role = role;
        this.contactNo = contactNo;
        this.section_ID = section_ID;
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

    public String getFullname() {
        return (lastname != null ? lastname : "") + ", " +
                (firstname != null ? firstname : "") + " " +
                (middlename != null ? middlename : "");
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSection_ID() {
        return section_ID;
    }

    public void setSection_ID(String section_ID) {
        this.section_ID = section_ID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaff_ID() {
        return staff_ID;
    }
    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
    }
}
