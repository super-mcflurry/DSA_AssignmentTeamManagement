/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author oscar
 */

public class Tutor implements Serializable{
    private String id;
    private String name;
    private String ic;
    private String gender;
    private String workType;
    private String phoneNo;
    private String email;
    private String faculty;
    private LocalDate registerDate;
    private int teachHour;
    private static int idNo = 1000;
    
    public Tutor(String name, String ic, String gender, String workType, String phoneNo, String faculty){
        this.id = "T" + idNo;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.workType = workType;
        this.phoneNo = phoneNo;
        this.email = id.toLowerCase() + "@tarumt.edu.my";
        this.faculty = faculty;
        this.registerDate = LocalDate.now();
        this.teachHour = 18;
        idNo++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public int getTeachHour() {
        return teachHour;
    }

    public void setTeachHour(int teachHour) {
        this.teachHour = teachHour;
    }

    public static int getIdNo() {
        return idNo;
    }

    public static void setIdNo(int idNo) {
        Tutor.idNo = idNo;
    }

    public String showtutorDetails(){
        return String.format("%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n%-15s%-20s\n",
                "ID", ": " + id, "Name", ": " + name, "IC", ": " + ic, "Gender", ": " + gender, "Work Type", ": " + workType, 
                "Phone Number", ": " + phoneNo, "Email", ": " + email, "Faculty", ": " + faculty, "Register Date", ": " + registerDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tutor tutor = (Tutor) obj;
        return Objects.equals(id, tutor.id);
    }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-14s %-8s %-11s %-14s %-20s %-8s %-11s",
                id, name, ic, gender, workType, phoneNo, email, faculty, registerDate);
    }
}
