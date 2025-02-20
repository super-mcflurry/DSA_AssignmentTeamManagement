/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Course;
import entity.Programme;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 *
 * @author jsony
 */
public class ProgrammeManagementUI implements Serializable {
    
    Scanner scanner = new Scanner(System.in);

    public int getProgrammeMenuChoice() {
        System.out.println("===========================================");
        System.out.println("|        PROGRAMME MANAGEMENT MENU        |");
        System.out.println("===========================================");
        System.out.println("| 1. Add new programme                    |");
        System.out.println("| 2. Remove programme                     |");
        System.out.println("| 3. Search programme                     |");
        System.out.println("| 4. Amend programme details              |");
        System.out.println("| 5. List programmes                      |");
        System.out.println("| 6. Add new course to programme          |");
        System.out.println("| 7. Remove course from programme         |");
        System.out.println("| 8. List all courses from a programme    |");
        System.out.println("| 0. Quit                                 |");
        System.out.println("===========================================");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public int getAmendProgrammeMenuOption() {
        int choice;
        boolean validInput = false;

        do {
            System.out.println("\nSelect attribute to edit:");
            System.out.println("1. Programme Name");
            System.out.println("2. Programme Intake");
            System.out.println("3. Programme Duration");
            System.out.println("4. Faculty");
            System.out.println("0. Exit amend programme");
            System.out.print("Enter the attribute number: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice >= 0 && choice <= 5) {
                    validInput = true;
                } else {
                    invalidNumberOption(1, 2, 3,4,5, 0);
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
                choice = -1; 
            }
        } while (!validInput);

        return choice;
    }
    
    public int addCourseOption(){
        System.out.println("1. Add existing course to programme");
        System.out.println("2. Add new course to programme");
        System.out.print("\nEnter choice (0 to exit): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public int listProgrammeFormat(){
        System.out.println("List programmes\n-------------------");
        System.out.println("1. List by Faculty ");
        System.out.println("2. List by Intake");
        System.out.println("3. List ALL");
        System.out.print("\nEnter choice (0 to exit): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    
    public Character getContinueAmendingOption(String programme){
        char continueOption;
        System.out.print("Do you want to continue amending " + programme + "? (y/n): ");
        continueOption = scanner.next().charAt(0);
        continueOption = Character.toLowerCase(continueOption);
        return continueOption;
    }
    
    public Character getContinueAddCourseOption(){
        char continueOption;
        System.out.print("Do you want to continue adding another course? (y/n): ");
        continueOption = scanner.next().charAt(0);
        continueOption = Character.toLowerCase(continueOption);
        return continueOption;
    }
    
    public Character getContinueAddToProgrammeOption(){
        char continueOption;
        System.out.print("\nDo you want to continue adding this course to other programmes? (y/n): ");
        continueOption = scanner.next().charAt(0);
        continueOption = Character.toLowerCase(continueOption);
        return continueOption;
    }

    public char duplicatedProgramme(String programmeCode) {
        char option;
        boolean validInput;

        do {
            System.out.println(programmeCode + " has been registered! \nYou need to enter a unique programme code!" );
            System.out.print("Do you want to add other new programme? (y/n): ");
            option = scanner.next().charAt(0);
            scanner.nextLine();
            option = Character.toLowerCase(option);

            validInput = option == 'y' || option == 'n';
            if (!validInput) {
                System.out.println("Invalid input! Please enter 'y' or 'n'.");
            }
        } while (!validInput);

        return option;
    }


    public void programmeNotFound(String chosenProgrammeCode) {
        System.out.println("Programme " + chosenProgrammeCode + " not found.");
    }

    public void listAllProgrammes(String outputStr) {
        
        System.out.println(outputStr);

    }
    
    public void addProgramHeader() {
        System.out.println("=====================================");
        System.out.println("          Add New Programme          ");
        System.out.println("=====================================");
    }
    
    public void removeProgramHeader() {
        System.out.println("======================================");
        System.out.println("           Remove Programme           ");
        System.out.println("======================================");
    }
    
    public void searchProgramHeader() {
        System.out.println("======================================");
        System.out.println("           Search Programme           ");
        System.out.println("======================================");
    }
    
    public void amendProgramHeader() {
        System.out.println("============================================");
        System.out.println("           Amend Programme Details          ");
        System.out.println("============================================");
    }
    
    public void addCourseToProgramHeader() {
        System.out.println("=============================================");
        System.out.println("           Add Course To Programme           ");
        System.out.println("=============================================");
    }
    
    public void removeCourseFromProgramHeader() {
        System.out.println("==================================================");
        System.out.println("           Remove Course From Programme           ");
        System.out.println("==================================================");
    }
    
    public void printProgrammeDetails(Programme programme) {
        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Programme Details");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Programme Code   :  " + programme.getProgrammeCode());
        System.out.println("Programme Name   :  " + programme.getProgrammeName());
        System.out.println("Programme Intake :  " + programme.getProgrammeIntake());
        System.out.println("Duration (year)  :  " + programme.getProgrammeDuration());
        System.out.println("Faculty          :  " + programme.getFaculty());
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
    }
    
    public void printCourseDetails(Course course) {
        System.out.println("\n----------------------------------------------------------------");
        System.out.println("Course Details");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Course Code         :  " + course.getCourseCode());
        System.out.println("Course Name         :  " + course.getCourseName());
        System.out.println("Course Credit Hours :  " + course.getCreditHours());
        System.out.println("Course Fees         :  " + String.format("RM%.2f", course.getCourseFee()));
        System.out.println("----------------------------------------------------------------");
    }
    
    public void printAllCoursesDetails(Course course, int count) {
        System.out.println("\n----------------------------------------------------------------");
        System.out.println("[Course " + count + "]");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Course Code         :  " + course.getCourseCode());
        System.out.println("Course Name         :  " + course.getCourseName());
        System.out.println("Course Credit Hours :  " + course.getCreditHours());
        System.out.println("Course Fees         :  " + String.format("RM%.2f", course.getCourseFee()));
    }
    
    public char showCourseDetailsMsg(){
        char choice = ' '; 
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Do you want to view all courses in details? (y/n): ");

            String input = scanner.next(); 

            if (input.length() == 1) { 
                choice = input.charAt(0); 
                if (choice == 'y' || choice == 'n') {
                    validInput = true;
                } else {
                    System.out.println("Please enter 'y' or 'n'.");
                }
            } else {
                System.out.println("Please enter 'y' or 'n'.");
            }
        }
        
        choice = Character.toLowerCase(choice);
        return choice;
    }

    public void invalidMessage(String msg) {
        System.out.println("Invalid " + msg);
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

    public String inputProgrammeCode() {
        System.out.println("\n******************************************************");
        System.out.println("*   First character represent the programme type     *");
        System.out.println("* R - Bachelor Degree / D - Diploma / F - Foundation *");
        System.out.println("******************************************************");
        System.out.print("Enter programme code (e.g RDS): ");
        String programmeCode = scanner.nextLine();

        while (true) {
            if (programmeCode.length() != 3) {
                System.out.println("Programme code must be 3 characters!");
            } else if (!Character.toString(programmeCode.charAt(0)).matches("[RrDdFf]")) {
                System.out.println("First character of programme code must be R, D, or F!");
            } else if (!programmeCode.matches("[a-zA-Z]+")) {
                System.out.println("Programme code must contain only letters!");
            } else {
                break;
            }

            System.out.print("Enter programme code (e.g RDS): ");
            programmeCode = scanner.nextLine();
        }

        return programmeCode.toUpperCase();
    }
    
    public String inputNewProgrammeCode() {
        System.out.println("\n******************************************************");
        System.out.println("*        First character represent the type          *");
        System.out.println("* R - Bachelor Degree / D - Diploma / F - Foundation *");
        System.out.println("******************************************************");
        System.out.print("Enter new programme code (e.g RDS): ");
        String programmeCode = scanner.nextLine();

        while (true) {
            if (programmeCode.length() != 3) {
                System.out.println("Programme code must be 3 characters!");
            } else if (!Character.toString(programmeCode.charAt(0)).matches("[RrDdFf]")) {
                System.out.println("First character of programme code must be R, D, or F!");
            } else if (!programmeCode.matches("[a-zA-Z]+")) {
                System.out.println("Programme code must contain only letters!");
            } else {
                break;
            }

            System.out.print("Enter programme code (e.g RDS): ");
            programmeCode = scanner.nextLine();
        }

        return programmeCode.toUpperCase();
    }

    public String inputProgrammeIntake() {
        String intakePattern = "^(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER) \\d{4}$";

        while (true) {
            System.out.print("Enter programme intake (e.g., May 2024): ");
            String programmeIntake = scanner.nextLine().trim().toUpperCase();

            if (programmeIntake.matches(intakePattern)) {
                return programmeIntake;
            } else {
                System.out.println("Invalid input! Intake must be in the format \"Month Year\" (e.g., May 2024).");
            }
        }
    }


    public int inputProgrammeDuration() {
        int programmeDuration = 0;
        boolean validInput = false;
        do {
            System.out.print("Enter programme duration (years): ");
            try {
                programmeDuration = scanner.nextInt();
                scanner.nextLine(); 
                
                if (programmeDuration > 0) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Duration cannot less than 1.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid duration.");
                scanner.nextLine(); // Consume the invalid input
            }
        } while (!validInput);
        return programmeDuration;
    }

    public String inputFaculty() {
        System.out.print("Enter faculty (e.g FOCS): ");
        String faculty = scanner.nextLine();
        
        while (true) {
            if (faculty.length() != 4) {
                System.out.println("Faculty must be 4 characters!");
            }
            else if (!faculty.matches("[a-zA-Z]+")) {
                System.out.println("Faculty must contain only letters!");
            }
            else {
                break;
            }

            System.out.print("Enter faculty (e.g FOCS): ");
            faculty = scanner.nextLine();
        }

        return faculty.toUpperCase();
    }

    public Programme inputProgrammeDetails() {
        String programmeCode = inputProgrammeCode();
        char firstChar = programmeCode.charAt(0);
        
        System.out.print("Enter programme name (e.g. Data Science): ");
        String programmeName = scanner.nextLine().trim();

        switch (Character.toUpperCase(firstChar)) {
            case 'R':
                programmeName = "Bachelor of " + programmeName;
                break;
            case 'D':
                programmeName = "Diploma in " + programmeName;
                break;
            case 'F':
                programmeName = "Foundation in " + programmeName;
                break;
        }
        String programmeIntake = inputProgrammeIntake();
        int programmeDuration = inputProgrammeDuration();
        String faculty = inputFaculty();

        System.out.println();
        return new Programme(programmeCode, programmeName.toUpperCase(), programmeIntake, programmeDuration, faculty);
    }

    public int removeProgrammeOption() {
        System.out.println("1. Remove a programme");
        System.out.println("2. Remove a range of programmes");
        System.out.println("3. Remove ALL programmes");
        System.out.println("0. Exit remove");
        System.out.print("Enter your option: ");
        int option = scanner.nextInt();
        return option;
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
                choice = scanner.nextInt(); // Read as a string and parse to int
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
    
    public char actionConfirmationChar(String action){
        char choice = ' '; 
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Are you sure to " + action + "? (y/n): ");

            String input = scanner.next(); 

            if (input.length() == 1) { 
                choice = input.charAt(0); 
                if (choice == 'y' || choice == 'n') {
                    validInput = true;
                } else {
                    System.out.println("Please enter 'y' or 'n'.");
                }
            } else {
                System.out.println("Please enter 'y' or 'n'.");
            }
        }
        
        choice = Character.toLowerCase(choice);
        return choice;
    }

    
    public String removeQuestion(){
        System.out.print("\nEnter the Programme Code that you want to remove: ");
        scanner.nextLine();
        String programmeCode = scanner.nextLine();
        return programmeCode.toUpperCase();
    }
    
    public Character deleteAllProgrammeConfirmation() {
        char confirmation;
        System.out.print("\nAre you sure to DELETE *ALL* Programmes? (y/n): ");
        confirmation = scanner.next().charAt(0);
        confirmation = Character.toLowerCase(confirmation);
        return confirmation;
    }

    public int inputRowNum(String action) {
        System.out.print("Select row to " + action + ": ");
        int index = scanner.nextInt();
        return index;
    }
    
    public int inputRowNum(String attribute, String action) {
        System.out.print("\nSelect " + attribute + " to " + action + ": ");
        int index = scanner.nextInt();
        return index;
    }
    
    public int inputStartingRowToRemove() {
        int startIndex = 0;
        boolean validInput = false;
        do {
            System.out.print("Enter starting row to remove: ");
            try {
                startIndex = scanner.nextInt();
                scanner.nextLine(); 
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
            }
        } while (!validInput);
        return startIndex;
    }

    public int inputEndingRowToRemove() {
        int endIndex = 0;
        boolean validInput = false;
        do {
            System.out.print("Enter ending row (inclusive): ");
            try {
                endIndex = scanner.nextInt();
                scanner.nextLine(); 
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); 
            }
        } while (!validInput);
        return endIndex;
    }

    public String inputProgrammeCodeToAddCourse() {
        System.out.print("Enter programme Code to add course: ");
        String targetId = scanner.nextLine();
        return targetId.toUpperCase();
    }
    
    public String actionPrompt(String entityName, String exampleId) {
        System.out.print("\nEnter " + entityName.toLowerCase() + " (e.g: " + exampleId.toUpperCase() + "): ");
        return scanner.nextLine().toUpperCase();
    }

    public String inputProgrammeCodeToSearch() {
        System.out.print("Enter programme Code to search: ");
        String targetId = scanner.nextLine();
        return targetId.toUpperCase();
    }

    public String inputNewProgrammeAttributeStr(String attribute) {
        System.out.print("Enter new programme " + attribute + ": ");
        String input = scanner.nextLine();
        return input.toUpperCase();
    }

    public int inputNewProgrammeAttributeInt(String attribute) {
        int input = 0;
        boolean validInput = false;
        do {
            System.out.print("Enter new programme " + attribute + ": ");
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

    public double inputNewProgrammeAttributeDouble(String attribute) {
        double input = 0.0;
        boolean validInput = false;
        do {
            System.out.print("Enter new programme " + attribute + ": ");
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
    
    public void displayCourseAddedMessage() {
        System.out.println("Course has been successfully added!");
    }
    
    public void displayCourseRemovedMessage() {
        System.out.println("Course has been successfully removed from this course!");
    }
    
    public void displayProgrammeRemovedMessage(String programmeCode) {
        System.out.println("Programme " + programmeCode + " has been successfully removed!");
    }
    
    public void displayAllProgrammeRemovedMessage() {
        System.out.println("All programme has been successfully removed!");
    }

    public void programmeAttributeUpdated(String attribute) {
        System.out.println("Programme " + attribute + " updated.");
    }
    
    public char sortOption() {
        char choice = ' '; 
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Do you want to view the list in sorted version? (y/n): ");

            String input = scanner.next(); 

            if (input.length() == 1) { 
                choice = input.charAt(0); 
                if (choice == 'y' || choice == 'n') {
                    validInput = true;
                } else {
                    System.out.println("Please enter 'y' or 'n'.");
                }
            } else {
                System.out.println("Please enter 'y' or 'n'.");
            }
        }
        
        choice = Character.toLowerCase(choice);
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
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            try {
                System.out.println("Select team size option:");
                System.out.println("1. Small (2-3 members)");
                System.out.println("2. Medium (4-5 members)");
                System.out.println("3. Large (6-8 members)");
                System.out.println("4. Custom");
                System.out.println("5. Fixed (Specify team size)");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
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

    public char addNewCourseConfirmation(String code, String name, int creditHours){
        char confirmation;
        
        System.out.println("\nNew Course to create\n----------------------");
        System.out.println("1. Course code         : " + code.toUpperCase());
        System.out.println("2. Course name         : " + name.toUpperCase());
        System.out.println("3. Course credit hours : " + creditHours);
        System.out.println("===========================================================");
        System.out.print("\nAre you sure to create this new course? (y/n): ");
        
        confirmation = scanner.next().charAt(0);
        scanner.nextLine(); 
        confirmation = Character.toLowerCase(confirmation);
        return confirmation;
    }
    
    public void newCourseAddedMsg(String course, String programme){
        System.out.println("New course " + course + " is created and added into programme " + programme + " successfully!");
    }
    
    public int addCourseSelection(){
        System.out.println("\nDo you want to add to:");
        System.out.println("1. Selected programme");
        System.out.println("2. Programme under selected faculty");
        System.out.println("3. Programme with same intake");
        System.out.println("4. All programme");
        System.out.print("Enter your choice here: ");
        
        int selection = scanner.nextInt();
        scanner.nextLine(); 
        return selection;
    }
    
    public char addCourseToAllProgramme(){
        char confirmation;
        
        System.out.print("Are you sure to add this course to *ALL* programmes above? (y/n): ");
        
        confirmation = scanner.next().charAt(0);
        scanner.nextLine(); 
        confirmation = Character.toLowerCase(confirmation);
        return confirmation;
    }
    
    public void newCourseAddedAllMsg(String course){
        System.out.println("New course " + course + " is created and added into all programmes successfully!");
    }
    
    public void newCourseAddByFaculty(String course, String faculty){
        System.out.println("New course " + course + " is created and added into all programmes under " + faculty + " successfully!");
    }
    
    public void newCourseAddByIntake(String course, String intake){
        System.out.println("New course " + course + " is created and added into all programmes in " + intake + " intake successfully!");
    }
    

//    public Character getProgrammeSortedOption() {
//        char confirmation;
//        System.out.print("View in ascending order?(y/n): ");
//        confirmation = scanner.next().charAt(0);
//        confirmation = Character.toLowerCase(confirmation);
//        return confirmation;
//    }
//
//    public Character askUserToPrintProgramList() {
//        char confirmation;
//        System.out.print("Print the list?(p/n): ");
//        confirmation = scanner.next().charAt(0);
//        confirmation = Character.toLowerCase(confirmation);
//        return confirmation;
//    }
    
}
