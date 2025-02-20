
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import boundary.AssignmentTeamManagementUI;
import dao.DoublyCircularLinkedListDAO;
import entity.*;
import utility.Utility;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author LAI GUAN HONG
 */
public class AssignmentTeamManagement implements Serializable {

    private AssignmentTeamManagementUI AssTeamUI = new AssignmentTeamManagementUI();
    private DoublyCircularLinkedListDAO dao = new DoublyCircularLinkedListDAO();
    private ListInterface<Course> courseList = new DoublyCircularLinkedList<>();

    private ListInterface<Programme> programmeList = new DoublyCircularLinkedList<>();
    private ListInterface<Programme> programmes = new DoublyCircularLinkedList<>();

    private ListInterface<TutorialGroup> tutorialGroups = new DoublyCircularLinkedList<>();

    private ListInterface<CourseAssignmentTeams> courseAssignmentTeams = new DoublyCircularLinkedList<>();

    private ListInterface<AssignmentTeam> assignmentTeams = new DoublyCircularLinkedList<>();

    private ListInterface<Student> students = new DoublyCircularLinkedList<>();

    private Course selectedCourse;

    private Programme selectedProgramme;

    private TutorialGroup selectedTutorialGroup;

    public AssignmentTeamManagement() {
        DoublyCircularLinkedList retrievedProgramme = dao.loadFromFile("Programme");
        programmeList = Objects.requireNonNullElseGet(retrievedProgramme, DoublyCircularLinkedList::new);
        DoublyCircularLinkedList retrievedData = dao.loadFromFile("Course");
        courseList = Objects.requireNonNullElseGet(retrievedData, DoublyCircularLinkedList::new);
        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = courseList.getPosition(i);
            for (int j = 0; j < programmeList.getSize(); j++) {
                Programme programme = programmeList.getPosition(j);
                if (course.getProgrammesAssociated().contains(programme)) {
                    course.getProgrammesAssociated().update(programme, programme);
                }
            }
        }
        DoublyCircularLinkedList courseAssignmentTeamsList = dao.loadFromFile("CourseAssignmentTeams");
        courseAssignmentTeams = Objects.requireNonNullElseGet(courseAssignmentTeamsList, DoublyCircularLinkedList::new);

    }

    public void runAssignmentTeamManagement() {
        while (true) {
            int courseOption = AssTeamUI.displayCourseMenu(listAllCourses(), getCourseListSize());
            if (courseOption == -1) {
                AssTeamUI.displayExitMessage();
                Utility.clearScreen();
                break;
            }
            selectedCourse = courseList.getPosition(courseOption);
            programmes = selectedCourse.getProgrammesAssociated();
            Utility.clearScreen();
            runProgrammeSelection();
        }
    }

    public void runProgrammeSelection() {
        while (true) {
            int programmeOption = AssTeamUI.displayProgrammeMenu(selectedCourse, listAllProgrammes(), getProgrammeListSize());
            if (programmeOption == -1) {
                Utility.clearScreen();
                break;
            }
            selectedProgramme = programmes.getPosition(programmeOption);
            tutorialGroups = selectedProgramme.getTutorialGroups();
            Utility.clearScreen();
            runTutorialGroupSelection();
        }
    }

    public void runTutorialGroupSelection() {
        while (true) {
            int tutorialGroupOption = AssTeamUI.displayTutorialGroupMenu(selectedProgramme, listAllTutorialGroups(), getTutorialGroupListSize());
            if (tutorialGroupOption == -1) {
                Utility.clearScreen();
                break;
            }
            selectedTutorialGroup = tutorialGroups.getPosition(tutorialGroupOption);
            if (selectedTutorialGroup.getTutorialGroupId().equals("RSD-2024-1")) {
                selectedTutorialGroup.getCourseAssignmentTeams().add(courseAssignmentTeams.getPosition(0));
            }
            students = selectedTutorialGroup.getStudents();
            courseAssignmentTeams = selectedTutorialGroup.getCourseAssignmentTeams();

            boolean found = false;
            for (int i = 0; i < courseAssignmentTeams.getSize(); i++) {
                CourseAssignmentTeams courseAssignmentTeam = courseAssignmentTeams.getPosition(i);
                if (courseAssignmentTeam.getCourse().equals(selectedCourse) && courseAssignmentTeam.getTutorialGroup().equals(selectedTutorialGroup)) {
                    assignmentTeams = courseAssignmentTeam.getAssignmentTeams();
                    found = true;
                    break;
                }
            }
            if (!found) {
                CourseAssignmentTeams newCourseAssignmentTeam = new CourseAssignmentTeams(selectedCourse, selectedTutorialGroup);
                courseAssignmentTeams.add(newCourseAssignmentTeam);
                assignmentTeams = newCourseAssignmentTeam.getAssignmentTeams();
            }

            for (int i = 0; i < assignmentTeams.getSize(); i++) {
                AssignmentTeam team = assignmentTeams.getPosition(i);
                ListInterface<Student> members = new DoublyCircularLinkedList<>();
                for (int k = 0; k < team.getMembers().getSize(); k++) {
                    Student student = team.getMembers().getPosition(k);
                    members.add(student);
                }

                for (int j = 0; j < members.getSize(); j++) {
                    Student student = members.getPosition(j);
                    if (!students.contains(student)) {
                        team.removeMember(student);
                    }
                }

                if (team.getMembers().getSize() == 0) {
                    team.setLeader(null);
                }
            }

            runAssignmentTeamMainMenu();
        }
    }

    public void runAssignmentTeamMainMenu() {
        Utility.clearScreen();
        int choice = AssTeamUI.displaySystemMenu(selectedTutorialGroup);
        runAssignmentTeamMethod(choice);
    }

    public void runAssignmentTeamMethod(int choice) {
        while (true) {
            switch (choice) {
                case 1 ->
                    // Create an assignment team
                    createAssignmentTeam();
                case 2 ->
                    removeAssignmentTeam();
                case 3 ->
                    // Amend details of an assignment team
                    amendAssignmentTeam();
                case 4 ->
                    // Add students to an assignment team
                    addStudentsToAssignmentTeam();
                case 5 ->
                    // Remove students from an assignment team
                    removeStudentsFromAssignmentTeam();

                case 6 ->
                    mergeAssignmentTeams();
                case 7 ->
                    // List all assignment teams
                    listAssignmentTeams();
                case 8 ->
                    // List students under an assignment team
                    listStudentsUnderAssignmentTeam();
                case 9 ->
                    generateSummaryReport();
                case 0 -> {
                    Utility.clearScreen();
                    return;
                }
                default -> {
                }
            }
            choice = AssTeamUI.displaySystemMenu(selectedTutorialGroup);
        }
    }

    public String listAllCourses() {
        StringBuilder sb = new StringBuilder();
        if (courseList.isEmpty()) {
            return sb.toString();
        }
        int size = courseList.getSize();

        for (int i = 0; i < size; i++) {
            Course course = courseList.getPosition(i);
            String courseCode = course.getCourseCode();
            String courseName = course.getCourseName();

            sb.append("| ").append(i + 1).append(". ");
            sb.append(" ".repeat(Math.max(0, 2 - String.valueOf(i + 1).length())));
            sb.append("| ").append(courseCode);
            sb.append(" ".repeat(Math.max(0, 12 - courseCode.length())));
            sb.append("| ").append(courseName);
            sb.append(" ".repeat(Math.max(0, 35 - courseName.length())));
            sb.append(" |\n");
        }

        // Footer
        sb.append("=".repeat(59));

        return sb.toString();
    }

    public String listAllProgrammes() {
        StringBuilder sb = new StringBuilder();
        if (selectedCourse.getProgrammesAssociated().isEmpty()) {
            return sb.toString();
        }
        int size = selectedCourse.getProgrammesAssociated().getSize();
        for (int i = 0; i < size; i++) {
            Programme programme = selectedCourse.getProgrammesAssociated().getPosition(i);
            String programmeCode = programme.getProgrammeCode();
            String programmeName = programme.getProgrammeName();

            sb.append("| ").append(i + 1).append(". ");
            sb.append("| ").append(programmeCode);
            sb.append(" ".repeat(Math.max(0, 15 - programmeCode.length())));
            sb.append("| ").append(programmeName);
            sb.append(" ".repeat(Math.max(0, 77 - programmeName.length())));
            sb.append(" |\n");
        }

        // Footer
        sb.append("=".repeat(103));
        return sb.toString();
    }

    public String listAllTutorialGroups() {
        StringBuilder sb = new StringBuilder();
        if (selectedProgramme.getTutorialGroups().isEmpty()) {
            return sb.toString();
        }
        int size = selectedProgramme.getTutorialGroups().getSize();
        for (int i = 0; i < size; i++) {
            TutorialGroup tutorialGroup = selectedProgramme.getTutorialGroups().getPosition(i);
            if (tutorialGroup.getTutorialGroupId().equals("RSD-2024-1")) {
                tutorialGroup.getCourseAssignmentTeams().add(courseAssignmentTeams.getPosition(0));
            }
            String tutorialGroupId = tutorialGroup.getTutorialGroupId();
            String tutorialGroupIntake = tutorialGroup.getIntake();
            String tutorialGroupStudents = String.valueOf(tutorialGroup.getStudents().getSize());

            int teamCount = 0;
            for (int j = 0; j < tutorialGroup.getCourseAssignmentTeams().getSize(); j++) {
                CourseAssignmentTeams courseAssignmentTeam = tutorialGroup.getCourseAssignmentTeams().getPosition(j);
                if (courseAssignmentTeam.getCourse().equals(selectedCourse)) {
                    teamCount = courseAssignmentTeam.getAssignmentTeams().getSize();
                    break;
                }
            }

            String tutorialGroupTeamCount = String.valueOf(teamCount);

            sb.append("| ").append(i + 1).append(". ");
            sb.append("| ").append(tutorialGroupId);
            sb.append(" ".repeat(Math.max(0, 15 - tutorialGroupId.length())));
            sb.append("| ").append(tutorialGroupIntake);
            sb.append(" ".repeat(Math.max(0, 12 - tutorialGroupIntake.length())));
            sb.append("| ").append(tutorialGroupStudents);
            sb.append(" ".repeat(Math.max(0, 19 - tutorialGroupStudents.length())));
            sb.append("| ").append(tutorialGroupTeamCount);
            sb.append(" ".repeat(Math.max(0, 39 - tutorialGroupTeamCount.length())));
            sb.append(" |\n");
        }

        // Footer
        sb.append("=".repeat(100));
        return sb.toString();
    }

    public String listSelectedStudents(ListInterface<Student> students) {
        StringBuilder sb = new StringBuilder();
        if (students.isEmpty()) {
            return sb.toString();
        }
        int size = students.getSize();
        for (int i = 0; i < size; i++) {
            Student student = students.getPosition(i);
            String studentId = student.getStudentID();
            String studentName = student.getStudentName();
            String studentEmail = student.getStudentEmail();
            String studentGender = student.getStudentGender();
            sb.append("| ").append(i + 1).append(". ");
            sb.append(" ".repeat(Math.max(0, 2 - String.valueOf(i + 1).length())));
            sb.append(" | ").append(studentId);
            sb.append(" ".repeat(Math.max(0, 12 - studentId.length())));
            sb.append("| ").append(studentName);
            sb.append(" ".repeat(Math.max(0, 25 - studentName.length())));
            sb.append("| ").append(studentEmail);
            sb.append(" ".repeat(Math.max(0, 22 - studentEmail.length())));
            sb.append("| ").append(studentGender);
            sb.append(" ".repeat(Math.max(0, 7 - studentGender.length())));
            sb.append(" |\n");
        }
        sb.append("=".repeat(83));
        return sb.toString();
    }

    public int getCourseListSize() {
        return courseList.getSize();
    }

    public int getProgrammeListSize() {
        return selectedCourse.getProgrammesAssociated().getSize();
    }

    public int getTutorialGroupListSize() {
        return selectedProgramme.getTutorialGroups().getSize();
    }

    public int getAssignmentTeamListSize() {
        return assignmentTeams.getSize();
    }

    public int getSelectedStudentListSize(ListInterface<Student> students) {
        return students.getSize();
    }

    // Method to create an assignment team
    public void createAssignmentTeam() {
        while (true) {
            Utility.clearScreen();
            int existingTeams = assignmentTeams.getSize();
            int[] teamSizes = calculateTeamSuggestions();

            if (teamSizes == null) {
                boolean stillCreateTeams = AssTeamUI.promptCreateMoreTeams();
                if (stillCreateTeams) {
                    createTeamsManually();

                }
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            }

            int suggestedNumTeams = teamSizes.length;
            int groupNeeded = suggestedNumTeams - existingTeams;

            if (groupNeeded == 0) {
                boolean stillCreateTeams = AssTeamUI.displayNoTeamNeeded(existingTeams);
                if (stillCreateTeams) {
                    createTeamsManually();
                }
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            } else if (groupNeeded > 0) {
                AssTeamUI.displayTeamNeeded(groupNeeded, existingTeams);
            } else {
                AssTeamUI.displayOverTeamNeeded(-groupNeeded, existingTeams);
                boolean stillCreateTeams = AssTeamUI.promptCreateMoreTeams();
                if (stillCreateTeams) {
                    createTeamsManually();
                }
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            }

            int choice = AssTeamUI.displayMethodToCreateTeam();
            if (choice == 1) {
                createTeamsAutomatically(teamSizes, existingTeams);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            } else if (choice == 2) {
                createTeamsManually();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            } else {
                Utility.clearScreen();
                break;
            }
            break;
        }
    }

    public int[] calculateTeamSuggestions() {
        int totalStudents = selectedTutorialGroup.getStudentsCount();
        int minTeamSize = selectedCourse.getMinTeamSize();
        int maxTeamSize = selectedCourse.getMaxTeamSize();

        int bestTeamSize = 0;
        int numTeams = 0;
        int remainingStudents = 0;

        if (totalStudents < minTeamSize) {
            AssTeamUI.displayInsufficientStudents(totalStudents, minTeamSize);
            return null;
        }

        for (int i = minTeamSize; i <= maxTeamSize; i++) {
            if (totalStudents % i == minTeamSize) {
                bestTeamSize = i;
                numTeams = totalStudents / bestTeamSize;
                remainingStudents = totalStudents % bestTeamSize;
                break;
            } else if (totalStudents % i == 0) {
                bestTeamSize = i;
                numTeams = totalStudents / bestTeamSize;
            }
        }

        if (bestTeamSize == 0) {
            bestTeamSize = minTeamSize;
            numTeams = totalStudents / bestTeamSize;
            remainingStudents = totalStudents % bestTeamSize;
        }
        int[] teamSizes;

        if (remainingStudents >= minTeamSize) {
            AssTeamUI.teamSuggestionRemaining(numTeams, totalStudents, bestTeamSize, remainingStudents);
            numTeams++;
            teamSizes = new int[numTeams];
            teamSizes[0] = remainingStudents;
            for (int i = 1; i < numTeams; i++) {
                teamSizes[i] = bestTeamSize;
            }
        } else if (remainingStudents > 0) {
            int teamsWithoutExtraStudent = numTeams - remainingStudents;
            if (teamsWithoutExtraStudent < 0) {
                teamSizes = new int[numTeams];
                for (int i = 0; i < numTeams; i++) {
                    teamSizes[i] = bestTeamSize;
                }
                while (remainingStudents > 0) {
                    for (int i = 0; i < numTeams; i++) {
                        if (remainingStudents == 0) {
                            break;
                        }
                        teamSizes[i]++;
                        remainingStudents--;
                    }
                }
                AssTeamUI.teamSuggest(numTeams, totalStudents);

            } else {
                AssTeamUI.teamSuggestionExtra(teamsWithoutExtraStudent, numTeams, totalStudents, remainingStudents, bestTeamSize);
                teamSizes = new int[numTeams];
                int index = 0;
                for (int i = 0; i < teamsWithoutExtraStudent; i++) {
                    teamSizes[index++] = bestTeamSize;
                }
                for (int i = 0; i < remainingStudents; i++) {
                    teamSizes[index++] = bestTeamSize + 1;
                }
            }
        } else {
            AssTeamUI.teamSuggestion(numTeams, totalStudents, bestTeamSize);
            teamSizes = new int[numTeams];
            for (int i = 0; i < numTeams; i++) {
                teamSizes[i] = bestTeamSize;
            }
        }
        return teamSizes;
    }

    public void createTeamsAutomatically(int[] teamSizes, int existingTeams) {
        ListInterface<AssignmentTeam> teamToAdded = new DoublyCircularLinkedList<>();
        int numNewTeams = teamSizes.length - existingTeams;

        for (int i = 0; i < numNewTeams; i++) {
            int teamSize = teamSizes[i];
            String teamId = selectedTutorialGroup.getTutorialGroupId();
            AssignmentTeam newTeam = new AssignmentTeam(teamId, teamSize, teamSize);
            teamToAdded.add(newTeam);
        }

        AssTeamUI.displayAssignmentTeams(teamToAdded);
        boolean insertName = AssTeamUI.insertTeamName();

        if (insertName) {
            for (int i = 0; i < teamToAdded.getSize(); i++) {
                AssignmentTeam team = teamToAdded.getPosition(i);
                String teamName = AssTeamUI.inputTeamName(i, team.getTeamId());
                team.setTeamName(teamName);
                boolean duplicate = checkDuplicateTeamName(team);
                if (duplicate) {
                    AssTeamUI.displayDuplicateTeamName(team.getTeamName());
                    i--;
                } else {
                    for (int j = 0; j < i; j++) {
                        AssignmentTeam previousTeam = teamToAdded.getPosition(j);
                        if (teamName.equalsIgnoreCase(previousTeam.getTeamName())) {
                            AssTeamUI.displayDuplicateTeamName(teamName);
                            i--;
                            break;
                        }
                    }
                }
            }
        }
        Utility.clearScreen();
        AssTeamUI.displayAssignmentTeams(teamToAdded);
        boolean confirm = AssTeamUI.confirmTeamCreation();

        if (confirm) {
            for (int i = 0; i < teamToAdded.getSize(); i++) {
                AssignmentTeam team = teamToAdded.getPosition(i);
                assignmentTeams.add(team);
            }
            AssTeamUI.displayAutoTeamCreationSuccess();
        } else {
            AssTeamUI.displayAutoTeamCreationCancelled();
        }
    }

    public void createTeamsManually() {
        Utility.clearScreen();
        while (true) {
            String teamId = selectedTutorialGroup.getTutorialGroupId();
            String teamName = AssTeamUI.inputAssignmentDetails();
            AssignmentTeam newTeam = new AssignmentTeam(teamId, teamName, selectedCourse);

            //check duplication of teamName
            boolean duplicate = checkDuplicateTeamName(newTeam);

            if (duplicate) {
                AssTeamUI.displayDuplicateTeamName(newTeam.getTeamName());
                continue;
            }

            AssTeamUI.displayTeamDetails(newTeam);

            boolean confirm = AssTeamUI.confirmTeamCreation();

            if (confirm) {
                boolean added = assignmentTeams.add(newTeam);
                if (added) {
                    AssTeamUI.displayTeamCreationSuccess(newTeam.getTeamName());
                } else {
                    AssTeamUI.displayTeamCreationFailure(newTeam.getTeamId());
                }
            } else {
                AssTeamUI.displayTeamCreationCancelled(newTeam.getTeamName());
            }

            boolean createAnother = AssTeamUI.createAnotherTeam();
            if (!createAnother) {
                break;
            }
        }
    }

    public boolean checkDuplicateTeamName(AssignmentTeam newTeam) {
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getTeamName().equalsIgnoreCase(newTeam.getTeamName())) {
                return true;
            }
        }
        return false;
    }

    // Method to remove an assignment team
    public void removeAssignmentTeam() {
        Utility.clearScreen();
        while (true) {
            if (assignmentTeams.isEmpty()) {
                AssTeamUI.displayNoTeamToRemove();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            }
            int option = AssTeamUI.displayMethodToRemoveTeam();

            if (option == 0) {
                                    Utility.clearScreen();
                return;
            } else if (option == 1) {
                //remove all teams
                AssTeamUI.displayAssignmentTeams(assignmentTeams);

                boolean confirm = AssTeamUI.confirmAllTeamRemoval();
                if (confirm) {
                    assignmentTeams.reset();
                    AssTeamUI.displayAllTeamRemovalSuccess();
                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                } else {
                    AssTeamUI.displayAllTeamRemovalCancelled();
                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                }
                break;
            } else {
                while (true) {
                    if (assignmentTeams.isEmpty()) {
                        AssTeamUI.displayNoTeamToRemove();
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        break;
                    }
                    int choice = AssTeamUI.selectAssignmentTeam(assignmentTeams, getAssignmentTeamListSize(), "remove team");
                    if (choice == -1) {
                        return;
                    }
                    AssignmentTeam teamToRemove = assignmentTeams.getPosition(choice);
                    AssTeamUI.displayTeamDetails(teamToRemove);
                    boolean confirm = AssTeamUI.confirmTeamRemoval();
                    if (confirm) {
                        assignmentTeams.remove(teamToRemove);
                        AssTeamUI.displayTeamRemovalSuccess(teamToRemove);
                    } else {
                        AssTeamUI.displayTeamRemovalCancelled(teamToRemove);
                    }
                    boolean removeAnother = AssTeamUI.removeAnotherTeam();
                    if (!removeAnother) {
                        break;
                    }
                }
                Utility.clearScreen();
                break;
            }
        }
    }

    public void amendAssignmentTeam() {
        Utility.clearScreen();
        while (true) {
            int choice = AssTeamUI.selectAssignmentTeam(assignmentTeams, getAssignmentTeamListSize(), "amend team");
            if (choice == -1) {

                Utility.clearScreen();
                return;
            }
            while (true) {
                AssignmentTeam teamToAmend = assignmentTeams.getPosition(choice);
                int amendChoice = AssTeamUI.selectAmendOption(teamToAmend);
                switch (amendChoice) {
                    case 1:
                        String newTeamName = AssTeamUI.promptNewTeamName(teamToAmend.getTeamName());
                        AssignmentTeam updatedTeamName = new AssignmentTeam(teamToAmend.getTeamId(), newTeamName, teamToAmend.getMinTeamSize(), teamToAmend.getMaxTeamSize(), teamToAmend.getMembers(), teamToAmend.getGrade());
                        AssTeamUI.displayTeamDetails(updatedTeamName);
                        boolean confirmNameUpdate = AssTeamUI.confirmTeamAmendment();
                        if (confirmNameUpdate) {
                            assignmentTeams.update(teamToAmend, updatedTeamName);
                            AssTeamUI.displayTeamAmendmentSuccess(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        } else {
                            AssTeamUI.displayTeamAmendmentCancelled(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 2:
                        int[] newMinMaxTeamSize = AssTeamUI.promptNewMinMaxTeamSize(teamToAmend.getMinTeamSize(), teamToAmend.getMaxTeamSize(), teamToAmend.getMembers().getSize());
                        AssignmentTeam updatedTeamSize = new AssignmentTeam(teamToAmend.getTeamId(), teamToAmend.getTeamName(), newMinMaxTeamSize[0], newMinMaxTeamSize[1], teamToAmend.getMembers(), teamToAmend.getGrade());
                        AssTeamUI.displayTeamDetails(updatedTeamSize);
                        boolean confirmSizeUpdate = AssTeamUI.confirmTeamAmendment();
                        if (confirmSizeUpdate) {
                            assignmentTeams.update(teamToAmend, updatedTeamSize);
                            AssTeamUI.displayTeamAmendmentSuccess(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        } else {
                            AssTeamUI.displayTeamAmendmentCancelled(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 3:
                        String newGrade = AssTeamUI.promptNewTeamGrade(teamToAmend.getGrade());
                        AssignmentTeam updatedTeamGrade = new AssignmentTeam(teamToAmend.getTeamId(), teamToAmend.getTeamName(), teamToAmend.getMinTeamSize(), teamToAmend.getMaxTeamSize(), teamToAmend.getMembers(), newGrade);
                        AssTeamUI.displayTeamDetails(updatedTeamGrade);
                        boolean confirmGradeUpdate = AssTeamUI.confirmTeamAmendment();
                        if (confirmGradeUpdate) {
                            assignmentTeams.update(teamToAmend, updatedTeamGrade);
                            AssTeamUI.displayTeamAmendmentSuccess(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        } else {
                            AssTeamUI.displayTeamAmendmentCancelled(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 4:
                        int leaderChoice = AssTeamUI.selectLeader(teamToAmend);
                        if (leaderChoice == -1) {

                            Utility.clearScreen();
                            break;
                        }
                        Student newLeader = teamToAmend.getMembers().getPosition(leaderChoice);
                        ListInterface<Student> members = teamToAmend.getMembers();
                        members.remove(newLeader);
                        members.addFront(newLeader);
                        AssignmentTeam updatedTeamLeader = new AssignmentTeam(teamToAmend.getTeamId(), teamToAmend.getTeamName(), teamToAmend.getMinTeamSize(), teamToAmend.getMaxTeamSize(), members, teamToAmend.getGrade());
                        AssTeamUI.displayTeamDetails(updatedTeamLeader);
                        boolean confirmLeaderUpdate = AssTeamUI.confirmTeamAmendment();
                        if (confirmLeaderUpdate) {
                            assignmentTeams.update(teamToAmend, updatedTeamLeader);
                            AssTeamUI.displayTeamAmendmentSuccess(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        } else {
                            AssTeamUI.displayTeamAmendmentCancelled(teamToAmend);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 5:
                        if (assignmentTeams.getSize() == 1) {
                            AssTeamUI.displayNoTeamToSwap();
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            break;
                        }

                        int swapChoice = AssTeamUI.selectSwapTeam(assignmentTeams, getAssignmentTeamListSize(), teamToAmend);
                        if (swapChoice == -1) {

                            Utility.clearScreen();
                            break;
                        }

                        AssignmentTeam teamToSwap = assignmentTeams.getPosition(swapChoice);
                        int studentChoiceSwap = AssTeamUI.selectStudentToSwap(teamToSwap, listSelectedStudents(teamToSwap.getMembers()), getSelectedStudentListSize(teamToSwap.getMembers()));
                        if (studentChoiceSwap == -1) {

                            Utility.clearScreen();
                            break;
                        }

                        if (studentChoiceSwap >= 0 && studentChoiceSwap < teamToSwap.getMembers().getSize()) {
                            Student studentToSwap = teamToSwap.getMembers().getPosition(studentChoiceSwap);

                            int studentChoiceAmend = AssTeamUI.selectStudentToSwap(teamToAmend, listSelectedStudents(teamToAmend.getMembers()), getSelectedStudentListSize(teamToAmend.getMembers()));
                            if (studentChoiceAmend == -1) {

                                Utility.clearScreen();
                                break;
                            }

                            if (studentChoiceAmend >= 0 && studentChoiceAmend < teamToAmend.getMembers().getSize()) {
                                Student studentAmend = teamToAmend.getMembers().getPosition(studentChoiceAmend);

                                ListInterface<Student> members1 = teamToAmend.getMembers();
                                ListInterface<Student> members2 = teamToSwap.getMembers();
                                members1.remove(studentAmend);
                                members1.add(studentToSwap);
                                members2.remove(studentToSwap);
                                members2.add(studentAmend);

                                AssignmentTeam updatedTeam1 = new AssignmentTeam(teamToAmend.getTeamId(), teamToAmend.getTeamName(), teamToAmend.getMinTeamSize(), teamToAmend.getMaxTeamSize(), members1, teamToAmend.getGrade());
                                AssignmentTeam updatedTeam2 = new AssignmentTeam(teamToSwap.getTeamId(), teamToSwap.getTeamName(), teamToSwap.getMinTeamSize(), teamToSwap.getMaxTeamSize(), members2, teamToSwap.getGrade());

                                boolean confirmSwap = AssTeamUI.confirmSwap(studentToSwap, studentAmend, teamToAmend, teamToSwap);
                                if (confirmSwap) {
                                    assignmentTeams.update(teamToAmend, updatedTeam1);
                                    assignmentTeams.update(teamToSwap, updatedTeam2);
                                    AssTeamUI.displayTeamAmendmentSuccess(teamToAmend);
                                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                                } else {
                                    AssTeamUI.displayTeamAmendmentCancelled(teamToAmend);
                                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                                }
                            }
                        }

                        break;
                    case 0:
                        Utility.clearScreen();
                        return;

                }
                boolean amendAnother = AssTeamUI.amendAnotherTeamDetails();
                if (!amendAnother) {
                    Utility.clearScreen();
                    break;
                }
            }
            boolean amendAnother = AssTeamUI.amendAnotherTeam();
            if (!amendAnother) {

                Utility.clearScreen();
                break;
            }
        }
    }

    public String getTotalNumberStudentsHaveTeam() {
        int total = 0;
        int totalStudents = selectedTutorialGroup.getStudentsCount();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            total += team.getMembers().getSize();
        }
        return total + "/" + totalStudents;
    }

    public void addStudentsToAssignmentTeam() {
        Utility.clearScreen();
        if (assignmentTeams.isEmpty()) {
            AssTeamUI.displayNoTeam();
            Utility.pressEnterToContinue();
            return;
        }

        ListInterface<Student> availableStudents = getAvailableStudents();
        if (availableStudents.isEmpty()) {
            AssTeamUI.teamFull();
            Utility.pressEnterToContinue();
            return;
        }

        boolean teamFull = true;
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getMembers().getSize() < team.getMaxTeamSize()) {
                teamFull = false;
                break;
            }
        }
        if (teamFull) {
            AssTeamUI.teamFull();
            Utility.pressEnterToContinue();
            return;
        }

        int option = AssTeamUI.displayMethodToAddStudent();
        if (option == 0) {
            Utility.clearScreen();
            return;
        }
        if (option == 1) {
            addStudentsAutomatically();
        } else {
            addStudentsManually();
        }

    }

    public void addStudentsAutomatically() {
        ListInterface<Student> availableStudents = getAvailableStudents();
        ListInterface<AssignmentTeam> sortedTeams = sortTeamByStudentSize(assignmentTeams);

        ListInterface<AssignmentTeam> originalTeamsCopy = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam originalTeam = assignmentTeams.getPosition(i);
            ListInterface<Student> originalMembers = new DoublyCircularLinkedList<>();
            for (int j = 0; j < originalTeam.getMembers().getSize(); j++) {
                Student student = originalTeam.getMembers().getPosition(j);
                originalMembers.add(student);
            }
            AssignmentTeam copyTeam = new AssignmentTeam(originalTeam.getTeamId(), originalTeam.getTeamName(), originalTeam.getMinTeamSize(), originalTeam.getMaxTeamSize(), originalMembers, originalTeam.getGrade());
            originalTeamsCopy.add(copyTeam);
        }

        ListInterface<AssignmentTeam> modifiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < sortedTeams.getSize(); i++) {
            AssignmentTeam team = sortedTeams.getPosition(i);
            int availableSlots = team.getMaxTeamSize() - team.getMembers().getSize();
            if (availableSlots == 0) {
                modifiedTeams.add(team);
                continue;
            }
            ListInterface<Student> modifiedMembers = new DoublyCircularLinkedList<>();
            for (int j = 0; j < team.getMembers().getSize(); j++) {
                Student student = team.getMembers().getPosition(j);
                modifiedMembers.add(student);
            }
            AssignmentTeam modifiedTeam = new AssignmentTeam(team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMaxTeamSize(), modifiedMembers, team.getGrade());
            for (int j = 0; j < availableSlots; j++) {
                if (availableStudents.isEmpty()) {
                    break;
                }
                Student student = availableStudents.getFront();
                availableStudents.removeFront();
                modifiedTeam.addMember(student);
            }
            modifiedTeams.add(modifiedTeam);
        }

        AssTeamUI.displayAllStudentsUnderTeams(modifiedTeams);
        boolean confirm = AssTeamUI.confirmStudentAddition();
        if (confirm) {
            assignmentTeams.reset();
            for (int i = 0; i < modifiedTeams.getSize(); i++) {
                AssignmentTeam modifiedTeam = modifiedTeams.getPosition(i);
                assignmentTeams.add(modifiedTeam);
            }
            AssTeamUI.displayStudentAutoAdditionSuccess();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else {
            assignmentTeams.reset();
            for (int i = 0; i < originalTeamsCopy.getSize(); i++) {
                AssignmentTeam originalTeam = originalTeamsCopy.getPosition(i);
                assignmentTeams.add(originalTeam);
            }
            AssTeamUI.displayStudentAutoAdditionCancelled();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }
    }

    public void addStudentsManually() {
        while (true) {
            int choice = AssTeamUI.displayAssignmentTeamDetails(assignmentTeams, getTotalNumberStudentsHaveTeam());

            if (choice == -1) {
                Utility.clearScreen();
                break;
            }
            AssignmentTeam teamToAdd = assignmentTeams.getPosition(choice);

            int availableSlots = teamToAdd.getMaxTeamSize() - teamToAdd.getMembers().getSize();
            if (availableSlots <= 0) {
                AssTeamUI.displayNoAvailableSlots(teamToAdd);
                continue;
            }
            while (true) {

                ListInterface<Student> studentsToAdd = getAvailableStudents();

                int studentChoice = AssTeamUI.selectStudentToAdd(listSelectedStudents(studentsToAdd), getSelectedStudentListSize(studentsToAdd));
                if (studentChoice == -1) {
                    Utility.clearScreen();
                    break;
                }
                Student studentToAdd = studentsToAdd.getPosition(studentChoice);

                AssTeamUI.displayStudentDetails(studentToAdd);

                boolean confirm = AssTeamUI.confirmStudentAddition();
                if (confirm) {
                    teamToAdd.addMember(studentToAdd);
                    AssTeamUI.displayStudentAdditionSuccess(studentToAdd, teamToAdd);
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                } else {
                    AssTeamUI.displayStudentAdditionCancelled(studentToAdd, teamToAdd);
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }

                if (teamToAdd.getMembers().getSize() == teamToAdd.getMaxTeamSize()) {
                    AssTeamUI.displayTeamFull(teamToAdd);
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    break;
                }

                boolean addAnother = AssTeamUI.addAnotherStudent(teamToAdd);
                if (!addAnother) {
                    Utility.clearScreen();
                    break;
                }
            }

            boolean addAnotherTeam = AssTeamUI.addStudentToOthersTeam();
            if (!addAnotherTeam) {
                break;
            }
        }
    }

    public ListInterface<Student> getAvailableStudents() {
        ListInterface<Student> availableStudents = new DoublyCircularLinkedList<>();
        ListInterface<Student> occupiedStudents = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            for (int j = 0; j < team.getMembers().getSize(); j++) {
                Student student = team.getMembers().getPosition(j);
                occupiedStudents.add(student);
            }
        }

        for (int i = 0; i < students.getSize(); i++) {
            Student student = students.getPosition(i);
            if (!occupiedStudents.contains(student)) {
                availableStudents.add(student);
            }
        }

        return availableStudents;
    }

    public void removeStudentsFromAssignmentTeam() {
        Utility.clearScreen();
        while (true) {
            boolean studentExist = false;
            for (int i = 0; i < assignmentTeams.getSize(); i++) {
                AssignmentTeam team = assignmentTeams.getPosition(i);
                if (!team.getMembers().isEmpty()) {
                    studentExist = true;
                    Utility.clearScreen();
                    break;
                }
            }
            if (!studentExist) {
                AssTeamUI.displayNoStudentToRemove();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                return;
            }
            int option = AssTeamUI.displayMethodToRemoveStudent();
            if (option == 0) {
                Utility.clearScreen();
                return;
            }
            if (option == 1) {
                boolean confirm = AssTeamUI.confirmAllStudentRemoval();
                if (!confirm) {
                    AssTeamUI.displayAllStudentRemovalCancelled();
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    return;
                }
                for (int i = 0; i < assignmentTeams.getSize(); i++) {
                    AssignmentTeam team = assignmentTeams.getPosition(i);
                    team.getMembers().reset();
                    team.setLeader(null);
                }
                AssTeamUI.displayAllStudentRemovalSuccess();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            }
            if (option == 2) {
                int choice = AssTeamUI.displayAssignmentTeamDetails(assignmentTeams, getTotalNumberStudentsHaveTeam());
                if (choice == -1) {
                    Utility.clearScreen();
                    return;
                }
                int optionToRemove = AssTeamUI.displayMethodToRemoveStudentFromTeam();
                if (optionToRemove == 0) {
                    Utility.clearScreen();
                    return;
                }
                if (optionToRemove == 1) {
                    AssignmentTeam teamToRemoveFrom = assignmentTeams.getPosition(choice);
                    ListInterface<Student> studentsToRemove = teamToRemoveFrom.getMembers();
                    AssTeamUI.displayStudentToRemove(studentsToRemove);
                    boolean confirm = AssTeamUI.confirmAllStudentRemoval();
                    if (confirm) {
                        teamToRemoveFrom.getMembers().reset();
                        teamToRemoveFrom.setLeader(null);
                        AssTeamUI.displayAllStudentRemovalSuccess();
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    } else {
                        AssTeamUI.displayAllStudentRemovalCancelled();
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    }
                }
                if (optionToRemove == 2) {
                    while (true) {
                        AssignmentTeam teamToRemoveFrom = assignmentTeams.getPosition(choice);
                        ListInterface<Student> studentsToRemove = teamToRemoveFrom.getMembers();

                        int studentChoice = AssTeamUI.selectStudentToRemove(listSelectedStudents(studentsToRemove), getSelectedStudentListSize(studentsToRemove));
                        if (studentChoice == -1) {
                            return;
                        }
                        Student studentToRemove = studentsToRemove.getPosition(studentChoice);
                        AssTeamUI.displayStudentDetails(studentToRemove);
                        boolean confirm = AssTeamUI.confirmStudentRemoval();
                        if (confirm) {
                            teamToRemoveFrom.removeMember(studentToRemove);
                            if (teamToRemoveFrom.getMembers().getSize() == 0) {
                                teamToRemoveFrom.setLeader(null);
                            }
                            AssTeamUI.displayStudentRemovalSuccess(studentToRemove, teamToRemoveFrom);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        } else {
                            AssTeamUI.displayStudentRemovalCancelled(studentToRemove, teamToRemoveFrom);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        if (teamToRemoveFrom.getMembers().isEmpty()) {
                            AssTeamUI.displayTeamEmpty(teamToRemoveFrom);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            break;
                        }
                        boolean removeAnother = AssTeamUI.removeAnotherStudent(teamToRemoveFrom);
                        if (!removeAnother) {
                            break;
                        }
                    }
                    Utility.clearScreen();
                    break;
                }
                boolean removeAnotherTeam = AssTeamUI.removeStudentFromOthersTeam();
                if (!removeAnotherTeam) {
                    break;
                }

            }
        }
    }

    public void mergeAssignmentTeams() {
        Utility.clearScreen();
        if (assignmentTeams.isEmpty()) {
            AssTeamUI.displayNoTeam();
            Utility.pressEnterToContinue();
            return;
        }
        if (assignmentTeams.getSize() == 1) {
            AssTeamUI.displaySingleTeam();
            Utility.pressEnterToContinue();
            return;
        }
        //check all team full
        boolean teamFull = true;
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getMembers().getSize() < team.getMaxTeamSize()) {
                teamFull = false;
                break;
            }
        }
        if (teamFull) {
            AssTeamUI.displayAllTeamFull();
            Utility.pressEnterToContinue();
            return;
        }
        int choice = AssTeamUI.selectMergeOption();
        if (choice == 0) {
            return;
        }
        if (choice == 1) {
            mergeTeamsAutomatically();
        } else {
            mergeTeamsManually();
        }
    }

    public void mergeTeamsAutomatically() {
        ListInterface<AssignmentTeam> mergedTeams = new DoublyCircularLinkedList<>();
        ListInterface<AssignmentTeam> deleteTeams = new DoublyCircularLinkedList<>();
        ListInterface<AssignmentTeam> sortedTeams = sortTeamByStudentSize(assignmentTeams);

        for (int i = 0; i < sortedTeams.getSize(); i++) {
            AssignmentTeam team = sortedTeams.getPosition(i);
            if (team.getMembers().getSize() == team.getMaxTeamSize()) {
                Utility.clearScreen();
                continue;
            }
            for (int j = sortedTeams.getSize() - 1; j > i; j--) {
                AssignmentTeam teamToMerge = sortedTeams.getPosition(j);
                if (teamToMerge.getMembers().getSize() == teamToMerge.getMaxTeamSize()) {
                    Utility.clearScreen();
                    continue;
                }
                AssignmentTeam mergedTeam = mergeTeams(team, teamToMerge);
                mergedTeams.add(mergedTeam);
                sortedTeams.remove(team);
                sortedTeams.remove(teamToMerge);
                deleteTeams.add(team);
                deleteTeams.add(teamToMerge);
                Utility.clearScreen();
                break;
            }
        }

        AssTeamUI.displayAllStudentsUnderTeams(mergedTeams);
        boolean confirmed = AssTeamUI.confirmMergeTeams();
        if (confirmed) {
            for (int i = 0; i < mergedTeams.getSize(); i++) {
                AssignmentTeam mergedTeam = mergedTeams.getPosition(i);
                assignmentTeams.add(mergedTeam);
            }
            for (int i = 0; i < deleteTeams.getSize(); i++) {
                AssignmentTeam team = deleteTeams.getPosition(i);
                assignmentTeams.remove(team);
            }
        }
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void mergeTeamsManually() {
        while (true) {
            AssTeamUI.displayAssignmentTeamsDetails(assignmentTeams);
            AssTeamUI.displayBack();
            int[] choice = AssTeamUI.selectTwoTeamsToMerge(assignmentTeams.getSize());
            AssignmentTeam team1 = assignmentTeams.getPosition(choice[0]);
            AssignmentTeam team2 = assignmentTeams.getPosition(choice[1]);
            if (choice[0] == -1 || choice[1] == -1) {
                Utility.clearScreen();
                return;
            }
            if (team1.getMembers() == null) {
                AssTeamUI.displayTeamEmpty(team1);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                continue;
            }
            if (team2.getMembers() == null) {
                AssTeamUI.displayTeamEmpty(team2);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                continue;
            }
            if (team1.getMembers().getSize() == team1.getMaxTeamSize() && team2.getMembers().getSize() == team2.getMaxTeamSize()) {
                AssTeamUI.displayTeamFull(team1);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                continue;
            }
            if (team1.getMembers().getSize() + team2.getMembers().getSize() > team1.getMaxTeamSize() && team1.getMembers().getSize() + team2.getMembers().getSize() > team2.getMaxTeamSize()) {
                AssTeamUI.displayTeamExceedMaxSize(team1, team2);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                continue;
            }
            AssignmentTeam mergedTeam = mergeTeams(team1, team2);
            AssTeamUI.displayStudentsUnderTeam(mergedTeam);
            boolean confirm = AssTeamUI.confirmMergeTeam();
            if (confirm) {
                assignmentTeams.add(mergedTeam);
                assignmentTeams.remove(team1);
                assignmentTeams.remove(team2);
                AssTeamUI.displayMergeTeamsSuccess(team1, team2, mergedTeam);
            } else {
                AssTeamUI.displayMergeTeamsCancelled(team1, team2);

            }
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            break;
        }

    }

    private AssignmentTeam mergeTeams(AssignmentTeam team1, AssignmentTeam team2) {
        int totalSize = team1.getMembers().getSize() + team2.getMembers().getSize();
        ListInterface<Student> mergedMembers = new DoublyCircularLinkedList<>();

        AssignmentTeam mergedTeam;
        if ((team1.getMaxTeamSize() - totalSize >= team2.getMaxTeamSize() - totalSize || team1.getMaxTeamSize() - totalSize < 0) && team2.getMaxTeamSize() - totalSize >= 0) {
            mergedTeam = new AssignmentTeam(team2.getTeamId(), team2.getTeamName(), team2.getMinTeamSize(), team2.getMaxTeamSize(), mergedMembers, team2.getGrade());
            for (int i = 0; i < team2.getMembers().getSize(); i++) {
                Student student = team2.getMembers().getPosition(i);
                mergedTeam.addMember(student);
            }
            for (int i = 0; i < team1.getMembers().getSize(); i++) {
                Student student = team1.getMembers().getPosition(i);
                mergedTeam.addMember(student);
            }
        } else {
            mergedTeam = new AssignmentTeam(team1.getTeamId(), team1.getTeamName(), team1.getMinTeamSize(), team1.getMaxTeamSize(), mergedMembers, team1.getGrade());
            for (int i = 0; i < team1.getMembers().getSize(); i++) {
                Student student = team1.getMembers().getPosition(i);
                mergedTeam.addMember(student);
            }
            for (int i = 0; i < team2.getMembers().getSize(); i++) {
                Student student = team2.getMembers().getPosition(i);
                mergedTeam.addMember(student);
            }
        }

        return mergedTeam;
    }

    public void listAssignmentTeams() {
        Utility.clearScreen();
        if (assignmentTeams.isEmpty()) {
            AssTeamUI.displayNoTeam();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }
        int choice = AssTeamUI.selectListTeamMethod();

        if (choice == 0) {
            Utility.clearScreen();
            return;
        } else if (choice == 1) {
            selectSortOption(assignmentTeams);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else if (choice == 2) {
            int filterOption = AssTeamUI.selectFilterOption();
            if (filterOption == 0) {
                Utility.clearScreen();
                return;
            }
            if (filterOption == 1) {
                //show the teams that are full
                ListInterface<AssignmentTeam> fullTeams = new DoublyCircularLinkedList<>();
                for (int i = 0; i < assignmentTeams.getSize(); i++) {
                    AssignmentTeam team = assignmentTeams.getPosition(i);
                    if (team.getMembers().getSize() == team.getMaxTeamSize()) {
                        fullTeams.add(team);
                    }
                }
                selectSortOption(fullTeams);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            } else if (filterOption == 2) {
                //show the teams that still has available slots
                ListInterface<AssignmentTeam> availableTeams = new DoublyCircularLinkedList<>();
                for (int i = 0; i < assignmentTeams.getSize(); i++) {
                    AssignmentTeam team = assignmentTeams.getPosition(i);
                    if (team.getMembers().getSize() < team.getMaxTeamSize()) {
                        availableTeams.add(team);
                    }
                }
                selectSortOption(availableTeams);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            } else if (filterOption == 3) {
                ListInterface<AssignmentTeam> ungradedTeams = new DoublyCircularLinkedList<>();
                for (int i = 0; i < assignmentTeams.getSize(); i++) {
                    AssignmentTeam team = assignmentTeams.getPosition(i);
                    if (team.getGrade().equalsIgnoreCase("N/A")) {
                        ungradedTeams.add(team);
                    }
                }
                selectSortOption(ungradedTeams);
                AssTeamUI.displayAssignmentTeamsDetails(ungradedTeams);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }

        }
    }

    public void selectSortOption(ListInterface<AssignmentTeam> assignmentTeams) {
        int option = AssTeamUI.selectSortOption();
        if (option == 0) {
            return;
        }
        if (option == 1) {
            ListInterface<AssignmentTeam> sortedTeams = sortTeamById(assignmentTeams);
            AssTeamUI.displayAssignmentTeamsDetails(sortedTeams);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else if (option == 2) {
            ListInterface<AssignmentTeam> sortedTeams = sortTeamByName(assignmentTeams);
            AssTeamUI.displayAssignmentTeamsDetails(sortedTeams);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else if (option == 3) {
            ListInterface<AssignmentTeam> sortedTeams = sortTeamByTeamSize(assignmentTeams);
            AssTeamUI.displayAssignmentTeamsDetails(sortedTeams);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else if (option == 4) {
            ListInterface<AssignmentTeam> sortedTeams = sortTeamByAvailableSlots(assignmentTeams);
            AssTeamUI.displayAssignmentTeamsDetails(sortedTeams);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else if (option == 5) {
            ListInterface<AssignmentTeam> sortedTeams = sortTeamsByGrade(assignmentTeams);
            AssTeamUI.displayAssignmentTeamsDetails(sortedTeams);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }
    }

    // Method to list students under an assignment team
    public void listStudentsUnderAssignmentTeam() {
        Utility.clearScreen();
        while (true) {
            boolean studentExist = false;
            for (int i = 0; i < assignmentTeams.getSize(); i++) {
                AssignmentTeam team = assignmentTeams.getPosition(i);
                if (!team.getMembers().isEmpty()) {
                    studentExist = true;
                    break;
                }
            }
            if (!studentExist) {
                AssTeamUI.displayNoStudentsUnderTeam();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                return;
            }
            int option = AssTeamUI.displayMethodToViewStudents();
            if (option == 0) {
                Utility.clearScreen();
                return;
            }
            if (option == 1) {
                AssTeamUI.displayAllStudentsUnderTeams(assignmentTeams);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                return;
            }
            if (option == 2) {
                while (true) {
                    int choice = AssTeamUI.selectAssignmentTeam(assignmentTeams, getAssignmentTeamListSize(), "view students under team");
                    if (choice == -1) {
                        break;
                    }
                    AssignmentTeam teamToView = assignmentTeams.getPosition(choice);
                    AssTeamUI.displayStudentsUnderTeam(teamToView);
                    boolean viewAnother = AssTeamUI.viewAnotherTeam();
                    if (!viewAnother) {
                        break;
                    }
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
                Utility.clearScreen();
                return;
            }
        }
    }

    public ListInterface<AssignmentTeam> sortTeams(Comparator<AssignmentTeam> comparator, ListInterface<AssignmentTeam> assignmentTeams) {
        assignmentTeams.bubbleSort(comparator);
        return assignmentTeams;
    }

    private static class SortByGrade implements Comparator<AssignmentTeam> {

        @Override
        public int compare(AssignmentTeam o1, AssignmentTeam o2) {
            String grade1 = o1.getGrade();
            String grade2 = o2.getGrade();

            if (grade1.equals("N/A")) {
                if (grade2.equals("N/A")) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (grade2.equals("N/A")) {
                return 1;
            }

            if (grade1.equals("100")) {
                if (grade2.equals("100")) {
                    return 0;
                } else {
                    return 1;
                }
            } else if (grade2.equals("100")) {
                return -1;
            }

            return grade1.compareTo(grade2);
        }
    }

    public ListInterface<AssignmentTeam> sortTeamsByGrade(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> copiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            copiedTeams.add(assignmentTeams.getPosition(i));
        }
        return sortTeams(new SortByGrade(), copiedTeams);
    }

    private static class SortByTeamStudentSize implements Comparator<AssignmentTeam> {

        @Override
        public int compare(AssignmentTeam o1, AssignmentTeam o2) {
            return o1.getMembers().getSize() - o2.getMembers().getSize();
        }

    }

    public ListInterface<AssignmentTeam> sortTeamByStudentSize(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> copiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            copiedTeams.add(assignmentTeams.getPosition(i));
        }
        return sortTeams(new SortByTeamStudentSize(), copiedTeams);
    }

    private static class SortByTeamName implements Comparator<AssignmentTeam> {

        @Override
        public int compare(AssignmentTeam o1, AssignmentTeam o2) {
            return o1.getTeamName().compareTo(o2.getTeamName());
        }

    }

    public ListInterface<AssignmentTeam> sortTeamByTeamSize(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> copiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            copiedTeams.add(assignmentTeams.getPosition(i));
        }
        return sortTeams(new SortByTeamSize(), copiedTeams);
    }

    private static class SortByTeamSize implements Comparator<AssignmentTeam> {

        @Override
        public int compare(AssignmentTeam o1, AssignmentTeam o2) {
            return o1.getMaxTeamSize() - o2.getMaxTeamSize();
        }

    }

    public ListInterface<AssignmentTeam> sortTeamById(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> copiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            copiedTeams.add(assignmentTeams.getPosition(i));
        }
        return sortTeams(new SortByTeamId(), copiedTeams);
    }

    private static class SortByTeamId implements Comparator<AssignmentTeam> {

        @Override
        public int compare(AssignmentTeam o1, AssignmentTeam o2) {
            return o1.getTeamId().compareTo(o2.getTeamId());
        }

    }

    public ListInterface<AssignmentTeam> sortTeamByAvailableSlots(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> copiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            copiedTeams.add(assignmentTeams.getPosition(i));
        }
        return sortTeams(new SortByTeamAvailableSlots(), copiedTeams);
    }

    private static class SortByTeamAvailableSlots implements Comparator<AssignmentTeam> {

        @Override
        public int compare(AssignmentTeam o1, AssignmentTeam o2) {
            return o1.getMaxTeamSize() - o1.getMembers().getSize() - (o2.getMaxTeamSize() - o2.getMembers().getSize());
        }

    }

    public ListInterface<AssignmentTeam> sortTeamByName(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> copiedTeams = new DoublyCircularLinkedList<>();
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            copiedTeams.add(assignmentTeams.getPosition(i));
        }
        return sortTeams(new SortByTeamName(), copiedTeams);
    }

    public void generateSummaryReport() {
        Utility.clearScreen();
        if (assignmentTeams.isEmpty()) {
            AssTeamUI.displayNoTeam();
            Utility.pressEnterToContinue();
            return;
        }
        int choice = AssTeamUI.selectReportOption();
        if (choice == 0) {
            return;
        }
        if (choice == 1) {
            generateGradeAnalysisReport();
        } else if (choice == 2) {
            teamAnalysisReport();
        }
    }

    public void generateGradeAnalysisReport() {
        Utility.clearScreen();
        AssTeamUI.reportHeader("Grade Analysis Report");
        ListInterface<AssignmentTeam> sortedTeams = sortTeamsByGrade(assignmentTeams);
        AssTeamUI.displayGrade(sortedTeams);

        char[] grades = new char[sortedTeams.getSize()];
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        int[] numericGrades = new int[sortedTeams.getSize()];

        for (int i = 0; i < sortedTeams.getSize(); i++) {
            AssignmentTeam team = sortedTeams.getPosition(i);
            if (team.getGrade().equals("N/A")) {
                grades[i] = 'F';
                numericGrades[i] = 0;
                countF++;
            } else {
                int grade = Integer.parseInt(team.getGrade());
                numericGrades[i] = grade;
                grades[i] = convertGrade(grade);
                switch (grades[i]) {
                    case 'A' ->
                        countA++;
                    case 'B' ->
                        countB++;
                    case 'C' ->
                        countC++;
                    case 'D' ->
                        countD++;
                    case 'F' ->
                        countF++;
                }
            }
        }

        int highest = findHighest(numericGrades);
        int lowest = findLowest(numericGrades);
        double mean = findMean(numericGrades);
        int mode = findMode(numericGrades);
        double median = findMedian(numericGrades);
        double variance = findVariance(numericGrades, mean);
        double standardDeviation = Math.sqrt(variance);

        AssTeamUI.displayCount(sortedTeams.getSize(), countA, countB, countC, countD, countF);

        AssTeamUI.displayDescriptiveAnalysisAndHistogram(lowest, highest, mean, median, mode, variance, standardDeviation, countA, countB, countC, countD, countF);

        gradePosition(sortedTeams);
        AssTeamUI.reportFooter();
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public char convertGrade(int grade) {
        if (grade >= 80) {
            return 'A';
        } else if (grade >= 70) {
            return 'B';
        } else if (grade >= 60) {
            return 'C';
        } else if (grade >= 50) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public int findHighest(int[] grades) {
        int highest = grades[0];
        for (int i = 1; i < grades.length; i++) {
            if (grades[i] > highest) {
                highest = grades[i];
            }
        }
        return highest;
    }

    public int findLowest(int[] grades) {
        int lowest = grades[0];
        for (int i = 1; i < grades.length; i++) {
            if (grades[i] < lowest) {
                lowest = grades[i];
            }
        }
        return lowest;
    }

    public double findMean(int[] grades) {
        double sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    public int findMode(int[] grades) {
        int mode = grades[0];
        int maxCount = 0;
        for (int i = 0; i < grades.length; i++) {
            int count = 0;
            for (int grade : grades) {
                if (grade == grades[i]) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mode = grades[i];
            }
        }
        return mode;
    }

    public double findMedian(int[] grades) {
        if (grades.length % 2 == 0) {
            return (grades[grades.length / 2] + grades[grades.length / 2 - 1]) / 2.0;
        } else {
            return grades[grades.length / 2];
        }
    }

    public double findVariance(int[] grades, double mean) {
        double sum = 0;
        for (int grade : grades) {
            sum += Math.pow(grade - mean, 2);
        }
        return sum / grades.length;
    }

    public void gradePosition(ListInterface<AssignmentTeam> assignmentTeams) {
        ListInterface<AssignmentTeam> highestTeams = new DoublyCircularLinkedList<>();
        ListInterface<AssignmentTeam> lowestTeams = new DoublyCircularLinkedList<>();
        ListInterface<AssignmentTeam> aboveAverageTeams = new DoublyCircularLinkedList<>();
        ListInterface<AssignmentTeam> belowAverageTeams = new DoublyCircularLinkedList<>();
        ListInterface<AssignmentTeam> failTeams = new DoublyCircularLinkedList<>();
        int[] grades = new int[assignmentTeams.getSize()];
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getGrade().equals("N/A")) {
                grades[i] = 0;
            } else {
                grades[i] = Integer.parseInt(team.getGrade());
            }
        }

        int highest = findHighest(grades);
        int lowest = findLowest(grades);
        double mean = findMean(grades);
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            int grade;
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getGrade().equals("N/A")) {
                grade = 0;
            } else {
                grade = Integer.parseInt(team.getGrade());
            }
            if (grade == highest) {
                highestTeams.add(team);
            }
            if (grade == lowest) {
                lowestTeams.add(team);
            }
            if (grade > mean) {
                aboveAverageTeams.add(team);
            }
            if (grade < mean) {
                belowAverageTeams.add(team);
            }
            if (grade < 50) {
                failTeams.add(team);
            }

        }
        AssTeamUI.displayGradePosition(highestTeams, lowestTeams, aboveAverageTeams, belowAverageTeams, failTeams);
    }

    public void teamAnalysisReport() {
        Utility.clearScreen();
        AssTeamUI.reportHeader("Team Analysis Report");
        ListInterface<AssignmentTeam> sortedTeams = sortTeamByStudentSize(assignmentTeams);
        int totalTeam = sortedTeams.getSize();
        int totalStudentWithTeam = 0;
        int[] teamSize = new int[sortedTeams.getSize()];
        for (int i = 0; i < sortedTeams.getSize(); i++) {
            AssignmentTeam team = sortedTeams.getPosition(i);
            totalStudentWithTeam += team.getMembers().getSize();
            teamSize[i] = team.getMembers().getSize();
        }
        AssTeamUI.displayTeamAnalysis(sortedTeams, totalStudentWithTeam, students.getSize());
        double averageTeamSize = (double) totalStudentWithTeam / totalTeam;
        int lowestTeamSize = findLowest(teamSize);
        int highestTeamSize = findHighest(teamSize);
        int[] teamSizeCount = new int[highestTeamSize - lowestTeamSize + 1];
        for (int i = 0; i < teamSize.length; i++) {
            teamSizeCount[teamSize[i] - lowestTeamSize]++;
        }
        AssTeamUI.displayTeamSizeAnalysis(sortedTeams, students, lowestTeamSize, highestTeamSize, averageTeamSize, teamSizeCount);
        AssTeamUI.reportFooter();
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public static void main(String[] args) {
        AssignmentTeamManagement assignmentTeamManagement = new AssignmentTeamManagement();
        assignmentTeamManagement.runAssignmentTeamManagement();
    }

}
