package control;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import adt.ArrayList;
import entity.*;
import boundary.TutorialGroupManagementUI;
import dao.DoublyCircularLinkedListDAO;
import utility.Utility;
import utility.Validator;


import java.io.Serializable;

public class TutorialGroupManagement implements Serializable {
    private ListInterface<TutorialGroup> tutorialGroupList = new DoublyCircularLinkedList<>();
    private ListInterface<Programme> programmeList = new DoublyCircularLinkedList<>();
    private ListInterface<Student> studentList = new DoublyCircularLinkedList<>();
    private DoublyCircularLinkedListDAO dao = new DoublyCircularLinkedListDAO();
    private final TutorialGroupManagementUI TUTORIALGROUPUI = new TutorialGroupManagementUI();


    DoublyCircularLinkedList<TutorialGroup> savedTutorialGroup = dao.loadFromFile("TutorialGroup");

    public TutorialGroupManagement(){
        Object retrievedData = dao.loadFromFile("TutorialGroup");
        if (retrievedData != null && retrievedData instanceof ListInterface) {
            tutorialGroupList = (ListInterface<TutorialGroup>) retrievedData;
        } else {
            tutorialGroupList = new DoublyCircularLinkedList<>();
        }
    }

    public void saveToFile() {
        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
    }


    public void runTutorialGroupManagement(){
        int choice;
        do{
            choice = TUTORIALGROUPUI.tutorialGroupMenu();
            switch(choice){
                case 0:
                    //Exit the menu
                    break;
                case 1:
                    Utility.clearScreen();
                    optimiseTutorialGroupsFromSchema();
                    break;
                case 2:
                    Utility.clearScreen();
                    addTutorialGroup();
                    break;
                case 3:
                    Utility.clearScreen();
                    removeTutorialGroup();
                    break;
                case 4:
                    Utility.clearScreen();
                    displayTutorialGroupList();
                    break;
                case 5:
                    Utility.clearScreen();
                    addStudentToTutorialGroup();
                    break;
                case 6:
                    Utility.clearScreen();
                    removeStudentFromTutorialGroup();
                    break;
                case 7:
                    Utility.clearScreen();
                    changeStudentTutorialGroup();
                    break;
                case 8:
                    Utility.clearScreen();
                    changeStudentProgramme();
                    break;
                case 9:
                    Utility.clearScreen();
                    displayStudentListForTutorialGroup();
                    break;
                case 10:
                    Utility.clearScreen();
                    generateReport();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while(choice != 0);
    }


    public void optimiseTutorialGroupsFromSchema(){
        boolean optimiseWithNewStudents = false;

        programmeList = dao.loadFromFile("Programme");
        if (programmeList.isEmpty()) {
            System.out.println("No Programme available, please add Programme first");
            return;
        }

        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to optimise: ");
        int option = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(option-1);

        //As first is repeating group, check for tutorial groups after 0
//        if (programme.getTutorialGroups().getPosition(1) == null) {
//            System.out.println("No Tutorial Groups exist. Please add Tutorial Groups first");
//            return;
//        }
//
//        if (programme.getTutorialGroups().getSize() <=2) {
//            System.out.println("Not enough Tutorial Groups to optimise. Please add more Tutorial Groups first");
//            return;
//        }


        studentList = dao.loadFromFile("Student");
        if (studentList.isEmpty()) {
            System.out.println("No Students available, please add Students first");
            return;
        }



        ListInterface<Student> notInTutorialGroup = getStudentsNotInTutorialGroup(studentList);

        ListInterface<Student> filteredStudentList = new DoublyCircularLinkedList<>();
        for (Student s : notInTutorialGroup) {
            if (s.getStudentProgramme().getProgrammeCode().equals(programme.getProgrammeCode())) {
                filteredStudentList.add(s);
            }
        }

        if (!filteredStudentList.isEmpty()) { //Meaning there is new students available, confirm with user if they want to add new students or not
            optimiseWithNewStudents = TUTORIALGROUPUI.askToOptimiseWithAddOrNot();
        } else {
            System.out.println("No new students available to add to tutorial groups, optimising with existing students only.");
        }


        int sumStudents = 0;
        ListInterface<TutorialGroup> tutorialGroups = programme.getTutorialGroups();
        for (int i = 1; i < tutorialGroups.getSize(); i++) {
            TutorialGroup tutorialGroup = tutorialGroups.getPosition(i);
            int numStudentsInTG = tutorialGroup.getStudents().getSize();
            sumStudents += numStudentsInTG;
        }

        if (sumStudents == 0) {
            System.out.println("No students in tutorial groups. Please add students to tutorial groups first.");
            return;
        } else if (sumStudents < 2) {
            System.out.println("Not enough students in tutorial groups. No optimisation needed");
            return;
        }

        ListInterface<Student> studentHolder = new DoublyCircularLinkedList<>();
        if(optimiseWithNewStudents) {
            // Step 1 : Display all students that are not in tutorial group for user to pick


            TUTORIALGROUPUI.displayStudentList(filteredStudentList);

            // Step 2 : User select students to add to tutorial group

            ListInterface<Integer> pickedStudentIndexes =  TUTORIALGROUPUI.askToPickStudentsFromList(1, filteredStudentList.getSize());


            for (Integer i : pickedStudentIndexes) {
                studentHolder.add(filteredStudentList.getPosition(i-1));
            }
            sumStudents += pickedStudentIndexes.getSize();
        }
        int optimalNum = TUTORIALGROUPUI.askForOptimalNumberInTutorialGroup();




        ListInterface<Integer> optimisedDistribution = generateOptimisedDistribution(sumStudents, optimalNum);
        optimiseTutorialGroupsFromSchema(optimisedDistribution, programme, (DoublyCircularLinkedList<Student>) studentHolder);

        System.out.println("Tutorial Groups have been optimised to\n");
        System.out.println(optimisedDistribution);

        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void addTutorialGroup(){


        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to add Tutorial Group to: ");

        int option = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(option-1);

        TutorialGroup newTutorialGroup = new TutorialGroup(programme.getProgrammeCode(),
                programme.getProgrammeIntake().substring(programme.getProgrammeIntake().length()-4),programme.getTutorialGroups().getSize());

        tutorialGroupList.add(newTutorialGroup);

        programme.getTutorialGroups().add(newTutorialGroup);
        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");

        System.out.println("Tutorial Group has been added\n");
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void  removeTutorialGroup(){
        programmeList = dao.loadFromFile("Programme");

        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to remove Tutorial Group from: ");
        int option = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(option-1);

        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int tutorialGroupId = Validator.intInput("Enter which Tutorial Group to remove by position: ");
        programme.getTutorialGroups().removeAtPosition(tutorialGroupId-1);

        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        System.out.println("Tutorial Group has been removed\n");
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void displayTutorialGroupList(){
        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to display Tutorial Group List: ");
        int option = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(option-1);
        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        Utility.pressEnterToContinue();
        Utility.clearScreen();

    }

    public void addStudentToTutorialGroup(){
        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to add Student to Tutorial Group to: ");
        int programmePosition = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(programmePosition-1);

        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int tutorialGroupId = Validator.intInput("Enter which Tutorial Group to add student by position: ");
        TutorialGroup programmeTutorialGroup = programme.getTutorialGroups().getPosition(tutorialGroupId-1);

        studentList = dao.loadFromFile("Student");
        //ListInterface<Student> studentListNotInTutorialGroup = getStudentsNotInTutorialGroup(studentList);

        ListInterface<Student> notInTutorialGroup = getStudentsNotInTutorialGroup(studentList);

        ListInterface<Student> filteredStudentList = new DoublyCircularLinkedList<>();
        for (Student s : notInTutorialGroup) {
            if (s.getStudentProgramme().getProgrammeCode().equals(programme.getProgrammeCode())) {
                filteredStudentList.add(s);
            }
        }
        if (filteredStudentList.isEmpty()) {
            System.out.println("No students available to add to tutorial group");
            Utility.pressEnterToContinue();
            Utility.clearScreen();
            return;
        }

        TUTORIALGROUPUI.displayStudentList(filteredStudentList);
        int studentPosition = Validator.intInput("Enter which student to add by position: ");
        filteredStudentList.getPosition(studentPosition-1).setIsInTutorialGroup(true);
        programmeTutorialGroup.getStudents().add(filteredStudentList.getPosition(studentPosition-1));


        tutorialGroupList = dao.loadFromFile("TutorialGroup");
        for (TutorialGroup tutorialGroup : tutorialGroupList) {
            if (tutorialGroup.equals(programmeTutorialGroup)) {
                tutorialGroup.setStudents(programmeTutorialGroup.getStudents());
            }
        }

        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        System.out.println("Student has been added to Tutorial Group\n");
        Utility.pressEnterToContinue();
        Utility.clearScreen();

    }

    public void removeStudentFromTutorialGroup(){
        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to remove Student from Tutorial Group: ");
        int programmePosition = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(programmePosition-1);

        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int tutorialGroupId = Validator.intInput("Enter which Tutorial Group to remove student by position: ");
        TutorialGroup currentTutorialGroup = programme.getTutorialGroups().getPosition(tutorialGroupId-1);

        studentList = currentTutorialGroup.getStudents();
        TUTORIALGROUPUI.displayStudentList(currentTutorialGroup.getStudents());
        int studentPosition = Validator.intInput("Enter which student to remove by position: ");
        Student toBeRemoved = currentTutorialGroup.getStudents().getPosition(studentPosition-1);
        toBeRemoved.setIsInTutorialGroup(false);
        currentTutorialGroup.getStudents().remove(toBeRemoved);


        tutorialGroupList = dao.loadFromFile("TutorialGroup");
        for (TutorialGroup tutorialGroup : tutorialGroupList) {
            if (tutorialGroup.equals(currentTutorialGroup)) {
                tutorialGroup.setStudents(currentTutorialGroup.getStudents());
            }
        }
        ListInterface<Student> loadedStudents = dao.loadFromFile("Student");
        for (Student s : loadedStudents) {
            if (s.equals(toBeRemoved)){
                System.out.println("Student ID: " + s.getStudentID() + " has been removed from Tutorial Group");
                s.setIsInTutorialGroup(false);
            }
        }

        dao.saveToFile((DoublyCircularLinkedList) loadedStudents, "Student");
        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        System.out.println("Student has been removed from Tutorial Group\n");
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void changeStudentTutorialGroup(){
        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to change Student Tutorial Group: ");
        int programmePosition = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(programmePosition-1);

        if(programme.getTutorialGroups().getSize() < 2){
            System.out.println("Programme only have 1 Tutorial Group, please add more Tutorial Group to change student Tutorial Group");
            return;
        }

        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int tutorialGroupId = Validator.intInput("Enter which Tutorial Group to change student by position: ");
        TutorialGroup currentTutorialGroup = programme.getTutorialGroups().getPosition(tutorialGroupId-1);

        TUTORIALGROUPUI.displayStudentList(currentTutorialGroup.getStudents());
        int studentPosition = Validator.intInput("Enter which student to change by position: ");
        Student student = currentTutorialGroup.getStudents().getPosition(studentPosition-1);


        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int newTutorialGroupId = Validator.intInput("Enter which Tutorial Group to change student to by position: ");
        TutorialGroup newTutorialGroup = programme.getTutorialGroups().getPosition(newTutorialGroupId-1);

        if(newTutorialGroup.getStudents().contains(student)){
            System.out.println("Not able to change,student is in the current Tutorial Group");
            return;
        }

        currentTutorialGroup.getStudents().remove(student);
        newTutorialGroup.getStudents().add(student);

        tutorialGroupList = dao.loadFromFile("TutorialGroup");
        for (TutorialGroup tutorialGroup : tutorialGroupList) {
            if (tutorialGroup.equals(currentTutorialGroup)) {
                tutorialGroup.setStudents(currentTutorialGroup.getStudents());
            }
            if (tutorialGroup.equals(newTutorialGroup)) {
                tutorialGroup.setStudents(newTutorialGroup.getStudents());
            }
        }

        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        System.out.println("Student Tutorial Group has been changed\n");
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void displayStudentListForTutorialGroup(){
        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to display Student List for Tutorial Group: ");
        int programmePosition = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(programmePosition-1);
        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int tutorialGroupId = Validator.intInput("Enter which Tutorial Group to display student list by position: ");
        TUTORIALGROUPUI.displayStudentList(programme.getTutorialGroups().getPosition(tutorialGroupId-1).getStudents());
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void generateReport(){
        int choice = TUTORIALGROUPUI.displayReport();
        programmeList = dao.loadFromFile("Programme");

        if (choice == 0){
            return;
        }
        while(choice != 1 && choice != 2) {
            System.out.println("Please enter a valid choice.");
            choice = TUTORIALGROUPUI.displayReport();
        }

        switch (choice){
            case 1 -> {
                Utility.clearScreen();
                TUTORIALGROUPUI.displayReport1(programmeList);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
            case 2 -> {
                Utility.clearScreen();
                TUTORIALGROUPUI.displayReport2(programmeList);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
            }
        }




    }

    public int[] genderCount(ListInterface<Student> studentList){
        int[] toReturn = new int[2];
        int male = 0;
        int female = 0;
        for (Student student : studentList) {
            if (student.getStudentGender().equals("M")) {
                male++;
            } else if (student.getStudentGender().equals("F")) {
                female++;
            }
        }
        toReturn[0] = male;
        toReturn[1] = female;
        return toReturn;
    }

    public static void main(String[] args) {
        TutorialGroupManagement tutorialGroupManagement = new TutorialGroupManagement();
        tutorialGroupManagement.runTutorialGroupManagement();
    }


    public ListInterface<Student> getStudentsNotInTutorialGroup(ListInterface<Student> studentList) {
        ListInterface<Student> studentListNotInTutorialGroup = new DoublyCircularLinkedList<>();

        for (Student s : studentList) {
            if (!s.getIsInTutorialGroup()) { // Checks if student is not in tutorial group
                studentListNotInTutorialGroup.add(s);
            }
        }
        return studentListNotInTutorialGroup;
    }

    public ListInterface<Integer> generateOptimisedDistribution(int fullSize,int optimalNum) {
        ListInterface<Integer> tutorialGroupSizes = new ArrayList<>();
        if (fullSize == 0 || optimalNum == 0) {
            return null;
        }

        // Returns the `fullsize` if it is smaller than the optimal number
        if(fullSize <= optimalNum) {
            tutorialGroupSizes.add(fullSize);
            return tutorialGroupSizes;
        }

        int preHeight = fullSize/optimalNum;
        int lastHeightRemainder = fullSize%optimalNum;
        //Build base
        for (int i = 0; i < preHeight; i++) {
            tutorialGroupSizes.add(optimalNum);
        }
        if (lastHeightRemainder != 0) { //If there are any remainders left, append
            tutorialGroupSizes.add(lastHeightRemainder);
        }

        // If there are only 2, return the distribution, as the base is already optimised
        if (tutorialGroupSizes.getSize() <= 2){
            return tutorialGroupSizes;
        }

        int lastIndex = tutorialGroupSizes.getSize()-1;
        int forComparison = lastIndex-1; // Get second last index
        int deductIndex = lastIndex-1;


        //Get an optimised distribution
        while (tutorialGroupSizes.getPosition(forComparison) > tutorialGroupSizes.getPosition(lastIndex)) {
            if (deductIndex >= 0) {
                tutorialGroupSizes.updateAtPosition(deductIndex, tutorialGroupSizes.getPosition(deductIndex) - 1);
                tutorialGroupSizes.updateAtPosition(lastIndex, tutorialGroupSizes.getPosition(lastIndex) + 1);
                deductIndex--;
            } else {
                deductIndex = lastIndex-1; //Reset to second last index
            }
        }

        return tutorialGroupSizes;

    }

    public void optimiseTutorialGroupsFromSchema(ListInterface<Integer> schema, Programme programme , DoublyCircularLinkedList<Student> studentHolder) {
        int actualProgrammeTutorialGroupSize = programme.getTutorialGroups().getSize()-1;
        tutorialGroupList = dao.loadFromFile("TutorialGroup");
        studentList = dao.loadFromFile("Student");

        // Not enough tutorial groups, add enough to put in students
        if (actualProgrammeTutorialGroupSize < schema.getSize() ) {
            int diff = schema.getSize() - actualProgrammeTutorialGroupSize;
            for (int i = 0; i < diff; i++) {
                TutorialGroup newTG = new TutorialGroup(programme.getProgrammeCode(), programme.getProgrammeIntake().substring(programme.getProgrammeIntake().length()-4), programme.getTutorialGroups().getSize());
                programme.getTutorialGroups().add(newTG);
                tutorialGroupList.add(newTG);

            }
        }

        // Too many tutorial groups, remove the last few, and park students in studentHolder, minus one because of Repeating
        else if (programme.getTutorialGroups().getSize()-1 > schema.getSize()) {

            int diff = actualProgrammeTutorialGroupSize - schema.getSize();

            for (int i = 0; i < diff; i++) {
                TutorialGroup tg = programme.getTutorialGroups().getPosition(programme.getTutorialGroups().getSize()-1);

                for (Student st: tg.getStudents()) {
                    studentHolder.add(st);
                }

                //studentHolder.addAllBehind((DoublyCircularLinkedList<Student>) tg.getStudents());
                programme.getTutorialGroups().removeAtPosition(programme.getTutorialGroups().getSize()-1);
                tutorialGroupList.remove(tg);
            }

        }

        int min = schema.getPosition(0);
        //Find smallest to use for optimisation, remove all students after min and park in studentHolder temporarily

        for(int i=0; i<schema.getSize(); i++) {
            if(min > schema.getPosition(i)) {
                min =  schema.getPosition(i);
            }
        }

        ListInterface<TutorialGroup> tg = programme.getTutorialGroups();

        for (int i = 1; i < tg.getSize(); i++) {
            TutorialGroup inner = tg.getPosition(i);
            int currentSize = inner.getStudents().getSize();

            if (currentSize > min) {
                for (int j = min; j < currentSize; j++) { //Iterating from min number helps efficiency a bit
                    studentHolder.add(inner.getStudents().getPosition(j)); //Very inefficient as no "Remove and return element" function in adt, two O(n) operations here
                }
                for (int j = currentSize-1; j >= min; j--) {
                    inner.getStudents().removeAtPosition(j);
                }

            }

        }

        int index = 0;
        for (int i = 1; i< tg.getSize(); i++) {
            TutorialGroup inner = tg.getPosition(i);
            while(inner.getStudents().getSize() < schema.getPosition(i-1)) {
                int tester = i-1;
                Student curr = studentHolder.getPosition(0);

                //Update student list of student with is in tutorial group
                for (Student s : studentList) {
                    if (s.getStudentID().equals(curr.getStudentID())) {
                        s.setIsInTutorialGroup(true);
                        curr.setIsInTutorialGroup(true);
                    }
                }

                inner.getStudents().add(curr);
                studentHolder.removeFront();
            }
        }

        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");



    }

    public void changeStudentProgramme(){
        programmeList = dao.loadFromFile("Programme");
        TUTORIALGROUPUI.displayProgrammeList(programmeList);
        System.out.println("Select which Programme to change Student Tutorial Group: ");
        int programmePosition = TUTORIALGROUPUI.getTutorialGroupPosition();
        Programme programme = programmeList.getPosition(programmePosition-1);

        if(programme.getTutorialGroups().getSize() < 2){
            System.out.println("Programme only have 1 Tutorial Group, please add more Tutorial Group to change student Tutorial Group");
            return;
        }

        TUTORIALGROUPUI.displayTutorialGroupList(programme.getTutorialGroups());
        int tutorialGroupId = Validator.intInput("Enter which Tutorial Group to change student by position: ");
        TutorialGroup currentTutorialGroup = programme.getTutorialGroups().getPosition(tutorialGroupId-1);

        TUTORIALGROUPUI.displayStudentList(currentTutorialGroup.getStudents());
        int studentPosition = Validator.intInput("Enter which student to change by position: ");
        Student student = currentTutorialGroup.getStudents().getPosition(studentPosition-1);

        transferStudentProgramme(programmeList,currentTutorialGroup,student,programme);

        System.out.println("Student Programme has been changed\n");
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void transferStudentProgramme(ListInterface<Programme> programmeList,TutorialGroup currentTutorialGroup,Student student,Programme programme){
        ListInterface<Programme> transferProgrammeList = new DoublyCircularLinkedList<>();
        for (Programme p : programmeList) {
            if (!p.equals(programme) && programme.getFaculty().equals(p.getFaculty())) {
                transferProgrammeList.add(p);
            }
        }
        TUTORIALGROUPUI.displayProgrammeList(transferProgrammeList);
        System.out.println("Select which Programme to change Student to: ");
        Programme newProgramme = transferProgrammeList.getPosition(TUTORIALGROUPUI.getTutorialGroupPosition()-1);

        currentTutorialGroup.getStudents().remove(student);
        TutorialGroup newTutorialGroup = newProgramme.getTutorialGroups().getPosition(1);
        newTutorialGroup.getStudents().add(student);
        student.setStudentProgramme(newProgramme);


        for (TutorialGroup tutorialGroup : tutorialGroupList) {
            if (tutorialGroup.equals(currentTutorialGroup)) {
                tutorialGroup.setStudents(currentTutorialGroup.getStudents());
            }
            if (tutorialGroup.equals(newTutorialGroup)) {
                tutorialGroup.setStudents(newTutorialGroup.getStudents());
            }
        }

        dao.saveToFile((DoublyCircularLinkedList) programmeList, "Programme");
        dao.saveToFile((DoublyCircularLinkedList) tutorialGroupList, "TutorialGroup");
        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
    }
    
}
