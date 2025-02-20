/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 * @author LAI GUAN HONG
 */

import adt.ListInterface;
import entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AssignmentTeamManagementUI {

    Scanner sc = new Scanner(System.in);

    public int displayCourseMenu(String courseList, int size) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(57) + "+");
                System.out.println("|              Assignment Team Management System          |");
                System.out.println("|                       (Select Course)                   |");
                System.out.println("+" + "=".repeat(57) + "+");
                if (size == 0) {
                    System.out.println("|                    (No Course Available)               |");
                    System.out.println("+" + "=".repeat(57) + "+");
                } else {
                    System.out.print("| No  | Course Code | Course Name");
                    System.out.print(" ".repeat(Math.max(0, 46 - "Course Code".length() - "Course Name".length())));
                    System.out.print(" |\n");
                    System.out.println("=".repeat(59));
                    System.out.println(courseList);
                }
                System.out.println("| 0. Back                                                 |");
                System.out.println("+" + "=".repeat(57) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (size == 0 && choice != 0) {
                    System.out.println("Invalid choice. Please enter 0 to exit.");
                    choice = -1;
                } else if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public int displayProgrammeMenu(Course selectedCourse, String programmeList, int size) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(101) + "+");
                System.out.println("|                                  Assignment Team Management System                                  |");
                String courseInfo = "Course: " + selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName();
                int totalSpaces = 101 - courseInfo.length();
                int leftSpaces = totalSpaces / 2;
                int rightSpaces = totalSpaces - leftSpaces;
                System.out.println("|" + " ".repeat(leftSpaces) + courseInfo + " ".repeat(rightSpaces) + "|");
                System.out.println("|                                        (Select Programme)                                           |");
                System.out.println("+" + "=".repeat(101) + "+");
                //show programme list
                if (size == 0) {
                    System.out.println("|                                    (No Programme Available)                                        |");
                    System.out.println("+" + "=".repeat(101) + "+");
                } else {
                    System.out.print("| No | Programme Code | Programme Name");
                    System.out.print(" ".repeat(Math.max(0, 91 - "Programme Code".length() - "Programme Name".length())));
                    System.out.print(" |\n");
                    System.out.println("=".repeat(103));
                    System.out.println(programmeList);
                }
                System.out.println("| 0. Back                                                                                             |");
                System.out.println("+" + "=".repeat(101) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (size == 0 && choice != 0) {
                    System.out.println("Invalid choice. Please enter 0 to back.");
                    choice = -1;
                } else if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public int displayTutorialGroupMenu(Programme selectedProgramme, String tutorialGroupList, int size) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(98) + "+");
                System.out.println("|                              Assignment Team Management System                                   |");
                String programmeInfo = "Programme: " + selectedProgramme.getProgrammeCode() + " - " + selectedProgramme.getProgrammeName();
                int totalSpaces = 98 - programmeInfo.length();
                int leftSpaces = totalSpaces / 2;
                int rightSpaces = totalSpaces - leftSpaces;
                System.out.println("|" + " ".repeat(leftSpaces) + programmeInfo + " ".repeat(rightSpaces) + "|");
                System.out.println("|                                  (Select Tutorial Group)                                         |");
                System.out.println("+" + "=".repeat(98) + "+");
                if (size == 0) {
                    System.out.println("|                               (No Tutorial Group Available)                                     |");
                    System.out.println("+" + "=".repeat(98) + "+");
                } else {
                    System.out.print("| No | Tutorial Group | Year Intake | Number of Students | Number of Teams ");
                    System.out.print(" ".repeat(Math.max(0, 81 - "Tutorial Group".length() - "Year Intake".length() - "Number of Students".length() - "Number of Teams".length())));
                    System.out.print(" |\n");
                    System.out.println("=".repeat(100));
                    System.out.println(tutorialGroupList);
                }
                System.out.println("| 0. Back                                                                                          |");
                System.out.println("+" + "=".repeat(98) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (size == 0 && choice != 0) {
                    System.out.println("Invalid choice. Please enter 0 to back.");
                    choice = -1;
                } else if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public int displaySystemMenu(TutorialGroup selectedTutorialGroup) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(88) + "+");
                System.out.println("|                            Assignment Team Management System                           |");
                String tutorialGroupInfo = "Tutorial Group: " + selectedTutorialGroup.getTutorialGroupId();
                int totalSpaces = 88 - tutorialGroupInfo.length();
                int leftSpaces = totalSpaces / 2;
                int rightSpaces = totalSpaces - leftSpaces;
                System.out.println("|" + " ".repeat(leftSpaces) + tutorialGroupInfo + " ".repeat(rightSpaces) + "|");
                System.out.println("+" + "=".repeat(88) + "+");
                System.out.println("| 1. Create Assignment Team                |  6. Merge Assignment Teams                  |");
                System.out.println("| 2. Remove Assignment Team                |  7. List Assignment Teams                   |");
                System.out.println("| 3. Amend Assignment Team Details         |  8. List Students under Assignment Team     |");
                System.out.println("| 4. Add Students to Assignment Team       |  9. Generate Summary Reports                |");
                System.out.println("| 5. Remove Students from Assignment Team  |  0. Back                                    |");
                System.out.println("+" + "=".repeat(88) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 9) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 9.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 9 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public String inputAssignmentDetails() {
        System.out.println("Enter the following details to create an assignment team:");
        return promptTeamName();
    }

    public String promptTeamName() {
        System.out.print("Enter team name: ");
        return sc.nextLine().trim();
    }

    public void displayTeamDetails(AssignmentTeam team) {
        System.out.println("+" + "=".repeat(48) + "+");
        System.out.println("|              Assignment Team Details           |");
        System.out.println("+" + "=".repeat(48) + "+");

        // Display team details with dynamic spacing
        System.out.println("| Team ID:         | " + team.getTeamId() + " ".repeat(Math.max(0, 28 - team.getTeamId().length())) + "|");
        System.out.println("| Team Name:       | " + team.getTeamName() + " ".repeat(Math.max(0, 28 - team.getTeamName().length())) + "|");
        System.out.println("| Min Team Size:   | " + team.getMinTeamSize() + " ".repeat(Math.max(0, 28 - String.valueOf(team.getMinTeamSize()).length())) + "|");
        System.out.println("| Max Team Size:   | " + team.getMaxTeamSize() + " ".repeat(Math.max(0, 28 - String.valueOf(team.getMaxTeamSize()).length())) + "|");
        System.out.println("| Team Grade:      | " + team.getGrade() + " ".repeat(Math.max(0, 28 - team.getGrade().length())) + "|");
        if (team.getLeader() == null) {
            System.out.println("| Team Leader:     | " + "N/A" + " ".repeat(Math.max(0, 28 - "N/A".length())) + "|");
        } else {
            System.out.println("| Team Leader:     | " + team.getLeader().getStudentName() + " ".repeat(Math.max(0, 28 - team.getLeader().getStudentName().length())) + "|");
        }

        System.out.println("+" + "=".repeat(48) + "+");
    }

    public void displayDuplicateTeamName(String teamName) {
        System.out.println("Team " + teamName + " already exists. Please enter a different team name.");
    }

    public boolean confirmTeamCreation() {
        System.out.print("Are you sure you want to create this assignment team? (Y/N):");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayTeamCreationSuccess(String teamName) {
        System.out.println("Assignment team created successfully for team: " + teamName);
    }

    public void displayTeamCreationFailure(String teamId) {
        System.out.println("Assignment team creation failed for: " + teamId);
    }

    public void displayTeamCreationCancelled(String teamName) {
        System.out.println("Assignment team creation cancelled for team: " + teamName);
    }

    public void displayAutoTeamCreationSuccess() {
        System.out.println("Auto assignment team creation successful.");
    }

    public void displayAutoTeamCreationCancelled() {
        System.out.println("Auto assignment team creation cancelled.");
    }


    public boolean createAnotherTeam() {
        System.out.print("Do you want to create another assignment team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public int selectAssignmentTeam(ListInterface<AssignmentTeam> assignmentTeams, int size, String function) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(106) + "+");
                System.out.println("|                                         List of Assignment Teams                                         |");
                System.out.println("+" + "=".repeat(106) + "+");
                System.out.println("| No. | Team ID             | Team Name                      | Team Size       | Occupied Slots    | Grade |");
                System.out.println("+" + "=".repeat(106) + "+");
                if (assignmentTeams.isEmpty()) {
                    System.out.println("| No assignment teams available.                                                                           |");
                }
                for (int i = 0; i < assignmentTeams.getSize(); i++) {
                    AssignmentTeam team = assignmentTeams.getPosition(i);
                    if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                        System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s| %-6s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize(), team.getGrade());
                    } else {
                        System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s| %-6s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize(), team.getGrade());
                    }
                }
                System.out.println("+" + "=".repeat(106) + "+");
                System.out.println("| 0. Back                                                                                                  |");
                System.out.println("+" + "=".repeat(106) + "+");
                System.out.print("Enter your choice to " + function + " : ");
                choice = sc.nextInt();
                sc.nextLine();
                if (size == 0 && choice != 0) {
                    System.out.println("Invalid choice. Please enter 0 to back.");
                    choice = -1;
                } else if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public boolean confirmTeamRemoval() {
        System.out.print("Do you want to remove this team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayTeamRemovalSuccess(AssignmentTeam team) {
        System.out.println("Assignment team removed successfully: " + team.getTeamId() + " " + team.getTeamName());
    }

    public void displayTeamRemovalCancelled(AssignmentTeam team) {
        System.out.println("Assignment team removal cancelled for team: " + team.getTeamId() + " " + team.getTeamName());
    }

    public boolean removeAnotherTeam() {
        System.out.print("Do you want to remove another assignment team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public int selectAmendOption(AssignmentTeam team) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(48) + "+");
                System.out.println("|              Assignment Team Details           |");
                System.out.println("+" + "=".repeat(48) + "+");

                // Display team details with dynamic spacing
                System.out.println("| Team ID:         | " + team.getTeamId() + " ".repeat(Math.max(0, 28 - team.getTeamId().length())) + "|");
                System.out.println("| Team Name:       | " + team.getTeamName() + " ".repeat(Math.max(0, 28 - team.getTeamName().length())) + "|");
                System.out.println("| Min Team Size:   | " + team.getMinTeamSize() + " ".repeat(Math.max(0, 28 - String.valueOf(team.getMinTeamSize()).length())) + "|");
                System.out.println("| Max Team Size:   | " + team.getMaxTeamSize() + " ".repeat(Math.max(0, 28 - String.valueOf(team.getMaxTeamSize()).length())) + "|");
                System.out.println("| Team Grade:      | " + team.getGrade() + " ".repeat(Math.max(0, 28 - team.getGrade().length())) + "|");
                if (team.getLeader() == null) {
                    System.out.println("| Team Leader:     | " + "N/A" + " ".repeat(Math.max(0, 28 - "N/A".length())) + "|");
                } else {
                    System.out.println("| Team Leader:     | " + team.getLeader().getStudentName() + " ".repeat(Math.max(0, 28 - team.getLeader().getStudentName().length())) + "|");
                }

                System.out.println("+" + "=".repeat(48) + "+");
                System.out.println("| 1. Amend Team Name                             |");
                System.out.println("| 2. Amend Team Size                             |");
                System.out.println("| 3. Amend Team Grade                            |");
                System.out.println("| 4. Amend Team Leader                           |");
                System.out.println("| 5. Swap Team Member                            |");
                System.out.println("| 0. Back                                        |");
                System.out.println("+" + "=".repeat(48) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 5 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public String promptNewTeamName(String teamName) {
        String newTeamName = "";
        while (newTeamName.isEmpty() || newTeamName.equals(teamName)) {
            System.out.print("Enter new team name: ");
            newTeamName = sc.nextLine().trim();
            if (newTeamName.equals(teamName)) {
                System.out.println("New team name cannot be the same as the old team name: " + teamName);
            } else if (newTeamName.isEmpty()) {
                System.out.println("New team name cannot be empty.");
            }
        }

        return newTeamName;
    }

    public int[] promptNewMinMaxTeamSize(int minTeamSize, int maxTeamSize, int memberSize) {
        int[] newMinMaxTeamSize = new int[2];
        while (true) {
            System.out.print("Enter new minimum team size: ");
            newMinMaxTeamSize[0] = sc.nextInt();
            System.out.print("Enter new maximum team size: ");
            newMinMaxTeamSize[1] = sc.nextInt();
            if (newMinMaxTeamSize[0] <= 0 || newMinMaxTeamSize[1] <= 0) {
                System.out.println("Team size must be greater than 0.");
                continue;
            } else if (newMinMaxTeamSize[0] > newMinMaxTeamSize[1]) {
                System.out.println("Maximum team size must be greater or equal to minimum team size");
                continue;
            } else if (newMinMaxTeamSize[0] == minTeamSize && newMinMaxTeamSize[1] == maxTeamSize) {
                System.out.println("New team size cannot be the same as the old team size: " + minTeamSize + " - " + maxTeamSize);
                continue;
            } else if (newMinMaxTeamSize[0] < memberSize) {
                System.out.println("New minimum team size cannot be less than the number of members in the team: " + memberSize);
                continue;
            }
            sc.nextLine();
            break;
        }
        return newMinMaxTeamSize;
    }

    public boolean confirmTeamAmendment() {
        System.out.print("Do you want to amend this team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayTeamAmendmentSuccess(AssignmentTeam team) {
        System.out.println("Assignment team amended successfully for: " + team.getTeamId() + " " + team.getTeamName());
    }

    public void displayTeamAmendmentCancelled(AssignmentTeam team) {
        System.out.println("Assignment team amendment cancelled for: " + team.getTeamId() + " " + team.getTeamName());
    }

    public boolean amendAnotherTeam() {
        System.out.print("Do you want to amend another assignment team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public boolean amendAnotherTeamDetails() {
        System.out.print("Do you want to amend another details? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }


    public int displayAssignmentTeamDetails(ListInterface<AssignmentTeam> assignmentTeams, String totalAvailableSlots) {
        System.out.println("+" + "=".repeat(98) + "+");
        System.out.println("|                          List of Assignment Teams and Available Slots                            |");
        System.out.println("+" + "=".repeat(98) + "+");
        System.out.println("| No. | Team ID       | Team Name                 | Team Size   | Occupied Slots | Available Slots |");
        System.out.println("+" + "=".repeat(98) + "+");

        if (assignmentTeams.isEmpty()) {
            System.out.println("| No assignment teams available.                                                                   |");
        }
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                System.out.printf("| %-4s| %-14s| %-26s| %-12s| %-15s| %-16s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + "/" + team.getMaxTeamSize(), team.getMaxTeamSize() - team.getMembers().getSize());
            } else {
                System.out.printf("| %-4s| %-14s| %-26s| %-12s| %-15s| %-16s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + "/" + team.getMaxTeamSize(), team.getMaxTeamSize() - team.getMembers().getSize());
            }
        }
        System.out.println("+" + "=".repeat(98) + "+");
        System.out.printf("| Total Students: %-80s |\n", totalAvailableSlots);
        System.out.println("+" + "=".repeat(98) + "+");
        System.out.println("| 0. Back                                                                                          |");
        System.out.println("+" + "=".repeat(98) + "+");
        int choice = -1;
        while (choice == -1) {
            System.out.print("Enter your choice: ");
            try {
                choice = sc.nextInt();
                if (choice < 0 || choice > assignmentTeams.getSize()) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + assignmentTeams.getSize() + ".");
                    choice = -1;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + assignmentTeams.getSize() + " (eg. 1).");
                sc.nextLine();

            }
        }

        return choice - 1;
    }

    public void displayNoAvailableSlots(AssignmentTeam team) {
        System.out.println("Assignment team " + team.getTeamId() + " " + team.getTeamName() + " has no available slots.");
    }

    public int selectStudentToAdd(String studentList, int size) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(81) + "+");
                System.out.println("|                     Select Student to Add to Assignment Team                   |");
                System.out.println("+" + "=".repeat(81) + "+");
                System.out.println("| No.  | Student ID  | Student Name             | Student Email         | Gender  | ");
                System.out.println("+" + "=".repeat(81) + "+");
                if (size == 0) {
                    System.out.println("| No students available.                                                         |");
                } else {
                    System.out.println(studentList);
                }
                System.out.println("| 0. Back                                                                         |");
                System.out.println("+" + "=".repeat(81) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public void displayStudentDetails(Student student) {
        System.out.println("+" + "=".repeat(40) + "+");
        System.out.println("|             Student Details            |");
        System.out.println("+" + "=".repeat(40) + "+");
        System.out.println("| Student ID: " + student.getStudentID() + " ".repeat(Math.max(0, 26 - student.getStudentID().length())) + " |");
        System.out.println("| Student Name: " + student.getStudentName() + " ".repeat(Math.max(0, 24 - student.getStudentName().length())) + " |");
        System.out.println("| Student Email: " + student.getStudentEmail() + " ".repeat(Math.max(0, 23 - student.getStudentEmail().length())) + " |");
        System.out.println("| Gender: " + student.getStudentGender() + " ".repeat(Math.max(0, 30 - student.getStudentGender().length())) + " |");
        System.out.println("+" + "=".repeat(40) + "+");
    }

    public boolean confirmStudentAddition() {
        System.out.print("Do you want to add this/these students to the team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayStudentAdditionSuccess(Student student, AssignmentTeam team) {
        System.out.println("Student " + student.getStudentID() + " added to team " + team.getTeamId() + " " + team.getTeamName() + " successfully.");
    }

    public void displayStudentAdditionCancelled(Student student, AssignmentTeam team) {
        System.out.println("Student " + student.getStudentID() + " addition to team " + team.getTeamId() + " " + team.getTeamName() + " cancelled.");
    }

    public void displayStudentAutoAdditionSuccess() {
        System.out.println("Student auto added to teams successfully.");
    }

    public void displayStudentAutoAdditionCancelled() {
        System.out.println("Student auto added to teams cancelled.");
    }

    public void displayTeamFull(AssignmentTeam team) {
        System.out.println("Team " + team.getTeamId() + " " + team.getTeamName() + " is already full.");
    }

    public boolean addAnotherStudent(AssignmentTeam team) {
        System.out.print("Do you want to add another student to the " + team.getTeamId() + " " + team.getTeamName() + " (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public boolean addStudentToOthersTeam() {
        System.out.print("Do you want to continue to add students to others assignment team (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public int selectStudentToRemove(String studentList, int size) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("|                 Select Student to Remove from Assignment Team                  |");
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("| No. | Student ID  | Student Name             | Student Email         | Gender  | ");
                System.out.println("+" + "=".repeat(80) + "+");
                if (size == 0) {
                    System.out.println("| No students available.                                                         |");
                } else {
                    System.out.println(studentList);
                }
                System.out.println("| 0. Back                                                                        |");
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public boolean confirmStudentRemoval() {
        System.out.print("Do you want to remove this student from the team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayStudentRemovalSuccess(Student student, AssignmentTeam team) {
        System.out.println("Student " + student.getStudentID() + " removed from team " + team.getTeamId() + " " + team.getTeamName() + " successfully.");
    }

    public void displayStudentRemovalCancelled(Student student, AssignmentTeam team) {
        System.out.println("Student " + student.getStudentID() + " removal from team " + team.getTeamId() + " " + team.getTeamName() + " cancelled.");
    }

    public void displayTeamEmpty(AssignmentTeam team) {
        System.out.println("Team " + team.getTeamId() + " " + team.getTeamName() + " is already empty.");
    }

    public boolean removeAnotherStudent(AssignmentTeam team) {
        System.out.print("Do you want to remove another student from the " + team.getTeamId() + " " + team.getTeamName() + " (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public boolean removeStudentFromOthersTeam() {
        System.out.print("Do you want to continue to remove students from others assignment team (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayAssignmentTeams(ListInterface<AssignmentTeam> assignmentTeams) {
        System.out.println("+" + "=".repeat(98) + "+");
        System.out.println("|                                    List of Assignment Teams                                      |");
        System.out.println("+" + "=".repeat(98) + "+");
        System.out.println("| No. | Team ID             | Team Name                      | Team Size       | Occupied Slots    |");
        System.out.println("+" + "=".repeat(98) + "+");
        if (assignmentTeams.isEmpty()) {
            System.out.println("| No assignment teams available.                                                                   |");
        }
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize());
            } else {
                System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize());
            }
        }
        System.out.println("+" + "=".repeat(98) + "+");
    }

    public void displayAssignmentTeamsDetails(ListInterface<AssignmentTeam> assignmentTeams) {
        System.out.println("+" + "=".repeat(106) + "+");
        System.out.println("|                                         List of Assignment Teams                                         |");
        System.out.println("+" + "=".repeat(106) + "+");
        System.out.println("| No. | Team ID             | Team Name                      | Team Size       | Occupied Slots    | Grade |");
        System.out.println("+" + "=".repeat(106) + "+");
        if (assignmentTeams.isEmpty()) {
            System.out.println("| No assignment teams available.                                                                           |");
        }
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s| %-6s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize(), team.getGrade());
            } else {
                System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s| %-6s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize(), team.getGrade());
            }
        }
        System.out.println("+" + "=".repeat(106) + "+");
    }

    public void displayBack() {
        System.out.println("| 0. Back                                                                                                  |");
        System.out.println("+" + "=".repeat(106) + "+");
    }

    public boolean viewAnotherTeam() {
        System.out.print("Do you want to view another assignment team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public boolean displayNoTeamNeeded(int size) {
        System.out.println("- " + size + " team(s) are already created. It is not necessary to create more teams.");
        System.out.print("Do you still want to continue to create more teams? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public boolean promptCreateMoreTeams() {
        System.out.print("Do you still want to continue to create more teams? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayTeamNeeded(int teamNeeded, int size) {
        System.out.println("- " + size + " team(s) are already created. " + "You are suggested to create " + teamNeeded + " more team(s).");
    }

    public void displayOverTeamNeeded(int teamNeeded, int size) {
        System.out.println("- " + size + " team(s) are already created. " + "It seems that you have created more teams than needed." + "You are suggested to remove or merge " + teamNeeded + " teams.");
    }


    public void displayInsufficientStudents(int totalStudents, int minTeamSize) {
        System.out.println("Suggestion:\n- There are " + totalStudents + " students available but the minimum team size is " + minTeamSize);
        System.out.println("- Please add more students to create teams or update the minimum team size");
    }

    public void teamSuggestionRemaining(int numTeams, int totalStudents, int bestTeamSize, int remainingStudents) {
        System.out.println("Suggestion:\n- Create " + (numTeams + 1) + " teams for " + totalStudents + " students");
        System.out.println("- " + numTeams + " teams each with " + bestTeamSize + " students and 1 team with " + remainingStudents + " students");
    }

    public void teamSuggestionExtra(int teamsWithoutExtraStudent, int numTeams, int totalStudents, int remainingStudents, int bestTeamSize) {
        System.out.println("Suggestion:\n- Create " + numTeams + " teams for " + totalStudents + " students");
        System.out.println("- " + remainingStudents + " teams with " + (bestTeamSize + 1) + " students and " + teamsWithoutExtraStudent + " teams with " + bestTeamSize + " students");
    }

    public void teamSuggestion(int numTeams, int totalStudents, int bestTeamSize) {
        System.out.println("Suggestion\n- Create " + numTeams + " teams for " + totalStudents + " students");
        System.out.println("- " + numTeams + " teams each with " + bestTeamSize + " students");
    }

    public void teamSuggest(int numTeams, int totalStudents) {
        System.out.println("Suggestion\n- Create " + numTeams + " teams for " + totalStudents + " students");
    }

    public int displayMethodToRemoveTeam() {
        // Display method to remove team menu, delete all, delete selected or back
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|              Remove Assignment Team Method               |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Remove All Assignment Teams                           |");
                System.out.println("| 2. Remove Selected Assignment Teams                      |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }


    public boolean confirmAllTeamRemoval() {
        System.out.print("Do you want to remove all assignment teams? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayAllTeamRemovalSuccess() {
        System.out.println("All assignment teams removed successfully.");
    }

    public void displayAllTeamRemovalCancelled() {
        System.out.println("All assignment team removal cancelled.");
    }

    public void displayNoTeamToRemove() {
        System.out.println("No assignment teams available to remove.");
    }

    public int displayMethodToCreateTeam() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|              Create Assignment Team Method               |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Automatically Create Assignment Teams                 |");
                System.out.println("| 2. Manually Create Assignment Teams                      |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public String promptNewTeamGrade(String teamGrade) {
        while (true) {
            System.out.print("Enter team grade (0 - 100): ");
            String grade = sc.nextLine().trim();
            if (grade.matches("^[0-9]{1,3}$") && Integer.parseInt(grade) >= 0 && Integer.parseInt(grade) <= 100) {
                return grade;
            } else if (grade.equals(teamGrade)) {
                System.out.println("New team grade cannot be the same as the old team grade: " + teamGrade);
            } else {
                System.out.println("Invalid input. Please enter a valid grade between 0 and 100.");
            }
        }
    }

    public int selectLeader(AssignmentTeam team) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(78) + "+");
                System.out.println("|                      Select Team Leader for Assignment Team                  |");
                System.out.println("+" + "=".repeat(78) + "+");
                System.out.println("| No. |    Student ID    |       Student Name       |    Student Email         |");
                System.out.println("+" + "=".repeat(78) + "+");
                if (team.getMembers().isEmpty()) {
                    System.out.println("| No students available.                                                     |");
                }
                for (int i = 0; i < team.getMembers().getSize(); i++) {
                    Student student = team.getMembers().getPosition(i);
                    System.out.printf("| %-4s| %-16s| %-25s| %-25s|\n", i + 1, student.getStudentID(), student.getStudentName(), student.getStudentEmail());
                }
                System.out.println("+" + "=".repeat(78) + "+");
                System.out.println("| 0. Back                                                                     |");
                System.out.println("+" + "=".repeat(78) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > team.getMembers().getSize()) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + team.getMembers().getSize() + ".");
                    choice = -1;
                } else if (choice == 1) {
                    System.out.println("Team Leader cannot be the same as the old team leader: " + team.getLeader().getStudentName());
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + team.getMembers().getSize() + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;
    }

    public int selectListTeamMethod() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|              List Assignment Team Method                 |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. List All Assignment Teams                             |");
                System.out.println("| 2. List Assignment Teams with Filter                     |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public int selectFilterOption() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|              Filter Assignment Team Method               |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. List Teams with Fully Occupied                        |");
                System.out.println("| 2. List Teams with Available Seats                       |");
                System.out.println("| 3. List Teams with Ungraded                              |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 3) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 3.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 3 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public void displayNoTeam() {
        System.out.println("No assignment teams available.");
    }

    public boolean insertTeamName() {
        System.out.print("Do you want to insert team name? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public String inputTeamName(int i, String teamId) {
        String teamName = "";
        while (teamName.isEmpty()) {
            System.out.print("Enter team name for team " + (i + 1) + " (" + teamId + "): ");
            teamName = sc.nextLine().trim();
            if (teamName.isEmpty()) {
                System.out.println("Team name cannot be empty.");
            }
        }
        return teamName;
    }

    public int displayMethodToRemoveStudent() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|              Select Delete Student Method                |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Delete All Students for Every Teams                   |");
                System.out.println("| 2. Delete Students from Selected Teams                   |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public int displayMethodToRemoveStudentFromTeam() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|        Delete Students Under Selected Team Method        |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Delete All Students                                   |");
                System.out.println("| 2. Delete Selected Students                              |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public void displayStudentToRemove(ListInterface<Student> students) {
        System.out.println("+" + "=".repeat(78) + "+");
        System.out.println("| No. |    Student ID    |       Student Name       |    Student Email         |");
        System.out.println("+" + "=".repeat(78) + "+");
        if (students.isEmpty()) {
            System.out.println("| No students available.                                                     |");
        }
        for (int i = 0; i < students.getSize(); i++) {
            Student student = students.getPosition(i);
            System.out.printf("| %-4s| %-16s| %-25s| %-25s|\n", i + 1, student.getStudentID(), student.getStudentName(), student.getStudentEmail());
        }
        System.out.println("+" + "=".repeat(78) + "+");
    }

    public boolean confirmAllStudentRemoval() {
        System.out.print("Do you want to remove all students from the team? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayAllStudentRemovalSuccess() {
        System.out.println("All students removed from the team successfully.");
    }

    public void displayAllStudentRemovalCancelled() {
        System.out.println("All student removal cancelled.");
    }

    public void displayNoStudentToRemove() {
        System.out.println("No students available to remove.");
    }

    public int displayMethodToViewStudents() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|                    View Student Method                   |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. View All Students                                     |");
                System.out.println("| 2. View Students Under Selected Team                     |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public void displayAllStudentsUnderTeams(ListInterface<AssignmentTeam> assignmentTeams) {
        System.out.println("                                   List of Teams                                  ");
        System.out.println("                               ---------------------                              \n");

        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (!team.getMembers().isEmpty()) {
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("|                                  Team Details                                  |");
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("| Team ID:         | " + team.getTeamId() + " ".repeat(Math.max(0, 60 - team.getTeamId().length())) + "|");
                System.out.println("| Team Name:       | " + team.getTeamName() + " ".repeat(Math.max(0, 60 - team.getTeamName().length())) + "|");
                System.out.println("| Min Team Size:   | " + team.getMinTeamSize() + " ".repeat(Math.max(0, 60 - String.valueOf(team.getMinTeamSize()).length())) + "|");
                System.out.println("| Max Team Size:   | " + team.getMaxTeamSize() + " ".repeat(Math.max(0, 60 - String.valueOf(team.getMaxTeamSize()).length())) + "|");
                System.out.println("| Team Grade:      | " + team.getGrade() + " ".repeat(Math.max(0, 60 - team.getGrade().length())) + "|");
                System.out.println("| Team Leader:     | " + team.getLeader().getStudentName() + " ".repeat(Math.max(0, 60 - team.getLeader().getStudentName().length())) + "|");
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("| No. | Student ID  | Student Name             | Student Email         | Gender  | ");
                System.out.println("+" + "=".repeat(80) + "+");
                for (int j = 0; j < team.getMembers().getSize(); j++) {
                    Student student = team.getMembers().getPosition(j);
                    System.out.printf("| %-4s| %-12s| %-25s| %-22s| %-8s|\n", j + 1, student.getStudentID(), student.getStudentName(), student.getStudentEmail(), student.getStudentGender());
                }
                System.out.println("+" + "=".repeat(80) + "+\n");
            }
        }
    }

    public void displayStudentsUnderTeam(AssignmentTeam team) {
        if (!team.getMembers().isEmpty()) {
            System.out.println("+" + "=".repeat(80) + "+");
            System.out.println("|                                  Team Details                                  |");
            System.out.println("+" + "=".repeat(80) + "+");
            System.out.println("| Team ID:         | " + team.getTeamId() + " ".repeat(Math.max(0, 60 - team.getTeamId().length())) + "|");
            System.out.println("| Team Name:       | " + team.getTeamName() + " ".repeat(Math.max(0, 60 - team.getTeamName().length())) + "|");
            System.out.println("| Min Team Size:   | " + team.getMinTeamSize() + " ".repeat(Math.max(0, 60 - String.valueOf(team.getMinTeamSize()).length())) + "|");
            System.out.println("| Max Team Size:   | " + team.getMaxTeamSize() + " ".repeat(Math.max(0, 60 - String.valueOf(team.getMaxTeamSize()).length())) + "|");
            System.out.println("| Team Grade:      | " + team.getGrade() + " ".repeat(Math.max(0, 60 - team.getGrade().length())) + "|");
            System.out.println("| Team Leader:     | " + team.getLeader().getStudentName() + " ".repeat(Math.max(0, 60 - team.getLeader().getStudentName().length())) + "|");
            System.out.println("+" + "=".repeat(80) + "+");
            System.out.println("| No. | Student ID  | Student Name             | Student Email         | Gender  | ");
            System.out.println("+" + "=".repeat(80) + "+");
            for (int j = 0; j < team.getMembers().getSize(); j++) {
                Student student = team.getMembers().getPosition(j);
                System.out.printf("| %-4s| %-12s| %-25s| %-22s| %-8s|\n", j + 1, student.getStudentID(), student.getStudentName(), student.getStudentEmail(), student.getStudentGender());
            }
            System.out.println("+" + "=".repeat(80) + "+");
        } else {
            System.out.println("No students available.");
        }

    }

    public void displayNoStudentsUnderTeam() {
        System.out.println("No students available under the team.");
    }

    public int selectMergeOption() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|               Merge Assignment Team Method               |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Automatically Merge Assignment Teams                  |");
                System.out.println("| 2. Manually Merge Assignment Teams                       |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;

    }

    public int[] selectTwoTeamsToMerge(int size) {
        while (true) {
            try {
                System.out.print("Enter the first team number to merge: ");
                int team1 = sc.nextInt();
                sc.nextLine();
                if (team1 < 0 || team1 > size) {
                    System.out.println("Invalid team number. Please enter a number between 1 and " + size + ".");
                    continue;
                }
                System.out.print("Enter the second team number to merge: ");
                int team2 = sc.nextInt();
                sc.nextLine();
                if (team2 < 0 || team2 > size) {
                    System.out.println("Invalid team number. Please enter a number between 1 and " + size + ".");
                    continue;
                }
                if (team1 == 0 || team2 == 0) {
                    return new int[]{team1 - 1, team2 - 1};
                }
                if (team1 == team2) {
                    System.out.println("Team 1 and Team 2 cannot be the same.");
                    continue;
                }
                return new int[]{team1 - 1, team2 - 1};
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid team number between 1 and " + size + ".");
                sc.nextLine();
            }
        }
    }

    public void displayTeamExceedMaxSize(AssignmentTeam team1, AssignmentTeam team2) {
        System.out.println("Team " + team1.getTeamId() + " and Team " + team2.getTeamId() + " cannot be merged as the merge size exceeds the maximum team size.");
    }


    public boolean confirmMergeTeam() {
        System.out.print("Do you want to merge these two teams? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayMergeTeamsSuccess(AssignmentTeam team1, AssignmentTeam team2, AssignmentTeam mergedTeam) {
        System.out.println("Team " + team1.getTeamId() + " " + team1.getTeamName() + " and Team " + team2.getTeamId() + " " + team2.getTeamName() + " merged successfully into Team " + mergedTeam.getTeamId() + " " + mergedTeam.getTeamName() + ".");
    }

    public void displayMergeTeamsCancelled(AssignmentTeam team1, AssignmentTeam team2) {
        System.out.println("Merging of Team " + team1.getTeamId() + " " + team1.getTeamName() + " and Team " + team2.getTeamId() + " " + team2.getTeamName() + " cancelled.");
    }

    public int displayMethodToAddStudent() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|               Add Student to Team Method                 |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Automatically Add Student                             |");
                System.out.println("| 2. Manually Add Student                                  |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public void teamFull() {
        System.out.println("Team is already full.");
    }

    public void displaySingleTeam() {
        System.out.println("There is only one team, no team to merge.");
    }

    public int selectSortOption() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|               Sort Assignment Team Method                 |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Sort by Team ID                                       |");
                System.out.println("| 2. Sort by Team Name                                     |");
                System.out.println("| 3. Sort by Team Size                                     |");
                System.out.println("| 4. Sort by Available Slots                               |");
                System.out.println("| 5. Sort by Grade                                         |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 5 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public int selectSwapTeam(ListInterface<AssignmentTeam> assignmentTeams, int size, AssignmentTeam team1) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(98) + "+");
                System.out.println("|                                        Select Team to Swap                                       |");
                System.out.println("+" + "=".repeat(98) + "+");
                System.out.println("| No. | Team ID             | Team Name                      | Team Size       | Occupied Slots    |");
                System.out.println("+" + "=".repeat(98) + "+");
                if (size == 0) {
                    System.out.println("| No teams available.                                                         |");
                } else {
                    for (int i = 0; i < assignmentTeams.getSize(); i++) {
                        AssignmentTeam team = assignmentTeams.getPosition(i);
                        if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                            System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize());
                        } else {
                            System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize());

                        }
                    }
                }
                System.out.println("+" + "=".repeat(98) + "+");
                System.out.println("| 0. Back                                                                                          |");
                System.out.println("+" + "=".repeat(98) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
                if (choice == 0) {
                    return -1;
                }
                if (assignmentTeams.getPosition(choice - 1).equals(team1)) {
                    System.out.println("Team cannot be swapped with itself.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;

    }

    public int selectStudentToSwap(AssignmentTeam team, String studentList, int size) {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("|                             Select Student to Swap                             |");
                String teamInfo = "Team: " + team.getTeamId() + " " + team.getTeamName();
                int totalSpaces = 80 - teamInfo.length();
                int leftSpaces = totalSpaces / 2;
                int rightSpaces = totalSpaces - leftSpaces;
                System.out.println("|" + " ".repeat(leftSpaces) + teamInfo + " ".repeat(rightSpaces) + "|");
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("| No. | Student ID  | Student Name             | Student Email         | Gender  | ");
                System.out.println("+" + "=".repeat(80) + "+");
                if (size == 0) {
                    System.out.println("| No students available.                                                         |");
                } else {
                    System.out.println(studentList);
                }
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.println("| 0. Back                                                                        |");
                System.out.println("+" + "=".repeat(80) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > size) {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + size + ".");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and " + size + " (eg. 1).");
                sc.nextLine();
            }
        }
        return choice - 1;

    }

    public boolean confirmSwap(Student studentToSwap, Student studentToSwap2, AssignmentTeam teamToSwap, AssignmentTeam teamToAmend) {
        System.out.print("Do you want to swap " + studentToSwap.getStudentName() + " with " + studentToSwap2.getStudentName() + " between Team " + teamToAmend.getTeamId() + " and Team " + teamToSwap.getTeamId() + "? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayNoTeamToSwap() {
        System.out.println("No team available to swap.");
    }

    public boolean confirmMergeTeams() {
        System.out.print("Do you want to merge these teams? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("Y");
    }

    public void displayAllTeamFull() {
        System.out.println("All teams are already full.");
    }

    public int selectReportOption() {
        int choice = -1;
        while (choice == -1) {
            try {
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("|               Generate Report Method                     |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.println("| 1. Generate Grade Analysis Report                        |");
                System.out.println("| 2. Generate Student Report                               |");
                System.out.println("| 0. Back                                                  |");
                System.out.println("+" + "=".repeat(58) + "+");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 2) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    choice = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid menu choice between 0 and 2 (eg. 1).");
                sc.nextLine();
            }
        }
        return choice;
    }

    public void reportHeader(String title) {
        String[] headerLines = {
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
                "@+++++++++++++@@+++++@@@@@@@@@#+*++++++++#@@#:::=@@@@*:::#=::::-@@@@-::::+-::::::::::::@@",
                "@+++++++++++++@#++++++@@@@@*++++++*%*+++++@@#:::=@@@@*:::#=:::::#@@%:::::+-::::::::::::@@",
                "@@@@@#+++*@@@@@+++++++*@@@#+++++#@@@@@++++@@#:::=@@@@*:::#=::::::@@=:::::+@@@@#:::=@@@@@@",
                "@@@@@#+++*@@@@++++%++++@@@@@++++#@#++++++%@@#:::=@@@@*:::#=::::::#%::::::+@@@@#:::=@@@@@@",
                "@@@@@#+++*@@@#+++*@%++++@@@@++++*++++*@@@@@@#:::=@@@@*:::#=:::-:::=::-:::+@@@@#:::=@@@@@@",
                "@@@@@#+++*@@@+++++++++++#@@@++++%@+++++@@@@@#:::=@@@@*:::#=:::*::::::%:::+@@@@#:::=@@@@@@",
                "@@@@@#+++*@@++++#****++++%@@++++%@@%++++%@@@@::::-@@=::::@=:::%=::::-@:::+@@@@#:::=@@@@@@",
                "@@@@@#+++*@*+++*@@@@@#++++@@++*@@@@@@++++*@@@@-::::::::-@@=:::%%::::@@:::+@@@@#:::=@@@@@@",
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@++++#@@@@@%##%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@++++*@@@@@@@@@@@@@*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*+++++#@@@@@#++%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%*++++++*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        };

        for (String line : headerLines) {
            for (int i = 0; i < 32; i++) {
                System.out.print(" ");
            }
            System.out.println(line);
        }

        System.out.println("\n" + "=".repeat(145));

        for (int i = 0; i < 46; i++) {
            System.out.print(" ");
        }
        System.out.println("TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");

        for (int i = 0; i < 58; i++) {
            System.out.print(" ");
        }
        System.out.println("ASSIGNMENT TEAM MANAGEMENT SUBSYSTEM");
        for (int i = 0; i < 66; i++) {
            System.out.print(" ");
        }

        System.out.println(title);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, hh:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("\nReport generated on: " + dtf.format(now) + "\n");
    }

    public void reportFooter() {
        System.out.println();
        for (int i = 0; i < 64; i++) {
            System.out.print(" ");
        }
        System.out.println("END OF THE SUMMARY REPORT");
        System.out.println("=".repeat(145));
    }

    public void displayCount(int size, int countA, int countB, int countC, int countD, int countF) {
        System.out.print("\nTotal number of teams: " + size);

        if (countA > 0) {
            System.out.print(", A: " + countA);
        }

        if (countB > 0) {
            System.out.print(", B: " + countB);
        }

        if (countC > 0) {
            System.out.print(", C: " + countC);
        }

        if (countD > 0) {
            System.out.print(", D: " + countD);
        }

        if (countF > 0) {
            System.out.print(", F: " + countF);
        }

        System.out.println();
    }

    public void displayGrade(ListInterface<AssignmentTeam> assignmentTeams) {
        System.out.println("+" + "=".repeat(114) + "+");
        System.out.println("|                                             List of Assignment Teams                                             |");
        System.out.println("+" + "=".repeat(114) + "+");
        System.out.println("| No. | Team ID             | Team Name                      | Team Size       | Occupied Slots    | Marks | Grade | ");
        System.out.println("+" + "=".repeat(114) + "+");
        if (assignmentTeams.isEmpty()) {
            System.out.println("| No assignment teams available.                                                                                   |");
        }
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            String grade;
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getGrade().equals("N/A")) {
                grade = "0";
                grade = countGrade(grade);
            } else {
                grade = countGrade(team.getGrade());
            }
            if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s| %-6s| %-6s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize(), team.getGrade(), grade);
            } else {
                System.out.printf("| %-4s| %-20s| %-31s| %-17s| %-17s| %-6s| %-6s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + " / " + team.getMaxTeamSize(), team.getGrade(), grade);
            }
        }
        System.out.println("+" + "=".repeat(114) + "+");
    }

    public String countGrade(String grade) {
        int marks = Integer.parseInt(grade);
        if (marks >= 80) {
            return "A";
        } else if (marks >= 70) {
            return "B";
        } else if (marks >= 60) {
            return "C";
        } else if (marks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public void displayDescriptiveAnalysisAndHistogram(int min, int max, double mean, double median, double mode, double variance, double standardDeviation, int countA, int countB, int countC, int countD, int countF) {
        int total = countA + countB + countC + countD + countF;
        System.out.println("\nDESCRIPTIVE ANALYSIS AND HISTOGRAM:\n");
        System.out.printf("%-70s%-70s%n", "DESCRIPTIVE STATISTICS", "HISTOGRAM");
        System.out.printf("%-70s%-70s%n", "---------------------", "----------");
        System.out.printf("%-70s%-70s%n", "MIN: " + min, "A: [" + repeatCharacter('*', countA) + "] (" + String.format("%.2f", ((double) countA / total) * 100) + "%)");
        System.out.printf("%-70s%-70s%n", "MAX: " + max, "B: [" + repeatCharacter('*', countB) + "] (" + String.format("%.2f", ((double) countB / total) * 100) + "%)");
        System.out.printf("%-70s%-70s%n", "MEAN: " + String.format("%.2f", mean), "C: [" + repeatCharacter('*', countC) + "] (" + String.format("%.2f", ((double) countC / total) * 100) + "%)");
        System.out.printf("%-70s%-70s%n", "MEDIAN: " + String.format("%.2f", median), "D: [" + repeatCharacter('*', countD) + "] (" + String.format("%.2f", ((double) countD / total) * 100) + "%)");
        System.out.printf("%-70s%-70s%n", "MODE: " + String.format("%.2f", mode), "F: [" + repeatCharacter('*', countF) + "] (" + String.format("%.2f", ((double) countF / total) * 100) + "%)");
        System.out.printf("%-70s%n", "VARIANCE: " + String.format("%.2f", variance));
        System.out.printf("%-70s%n", "STANDARD DEVIATION: " + String.format("%.2f", standardDeviation));
    }

    private static String repeatCharacter(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count * 4; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public void displayGradePosition(ListInterface<AssignmentTeam> highestTeams, ListInterface<AssignmentTeam> lowestTeams, ListInterface<AssignmentTeam> aboveAverageTeams, ListInterface<AssignmentTeam> belowAverageTeams, ListInterface<AssignmentTeam> failedTeams) {
        System.out.println("\nTEAM POSITIONING:");
        System.out.println("\nHIGHEST GRADE TEAMS:");
        for (int i = 0; i < highestTeams.getSize(); i++) {
            AssignmentTeam team = highestTeams.getPosition(i);
            System.out.println(i + 1 + ". Team " + team.getTeamId() + " " + team.getTeamName());
        }
        System.out.println("\nLOWEST GRADE TEAMS:");
        for (int i = 0; i < lowestTeams.getSize(); i++) {
            AssignmentTeam team = lowestTeams.getPosition(i);
            System.out.println(i + 1 + ". Team " + team.getTeamId() + " " + team.getTeamName());
        }
        System.out.println("\nFAILED GRADE TEAMS:");
        for (int i = 0; i < failedTeams.getSize(); i++) {
            AssignmentTeam team = failedTeams.getPosition(i);
            System.out.println(i + 1 + ". Team " + team.getTeamId() + " " + team.getTeamName());
        }
        System.out.println("\nABOVE AVERAGE GRADE TEAMS:");
        for (int i = 0; i < aboveAverageTeams.getSize(); i++) {
            AssignmentTeam team = aboveAverageTeams.getPosition(i);
            System.out.println(i + 1 + ". Team " + team.getTeamId() + " " + team.getTeamName());
        }
        System.out.println("\nBELOW AVERAGE GRADE TEAMS:");
        for (int i = 0; i < belowAverageTeams.getSize(); i++) {
            AssignmentTeam team = belowAverageTeams.getPosition(i);
            System.out.println(i + 1 + ". Team " + team.getTeamId() + " " + team.getTeamName());
        }
    }

    public void displayTeamAnalysis(ListInterface<AssignmentTeam> assignmentTeams, int studentWithTeam, int totalStudents) {
        System.out.println("+" + "=".repeat(120) + "+");
        System.out.println("|                                     List of Assignment Teams and Available Slots                                       |");
        System.out.println("+" + "=".repeat(120) + "+");
        System.out.println("| No. | Team ID       | Team Name                 | Team Size   | Occupied Slots | Available Slots | Leader Name         |");
        System.out.println("+" + "=".repeat(120) + "+");

        if (assignmentTeams.isEmpty()) {
            System.out.println("| No assignment teams available.                                                                                     |");
        }
        for (int i = 0; i < assignmentTeams.getSize(); i++) {
            AssignmentTeam team = assignmentTeams.getPosition(i);
            if (team.getMinTeamSize() == team.getMaxTeamSize()) {
                if (team.getLeader() == null) {
                    System.out.printf("| %-4s| %-14s| %-26s| %-12s| %-15s| %-16s| %-20s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + "/" + team.getMaxTeamSize(), team.getMaxTeamSize() - team.getMembers().getSize(), "N/A");
                } else {
                    System.out.printf("| %-4s| %-14s| %-26s| %-12s| %-15s| %-16s| %-20s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize(), team.getMembers().getSize() + "/" + team.getMaxTeamSize(), team.getMaxTeamSize() - team.getMembers().getSize(), team.getLeader().getStudentName());
                }
            } else {
                if (team.getLeader() == null) {
                    System.out.printf("| %-4s| %-14s| %-26s| %-12s| %-15s| %-16s| %-20s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + "/" + team.getMaxTeamSize(), team.getMaxTeamSize() - team.getMembers().getSize(), "N/A");
                } else {
                    System.out.printf("| %-4s| %-14s| %-26s| %-12s| %-15s| %-16s| %-20s|\n", i + 1, team.getTeamId(), team.getTeamName(), team.getMinTeamSize() + " - " + team.getMaxTeamSize(), team.getMembers().getSize() + "/" + team.getMaxTeamSize(), team.getMaxTeamSize() - team.getMembers().getSize(), team.getLeader().getStudentName());
                }
            }
        }
        System.out.println("+" + "=".repeat(120) + "+");
        System.out.printf("| Total Students: %-102s |\n", studentWithTeam + " / " + totalStudents);
        System.out.println("+" + "=".repeat(120) + "+");
    }

    public void displayTeamSizeAnalysis(ListInterface<AssignmentTeam> assignmentTeams, ListInterface<Student> students, int lowestTeamSize, int highestTeamSize, double averageTeamSize, int[] teamSizeFrequency) {
        int totalTeams = 0;
        for (int frequency : teamSizeFrequency) {
            totalTeams += frequency;
        }

        System.out.println("\nTEAM SIZE ANALYSIS:");
        System.out.println("Lowest Team Size: " + lowestTeamSize);
        System.out.println("Highest Team Size: " + highestTeamSize);
        System.out.println("Average Team Size: " + String.format("%.2f", averageTeamSize));

        System.out.println("\nTEAM SIZE FREQUENCY HISTOGRAM:");

        int maxFrequency = findMaxFrequency(teamSizeFrequency);

        for (int i = lowestTeamSize; i <= highestTeamSize; i++) {
            int frequency = teamSizeFrequency[i - lowestTeamSize];
            double percentage = ((double) frequency / totalTeams) * 100;
            System.out.printf("%-3d: %5d [%-" + maxFrequency + "s] %.2f%%\n", i, frequency, repeatCharacter2('*', frequency), percentage);
        }

        System.out.println("\nTotal Number of Teams (" + totalTeams + " team(s)):");
        for (int i = lowestTeamSize; i <= highestTeamSize; i++) {
            int frequency = teamSizeFrequency[i - lowestTeamSize];
            if (frequency > 0) {
                System.out.println(frequency + " team(s) with " + i + " member(s):");
                for (int j = 0; j < assignmentTeams.getSize(); j++) {
                    AssignmentTeam team = assignmentTeams.getPosition(j);
                    if (team.getMembers().getSize() == i) {
                        System.out.println("  - Team " + team.getTeamId() + " " + team.getTeamName());
                    }
                }
            }
        }

        System.out.println("\nStudents without a team:");
        boolean hasStudentsWithoutTeam = false;
        for (int i = 0; i < students.getSize(); i++) {
            Student student = students.getPosition(i);
            boolean isInTeam = false;
            for (int j = 0; j < assignmentTeams.getSize(); j++) {
                AssignmentTeam team = assignmentTeams.getPosition(j);
                if (team.getMembers().contains(student)) {
                    isInTeam = true;
                    break;
                }
            }
            if (!isInTeam) {
                System.out.println(" - " + student.getStudentID() + " " + student.getStudentName());
                hasStudentsWithoutTeam = true;
            }
        }

        if (!hasStudentsWithoutTeam) {
            System.out.println("All students are assigned to a team.");
        }
    }

    private int findMaxFrequency(int[] teamSizeFrequency) {
        int maxFrequency = Integer.MIN_VALUE;
        for (int frequency : teamSizeFrequency) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }
        return maxFrequency;
    }

    private String repeatCharacter2(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count * 1.5; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public void displayExitMessage() {
        System.out.println("Exiting Assignment Team Management System. Goodbye!");
    }


}
