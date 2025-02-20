/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ListInterface;
import adt.DoublyCircularLinkedList;
import entity.Programme;
import boundary.ProgrammeManagementUI;
import dao.DoublyCircularLinkedListDAO;
import entity.Course;
import entity.Student;
import java.io.Serializable;
import java.util.Comparator;
import utility.Utility;

/**
 *
 * @author jsony
 */
public class ProgrammeManagement implements Serializable {

    private ListInterface<Programme> programmeList = new DoublyCircularLinkedList<>();
    private final ProgrammeManagementUI programmeUI = new ProgrammeManagementUI();
    private DoublyCircularLinkedListDAO dao = new DoublyCircularLinkedListDAO();
    private ListInterface<String> facultyList = new DoublyCircularLinkedList<>();
    private ListInterface<String> intakeList = new DoublyCircularLinkedList<>();
    DoublyCircularLinkedList<Course> savedCourse = dao.loadFromFile("Course");
    DoublyCircularLinkedList<Student> studentList = dao.loadFromFile("Student");

    public ProgrammeManagement() {
        Object retrievedData = dao.loadFromFile("Programme");
        if (retrievedData != null && retrievedData instanceof ListInterface) {
            programmeList = (ListInterface<Programme>) retrievedData;
        } else {
            programmeList = new DoublyCircularLinkedList<>();
        }
    }

    public void runProgrammeManagement() {
        int choice = 0;
        do {
            choice = programmeUI.getProgrammeMenuChoice();
            switch (choice) {
                case 0 -> {
                }
                case 1 -> {
                    Utility.clearScreen();
                    addNewProgramme();
                }
                case 2 -> {
                    Utility.clearScreen();
                    removeProgramme();
                }
                case 3 -> {
                    Utility.clearScreen();
                    findProgramme();
                }
                case 4 -> {
                    Utility.clearScreen();
                    ammendProgramme();
                }
                case 5 -> {
                    Utility.clearScreen();
                    listProgrammes();
                }
                case 6 -> {
                    Utility.clearScreen();
                    addCourseToProgramme();
                }
                case 7 -> {
                    Utility.clearScreen();
                    deleteCourseFromProgramme();
                }
                case 8 -> {
                    Utility.clearScreen();
                    displayCourseInProgramme();
                }
                default ->
                    System.out.println("Please select 1 to 8 selection");
            }
        } while (choice != 0);
    }

    public void saveToFile() {
        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
    }

