/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import boundary.maintainStudentUI;
import dao.DoublyCircularLinkedListDAO;
import entity.*;
import entity.Student.CourseStatus;
import java.util.Calendar;
import java.util.Comparator;
import utility.Utility;

public class MaintainStudent {

    ListInterface<Student> studentList = new DoublyCircularLinkedList<>();
    maintainStudentUI studentUI = new maintainStudentUI();
    private DoublyCircularLinkedListDAO dao = new DoublyCircularLinkedListDAO();
    ListInterface<Programme> savedProgramme = dao.loadFromFile("Programme");
    ListInterface<Course> savedCourse = new DoublyCircularLinkedList<>();
    ListInterface<TutorialGroup> savedTutorialGroup = dao.loadFromFile("TutorialGroup");
    ListInterface<CreditTransfer> creditTransferList = dao.loadFromFile("CreditTransfer");

    public MaintainStudent() {
        Object retrievedData = dao.loadFromFile("Student");
        if (retrievedData != null && retrievedData instanceof ListInterface) {
            studentList = (ListInterface<Student>) retrievedData;
        } else {
            studentList = new DoublyCircularLinkedList<>();
        }

        Object retrievedCData = dao.loadFromFile("Course");
        if (retrievedCData != null && retrievedCData instanceof ListInterface) {
            savedCourse = (ListInterface<Course>) retrievedCData;
        } else {
            savedCourse = new DoublyCircularLinkedList<>();
        }
    }

