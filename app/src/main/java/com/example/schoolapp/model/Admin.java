// Supervisor.java (was surveillant)
package com.example.schoolapp.model;

public class Admin extends User {
    private String institutionName;

    public Admin(long id, String email, String password, String firstname,
                      String lastname, String institutionName,int age) {
        super(id, email, password, firstname, lastname,age);
        this.institutionName = institutionName;
    }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
}