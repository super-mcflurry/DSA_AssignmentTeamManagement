/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import entity.Course;
import entity.CreditTransfer;
import entity.Programme;
import entity.Student;
import entity.Student.CourseStatus;
import entity.Tutor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author jsony
 */
public class CourseManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getChoice() {
        int choice;
        System.out.println("***********************************************************************************************");
        System.out.println("|                                Course Management Main Menu                                  |");
        System.out.println("***********************************************************************************************");
        System.out.println("|  1 | Add a new course                      |  7 | List courses taken by different faculties |");
        System.out.println("|  2 | Manage course structure               |  8 | Add programme to course                   |");
        System.out.println("|  3 | Remove course                         |  9 | Remove programme from course              |");
        System.out.println("|  4 | Find course                           |  10| Manage credit transfer                    |");
        System.out.println("|  5 | Amend course details                  |  11| Generate reports                          |");
        System.out.println("|  6 | List all courses                      |  0 | Quit                                      |");
        System.out.println("***********************************************************************************************");
        System.out.print("Enter choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public String inputCourseCode() {
        String courseID;
        boolean isValid = false;

        do {
            System.out.print("Enter Course Code (e.g: BAIT2073): ");
            courseID = scanner.nextLine().toUpperCase();

                if (courseID.length() == 8) {
                    isValid = true;
                for (int i = 0; i < 4; i++) {
                        if (!Character.isLetter(courseID.charAt(i))) {
                        isValid = false;
                        break;
                    }
                }
                    for (int i = 4; i < 8; i++) {
                        if (!Character.isDigit(courseID.charAt(i))) {
                            isValid = false;
                            break;
            }
                    }
                }

            if (!isValid) {
                System.out.println("Invalid format! Course code must be in the format of four letters followed by four digits. (e.g: BAIT2073)\n");
            }
        } while (!isValid);

        return courseID;
    }



    public String inputCourseName() {
        System.out.print("Enter Course Name: ");
        String CourseName = scanner.nextLine().toUpperCase();
        return CourseName;
    }

    public double inputCourseFee() {
        double courseFee = 0;
        boolean validInput = false;
        do {
            System.out.print("Enter Course Fee (RM): ");
            try {
                courseFee = scanner.nextDouble();
                scanner.nextLine();

                if (courseFee > 0) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Fee must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid fee.");
                scanner.nextLine(); 
            }
        } while (!validInput);
        return courseFee;
    }
    
