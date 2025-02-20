/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import boundary.CourseManagementUI;
import dao.DoublyCircularLinkedListDAO;
import entity.*;
import entity.Student.CourseStatus;
import java.io.Serializable;
import java.time.LocalDate;
import utility.Utility;

/**
 *
 * @author jsony
 */
public class CourseManagement implements Serializable {

    private CourseManagementUI courseUI = new CourseManagementUI();
    private ListInterface<Course> courseList = new DoublyCircularLinkedList<>();
    private DoublyCircularLinkedListDAO dao = new DoublyCircularLinkedListDAO();
    private ListInterface<String> facultyList = new DoublyCircularLinkedList<>();
    DoublyCircularLinkedList<Programme> savedProgramme = dao.loadFromFile("Programme");
    DoublyCircularLinkedList<Student> studentList = dao.loadFromFile("Student");
    DoublyCircularLinkedList<TutorClass> tutorClassList = dao.loadFromFile("TutorClass");

    public CourseManagement() {
        Object retrievedData = dao.loadFromFile("Course");
        if (retrievedData != null && retrievedData instanceof ListInterface) {
            courseList = (ListInterface<Course>) retrievedData;
        } else {
            courseList = new DoublyCircularLinkedList<>();
        }
    }

    public void runCourseManagement() {
        int choice = 0;
        do {
            choice = courseUI.getChoice();
            switch (choice) {
                case 0 ->
                    System.out.println("Quit course management....");
                case 1 -> {
                    Utility.clearScreen();
                    addCourse();
                }
                case 2 -> {
                    Utility.clearScreen();
                    Course course = manageCourseStructureMenu();
                    manageCourseStructure(course);
                }
                case 3 -> {
                    Utility.clearScreen();
                    removeCourse();
                }
                case 4 -> {
                    Utility.clearScreen();
                    findCourse();
                }
                case 5 -> {
                    Utility.clearScreen();
                    updateCourseDetails();
                }
                case 6 -> {
                    Utility.clearScreen();
                    displayAllCourse();
                }
                case 7 -> {
                    Utility.clearScreen();
                    displayAllCourseUnderFaculty();
                }
                case 8 -> {
                    Utility.clearScreen();
                    addProgrammeToCourse();
                }
                case 9 -> {
                    Utility.clearScreen();
                    removeProgrammeFromCourse();
                }
                case 10 -> {
                    Utility.clearScreen();
                    manageCreditTransfer();
                }
                case 11 -> {
                    Utility.clearScreen();
                    GenerateReport();
                }
                default -> {
                    System.out.println("Please choose 1 to 8 selection");
                }
            }
        } while (choice != 0);
    }

    public void addCourse() {
        int confirmation;
        boolean success = false;

        Course newCourse;

        do {
            Utility.clearScreen();
            newCourse = courseUI.inputCourseDetails();

            if (!courseList.isEmpty() && courseList.contains(newCourse)) {
                courseUI.duplicatedCourse(newCourse.getCourseCode());
                char choice;
                do {
                    choice = courseUI.recreateNewCourse();
                } while (choice != 'y' && choice != 'n');

                if (choice == 'n') {
                    System.out.println("Exiting..............");
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    return;
                }
            }
        } while (!courseList.isEmpty() && courseList.contains(newCourse));

        courseUI.showCourseDetails(newCourse);
        do {
            confirmation = courseUI.actionConfirmation("add this course");
            switch (confirmation) {
                case 1:
                    courseList.add(newCourse);
                    System.out.println("Course Added Successfully");
                    dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                    success = true;
                    break;
                case 2:
                    System.out.println("Exiting.....\n");
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    success = true;
                    return;
                default:
                    courseUI.invalidNumberOption(1, 2);
            }
        } while (!success);

        char proceedOption = courseUI.proceedManageCourseStructure();
        if (proceedOption == 'y') {
            manageCourseStructure(newCourse);
        } else {
            System.out.println("Exiting.....\n");
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }
    }