    public void addNewProgramme() {
        boolean success = false;

        programmeUI.addProgramHeader();
        Programme newProgramme;

        do {
            newProgramme = programmeUI.inputProgrammeDetails();

            if (!programmeList.isEmpty() && programmeList.contains(newProgramme)) {
                char readdOption = programmeUI.duplicatedProgramme(newProgramme.getProgrammeCode());
                if(readdOption == 'n') {
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    return;
                }
            } else {
                programmeUI.printProgrammeDetails(newProgramme);
                int confirmation = programmeUI.actionConfirmation("add this programme");

                switch (confirmation) {
                    case 1:
                        if (programmeList.add(newProgramme)) {
                            System.out.println("Programme Added Successfully");
                            dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
                            success = true;
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 2:
                        System.out.println("Exiting.....\n");
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        success = true;
                        break;
                    default:
                        programmeUI.invalidNumberOption(1, 2);
                }
            }
        } while (!success);
    }

    public void removeProgramme() {
        boolean success = false;

        if (!programmeList.isEmpty()) {
            int removeOption;

            programmeUI.removeProgramHeader();
            listAllProgrammes();
            do {
                removeOption = programmeUI.removeProgrammeOption();
                switch (removeOption) {
                    case 0 -> {
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    }
                    case 1 -> {
                        int selectedProgramme = programmeUI.inputRowNum("a programme", "remove");
                        while (selectedProgramme <= 0 || selectedProgramme > programmeList.getSize()) {
                            System.out.println("Please enter a row in the range!");
                            selectedProgramme = programmeUI.inputRowNum("a programme", "remove");
                        }
                        Programme currentProgramme = programmeList.getPosition(selectedProgramme - 1);
                        programmeUI.printProgrammeDetails(currentProgramme);
                        int choice = 0;

                        do {
                            choice = programmeUI.actionConfirmation("remove this programme");
                            switch (choice) {
                                case 1 -> {
                                    if (checkProgrammeHasStudents(currentProgramme)) {
                                        System.out.println("Failed to remove programme " + currentProgramme.getProgrammeCode() + " as it contains students. Remove all students from the programme first.");
                                        choice = 0;
                                    } else {
                                        if (selectedProgramme == programmeList.getSize()) {
                                            programmeList.removeBack();
                                            programmeUI.displayProgrammeRemovedMessage(currentProgramme.getProgrammeCode());
                                            dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
                                            updateDeletedProgrammeInCourse(currentProgramme);
                                            success = true;
                                            Utility.pressEnterToContinue();
                                            Utility.clearScreen();
                                            break;
                                        } else {
                                            programmeList.removeAtPosition(selectedProgramme - 1);
                                            programmeUI.displayProgrammeRemovedMessage(currentProgramme.getProgrammeCode());
                                            dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
                                            updateDeletedProgrammeInCourse(currentProgramme);
                                            success = true;
                                            Utility.pressEnterToContinue();
                                            Utility.clearScreen();
                                            break;
                                        }
                                    }
                                }
                                case 2 -> {
                                    System.out.println("Exiting.....\n");
                                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                                    success = true;
                                }
                                default ->
                                    programmeUI.invalidNumberOption(1, 2);
                            }
                        } while (success == false);

                        break;
                    }
                    case 2 -> {
                        System.out.println("");
                        int startRow, endRow, choice = 0;

                        startRow = programmeUI.inputStartingRowToRemove();
                        while (startRow <= 0 || startRow > programmeList.getSize()) {
                            System.out.println("Please enter a row in the range!");
                            startRow = programmeUI.inputStartingRowToRemove();
                        }

                        endRow = programmeUI.inputEndingRowToRemove();
                        while (endRow < startRow || endRow > programmeList.getSize()) {
                            System.out.println("Please enter a correct ending row!");
                            endRow = programmeUI.inputEndingRowToRemove();
                        }

                        do {
                            choice = programmeUI.actionConfirmation("remove programme from row " + startRow + " to " + endRow);
                            switch (choice) {
                                case 1 -> {
                                    for (int i = endRow; i >= startRow; i--) {
                                        if (i == programmeList.getSize()) {
                                            Programme selectedProgramme = programmeList.getPosition(i - 1);
                                            if (checkProgrammeHasStudents(selectedProgramme)) {
                                                System.out.println("Failed to remove programme " + selectedProgramme.getProgrammeCode() + " as it contains students. Remove all students from the programme first.");
                                                choice = 0;
                                            } else {
                                                programmeList.removeBack();
                                                updateDeletedProgrammeInCourse(selectedProgramme);
                                            }
                                        } else {
                                            Programme selectedProgramme = programmeList.getPosition(i - 1);
                                            if (checkProgrammeHasStudents(selectedProgramme)) {
                                                System.out.println("Failed to remove programme " + selectedProgramme.getProgrammeCode() + " as it contains students. Remove all students from the programme first.");
                                                choice = 0;
                                            } else {
                                                programmeList.remove(selectedProgramme);
                                                updateDeletedProgrammeInCourse(selectedProgramme);
                                            }

                                        }
                                    }
                                    success = true;
                                    dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
                                    System.out.println("Programmes from row " + startRow + " to " + endRow + " removed successfully!");
                                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                                    return;
                                }
                                case 2 -> {
                                    System.out.println("Exiting.....\n");
                                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                                    success = true;
                                }
                                default ->
                                    programmeUI.invalidNumberOption(1, 2);
                            }
                        } while (!success);
                    }
                    case 3 -> {
                        char choice;
                        do {
                            choice = programmeUI.deleteAllProgrammeConfirmation();
                            switch (choice) {
                                case 'y' -> {
                                    if (studentList.isEmpty()) {
                                        programmeList.reset();
                                        programmeUI.displayAllProgrammeRemovedMessage();
                                        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
                                        deleteAllProgrammeInCourse();
                                        Utility.pressEnterToContinue();
                                        Utility.clearScreen();
                                        return;
                                    } else {
                                        System.out.println("Failed to removed all programme as : ");
                                        for (int i = 0; i < programmeList.getSize(); i++) {
                                            Programme programme = programmeList.getPosition(i);
                                            if (checkProgrammeHasStudents(programme)) {
                                                System.out.println("Programme " + programme.getProgrammeCode() + " contains students. Remove all students from the programme first.");
                                            }
                                            choice = 0;
                                        }
                                    }
                                }
                                case 'n' -> {
                                    System.out.println("Exiting.....\n");
                                    Utility.pressEnterToContinue();
                                    Utility.clearScreen();
                                    success = true;
                                }
                                default ->
                                    programmeUI.invalidCharacterOption('y', 'n');
                            }
                        } while (!success);
                        break;
                    }
                    default -> {
                        programmeUI.invalidNumberOption(1, 2, 3);
                        break;
                    }
                }
            } while (removeOption < 0 || removeOption > 2);
        } else {
            System.out.println("There is no available course");
        }
    }

    private boolean checkProgrammeHasStudents(Programme programme) {
        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);

            if (student.getStudentProgramme().equals(programme)) {
                return true;
            }
        }

