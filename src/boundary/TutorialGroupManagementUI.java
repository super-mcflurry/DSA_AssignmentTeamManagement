package boundary;

import adt.ListInterface;
import entity.Programme;
import entity.Student;
import entity.TutorialGroup;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import utility.Validator;


public class TutorialGroupManagementUI implements Serializable {

    Scanner sc = new Scanner(System.in);

    public int tutorialGroupMenu() {
        System.out.println("\n=======================================================");
        System.out.println("**            Tutorial Group Management Menu         **");
        System.out.println("=======================================================");
        System.out.println("1. Optimise Students for Tutorial Group");
        System.out.println("2. Add Tutorial Group");
        System.out.println("3. Remove Tutorial Group");
        System.out.println("4. Display Tutorial Group List for programme");
        System.out.println("-------------------------------------------------------");
        System.out.println("5. Add Student to Tutorial Group");
        System.out.println("6. Remove Student from Tutorial Group");
        System.out.println("7. Change student Tutorial Group");
        System.out.println("8. Change student programme");
        System.out.println("9. Display Student List for Tutorial Group");
        System.out.println("10. Generate Report for Tutorial Group");
        System.out.println("0. Back");
        int choice = Validator.choiceInput("Enter your choice: ", 0, 10);
        return choice;
    }


    public String addTutorialGroup() {
        System.out.println("Add Tutorial Group");
        String intake = Validator.stringInput("Enter intake: ");
        return intake;
    }


    public void displayTutorialGroupList(ListInterface<TutorialGroup> tutorialGroupList) {
        System.out.println("\n===========================================");
        System.out.println("**           Tutorial Group List         **");
        System.out.println("===========================================");
        System.out.println("Tutorial Group ID\tIntake\tStudent Count");
        System.out.println("-----------------\t------\t-------------");
        int i = 1;
        for (TutorialGroup tg : tutorialGroupList) {
            System.out.println(i + ". " + tg.getTutorialGroupId() + "\t\t" + tg.getIntake() + "\t\t" + tg.getStudentsCount());
            i++;
        }
    }

    public void displayStudentList(ListInterface<Student> studentList) {
        System.out.println("\n===========================================");
        System.out.println("**           Student List          **");
        System.out.println("===========================================");
        System.out.println("Student ID\t\tStudent Name");
        System.out.println("----------\t\t------------");

        for (int i = 0; i < studentList.getSize(); i++) {
            Student s = studentList.getPosition(i);
            System.out.println(i + 1 + ". " + s.getStudentID() + "\t\t" + s.getStudentName());
        }
    }

    public ListInterface<Integer> askToPickStudentsFromList(int min, int max) {
        return Validator.multipleChoiceInput("Enter the student position(s) you want to pick (separated by comma e.g. : 1,3,5,6): ", min, max);
    }

    public boolean askToOptimiseWithAddOrNot() {
        char answ = Validator.yesNoInput("Do you have new students to be included for optimisation? (y/n)");
        if (answ == 'Y') {
            return true;
        } else {
            return false;
        }
    }

    public int askForOptimalNumberInTutorialGroup() {
        int num = Validator.intInput("Enter the optimal number of students in a tutorial group: ");
        return num;
    }

    public String getProgrammeCode() {
        String progCode = Validator.stringInput("Enter Programme Code: ");
        return progCode;
    }

    public int getTutorialGroupPosition() {
        int pos = Validator.intInput("Enter your choice: ");
        return pos;
    }

    public void displayProgrammeList(ListInterface<Programme> programmeList) {
        int i = 1;
        for (Programme p : programmeList) {
            System.out.println(i + ". " + p.toString());
            i++;
        }

    }

    public int displayReport() {
        System.out.println("\n=======================================================");
        System.out.println("**            Tutorial Group Report Menu         **");
        System.out.println("=======================================================");
        System.out.println("1. Display Tutorial Group Summary Report");
        System.out.println("2. Display Tutorial Group Gender Detail Report");
        System.out.println("0. Back");
        int choice = Validator.choiceInput("Enter your choice: ", 0, 2);
        return choice;
    }

