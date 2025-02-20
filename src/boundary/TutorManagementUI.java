/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import entity.Course;
import entity.Programme;
import entity.Tutor;
import entity.TutorClass;
import entity.TutorialGroup;
import utility.Utility;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author zhixi
 */
public class TutorManagementUI implements Serializable{
    
    Scanner sc = new Scanner(System.in);
    
    public int tutorMenu(){
        int choice = 0;
            while(true){
            try {
                System.out.println("\n" + "=".repeat(110));
                System.out.println("|                                             TUTOR MANAGEMENT                                               |");
                System.out.println("=".repeat(110));
                System.out.println("| 1. Add New Tutor                                    | 8. Search Courses Under A Tutor                      |");
                System.out.println("| 2. Amend Tutor                                      | 9. Search Tutors For A Course                        |");
                System.out.println("| 3. Remove Tutor                                     | 10. List Tutors And Tutorial Group For A Course      |");
                System.out.println("| 4. Assign Tutor To Courses                          | 11. List Courses For Each Tutor                      |");
                System.out.println("| 5. Assign Tutor To Tutorial Groups                  | 12. Generate Report                                  |");
                System.out.println("| 6. Add Tutors To Tutorial Group For A Course        | 13. List All Tutor                                   |");
                System.out.println("| 7. Random Assign Classes                            |                                                      |");
                System.out.println("=".repeat(110));
                System.out.print("Enter choice  (0 to back) : ");
                choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a valid number!");
            }
        }
    }

    public int reportMenu() {
        int choice = 0;
        while (true) {
            try {
                System.out.println("\nSummary Report");
                System.out.println("---------------------------------------------");
                System.out.println("1.  Display Part Time Tutor Financial Report");
                System.out.println("2.  Display Tutor Class Assignment Report");
                System.out.println("---------------------------------------------");
                System.out.print("Select your choice (0 to exit) : ");
                choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                sc.next();
            }
        }
    }
    
    public int amendMenu(){
        int choice = 0;
        while (true) {
            try {
                System.out.println("\n=======================================================");
                System.out.println("|                  Amend Tutor Details                |");
                System.out.println("=======================================================");
                System.out.println("| 1. Name                                             |");
                System.out.println("| 2. IC                                               |");
                System.out.println("| 3. Work Type                                        |");
                System.out.println("| 4. Phone Number                                     |");
                System.out.println("| 5. Faculty                                          |");
                System.out.println("| 6. ALL                                              |");
                System.out.println("=======================================================");
                System.out.print("Enter choice (0 to back) : ");
                choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a valid number!");
            }
        }
    }

    public void filterTutorMenu(){
        System.out.println("\n=======================================================");
        System.out.println("|                  Filter Tutor By                    |");
        System.out.println("=======================================================");
        System.out.println("| 0. No Filter                                        |");
        System.out.println("| 1. Name                                             |");
        System.out.println("| 2. Work Type                                        |");
        System.out.println("| 3. Gender                                           |");
        System.out.println("=======================================================");
    }

    public String inputGender() {
        int choice = 0;
        while (true) {
            try {
                System.out.println("\n=======================================================");
                System.out.println("|                      Gender                         |");
                System.out.println("=======================================================");
                System.out.println("| 1. Male                                             |");
                System.out.println("| 2. Female                                           |");
                System.out.println("=======================================================");
                System.out.print("Enter choice (0 to back) : ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        return "Male";
                    case 2:
                        return "Female";
                }
            } catch(InputMismatchException e){
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a valid number!");
            }
        }
    }