        return false;
    }

    public void updateDeletedProgrammeInCourse(Programme deletedProgramme) {
        DoublyCircularLinkedList<Course> savedCourses = dao.loadFromFile("Course");

        if (!savedCourses.isEmpty()) {
            for (int i = 0; i < savedCourses.getSize(); i++) {
                Course currentCourse = savedCourses.getPosition(i);
                if (currentCourse != null && currentCourse.getProgrammesAssociated() != null) {
                    ListInterface<Programme> programmesAssociated = currentCourse.getProgrammesAssociated();

                    if (programmesAssociated != null) {
                        // Iterate through the associated programmes of the current course
                        for (int j = 0; j < programmesAssociated.getSize(); j++) {
                            // Check if the current course has the updated programme in its list of associated programmes
                            if (programmesAssociated.contains(deletedProgramme)) {
                                programmesAssociated.remove(deletedProgramme);
                                dao.saveToFile(savedCourses, "Course");
                            }
                        }
                    }
                }
            }
        }
    }

    public void deleteAllProgrammeInCourse() {
        DoublyCircularLinkedList<Course> savedCourses = dao.loadFromFile("Course");

        if (!savedCourses.isEmpty()) {
            for (int i = 0; i < savedCourses.getSize(); i++) {
                Course currentCourse = savedCourses.getPosition(i);
                if (currentCourse != null && currentCourse.getProgrammesAssociated() != null) {
                    ListInterface<Programme> programmesAssociated = currentCourse.getProgrammesAssociated();

                    if (programmesAssociated != null) {
                        programmesAssociated.reset();
                        dao.saveToFile(savedCourses, "Course");
                    }
                }
            }
        }
    }

    public void findProgramme() {
        String targetId;
        programmeUI.searchProgramHeader();
        if (programmeList.isEmpty()) {
            System.out.println("There is no available programme.");
            runProgrammeManagement();
        }
        targetId = programmeUI.inputProgrammeCodeToSearch();
        Programme foundProgramme = programmeList.find(new Programme(targetId));
        if (foundProgramme != null) {
            System.out.println("\nProgramme found !!!");
            programmeUI.printProgrammeDetails(foundProgramme);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        } else {
            programmeUI.programmeNotFound(targetId);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }
    }

    public void ammendProgramme() {
        int index, attributeNumber;
        char continueOption;

        programmeUI.amendProgramHeader();
        if (programmeList.isEmpty()) {
            System.out.println("There is no available programme.");
            runProgrammeManagement();
        }
        do {
            Utility.clearScreen();
            System.out.println("");
            listAllProgrammes();
            index = programmeUI.inputRowNum("amend");
            if (index >= 1 && index <= programmeList.getSize()) {
                Programme programmeToEdit = programmeList.getPosition(index - 1);
                Programme newProgramme = new Programme(programmeToEdit.getProgrammeCode(),
                        programmeToEdit.getProgrammeName(),
                        programmeToEdit.getProgrammeIntake(),
                        programmeToEdit.getProgrammeDuration(),
                        programmeToEdit.getFaculty());

                do {
                    attributeNumber = programmeUI.getAmendProgrammeMenuOption();

                    switch (attributeNumber) {
                        case 1 -> {
                            System.out.println("\nOriginal programme name: " + programmeToEdit.getProgrammeName());
                            String newProgName = programmeUI.inputNewProgrammeAttributeStr("name");
                            newProgramme.setProgrammeName(newProgName);
                            programmeUI.programmeAttributeUpdated("name");
                        }
                        case 2 -> {
                            System.out.println("\nOriginal programme intake: " + programmeToEdit.getProgrammeIntake());
                            String newProgIntake = programmeUI.inputNewProgrammeAttributeStr("intake");
                            newProgramme.setProgrammeIntake(newProgIntake);
                            programmeUI.programmeAttributeUpdated("intake");
                        }
                        case 3 -> {
                            System.out.println("\nOriginal programme duration: " + programmeToEdit.getProgrammeDuration());
                            int newProgDuration = programmeUI.inputNewProgrammeAttributeInt("duration");
                            newProgramme.setProgrammeDuration(newProgDuration);
                            programmeUI.programmeAttributeUpdated("duration");
                        }
                        case 4 -> {
                            System.out.println("\nOriginal programme faculty: " + programmeToEdit.getFaculty());
                            String newFaculty = programmeUI.inputNewProgrammeAttributeStr("faculty");
                            newProgramme.setFaculty(newFaculty);
                            programmeUI.programmeAttributeUpdated("faculty");
                        }
                        case 0 -> {
                            System.out.println("Exiting system...........");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                            return;
                        }
                        default ->
                            programmeUI.invalidMessage("attribute number");
                    }

                    continueOption = programmeUI.getContinueAmendingOption("other attributes for this programme");
                    while (continueOption != 'y' && continueOption != 'n') {
                        programmeUI.invalidCharacterOption('y', 'n');
                        continueOption = programmeUI.getContinueAmendingOption("other attributes for this programme");
                    }
                } while (continueOption == 'y');
                programmeList.update(programmeToEdit, newProgramme);
                dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
                updateProgrammeUnderCourse(newProgramme);
                System.out.println("\nUpdated Version:");
                programmeUI.printProgrammeDetails(newProgramme);
            } else {
                programmeUI.invalidMessage("row number");
            }
            System.out.println("");
            continueOption = programmeUI.getContinueAmendingOption("other programme");
            if (continueOption != 'y' && continueOption != 'n') {
                programmeUI.invalidCharacterOption('y', 'n');
                continueOption = programmeUI.getContinueAmendingOption("this programme");
            }
        } while (continueOption == 'y');

        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void updateProgrammeUnderCourse(Programme updatedProgramme) {
        // Retrieve the list of courses from the DAO
        DoublyCircularLinkedList<Course> savedCourses = dao.loadFromFile("Course");

        if (!savedCourses.isEmpty()) {
            for (int i = 0; i < savedCourses.getSize(); i++) {
                Course currentCourse = savedCourses.getPosition(i);
                if (currentCourse != null && currentCourse.getProgrammesAssociated() != null) {
                    ListInterface<Programme> programmesAssociated = currentCourse.getProgrammesAssociated();

                    if (programmesAssociated != null) {
                        // Iterate through the associated programmes of the current course
                        for (int j = 0; j < programmesAssociated.getSize(); j++) {
                            // Check if the current course has the updated programme in its list of associated programmes
                            if (programmesAssociated.contains(updatedProgramme)) {
                                Programme programme = programmesAssociated.getPosition(j);

                                // Check if the programme code matches the updated programme
                                if (programme.getProgrammeCode().equals(updatedProgramme.getProgrammeCode())) {
                                    // Update the attributes of the programme in the course
                                    programme.setProgrammeName(updatedProgramme.getProgrammeName());
                                    programme.setProgrammeIntake(updatedProgramme.getProgrammeIntake());
                                    programme.setProgrammeDuration(updatedProgramme.getProgrammeDuration());
                                    programme.setProgrammeFee(updatedProgramme.getProgrammeFee());

                                    // Save the updated list of courses back to file
                                    dao.saveToFile(savedCourses, "Course");
                                }
                            }
                        }
                    }
                }
            }
        } else {
        }
    }

    public void listProgrammes() {
        int choice = programmeUI.listProgrammeFormat();
        while (choice > 3) {
            System.out.println("Invalid choice. Please enter 1 or 2 or 3.\n");
            choice = programmeUI.listProgrammeFormat();
        }
        switch (choice) {
            case 1 -> {
                ListInterface<String> facultyList = new DoublyCircularLinkedList<>();
                for (int i = 0; i < programmeList.getSize(); i++) {
                    String faculty = programmeList.getPosition(i).getFaculty();
                    facultyList.add(faculty);
                }

                for (int i = 0; i < facultyList.getSize(); i++) {
                    System.out.println("\nFaculty : [" + facultyList.getPosition(i) + "]");
                    listProgrammeByFaculty(facultyList.getPosition(i));
                }

                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
            case 2 -> {
                ListInterface<String> intakeList = new DoublyCircularLinkedList<>();
                for (int i = 0; i < programmeList.getSize(); i++) {
                    String intake = programmeList.getPosition(i).getProgrammeIntake();
                    intakeList.add(intake);
                }

                for (int i = 0; i < intakeList.getSize(); i++) {
                    System.out.println("\nIntake : [" + intakeList.getPosition(i) + "]");
                    listProgrammeByIntake(intakeList.getPosition(i));
                }

                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
            case 3 -> {
                listAllProgrammesSorted();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
            case 0 -> {
                break;
            }
        }
    }

    public void addCourseToProgramme() {
        programmeUI.addCourseToProgramHeader();
        int addCourseChoice = programmeUI.addCourseOption();

        switch (addCourseChoice) {
            case 1 -> {
                addExistingCourseToProgramme();
            }
            case 2 -> {
                addNewCourseToProgramme();
            }
            case 0 -> {
                System.out.println("Exiting system........");
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
            default ->
                programmeUI.invalidNumberOption(1, 2);
        }
    }

    public void addExistingCourseToProgramme() {
        ListInterface<Course> availableCourse = new DoublyCircularLinkedList<>();
        Programme selectedProgramme = null;

        listAllProgrammes();
        while (selectedProgramme == null) {
            int targetProgramme = programmeUI.inputRowNum("a programme", "add course");
            while (targetProgramme <= 0 || targetProgramme > programmeList.getSize()) {
                System.out.println("Please enter a row in the range!");
                targetProgramme = programmeUI.inputRowNum("a programme", "add course");
            }
            selectedProgramme = programmeList.getPosition(targetProgramme - 1);
            if (selectedProgramme == null) {
                System.out.println("Programme not found. Please enter a valid Programme Code.");
            }
        }

        char continueOption;
        do {
            boolean isValidAddChoice = false;
            Course courseToAdd = null;
            int count = 1;
            int chosenCourse;
            availableCourse.reset();

            for (int i = 0; i < savedCourse.getSize(); i++) {
                Course course = savedCourse.getPosition(i);
                ListInterface<Programme> programme = course.getProgrammesAssociated();
                if (!programme.contains(selectedProgramme)) {
                    availableCourse.add(course);
                }
            }

            if (!availableCourse.isEmpty()) {
                System.out.println("\nCourses available for adding to programme " + selectedProgramme.getProgrammeCode() + ":");
                for (int i = 0; i < availableCourse.getSize(); i++) {
                    Course course = availableCourse.getPosition(i);
                    System.out.println(count + ". " + course.getCourseCode() + " - " + course.getCourseName());
                    count++;
                }
            } else {
                System.out.println("\nThere are no courses available for addding to programme!");
            }

            chosenCourse = programmeUI.inputRowNum("a course", "to add");
            while (chosenCourse <= 0 || chosenCourse > availableCourse.getSize()) {
                System.out.println("Please enter a row in the range!");
                chosenCourse = programmeUI.inputRowNum("a course", "to add");
            }

            courseToAdd = availableCourse.getPosition(chosenCourse - 1);
            Course selectedCourse = savedCourse.find(courseToAdd);
            ListInterface<Programme> targetProgrammes = selectedCourse.getProgrammesAssociated();

            programmeUI.printCourseDetails(courseToAdd);
            do {
                int confirmation = programmeUI.actionConfirmation("add this course to this progamme");
                switch (confirmation) {
                    case 1 -> {
                        if (targetProgrammes.add(selectedProgramme)) {
                            enrollProgrammeStudentToNewCourse(selectedProgramme, courseToAdd);
                            programmeUI.displayCourseAddedMessage();
                            dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                            isValidAddChoice = true;
                        } else {
                            System.out.println("Adding failed......");
                            isValidAddChoice = true;
                        }
                    }
                    case 2 -> {
                        isValidAddChoice = true;
                        break;
                    }
                    default ->
                        programmeUI.invalidNumberOption(1, 2);
                }
            } while (isValidAddChoice == false);

            continueOption = programmeUI.getContinueAddCourseOption();
        } while (continueOption == 'y');

        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void addNewCourseToProgramme() {
        String newCourseCode;
        boolean existingCourse;
        char confirmation, addConfirmation;

        System.out.println("\nCreate a new course and add to programme\n----------------------------------------------");
        do {
            newCourseCode = programmeUI.inputCourseCode();
            if (savedCourse.contains(new Course(newCourseCode, "", 0, 0, 0))) {
                System.out.println("This course is existing in the system!\nEnter a unique course code.\n");
                existingCourse = true;
            } else {
                existingCourse = false;
            }
        } while (existingCourse == true);
        String newCourseName = programmeUI.inputCourseName();
        int newCreditHours = programmeUI.inputCreditHours();

        Course newCourse = new Course(newCourseCode, newCourseName, newCreditHours);
        confirmation = programmeUI.addNewCourseConfirmation(newCourseCode, newCourseName, newCreditHours);
        if (confirmation == 'y') {
            int choice = programmeUI.addCourseSelection();
            switch (choice) {
                case 1 -> {
                    ListInterface<Programme> availableProgramme = new DoublyCircularLinkedList<>();
                    for (int i = 0; i < programmeList.getSize(); i++) {
                        Programme programme = programmeList.getPosition(i);
                        availableProgramme.add(programme);
                    }
                    listAllProgrammeCode();

                    Programme selectedProgramme = null;
                    int targetProgramme = 0;
                    while (selectedProgramme == null) {
                        targetProgramme = programmeUI.inputRowNum("a programme", "add this new course");
                        while (targetProgramme < 1 || targetProgramme > programmeList.getSize()) {
                            System.out.println("Please enter a number in the range!");
                            targetProgramme = programmeUI.inputRowNum("a programme", "add this new course");
                        }
                        selectedProgramme = programmeList.getPosition(targetProgramme - 1);
                        if (selectedProgramme == null) {
                            System.out.println("Programme not found. Please enter a valid Programme Code.");
                        }
                    }
                    addConfirmation = programmeUI.actionConfirmationChar("add this new course to this programme");
                    if (addConfirmation == 'y') {
                        newCourse.getProgrammesAssociated().add(selectedProgramme);
                        enrollProgrammeStudentToNewCourse(selectedProgramme, newCourse);
                        savedCourse.add(newCourse);
                        dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                        programmeUI.newCourseAddedMsg(newCourseCode, selectedProgramme.getProgrammeCode());

                        if ((targetProgramme - 1) == programmeList.getSize()) {
                            availableProgramme.removeBack();
                        } else {
                            availableProgramme.removeAtPosition(targetProgramme - 1);
                        }
                    } else {
                        System.out.println("Exiting.............");
                        return;
                    }

                    char continueOption = programmeUI.getContinueAddToProgrammeOption();
                    while (continueOption == 'y') {
                        System.out.println("\nList of available programmes:");
                        for (int i = 0; i < availableProgramme.getSize(); i++) {
                            System.out.println(i + 1 + ". " + availableProgramme.getPosition(i).getProgrammeCode() + " - " + availableProgramme.getPosition(i).getProgrammeName());
                        }
                        targetProgramme = programmeUI.inputRowNum("a programme", "add this new course");
                        while (targetProgramme < 1 || targetProgramme > availableProgramme.getSize()) {
                            System.out.println("Please enter a number in the range!");
                            targetProgramme = programmeUI.inputRowNum("a programme", "add this new course");
                        }
                        selectedProgramme = availableProgramme.getPosition(targetProgramme - 1);
                        addConfirmation = programmeUI.actionConfirmationChar("add this new course to this programme");
                        if (addConfirmation == 'y') {
                            newCourse.getProgrammesAssociated().add(selectedProgramme);
                            enrollProgrammeStudentToNewCourse(selectedProgramme, newCourse);
                            savedCourse.add(newCourse);
                            dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                            programmeUI.newCourseAddedMsg(newCourseCode, selectedProgramme.getProgrammeCode());

                            if ((targetProgramme - 1) == availableProgramme.getSize()) {
                                availableProgramme.removeBack();
                            } else {
                                availableProgramme.removeAtPosition(targetProgramme - 1);
                            }
                        } else {
                            System.out.println("Exiting.............");
                            break;
                        }

                        continueOption = programmeUI.getContinueAddToProgrammeOption();
                    }
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
                case 2 -> {
                    boolean isValidFacultyChoice = false;
                    int facultyChoice = -1;
                    String selectedFaculty = "";

                    listAllFaculty();
                    while (!isValidFacultyChoice) {
                        facultyChoice = programmeUI.inputRowNum("a faculty", "add course to all programmes under this faculty");
                        if (facultyChoice >= 1 && facultyChoice <= facultyList.getSize()) {
                            selectedFaculty = facultyList.getPosition(facultyChoice - 1);
                            isValidFacultyChoice = true;
                        } else {
                            programmeUI.invalidMessage("choice. Enter 1 to " + facultyList.getSize() + " only!");
                        }
                    }

                    listProgrammeByFaculty(selectedFaculty.toUpperCase());
                    addConfirmation = programmeUI.actionConfirmationChar("add this new course to the list above");
                    if (addConfirmation == 'y') {
                        for (int i = 0; i < programmeList.getSize(); i++) {
                            Programme programme = programmeList.getPosition(i);
                            if (programme.getFaculty().equals(selectedFaculty)) {
                                newCourse.getProgrammesAssociated().add(programme);
                                enrollProgrammeStudentToNewCourse(programme, newCourse);
                            }
                        }

                        savedCourse.add(newCourse);
                        dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                        programmeUI.newCourseAddByFaculty(newCourseCode, selectedFaculty);
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    } else {
                        System.out.println("Exiting.............");
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        return;
                    }
                }
                case 3 -> {
                    boolean isValidIntakeChoice = false;
                    int intakeChoice = -1;
                    String selectedIntake = "";

                    listAllIntake();
                    while (!isValidIntakeChoice) {
                        intakeChoice = programmeUI.inputRowNum("an intake", "add course to all programmes in this intake");
                        if (intakeChoice >= 1 && intakeChoice <= intakeList.getSize()) {
                            selectedIntake = intakeList.getPosition(intakeChoice - 1);
                            isValidIntakeChoice = true;
                        } else {
                            programmeUI.invalidMessage("choice. Enter 1 to " + intakeList.getSize() + " only!");
                        }
                    }

                    listProgrammeByIntake(selectedIntake.toUpperCase());
                    addConfirmation = programmeUI.actionConfirmationChar("add this new course to the list above");
                    if (addConfirmation == 'y') {
                        for (int i = 0; i < programmeList.getSize(); i++) {
                            Programme programme = programmeList.getPosition(i);
                            if (programme.getProgrammeIntake().equals(selectedIntake)) {
                                newCourse.getProgrammesAssociated().add(programme);
                                enrollProgrammeStudentToNewCourse(programme, newCourse);
                            }
                        }
                        savedCourse.add(newCourse);
                        dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                        programmeUI.newCourseAddByIntake(newCourseCode, selectedIntake);
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    } else {
                        System.out.println("Exiting.............");
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        return;
                    }
                }
                case 4 -> {
                    listAllProgrammeCode();
                    char addAllConfirmation = programmeUI.addCourseToAllProgramme();
                    if (addAllConfirmation == 'y') {
                        for (int i = 0; i < programmeList.getSize(); i++) {
                            Programme currentProgramme = programmeList.getPosition(i);
                            newCourse.getProgrammesAssociated().add(currentProgramme);
                            enrollProgrammeStudentToNewCourse(currentProgramme, newCourse);
                        }
                        savedCourse.add(newCourse);
                        dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                        programmeUI.newCourseAddedAllMsg(newCourseCode);
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                    }
                }
                default ->
                    programmeUI.invalidNumberOption(1, 2, 3, 4);
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

    private void deleteCourseFromProgramme() {
        ListInterface<Course> listContainProgramme = new DoublyCircularLinkedList<>();
        Programme selectedProgramme = null;
        int confirmation;
        boolean isValidDeleteChoice = false;

        programmeUI.removeCourseFromProgramHeader();
        listAllProgrammes();

        int programmeChoice = programmeUI.inputRowNum("display associated course");
        if (programmeChoice >= 1 && programmeChoice <= programmeList.getSize()) {
            selectedProgramme = programmeList.getPosition(programmeChoice - 1);
            System.out.println("\nCourses associated with this programme:");
            int count = 1;
            for (int i = 0; i < savedCourse.getSize(); i++) {
                Course currentCourse = savedCourse.getPosition(i);
                ListInterface<Programme> currentProgrammes = currentCourse.getProgrammesAssociated();
                if (currentProgrammes != null) {
                    if (currentProgrammes.contains(selectedProgramme)) {
                        listContainProgramme.add(currentCourse);
                        System.out.println(count + ". " + currentCourse.getCourseCode() + " - " + currentCourse.getCourseName());
                        count++;
                    }
                }
            }
            if (listContainProgramme.isEmpty()) {
                System.out.println("Null");
                return;
            }
            System.out.println("");
        }

        int courseChoice;
        while (true) {
            courseChoice = programmeUI.inputRowNum("delete course from programme");
            if (courseChoice >= 1 && courseChoice <= listContainProgramme.getSize()) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid course choice.");
            }
        }

        Course targetCourse = listContainProgramme.getPosition(courseChoice - 1);
        Course selectedCourse = savedCourse.find(targetCourse);
        ListInterface<Programme> targetProgrammes = selectedCourse.getProgrammesAssociated();

        programmeUI.printCourseDetails(targetCourse);
        do {
            confirmation = programmeUI.actionConfirmation("delete this course from progamme");
            switch (confirmation) {
                case 1 -> {
                    if (targetProgrammes.getSize() == 1) {
                        targetProgrammes.reset();
                        removeProgrammeStudentFromCourse(selectedProgramme, selectedCourse);
                        programmeUI.displayCourseRemovedMessage();
                        dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                        isValidDeleteChoice = true;
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        return;
                    } else {
                        targetProgrammes.remove(selectedProgramme);
                        removeProgrammeStudentFromCourse(selectedProgramme, selectedCourse);
                        programmeUI.displayCourseRemovedMessage();
                        dao.saveToFile((DoublyCircularLinkedList) savedCourse, "Course");
                        isValidDeleteChoice = true;
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        return;
                    }
                }
                case 2 -> {
                    System.out.println("Exiting.....\n");
                    isValidDeleteChoice = true;
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
                default ->
                    programmeUI.invalidNumberOption(1, 2);
            }
        } while (isValidDeleteChoice == false);
    }

    public void displayCourseInProgramme() {
        ListInterface<Course> listContainProgramme = new DoublyCircularLinkedList<>();

        System.out.println("\n-----List all courses for a programme-----");
        listAllProgrammes();

        int choice = programmeUI.inputRowNum("display associated course");
        if (choice >= 1 && choice <= programmeList.getSize()) {
            Programme targetProgramme = programmeList.getPosition(choice - 1);
            System.out.println("\nCourses associated with this programme:");
            int count = 1;
            if (!savedCourse.isEmpty()) {
                for (int i = 0; i < savedCourse.getSize(); i++) {
                    Course currentCourse = savedCourse.getPosition(i);
                    ListInterface<Programme> currentProgrammes = currentCourse.getProgrammesAssociated();
                    if (!currentProgrammes.isEmpty()) {
                        if (currentProgrammes.contains(targetProgramme)) {
                            listContainProgramme.add(currentCourse);
                            System.out.println(count + ". " + currentCourse.getCourseCode() + " - " + currentCourse.getCourseName());
                            count++;
                        }
                    }
                }
                if (count == 1) {
                    System.out.println("Null");
                } else {
                    char option = programmeUI.showCourseDetailsMsg();
                    if (option == 'y') {
                        for (int i = 0; i < listContainProgramme.getSize(); i++) {
                            Course course = listContainProgramme.getPosition(i);
                            programmeUI.printAllCoursesDetails(course, i + 1);
                        }
                        System.out.println("----------------------------------------------------------------");
                    }
                }
            } else {
                System.out.println("There is no available course!");
            }
        }
        Utility.pressEnterToContinue();
        Utility.clearScreen();
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

    public void displayAllCourseCode() {
        if (savedCourse.isEmpty()) {
            System.out.println("No course codes available.\nYou must add course first!\n");
            runProgrammeManagement();
        } else {
            System.out.println("\nCourse Codes\n================");
            for (int i = 0; i < savedCourse.getSize(); i++) {
                System.out.println((i + 1) + ". " + savedCourse.getPosition(i).getCourseCode());
            }
        }
    }

    public String getAllProgrammeCodes() {
        String outputStr = "\nList of Available Programme: \n";

        if (programmeList.isEmpty()) {
            return outputStr + "Null\n";
        }

        for (int i = 0; i < programmeList.getSize(); i++) {
            outputStr += i + 1 + ". " + programmeList.getPosition(i).getProgrammeCode() + " - " + programmeList.getPosition(i).getProgrammeName() + "\n";
        }

        return outputStr;
    }

    public void listAllProgrammeCode() {
        programmeUI.listAllProgrammes(getAllProgrammeCodes());
    }

    public String getAllProgrammes() {
        String line = "=========================================================================================================================================";
        String outputStr = line + "\n";
        outputStr += String.format("     %-10s %-80s %-10s %-15s %-10s\n", "Code", "Programme Name", "Duration", "Intake", "Faculty");
        outputStr += line + "\n";
        
        for (int i = 0; i < programmeList.getSize(); i++) {
            Programme currentProgramme = programmeList.getPosition(i);
            if (currentProgramme != null) {
                outputStr += String.format("%-2d|  %-10s %-80s %-10s %-15s %-10s\n", i + 1, currentProgramme.getProgrammeCode(), currentProgramme.getProgrammeName(),
                        currentProgramme.getProgrammeDuration(), currentProgramme.getProgrammeIntake(), currentProgramme.getFaculty());
            }
        }

        outputStr += line + "\n";
        return outputStr;
    }

    public void listAllProgrammes() {
        if (programmeList.isEmpty()) {
            System.out.println("There is no available programme.");
        } else {
            programmeUI.listAllProgrammes(getAllProgrammes());
        }
    }
    
    public void listAllProgrammesSorted() {
        if (programmeList.isEmpty()) {
            System.out.println("There is no available programme.");
        } else {
            programmeUI.listAllProgrammes(getAllProgrammesSorted());
        }
    }
    
    public String getAllProgrammesSorted() {
        String line = "=========================================================================================================================================";
        String outputStr = line + "\n";
        outputStr += String.format("     %-10s %-80s %-10s %-15s %-10s\n", "Code", "Programme Name", "Duration", "Intake", "Faculty");
        outputStr += line + "\n";
        
        char option = programmeUI.sortOption();
        if(option == 'y') {
            programmeList = sortProgrammesByIntake(programmeList);
        }
        for (int i = 0; i < programmeList.getSize(); i++) {
            Programme currentProgramme = programmeList.getPosition(i);
            if (currentProgramme != null) {
                outputStr += String.format("%-2d|  %-10s %-80s %-10s %-15s %-10s\n", i + 1, currentProgramme.getProgrammeCode(), currentProgramme.getProgrammeName(),
                        currentProgramme.getProgrammeDuration(), currentProgramme.getProgrammeIntake(), currentProgramme.getFaculty());
            }
        }

        outputStr += line + "\n";
        return outputStr;
    }

    public void listProgrammeByFaculty(String faculty) {
        int count = 1;
        boolean resultsFound = false;
        String line = "==============================================================================================================================";
        String outputStr = line + "\n";
        outputStr += String.format("     %-10s %-80s %-10s %-15s\n", "Code", "Programme Name", "Duration", "Intake");
        outputStr += line + "\n";

        for (int i = 0; i < programmeList.getSize(); i++) {
            Programme currentProgramme = programmeList.getPosition(i);
            if (currentProgramme != null && currentProgramme.getFaculty().equals(faculty)) {
                outputStr += String.format("%-2d|  %-10s %-80s %-10s %-15s\n", count, currentProgramme.getProgrammeCode(), currentProgramme.getProgrammeName(),
                        currentProgramme.getProgrammeDuration(), currentProgramme.getProgrammeIntake());
                count++;
                resultsFound = true;
            }
        }

        if (resultsFound) {
            outputStr += line + "\n";
            System.out.println(outputStr);
        } else {
            System.out.println("No programmes available for the given faculty.");
        }
    }

    public void listProgrammeByIntake(String intake) {
        int count = 1;
        boolean resultsFound = false;
        String line = "=======================================================================================================================";
        String outputStr = line + "\n";
        outputStr += String.format("     %-10s %-80s %-10s %-10s\n", "Code", "Programme Name", "Duration", "Faculty");
        outputStr += line + "\n";

        for (int i = 0; i < programmeList.getSize(); i++) {
            Programme currentProgramme = programmeList.getPosition(i);
            if (currentProgramme != null && currentProgramme.getProgrammeIntake().toUpperCase().equals(intake)) {
                outputStr += String.format("%-2d|  %-10s %-80s %-10s %-10s\n", count, currentProgramme.getProgrammeCode(), currentProgramme.getProgrammeName(),
                        currentProgramme.getProgrammeDuration(), currentProgramme.getFaculty());
                count++;
                resultsFound = true;
            }
        }

        if (resultsFound) { // Only print footer if any results are found
            outputStr += line + "\n";
            System.out.println(outputStr);
        } else {
            System.out.println("No programmes available for the given intake.");
        }
    }

    public void getAllFaculty() {
        if (programmeList != null) {
            for (int i = 0; i < programmeList.getSize(); i++) {
                Programme programme = programmeList.getPosition(i);
                if (facultyList.isEmpty()) {
                    facultyList.add(programme.getFaculty());
                } else {
                    if (!facultyList.contains(programme.getFaculty())) {
                        facultyList.add(programme.getFaculty());
                    }
                }
            }
        }
    }

    public void listAllFaculty() {
        getAllFaculty();
        if (!facultyList.isEmpty()) {
            int count = 1;
            System.out.println("\nList of Faculty: ");
            for (int i = 0; i < facultyList.getSize(); i++) {
                System.out.println(count + ". " + facultyList.getPosition(i));
                count++;
            }
            System.out.println();
        } else {
            System.out.println("There is no result found!");
        }
    }

    public void getAllIntake() {
        if (programmeList != null) {
            for (int i = 0; i < programmeList.getSize(); i++) {
                Programme programme = programmeList.getPosition(i);
                if (intakeList.isEmpty()) {
                    intakeList.add(programme.getProgrammeIntake().toUpperCase());
                } else {
                    if (!intakeList.contains(programme.getProgrammeIntake().toUpperCase())) {
                        intakeList.add(programme.getProgrammeIntake().toUpperCase());
                    }
                }
            }
        }
    }

    public void listAllIntake() {
        getAllIntake();
        if (!intakeList.isEmpty()) {
            int count = 1;
            System.out.println("\nList of Intake: ");
            for (int i = 0; i < intakeList.getSize(); i++) {
                System.out.println(count + ". " + intakeList.getPosition(i));
                count++;
            }
            System.out.println();
        } else {
            System.out.println("There is no result found!");
        }
    }
    
    public ListInterface<Programme> sortProgrammesByIntake(ListInterface<Programme> programmeList) {
        
        Comparator<Programme> comparator = new Comparator<Programme>() {
            @Override
            public int compare(Programme p1, Programme p2) {
                String intake1 = p1.getProgrammeIntake();
                String intake2 = p2.getProgrammeIntake();

                String[] parts1 = intake1.split(" ");
                String[] parts2 = intake2.split(" ");

                int yearComparison = Integer.compare(Integer.parseInt(parts1[1]), Integer.parseInt(parts2[1]));
                if (yearComparison != 0) {
                    return yearComparison;
                }

                String month1 = parts1[0];
                String month2 = parts2[0];

                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

                return Integer.compare(getMonthIndex(month1, months), getMonthIndex(month2, months));
            }

            private int getMonthIndex(String month, String[] months) {
                for (int i = 0; i < months.length; i++) {
                    if (months[i].equalsIgnoreCase(month)) {
                        return i;
                    }
                }
                return -1;
            }
        };

        ListInterface<Programme> copiedProgrammes = new DoublyCircularLinkedList<>();
        for (int i = 0; i < programmeList.getSize(); i++) {
            copiedProgrammes.add(programmeList.getPosition(i));
        }

        copiedProgrammes.bubbleSort(comparator);

        return copiedProgrammes;
    }

    public static void main(String[] args) {
        ProgrammeManagement programmeManagement = new ProgrammeManagement();
        programmeManagement.runProgrammeManagement();
    }

}