    public void studentRegMenu() {
        int choice;
        do {
            choice = studentUI.stdRegMenu();
            switch (choice) {
                case 1 -> {
                    Utility.clearScreen();
                    addStd();
                }
                case 2 -> {
                    Utility.clearScreen();
                    removeStd();
                }
                case 3 -> {
                    Utility.clearScreen();
                    amendStudentDetails();
                }
                case 4 -> {
                    Utility.clearScreen();
                    displayCourseOfStudent();
                }
                case 5 -> {
                    Utility.clearScreen();
                    addStudentToCourse();
                }
                case 6 -> {
                    Utility.clearScreen();
                    removeStudentFromCourse();
                }
                case 7 -> {
                    Utility.clearScreen();
                    studentCreditTransfer();
                }
                case 8 -> {
                    Utility.clearScreen();
                    displaStudentFee();
                }
                case 9 -> {
                    Utility.clearScreen();
                    listStudentForCourse();
                }
                case 10 -> {
                    Utility.clearScreen();
                    report();
                }
                case 11 -> {
                    studentUI.displayStudentsAllDetails(studentList);
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
                case 0 -> {
                    return;
                }
                default ->
                    studentUI.displayInvalidOption("0-11");
            }
        } while (choice != 0);
    }

    public void addStd() {
        boolean continueRegister = true;

        do {
            Student newStudent = studentUI.getStudDetails(savedProgramme, studentList);
            studentUI.displayNewStudent(newStudent);

            if (!studentList.contains(newStudent)) {
                if (studentUI.confirmation("\nConfirm registration? (Y/N) : ")) {
                    // Add new students to the student list
                    boolean added = studentList.add(newStudent);
                    if (added) {
                        enrollStudentForCourse(newStudent);
                        studentUI.displaySuccessfullMessage("Student Register");
                        studentUI.displayBill(newStudent);
                        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                    } else {
                        studentUI.displayFailMessage("register a new student.");
                    }

                }
                continueRegister = studentUI.askContinue("Do you want to continue registering for a new student? (Y/N) : ");
            }
        } while (continueRegister);

        studentUI.displayStudents(studentList);
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void removeStd() {

        boolean continueRemove = true;
        do {
            studentUI.removeStudentUI();
            studentUI.displayStudents(studentList);
            if (studentList.isEmpty()) {
                studentUI.displayExitMessage();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                return;
            }

            int index = studentUI.inputIndex("Enter the index of the student to remove (0 to back) : ", studentList.getSize());
            if (index == 0) {
                studentUI.displayExitMessage();
                Utility.clearScreen();
                return;
            }
            index -= 1;
            Student studentToRemove = studentList.getPosition(index);

            studentUI.displayStudentToRemove(studentToRemove);

            if (studentUI.confirmation("\nAre you sure you want to remove this student? (Y/N) : ")) {
                boolean removed = studentList.removeAtPosition(index);
                if (removed) {
                    removeStudentFromTutGrp(studentToRemove);
                    studentUI.displaySuccessfullMessage("Student removed");
                    dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                } else {
                    studentUI.displayFailMessage("remove the student.");
                }

            }
            continueRemove = studentUI.askContinue("\nDo you want to continue Remove student? (Y/N) : ");

        } while (continueRemove);
        studentUI.displayStudents(studentList);
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void removeStudentFromTutGrp(Student studentToRemove) {
        savedProgramme = dao.loadFromFile("Programme");
        savedTutorialGroup = dao.loadFromFile("TutorialGroup");

        // Remove from programme
        for (Programme pg : savedProgramme) {
            if (pg.equals(studentToRemove.getStudentProgramme())) {
                for (TutorialGroup tg : pg.getTutorialGroups()) {
                    if (tg.getStudents().contains(studentToRemove)) {
                        tg.getStudents().remove(studentToRemove);
                    }
                }
            }
        }
        //Remove from Tutorial Group
        for (TutorialGroup tg : savedTutorialGroup) {
            if (tg.getStudents().contains(studentToRemove)) {
                tg.getStudents().remove(studentToRemove);
            }
        }

        // Remove from Student's Programme
        for (TutorialGroup tg : studentToRemove.getStudentProgramme().getTutorialGroups()) {
            if (tg.getStudents().contains(studentToRemove)) {
                tg.getStudents().remove(studentToRemove);
            }
        }
        studentToRemove.setIsInTutorialGroup(false);
        dao.saveToFile((DoublyCircularLinkedList) savedProgramme, "Programme");
        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
        dao.saveToFile((DoublyCircularLinkedList) savedTutorialGroup, "TutorialGroup");

    }

    public void amendStudentDetails() {
        studentUI.displayAmendUI();
        studentUI.displayStudents(studentList); // Display the list of students first
        if (!studentList.isEmpty()) {
            int index = studentUI.inputIndex("Enter the index of student to amend details (0 to back) : ", studentList.getSize());
            if (index == 0) {
                studentUI.displayExitMessage();
                Utility.clearScreen();
                return;
            }
            index -= 1;
            Student studentToUpdate = studentList.getPosition(index);
            int choice;
            do {
                choice = studentUI.amendMenu();
                switch (choice) {
                    case 1:
                        String newName = studentUI.getNewName(studentToUpdate.getStudentName());
                        if (studentUI.confirmation("Confirm change Name from " + studentToUpdate.getStudentName() + " to " + newName + " (Y/N) : ")) {
                            studentToUpdate.setStudentName(newName);
                            studentUI.displaySuccessfullMessage("amended");
                            dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 2:
                        String newIC = studentUI.getNewIC(studentList, studentToUpdate.getStudentIC());
                        String newGender = (Integer.parseInt(newIC.substring(newIC.length() - 1)) % 2 == 0) ? "F" : "M";
                        if (studentUI.confirmation("Confirm change IC from " + studentToUpdate.getStudentIC() + " to " + newIC + " (Y/N) : ")) {
                            studentToUpdate.setStudentIC(newIC);
                            studentToUpdate.setStudentGender(newGender);
                            studentUI.displaySuccessfullMessage("amended");
                            dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 3:
                        String newPhone = studentUI.getNewPhone(studentList, studentToUpdate.getStudentPhoneNo());
                        if (studentUI.confirmation("Confirm change phone.no from " + studentToUpdate.getStudentPhoneNo() + " to " + newPhone + " (Y/N) : ")) {
                            studentToUpdate.setStudentPhoneNo(newPhone);
                            studentUI.displaySuccessfullMessage("amended");
                            dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 4:
//                        tutorialGroupManagement.transferStudentProgramme(programmeList, currTutorialGroup, studentToUpdate, studentToUpdate.getStudentProgramme());
//                        System.out.println("Student Programme Updated");
                        Programme newProgramme = studentUI.getNewStudProgramme(savedProgramme, studentToUpdate.getStudentProgramme());
                        if (studentUI.confirmation("Confirm change programme from " + studentToUpdate.getStudentProgramme().getProgrammeCode() + " to " + newProgramme.getProgrammeCode() + " (Y/N) : ")) {
                            removeStudentFromTutGrp(studentToUpdate);
                            studentToUpdate.setStudentProgramme(newProgramme);
                            studentToUpdate.getStudentCourseList().reset();
                            enrollStudentForCourse(studentToUpdate);
                            studentUI.displaySuccessfullMessage("amended");
                            dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 5:
                        String newEmail = studentUI.getNewEmail(studentList, studentToUpdate.getStudentEmail());
                        if (studentUI.confirmation("Confirm change email from " + studentToUpdate.getStudentEmail() + " to " + newEmail + " (Y/N) : ")) {
                            studentToUpdate.setStudentEmail(newEmail);
                            studentUI.displaySuccessfullMessage("amended");
                            dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                            Utility.pressEnterToContinue();
                            Utility.clearScreen();
                        }
                        break;
                    case 6:
                        String newNamea = studentUI.getNewName(studentToUpdate.getStudentName());
                        String newICa = studentUI.getNewIC(studentList, studentToUpdate.getStudentIC());
                        String newGendera = (Integer.parseInt(newICa.substring(newICa.length() - 1)) % 2 == 0) ? "F" : "M";
                        String newPhonea = studentUI.getNewPhone(studentList, studentToUpdate.getStudentPhoneNo());
                        Programme newProgrammea = studentUI.getNewStudProgramme(savedProgramme, studentToUpdate.getStudentProgramme());
                        String newEmaila = studentUI.getNewEmail(studentList, studentToUpdate.getStudentEmail());

                        Student newStudentDetails = new Student(studentToUpdate.getStudentID(), newNamea, newICa, newGendera, newPhonea, newProgrammea, newEmaila);
                        String newDetails = "\nNew Details:\n"
                                + "studentName : " + newNamea + "\n"
                                + "studentIC : " + newICa + "\n"
                                + "studentGender : " + newGendera + "\n"
                                + "studentPhoneNo : " + newPhonea + "\n"
                                + "studentProgramme : " + newProgrammea.getProgrammeName() + "\n"
                                + "studentEmail : " + newEmaila;

                        studentUI.displayAllDetails(newDetails);
                        if (studentUI.confirmation("Confirm to update all details of the student? (Y/N) : ")) {
                            boolean updated = studentList.update(studentToUpdate, newStudentDetails);
                            if (updated) {
                                removeStudentFromTutGrp(studentToUpdate);
                                studentToUpdate.getStudentCourseList().reset();
                                enrollStudentForCourse(studentToUpdate);
                                studentUI.displaySuccessfullMessage("amended");
                                dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                            } else {
                                studentUI.displayFailMessage("amend student details.");

                            }
                        }
                        Utility.pressEnterToContinue();
                        Utility.clearScreen();
                        break;

                    case 0:
                        return;
                    default:
                        studentUI.displayInvalidOption("0-6");
                }
            } while (choice != 0);
        } else {
            studentUI.displayExitMessage();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }

    }

    public void displayCourseOfStudent() {
        studentUI.displaySearchStudentCourseUI();
        studentUI.displayStudents(studentList);
        if (!studentList.isEmpty()) {
            int index = studentUI.inputIndex("Enter the index of the student to search (0 to back) : ", studentList.getSize());
            if (index == 0) {
                studentUI.displayExitMessage();
                Utility.clearScreen();
                return;
            }
            index -= 1;
            Student studentToSearch = studentList.getPosition(index);
            studentUI.displayCourseofStudent(studentToSearch);
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else {
            studentUI.displayExitMessage();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }

    }

    public ListInterface<Course> findCoursesByProgramme(Programme studentProgramme, ListInterface<Course> courseList) {
        ListInterface<Course> registeredCourses = new DoublyCircularLinkedList<>();

        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = courseList.getPosition(i);

            for (int j = 0; j < course.getProgrammesAssociated().getSize(); j++) {
                Programme programme = course.getProgrammesAssociated().getPosition(j);
                if (programme.getProgrammeCode().equals(studentProgramme.getProgrammeCode())) {
                    registeredCourses.add(course);
                    break;
                }
            }
        }

        return registeredCourses;
    }

    public void enrollStudentForCourse(Student student) {
        ListInterface<Course> courses = findCoursesByProgramme(student.getStudentProgramme(), savedCourse);
        for (int i = 0; i < courses.getSize(); i++) {
            Course course = courses.getPosition(i);
            int totalCreditHour = student.getTotalCreditHour();
            totalCreditHour += course.getCreditHours();
            student.getStudentCourseList().add(student.new CourseStatus(course, "Main"));
            student.setTotalCreditHour(totalCreditHour);
        }
    }

    public ListInterface<Course> findAvaibleCourseList(Student student) {
        ListInterface<Course> avaibleCourseList = new DoublyCircularLinkedList();
        ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();
        for (int i = 0; i < savedCourse.getSize(); i++) {
            Course course = savedCourse.getPosition(i);
            CourseStatus courseStatus = student.new CourseStatus(course, "");
            if (!studentCourseList.contains(courseStatus)) {
                avaibleCourseList.add(courseStatus.getCourse());
            }
        }
        return avaibleCourseList;
    }

    public void addStudentToCourse() {
        boolean continueAdding = true;
        studentUI.displayAddStudentCourseUI();
        studentUI.displayStudents(studentList);
        if (!studentList.isEmpty()) {
            int studentIndex = studentUI.inputIndex("Enter the index of the student to add (0 to back) : ", studentList.getSize());
            if (studentIndex == 0) {
                studentUI.displayExitMessage();
                Utility.clearScreen();
                return;
            }
            studentIndex -= 1;
            Student studentToAdd = studentList.getPosition(studentIndex);

            ListInterface<CourseStatus> courseListToAdd = new DoublyCircularLinkedList();
            ListInterface<Course> avaibleCourseList = findAvaibleCourseList(studentToAdd);
            String status = "";
            boolean hasCourse = false;
            do {

                studentUI.displayCourseList(avaibleCourseList);

                if (!avaibleCourseList.isEmpty()) {
                    int courseIndex = studentUI.inputIndex("Enter the index of the Course to add (0 to back) : ", avaibleCourseList.getSize());
                    if (courseIndex == 0) {
                        studentUI.displayExitMessage();
                        Utility.clearScreen();
                        return;
                    }
                    courseIndex -= 1;
                    Course courseToAdd = avaibleCourseList.getPosition(courseIndex);

                    boolean alreadyEnrolled = false;
                    boolean creditHourExceed = false;

                    ListInterface<CourseStatus> studentCourseList = studentToAdd.getStudentCourseList();
                    CourseStatus courseStatus = studentToAdd.new CourseStatus(courseToAdd, "");
                    if (studentCourseList.contains(courseStatus)) {
                        alreadyEnrolled = true;
                    }

                    if (alreadyEnrolled) {
                        studentUI.displayFailMessage(" add: The student is already enrolled in the selected course.");

                    } else {
                        status = studentUI.getStatus(studentToAdd);
                        if (!status.equals("")) {
                            int creditHour = studentToAdd.getTotalCreditHour();
                            CourseStatus courseStatusToAdd = studentToAdd.new CourseStatus(courseToAdd, status);
                            if (!status.equals("Resit")) {
                                if (creditHour + courseToAdd.getCreditHours() > studentToAdd.getMaxCreditHour()) {
                                    creditHourExceed = true;
                                } else {
                                    creditHour += courseToAdd.getCreditHours();
                                }
                            }
                            if (creditHourExceed) {
                                studentUI.displayFailMessage(" add: The student credit hour is " + studentToAdd.getTotalCreditHour() + ". Maximum limit of credit hour is 20.");
                            } else {
                                studentUI.displayStudentCourseStatus(studentToAdd, courseToAdd, status);
                                if (studentUI.confirmation("\nConfirm addition of student to course with selected status? (Y/N): ")) {
                                    boolean added;
                                    if (status.equals("Resit")) {
                                        added = studentCourseList.addFront(courseStatusToAdd);
                                    } else {
                                        added = studentCourseList.addBack(courseStatusToAdd);
                                    }
                                    if (added) {
                                        studentToAdd.setTotalCreditHour(creditHour);
                                        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                                        studentUI.displaySuccessfullMessage("add student to course");
                                        courseListToAdd.add(courseStatusToAdd);
                                        hasCourse = true;
                                    } else {
                                        studentUI.displayFailMessage("add student to course");
                                    }

                                } else {
                                    studentUI.displayCancelMessage();
                                }
                            }
                        }
                    }
                    if (!studentUI.askContinue("Do you want to add this student to another course? (Y/N) : ")) {
                        continueAdding = false;
                    }
                } else {
                    studentUI.displayExitMessage();
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                    return;
                }
            } while (continueAdding);
            if (hasCourse) {
                studentUI.displayNewCourseBill(studentToAdd, courseListToAdd);
            }
            if (studentUI.askContinue("Do you want to add another student to any course? (Y/N) : ")) {
                addStudentToCourse(); // Recursive call to add another student
            }
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        } else {
            studentUI.displayExitMessage();
            Utility.pressEnterToContinue();
            Utility.clearScreen();
        }

    }

    public ListInterface<Student> GetStudentForCourse(Course course) {
        ListInterface<Student> studentInThisCourse = new DoublyCircularLinkedList<>();

        for (int i = 0; i < studentList.getSize(); i++) {
            Student student = studentList.getPosition(i);
            ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();

            for (int j = 0; j < studentCourseList.getSize(); j++) {
                String code = studentCourseList.getPosition(j).getCourse().getCourseCode();
                if (code.equals(course.getCourseCode())) {
                    studentInThisCourse.add(student);
                }
            }
        }
        return studentInThisCourse;
    }

    public void removeStudentFromCourse() {
        boolean continueRemoving = true;
        do {
            studentUI.displayRemoveStudentCourseUI();
            studentUI.displayCourseList(savedCourse);
            if (!savedCourse.isEmpty()) {
                int courseIndex = studentUI.inputIndex("Enter the index of the Course to remove the student from (0 to back) : ", savedCourse.getSize());
                if (courseIndex == 0) {
                    studentUI.displayExitMessage();
                    Utility.clearScreen();
                    return;
                }
                courseIndex -= 1;
                Course courseToRemoveFrom = savedCourse.getPosition(courseIndex);

                ListInterface<Student> studentInThisCourse = GetStudentForCourse(courseToRemoveFrom);
                if (studentInThisCourse.isEmpty()) {
                    continue;
                }
                studentUI.displayStudentsOfCourse(courseToRemoveFrom, studentInThisCourse);
                int studentIndex = studentUI.inputIndex("Enter the index of the student to remove (0 to back) : ", studentInThisCourse.getSize());
                if (studentIndex == 0) {
                    studentUI.displayExitMessage();
                    Utility.clearScreen();
                    return;
                }
                studentIndex -= 1;

                if (studentUI.confirmation("Are you sure you want to remove this student from the course? (Y/N): ")) {
                    Student student = studentInThisCourse.getPosition(studentIndex);
                    CourseStatus courseStatus = student.new CourseStatus(courseToRemoveFrom, "");
                    ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();
                    boolean removed = studentCourseList.remove(courseStatus);
                    if (removed) {
                        dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
                        studentUI.displaySuccessfullMessage("Student removed from the course");
                    } else {
                        studentUI.displayFailMessage("remove student from the course");
                    }
                    int totalCreditHour = student.getTotalCreditHour();
                    totalCreditHour -= courseToRemoveFrom.getCreditHours();
                    student.setTotalCreditHour(totalCreditHour);

                } else {
                    studentUI.displayCancelMessage();
                }

                if (!studentUI.askContinue("Do you want to remove another student from this course? (Y/N) : ")) {
                    continueRemoving = false;
                }
            } else {
                studentUI.displayExitMessage();
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                return;
            }
        } while (continueRemoving);
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void studentCreditTransfer() {
        studentUI.displayCreditTransferUI();
        studentUI.displayStudents(studentList);
        int index = studentUI.inputIndex("Enter the index of the student to search (0 to back) : ", studentList.getSize());
        if (index == 0) {
            studentUI.displayExitMessage();
            Utility.clearScreen();
            return;
        }
        index -= 1;
        Student student = studentList.getPosition(index);

        studentUI.displayCourseofStudent(student);

        ListInterface<CourseStatus> studentCourse = student.getStudentCourseList();
        int courseIndex;
        CourseStatus courseToTransfer;
        Course course;

        while (true) {
            courseIndex = studentUI.inputIndex("Enter the index of course to transfer (0 to back) : ", studentCourse.getSize());
            if (courseIndex == 0) {
                studentUI.displayExitMessage();
                Utility.clearScreen();
                return;
            }
            courseToTransfer = studentCourse.getPosition(courseIndex - 1);
            course = courseToTransfer.getCourse();

            if (!courseToTransfer.getStatus().equals("Main")) {
                studentUI.displayNotMainMessage();
            } else {
                break;
            }
        }

        CreditTransfer transfer = studentUI.getTransferDetails(course, student);
        boolean transferAdded = creditTransferList.add(transfer);
        if (transferAdded) {
            studentUI.displaySuccessfullMessage("credit transfer applied");
            dao.saveToFile((DoublyCircularLinkedList) creditTransferList, "CreditTransfer");
            courseToTransfer.setStatus("Pending");
            dao.saveToFile((DoublyCircularLinkedList) studentList, "Student");
        } else {
            studentUI.displayFailMessage("apply credit transfer for this course.");
        }
        Utility.pressEnterToContinue();
        Utility.clearScreen();

    }

    public void listStudentForCourse() {
        studentUI.displaystudentCourseUI();
        studentUI.displayCourseList(savedCourse);
        int courseIndex = studentUI.inputIndex("Enter the index of the Course to check Student List (0 to back) : ", savedCourse.getSize());
        if (courseIndex == 0) {
            studentUI.displayExitMessage();
            Utility.clearScreen();
            return;
        }
        courseIndex -= 1;
        Course selectedCourse = savedCourse.getPosition(courseIndex);
        ListInterface<Student> studentInThisCourse = GetStudentForCourse(selectedCourse);
        int choice = studentUI.filterMenu();
        switch (choice) {
            case 0:
                return;
            case 1:
                filterbyProgramme(selectedCourse, studentInThisCourse);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            case 2:
                studentUI.filterByGender(selectedCourse, studentInThisCourse);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            case 3:
                studentUI.displayStudentsOfCourse(selectedCourse, studentInThisCourse);
                Utility.pressEnterToContinue();
                Utility.clearScreen();
                break;
            default:
                studentUI.displayInvalidOption("0-3");
        }
    }

    public void filterbyProgramme(Course course, ListInterface<Student> studentInThisCourse) {
        studentUI.displayProgrammeList(savedProgramme);
        int programmeIndex = studentUI.inputIndex("Enter the index of Programme to search (0 to back) : ", savedProgramme.getSize());
        if (programmeIndex == 0) {
            studentUI.displayExitMessage();
            Utility.clearScreen();
            return;
        }
        programmeIndex -= 1;
        Programme selectedProgramme = savedProgramme.getPosition(programmeIndex);
        studentUI.filterByProgramme(course, studentInThisCourse, selectedProgramme);
    }

    public void displaStudentFee() {
        studentUI.displayBillUI();
        studentUI.displayStudents(studentList);
        int index = studentUI.inputIndex("Enter the index of the student to search (0 to back) : ", studentList.getSize());
        if (index == 0) {
            studentUI.displayExitMessage();
            Utility.clearScreen();
            return;
        }
        index -= 1;
        Student studentToCalcFee = studentList.getPosition(index);
        studentUI.displayBill(studentToCalcFee);
        Utility.pressEnterToContinue();
        Utility.clearScreen();
    }

    public void report() {
        int choice;
        do {
            choice = studentUI.report();
            switch (choice) {
                case 1 -> {
                    studentUI.displayCourseStudents(savedCourse, studentList);
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
                case 2 -> {
                    displayProgrammeStudents();
                    Utility.pressEnterToContinue();
                    Utility.clearScreen();
                }
                case 0 -> {
                    Utility.clearScreen();
                    return;
                }
                default ->
                    studentUI.displayInvalidOption("0-2");
            }
        } while (choice != 0);
    }

    public void displayProgrammeStudents() {
        ListInterface<Programme> thisYearProgramme = new DoublyCircularLinkedList<>();
        for (int i = 0; i < savedProgramme.getSize(); i++) {
            Programme programme = savedProgramme.getPosition(i);
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR) % 100;
            if ((programme.getProgrammeIntake().substring(programme.getProgrammeIntake().length() - 2)).equals(String.valueOf(currentYear))) {
                thisYearProgramme.add(programme);
            }
        }
        studentUI.displayStudentRegisterReport(thisYearProgramme, studentList);

    }
    

    public static void main(String[] args) {
        MaintainStudent maintainStudent = new MaintainStudent();
        maintainStudent.studentRegMenu();
    }
}
