/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import boundary.TutorManagementUI;
import dao.DoublyCircularLinkedListDAO;
import entity.Course;
import entity.Programme;
import entity.Tutor;
import entity.TutorClass;
import entity.TutorialGroup;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Random;
import utility.Utility;

/**
 *
 * @author zhixi
 */
public class TutorManagement implements Serializable {

    private ListInterface<Tutor> tutorList;
    private ListInterface<TutorClass> tutorClassList;
    private DoublyCircularLinkedListDAO dao = new DoublyCircularLinkedListDAO();
    private final TutorManagementUI TUTORUI = new TutorManagementUI();
    DoublyCircularLinkedList<Course> savedCourses = dao.loadFromFile("Course");

    public TutorManagement() {
        Object retrievedTutorData = dao.loadFromFile("Tutor");
        if (retrievedTutorData != null && retrievedTutorData instanceof ListInterface) {
            tutorList = (ListInterface<Tutor>) retrievedTutorData;
        } else {
            tutorList = new DoublyCircularLinkedList<>();
        }
        Object retrievedTutorClassData = dao.loadFromFile("TutorClass");
        if (retrievedTutorClassData != null && retrievedTutorClassData instanceof ListInterface) {
            tutorClassList = (ListInterface<TutorClass>) retrievedTutorClassData;
        } else {
            tutorClassList = new DoublyCircularLinkedList<>();
        }
    }

