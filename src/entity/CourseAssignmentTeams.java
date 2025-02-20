package entity;

/**
 * @author LAI GUAN HONG
 */

import adt.DoublyCircularLinkedList;
import adt.ListInterface;

import java.io.Serializable;

public class CourseAssignmentTeams implements Serializable {
    private Course course;
    private TutorialGroup tutorialGroup;
    private ListInterface<AssignmentTeam> assignmentTeams;

    public CourseAssignmentTeams(Course course, TutorialGroup tutorialGroup) {
        this.course = course;
        this.tutorialGroup = tutorialGroup;
        this.assignmentTeams = new DoublyCircularLinkedList<>();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public ListInterface<AssignmentTeam> getAssignmentTeams() {
        return assignmentTeams;
    }

    public void setAssignmentTeams(ListInterface<AssignmentTeam> assignmentTeams) {
        this.assignmentTeams = assignmentTeams;
    }

    @Override
    public String toString() {
        return "CourseAssignmentTeams{" +
                "course=" + course +
                ", tutorialGroup=" + tutorialGroup +
                ", assignmentTeams=" + assignmentTeams +
                '}';
    }
}