    public int inputCreditHours() {
        int creditHours = 0;
        int maxCreditHours = 4;
        int minCreditHours = 2;
        boolean validInput = false;
        do {
            System.out.print("Enter Course Credit Hours: ");
            try {
                creditHours = scanner.nextInt();
                scanner.nextLine(); 

                if (creditHours >= minCreditHours && creditHours <= maxCreditHours) {
                    validInput = true;
                } else {
                    if (creditHours < minCreditHours) {
                        System.out.println("Minimum credit hours for a course is 2.");
                    }
                    if (creditHours > maxCreditHours){
                        System.out.println("Maximum credit hours for a course is 4.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer value for credit hours.");
                scanner.nextLine();
            }
        } while (!validInput);
        return creditHours;
    }

    public int[] setTeamSize() {
        int choice = 0;

        while (true) {
            try {
                System.out.println("\nSelect team size option:");
                System.out.println("1. Small (2-3 members)");
                System.out.println("2. Medium (4-5 members)");
                System.out.println("3. Large (6-8 members)");
                System.out.println("4. Custom");
                System.out.println("5. Fixed (Specify team size)");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                if (choice < 1 || choice > 5) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.nextLine(); 
            }
        }

        int[] teamSize = new int[2];

        switch (choice) {
            case 1:
                teamSize[0] = 2;
                teamSize[1] = 3;
                break;
            case 2:
                teamSize[0] = 4;
                teamSize[1] = 5;
                break;
            case 3:
                teamSize[0] = 6;
                teamSize[1] = 8;
                break;
            case 4:
                System.out.print("Enter minimum team size: ");
                teamSize[0] = scanner.nextInt();
                System.out.print("Enter maximum team size: ");
                teamSize[1] = scanner.nextInt();
                break;
            case 5:
                System.out.print("Enter fixed team size: ");
                int fixedSize = scanner.nextInt();
                teamSize[0] = fixedSize;
                teamSize[1] = fixedSize;
                break;
        }
        return teamSize;
    }


    public Course inputCourseDetails() {
        System.out.println("****************************");
        System.out.println("|      Add New Course      |");
        System.out.println("****************************\n");
        String courseCode = inputCourseCode();
        String courseName = inputCourseName();
        int creditHours = inputCreditHours();
        System.out.println();
        return new Course(courseCode, courseName, creditHours);
    }
    
    public void duplicatedCourse(String courseCode) {
        System.out.println(courseCode + " has been registered! \nYou need to enter a unique course code!" );
    }
    
    public char recreateNewCourse() {
        char continueOption;
        while (true) {
            System.out.print("\nDo you want to recreate a new course? (y/n): ");
            continueOption = scanner.next().charAt(0);
            continueOption = Character.toLowerCase(continueOption);
            scanner.nextLine();
            if (continueOption == 'y' || continueOption == 'n') {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
        return continueOption;
    }
    
    public char proceedManageCourseStructure() {
        char option;
        while (true) {
            System.out.print("\nDo you want to proceed to set the course structure? (y/n): ");
            option = scanner.next().charAt(0);
            option = Character.toLowerCase(option);
            if (option == 'y' || option == 'n') {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
        return option;
    }

    public String inputCourseCodeToSearch() {
        System.out.println("\n-----Search Course-----");
        String targetCode = inputCourseCode();
        return targetCode;
    }

    
    public int inputRowNum(String action) {
        System.out.print("Select row to " + action + ": ");
        int index = scanner.nextInt();
        return index;
    }
    
    public int inputRowNum(String attribute, String action) {
        System.out.print("Select " + attribute + " to " + action + ": ");
        int index = scanner.nextInt();
        return index;
    }
    
    public String inputProgrammeCodeToAdd() {
        System.out.print("Enter programme Code to add to this course: ");
        scanner.nextLine();
        String targetId = scanner.nextLine();
        return targetId.toUpperCase();
    }
    
    public String actionPrompt(String entityName, String exampleId) {
        System.out.print("\nEnter " + entityName.toLowerCase() + " (e.g: " + exampleId.toUpperCase() + "): ");
        return scanner.nextLine().toUpperCase();
    }
    
    public void courseNotFound(String chosenCourseCode) {
        System.out.println("Course " + chosenCourseCode + " not found.");
    }

    public void SuccessfulMsg() {
        System.out.println("Action performed successfully!");
    }

    public void ErrorMsg() {
        System.out.println("Action performed failed!");
    }
    
    public void showCourseDetails(Course course) {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Course Details");
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Course Code          :  " + course.getCourseCode());
        System.out.println("Course Name          :  " + course.getCourseName());
        System.out.println("Course credit hours  :  " + course.getCreditHours());
        System.out.println("Course Fee           :  " + String.format("RM%.2f", course.getCourseFee()));
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
    
    public void printSelectedCourseDetails(Course course) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Course Details");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(course.toString());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
    
    public void showProgrammeDetails (Programme programme) {
        System.out.println("========================================================================================================");
        System.out.println("Programme Details");
        System.out.println("========================================================================================================");
        System.out.println("Programme Code     : " + programme.getProgrammeCode());
        System.out.println("Programme Name     : " + programme.getProgrammeName());
        System.out.println("Programme Duration : " + programme.getProgrammeDuration() + " years");
        System.out.println("Programme Intake   : " + programme.getProgrammeIntake());
        System.out.println("Programme Faculty  : " + programme.getFaculty());
        System.out.println("========================================================================================================");
    }
    
    public void listAllProgrammeUnderCourse(String outputStr){
        System.out.println(outputStr);
    }
    
    public void listAllFaculty() {
        System.out.println("\nList of Faculty: \n-------------------------------------------");
    }

    public void listAllCourses(String outputStr) {
        System.out.println("\nList of Courses: \n-------------------------------------------\n" + outputStr);
    }

    public void listAllProgramme(String outputStr) {
        System.out.println("\nList of Programme: \n-------------------------------------------\n" + outputStr);
    }

    public void displayProgrammeAddedMessage() {
        System.out.println("Programme has been successfully added!");
    }

    public void displayProgrammeRemovedMessage() {
        System.out.println("Programme has been successfully removed!");
    }
    
    public void courseAttributeUpdated(String attribute) {
        System.out.println("Course " + attribute + " updated.\n");
    }
    
    public String inputNewCourseAttributeStr(String attribute) {
        System.out.print("\nEnter new course " + attribute + ": ");
        String input = scanner.nextLine();
        return input.toUpperCase();
    }

    public int inputNewCourseAttributeInt(String attribute) {
        int input = 0;
        boolean validInput = false;
        do {
            System.out.print("\nEnter new course " + attribute + ": ");
            try {
                input = scanner.nextInt();
                scanner.nextLine(); 
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
            }
        } while (!validInput);
        return input;
    }

    public double inputNewCourseAttributeDouble(String attribute) {
        double input = 0.0;
        boolean validInput = false;
        do {
            System.out.print("\nEnter new course " + attribute + ": ");
            try {
                input = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        } while (!validInput);
        return input;
    }
    
    public int getAmmendCourseMenuOption() {
        int choice;
        boolean validInput = false;

        do {
            System.out.println("Select attribute to edit:");
            System.out.println("1. Course Name");
            System.out.println("2. Course Credit Hours");
            System.out.println("0. Exit amend programme");
            System.out.print("Enter the attribute number: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice >= 0 && choice <= 2) {
                    validInput = true;
                } else {
                    invalidNumberOption(1, 2, 0);
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
                choice = -1; 
            }
        } while (!validInput);

        return choice;
    }
    
    public int actionConfirmation(String action){
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Are you sure to " + action + "?\n");
            System.out.println("1. Yes");
            System.out.println("2. Cancel");
            System.out.print("Enter choice: ");

            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 2) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a valid choice (1 or 2).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }

        return choice;
    }
    
    public Character getContinueManagingOption() {
        char continueOption;
        do {
            System.out.print("Do you want to continue managing the course structure? (y/n): ");
            continueOption = scanner.next().charAt(0);
            continueOption = Character.toLowerCase(continueOption);
            if (continueOption != 'y' && continueOption != 'n') {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (continueOption != 'y' && continueOption != 'n');
        return continueOption;
    }

    public Character getContinueOption(String course) {
        char continueOption;
        do {
            System.out.print("Do you want to continue amending " + course + "? (y/n): ");
            continueOption = scanner.next().charAt(0);
            continueOption = Character.toLowerCase(continueOption);
            if (continueOption != 'y' && continueOption != 'n') {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (continueOption != 'y' && continueOption != 'n');
        return continueOption;
    }

    public Character getContinueAddingProgrammeOption() {
        char continueOption;
        do {
            System.out.print("Do you want to continue adding other programmes? (y/n): ");
            continueOption = scanner.next().charAt(0);
            continueOption = Character.toLowerCase(continueOption);
            if (continueOption != 'y' && continueOption != 'n') {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (continueOption != 'y' && continueOption != 'n');
        return continueOption;
    }

    
    public void invalidCharacterOption(char... options) {
        StringBuilder message = new StringBuilder("Invalid option. Please enter ");
        for (int i = 0; i < options.length; i++) {
            message.append("'").append(options[i]).append("'");
            if (i < options.length - 1) {
                message.append(" or ");
            }
        }
        message.append(".\n");
        System.out.println(message.toString());
    }
    
    public Character getContinueRemoveOption() {
        char continueOption;
        do {
            System.out.print("Do you want to continue removing programmes? (y/n): ");
            continueOption = scanner.next().charAt(0);
            continueOption = Character.toLowerCase(continueOption);
            if (continueOption != 'y' && continueOption != 'n') {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (continueOption != 'y' && continueOption != 'n');
        return continueOption;
    }
    
    public int RemoveConfirmation(){
        System.out.println("Are you sure to delete?\n");
        System.out.println("1. Yes");
        System.out.println("2. Cancel");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();

        return choice;
    }

    
    public int RemoveQuestion(){
        System.out.print("Select the Course that you want to remove: ");
        int index = scanner.nextInt();
        return index;
    }
    
    public void invalidNumberOption(int... options) {
        StringBuilder message = new StringBuilder("Invalid option. Please enter ");
        for (int i = 0; i < options.length; i++) {
            message.append(options[i]);
            if (i < options.length - 1) {
                message.append(" or ");
            }
        }
        message.append(".\n");
        System.out.println(message.toString());
    }
    
    public int addProgramToCourseMenu() {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("\nDo you want to add by:");
            System.out.println("1. Selecting a specific programme");
            System.out.println("2. Selecting all programmes under a faculty");
            System.out.print("Enter choice: ");

            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 2) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a valid choice (1 or 2).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }

        return choice;
    }
    
    public void removeProgrammeFromCourseMenu(ListInterface courseList) {
        System.out.println("*************************************************************");
        System.out.println("             Remove A Programme From Course                  ");
        showCourseCodeWithName(courseList);
    }
    
    public void addProgrammeToCourseMenu(ListInterface courseList) {
        System.out.println("*************************************************************");
        System.out.println("                  Add Programme To Course                    ");
        showCourseCodeWithName(courseList);
    }
    
    public void amendCourseMenu(ListInterface courseList) {
        System.out.println("*************************************************************");
        System.out.println("                    Amend Course Details                     ");
        showCourseCodeWithName(courseList);
    }
    
    public void removeCourseMenu(ListInterface courseList) {
        System.out.println("*************************************************************");
        System.out.println("                         Remove Course                       ");
        showCourseCodeWithName(courseList);
    }
    
    public void manageCourseMenu(ListInterface courseList) {
        System.out.println("*************************************************************");
        System.out.println("                    Manage Course Structure                  ");
        showCourseCodeWithName(courseList);
    }

    public void showCourseCodeWithName(ListInterface courseList) {
        System.out.println("*************************************************************");
        System.out.println(String.format("| %-3s | %-8s | %-40s |", "No.", "Code", "Name"));
        System.out.println("*************************************************************");
        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = (Course) courseList.getPosition(i);
            System.out.println(String.format("| %-3d | %-8s | %-40s |", i + 1, course.getCourseCode(), course.getCourseName()));
        }
        System.out.println("*************************************************************");
    }
    
    
    public int manageCourseStructureOption() {
        int choice = 0;

        while (true) {
            try {
                System.out.println("\nSelect an option:");
                System.out.println("1. Set course leader");
                System.out.println("2. Set minimum grade for credit transfer");
                System.out.println("3. Set coursework/exam weightage");
                System.out.println("4. Set assignment team size"); 
               System.out.print("Enter your choice (0 to exit): ");
                choice = scanner.nextInt();
                if (choice < 0 || choice > 43) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }

        return choice;
    }

    
    public void showCourseStructure(Course course) {
        System.out.println("\n-----------------------------------------------------------------------------------------------");
        System.out.println("Course Structure");
        System.out.println("-----------------------------------------------------------------------------------------------");
        if (course.getCourseLeader() != null) {
            System.out.println("Course Leader                     :  " + course.getCourseLeader().getName());
        } else {
            System.out.println("Course Leader                     :  None");
        }
        System.out.println("Minimum grade for credit transfer :  " + course.getCtMinGrade());
        System.out.println("Coursework weightage(%)           :  " + course.getCourseworkWeightage());
        System.out.println("Exam weightage(%)                 :  " + course.getExamWeightage());
        System.out.println("Assignment team size              :  " + course.getMinTeamSize() + " - " + course.getMaxTeamSize());
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    
    public void printAllTutors(ListInterface<Tutor> tutorList) {
        String line = "==========================================================================================";
        System.out.println("\n" + line);
        System.out.printf("     %-10s %-30s %-15s %-10s\n",  "Tutor ID", "Tutor Name", "Work Type", "Faculty");
        System.out.println(line);

        for (int i = 0; i < tutorList.getSize(); i++) {
            Tutor currentTutor = tutorList.getPosition(i);
            if (currentTutor != null) {
                System.out.printf("%-2d|  %-10s %-30s %-15s %-10s\n", i + 1, currentTutor.getId(), currentTutor.getName(),
                        currentTutor.getWorkType(), currentTutor.getFaculty());
            }
        }

        System.out.println(line);
    }
    
    public char inputMinimumGradeForCreditTransfer() {
        char grade = ' ';
        boolean validInput = false;

        while (!validInput) {
            System.out.print("\nPlease enter the minimum grade for credit transfer (A, B, or C): ");
            String input = scanner.next().toUpperCase(); // Convert input to uppercase for case insensitivity

            if (input.length() == 1) {
                grade = input.charAt(0);
                if (grade == 'A' || grade == 'B' || grade == 'C') {
                    validInput = true;
                } else {
                    System.out.println("Invalid grade. Please enter A, B, or C.");
                }
            } else {
                System.out.println("Invalid input. Please enter a single character.");
            }
        }

        return grade;
    }

    public int[] setWeightage() {
        int choice = 0;

        while (true) {
            try {
                System.out.println("\nSelect weightage option:");
                System.out.println("1. 50% coursework, 50% exam");
                System.out.println("2. 60% coursework, 40% exam");
                System.out.println("3. 70% coursework, 30% exam");
                System.out.println("4. 100% coursework, 0% exam");
                System.out.println("5. Custom");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }

        int[] weightage = new int[2];

        switch (choice) {
            case 1:
                weightage[0] = 50;
                weightage[1] = 50;
                break;
            case 2:
                weightage[0] = 60;
                weightage[1] = 40;
                break;
            case 3:
                weightage[0] = 70;
                weightage[1] = 30;
                break;
            case 4:
                weightage[0] = 100;
                weightage[1] = 0;
                break;
            case 5:
                System.out.print("Enter coursework weightage: ");
                weightage[0] = scanner.nextInt();
                System.out.print("Enter exam weightage: ");
                weightage[1] = scanner.nextInt();
                break;
        }
        return weightage;
    }

    public int creditTransferManu() {
        System.out.println("*****************************************");
        System.out.println("|          Credit Transfer Menu         |");
        System.out.println("*****************************************");
        System.out.println("| 1. Check pending credit transfer      |");
        System.out.println("| 2. List ALL approved/rejected enquiry |");
        System.out.println("| 0. Exit                               |");
        System.out.println("*****************************************");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        return choice;
    }
    
    public int searchMenu() {
        System.out.println("*****************************************");
        System.out.println("|             Search Menu               |");
        System.out.println("*****************************************");
        System.out.println("| 1. Search by selecting                |");
        System.out.println("| 2. Search by typing                   |");
        System.out.println("| 0. Exit                               |");
        System.out.println("*****************************************");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    } 


    public ListInterface showAllCreditTransfer(ListInterface creditTransferList) {
        ListInterface<CreditTransfer> pendingCT = new DoublyCircularLinkedList<>();
        String line = "=========================================================================================================================================";
        String outputStr = "Credit transfer enquiry list\n----------------------------\n\n" + line + "\n";
        outputStr += String.format("     %-30s %-80s %-20s\n", "Student", "Credit Transfer Enquiry", "Status");
        outputStr += line + "\n";

        int count = 1;
        for (int i = 0; i < creditTransferList.getSize(); i++) {
            CreditTransfer creditTransfer = (CreditTransfer) creditTransferList.getPosition(i);
            if (creditTransfer != null && "Pending".equals(creditTransfer.getStatus())) {
                outputStr += String.format("%-2d|  %-30s %-80s %-20s\n", count, creditTransfer.getStudent().getStudentName(),  creditTransfer.getSelectedCourse().getCourseName(), creditTransfer.getStatus());
                pendingCT.add(creditTransfer);
                count++;
            }
        }

        outputStr += line + "\n";
        
        if (pendingCT.isEmpty()){
            System.out.println("There is no pending credit transfer enquiry.");
        }
        else {
            System.out.println(outputStr);
        }
        
        return pendingCT;
    }
    
    public void showCreditTransferDetails(CreditTransfer ct) {
        char grade = ct.getGrade();
        grade = Character.toUpperCase(grade);
        
        System.out.println("\nCredit Transfer Details\n=======================");
        System.out.println("Student IC                      : " + ct.getStudent().getStudentIC()); 
        System.out.println("Student Name                    : " + ct.getStudent().getStudentName()); 
        System.out.println("Course required credit transfer : " + ct.getSelectedCourse().getCourseName()); 
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Previous course              : " + ct.getPreviousCourse());
        System.out.println("Previous course Description  : " + ct.getCourseDesc()); 
        System.out.println("Previous course credit hours : " + ct.getCreditHours()); 
        System.out.println("Previous course grade        : " + grade);
   }
    
    public int CreditTransferConfirmation(){
        System.out.println("\nDo you want to approve or reject this enquiry?\n");
        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();

        return choice;
    }
    
    public void showPerformedCreditTransfer(ListInterface creditTransferList) {
        String line = "=========================================================================================================================================";
        String outputStr = "Credit transfer enquiry listing\n-----------------------------------------\n\n" + line + "\n";
        outputStr += String.format("    %-30s %-30s %-30s %-5s %-10s %-30s\n", "Student", "Credit Transfer Course", "Previous Course", "Grade", "Status", "Action Performed Time");
        outputStr += line + "\n";

        int count = 1;
        for (int i = 0; i < creditTransferList.getSize(); i++) {
            CreditTransfer creditTransfer = (CreditTransfer) creditTransferList.getPosition(i);
            char grade = creditTransfer.getGrade();
            grade = Character.toUpperCase(grade);
            if (creditTransfer != null && !"Pending".equals(creditTransfer.getStatus())) {
                outputStr += String.format("%-2d  %-30s %-30s %-30s %-5s %-10s %-30s\n", count, 
                                                                                    creditTransfer.getStudent().getStudentID() + "-" + creditTransfer.getStudent().getStudentName(),
                                                                                creditTransfer.getSelectedCourse().getCourseName(), 
                                                                                creditTransfer.getPreviousCourse(),
                                                                                grade,
                                                                                creditTransfer.getStatus(),
                                                                                creditTransfer.getActionDate());
                count++;
            }
        }

        outputStr += line + "\n";
        
        System.out.println(outputStr);
    }
    
    public int summaryReport() {
        System.out.println("*********************************");
        System.out.println("|    Generate Summary Report    |");
        System.out.println("*********************************");
        System.out.println("| 1. Course summary report      |");
        System.out.println("| 2. Financial summary report   |");
        System.out.println("*********************************");
        System.out.print("Enter your choice (0 to exit): ");
        int choice = scanner.nextInt();
        
        return choice;
    }
    
    public void summaryReport1(ListInterface<Course> courseList, ListInterface<Student> studentList, int programmeSize) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        int count = 1;
        int studentSize = studentList.getSize();

        String line = "=========================================================================================================================================";
        String outputStr = line + "\n                                   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY\n                                                  COURSE MANAGEMENT SUBSYSTEM\n\n";
        outputStr += "                                                     COURSE SUMMARY REPORT\n                                                -------------------------------\n";
        outputStr += "\nGenerated at: " + formattedDateTime + "\n\n";
        outputStr += "-----------------------------------------------------------------------------------------------------------------------------------------\n";
        outputStr += String.format("%-10s %-40s %-25s %-15s %-20s %-10s\n", "Code", "Name","Course Leader", "Credit Hours", "Programme Offered", "Popularity");
        outputStr += String.format("%-10s %-40s %-25s %-15s %-20s %-10s\n", "----", "----", "-------------","------------", "-----------------", "----------");

        int totalEnrolledStudents = studentList.getSize();
        int[] enrolledStudentCountForEachCourseArray = new int[courseList.getSize()];
        String[][] courseCodeArray = new String[courseList.getSize()][1];

        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = courseList.getPosition(i);
            int programmeOffered = course.getProgrammesAssociated().getSize();
            int studentEnrolled = getStudentEnrolledCount(course, studentList);
            enrolledStudentCountForEachCourseArray[i] = studentEnrolled;
            courseCodeArray[i][0] = course.getCourseCode();

            outputStr += String.format("%-10s %-40s %-25s %-15s %-20s %-10s\n", course.getCourseCode(), course.getCourseName(),
                    (course.getCourseLeader() != null ? course.getCourseLeader().getName() : "N/A"), "     " + course.getCreditHours(),
                    "       " + programmeOffered + "/" + programmeSize, "    " + studentEnrolled);
        }
        
        System.out.println(outputStr);
        
        printGraph(enrolledStudentCountForEachCourseArray, courseCodeArray, totalEnrolledStudents);


        System.out.println("\n                                                  END OF THE COURSE SUMMARY REPORT");
        System.out.println(line);

    }

    private void printGraph(int[] enrolledStudentCountForEachCourseArray, String[][] courseCodeArray, int totalEnrolledStudent){
        System.out.println("\nPercentages Of The Course Popularity\n****************************************");
        String summaryCountStr="";
        for(int n=0; n < enrolledStudentCountForEachCourseArray.length; n++){
            double percentageOfCount = Math.round(((double) enrolledStudentCountForEachCourseArray[n] / (double) totalEnrolledStudent) * 100);


            int divider = (int)percentageOfCount / 10;
            int remainder = (int)percentageOfCount % 10;

            String percentageBar = setPercentageBar(divider,remainder);

            summaryCountStr += String.format("%s = [%s] (%2.0f%s) \n",courseCodeArray[n][0],percentageBar,percentageOfCount,"%"); 
        }

        System.out.println(summaryCountStr);
    }

    private int getStudentEnrolledCount(Course course, ListInterface<Student> studentList) {
        int count = 0;
        for (int j = 0; j < studentList.getSize(); j++) {
            Student student = studentList.getPosition(j);
            ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();
            if (studentCourseList.contains(student.new CourseStatus(course, ""))) {
                count++;
            }
        }
        return count;
    }

    private String setPercentageBar(int divider, int remainder) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < divider; i++) {
            sb.append(" ");
        }
        if (remainder > 0) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public void financialReport(ListInterface<Course> courseList, ListInterface<Student> studentList) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        String line = "=================================================================================================================================================";
        String outputStr = line + "\n                                   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY\n                                                  COURSE MANAGEMENT SUBSYSTEM\n\n";
        outputStr += "                                                    COURSE FINANCIAL REPORT\n                                               ---------------------------------\n";
        outputStr += "\nGenerated at: " + formattedDateTime + "\n\n";
        outputStr += "-------------------------------------------------------------------------------------------------------------------------------------------------\n";
        outputStr += String.format("%-10s %-40s %-15s %-15s %-15s %-15s %-15s %-15s\n", "Code", "Name", "Credit Hours", "Course Fee", "Main Revenue", "Resit Revenue", "Repeat Revenue", "Total Revenue");
        outputStr += String.format("%-10s %-40s %-15s %-15s %-15s %-15s %-15s %-15s\n", "----", "----", "-------------", "-----------", "--------------", "-------------", "--------------", "--------------");

        double totalMainRevenue = 0.0;
        double totalResitRevenue = 0.0;
        double totalRepeatRevenue = 0.0;
        double totalOverallRevenue = 0.0;
        double highestResit = 0.0;
        double highestRepeat = 0.0;
        Course highestResitCourse = null;
        Course highestRepeatCourse = null;

        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = courseList.getPosition(i);

            double mainRevenue = calculateMainRevenue(course, studentList);
            double resitRevenue = calculateResitRevenue(course, studentList);
            if((resitRevenue / course.getResitFee()) > highestResit) {
                highestResit = resitRevenue / course.getResitFee();
                highestResitCourse = course;
            }
            double repeatRevenue = calculateRepeatRevenue(course, studentList);
            if((repeatRevenue / course.getCourseFee()) > highestRepeat) {
                highestRepeat = repeatRevenue / course.getCourseFee();
                highestRepeatCourse = course;
            }

            double totalRevenue = mainRevenue + resitRevenue + repeatRevenue;

            totalMainRevenue += mainRevenue;
            totalResitRevenue += resitRevenue;
            totalRepeatRevenue += repeatRevenue;
            totalOverallRevenue += totalRevenue;

            outputStr += String.format("%-10s %-40s %-15d %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f\n", course.getCourseCode(), course.getCourseName(),
                    course.getCreditHours(), course.getCourseFee(), mainRevenue, resitRevenue, repeatRevenue, totalRevenue);
        }

        outputStr += "-------------------------------------------------------------------------------------------------------------------------------------------------\n";
        outputStr += String.format("%-10s %-40s %-15s %-15s %-15.2f %-15.2f %-15.2f %-15.2f\n", "Total(RM):", "", "", "", totalMainRevenue, totalResitRevenue, totalRepeatRevenue, totalOverallRevenue);
        outputStr += "-------------------------------------------------------------------------------------------------------------------------------------------------\n";
        System.out.println(outputStr);
        
        System.out.printf("%-60s%-60s\n", "Highest Resit Course", "Highest Repeat Course");
        if(highestResit != 0.0 && highestRepeat != 0.0) {
            System.out.printf("%-60s%-60s\n", "   -> [" + (int) highestResit + " resit]  <" + highestResitCourse.getCourseCode() + "> " + highestResitCourse.getCourseName(),
                "   -> [" + (int) highestRepeat + " repeat]  <" + highestRepeatCourse.getCourseCode() + "> " + highestRepeatCourse.getCourseName());
        }
        if(highestResit == 0.0 && highestRepeat != 0.0) {
            System.out.printf("%-60s%-60s\n", "   -> N/A" ,
                "   -> [" + (int) highestRepeat + " repeat]  <" + highestRepeatCourse.getCourseCode() + "> " + highestRepeatCourse.getCourseName());
        }
        if(highestResit != 0.0 && highestRepeat == 0.0) {
            System.out.printf("%-60s%-60s\n", "   -> [" + (int) highestResit + " resit]  <" + highestResitCourse.getCourseCode() + "> " + highestResitCourse.getCourseName(),
                "   -> N/A");
        }
        if(highestResit == 0.0 && highestRepeat == 0.0) {
            System.out.printf("%-60s%-60s\n", "   -> N/A","   -> N/A");
        }

        printBarChart(courseList, studentList, totalOverallRevenue);

        System.out.println("\n                                                  END OF THE COURSE FINANCIAL REPORT");
        System.out.println(line);
    }
    
    private double calculateMainRevenue(Course course, ListInterface<Student> studentList) {
        double repeatRevenue = 0.0;
        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);
            ListInterface<Student.CourseStatus> courseStatusList = student.getStudentCourseList();

            for (int j = 0; j < courseStatusList.getSize(); j++) {
                Student.CourseStatus courseStatus = courseStatusList.getPosition(j);
                Course enrolledCourse = courseStatus.getCourse();

                if (enrolledCourse.equals(course) && courseStatus.getStatus().equalsIgnoreCase("Main")) {
                    repeatRevenue += course.getCourseFee();
                }
            }
        }
        return repeatRevenue;
    }
    
    private double calculateResitRevenue(Course course, ListInterface<Student> studentList) {
        double resitRevenue = 0.0;
        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);
            ListInterface<Student.CourseStatus> courseStatusList = student.getStudentCourseList();

            for (int j = 0; j < courseStatusList.getSize(); j++) {
                Student.CourseStatus courseStatus = courseStatusList.getPosition(j);
                Course enrolledCourse = courseStatus.getCourse();

                if (enrolledCourse.equals(course) && courseStatus.getStatus().equalsIgnoreCase("Resit")) {
                    resitRevenue += course.getResitFee();
                }
            }
        }
        return resitRevenue;
    }
    
    private double calculateRepeatRevenue(Course course, ListInterface<Student> studentList) {
        double repeatRevenue = 0.0;
        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);
            ListInterface<Student.CourseStatus> courseStatusList = student.getStudentCourseList();

            for (int j = 0; j < courseStatusList.getSize(); j++) {
                Student.CourseStatus courseStatus = courseStatusList.getPosition(j);
                Course enrolledCourse = courseStatus.getCourse();

                if (enrolledCourse.equals(course) && courseStatus.getStatus().equalsIgnoreCase("Repeat")) {
                    repeatRevenue += course.getCourseFee();
                }
            }
        }
        return repeatRevenue;
    }

    private void printBarChart(ListInterface<Course> courseList, ListInterface<Student> studentList, double totalOverallRevenue) {
        System.out.println("\nRevenue Contribution by Each Course (Bar Chart)\n*************************************************");

        int maxCourseCodeLength = 0;
        for (int i = 0; i < courseList.getSize(); i++) {
            int length = courseList.getPosition(i).getCourseCode().length();
            if (length > maxCourseCodeLength) {
                maxCourseCodeLength = length;
            }
        }

        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = courseList.getPosition(i);
            double mainRevenue = calculateMainRevenue(course, studentList);
            double resitRevenue = calculateResitRevenue(course, studentList);
            double repeatRevenue = calculateRepeatRevenue(course, studentList);
            double totalRevenue = mainRevenue + resitRevenue + repeatRevenue;
            double percentageRevenue = (totalRevenue / totalOverallRevenue) * 100;

            int barLength = (int) (percentageRevenue / 5); 

            System.out.printf("%-" + (maxCourseCodeLength + 5) + "s [", course.getCourseCode() + " = ");
            for (int j = 0; j < barLength; j++) {
                System.out.print(" ");
            }
            System.out.printf("] (%.2f%%)\n", percentageRevenue);
        }
    }





}
