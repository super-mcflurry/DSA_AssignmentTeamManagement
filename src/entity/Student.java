/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CHOONG ZHI YANG
 */
public class Student implements Serializable {

    private String studentID;
    private String studentName;
    private String studentIC;
    private String studentGender;
    private String studentPhoneNo;
    private Programme studentProgramme;
    private String studentEmail;
    private ListInterface<CourseStatus> studentCourseList;
    private int totalCreditHour;
    private boolean isInTutorialGroup;
    private final int MaxCreditHour = 20;
    private static int numOfStudents = 0 ;

    public Student() {
        this.studentCourseList = new DoublyCircularLinkedList<>();
    }

    public Student(String studentID, String studentName, String studentIC, String studentGender, String studentPhoneNo, Programme studentProgramme, String studentEmail) {
        this.numOfStudents++;
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentIC = studentIC;
        this.studentGender = studentGender;
        this.studentPhoneNo = studentPhoneNo;
        this.studentProgramme = studentProgramme;
        this.studentEmail = studentEmail;
        this.studentCourseList = new DoublyCircularLinkedList<>();
        this.totalCreditHour = 0;
        this.isInTutorialGroup = false;
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public void setNumOfStudents(int numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentIC() {
        return studentIC;
    }

    public void setStudentIC(String studentIC) {
        this.studentIC = studentIC;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentPhoneNo() {
        return studentPhoneNo;
    }

    public void setStudentPhoneNo(String studentPhoneNo) {
        this.studentPhoneNo = studentPhoneNo;
    }

    public Programme getStudentProgramme() {
        return studentProgramme;
    }

    public void setStudentProgramme(Programme studentProgramme) {
        this.studentProgramme = studentProgramme;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public int getTotalCreditHour() {
        return totalCreditHour;
    }

    public void setTotalCreditHour(int totalCreditHour) {
        this.totalCreditHour = totalCreditHour;
    }

    public int getMaxCreditHour() {
        return MaxCreditHour;
    }
    
    public boolean getIsInTutorialGroup() {
        return isInTutorialGroup;
    }

    public void setIsInTutorialGroup(boolean isInTutorialGroup) {
        this.isInTutorialGroup = isInTutorialGroup;
    }

    public String studentDetails() {
        return "studentID : " + studentID + "\nstudentName : " + studentName + "\nstudentIC : " + studentIC + "\nstudentGender : " + studentGender + "\nstudentPhoneNo : " + studentPhoneNo + "\nstudentProgramme : " + studentProgramme.getProgrammeName() + "\nstudentEmail : " + studentEmail;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-30s %-20s %-7s %-20s %-25s %-18s %-13s", studentID, studentName, studentIC, studentGender, studentPhoneNo, studentEmail, studentProgramme.getProgrammeCode(), totalCreditHour);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        return studentID.equals(other.studentID);
    }

    public ListInterface<CourseStatus> getStudentCourseList() {
        return studentCourseList;
    }

    public void setStudentCourseList(ListInterface<CourseStatus> studentCourseList) {
        this.studentCourseList = studentCourseList;
    }

    public class CourseStatus implements Serializable {

        private Course course;
        private String status;

        public CourseStatus(Course course, String status) {
            this.course = course;
            this.status = status;
        }

        public Course getCourse() {
            return course;
        }

        // Getters and setters
        public void setCourse(Course course) {
            this.course = course;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CourseStatus other = (CourseStatus) obj;
            return Objects.equals(course.getCourseCode(), other.course.getCourseCode());
        }
    }

}
