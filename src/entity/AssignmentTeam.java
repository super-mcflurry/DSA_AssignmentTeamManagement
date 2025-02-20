package entity;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;

import java.io.Serializable;

/**
 * @author Lai Guan Hong
 */
public class AssignmentTeam implements Serializable {
    private String teamId = "";
    private String teamName = "";
    private int minTeamSize = 0;
    private int maxTeamSize = 0;

    private String grade;

    private Student leader;
    private Course course;
    private ListInterface<Student> members;

    private static int teamNo = 1;
    public AssignmentTeam() {
    }

    public AssignmentTeam(String teamId, String teamName, Course course) {
        this.teamId = teamId + "-" + teamNo;
        teamNo++;
        this.teamName = teamName;
        this.minTeamSize = course.getMinTeamSize();
        this.maxTeamSize = course.getMaxTeamSize();
        this.members = new DoublyCircularLinkedList<>();
        this.leader = members.getFront();
        this.grade = "N/A";
    }

    public AssignmentTeam(String teamId, String teamName, int minTeamSize, int maxTeamSize, ListInterface<Student> members, String grade) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.minTeamSize = minTeamSize;
        this.maxTeamSize = maxTeamSize;
        this.members = members;
        this.leader = members.getFront();
        this.grade = grade;
    }

    public AssignmentTeam(String teamId,  int minTeamSize, int maxTeamSize) {
        this.teamId = teamId + "-" + teamNo;
        teamNo++;
        this.teamName = "Untitled";
        this.minTeamSize = minTeamSize;
        this.maxTeamSize = maxTeamSize;
        this.members = new DoublyCircularLinkedList<>();
        this.leader = members.getFront();
        this.grade = "N/A";
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMinTeamSize() {
        return minTeamSize;
    }

    public void setMinTeamSize(int minTeamSize) {
        this.minTeamSize = minTeamSize;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public ListInterface<Student> getMembers() {
        return members;
    }

    public void setMembers(ListInterface<Student> members) {
        this.members = members;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Student getLeader() {
        return leader;
    }

    public void setLeader(Student leader) {
        this.leader = leader;
    }

    public void addMember(Student student) {
        if (members.getSize() < maxTeamSize) {
            if (members.isEmpty()) {
                members.add(student);
                leader = student;
            } else {
                if (members.add(student)) {
                    System.out.println("Student added to the team.");
                } else {
                    System.out.println("Student is already in the team.");
                }
            }
        } else {
            System.out.println("Team is already at maximum size " + maxTeamSize + ". Unable to add student.");
        }
    }

    public void removeMember(Student student) {
        if (!members.isEmpty()){
            if (members.remove(student)) {
                System.out.println("Student removed from the team.");
                leader = members.getFront();
            } else {
                System.out.println("Student is not in the team.");
            }
        } else {
            System.out.println("Team is empty. Unable to remove student.");
        }
    }

    public String toString() {
        return String.format("%-10s %-20s %-10s %-10s\n", teamId, teamName, minTeamSize, maxTeamSize);
    }

}
