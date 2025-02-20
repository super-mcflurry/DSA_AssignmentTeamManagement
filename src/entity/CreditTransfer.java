/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author jsony
 */
public class CreditTransfer  implements Serializable {
    
    private String previousCourse;
    private String courseDesc;
    private int creditHours;
    private char grade;
    private Student student;
    private Course selectedCourse;
    private String status;
    private LocalDate actionDate; 

    public CreditTransfer(String previousCourse, String courseDesc, int creditHours, char grade, Course selectedCourse, Student student) {
        this.previousCourse = previousCourse;
        this.courseDesc = courseDesc;
        this.creditHours = creditHours;
        this.grade = grade;
        this.selectedCourse = selectedCourse;
        this.student = student;
        this.status = "Pending";
    }
    
    public String getPreviousCourse() {
        return previousCourse;
    }

    public void setPreviousCourse(String previousCourse) {
        this.previousCourse = previousCourse;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
//    @Override
//    public String toString() {
//       
//    }

}