    public void runTutorManagement() {
        int choice;
        do {
            choice = TUTORUI.tutorMenu();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    addTutor();
                    break;
                case 2:
                    amendTutor();
                    break;
                case 3:
                    removeTutor();
                    break;
                case 4:
                    assignTutorToCourse();
                    break;
                case 5:
                    assignTutorToTutorialGroup();
                    break;
                case 6:
                    addTutorsToTG();
                    break;
                case 7:
                    randomAssign();
                    break;
                case 8:
                    searchCourseUnderTutor();
                    break;
                case 9:
                    searchTutorForCourse();
                    break;
                case 10:
                    listClassInfoForCourse();
                    break;
                case 11:
                    listCourseForEachTutor();
                    break;
                case 12:
                    generateReport();
                    break;
                case 13:
                    displayTutors();
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    break;
                default:
                    System.out.println("Please select 1 to 13 selection");
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        TutorManagement tutorManagement = new TutorManagement();
        tutorManagement.runTutorManagement();
    }

    public boolean displayTutors() {
        boolean tutorValid = false;
        if (!tutorList.isEmpty()) {
            tutorValid = true;
            System.out.println("\nTutor List");
            System.out.println("=".repeat(133));
            System.out.printf("| %-5s %-6s %-20s %-14s %-8s %-11s %-14s %-20s %-8s %-11s|\n", "Index", "ID", "Name", "IC Number", "Gender", "Work Type", "Phone Number", "Email", "Faculty", "Registered Date");
            System.out.println("=".repeat(133));
            for (int i = 0; i < tutorList.getSize(); i++) {
                System.out.printf("| %-5d %s    |\n", i + 1, tutorList.getPosition(i).toString());
            }
        } else {
            System.out.println("Error: No Tutor Registered.");
        }
        System.out.println("=".repeat(133));
        return tutorValid;
    }

    // <editor-fold defaultstate="collapsed" desc="$ Manage Tutor $">
    public void addTutor() {
        boolean continueAdd;
        do {
            displayTutors();
            Tutor newTutor = TUTORUI.inputNewTutor(tutorList);
            System.out.println("New Tutor Information");
            System.out.println(newTutor.showtutorDetails());
            if (TUTORUI.confirmation("Confirm Add New Tutor? (Y/N) : ")) {
                boolean success = tutorList.add(newTutor);
                if (success) {
                    System.out.println("Tutor Added Successfully");
                    dao.saveToFile((DoublyCircularLinkedList) tutorList, "Tutor");
                    displayTutors();
                } else {
                    System.out.println("Tutor Added Failure");
                }
            }
            continueAdd = TUTORUI.confirmation("\nDo You Want To Continue Add Tutor? (Y/N) : ");

        } while (continueAdd);
    }

    public void amendTutor() {
        if (!displayTutors()) {
            return;
        }
        System.out.println("\n----Amend Tutor----");
        int index = TUTORUI.inputIndex("Enter The Index Of Tutor To Amend The Details (0 to back) : ", tutorList.getSize());
        if (index == 0) {
            return;
        }
        Tutor tutorToUpdate = tutorList.getPosition(index - 1);
        Tutor editedTutor = tutorToUpdate;
        int choice;
        do {
            choice = TUTORUI.amendMenu();
            switch (choice) {
                case 1:
                    String newName = TUTORUI.inputNewName(editedTutor.getName());
                    if (TUTORUI.confirmation("Confirm change Name from " + editedTutor.getName() + " to " + newName + "? (Y/N) : ")) {
                        editedTutor.setName(newName);
                        System.out.println("Successfully amended");
                    }
                    break;
                case 2:
                    String newIC = TUTORUI.inputNewIC(tutorList, editedTutor.getIc());
                    if (TUTORUI.confirmation("Confirm change IC number from " + editedTutor.getIc() + " to " + newIC + "? (Y/N) : ")) {
                        String newGender = TUTORUI.checkGender(newIC);
                        editedTutor.setIc(newIC);
                        editedTutor.setGender(newGender);
                        System.out.println("Successfully amended");
                    }
                    break;
                case 3:
                    String newWorkType = TUTORUI.inputNewWorkType(editedTutor.getWorkType());
                    if (TUTORUI.confirmation("Confirm change work type from " + editedTutor.getWorkType() + " to " + newWorkType + "? (Y/N) : ")) {
                        editedTutor.setWorkType(newWorkType);
                        System.out.println("Successfully amended");
                    }
                    break;
                case 4:
                    String newPhoneNo = TUTORUI.inputNewPhone(tutorList, editedTutor.getPhoneNo());
                    if (TUTORUI.confirmation("Confirm change phone number from " + editedTutor.getPhoneNo() + " to " + newPhoneNo + "? (Y/N) : ")) {
                        editedTutor.setPhoneNo(newPhoneNo);
                        System.out.println("Successfully amended");
                    }
                    break;
                case 5:
                    String newFaculty = TUTORUI.inputNewFaculty(editedTutor.getFaculty());
                    if (TUTORUI.confirmation("Confirm change email from " + editedTutor.getFaculty() + " to " + newFaculty + "? (Y/N) : ")) {
                        editedTutor.setFaculty(newFaculty);
                        System.out.println("Successfully amended");
                    }
                    break;
                case 6:
                    System.out.println("\nAmend details of tutor " + editedTutor.getName());
                    newName = TUTORUI.inputNewName(editedTutor.getName());
                    newIC = TUTORUI.inputNewIC(tutorList, editedTutor.getIc());
                    String newGender = TUTORUI.checkGender(newIC);
                    newWorkType = TUTORUI.inputNewWorkType(editedTutor.getWorkType());
                    newPhoneNo = TUTORUI.inputNewPhone(tutorList, editedTutor.getPhoneNo());
                    newFaculty = TUTORUI.inputNewFaculty(editedTutor.getFaculty());

                    String newDetails = "\nNew Tutor Details:\n"
                            + "Name      : " + newName + "\n"
                            + "IC Number : " + newIC + "\n"
                            + "Gender    : " + newGender + "\n"
                            + "Work Type : " + newWorkType + "\n"
                            + "PhoneNo   : " + newPhoneNo + "\n"
                            + "Faculty   : " + newFaculty;

                    System.out.println(newDetails);

                    if (TUTORUI.confirmation("Confirm to update all details of the student? (Y/N) : ")) {
                        editedTutor.setName(newName);
                        editedTutor.setIc(newIC);
                        editedTutor.setGender(newGender);
                        editedTutor.setWorkType(newWorkType);
                        editedTutor.setPhoneNo(newPhoneNo);
                        editedTutor.setFaculty(newFaculty);
                        System.out.println("Successfully amended");
                        return;
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please select a valid option (0-6).");
            }
            for (int i = 0; i < tutorClassList.getSize(); i++) {
                TutorClass currentClass = tutorClassList.getPosition(i);
                if (currentClass.getTutor().equals(tutorToUpdate)) {
                    currentClass.setTutor(editedTutor);
                }
            }
            tutorList.update(tutorToUpdate, editedTutor);
            dao.saveToFile((DoublyCircularLinkedList) tutorList, "Tutor");
            dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
        } while (choice != 0);
    }

    public void removeTutor() {
        boolean continueRemove;
        do {
            if (!displayTutors()) {
                return;
            }
            System.out.println("\n----Remove Tutor----");
            int index = TUTORUI.inputIndex("Enter the index of the tutor to remove (0 to back) : ", tutorList.getSize());
            if (index == 0) {
                System.out.println("Exiting......");
                return;
            }
            Tutor tutorToRemove = tutorList.getPosition(index - 1);
            System.out.println("\nTutor to Remove:");
            System.out.println("----------------------");
            System.out.println(tutorToRemove.showtutorDetails());
            if (TUTORUI.confirmation("Are you sure you want to remove this tutor? (Y/N) : ")) {
                if (index == tutorList.getSize()) {
                    tutorList.removeBack();
                } else {
                    tutorList.removeAtPosition(index - 1);
                }
                dao.saveToFile((DoublyCircularLinkedList) tutorList, "Tutor");
                for (int i = tutorClassList.getSize() - 1; i >= 0; i--) {
                    TutorClass currentClass = tutorClassList.getPosition(i);
                    if (currentClass.getTutor().equals(tutorToRemove)) {
                        tutorClassList.removeAtPosition(i);
                    }
                }
                dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                System.out.println("Tutor removed Successfully.");
                displayTutors();
            }
            continueRemove = TUTORUI.confirmation("\nDo you want to continue remove tutor? (Y/N) : ");

        } while (continueRemove);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="$ Assign Tutor $">
    public ListInterface<Course> getCourseInFaculty(String faculty) {
        ListInterface<Course> coursesInFaculty = new DoublyCircularLinkedList<>();
        for (int i = 0; i < savedCourses.getSize(); i++) {
            Course currentCourse = savedCourses.getPosition(i);
            if (!currentCourse.getProgrammesAssociated().isEmpty()) {
                ListInterface<Programme> currentProgramme = currentCourse.getProgrammesAssociated();
                for (int j = 0; j < currentProgramme.getSize(); j++) {
                    if (currentProgramme.getPosition(j).getFaculty().equals(faculty)) {
                        coursesInFaculty.add(currentCourse);
                        break;
                    }
                }
            }
        }
        coursesInFaculty = TUTORUI.sortCourseByName(coursesInFaculty);
        return coursesInFaculty;
    }

    public ListInterface<TutorialGroup> getTutorialGroupsInCourse(Course course) {
        ListInterface<TutorialGroup> tutorialGroupList = new DoublyCircularLinkedList<>();
        for (int i = 0; i < course.getProgrammesAssociated().getSize(); i++) {
            for (int j = 0; j < course.getProgrammesAssociated().getPosition(i).getTutorialGroups().getSize(); j++) {
                tutorialGroupList.add(course.getProgrammesAssociated().getPosition(i).getTutorialGroups().getPosition(j));
            }
        }
        return tutorialGroupList;
    }

    public boolean checkTutorTeachHour(Tutor tutor, int classDuration) {
        boolean valid = false;
        int teachHour = tutor.getTeachHour();
        if (teachHour >= classDuration) {
            for (int i = 0; i < tutorList.getSize(); i++) {
                if (tutorList.getPosition(i).equals(tutor)) {
                    tutorList.getPosition(i).setTeachHour(teachHour - classDuration);
                    dao.saveToFile((DoublyCircularLinkedList) tutorList, "Tutor");
                }
            }
            valid = true;
        } else {
            System.out.println("Error: The tutor's teach hours already reach the limit!");
        }
        return valid;
    }

    public int checkClassDurationSet(Course course, char classType) {
        int classDuration = 0;
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            if (tutorClassList.getPosition(i).getCourse().equals(course)
                    && tutorClassList.getPosition(i).getClassType() == classType) {
                classDuration = tutorClassList.getPosition(i).getClassDuration();
                break;
            }
        }
        return classDuration;
    }

    public void assignTutorToCourse() {
        if (!displayTutors()) {
            return;
        }
        System.out.println("\n----Assign Tutor To A Course----");
        int tutorIndex = TUTORUI.inputIndex("Enter The Index Of Tutor To Assign A Course (0 to back) : ", tutorList.getSize());
        if (tutorIndex == 0) {
            return;
        }
        Tutor tutorToAssign = tutorList.getPosition(tutorIndex - 1);
        if (TUTORUI.displayCoursesInFaculty(getCourseInFaculty(tutorToAssign.getFaculty()))) {
            int courseIndex = TUTORUI.inputIndex("Enter The Index Of Course (0 to back) : ", getCourseInFaculty(tutorToAssign.getFaculty()).getSize());
            if (courseIndex == 0) {
                return;
            }
            Course course = getCourseInFaculty(tutorToAssign.getFaculty()).getPosition(courseIndex - 1);
            char classType = TUTORUI.inputClassType(tutorToAssign, course, tutorClassList);
            if (classType == ' ') {
                return;
            }
            if (!tutorClassList.isEmpty()) {
                boolean valid = false;
                for (int i = 0; i < tutorClassList.getSize(); i++) {
                    TutorClass currentClass = tutorClassList.getPosition(i);
                    if (currentClass.isSame(tutorToAssign.getId(), course.getCourseCode(), classType)) {
                        valid = false;
                        System.out.println("Error: The tutor already assign to same course and same class type!");
                        break;
                    } else {
                        valid = true;
                    }
                }
                if (valid) {
                    int classDuration = checkClassDurationSet(course, classType);
                    if (classDuration == 0) {
                        classDuration = TUTORUI.inputClassDuration();
                    }
                    TutorClass newTutorClass = new TutorClass(tutorToAssign, classType, course);
                    newTutorClass.setClassDuration(classDuration);
                    if (newTutorClass.getClassType() == 'L') {
                        if (checkTutorTeachHour(tutorToAssign, classDuration)) {
                            ListInterface<TutorialGroup> tutorialGroup = getTutorialGroupsInCourse(course);
                            newTutorClass.setTutorialGroups(tutorialGroup);
                        }
                    }
                    if (tutorClassList.add(newTutorClass)) {
                        dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                        System.out.println("Tutor Assigned Successfully!");
                    } else {
                        System.out.println("Error: Tutor Assign Failure!");
                    }
                }
            } else {
                int classDuration = checkClassDurationSet(course, classType);
                if (classDuration == 0) {
                    classDuration = TUTORUI.inputClassDuration();
                }
                TutorClass newTutorClass = new TutorClass(tutorToAssign, classType, course);
                newTutorClass.setClassDuration(classDuration);
                if (newTutorClass.getClassType() == 'L') {
                    if (checkTutorTeachHour(tutorToAssign, classDuration)) {
                        ListInterface<TutorialGroup> tutorialGroup = getTutorialGroupsInCourse(course);
                        newTutorClass.setTutorialGroups(tutorialGroup);
                    }
                }
                if (tutorClassList.add(newTutorClass)) {
                    dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                    System.out.println("Tutor Assigned Successfully!");
                } else {
                    System.out.println("Error: Tutor Assign Failure!");
                }
            }
        }
    }

    public ListInterface<Course> getCourseInClass() {
        ListInterface<Course> coursesInClass = new DoublyCircularLinkedList<>();
        Course currentCourseCode = new Course();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (!currentClass.getCourse().equals(currentCourseCode)) {
                coursesInClass.add(currentClass.getCourse());
                currentCourseCode = currentClass.getCourse();
            }
        }
        coursesInClass = TUTORUI.sortCourseByName(coursesInClass);
        return coursesInClass;
    }

    public void assignTutorToTutorialGroup() {
        if (TUTORUI.displayCourseInClass(getCourseInClass())) {
            ListInterface<Course> coursesInClass = getCourseInClass();
            int courseIndex = TUTORUI.inputIndex("Enter The Index Of Course (0 to back) : ", coursesInClass.getSize());
            if (courseIndex == 0) {
                return;
            }
            Course course = coursesInClass.getPosition(courseIndex - 1);
            char classType = TUTORUI.selectionClassTypeForCourse(tutorClassList, course, 0);
            ListInterface<Tutor> tutorsInClass = TUTORUI.displayTutorsInCourse(tutorClassList, course, classType);
            int tutorIndex = TUTORUI.inputIndex("Enter the index of the tutor : ", tutorsInClass.getSize());
            Tutor tutor = tutorsInClass.getPosition(tutorIndex - 1);
            do {
                ListInterface<TutorialGroup> tutorialGroupsInCourse = TUTORUI.displayTutorialGroupsInCourse(tutorClassList, course, classType);
                if (tutorialGroupsInCourse.isEmpty()) {
                    return;
                }
                int tutorialGroupIndex = TUTORUI.inputIndex("Enter the index of the tutorial group (0 to back): ", tutorialGroupsInCourse.getSize());
                if (tutorialGroupIndex == 0) {
                    return;
                }
                TutorialGroup tutorialGroup = tutorialGroupsInCourse.getPosition(tutorialGroupIndex - 1);
                if (TUTORUI.confirmation("Confirm to assign tutor to tutorial group? (Y/N) : ")) {
                    for (int i = 0; i < tutorClassList.getSize(); i++) {
                        TutorClass currentClass = tutorClassList.getPosition(i);
                        if (currentClass.isSame(tutor.getId(), course.getCourseCode(), classType)) {
                            ListInterface<TutorialGroup> tutorialGroupList = currentClass.getTutorialGroups();
                            if (checkTutorTeachHour(tutor, currentClass.getClassDuration())) {
                                if (tutorialGroupList.add(tutorialGroup)) {
                                    currentClass.setTutor(tutor);
                                    currentClass.setTutorialGroups(tutorialGroupList);
                                    dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                                    System.out.println("Tutor Assigned Successfully!");
                                    break;
                                } else {
                                    System.out.println("Error: Tutor Assign Failure!");
                                }
                            }
                        }
                    }
                }
            } while (TUTORUI.confirmation("Do you want to assign more tutorial group for " + tutor.getName() + " in " + course.getCourseName() + "? (Y/N) : "));
        }

    }

    public void addTutorsToTG() {
        ListInterface<Course> coursesInClass = TUTORUI.displayCoursesForTG(getCourseInClass());
        int courseIndex = TUTORUI.inputIndex("Enter The Index Of Course (0 to back) : ", coursesInClass.getSize());
        if (courseIndex == 0) {
            return;
        }
        Course course = coursesInClass.getPosition(courseIndex - 1);
        do {
            TUTORUI.displayTutorialGroups(course);
            ListInterface<TutorialGroup> tutorialGroupsInCourse = TUTORUI.getTutorialGroupsInCourse(course);
            int tutorialGroupIndex = TUTORUI.inputIndex("Enter The Index Of Tutorial Group (0 to back) : ", tutorialGroupsInCourse.getSize());
            if (tutorialGroupIndex == 0) {
                return;
            }
            TutorialGroup tutorialGroup = tutorialGroupsInCourse.getPosition(tutorialGroupIndex - 1);
            ListInterface<TutorClass> tutorsForTG = TUTORUI.displayTutorForTG(tutorClassList, course, tutorialGroup);
            if (tutorsForTG.isEmpty()) {
                return;
            }
            int tutorIndex = TUTORUI.inputIndex("Enter the index of the tutor : ", tutorsForTG.getSize());
            TutorClass tutorClass = tutorsForTG.getPosition(tutorIndex - 1);
            if (TUTORUI.confirmation("Confirm to assign tutor to tutorial group? (Y/N) : ")) {
                ListInterface<TutorialGroup> tutorialGroupList = tutorClass.getTutorialGroups();
                if (checkTutorTeachHour(tutorClass.getTutor(), tutorClass.getClassDuration())) {
                    if (tutorialGroupList.add(tutorialGroup)) {
                        tutorClass.setTutor(tutorClass.getTutor());
                        tutorClass.setTutorialGroups(tutorialGroupList);
                        dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                        System.out.println("Tutor Assigned Successfully!");
                    } else {
                        System.out.println("Error: Tutor Assign Failure!");
                    }
                }
            }
        } while (TUTORUI.confirmation("Do you want to assign more tutor to tutorial group for " + course.getCourseName() + "? (Y/N) : "));
    }

    public void randomAssignTutorToCourse() {
        Random rand = new Random();
        int tutorIndex = rand.nextInt(tutorList.getSize());
        Tutor currentTutor = tutorList.getPosition(tutorIndex);
        int courseIndex = rand.nextInt(getCourseInFaculty(currentTutor.getFaculty()).getSize());
        Course course = getCourseInFaculty(currentTutor.getFaculty()).getPosition(courseIndex);
        char classType;
        ListInterface<String> classTypeList = new DoublyCircularLinkedList<>();
        classTypeList.add("T");
        classTypeList.add("P");
        if (currentTutor.getWorkType().equals("Full Time")) {
            classTypeList.addFront("L");
        }
        for (int j = 0; j < tutorClassList.getSize(); j++) {
            TutorClass currentTutorClass = tutorClassList.getPosition(j);
            if (currentTutorClass.getCourse().equals(course) && currentTutorClass.getClassType() == 'L') {
                classTypeList.remove("L");
            }
        }
        for (int j = 0; j < tutorClassList.getSize(); j++) {
            TutorClass currentTutorClass = tutorClassList.getPosition(j);
            if (currentTutorClass.getTutor().equals(currentTutor) && currentTutorClass.getCourse().equals(course)) {
                classTypeList.remove(Character.toString(currentTutorClass.getClassType()));
            }
        }
        if (!classTypeList.isEmpty()) {
            int classTypeIndex = rand.nextInt(classTypeList.getSize());
            classType = classTypeList.getPosition(classTypeIndex).charAt(0);
            int classDuration = checkClassDurationSet(course, classType);
            if (classDuration == 0) {
                classDuration = rand.nextInt(2) + 1;
            }
            if (!tutorClassList.isEmpty()) {
                for (int k = 0; k < tutorClassList.getSize(); k++) {
                    TutorClass currentClass = tutorClassList.getPosition(k);
                    if (!currentClass.isSame(currentTutor.getId(), course.getCourseCode(), classType)) {
                        TutorClass newTutorClass = new TutorClass(currentTutor, classType, course);
                        newTutorClass.setClassDuration(classDuration);
                        if (newTutorClass.getClassType() == 'L') {
                            if (checkTutorTeachHour(currentTutor, classDuration)) {
                                ListInterface<TutorialGroup> tutorialGroup = getTutorialGroupsInCourse(course);
                                newTutorClass.setTutorialGroups(tutorialGroup);
                                newTutorClass.setTutor(currentTutor);
                            }
                        }
                        tutorClassList.add(newTutorClass);
                        dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                        break;
                    }
                }
            } else {
                TutorClass newTutorClass = new TutorClass(currentTutor, classType, course);
                newTutorClass.setClassDuration(classDuration);
                if (newTutorClass.getClassType() == 'L') {
                    if (checkTutorTeachHour(currentTutor, classDuration)) {
                        ListInterface<TutorialGroup> tutorialGroup = getTutorialGroupsInCourse(course);
                        newTutorClass.setTutorialGroups(tutorialGroup);
                    }
                }
                tutorClassList.add(newTutorClass);
                dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
            }
        }
    }

    public void randomAssignTG() {
        Random rand = new Random();
        ListInterface<Course> coursesInClass = getCourseInClass();
        Course course = coursesInClass.getPosition(rand.nextInt(coursesInClass.getSize()));
        ListInterface<String> classTypeList = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getCourse().equals(course)) {
                switch (currentClass.getClassType()) {
                    case 'T':
                        classTypeList.addBack("Tutorial");
                        break;
                    case 'P':
                        classTypeList.addBack("Practical");
                        break;
                }
            }
        }
        if (!classTypeList.isEmpty()) {
            int classTypeIndex = rand.nextInt(classTypeList.getSize());
            char classType = classTypeList.getPosition(classTypeIndex).charAt(0);
            ListInterface<Tutor> tutorsInClass = getTutorsInCourse(tutorClassList, course, classType);
            Tutor tutor = tutorsInClass.getPosition(rand.nextInt(tutorsInClass.getSize()));
            ListInterface<TutorialGroup> tutorialGroups = getTutorialGroupsInCourse(course);
            for (int i = 0; i < tutorClassList.getSize(); i++) {
                TutorClass currentClass = tutorClassList.getPosition(i);
                if (currentClass.getCourse().equals(course) && currentClass.getClassType() == classType) {
                    for (int j = 0; j < currentClass.getTutorialGroups().getSize(); j++) {
                        TutorialGroup currentTG = currentClass.getTutorialGroups().getPosition(j);
                        if (tutorialGroups.contains(currentTG)) {
                            tutorialGroups.remove(currentTG);
                        }
                    }
                }
            }
            if (!tutorialGroups.isEmpty()) {
                TutorialGroup tutorialGroup = tutorialGroups.getPosition(rand.nextInt(tutorialGroups.getSize()));
                for (int i = 0; i < tutorClassList.getSize(); i++) {
                    TutorClass currentClass = tutorClassList.getPosition(i);
                    if (currentClass.isSame(tutor.getId(), course.getCourseCode(), classType)) {
                        ListInterface<TutorialGroup> tutorialGroupList = currentClass.getTutorialGroups();
                        if (checkTutorTeachHour(tutor, currentClass.getClassDuration())) {
                            if (tutorialGroupList.add(tutorialGroup)) {
                                currentClass.setTutor(tutor);
                                currentClass.setTutorialGroups(tutorialGroupList);
                                dao.saveToFile((DoublyCircularLinkedList) tutorClassList, "TutorClass");
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    public void randomAssign() {
        int numOfClass = TUTORUI.inputRandomClassToAssign();
        for (int i = 0; i < numOfClass; i++) {
            randomAssignTutorToCourse();
        }
        for (int i = 0; i < numOfClass; i++) {
            randomAssignTG();
        }
        System.out.println("Random Assign Successfully!");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="$ Search Tutor $">
    public void searchCourseUnderTutor() {
        displayTutors();
        int index = TUTORUI.inputIndex("Enter The Index Of Tutor (0 to back) : ", tutorList.getSize());
        if (index == 0) {
            return;
        }
        Tutor tutor = tutorList.getPosition(index - 1);
        ListInterface<Course> coursesTeachByTutor = new DoublyCircularLinkedList<>();
        ListInterface<TutorClass> tutorClassToDisplay = new DoublyCircularLinkedList<>();
        int courseCount = 0;
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getTutor().getId().equals(tutor.getId())) {
                if (!coursesTeachByTutor.contains(currentClass.getCourse())) {
                    coursesTeachByTutor.add(currentClass.getCourse());
                    tutorClassToDisplay.add(currentClass);
                }
            }
        }
        if (tutorClassToDisplay.isEmpty()) {
            System.out.println("Error: No Course Under Tutor" + tutor.getName());
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }
        System.out.println("\n" + "=".repeat(78));
        System.out.printf("|%-76s|\n", " ".repeat(26) + "Courses Under Tutor");
        System.out.printf("| %-22s %-35s %-16s|\n", "Tutor ID : " + tutor.getId(), "Tutor Name : " + tutor.getName(), "Faculty : " + tutor.getFaculty());
        System.out.println("-".repeat(78));
        tutorClassToDisplay = TUTORUI.sortTutorClassByClassCourse(tutorClassToDisplay);
        for (int i = 0; i < tutorClassToDisplay.getSize(); i++) {
            TutorClass currentClass = tutorClassToDisplay.getPosition(i);
            System.out.printf("| %-75s|\n", (courseCount += 1) + ". " + currentClass.getCourse().getCourseCode() + " - " + currentClass.getCourse().getCourseName());
        }
        System.out.println("-".repeat(78));
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public ListInterface<Tutor> getTutorsInCourse(ListInterface<TutorClass> tutorClassList, Course selectedCourse, char selectedClassType) {
        ListInterface<Tutor> tutors = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getClassType() == selectedClassType && currentClass.getCourse().equals(selectedCourse)) {
                Tutor tutor = currentClass.getTutor();
                tutors.add(tutor);
            }
        }
        return tutors;
    }

    public void searchTutorForCourse() {
        if (!TUTORUI.displayCourseInClass(getCourseInClass())) {
            return;
        }
        ListInterface<Course> coursesInClass = getCourseInClass();
        int courseIndex = TUTORUI.inputIndex("Enter The Index Of Course (0 to back) : ", coursesInClass.getSize());
        if (courseIndex == 0) {
            return;
        }
        Course course = coursesInClass.getPosition(courseIndex - 1);
        ListInterface<TutorClass> tutorClassMatchCourse = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getCourse().equals(course)) {
                tutorClassMatchCourse.add(currentClass);
            }
        }
        char classType = TUTORUI.selectionClassTypeForCourse(tutorClassList, course, 1);
        ListInterface<Tutor> tutorsInClass = getTutorsInCourse(tutorClassList, course, classType);
        ListInterface<Tutor> filterTutors = filterTutor(tutorsInClass);
        if (filterTutors.isEmpty()) {
            System.out.println("Error: No Tutor Available For" + course.getCourseName());
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }
        int index = 0;
        System.out.println("\n" + "=".repeat(63));
        System.out.printf("|%-61s|\n", " ".repeat(5) + course.getCourseCode() + " - " + course.getCourseName() + " - " + TUTORUI.checkClassType(classType));
        System.out.printf("| %-7s %-10s %-20s %-20s|\n", "Index", "Tutor ID", "Tutor Name", "Email");
        System.out.println("=".repeat(63));
        for (int i = 0; i < filterTutors.getSize(); i++) {
            Tutor tutor = filterTutors.getPosition(i);
            System.out.printf("| %-7s %-10s %-20s %-20s|\n", index += 1, tutor.getId(), tutor.getName(), tutor.getEmail());
        }
        System.out.println("=".repeat(63));
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void listClassInfoForCourse() {
        if (!TUTORUI.displayCourseInClass(getCourseInClass())) {
            return;
        }
        ListInterface<Course> coursesInClass = getCourseInClass();
        int courseIndex = TUTORUI.inputIndex("Enter The Index Of Course (0 to back) : ", coursesInClass.getSize());
        if (courseIndex == 0) {
            return;
        }
        Course course = coursesInClass.getPosition(courseIndex - 1);
        ListInterface<TutorClass> tutorClassToDisplay = new DoublyCircularLinkedList<>();
        for (int i = 0; i < tutorClassList.getSize(); i++) {
            TutorClass currentClass = tutorClassList.getPosition(i);
            if (currentClass.getCourse().equals(course)) {
                tutorClassToDisplay.add(currentClass);
            }
        }
        System.out.println();
        System.out.print("=".repeat(55));
        System.out.printf("\n|%-3s%-50s|\n", " ", "Course " + course.getCourseCode() + " - " + course.getCourseName());
        System.out.printf("| %-10s %-20s %-20s|\n", "Tutor ID", "Tutor Name", "Class Type");
        System.out.print("=".repeat(55) + "\n");
        tutorClassToDisplay = TUTORUI.sortTutorClassByClassType(tutorClassToDisplay);
        for (int i = 0; i < tutorClassToDisplay.getSize(); i++) {
            TutorClass currentClass = tutorClassToDisplay.getPosition(i);
            System.out.printf("| %-10s %-20s %-20s|\n", currentClass.getTutor().getId(), currentClass.getTutor().getName(), TUTORUI.checkClassType(currentClass.getClassType()));
        }
        System.out.print("=".repeat(55));
        System.out.printf("\n| %-52s|\n", "Tutorial Groups");
        System.out.printf("| %-52s|\n", "---------------");
        int index = 0;
        for (int i = 0; i < savedCourses.getSize(); i++) {
            Course currentCourse = savedCourses.getPosition(i);
            if (currentCourse.equals(course)) {
                for (int j = 0; j < course.getProgrammesAssociated().getSize(); j++) {
                    Programme currentProgramme = course.getProgrammesAssociated().getPosition(j);
                    for (int k = 0; k < currentProgramme.getTutorialGroups().getSize(); k++) {
                        System.out.printf("| %-52s|\n", (index += 1) + ". " + currentProgramme.getTutorialGroups().getPosition(k).getTutorialGroupId());
                    }
                }
                System.out.print("-".repeat(55) + "\n");
            }
        }
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void listCourseForEachTutor() {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("| %-10s %-20s %-45s|\n", "Tutor ID", "Tutor Name", "Courses");
        System.out.println("=".repeat(80));
        for (int j = 0; j < tutorList.getSize(); j++) {
            boolean courseFound = false;
            boolean courseAvailable = false;
            Tutor tutor = tutorList.getPosition(j);
            System.out.printf("| %-10s %-20s", tutor.getId(), tutor.getName());
            int count = 0;
            ListInterface<Course> coursesDisplayed = new DoublyCircularLinkedList<>();
            ListInterface<TutorClass> classCourseToDisplay = new DoublyCircularLinkedList<>();
            for (int i = 0; i < tutorClassList.getSize(); i++) {
                TutorClass currentClass = tutorClassList.getPosition(i);
                if (currentClass.getTutor().equals(tutor)) {
                    if (!coursesDisplayed.contains(currentClass.getCourse())) {
                        classCourseToDisplay.add(currentClass);
                        coursesDisplayed.add(currentClass.getCourse());
                        courseFound = true;
                    }
                }
            }
            classCourseToDisplay = TUTORUI.sortTutorClassByClassCourse(classCourseToDisplay);
            for (int i = 0; i < classCourseToDisplay.getSize(); i++) {
                TutorClass currentClass = classCourseToDisplay.getPosition(i);
                if (count > 0) {
                    System.out.print("|" + " ".repeat(32));
                }
                System.out.printf(" %-45s|\n", currentClass.getCourse().getCourseCode() + " - " + currentClass.getCourse().getCourseName());
                count++;
            }
            if (!courseAvailable && !courseFound) {
                System.out.printf(" %-45s|\n", "N/A");
            }
            System.out.println("-".repeat(80));
        }
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public ListInterface<Tutor> filterTutor(ListInterface<Tutor> tutorList) {
        ListInterface<Tutor> filteredTutors = new DoublyCircularLinkedList<>();
        TUTORUI.filterTutorMenu();
        int filterType = TUTORUI.inputIndex("Enter the index of the filter type : ", 3);
        String filterValue = "";
        switch (filterType) {
            case 1:
                filterValue = TUTORUI.inputName();
                break;
            case 2:
                filterValue = TUTORUI.inputWorkType();
                break;
            case 3:
                filterValue = TUTORUI.inputGender();
                break;
            default:
                return tutorList;
        }
        for (int i = 0; i < tutorList.getSize(); i++) {
            Tutor tutor = tutorList.getPosition(i);
            boolean matchesFilter = false;
            switch (filterType) {
                case 1:
                    matchesFilter = tutor.getName().contains(filterValue);
                    break;
                case 2:
                    matchesFilter = tutor.getWorkType().equalsIgnoreCase(filterValue);
                    break;
                case 3:
                    matchesFilter = tutor.getGender().equalsIgnoreCase(filterValue);
                    break;
            }
            if (matchesFilter) {
                filteredTutors.add(tutor);
            }
        }
        return filteredTutors;
    }
    // </editor-fold>

    public void generateReport() {
        int reportType = TUTORUI.reportMenu();
        switch (reportType) {
            case 1:
                if (!tutorList.isEmpty() && !tutorClassList.isEmpty()) {
                    TUTORUI.displayPartTimeTutorReport(tutorList, tutorClassList);
                } else {
                    System.out.println("Error: No tutor or tutor class record found!");
                }
                break;
            case 2:
                if (!tutorList.isEmpty() && !tutorClassList.isEmpty()) {
                    TUTORUI.displayTutorAssignmentReport(tutorList, tutorClassList);
                } else {
                    System.out.println("Error: No tutor or tutor class record found!");
                }
                break;
            default:
                break;
        }
    }
}
