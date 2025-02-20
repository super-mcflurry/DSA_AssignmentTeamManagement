/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;

import java.io.Serializable;

/**
 *
 * @author oscar
 */
public class TutorialGroup implements Serializable {
    private String tutorialGroupProgrammeKey;
    private String tutorialGroupId;
    private String intake;
    private ListInterface<Student> students;
    private ListInterface<CourseAssignmentTeams> courseAssignmentTeams;



    public TutorialGroup(String key, String intake, int groupNo){
        this.tutorialGroupProgrammeKey = key.toUpperCase();
        this.intake = intake;
        this.tutorialGroupId = tutorialGroupProgrammeKey + "-" + intake + "-" + groupNo;
        this.students = new DoublyCircularLinkedList<>();
        this.courseAssignmentTeams = new DoublyCircularLinkedList<>();

    }

    public TutorialGroup(String key, String intake, boolean isRepeat) {
        this.tutorialGroupProgrammeKey = key.toUpperCase();
        this.intake = intake;
        this.tutorialGroupId = tutorialGroupProgrammeKey + "-" + intake + "-" + "R";
        this.students = new DoublyCircularLinkedList<>();
        this.courseAssignmentTeams = new DoublyCircularLinkedList<>();
    }

    public String getTutorialGroupId() {
        return tutorialGroupId;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public int getStudentsCount(){
        return students.getSize();
    }

    public ListInterface<Student> getStudents() {
        return students;
    }

    public void setStudents(ListInterface<Student> students) {
        this.students = students;
    }
    public ListInterface<CourseAssignmentTeams> getCourseAssignmentTeams() {
        return courseAssignmentTeams;
    }

    public void setCourseAssignmentTeams(ListInterface<CourseAssignmentTeams> courseAssignmentTeams) {
        this.courseAssignmentTeams = courseAssignmentTeams;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        TutorialGroup tutorialGroup = (TutorialGroup) obj;
        return tutorialGroupId.equals(tutorialGroup.tutorialGroupId);
    }

    public String toString(){
        return String.format("%-20s %-20s %-10s %-10s %-10s %d\n",
                "Tutorial Group ID: ", tutorialGroupId, "Intake: ", intake, "Number of students: ", students.getSize());
    }
}