    public void removeCourse() {
        boolean success = false;

        if (!courseList.isEmpty()) {
            courseUI.removeCourseMenu(courseList);
            int courseSelection = courseUI.RemoveQuestion();
            Course selectedCourse = courseList.getPosition(courseSelection - 1);
            courseUI.printSelectedCourseDetails(selectedCourse);
            int choice = 0;

            do {
                choice = courseUI.actionConfirmation("remove this course");
                switch (choice) {
                    case 1 -> {
                        if (courseSelection == courseList.getSize()) {
                            courseList.removeBack();
                            courseUI.displayProgrammeRemovedMessage();
                            dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                            for (int i = tutorClassList.getSize() - 1; i >= 0; i--) {
                                TutorClass currentClass = tutorClassList.getPosition(i);
                                if (currentClass.getCourse().equals(selectedCourse)) {
                                    tutorClassList.removeAtPosition(i);
                                }
                            }
                            dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                            success = true;
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            break;
                        } else {
                            courseList.removeAtPosition(courseSelection - 1);
                            courseUI.displayProgrammeRemovedMessage();
                            dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                            for (int i = tutorClassList.getSize() - 1; i >= 0; i--) {
                                TutorClass currentClass = tutorClassList.getPosition(i);
                                if (currentClass.getCourse().equals(selectedCourse)) {
                                    tutorClassList.removeAtPosition(i);
                                }
                            }
                            dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                            success = true;
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            break;
                        }
                    }
                    case 2 -> {
                        System.out.println("Exiting.....\n");
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        success = true;
                    }
                    default ->
                        courseUI.invalidNumberOption(1, 2);
                }
            } while (success == false);
        } else {
            System.out.println("There is no available course");
        }
    }

    public void findCourse() {
        int choice = courseUI.searchMenu();
        if (choice == 0) {
            System.out.println("Exiting..........");
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }

        while (choice != 1 && choice != 2) {
            System.out.println("Please enter a valid choice.");
            choice = courseUI.searchMenu();
        }

        switch (choice) {
            case 1 -> {
                Utility.clearScreen();
                findCourseBySelecting();
            }
            case 2 -> {
                Utility.clearScreen();
                findCourseByTyping();
            }
        }
    }

