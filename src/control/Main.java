/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import dao.DoublyCircularLinkedListDAO;
import dao.Initializer;
import entity.*;
import utility.Utility;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        Initializer initializer = new Initializer();

        DoublyCircularLinkedListDAO<Programme> programmeDAO = new DoublyCircularLinkedListDAO<>();
        DoublyCircularLinkedListDAO<Course> courseDAO = new DoublyCircularLinkedListDAO<>();
        DoublyCircularLinkedListDAO<Student> studentDAO = new DoublyCircularLinkedListDAO<>();
        DoublyCircularLinkedListDAO<Tutor> tutorDAO = new DoublyCircularLinkedListDAO<>();
        DoublyCircularLinkedListDAO<TutorialGroup> tutorialGroupDAO = new DoublyCircularLinkedListDAO<>();
        DoublyCircularLinkedListDAO<CreditTransfer> creditTransferDao = new DoublyCircularLinkedListDAO<>();
        DoublyCircularLinkedListDAO<CourseAssignmentTeams> courseAssignmentTeamsDAO = new DoublyCircularLinkedListDAO<>();
        

        ListInterface<Programme> programmeList = new DoublyCircularLinkedList<>(); // Initialize the programme list
        ListInterface<Course> courseList = new DoublyCircularLinkedList<>(); // Initialize the course list
        ListInterface<Student> studentList = new DoublyCircularLinkedList<>(); // Initialize the student list
        ListInterface<Tutor> tutorList = new DoublyCircularLinkedList<>(); // Initialize the tutor list
        ListInterface<TutorialGroup> tutorialGroupList = new DoublyCircularLinkedList<>(); // Initialize the tutorial group list
        ListInterface<CreditTransfer> creditTransferList = new DoublyCircularLinkedList<>();
        ListInterface<CourseAssignmentTeams> courseAssignmentTeamsList = new DoublyCircularLinkedList<>();
        ListInterface<CourseAssignmentTeams> teamsList = new DoublyCircularLinkedList<>();

        // Check if the data files exist
        File programmeFile = new File("Programme");
        File courseFile = new File("Course");
        File studentFile = new File("Student");
        File tutorFile = new File("Tutor");
        File tutorialGroupFile = new File("TutorialGroup");
        File creditTransferFile = new File("creditTransfer");
        File courseAssignmentTeamsFile = new File("CourseAssignmentTeams");
                

        if (programmeFile.exists() && courseFile.exists() && studentFile.exists() && tutorFile.exists() && creditTransferFile.exists()) {
            programmeList = programmeDAO.loadFromFile("Programme");
            courseList = courseDAO.loadFromFile("Course");
            studentList = studentDAO.loadFromFile("Student");
            tutorList = tutorDAO.loadFromFile("Tutor");
            tutorialGroupList = tutorialGroupDAO.loadFromFile("TutorialGroup");
            int lastPosition = tutorList.getSize() - 1;
            Tutor.setIdNo(Integer.parseInt(tutorList.getPosition(lastPosition).getId().substring(1)) + 1);
            creditTransferList = creditTransferDao.loadFromFile("creditTransfer");
        } else {
            // Initialize programme and course lists
            boolean programInitResult = initializer.initializeProgramme(programmeList);
            boolean courseInitResult = initializer.initializeCourse(courseList, programmeList, tutorList);
            studentList = initializer.initializeStudent(programmeList, courseList);
            boolean tutorialGroupInitResult = initializer.initializeTutorialGroup(tutorialGroupList, programmeList, studentList);


            if (!programInitResult || !courseInitResult) {
                // Handle initialization failure
                System.err.println("Initialization failed!");
                System.exit(1); // Exit the program or handle the failure as appropriate
            }

            // Save initialized lists to files
            programmeDAO.saveToFile((DoublyCircularLinkedList<Programme>) programmeList, "Programme");
            courseDAO.saveToFile((DoublyCircularLinkedList<Course>) courseList, "Course");

            // Initialize student list

            studentDAO.saveToFile((DoublyCircularLinkedList<Student>) studentList, "Student");
            
            // Initialize tutor list
            tutorList = initializer.initializeTutor();
            tutorDAO.saveToFile((DoublyCircularLinkedList<Tutor>) tutorList, "Tutor");

            // Initialize tutorial group list
            tutorialGroupDAO.saveToFile((DoublyCircularLinkedList<TutorialGroup>) tutorialGroupList, "TutorialGroup");
            
            creditTransferDao.saveToFile((DoublyCircularLinkedList<CreditTransfer>) creditTransferList, "creditTransfer");

        }
        courseAssignmentTeamsList = initializer.initializeTeams(courseList, tutorialGroupList);
        teamsList = initializer.initializeAssignmentTeams(courseAssignmentTeamsList,tutorialGroupList);
        courseAssignmentTeamsDAO.saveToFile((DoublyCircularLinkedList<CourseAssignmentTeams>) teamsList, "CourseAssignmentTeams");
        boolean exit = false;

        while (!exit) {
            try {
                System.out.println("Main Menu");
            System.out.println("1. Student Management");
            System.out.println("2. Programme Management");
            System.out.println("3. Course Management");
            System.out.println("4. Tutorial Group Management");
            System.out.println("5. Tutor Management");
            System.out.println("6. Assignment Team Management");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

                int choice = scan.nextInt();

                switch (choice) {
                    case 1:
                        Utility.clearScreen();
                        MaintainStudent.main(args);
                        break;
                    case 2:
                        Utility.clearScreen();
                        ProgrammeManagement.main(args);
                        break;
                    case 3:
                        Utility.clearScreen();
                        CourseManagement.main(args);
                        break;
                    case 4:
                        Utility.clearScreen();
                        TutorialGroupManagement.main(args);
                        break;
                    case 5:
                        Utility.clearScreen();
                        TutorManagement.main(args);
                        break;
                    case 6:
                        Utility.clearScreen();
                        AssignmentTeamManagement.main(args);
                        break;    
                    case 0:
                        exit = true;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scan.nextLine();
            }
        }

    }

}
