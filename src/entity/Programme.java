/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.ListInterface;
import java.io.Serializable;
import java.util.Objects;
import adt.DoublyCircularLinkedList;


/**
 *
 * @author jsony
 */

public class Programme implements Serializable {
    
    private String programmeCode;
    private String programmeName;
    private String programmeIntake;
    private int programmeDuration;
    private String faculty;
    private double programmeFee;
    private ListInterface<TutorialGroup> tutorialGroups;

    public Programme() {
        this.programmeFee = 0;
    }

    public Programme(String programmeCode) {
        this.programmeCode = programmeCode;
    }

    public Programme(String programmeCode, String programmeName, String programmeIntake, int programmeDuration, String faculty) {
        this();
        this.programmeCode = programmeCode;
        this.programmeName = programmeName;
        this.programmeIntake = programmeIntake;
        this.programmeDuration = programmeDuration;
        this.faculty = faculty;
        this.tutorialGroups = new DoublyCircularLinkedList<>();
        tutorialGroups.addFront(new TutorialGroup(programmeCode, programmeIntake.substring(programmeIntake.length()-4), true));
        //tutorialGroups.addBack(new TutorialGroup(programmeCode, programmeIntake.substring(programmeIntake.length()-4), this.tutorialGroups.getSize()));
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public double getProgrammeFee() {
        return programmeFee;
    }

    public void setProgrammeFee(double programmeFee) {
        this.programmeFee = programmeFee;
    }

    public String getProgrammeIntake() {
        return programmeIntake;
    }

    public void setProgrammeIntake(String programmeIntake) {
        this.programmeIntake = programmeIntake;
    }

    public int getProgrammeDuration() {
        return programmeDuration;
    }

    public void setProgrammeDuration(int programmeDuration) {
        this.programmeDuration = programmeDuration;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public ListInterface<TutorialGroup> getTutorialGroups() {
        return tutorialGroups;
    }

    public void setTutorialGroups(ListInterface<TutorialGroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Programme programme = (Programme) obj;
        return Objects.equals(programmeCode, programme.programmeCode);
    }


    @Override
    public String toString() {
        String result = String.format("%-10s %-80s %-10s %-15s %-10s ", programmeCode, programmeName, programmeDuration, programmeIntake, faculty);

        return result;
    }
    
    
}