    public void findCourseBySelecting() {
        courseUI.showCourseCodeWithName(courseList);
        int choice = courseUI.inputRowNum("a course", "show the details");
        while (choice < 1 || choice > courseList.getSize()) {
            System.out.println("Invalid row. Please select a row listed above.");
            choice = courseUI.inputRowNum("a course", "show the details");
        }

        Course selectedCourse = courseList.getPosition(choice - 1);
        courseUI.printSelectedCourseDetails(selectedCourse);

        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void findCourseByTyping() {
        String targetCourse;
        if (courseList.isEmpty()) {
            System.out.println("There is no available course.");
            runCourseManagement();
        }
        targetCourse = courseUI.inputCourseCodeToSearch();
        Course foundCourse = courseList.find(new Course(targetCourse, "", 0));
        if (foundCourse != null) {
            courseUI.printSelectedCourseDetails(foundCourse);
            courseUI.SuccessfulMsg();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else {
            courseUI.courseNotFound(targetCourse);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }
    }

    public void updateCourseDetails() {
        int index, attributeNumber;
        char continueOption;
        if (courseList.isEmpty()) {
            System.out.println("There is no available course.");
            runCourseManagement();
        }
        do {
            Utility.clearScreen();
            courseUI.amendCourseMenu(courseList);
            index = courseUI.inputRowNum("amend");
            if (index >= 1 && index <= courseList.getSize()) {
                Course courseToEdit = courseList.getPosition(index - 1);
                System.out.println("");
                courseUI.showCourseDetails(courseToEdit);
                Course newCourse = new Course(courseToEdit.getCourseCode(),
                        courseToEdit.getCourseName(),
                        courseToEdit.getCreditHours());
                newCourse.setProgrammesAssociated(courseToEdit.getProgrammesAssociated());

                do {
                    System.out.println("");
                    attributeNumber = courseUI.getAmmendCourseMenuOption();

                    switch (attributeNumber) {
                        case 1 -> {
                            String newCourseName = courseUI.inputNewCourseAttributeStr("name");
                            newCourse.setCourseName(newCourseName);
                            courseUI.courseAttributeUpdated("name");
                        }
                        case 2 -> {
                            int newCreditHours = courseUI.inputNewCourseAttributeInt("credit hours");
                            newCourse.setCreditHours(newCreditHours);
                            newCourse.setCourseFee(newCreditHours);
                            courseUI.courseAttributeUpdated("credit hours");
                        }
                        case 0 -> {
                            System.out.println("Exiting amending module........");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            return;
                        }
                        default ->
                            courseUI.invalidNumberOption(0, 1, 2);
                    }

                    continueOption = courseUI.getContinueOption("this programme");
                    while (continueOption != 'y' && continueOption != 'n') {
                        courseUI.invalidCharacterOption('y', 'n');
                        continueOption = courseUI.getContinueOption("this programme");
                    }
                } while (continueOption == 'y');
                courseList.update(courseToEdit, newCourse);
                dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                Utility.clearScreen();
                System.out.println("Here is the updated version:");
                courseUI.showCourseDetails(newCourse);
                System.out.println("");
            } else {
                System.out.println("There is no " + index + " row.");
            }
            continueOption = courseUI.getContinueOption("other programme");
            while (continueOption != 'y' && continueOption != 'n') {
                courseUI.invalidCharacterOption('y', 'n');
                continueOption = courseUI.getContinueOption("other programme");
            }
        } while (continueOption == 'y');
        System.out.println("Exiting amending module........");
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void displayAllCourse() {
        if (courseList.isEmpty()) {
            System.out.println("There is no available course.");
        } else {
            courseUI.listAllCourses(getAllCourses());
        }

        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void addProgrammeToCourse() {
        Course selectedCourse = null;
        Programme programmeToAdd = null;
        ListInterface<Programme> existingProgrammeList = new DoublyCircularLinkedList<>();

        courseUI.addProgrammeToCourseMenu(courseList);

        int courseChoice = courseUI.inputRowNum("add programme");
        while (courseChoice < 1 || courseChoice > courseList.getSize()) {
            System.out.println("Invalid row. Please select a row listed above.");
            courseChoice = courseUI.inputRowNum("add programme");
        }

        selectedCourse = courseList.getPosition(courseChoice - 1);
        ListInterface<Programme> programmesAssociated = selectedCourse.getProgrammesAssociated();

        int addChoice = courseUI.addProgramToCourseMenu();
        Utility.clearScreen();
        courseUI.printSelectedCourseDetails(selectedCourse);

        switch (addChoice) {
            case 1 -> {
                if (savedProgramme.isEmpty()) {
                    System.out.println("No programme available.\nYou must add programme first!\n");
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                } else {
                    while (true) {
                        int count = 1;
                        existingProgrammeList.reset();
                        System.out.println("\nProgramme available to add for course " + selectedCourse.getCourseCode() + "\n========================================================");
                        ListInterface<Programme> existingProgramme = selectedCourse.getProgrammesAssociated();
                        if (!existingProgramme.isEmpty()) {
                            for (int i = 0; i < savedProgramme.getSize(); i++) {
                                Programme currentProgramme = savedProgramme.getPosition(i);
                                if (currentProgramme != null && !existingProgramme.contains(currentProgramme)) {
                                    System.out.println(count + ". " + currentProgramme.getProgrammeCode() + " - " + currentProgramme.getProgrammeName());
                                    existingProgrammeList.add(currentProgramme);
                                    count++;
                                }
                            }
                            System.out.println("");
                        } else {
                            for (int i = 0; i < savedProgramme.getSize(); i++) {
                                System.out.println((i + 1) + ". " + savedProgramme.getPosition(i).getProgrammeCode() + " - " + savedProgramme.getPosition(i).getProgrammeName());
                                existingProgrammeList.add(savedProgramme.getPosition(i));
                            }
                            System.out.println("");
                        }

                        int programmeChoice = courseUI.inputRowNum("a programme", "to add to this course (0 to exit)");
                        if (programmeChoice == 0) {
                            System.out.println("Exiting...........");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            return;
                        }

                        programmeToAdd = existingProgrammeList.getPosition(programmeChoice - 1);

                        if (programmeToAdd != null) {
                            if (programmesAssociated.isEmpty() || !programmesAssociated.contains(programmeToAdd)) {
                                courseUI.showProgrammeDetails(programmeToAdd);
                                int confirmation = courseUI.actionConfirmation("add this programme to this course");
                                if (confirmation == 1) {
                                    programmesAssociated.addBack(programmeToAdd);
                                    enrollProgrammeStudentToNewCourse(programmeToAdd, selectedCourse);
                                    courseUI.displayProgrammeAddedMessage();
                                    dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                                } else {
                                    courseUI.ErrorMsg();
                                }
                            }
                        }

                        char continueOption = courseUI.getContinueAddingProgrammeOption();
                        while (continueOption != 'y' && continueOption != 'n') {
                            System.out.println("Please enter 'y' or 'n' only!");
                            continueOption = courseUI.getContinueAddingProgrammeOption();
                        }
                        if (continueOption != 'y') {
                            Utility.clearScreen();
                            System.out.println("Here is the latest version of course with programmes associated:");
                            courseUI.printSelectedCourseDetails(selectedCourse);
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            break;
                        }
                    }
                }
            }
            case 2 -> {
                if (savedProgramme.isEmpty()) {
                    System.out.println("No programme available.\nYou must add programme first!\n");
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                } else {
                    listAllFaculty();
                    int choice = courseUI.inputRowNum("a faculty", "add programmes");
                    while (choice < 1 && choice > facultyList.getSize()) {
                        System.out.println("Please enter a valid choice.\n");
                        choice = courseUI.inputRowNum("a faculty", "add programmes");
                    }

                    String faculty = facultyList.getPosition(choice - 1);

                    int count = 1;
                    existingProgrammeList.reset();
                    System.out.println("\nProgramme under Faculty (" + faculty + ") available to add for course " + selectedCourse.getCourseCode() + "\n========================================================================");
                    ListInterface<Programme> existingProgramme = selectedCourse.getProgrammesAssociated();
                    if (!existingProgramme.isEmpty()) {
                        for (int i = 0; i < savedProgramme.getSize(); i++) {
                            Programme currentProgramme = savedProgramme.getPosition(i);
                            if (currentProgramme.getFaculty().equals(faculty)) {
                                if (!existingProgramme.contains(currentProgramme)) {
                                    System.out.println(count + ". " + currentProgramme.getProgrammeCode() + " - " + currentProgramme.getProgrammeName());
                                    existingProgrammeList.add(currentProgramme);
                                    count++;
                                }
                            }
                        }
                        System.out.println("");
                    } else {
                        for (int i = 0; i < savedProgramme.getSize(); i++) {
                            Programme currentProgramme = savedProgramme.getPosition(i);
                            if (currentProgramme.getFaculty().equals(faculty)) {
                                System.out.println(count + ". " + currentProgramme.getProgrammeCode() + " - " + currentProgramme.getProgrammeName());
                                existingProgrammeList.add(currentProgramme);
                                count++;
                            }
                        }
                        System.out.println("");
                    }

                    if (existingProgrammeList.isEmpty()) {
                        System.out.println("No available programme to add for this course.");
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        return;
                    }

                    int confirmation = courseUI.actionConfirmation("add all these programmes to this course");
                    if (confirmation == 1) {
                        for (int i = 0; i < existingProgrammeList.getSize(); i++) {
                            Programme programme = existingProgrammeList.getPosition(i);
                            programmesAssociated.addBack(programme);
                            enrollProgrammeStudentToNewCourse(programme, selectedCourse);
                        }
                        courseUI.displayProgrammeAddedMessage();
                        dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                        Utility.clearScreen();
                        System.out.println("Here is the latest version of course with programmes associated:");
                        courseUI.printSelectedCourseDetails(selectedCourse);
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    } else {
                        courseUI.ErrorMsg();
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    }
                }
            }
        }

    }

    public void enrollProgrammeStudentToNewCourse(Programme programme, Course newCourse) {
        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);
            if (student.getStudentProgramme().equals(programme)) {
                student.getStudentCourseList().add(student.new CourseStatus(newCourse, "Main"));
                int totalCreditHour = student.getTotalCreditHour();
                totalCreditHour += newCourse.getCreditHours();
                student.setTotalCreditHour(totalCreditHour);
            }
        }
        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
    }

    public void removeProgrammeFromCourse() {
        char continueOption;
        courseUI.removeProgrammeFromCourseMenu(courseList);

        int courseChoice = courseUI.inputRowNum("remove programme from course");
        while (courseChoice < 1 || courseChoice > courseList.getSize()) {
            System.out.println("Invalid row. Please select a row listed above.");
            courseChoice = courseUI.inputRowNum("remove programme from course");
        }

        Course course = courseList.getPosition(courseChoice - 1);

        do {
            ListInterface<Programme> programmeUnderCourse = course.getProgrammesAssociated();
            if (!course.getProgrammesAssociated().isEmpty()) {
                System.out.println("\nCourse Code: " + course.getCourseCode());
                System.out.println("Course Name: " + course.getCourseName());
                System.out.println("----------------------------------------------------------------------------------------------------------------");
                System.out.print("Programmes Under This Course:\n");
                for (int i = 0; i < programmeUnderCourse.getSize(); i++) {
                    Programme programme = programmeUnderCourse.getPosition(i);
                    System.out.println(i + 1 + ". " + programme.getProgrammeCode() + " - " + programme.getProgrammeName());
                }
                System.out.println("----------------------------------------------------------------------------------------------------------------");
            } else {
                System.out.println("There are no programmes associated with this course!");
                return;
            }

            System.out.println("");
            int programmeChoice = courseUI.inputRowNum("a programme", "remove");
            while (programmeChoice < 1 || programmeChoice > programmeUnderCourse.getSize()) {
                System.out.println("Invalid choice. Please enter a number in the range.");
                programmeChoice = courseUI.inputRowNum("a programme", "remove");
            }
            courseUI.showProgrammeDetails(programmeUnderCourse.getPosition(programmeChoice - 1));

            int removeConfirmation = courseUI.RemoveConfirmation();
            while (removeConfirmation != 1 && removeConfirmation != 2) {
                courseUI.invalidNumberOption(1, 2);
                removeConfirmation = courseUI.RemoveConfirmation();
            }

            switch (removeConfirmation) {
                case 1 -> {
                    if (programmeChoice == courseList.getSize()) {
                        removeProgrammeStudentFromCourse(programmeUnderCourse.getPosition(programmeChoice - 1), course);
                        programmeUnderCourse.removeBack();
                        courseUI.displayProgrammeRemovedMessage();
                        dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                        break;
                    } else {
                        removeProgrammeStudentFromCourse(programmeUnderCourse.getPosition(programmeChoice - 1), course);
                        programmeUnderCourse.removeAtPosition(programmeChoice - 1);
                        courseUI.displayProgrammeRemovedMessage();
                        dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                        break;
                    }
                }
                case 2 -> {
                    System.out.println("Exiting.....\n");
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    return;
                }
            }

            continueOption = courseUI.getContinueRemoveOption();
        } while (continueOption == 'y');

        if (continueOption != 'y') {
            Utility.clearScreen();
            System.out.println("Here is the latest version of course with programme associated:");
            courseUI.printSelectedCourseDetails(course);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }
    }

    public ListInterface<Student> GetStudentForCourse(Course course) {
        ListInterface<Student> studentInThisCourse = new DoublyCircularLinkedList<>();

        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);
            ListInterface<Student.CourseStatus> studentCourseList = student.getStudentCourseList();

            for (int j = 0; j < studentCourseList.getSize(); j++) {
                String code = studentCourseList.getPosition(j).getCourse().getCourseCode();
                if (code.equals(course.getCourseCode())) {
                    studentInThisCourse.add(student);
                }
            }
        }
        return studentInThisCourse;
    }