    public void displayReport1(ListInterface<Programme> programmeList) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("\n===========================================================================================================================");
        System.out.println("                                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY                 ");
        System.out.println("                                       TUTORIAL GROUP MANAGEMENT SUBSYSTEM REPORT                                     ");
        System.out.println("\n");
        System.out.println("TUTORIAL GROUP SUMMARY REPORT ");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Report generated at: " + formattedDateTime + "\n");


        System.out.printf("%-3s %-80s %5s %-12s %-3s\n", "No", "Programme", "Intake", "Tutorial Group ID", "Student Count");
        System.out.printf("%-3s %-80s %5s %-12s %-3s\n", "--", "---------", "------", "-----------------", "-------------");


        int i = 1;
        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                System.out.printf("%-2d. %-80s %5s %15s %10s\n", i, p.getProgrammeName(), tg.getIntake(), tg.getTutorialGroupId(), tg.getStudentsCount());
                i++;
            }
            System.out.println("***");

        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------");

        TutorialGroup highestTG = programmeList.getPosition(0).getTutorialGroups().getPosition(0);
        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                if (tg.getStudentsCount() > highestTG.getStudentsCount()) {
                    highestTG = tg;
                }
            }
        }
        System.out.println("Tutorial Group with the highest student count: " + highestTG.getTutorialGroupId() + " with " + highestTG.getStudentsCount() + " students");


        TutorialGroup lowestTG = programmeList.getPosition(0).getTutorialGroups().getPosition(0);
        TutorialGroup secondLowestTG = highestTG;
        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                if (tg.getStudentsCount() < lowestTG.getStudentsCount()) {
                    secondLowestTG = lowestTG;
                    lowestTG = tg;
                } else if (tg.getStudentsCount() < secondLowestTG.getStudentsCount() && tg.getStudentsCount() != lowestTG.getStudentsCount()) {
                    secondLowestTG = tg;
                }
            }
        }
        if (lowestTG.getStudentsCount() == 0) {
            lowestTG = secondLowestTG;
        }
        System.out.println("Tutorial Group with the lowest student count: " + lowestTG.getTutorialGroupId() + " with " + lowestTG.getStudentsCount() + " students");

        System.out.println("\n                                                  END OF THE TUTORIAL GROUP SUMMARY REPORT");
        System.out.println("===========================================================================================================================");


    }

    public void displayReport2(ListInterface<Programme> programmeList) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("\n===========================================================================================================================");
        System.out.println("                                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY                 ");
        System.out.println("                                       TUTORIAL GROUP MANAGEMENT SUBSYSTEM REPORT                                     ");
        System.out.println("\n");
        System.out.println("TUTORIAL GROUP GENDER DETAIL REPORT ");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Report generated at: " + formattedDateTime + "\n");

        System.out.printf("%-3s %-12s %-3s %-3s %-10s %-12s\n", "No", "Tutorial Group ID", "Male Count", "Female Count", "Percentage" ,"Estimated Visualisation");
        System.out.printf("%-3s %-12s %-3s %-3s %-10s %-12s\n", "--", "-----------------", "----------", "------------", "----------" ,"-----------------------");


        for ( Programme p : programmeList) {
            int counter = 1;

            for (TutorialGroup tg : p.getTutorialGroups()) {
                int maleCount = 0;
                int femaleCount = 0;
                for (Student s : tg.getStudents()) {
                    if (s.getStudentGender().equals("M")) {
                        maleCount++;
                    } else {
                        femaleCount++;
                    }
                }
                System.out.printf("%-2d. %12s %10d %12d %15s %16s\n", counter, tg.getTutorialGroupId(), maleCount, femaleCount, getGenderPercentage(maleCount,femaleCount)  ,visualiseGender(maleCount, femaleCount));
                counter++;
            }
            System.out.println("***");
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------");


        TutorialGroup highestMaleTG = programmeList.getPosition(0).getTutorialGroups().getPosition(0);
        TutorialGroup highestFemaleTG = programmeList.getPosition(0).getTutorialGroups().getPosition(0);
        int highestMaleCount = 0;
        int highestFemaleCount = 0;

        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                int maleCount = 0;
                for (Student s : tg.getStudents()) {
                    if (s.getStudentGender().equals("M")) {
                        maleCount++;
                    }
                }
                if (maleCount > highestMaleCount) {
                    highestMaleCount = maleCount;
                    highestMaleTG = tg;
                }
            }
        }

        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                int femaleCount = 0;
                for (Student s : tg.getStudents()) {
                    if (s.getStudentGender().equals("F")) {
                        femaleCount++;
                    }
                }
                if (femaleCount > highestFemaleCount) {
                    highestFemaleCount = femaleCount;
                }
            }
        }

        System.out.println("Tutorial Group with the highest male student count: " + highestMaleTG.getTutorialGroupId() + " with " + highestMaleCount + " students");
        System.out.println("Tutorial Group with the highest female student count: " + highestFemaleTG.getTutorialGroupId() + " with " + highestFemaleCount + " students");


        TutorialGroup lowestMaleTG = programmeList.getPosition(0).getTutorialGroups().getPosition(0);
        TutorialGroup lowestFemaleTG = programmeList.getPosition(0).getTutorialGroups().getPosition(0);
        int lowestMaleCount = 100;
        int lowestFemaleCount = 100;

        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                int maleCount = 0;
                for (Student s : tg.getStudents()) {
                    if (s.getStudentGender().equals("M")) {
                        maleCount++;
                    }
                }
                if (maleCount != 0 && maleCount < lowestMaleCount) {
                    lowestMaleCount = maleCount;
                    lowestMaleTG = tg;
                }
            }
        }

        for (Programme p : programmeList) {
            for (TutorialGroup tg : p.getTutorialGroups()) {
                int femaleCount = 0;
                for (Student s : tg.getStudents()) {
                    if (s.getStudentGender().equals("F")) {
                        femaleCount++;
                    }
                }
                if (femaleCount != 0 && femaleCount < lowestFemaleCount) {
                    lowestFemaleCount = femaleCount;
                    lowestFemaleTG = tg;
                }
            }
        }

        System.out.println("\nTutorial Group with the lowest male student count: " + lowestMaleTG.getTutorialGroupId() + " with " + lowestMaleCount + " students");
        System.out.println("Tutorial Group with the lowest female student count: " + lowestFemaleTG.getTutorialGroupId() + " with " + lowestFemaleCount + " students");


        System.out.println("\n                                                  END OF THE TUTORIAL GROUP SUMMARY REPORT");
        System.out.println("===========================================================================================================================");


    }

    public String visualiseGender(int maleCount, int femaleCount) {
        int total = maleCount + femaleCount;

        int malePercentage = roundUpToNearestMultipleOfTen ((maleCount * 100) / (total == 0 ? 1 : total));
        int femalePercentage = roundUpToNearestMultipleOfTen ((femaleCount * 100) / (total == 0 ? 1 : total));


        //Display breakdown between male and female, with a maximum of 10 symbols, males are represented with the "M" symbol and females are represented with the "F" symbol
        int maleSymbols = (malePercentage * 10) / 100;
        int femaleSymbols = (femalePercentage * 10) / 100;
        String holder = "";
        int maxDisplay = 10;
        if (maleSymbols == 0 && femaleSymbols ==0)   {
            holder = "----------";
        } else{

            for (int i = 0; i < maleSymbols; i++) {
                holder = holder.concat("M");
                maxDisplay--;
            }
            for (int i = 0; i < maxDisplay; i++) {
                holder = holder.concat("F");
            }
        }
        return holder;
    }

    public String getGenderPercentage(int maleCount, int femaleCount) {
        String toReturn = "";
        int total = maleCount + femaleCount;
        int malePercentage = (maleCount * 100) / (total == 0 ? 1 : total);
        int femalePercentage = (femaleCount * 100) / (total == 0 ? 1 : total);

        toReturn = malePercentage + "% / " + femalePercentage + "%";
        return  toReturn;
    }

    public static int roundUpToNearestMultipleOfTen(int number) {
        // Calculate the quotient when dividing the number by 10
        return ((number+5)/10)*10;
    }
}
