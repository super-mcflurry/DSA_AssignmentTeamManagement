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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author choong zhi yang
 */
public class maintainStudentUI {

    Scanner scanner = new Scanner(System.in);

    public int stdRegMenu() {
        int choice = 0;

        while (true) {
            try {
                System.out.println("\nStudent Registeration");
                System.out.println("---------------------------------------------");
                System.out.println(" 1. Add Student");
                System.out.println(" 2. Remove Student");
                System.out.println(" 3. Amend Student Details");
                System.out.println(" 4. Search Students for Registered Course");
                System.out.println(" 5. Add Students to a Course");
                System.out.println(" 6. Remove Students from a Course");
                System.out.println(" 7. Student Credit Transfer");
                System.out.println(" 8. Display student Bill for all Registered Course");
                System.out.println(" 9. List Students for Course");
                System.out.println("10. Generate report");
                System.out.println("11. All Students List");
                System.out.println("\n---------------------------------------------\n");
                System.out.print("Select your choice ( 0 to exit ):  ");
                choice = scanner.nextInt();

                if (choice == 5 || choice == 6 || choice == 7) {
                    Calendar currentDate = Calendar.getInstance();
                    Calendar startDate = Calendar.getInstance();

//                    startDate.set(2024, Calendar.MARCH, 31, 0, 0, 0);
                    startDate.set(2024, Calendar.APRIL, 1, 0, 0, 0);
                    Calendar endDate = (Calendar) startDate.clone();
                    endDate.add(Calendar.WEEK_OF_YEAR, 4);

                    if (currentDate.after(startDate) && currentDate.before(endDate)) {
                        break;
                    } else {
                        System.out.println("Operation not available at the moment.");
                        System.out.println("You can only perform this operation between " + startDate.getTime() + " and " + endDate.getTime());
                    }
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return choice;
    }

    public Student getStudDetails(ListInterface<Programme> programmeList, ListInterface<Student> studentList) {

        System.out.println("\n------------------------------------------");
        System.out.println("          --- Add NEW Student ---         ");
        System.out.println("------------------------------------------");
        String stdName = getName();
        String stdIC = getIC(studentList);
        String stdGender = (Integer.parseInt(stdIC.substring(stdIC.length() - 1)) % 2 == 0) ? "F" : "M";
        String stdContact = getPhone(studentList);
        String stdEmail = getEmail(studentList);
        Programme studProgramme = getStudProgramme(programmeList);
        String stdID = getID(studProgramme, studentList);

        return new Student(stdID, stdName, stdIC, stdGender, stdContact, studProgramme, stdEmail);
    }

    public String getName() {
        System.out.print("Enter Student Name : ");
        String name = scanner.nextLine();
        return name;
    }

    public String getIC(ListInterface<Student> studentList) {
        String IC;
        boolean isValid;
        do {
            System.out.print("Enter Student IC: ");
            IC = scanner.nextLine();
            isValid = isValidIC(IC);
            if (!isValid) {
                System.out.println("Error: IC number should be 12 digits long and contain only numbers.");
            } else if (isDuplicateIC(IC, studentList)) {
                System.out.println("Error: IC number already exists for another student.");
                isValid = false;
            }
        } while (!isValid);
        return IC;
    }

    public boolean isValidIC(String IC) {
        return IC.matches("\\d{12}");
    }

    public boolean isDuplicateIC(String studentIC, ListInterface<Student> studentList) {
        for (int i = 0; i < studentList.getSize(); i++) {
            Student existingStudent = studentList.getPosition(i);
            if (existingStudent.getStudentIC().equals(studentIC)) {
                return true;
            }
        }
        return false;
    }

    public String getPhone(ListInterface<Student> studentList) {
        String phone;
        boolean isValid;
        do {
            System.out.print("Enter Student Contact : ");
            phone = scanner.nextLine();
            isValid = isValidPhone(phone);
            if (!isValid) {
                System.out.println("Invalid contact number format. Please enter a valid Malaysian contact number.");
            } else if (isDuplicatePhone(phone, studentList)) {
                System.out.println("Error: Phone number already exists for another student.");
                isValid = false; // Set isValid to false to repeat the loop
            }
        } while (!isValid);
        return phone;
    }

    public boolean isValidPhone(String phone) {
        return phone.matches("(0)?[0-9]{10,11}");
    }

    public boolean isDuplicatePhone(String phone, ListInterface<Student> studentList) {
        for (int i = 0; i < studentList.getSize(); i++) {
            Student existingStudent = studentList.getPosition(i);
            if (existingStudent.getStudentPhoneNo().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public String getEmail(ListInterface<Student> studentList) {
        String email;
        boolean isValid;
        do {
            System.out.print("Enter Student Email : ");
            email = scanner.nextLine();
            isValid = isValidEmail(email);
            if (!isValid) {
                System.out.println("Invalid email format. please enter a valid email format.");
            } else if (isDuplicateEmail(email, studentList)) {
                System.out.println("Error: Email already exists for another student.");
                isValid = false; // Set isValid to false to repeat the loop
            }
        } while (!isValid);
        return email;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^(.+)@(.+)$";
        return email.matches(emailRegex);
    }

    public boolean isDuplicateEmail(String email, ListInterface<Student> studentList) {
        for (int i = 0; i < studentList.getSize(); i++) {
            Student existingStudent = studentList.getPosition(i);
            if (existingStudent.getStudentEmail().equalsIgnoreCase(email)) {

                return true;
            }
        }
        return false;
    }

    public String getID(Programme studProgramme, ListInterface<Student> studentList) {
        String studIntake = studProgramme.getProgrammeIntake();
        String facultyCode = "";
        switch (studProgramme.getFaculty()) {
            case "FOCS" ->
                facultyCode = "WMR";
            case "FAFB" ->
                facultyCode = "WBR";
            default ->
                facultyCode = "TAR";
        }

        Student student = new Student();
        int studentCount = student.getNumOfStudents();
        String ID = studIntake.substring(studIntake.length() - 2) + facultyCode + String.format("%05d", studentCount + 1);

        return ID;
    }

    public DoublyCircularLinkedList<Programme> getEligibleProgrammes(ListInterface<Programme> savedProgramme) {
        DoublyCircularLinkedList<Programme> eligibleProgrammes = new DoublyCircularLinkedList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        for (int i = 0; i < savedProgramme.getSize(); i++) {
            Programme programme = savedProgramme.getPosition(i);
            String[] intakeParts = programme.getProgrammeIntake().split(" ");
            int intakeYear = Integer.parseInt(intakeParts[1]);
            int intakeMonth = parseMonth(intakeParts[0]);
            if (intakeYear > currentYear || (intakeYear == currentYear && intakeMonth >= currentMonth)) {
                eligibleProgrammes.add(programme);
            }
        }
        return eligibleProgrammes;
    }

    private int parseMonth(String monthName) {
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        monthName = monthName.toUpperCase();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(monthName)) {
                return i + 1;
            }
        }
        return -1;
    }

    public Programme getStudProgramme(ListInterface<Programme> savedProgramme) {
        Programme studProgramme = null;

        ListInterface<Programme> eligibleProgrammes = getEligibleProgrammes(savedProgramme);

        while (studProgramme == null) {
            try {
                if (!eligibleProgrammes.isEmpty()) {
                    displayProgrammeList(eligibleProgrammes);
                    System.out.print("Enter your choice : ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= eligibleProgrammes.getSize()) {
                        studProgramme = eligibleProgrammes.getPosition(choice - 1);
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and " + eligibleProgrammes.getSize());
                    }
                } else {
                    System.out.println("No Programme available");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return studProgramme;
    }

    public boolean confirmation(String message) {

        String choice;
        do {
            scanner.nextLine();
            System.out.print(message);
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (choice.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        } while (true);
    }

    public boolean askContinue(String message) {

        String choice;
        do {
            System.out.print(message);
            choice = scanner.nextLine();
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
        boolean isValid = false;

        do {
            try {
                System.out.println(" ");
                System.out.print(message);
                index = scanner.nextInt();
                if (index >= 0 && index <= listSize) {
                    isValid = true;
                } else {
                    System.out.println("Error: Please enter a valid index within the range of 1 to " + listSize);
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                isValid = false;
                scanner.nextLine();
            }
        } while (!isValid);

        return index;
    }
    
    public void displayAmendUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("      --- Amend Student Details ---       ");
        System.out.println("------------------------------------------");
    }
    
    public void displaySearchStudentCourseUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("     --- Search Student's Course ---      ");
        System.out.println("------------------------------------------");
    }
    
    public void displayAddStudentCourseUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("       --- Add Student To Course ---      ");
        System.out.println("------------------------------------------");
    }
    
    public void displayRemoveStudentCourseUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("       --- Add Student To Course ---      ");
        System.out.println("------------------------------------------");
    }
    
    public void displayCreditTransferUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("       --- Student Credit Transfer ---      ");
        System.out.println("------------------------------------------");
    }
    
    public void displayBillUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("       --- Display Student Bill ---      ");
        System.out.println("------------------------------------------");
    }
    
    public void displaystudentCourseUI(){
        System.out.println("\n------------------------------------------");
        System.out.println("    --- Filter Student From Course ---    ");
        System.out.println("------------------------------------------");
    }

    
    
    public int amendMenu() {
        int choice = 0;
        while (true) {
            try {
                System.out.println("Which detail do you want to amend?");
                System.out.println("1. Name");
                System.out.println("2. IC");
                System.out.println("3. Phone Number");
                System.out.println("4. Programme");
                System.out.println("5. Email");
                System.out.println("6. ALL");
                System.out.print("Enter your choice (0 to back) : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.next();
            }
        }
    }

    public String getNewName(String oldName) {
        String newName;
        boolean isValid = false;
        do {
            System.out.print("Enter New Name: ");
            newName = scanner.nextLine();
            if (newName.equals(oldName)) {
                System.out.println("New Name cannot same with old Name.");
                isValid = false;
            } else {
                isValid = true;
            }
        } while (!isValid);
        return newName;
    }

    public String getNewIC(ListInterface<Student> studentList, String oldIC) {
        String newIC;
        boolean isValid;
        do {
            System.out.print("Enter New IC: ");
            newIC = scanner.nextLine();
            isValid = isValidIC(newIC);
            if (!isValid) {
                System.out.println("Error: IC number should be 12 digits long and contain only numbers.");
            } else if (newIC.equals(oldIC)) {
                System.out.println("New IC cannot same with old IC.");
                isValid = false;
            } else if (isDuplicateIC(newIC, studentList)) {
                System.out.println("Error: IC number already exists for another student.");
                isValid = false;
            }
        } while (!isValid);
        return newIC;
    }

    public String getNewPhone(ListInterface<Student> studentList, String oldPhone) {
        String newPhone;
        boolean isValid;
        do {
            System.out.print("Enter New Phone.No : ");
            newPhone = scanner.nextLine();
            isValid = isValidPhone(newPhone);
            if (!isValid) {
                System.out.println("Invalid contact number format. Please enter a valid Malaysian contact number.");
            } else if (newPhone.equals(oldPhone)) {
                System.out.println("New Phone cannot same with old Phone.");
                isValid = false;
            } else if (isDuplicatePhone(newPhone, studentList)) {
                System.out.println("Error: Phone number already exists for another student.");
                isValid = false;
            }
        } while (!isValid);
        return newPhone;
    }
    
        public DoublyCircularLinkedList<Programme> getEligibleProgrammesFacaulty(ListInterface<Programme> savedProgramme, Programme oldProgramme) {
        DoublyCircularLinkedList<Programme> eligibleProgrammes = new DoublyCircularLinkedList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        for (int i = 0; i < savedProgramme.getSize(); i++) {
            Programme programme = savedProgramme.getPosition(i);
            String[] intakeParts = programme.getProgrammeIntake().split(" ");
            int intakeYear = Integer.parseInt(intakeParts[1]);
            int intakeMonth = parseMonth(intakeParts[0]);
            if (intakeYear > currentYear || (intakeYear == currentYear && intakeMonth >= currentMonth) && programme.getFaculty().equals(oldProgramme.getFaculty())) {
                eligibleProgrammes.add(programme);
            }
        }
        return eligibleProgrammes;
    }

    public Programme getNewStudProgramme(ListInterface<Programme> savedProgramme, Programme oldProgramme) {
        Programme newStudProgramme = null;

        ListInterface<Programme> eligibleProgrammes = getEligibleProgrammesFacaulty(savedProgramme,oldProgramme);

        while (newStudProgramme == null) {
            try {
                if (eligibleProgrammes != null) {
                    displayProgrammeList(eligibleProgrammes);
                    System.out.print("Enter your choice for new programme: ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= eligibleProgrammes.getSize()) {
                        newStudProgramme = eligibleProgrammes.getPosition(choice - 1);
                        if (newStudProgramme.equals(oldProgramme)) {
                            System.out.println("New Programme cannot same with old Programme.");
                            newStudProgramme = null;
                        }
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and " + eligibleProgrammes.getSize());
                    }

                } else {
                    System.out.println("No Programme avasible");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return newStudProgramme;
    }

    public String getNewEmail(ListInterface<Student> studentList, String oldEmail) {
        String newEmail;
        boolean isValid;
        do {
            System.out.print("Enter New Student Email : ");
            newEmail = scanner.nextLine();
            isValid = isValidEmail(newEmail);
            if (!isValid) {
                System.out.println("Invalid email format. please enter a valid email format.");
            } else if (newEmail.equals(oldEmail)) {
                System.out.println("New Email cannot same with old Email.");
                isValid = false;
            } else if (isDuplicateEmail(newEmail, studentList)) {
                System.out.println("Error: Email already exists for another student.");
                isValid = false;
            }
        } while (!isValid);
        return newEmail;
    }

    public String getStatus(Student student) {
        System.out.println("Choose the status:");
        System.out.println("1. Main");
        System.out.println("2. Elective");
        System.out.println("3. Resit");
        System.out.println("4. Repeat");
        System.out.print("Enter your choice (0 to exit) : ");

        int choice;
        do {
            try {
                choice = scanner.nextInt();
                if (choice >= 0 && choice <= 4) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and 4.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume newline character
            }
        } while (true);

        if (choice == 0) {
            return "";
        }

        switch (choice) {
            case 1:
                return "Main";
            case 2:
                return "Elective";
            case 3:
                return checkResitRepeatEligibility(student, "Resit");
            case 4:
                return checkResitRepeatEligibility(student, "Repeat");
            default:
                return "";
        }
    }

    private String checkResitRepeatEligibility(Student student, String status) {

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR) % 100;

        if (student.getStudentID().startsWith(String.valueOf(currentYear))) {
            System.out.println("Error: A new student cannot be marked as " + status + ".");
            return getStatus(student);
        } else {
            return status;
        }
    }

    public CreditTransfer getTransferDetails(Course course, Student student) {
        scanner.nextLine();
        System.out.print("Enter previous Course Name : ");
        String preCourse = scanner.nextLine();
        System.out.print("Enter Previous Course Description : ");
        String desc = scanner.nextLine();
        System.out.print("Enter previous Course Credit Hour : ");
        int creditHour = scanner.nextInt();
        System.out.print("Enter previous Course Grade : ");
        char grade = scanner.next().charAt(0);
        return new CreditTransfer(preCourse, desc, creditHour, grade, course, student);

    }

    public int filterMenu() {
        int choice = 0;
        while (true) {
            try {
                System.out.println("\nChoose criteria for listing students:");
                System.out.println("---------------------------------------------");
                System.out.println("1. Programme");
                System.out.println("2. Gender");
                System.out.println("3. List All");
                System.out.println("\n---------------------------------------------\n");
                System.out.print("Select your choice ( 0 to exit ):  ");
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.next();
            }
        }
    }

    public int report() {
        int choice = 0;
        while (true) {
            try {
                System.out.println("\nSummary Report");
                System.out.println("---------------------------------------------");
                System.out.println("1.  Display Student Status for each Course");
                System.out.println("2.  Display total student registered for each programme");
                System.out.println("---------------------------------------------");
                System.out.print("Select your choice (0 to exit) : ");
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.next();
            }
        }

    }

    public void displayBill(Student student) {
        double totalFee = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("=====================================================================================================");
        System.out.println("\n                    TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY \n");
        System.out.println("                                     STUDENT BILL");
        System.out.println("                                  ------------------");
        System.out.println("\nStudent ID: " + student.getStudentID());
        System.out.println("Student Name: " + student.getStudentName() + "                                              " + formattedDateTime);
        System.out.println("-------------------------------------------------------------------------------------------------------\n");
        System.out.printf("%-10s %-30s %-15s %-15s %-10s\n", "Course ID", "Course Name", "Credit Hours", "Status", "Course Fee");
        System.out.printf("%-10s %-30s %-15s %-15s %-10s\n", "---------", "-----------", "------------", "------", "----------");
        ListInterface<CourseStatus> courseList = student.getStudentCourseList();
        for (int i = 0; i < courseList.getSize(); i++) {
            CourseStatus courseStatus = courseList.getPosition(i);
            Course course = courseStatus.getCourse();
            String status = courseStatus.getStatus();

            Double fee;

            if (!status.equals("Resit")) {
                fee = course.getCourseFee();
            } else {
                fee = course.getResitFee();
            }
            System.out.printf("%-10s %-30s %-16d %-15s RM%.2f\n", course.getCourseCode(), course.getCourseName(), course.getCreditHours(), status, fee);
            totalFee += fee;
        }
        System.out.println("\n-------------------------------------------------------------------------------------------------------");
        System.out.printf("%74s RM%.2f\n", "Total Fee:", totalFee);
        System.out.println("=======================================================================================================");
    }

    public void displayNewCourseBill(Student student, ListInterface<CourseStatus> courseList) {
        double totalFee = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("=====================================================================================================");
        System.out.println("\n                    TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY                           \n");
        System.out.println("                                  STUDENT COURSE BILL                                                        ");
        System.out.println("                               ------------------------");
        System.out.println("\nStudent ID: " + student.getStudentID());
        System.out.println("Student Name: " + student.getStudentName() + "                                              " + formattedDateTime);
        System.out.println("-------------------------------------------------------------------------------------------------------\n");
        System.out.printf("%-10s %-30s %-15s %-15s %-10s\n", "Course ID", "Course Name", "Credit Hours", "Status", "Course Fee");
        System.out.printf("%-10s %-30s %-15s %-15s %-10s\n", "---------", "-----------", "------------", "------", "----------");

        for (int i = 0; i < courseList.getSize(); i++) {
            CourseStatus courseStatus = courseList.getPosition(i);
            Double fee;
            String status = courseStatus.getStatus();
            Course course = courseStatus.getCourse();
            if (!status.equals("Resit")) {
                fee = course.getCourseFee();
            } else {
                fee = course.getResitFee();
            }
            System.out.printf("%-10s %-30s %-16d %-15s RM%.2f\n", course.getCourseCode(), course.getCourseName(), course.getCreditHours(), status, fee);
            totalFee += fee;
        }
        System.out.println("\n-------------------------------------------------------------------------------------------------------");
        System.out.printf("%75s RM%.2f\n", "Total Fee:", totalFee);
        System.out.println("=======================================================================================================");
    }

    public void displayInvalidOption(String option) {
        System.out.println("Please select a valid option (" + option + ").");
    }

    public void displayNewStudent(Student newStudent) {
        System.out.println("\nNew Register Student");
        System.out.println("-----------------------------------");
        System.out.println(newStudent.studentDetails());
    }

    public void displaySuccessfullMessage(String operation) {
        System.out.println(operation + "  Successfully");
    }

    public void displayFailMessage(String operation) {
        System.out.println("failed to " + operation);
    }

    public void displayCancelMessage() {
        System.out.println("Operation is canceled.");
    }

    public void displayStudentsAllDetails(ListInterface<Student> studentList) {
        if (studentList.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            System.out.println("\nStudent List");
            System.out.println("============================================================================================================================================================================");
            System.out.printf("%-8s %-15s %-30s %-20s %-7s %-20s %-25s %-10s %-5s %-10s\n", "Index", "ID", "Name", "IC", "Gender", "PhoneNo", "Email", "Programme", "TotalCreditHour","TotalCourse");
            System.out.println("============================================================================================================================================================================");
            for (int i = 0; i < studentList.getSize(); i++) {
                Student student = studentList.getPosition(i);
                System.out.println(String.format("%-8d %s %-10s\n", i + 1, student.toString(), student.getStudentCourseList().getSize()));
            }
        }
    }

    public void displayStudents(ListInterface<Student> studentList) {
        if (studentList.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            System.out.println("\n              Student List                 ");
            System.out.println("=============================================");
            System.out.printf("%-5s %-15s %-30s \n", "Index", "ID", "Name");
            System.out.println("=============================================");
            for (int i = 0; i < studentList.getSize(); i++) {
                Student student = studentList.getPosition(i);
                System.out.printf("%-5s %-15s %-30s \n", i + 1, student.getStudentID(), student.getStudentName());
            }
        }
    }

    public void displayCourseList(ListInterface<Course> savedCourse) {
        if (savedCourse.isEmpty()) {
            System.out.println("No avaible course");
        } else {
            System.out.println("\n                              Courses List");
            System.out.println("====================================================================================");
            System.out.println(String.format("%-5s %-10s %-30s %-10s", "index", "Course Code", "Course Name", "creditHours"));
            System.out.println("------------------------------------------------------------------------------------");

            for (int i = 0; i < savedCourse.getSize(); i++) {
                Course course = savedCourse.getPosition(i);
                System.out.println(String.format("%-5s %-10s %-35s %-10s", i + 1, course.getCourseCode(), course.getCourseName(), course.getCreditHours()));
            }
        }
    }

    public void displayNoCourseMessage() {
        System.out.println("No avaible course");
    }

    public void displayExitMessage() {
        System.out.println("Exiting......");
    }

    public void displayNotMainMessage() {
        System.out.println("Only course with status Main can apply credit transfer.");
    }

    public void removeStudentUI() {
        System.out.println("\n------------------------------------------");
        System.out.println("          --- Remove Student ---           ");
        System.out.println("------------------------------------------");

    }

    public void displayStudentToRemove(Student studentToRemove) {
        System.out.println("\nStudent to Remove:");
        System.out.println("----------------------");
        System.out.println(studentToRemove.studentDetails());
    }

    public void displayAllDetails(String all) {
        System.out.println(all);
    }

    public void displayCourseofStudent(Student student) {
        ListInterface<Student.CourseStatus> studentCourse = student.getStudentCourseList();

        if (studentCourse.isEmpty()) {
            System.out.println("No enrolled course for student " + student.getStudentID());
        } else {
            System.out.println("\nCourses for Student " + student.getStudentID() + " - " + student.getStudentName());
            System.out.println("===============================================================================");
            System.out.println(String.format("%-5s %-12s %-30s %-15s %-10s", "Index", "Course Code", "Course Name", "Credit Hours", "Status"));
            System.out.println("-------------------------------------------------------------------------------");

            for (int i = 0; i < studentCourse.getSize(); i++) {
                Course course = studentCourse.getPosition(i).getCourse();
                String status = studentCourse.getPosition(i).getStatus();
                System.out.println(String.format("%-5s %-12s %-35s %-12d %-10s", i + 1, course.getCourseCode(), course.getCourseName(), course.getCreditHours(), status));

            }
        }

    }

    public String getCourseStatus(Student student, Course course) {
        String studentStatus = "";

        ListInterface<Student.CourseStatus> studentCourseList = student.getStudentCourseList();
        for (int i = 0; i < studentCourseList.getSize(); i++) {
            String code = studentCourseList.getPosition(i).getCourse().getCourseCode();
            String status = studentCourseList.getPosition(i).getStatus();
            if (code.equals(course.getCourseCode())) {
                studentStatus = status;
            }
        }
        return studentStatus;
    }

    public void displayStudentsOfCourse(Course course, ListInterface<Student> studentInThisCourse) {
        ListInterface<Student> sortedStudentList = sortStudentByName(studentInThisCourse);
        if (studentInThisCourse.isEmpty()) {
            System.out.println("No student enrolled in this course.");
        } else {
            System.out.println("\nAll Students in course " + course.getCourseName());
            System.out.println("=============================================================");
            System.out.println(String.format("%-5s %-15s %-20s %-10s", "Index", "ID", "Name", "Status"));
            for (int i = 0; i < sortedStudentList.getSize(); i++) {
                Student student = sortedStudentList.getPosition(i);
                String status = getCourseStatus(sortedStudentList.getPosition(i), course);
                System.out.println(String.format("%-5s %-15s %-20s %-5s", i + 1, student.getStudentID(), student.getStudentName(), status));
            }
        }
    }

    public void filterByProgramme(Course course, ListInterface<Student> studentInThisCourse, Programme programme) {
        boolean hasStudent = false;
        System.out.println(" ");
        System.out.println(programme.getProgrammeCode() + " Students in Course " + course.getCourseName());
        System.out.println("=============================================================");
        System.out.println(String.format("%-5s %-15s %-20s %-10s", "Index", "ID", "Name", "Status"));
        System.out.println("-------------------------------------------------------------");
        int index = 1;
        ListInterface<Student> sortedStudentList = sortStudentByName(studentInThisCourse);
        for (int j = 0; j < sortedStudentList.getSize(); j++) {
            Student student = sortedStudentList.getPosition(j);
            String status = getCourseStatus(student, course);
            if (programme.getProgrammeCode().equals(student.getStudentProgramme().getProgrammeCode())) {
                hasStudent = true;
                System.out.println(String.format("%-5s %-15s %-20s %-5s", index, student.getStudentID(), student.getStudentName(), status));
                index++;
            }
        }
        if (!hasStudent) {
            System.out.println("No Student from " + programme.getProgrammeCode() + " enrolled in course " + course.getCourseName());
        }
    }

    // Method to filter students by gender
    public void filterByGender(Course course, ListInterface<Student> studentInThisCourse) {
        boolean hasStudent = false;
        String gender;
        String code;
        System.out.println("1. Male");
        System.out.println("2. Female");
        int genderIndex = inputIndex("Enter the index of gender to search (0 to exit) : ", 2);
        int index = 1;
        if (genderIndex != 0) {
            if (genderIndex == 1) {
                gender = "Male";
                code = "M";
            } else {
                gender = "Female";
                code = "F";
            }
            System.out.println(" ");
            System.out.println(gender + " Students in course " + course.getCourseName());
            System.out.println("=============================================================");
            System.out.println(String.format("%-5s %-15s %-20s %-10s", "Index", "ID", "Name", "Status"));
            System.out.println("-------------------------------------------------------------");
            ListInterface<Student> sortedStudentList = sortStudentByName(studentInThisCourse);
            for (int i = 0; i < sortedStudentList.getSize(); i++) {
                Student student = sortedStudentList.getPosition(i);
                String status = getCourseStatus(sortedStudentList.getPosition(i), course);
                if (student.getStudentGender().equals(code)) {
                    hasStudent = true;
                    System.out.println(String.format("%-5s %-15s %-20s %-5s", index, student.getStudentID(), student.getStudentName(), status));
                    index++;
                }
            }
            if (!hasStudent) {
                System.out.println("No " + gender + " students enrolled in course " + course.getCourseName());
            }

        }

    }

    public void displayStudentCourseStatus(Student studentToAdd, Course courseToAdd, String status) {
        System.out.print("\nSelected Student: ");
        System.out.print(studentToAdd.getStudentName());
        System.out.print("\nSelected Course: ");
        System.out.print(courseToAdd.getCourseName());
        System.out.print("\nSelected Status: ");
        System.out.print(status);
    }

    public void displayProgrammeList(ListInterface<Programme> programmeList) {
        if (programmeList.isEmpty()) {
            System.out.println("No Programme availble");
        } else {
            System.out.println("""
                                \nProgramme available
                               =====================""");
            for (int i = 0; i < programmeList.getSize(); i++) {
                System.out.println(i + 1 + ". " + programmeList.getPosition(i).getProgrammeCode() + " - " + programmeList.getPosition(i).getProgrammeName());
            }
        }
    }

    public int getNoOfStudent(Programme programme, ListInterface<Student> studentList) {
        int total = 0;
        if (!studentList.isEmpty()) {
            for (int i = 0; i < studentList.getSize(); i++) {
                Student student = studentList.getPosition(i);
                if (student.getStudentProgramme().getProgrammeCode().equals(programme.getProgrammeCode())) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public void displayStudentRegisterReport(ListInterface<Programme> programmeList, ListInterface<Student> studentList) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        if (programmeList.isEmpty()) {
            System.out.println("No Programme availble");
        } else {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            System.out.println("=========================================================================================================================================");
            System.out.println("\n                                   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY\n                                                  STUDENT MANAGEMENT SUBSYSTEM\n\n");
            System.out.println("                                                    STUDENT REGISTRATION REPORT\n                                               ---------------------------------\n");
            System.out.println("\nGenerated at: " + formattedDateTime + "\n");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("%-10s %-80s %-15s %-15s %-15s\n", "Code", "Name", "Intake", "Facaulty", "No of Student");
            System.out.printf("%-10s %-80s %-15s %-15s %-15s\n", "----", "----", "-------------", "-----------", "--------------");
            int totalStudents = 0;
            Programme highestProgramme = programmeList.getPosition(0);
            int highestNumber = 0;
            Programme lowestProgramme = programmeList.getPosition(0);
            int lowestNumber = 10;
            for (int i = 0; i < programmeList.getSize(); i++) {
                Programme programme = programmeList.getPosition(i);
                int noOfStudent = getNoOfStudent(programme, studentList);
                System.out.printf("%-10s %-80s %-15s %-20s %-15s\n", programme.getProgrammeCode(), programme.getProgrammeName(), programme.getProgrammeIntake(), programme.getFaculty(), noOfStudent);
                totalStudents += noOfStudent;
                if (noOfStudent > highestNumber) {
                    highestProgramme = programme;
                    highestNumber = noOfStudent;
                }
                if (noOfStudent < lowestNumber && noOfStudent != 0) {
                    lowestProgramme = programme;
                    lowestNumber = noOfStudent;
                }
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-90s %-15s %-20s %-15s\n", "Total Number Of Student Registered in Year " + currentYear + " : ", "", "", totalStudents);
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

            System.out.println("\nProgramme with HIGHEST registered students : ");
            System.out.println(highestProgramme.getProgrammeCode() + " - " + highestProgramme.getProgrammeName() + " - [" + highestNumber + " Students]");
            System.out.println("\nProgramme with LOWEST registered students : ");
            System.out.println(lowestProgramme.getProgrammeCode() + " - " + lowestProgramme.getProgrammeName() + " - [" + lowestNumber + " Students]");
            System.out.println("\n[Note : Programme with 0 student registered is not counted .]");

            System.out.println("\n\n                                                     END OF STUDENT REGISTRATION REPORT                                                ");
            System.out.println("=========================================================================================================================================");
        }
    }

    public void displayCourseStudents(ListInterface<Course> courseList, ListInterface<Student> studentList) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        if (courseList.isEmpty()) {
            System.out.println("No Course availble");
        } else {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            System.out.println("=========================================================================================================================================");
            System.out.println("\n                                   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY\n                                                  STUDENT MANAGEMENT SUBSYSTEM\n\n");
            System.out.println("                                                    STUDENT COURSE REPORT\n                                               ---------------------------------\n");
            System.out.println("\nGenerated at: " + formattedDateTime + "\n");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("%-10s %-40s %-15s %-15s %-15s %-15s\n", "Code", "Name", "Credit Hours", "Main Student", "Repeat Student", "Resit Student");
            System.out.printf("%-10s %-40s %-15s %-15s %-15s %-15s\n", "----", "----", "------------", "------------", "------------", "------------");
            int highestRepeat = 0;
            Course highestRepeatCourse = courseList.getPosition(0);
            int highestResit = 0;
            Course highestResitCourse = courseList.getPosition(0);

            for (int i = 0; i < courseList.getSize(); i++) {
                Course course = courseList.getPosition(i);
                int main = 0;
                int repeat = 0;
                int resit = 0;

                for (int j = 0; j < studentList.getSize(); j++) {
                    Student student = studentList.getPosition(j);
                    ListInterface<CourseStatus> studentCourseList = student.getStudentCourseList();

                    for (int k = 0; k < studentCourseList.getSize(); k++) {
                        String code = studentCourseList.getPosition(k).getCourse().getCourseCode();
                        if (code.equals(course.getCourseCode())) {
                            switch (studentCourseList.getPosition(k).getStatus()) {
                                case "Main" ->
                                    main += 1;
                                case "Resit" ->
                                    resit += 1;
                                case "Repeat" ->
                                    repeat += 1;
                            }
                        }
                    }
                }

                System.out.printf("%-10s %-45s %-15s %-15s %-15s %-15s\n", course.getCourseCode(), course.getCourseName(), course.getCreditHours(), main, repeat, resit);
                if (resit != 0 && repeat != 0) {
                    if (repeat > highestRepeat) {
                        highestRepeat = repeat;
                        highestRepeatCourse = course;
                    }
                    if (resit > highestResit) {
                        highestResit = resit;
                        highestResitCourse = course;
                    }
                }

            }

            System.out.println("\nCourse with HIGHEST REPEAT students : ");
            if (highestRepeat == 0) {
                System.out.println("---");
            } else {
                System.out.println(highestRepeatCourse.getCourseCode() + " - " + highestRepeatCourse.getCourseName() + " - [" + highestRepeat + " Repeat Students]");
            }

            System.out.println("\nCourse with HIGHEST RESIT students : ");
            if (highestResit == 0) {
                System.out.println("---");
            } else {
                System.out.println(highestResitCourse.getCourseCode() + " - " + highestResitCourse.getCourseName() + " - [" + highestResit + " Resit Students]");
            }
            System.out.println("\n[Note : Course with 0 repeat/resit students is not counted .]");

            System.out.println("\n\n                                                     END OF STUDENT COURSE REPORT                                                ");
            System.out.println("=========================================================================================================================================");
        }
    }
    
        public ListInterface<Student> sortStudentByName(ListInterface<Student> studentList) {
        Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student c1,Student c2) {
                return c1.getStudentName().compareTo(c2.getStudentName());            }
        };
        ListInterface<Student> students = new DoublyCircularLinkedList<>();
        for (int i = 0; i < studentList.getSize(); i++) {
            students.add(studentList.getPosition(i));
        }
        students.bubbleSort(comparator);

        return students;
    }


}