    public void removeProgrammeStudentFromCourse(Programme programme, Course course) {
        ListInterface<Student> studentInThisCourse = GetStudentForCourse(course);

        for (int i = 0; i < studentInThisCourse.getSize(); i++) {
            Student student = studentInThisCourse.getPosition(i);
            ListInterface<Student.CourseStatus> studentCourseList = student.getStudentCourseList();

            for (int j = 0; j < studentCourseList.getSize(); j++) {
                Student.CourseStatus courseStatus = studentCourseList.getPosition(j);
                if (courseStatus.getCourse().getCourseCode().equals(course.getCourseCode())) {
                    studentCourseList.removeAtPosition(j);
                    int totalCreditHour = student.getTotalCreditHour();
                    totalCreditHour -= course.getCreditHours();
                    student.setTotalCreditHour(totalCreditHour);
                    break;
                }
            }

        }
        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
    }

    public void listAllFaculty() {
        int count = 1;
        getAllFaculty();
        if (!courseList.isEmpty()) {
            courseUI.listAllFaculty();
            for (int i = 0; i < facultyList.getSize(); i++) {
                System.out.println(count + ". " + facultyList.getPosition(i));
                count++;
            }
            System.out.println("");
        } else {
            System.out.println("There is no result found!");
        }
    }

