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
 * @author zhixi
 */
public class TutorClass implements Serializable{
    private Tutor tutor;
    private Course course;
    private ListInterface<TutorialGroup> tutorialGroups;
    private int classDuration;
    private char classType;
    
    public TutorClass(Tutor tutor, char classType, Course course){
        this.tutor = tutor;
        this.course = course;
        this.classType = classType;
        this.tutorialGroups = new DoublyCircularLinkedList<>();
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ListInterface<TutorialGroup> getTutorialGroups() {
        return tutorialGroups;
    }

    public void setTutorialGroups(ListInterface<TutorialGroup> tutorialGroup) {
        this.tutorialGroups = tutorialGroup;
    }

    public int getClassDuration() {
        return classDuration;
    }

    public void setClassDuration(int classDuration) {
        this.classDuration = classDuration;
    }

    public char getClassType() {
        return classType;
    }

    public void setClassType(char tutorType) {
        this.classType = tutorType;
    }

    public boolean isSame(String tutorId, String courseCode, char classType){
        return this.tutor.getId().equals(tutorId) &&
                this.course.getCourseCode().equals(courseCode) &&
                this.classType == classType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TutorClass tutorClass = (TutorClass) obj;
        return tutor.equals(tutorClass.tutor) && course.equals(tutorClass.course) && classType == tutorClass.classType;
    }

    @Override
    public String toString(){
        return String.format("%-20s %-20s %-10s %-10s\n%-10s %-20s\n%-15s %-20s",
                "Tutor Name : ", tutor.getName(), "Faculty : ", tutor.getFaculty(), "Course : ", course.getCourseName(),
                "Tutorial Group : ", tutorialGroups.toString());
    }
}