    public boolean confirmation(String message) {

        String choice;
        do {
            System.out.print(message);
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (choice.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        } while (true);
    }
    
    public int inputIndex(String message, int listSize) {
        int index = -1;
        boolean valid = false;

        do {
            try {
                System.out.print(message);
                index = sc.nextInt();
                sc.nextLine();
                if (index >= 0 && index <= listSize) {
                    valid = true;
                } else {
                    System.out.println("Error: Please Enter A Valid Index (1-" + listSize + ")");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Please Enter A valid Number.");
                valid = false;
            }
        } while (!valid);

        return index;
    }
    
    // <editor-fold defaultstate="collapsed" desc="$ Manage Tutor $">
    public String inputName(){
        String name;
        while(true) {
            System.out.print("Enter Tutor Name : ");
            name = sc.nextLine();
            
            if(!name.matches("[a-zA-Z ]+")){
                System.out.println("Error: Name must contain letters only!\n");
            }
            else{
                return name.toUpperCase();
            }
        }
    }
    
    public String inputIC(ListInterface<Tutor> tutorList){
        String IC;
        while(true){
            System.out.print("Enter Tutor IC Number : ");
            IC = sc.nextLine();
            
            if(!IC.matches("\\d{12}")){
                System.out.println("IC must contain 12 digits numbers only!\n");
            }
            else if(duplicatedIC(IC, tutorList)){
                System.out.println("Error: The IC Number Already Exist For Other Tutor!\n");
            }
            else{
                return IC;
            }
        }
    }
    
    public boolean duplicatedIC(String ic, ListInterface<Tutor> tutorList){
        if(!tutorList.isEmpty()){
            for(int i = 0; i < tutorList.getSize();i++){
                Tutor existingTutor = tutorList.getPosition(i);
                if(existingTutor.getIc().equals(ic)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String inputWorkType(){
        int choice = 0;
        while(true){
            System.out.println("Choose Tutor's Work Type");
            System.out.println("1. Full Time");
            System.out.println("2. Part Time");
            System.out.print("Enter choice : ");

            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1:
                        return "Full Time";
                    case 2:
                        return "Part Time";
                    default:
                        System.out.println("Error: Please enter a valid choice (1 or 2)!\n");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a number 1 or 2!\n");
            }
        }
    }
    
    public String inputPhoneNo(ListInterface<Tutor> tutorList){
        String phoneNo;
        while(true){
            System.out.print("Enter Tutor Phone Number (e.g 0123456789): ");
            phoneNo = sc.nextLine();
            
            if(!phoneNo.matches("0\\d{9,10}")){
                System.out.println("Error: Phone Number is not valid!\n");
            }
            else if(duplicatedPhoneNo(phoneNo, tutorList)){
                System.out.println("Error: The Phone Number Already Exist For Other Tutor!\n");
            }
            else{
                return phoneNo;
            }
        }
    }
    
    public boolean duplicatedPhoneNo(String phoneNo, ListInterface<Tutor> tutorList){
        if(!tutorList.isEmpty()){
            for(int i = 0; i < tutorList.getSize();i++){
                Tutor existingTutor = tutorList.getPosition(i);
                if(existingTutor.getPhoneNo().equals(phoneNo)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String inputFaculty(){
        String faculty;
         while (true) {
            System.out.print("Enter faculty (e.g FOCS): ");
            faculty = sc.nextLine();
            
            if (!faculty.matches("[a-zA-Z]+")) {
                System.out.println("Error: Faculty must contain letters only!\n");
            }
            else if (faculty.length() != 4) {
                System.out.println("Error: Faculty must be 4 characters only!\n");
            }
            else {
                return faculty.toUpperCase();
            }
        }
    }
    
    public String checkGender(String ic){
        String gender;
        char lastDigitChar = ic.charAt(ic.length() - 1);
        int lastDigit = Character.getNumericValue(lastDigitChar);
        if(lastDigit % 2 != 0){
            gender = "Male";
        }
        else{
            gender = "Female";
        }
        return gender;
    }
    
    public Tutor inputNewTutor(ListInterface<Tutor> tutorList){
        System.out.println("\n-----ADD NEW TUTOR-----");
        String name = inputName();
        String ic = inputIC(tutorList);
        String workType = inputWorkType();
        String phoneNo = inputPhoneNo(tutorList);
        String faculty = inputFaculty();
        String gender = checkGender(ic);
        return new Tutor(name, ic, gender, workType, phoneNo, faculty);
    }
    
    public String inputNewName(String oldName){
        String newName;
        while (true) {
            System.out.print("Enter New Name : ");
            newName = sc.nextLine();
            if(!newName.matches("[a-zA-Z ]+")){
                System.out.println("Error: Name must contain letters only!\n");
            }
            else if (newName.equals(oldName)) {
                System.out.println("New name cannot same as old Name!\n");
            } else {
                return newName.toUpperCase();
            }
        }
    }
    
    public String inputNewIC(ListInterface<Tutor> tutorList, String oldIC) {
        String newIC;
        while (true){
            System.out.print("Enter New IC Number : ");
            newIC = sc.nextLine();
            if(!newIC.matches("\\d{12}")){
                System.out.println("Error: IC must contain 12 digits numbers only!\n");
            }
            else if (newIC.equals(oldIC)) {
                System.out.println("Error: New IC number cannot same as old IC number!\n");
            } 
            else if(duplicatedIC(newIC, tutorList)){
                System.out.println("Error: The IC Number Already Exist For Other Tutor!\n");
            }
            else {
                return newIC;
            }
        }
    }
    
    public String inputNewWorkType(String oldWorkType){
        int choice = 0;
        while(true){
            System.out.println("Choose Tutor's Work Type");
            System.out.println("1. Full Time");
            System.out.println("2. Part Time");
            System.out.print("Enter choice : ");

            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1:
                        if(oldWorkType.equals("Full Time")){
                            System.out.println("Error: New work type cannot same as old work type!\n");
                            break;
                        }
                        else
                            return "Full Time";
                    case 2:
                        if(oldWorkType.equals("Part Time")){
                            System.out.println("Error: New work type cannot same as old work type!\n");
                            break;
                        }
                        else
                            return "Part Time";
                    default:
                        System.out.println("Error: Please enter a valid choice (1 or 2)!\n");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a number 1 or 2!\n");
            }
        }
    }
    
    public String inputNewPhone(ListInterface<Tutor> tutorList, String oldPhoneNo) {
        String newPhoneNo;
        while (true) {
            System.out.print("Enter New Phone.No : ");
            newPhoneNo = sc.nextLine();
            if(!newPhoneNo.matches("0\\d{9,10}")){
                System.out.println("Error: Phone Number is not valid!\n");
            }
            else if (newPhoneNo.equals(oldPhoneNo)) {
                System.out.println("Error: New Phone cannot same with old Phone!\n");
            }
            else if(duplicatedPhoneNo(newPhoneNo, tutorList)){
                System.out.println("Error: The Phone Number Already Exist For Other Tutor!\n");
            }
            else
                return newPhoneNo;
        }
    }
    
    public String inputNewFaculty(String oldFaculty){
        String newFaculty;
        while (true) {
            System.out.print("Enter New Faculty : ");
            newFaculty = sc.nextLine();
            if (!newFaculty.matches("[a-zA-Z]+")) {
                System.out.println("Error: Faculty must contain letters only!\n");
            }
            else if (newFaculty.length() != 4) {
                System.out.println("Error: Faculty must be 4 characters only!\n");
            }
            else if (newFaculty.equals(oldFaculty)) {
                System.out.println("Error: New faculty cannot same as old faculty!\n");
            } else {
                return newFaculty.toUpperCase();
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="$ Assign Tutor $">
    public String checkClassType(char classTypeChar){
        String classType = "";
        switch (classTypeChar) {
            case 'T':
                classType = "Tutorial";
                break;
            case 'P':
                classType = "Practical";
                break;
            case 'L':
                classType = "Lecture";
                break;
        }
        return classType;
    }

    public char inputClassType(Tutor tutor, Course course, ListInterface<TutorClass> tutorClass){
        while(true){
            ListInterface<String> classTypeList = new DoublyCircularLinkedList<>();
            classTypeList.add("Tutorial");
            classTypeList.add("Practical");
            if(tutor.getWorkType().equals("Full Time")){
                classTypeList.addFront("Lecture");
            }
            for(int i = 0; i < tutorClass.getSize(); i++){
                TutorClass currentTutorClass = tutorClass.getPosition(i);
                if(currentTutorClass.getCourse().equals(course) && currentTutorClass.getClassType() == 'L'){
                    classTypeList.remove("Lecture");
                }
            }
            for(int j = 0; j < tutorClass.getSize(); j++) {
                TutorClass currentTutorClass = tutorClass.getPosition(j);
                if (currentTutorClass.getTutor().equals(tutor) && currentTutorClass.getCourse().equals(course)){
                    char assignedClassType = currentTutorClass.getClassType();
                    switch (assignedClassType) {
                        case 'T':
                            classTypeList.remove("Tutorial");
                            break;
                        case 'P':
                            classTypeList.remove("Practical");
                            break;
                        case 'L':
                            classTypeList.remove("Lecture");
                            break;
                        default:
                            System.out.println("The class is fully assigned!");
                    }
                }
            }
            if(classTypeList.isEmpty()){
                System.out.println("The tutor has been assigned to all classes for this course!");
                return ' ';
            }
            System.out.println("\nSelect Class Type");
            System.out.println("=".repeat(25));
            for(int i = 0; i < classTypeList.getSize(); i++){
                System.out.printf("| %-22s|\n", (i+1) + ". " + classTypeList.getPosition(i));
            }
            System.out.println("=".repeat(25));
            System.out.print("Enter choice : ");
            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 1 && choice <= classTypeList.getSize()) {
                    return classTypeList.getPosition(choice - 1).charAt(0);
                } else {
                    System.out.println("Error: Please enter a valid choice (1-" + classTypeList.getSize() + ")!");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a number 1-" + classTypeList.getSize() + "!");
            }
        }
    }

    public char selectionClassTypeForCourse(ListInterface<TutorClass> tutorClassList, Course course, int lectureOption){
    ListInterface<String> classTypeList = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if(currentClass.getCourse().equals(course)) {
                if (lectureOption == 1) {
                    switch (currentClass.getClassType()) {
                        case 'T':
                            classTypeList.addBack("Tutorial");
                            break;
                        case 'P':
                            classTypeList.addBack("Practical");
                            break;
                        case 'L':
                            classTypeList.addFront("Lecture");
                            break;
                    }
                } else {
                    switch (currentClass.getClassType()) {
                        case 'T':
                            classTypeList.addBack("Tutorial");
                            break;
                        case 'P':
                            classTypeList.addFront("Practical");
                            break;
                    }
                }
            }
        }
        while (true) {
            System.out.println("\nClass Type");
            System.out.println("=".repeat(25));
            for(int i = 0; i < classTypeList.getSize(); i++){
                System.out.printf("| %-22s|\n", (i+1) + ". " + classTypeList.getPosition(i));
            }
            System.out.println("=".repeat(25));
            System.out.print("Enter choice : ");
            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 1 && choice <= classTypeList.getSize()) {
                    return classTypeList.getPosition(choice - 1).charAt(0);
                } else {
                    System.out.println("Error: Please enter a valid choice (1-" + classTypeList.getSize() + ")!");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a number 1-" + classTypeList.getSize() + "!");
            }
        }
    }

    public int inputClassDuration(){
        while(true){
            try {
                System.out.print("Enter Class Duration (1 or 2 Hours): ");
                int hour =sc.nextInt();
                sc.nextLine();
                if(hour != 1 && hour != 2){
                    System.out.println("Error: The class duration must be 1 or 2 hours only!");
                }
                else{
                    return hour;
                }
            }catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a number 1 or 2!");
            }
        }
    }

    public boolean displayCoursesInFaculty(ListInterface<Course> savedCourses) {
        boolean courseValid = false;
        int index = 0;
        System.out.println("\nCourse List");
        System.out.println("=".repeat(60));
        System.out.printf("| %-7s %-13s %-35s|\n", "Index", "Course Code", "Course Name");
        System.out.println("=".repeat(60));
        for (int i = 0; i < savedCourses.getSize(); i++) {
            Course currentCourse = savedCourses.getPosition(i);
            System.out.printf("| %-7s %-13s %-35s|\n", index += 1, currentCourse.getCourseCode(), currentCourse.getCourseName());
            courseValid = true;
        }
        if (!courseValid) {
            System.out.println("Error: The Courses Not Yet Assigned To Any Programmes!");
        }
        System.out.println("=".repeat(60));
        return courseValid;
    }

    public ListInterface<Tutor> displayTutorsInCourse(ListInterface<TutorClass> tutorClassList, Course selectedCourse, char selectedClassType) {
        ListInterface<Tutor> tutors = new DoublyCircularLinkedList<>();
        String classType = "";
        System.out.println("\nCourse: " + selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName());
        switch (selectedClassType) {
            case 'T':
                classType = "Tutorial";
                break;
            case 'P':
                classType = "Practical";
                break;
            case 'L':
                classType = "Lecture";
                break;
        }
        System.out.println("Class Type: " + classType);
        System.out.println("=".repeat(44));
        System.out.printf("| %-7s %-12s %-20s|\n","Index", "Tutor ID", "Tutor Name");
        System.out.println("=".repeat(44));
        int index = 0;
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getClassType() == selectedClassType && currentClass.getCourse().equals(selectedCourse)) {
                Tutor tutor = currentClass.getTutor();
                System.out.printf("| %-7s %-12s %-20s|\n", index += 1, tutor.getId(), tutor.getName());
                tutors.add(tutor);
            }
        }
        System.out.println("=".repeat(44));
        return tutors;
    }

    public ListInterface<TutorialGroup> displayTutorialGroupsInCourse(ListInterface<TutorClass> tutorClassList, Course selectedCourse, char selectedClassType) {
        ListInterface<TutorialGroup> tutorialGroups = getTutorialGroupsInCourse(selectedCourse);
        for(int i = 0; i < tutorClassList.getSize(); i++){
            TutorClass currentClass = tutorClassList.getPosition(i);
            if(currentClass.getCourse().equals(selectedCourse) && currentClass.getClassType() == selectedClassType){
                for(int j = 0; j < currentClass.getTutorialGroups().getSize(); j++){
                    TutorialGroup currentTG = currentClass.getTutorialGroups().getPosition(j);
                    if(tutorialGroups.contains(currentTG)){
                        tutorialGroups.remove(currentTG);
                    }
                }
            }
        }
        if(tutorialGroups.isEmpty()){
            System.out.println("No tutorial group available for this course!");
            return tutorialGroups;
        }
        System.out.println("\nTutorial Groups for Course: " + selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName());
        System.out.println("=".repeat(27));
        System.out.printf("| %-5s %-18s|\n", "Index", "Tutorial Group ID");
        System.out.println("=".repeat(27));
        for (int i = 0; i < tutorialGroups.getSize(); i++) {
            System.out.printf("| %-5s %-18s|\n", (i + 1), tutorialGroups.getPosition(i).getTutorialGroupId());
        }

        System.out.println("=".repeat(27));
        return tutorialGroups;
    }

    public ListInterface<TutorialGroup> getTutorialGroupsInCourse(Course selectedCourse){
        ListInterface<TutorialGroup> tutorialGroups = new DoublyCircularLinkedList<>();
        for (int i = 0; i < selectedCourse.getProgrammesAssociated().getSize(); i++) {
            Programme currentProgramme = selectedCourse.getProgrammesAssociated().getPosition(i);
            for (int j = 0; j < currentProgramme.getTutorialGroups().getSize(); j++) {
                TutorialGroup currentTG = currentProgramme.getTutorialGroups().getPosition(j);
                tutorialGroups.add(currentTG);
            }
        }
        return tutorialGroups;
    }

    public void displayTutorialGroups(Course selectedCourse) {
        ListInterface<TutorialGroup> tutorialGroups = getTutorialGroupsInCourse(selectedCourse);
        System.out.println("\nTutorial Groups");
        System.out.println("=======================================================");
        System.out.printf("%-5s %-18s\n", "Index", "Tutorial Group ID");
        System.out.println("=======================================================");
        for (int i = 0; i < tutorialGroups.getSize(); i++) {
            System.out.printf("%-5s %-18s\n", (i + 1), tutorialGroups.getPosition(i).getTutorialGroupId());
        }
        System.out.println("=======================================================");
    }

    public boolean displayCourseInClass(ListInterface<Course> courseList) {
        boolean valid = false;
        ListInterface<Course> courses = new DoublyCircularLinkedList<>();
        for (int i = 0; i < courseList.getSize(); i++) {
            courses.add(courseList.getPosition(i));
            valid = true;
        }
        if (!valid) {
            System.out.println("The courses haven't assigned to any tutor!");
        }
        else{
            System.out.println("\n" + "=".repeat(61));
            System.out.printf("| %-7s %-14s %-35s|\n", "Index", "Course Code", "Course Name");
            System.out.println("=".repeat(61));
            for (int i = 0; i < courses.getSize(); i++) {
                Course currentCourse = courses.getPosition(i);
                System.out.printf("| %-7s %-14s %-35s|\n", (i + 1), currentCourse.getCourseCode(), currentCourse.getCourseName());
            }
            System.out.println("=".repeat(61));
        }
        return valid;
    }

    public ListInterface<Course> displayCoursesForTG(ListInterface<Course> courses){
        ListInterface<Course> coursesForTG = courses;
        System.out.println("\nCourses");
        System.out.println("=".repeat(50));
        System.out.printf("%-5s %-30s\n", "Index", "Course");
        System.out.println("=".repeat(50));
        coursesForTG = sortCourseByName(coursesForTG);
        for (int i = 0; i < coursesForTG.getSize(); i++) {
            System.out.printf("%-5s %-30s\n", (i + 1), coursesForTG.getPosition(i).getCourseCode() + " - " + coursesForTG.getPosition(i).getCourseName());
        }
        System.out.println("=".repeat(50));
        return courses;
    }

    public ListInterface<TutorClass> displayTutorForTG(ListInterface<TutorClass> tutorClassList, Course selectedCourse, TutorialGroup selectedTG){
        ListInterface<TutorClass> tutorClasses = new DoublyCircularLinkedList<>();
        ListInterface<TutorialGroup> assignedTTG = new DoublyCircularLinkedList<>();
        ListInterface<TutorialGroup> assignedPTG = new DoublyCircularLinkedList<>();
        int index = 0;
        for(int i = 0; i < tutorClassList.getSize(); i++){
            TutorClass currentClass = tutorClassList.getPosition(i);
            if(currentClass.getCourse().equals(selectedCourse) && currentClass.getClassType() == 'T'){
                for(int j = 0; j < currentClass.getTutorialGroups().getSize(); j++){
                    TutorialGroup currentTG = currentClass.getTutorialGroups().getPosition(j);
                    assignedTTG.add(currentTG);
                }
            }
        }
        for(int i = 0; i < tutorClassList.getSize(); i++){
            TutorClass currentClass = tutorClassList.getPosition(i);
            if(currentClass.getCourse().equals(selectedCourse) && currentClass.getClassType() == 'P'){
                for(int j = 0; j < currentClass.getTutorialGroups().getSize(); j++){
                    TutorialGroup currentTG = currentClass.getTutorialGroups().getPosition(j);
                    assignedPTG.add(currentTG);
                }
            }
        }
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getCourse().equals(selectedCourse)) {
                if(!assignedTTG.contains(selectedTG) && currentClass.getClassType() == 'T') {
                    tutorClasses.add(currentClass);
                }
                if(!assignedPTG.contains(selectedTG) && currentClass.getClassType() == 'P') {
                    tutorClasses.add(currentClass);
                }
            }
        }
        tutorClasses = sortTutorClassByClassType(tutorClasses);
        if(tutorClasses.isEmpty()){
            System.out.println("Tutors of " + selectedCourse.getCourseName() + " is assigned for this tutorial group!");
            return tutorClasses;
        }
        System.out.println("\nCourse: " + selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName());
        System.out.println("=======================================================");
        System.out.printf("%-5s %-12s %-14s %-14s\n", "Index", "Tutor ID", "Tutor Name", "Class Type");
        System.out.println("=======================================================");
        for(int i = 0; i < tutorClasses.getSize(); i++){
            Tutor tutor = tutorClasses.getPosition(i).getTutor();
            System.out.printf("%-5s %-12s %-14s %-14s\n", index += 1, tutor.getId(), tutor.getName(), checkClassType(tutorClasses.getPosition(i).getClassType()));
        }
        System.out.println("=======================================================");
        return tutorClasses;
    }

    public int inputRandomClassToAssign(){
        int num;
        while (true) {
            try {
                System.out.println("How Many Class You Want To Assign?");
                System.out.print("Enter Number (0 to back) : ");
                num = sc.nextInt();
                sc.nextLine();
                return num;
            } catch(InputMismatchException e){
                sc.nextLine();
                System.out.println("Error: Invalid input. Please enter a valid number!");
            }
        }
    }
    // </editor-fold>

    public double calculatePartTimeFee(int classDuration, int creditHour, int totalTutorialGroups){
        return classDuration * (30 + creditHour) * totalTutorialGroups * 14;
    }

    public void displayPartTimeTutorReport(ListInterface<Tutor> tutorList, ListInterface<TutorClass> tutorClassList) {
        Tutor bestTutor = null;
        Tutor worstTutor = null;
        Tutor tmpTutor;
        double totalEarnings;
        double highestEarnings = 0;
        double lowestEarnings = 999999;
        int totalTG;
        int totalHours;
        int highestHours = 0;
        int lowestHours = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("\n" + "=".repeat(135));
        System.out.printf("%-135s\n", " ".repeat(40) + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.printf("%-135s\n", " ".repeat(52) + "TEACHING ASSIGNMENT SUBSYSTEM");
        System.out.printf("%-135s\n", " ");
        System.out.printf("%-135s\n", " ".repeat(50) + "PART TIME TUTOR FINANCIAL REPORT");
        System.out.printf("%-135s\n", " ".repeat(50) + "-".repeat(32));
        System.out.printf("%-135s\n", "Generated at: " + formattedDateTime);
        System.out.println("=".repeat(135) + "\n");
        System.out.printf("%-30s %-40s %-12s %-24s %-12s %-12s\n", "Tutor Name", "Course", "Class Type", "Total Tutorial Group", "Hour/CLass", "Amount (RM)");
        System.out.printf("%-30s %-40s %-12s %-24s %-12s %-12s\n", "-".repeat(10), "-".repeat(6), "-".repeat(10), "-".repeat(20), "-".repeat(10), "-".repeat(11));
        for (int j = 0; j < tutorList.getSize(); j++) {
            Tutor currentTutor = tutorList.getPosition(j);
            if (currentTutor.getWorkType().equals("Part Time")) {
                ListInterface<TutorClass> showedClass = new DoublyCircularLinkedList<>();
                ListInterface<TutorClass> classToDisplay = new DoublyCircularLinkedList<>();
                boolean showed = false;
                tmpTutor = currentTutor;
                for (int i = 0; i < tutorClassList.getSize(); i++) {
                    TutorClass currentClass = tutorClassList.getPosition(i);
                    if (currentClass.getTutor().equals(currentTutor)) {
                        classToDisplay.add(currentClass);
                    }
                }
                totalEarnings = 0;
                classToDisplay = sortTutorClassByClassCourse(classToDisplay);
                for (int i = 0; i < classToDisplay.getSize(); i++) {
                    TutorClass currentClass = classToDisplay.getPosition(i);
                    totalTG = currentClass.getTutorialGroups().getSize();
                    if (totalTG != 0) {
                        if (!showed) {
                            showed = true;
                            showedClass.add(currentClass);
                            System.out.printf("%-25s %-45s %-22s %-19s %-10s %-9s\n",
                                    currentTutor.getName(), currentClass.getCourse().getCourseCode() + " - " + currentClass.getCourse().getCourseName(), checkClassType(currentClass.getClassType()), totalTG,
                                    currentClass.getClassDuration(), String.format("%.2f", calculatePartTimeFee(currentClass.getClassDuration(), currentClass.getCourse().getCreditHours(), totalTG)));
                        } else {
                            if (!showedClass.contains(currentClass)) {
                                showedClass.add(currentClass);
                                System.out.printf("%-25s %-45s %-22s %-19s %-10s %-9s\n", " ",
                                        currentClass.getCourse().getCourseCode() + " - " + currentClass.getCourse().getCourseName(), checkClassType(currentClass.getClassType()), totalTG,
                                        currentClass.getClassDuration(), String.format("%.2f", calculatePartTimeFee(currentClass.getClassDuration(), currentClass.getCourse().getCreditHours(), totalTG)));
                            }
                        }
                        totalEarnings += calculatePartTimeFee(currentClass.getClassDuration(), currentClass.getCourse().getCreditHours(), currentClass.getTutorialGroups().getSize());
                    }
                }
                totalHours = 18 - currentTutor.getTeachHour();
                if (showed) {
                    System.out.printf("%-135s\n", " ".repeat(123) + "-".repeat(12));
                    System.out.printf("%-135s\n", " ".repeat(126) + String.format("%.2f",totalEarnings));
                    System.out.println("-".repeat(135));
                }
                if(highestEarnings < totalEarnings){
                    highestEarnings = totalEarnings;
                    highestHours = totalHours;
                    bestTutor = tmpTutor;
                }
                if(lowestEarnings > totalEarnings && totalEarnings != 0){
                    lowestEarnings = totalEarnings;
                    lowestHours = totalHours;
                    worstTutor = tmpTutor;
                }
            }
        }
        System.out.println("\nHighest Revenue Part Time Tutor:");
        System.out.printf("\t%-20s %-20s\n", "Tutor", ": " + bestTutor.getName());
        System.out.printf("\t%-20s %-20s\n", "Total Revenue", ": RM " + String.format("%.2f",highestEarnings));
        System.out.printf("\t%-20s %-20s\n", "Total Teach Hours", ": " + highestHours);
        System.out.println("\nLowest Revenue Part Time Tutor:");
        System.out.printf("\t%-20s %-20s\n", "Tutor", ": " + worstTutor.getName());
        System.out.printf("\t%-20s %-20s\n", "Total Revenue", ": RM " + String.format("%.2f",lowestEarnings));
        System.out.printf("\t%-20s %-20s\n", "Total Teach Hours", ": " + lowestHours);
        System.out.printf("%-135s\n", " ".repeat(46) + "END OF THE PART TIME TUTOR FINANCIAL REPORT");
        System.out.println("=".repeat(135));
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void displayTutorAssignmentReport(ListInterface<Tutor> tutorList, ListInterface<TutorClass> tutorClassList) {
        Tutor tmpTutor;
        ListInterface<Tutor> highestClassTutor = new DoublyCircularLinkedList<>();
        ListInterface<Tutor> lowestClassTutor = new DoublyCircularLinkedList<>();
        int totalTeachHour = 0;
        int totalClass;
        int totalClassTeach = 0;
        int highestClass = 0;
        int lowestClass = 9999;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("\n" + "=".repeat(121));
        System.out.printf("%-135s\n", " ".repeat(33) + "TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.printf("%-135s\n", " ".repeat(45) + "TEACHING ASSIGNMENT SUBSYSTEM");
        System.out.printf("%-135s\n", " ");
        System.out.printf("%-135s\n", " ".repeat(45) + "TUTOR CLASS ASSIGNMENT REPORT");
        System.out.printf("%-135s\n", " ".repeat(45) + "-".repeat(29));
        System.out.printf("%-135s\n", "Generated at: " + formattedDateTime);
        System.out.println("=".repeat(121) + "\n");
        System.out.printf("%-30s %-40s %-12s %-12s %-12s %-12s\n", "Tutor Name", "Course", "Class type", "Total Class", "Hour/CLass", "Teach Hour");
        System.out.printf("%-30s %-40s %-12s %-12s %-12s %-12s\n", "-".repeat(10), "-".repeat(6), "-".repeat(10), "-".repeat(11), "-".repeat(10), "-".repeat(10));
        for (int j = 0; j < tutorList.getSize(); j++) {
            Tutor currentTutor = tutorList.getPosition(j);
            ListInterface<TutorClass> classToDisplay = new DoublyCircularLinkedList<>();
            boolean showed = false;
            totalClassTeach = 0;
            for (int i = 0; i < tutorClassList.getSize(); i++) {
                TutorClass currentClass = tutorClassList.getPosition(i);
                if (currentClass.getTutor().equals(currentTutor)) {
                    classToDisplay.add(currentClass);
                }
            }
            classToDisplay = sortTutorClassByClassCourse(classToDisplay);
            for (int i = 0; i < classToDisplay.getSize(); i++) {
                TutorClass currentClass = classToDisplay.getPosition(i);
                if(currentClass.getTutorialGroups().getSize() != 0){
                    totalClass = currentClass.getTutorialGroups().getSize();
                    if(currentClass.getClassType() == 'L'){
                        totalClass = 1;
                    }
                    if (!showed) {
                        showed = true;
                        System.out.printf("%-25s %-45s %-17s %-11s %-13s %-8s\n",
                                currentTutor.getName(), currentClass.getCourse().getCourseCode() + " - " + currentClass.getCourse().getCourseName(), checkClassType(currentClass.getClassType()),
                                totalClass, currentClass.getClassDuration(), totalClass * currentClass.getClassDuration());
                    } else {
                        System.out.printf("%-25s %-45s %-17s %-11s %-13s %-8s\n", " ",
                                currentClass.getCourse().getCourseCode() + " - " + currentClass.getCourse().getCourseName(), checkClassType(currentClass.getClassType()),
                                totalClass, currentClass.getClassDuration(), totalClass * currentClass.getClassDuration());
                    }
                    totalClassTeach += totalClass;
                }
            }
            totalTeachHour = 18 - currentTutor.getTeachHour();
            if(highestClass < totalClassTeach){
                highestClass = totalClassTeach;
            }
            if(lowestClass > totalClassTeach && totalClassTeach != 0){
                lowestClass = totalClassTeach;
            }
            if (showed) {
                System.out.printf("%121s\n", " ".repeat(83) + "-".repeat(11) + " ".repeat(15) + "-".repeat(10));
                System.out.printf("%-25s %-45s %-17s %-11s %-13s %-8s\n", " ", " ", " ", totalClassTeach, " ",  totalTeachHour);
                System.out.println("-".repeat(121));
            }
        }
        for (int j = 0; j < tutorList.getSize(); j++) {
            Tutor currentTutor = tutorList.getPosition(j);
            ListInterface<TutorClass> classToDisplay = new DoublyCircularLinkedList<>();
            tmpTutor = currentTutor;
            totalClassTeach = 0;
            for (int i = 0; i < tutorClassList.getSize(); i++) {
                TutorClass currentClass = tutorClassList.getPosition(i);
                if (currentClass.getTutor().equals(currentTutor)) {
                    classToDisplay.add(currentClass);
                }
            }
            classToDisplay = sortTutorClassByClassCourse(classToDisplay);
            for (int i = 0; i < classToDisplay.getSize(); i++) {
                TutorClass currentClass = classToDisplay.getPosition(i);
                if(currentClass.getTutorialGroups().getSize() != 0){
                    totalClass = currentClass.getTutorialGroups().getSize();
                    if(currentClass.getClassType() == 'L'){
                        totalClass = 1;
                    }
                    totalClassTeach += totalClass;
                }
            }
            if(highestClass < totalClassTeach){
                highestClass = totalClassTeach;
                highestClassTutor.add(tmpTutor);
            }
            else if(highestClass == totalClassTeach){
                highestClassTutor.add(tmpTutor);
            }
            if(lowestClass > totalClassTeach && totalClassTeach != 0){
                lowestClass = totalClassTeach;
                lowestClassTutor.add(tmpTutor);
            }
            else if(lowestClass == totalClassTeach){
                lowestClassTutor.add(tmpTutor);
            }
        }
        System.out.println("\nHighest Class Handled:");
        System.out.printf("\t%-15s %-2s", "Tutor", ": ");
        for(int i = 0; i < highestClassTutor.getSize(); i++){
            System.out.print(highestClassTutor.getPosition(i).getName());
            if(i != highestClassTutor.getSize() - 1){
                System.out.print(", ");
            }
        }
        System.out.printf("\n\t%-15s %-20s\n", "Total Class", ": " + highestClass);
        System.out.println("\nLowest Class Handled:");
        System.out.printf("\t%-15s %-2s", "Tutor", ": ");
        for (int i = 0; i < lowestClassTutor.getSize(); i++) {
            System.out.print(lowestClassTutor.getPosition(i).getName());
            if(i != lowestClassTutor.getSize() - 1){
                System.out.print(", ");
            }
        }
        System.out.printf("\n\t%-15s %-20s\n", "Total Class", ": " + lowestClass);
        System.out.printf("%-135s\n", " ".repeat(45) + "END OF THE TUTOR ASSIGNMENT REPORT");
        System.out.println("=".repeat(121));
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public ListInterface<TutorClass> sortTutorClassByClassType(ListInterface<TutorClass> tutorClassList) {

        Comparator<TutorClass> comparator = new Comparator<TutorClass>() {
            @Override
            public int compare(TutorClass tc1, TutorClass tc2) {
                return Character.compare(tc1.getClassType(), tc2.getClassType());
            }
        };
        ListInterface<TutorClass> tutorClasses = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            tutorClasses.add(tutorClassList.getPosition(i));
        }
        tutorClasses.bubbleSort(comparator);
        return tutorClasses;
    }

    public ListInterface<TutorClass> sortTutorClassByClassCourse(ListInterface<TutorClass> tutorClassList) {
        Comparator<TutorClass> comparator = new Comparator<TutorClass>() {
            @Override
            public int compare(TutorClass tc1, TutorClass tc2) {
                return tc1.getCourse().getCourseName().compareTo(tc2.getCourse().getCourseName());            }
        };
        ListInterface<TutorClass> tutorClasses = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            tutorClasses.add(tutorClassList.getPosition(i));
        }
        tutorClasses.bubbleSort(comparator);
        return tutorClasses;
    }

    public ListInterface<Course> sortCourseByName(ListInterface<Course> courseList) {
        Comparator<Course> comparator = new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getCourseName().compareTo(c2.getCourseName());            }
        };
        ListInterface<Course> courses = new DoublyCircularLinkedList<>();
        for (int i = 0; i < courseList.getSize(); i++) {
            courses.add(courseList.getPosition(i));
        }
        courses.bubbleSort(comparator);

        return courses;
    }
}