    public void getAllFaculty() {
        if (courseList != null) {
            for (int i = 0; i < courseList.getSize(); i++) {
                Course course = courseList.getPosition(i);
                ListInterface<Programme> programmeList = course.getProgrammesAssociated();
                if (programmeList != null) {
                    for (int j = 0; j < programmeList.getSize(); j++) {
                        Programme programme = programmeList.getPosition(j);
                        String uniqueFaculty = programme.getFaculty();
                        if (!facultyList.contains(uniqueFaculty)) {
                            facultyList.add(uniqueFaculty);
                        }
                    }
                }
            }
        }
    }

    public void displayAllCourseUnderFaculty() {
        int index, count = 1;
        listAllFaculty();
        if (facultyList.isEmpty()) {
            System.out.println("There is no available faculty.");
            runCourseManagement();
        } else {
            index = courseUI.inputRowNum("display all courses");
            while (index < 1 && index > facultyList.getSize()) {
                System.out.println("Please enter a valid choice.");
                index = courseUI.inputRowNum("display all courses");
            }
            if (index >= 1 && index <= facultyList.getSize()) {
                String faculty = facultyList.getPosition(index - 1);
                System.out.println("\nCourses under Faculty: " + faculty);
                System.out.println("===========================================================================================================================================");
                System.out.println(String.format("    %-10s %-50s %-20s %-15s %10s", "Code", "Course Name", "Course Leader", "Credit Hours", "Fee(RM)"));
                System.out.println("===========================================================================================================================================");
                for (int i = 0; i < courseList.getSize(); i++) {
                    Course course = courseList.getPosition(i);
                    ListInterface<Programme> programmeList = course.getProgrammesAssociated();
                    if (programmeList != null) {
                        for (int j = 0; j < programmeList.getSize(); j++) {
                            Programme programme = programmeList.getPosition(j);
                            String currentFaculty = programme.getFaculty();
                            if (faculty == null ? currentFaculty == null : faculty.equals(currentFaculty)) {
                                System.out.println(String.format("%-2d  %-10s %-50s %-20s %-15s %10.2f\n", count, course.getCourseCode(), course.getCourseName(), (course.getCourseLeader() != null ? course.getCourseLeader().getName() : "N/A"), course.getCreditHours(), course.getCourseFee()));
                                count++;
                                break;
                            }
                        }
                    }
                }
                System.out.println("===========================================================================================================================================");
            }
        }

        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public String getAllCourses() {
        String outputStr = "";
        for (int i = 0; i < courseList.getSize(); i++) {
            outputStr += "\nCourse " + (i + 1);
            outputStr += "\n~~~~~~~~~~\n" + courseList.getPosition(i);
            outputStr += "===============================================================================================================================\n";
        }
        return outputStr;
    }

    public void listAllCourses() {
        if (courseList.isEmpty()) {
            System.out.println("There is no available programme.");
        } else {
            courseUI.listAllCourses(getAllCourses());
        }
    }

    public Course manageCourseStructureMenu() {
        courseUI.manageCourseMenu(courseList);
        int index = courseUI.inputRowNum("manage the course structure");

        while (index < 1 || index > courseList.getSize()) {
            System.out.println("Please select a row within the range.");
            index = courseUI.inputRowNum("manage the course structure");
        }

        Utility.clearScreen();

        Course selectedCourse = courseList.getPosition(index - 1);
        return selectedCourse;
    }

    public void manageCourseStructure(Course selectedCourse) {
        boolean continueManaging = true;

        courseUI.showCourseStructure(selectedCourse);
        while (continueManaging) {
            int choice = courseUI.manageCourseStructureOption();

            switch (choice) {
                case 0:
                    System.out.println("\nExiting......");
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    return;
                case 1:
                    addCourseLeader(selectedCourse);
                    break;
                case 2:
                    setMinimumGrade(selectedCourse);
                    break;
                case 3:
                    setWeightage(selectedCourse);
                    break;
                case 4:
                    setAssTeamSize(selectedCourse);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

            char continueOption = courseUI.getContinueManagingOption();
            continueManaging = (continueOption == 'y');
        }

        Utility.clearScreen();
        System.out.println("This is the updated version:");
        courseUI.showCourseStructure(selectedCourse);
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void addCourseLeader(Course course) {
        int confirmation;
        boolean success = false;
        ListInterface<Tutor> tutorAvailable = getTutorClass(course);
        if (!tutorAvailable.isEmpty()) {
            courseUI.printAllTutors(tutorAvailable);
        } else {
            System.out.println("There are no available tutors.");
            return;
        }
        int index = courseUI.inputRowNum("add the leader");
        while (index <= 0 || index > tutorAvailable.getSize()) {
            System.out.println("Please enter a row in the range!");
            index = courseUI.inputRowNum("add the leader");
        }

        Tutor chosenTutor = tutorAvailable.getPosition(index - 1);

        do {
            confirmation = courseUI.actionConfirmation("add this tutor as the course leader");
            switch (confirmation) {
                case 1 -> {
                    course.setCourseLeader(chosenTutor);
                    System.out.println("Course Leader Added Successfully");
                    dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                    success = true;
                }
                case 2 -> {
                    courseUI.ErrorMsg();
                    success = true;
                }
                default ->
                    courseUI.invalidNumberOption(1, 2);
            }
        } while (success == false);
    }

    public ListInterface<Tutor> getTutorClass(Course course) {
        DoublyCircularLinkedList<TutorClass> savedTutorClass = dao.loadFromFile("TutorClass");
        ListInterface<Tutor> foundTutor = new DoublyCircularLinkedList<>();
        ListInterface<Tutor> assignedTutor = new DoublyCircularLinkedList<>();

        for (int i = 0; i < courseList.getSize(); i++) {
            Course currentCourse = courseList.getPosition(i);
            if (currentCourse.getCourseLeader() != null) {
                assignedTutor.add(currentCourse.getCourseLeader());
            }
        }

        if (!savedTutorClass.isEmpty()) {
            for (int i = 0; i < savedTutorClass.getSize(); i++) {
                TutorClass tutorClass = savedTutorClass.getPosition(i);
                Tutor tutor = tutorClass.getTutor();

                if (!isTutorAssigned(tutor, assignedTutor) && tutorClass.getCourse().equals(course)) {
                    foundTutor.add(tutor);
                }
            }
        } else {
            foundTutor = null;
        }

        return foundTutor;
    }

    public boolean isTutorAssigned(Tutor tutor, ListInterface<Tutor> assignedTutor) {
        for (int i = 0; i < assignedTutor.getSize(); i++) {
            Tutor assigned = assignedTutor.getPosition(i);
            if (assigned.getId().equals(tutor.getId())) {
                return true;
            }
        }
        return false;
    }

    public void setMinimumGrade(Course course) {
        int confirmation;
        boolean success = false;
        char grade = courseUI.inputMinimumGradeForCreditTransfer();
        do {
            confirmation = courseUI.actionConfirmation("set this grade as the minimum grade for credit transfer");
            switch (confirmation) {
                case 1 -> {
                    course.setCtMinGrade(grade);
                    System.out.println("Minimum Grade for Credit Transfer Set Successfully");
                    dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
                    success = true;
                }
                case 2 -> {
                    courseUI.ErrorMsg();
                    success = true;
                }
                default ->
                    courseUI.invalidNumberOption(1, 2);
            }
        } while (success == false);
    }

    public void setWeightage(Course course) {
        int[] weightage = courseUI.setWeightage();
        course.setCourseworkWeightage(weightage[0]);
        course.setExamWeightage(weightage[1]);
        System.out.println("Coursework/Exam Weightage Set Successfully");
        dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
    }

    public void setAssTeamSize(Course course) {
        int teamSize[] = courseUI.setTeamSize();
        course.setMinTeamSize(teamSize[0]);
        course.setMaxTeamSize(teamSize[1]);
        System.out.println("Assignment Team Size Set Succesfully");
        dao.saveToFile((DoublyCircularLinkedList) courseList, "Course");
    }

    public void manageCreditTransfer() {
        DoublyCircularLinkedList<CreditTransfer> creditTransferList = dao.loadFromFile("CreditTransfer");
        int choice = courseUI.creditTransferManu();
        if (choice == 0) {
            System.out.println("Exiting..........");
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }

        while (choice != 1 && choice != 2) {
            System.out.println("Please enter a valid choice.");
            choice = courseUI.creditTransferManu();
        }

        switch (choice) {
            case 1 -> {
                Utility.clearScreen();
                getCreditTransferEnquiry();
            }
            case 2 -> {
                Utility.clearScreen();
                courseUI.showPerformedCreditTransfer(creditTransferList);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
        }
    }

    public void getCreditTransferEnquiry() {
        DoublyCircularLinkedList<CreditTransfer> creditTransferList = dao.loadFromFile("CreditTransfer");
        ListInterface<CreditTransfer> pendingApprovalCT = new DoublyCircularLinkedList<>();
        if (!creditTransferList.isEmpty()) {
            pendingApprovalCT = courseUI.showAllCreditTransfer(creditTransferList);
            if (pendingApprovalCT.isEmpty()) {
                System.out.println("There is no pending credit trasnfer enquiry.");
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                return;
            }
        } else {
            System.out.println("There is no credit trasnfer enquiry.");
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }

        int index = courseUI.inputRowNum("an index", "view the details");
        while (index < 1 || index > creditTransferList.getSize()) {
            System.out.println("Please select a correct index.");
            index = courseUI.inputRowNum("an index", "view the details");
        }

        CreditTransfer ct = pendingApprovalCT.getPosition(index - 1);
        courseUI.showCreditTransferDetails(ct);

        CreditTransfer newCT = new CreditTransfer(ct.getPreviousCourse(), ct.getCourseDesc(), ct.getCreditHours(), ct.getGrade(), ct.getSelectedCourse(), ct.getStudent());

        int confirmation = courseUI.CreditTransferConfirmation();
        while (confirmation != 1 && confirmation != 2) {
            System.out.println("Please enter 1 or 2 only.");
            confirmation = courseUI.CreditTransferConfirmation();
        }

        switch (confirmation) {
            case 1 -> {
                boolean removed = removeStudentFromCourse(ct);
                if (removed) {
                    newCT.setActionDate(LocalDate.now());
                    newCT.setStatus("Approved");
                    creditTransferList.update(ct, newCT);
                    dao.saveToFile((DoublyCircularLinkedList) creditTransferList, "CreditTransfer");
                    courseUI.SuccessfulMsg();
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
            }
            case 2 -> {
                Student student = ct.getStudent();
                CourseStatus cs = student.new CourseStatus(ct.getSelectedCourse(), "Pending");
                ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();
                studentCourseList.get(cs).setStatus("Main");
                for (int j = 0; j < studentList.getSize(); j++) {
                    if (studentList.getPosition(j).getStudentID().equals(student.getStudentID())) {
                        studentList.getPosition(j).setStudentCourseList(studentCourseList);
                    }
                }
                dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                newCT.setActionDate(LocalDate.now());
                newCT.setStatus("Rejected");
                creditTransferList.update(ct, newCT);
                dao.saveToFile((DoublyCircularLinkedList) creditTransferList, "CreditTransfer");
                courseUI.SuccessfulMsg();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
        }
    }

    public boolean removeStudentFromCourse(CreditTransfer ct) {
        Student student = ct.getStudent();
        ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();
        for (int i = 0; i < studentCourseList.getSize(); i++) {
            CourseStatus cs = studentCourseList.getPosition(i);
            if (cs.getCourse().getCourseCode().equals(ct.getSelectedCourse().getCourseCode())) {
                boolean removed = studentCourseList.removeAtPosition(i);
                if (removed) {
                    // Update student's total credit hour
                    int totalCreditHour = student.getTotalCreditHour();
                    totalCreditHour -= ct.getSelectedCourse().getCreditHours();
                    student.setTotalCreditHour(totalCreditHour);
                    for (int j = 0; j < studentList.getSize(); j++) {
                        if (studentList.getPosition(j).getStudentID().equals(student.getStudentID())) {
                            studentList.getPosition(j).setStudentCourseList(studentCourseList);
                        }
                    }
                    dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void GenerateReport() {
        int choice = courseUI.summaryReport();

        if (choice == 0) {
            return;
        }
        while (choice != 1 && choice != 2) {
            System.out.println("Please enter a valid choice.");
            choice = courseUI.summaryReport();
        }

        switch (choice) {
            case 1 -> {
                Utility.clearScreen();
                courseUI.summaryReport1(courseList, studentList, savedProgramme.getSize());
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
            case 2 -> {
                Utility.clearScreen();
                courseUI.financialReport(courseList, studentList);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
        }
    }

    public static void main(String[] args) {
        CourseManagement courseManagement = new CourseManagement();
        courseManagement.runCourseManagement();
    }

}
