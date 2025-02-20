package utility;

import java.util.Scanner;
import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import java.util.regex.*;

public class Validator {

    public static char yesNoInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        boolean charTest = false;
        System.out.printf("\n%s > ", prompt);
        char temp = sc.next().charAt(0);
        do {
            switch (temp) {
                case 'Y', 'y', 'N', 'n' -> charTest = true;
                default -> {
                    System.out.print("Invalid input , please try again.");
                    System.out.printf("\n%s > ", prompt);
                    temp = sc.next().charAt(0);
                }
            }
        } while (!charTest);
        return Character.toUpperCase(temp);
    }
    //Doesnt have a question prompt, always returns a capital
    public static char yesNoInput() {
        Scanner sc = new Scanner(System.in);
        boolean charTest = false;
        char temp = sc.next().charAt(0);
        do {
            switch (temp) {
                case 'Y', 'y', 'N', 'n' -> charTest = true;
                default -> {
                    System.out.print("Invalid input , please try again : ");
                    temp = sc.next().charAt(0);
                }
            }
        } while (!charTest);
        return Character.toUpperCase(temp);
    }

    //Used to prompt for a int input with validation
    public static int intInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("[\r\n]");
        System.out.printf("\n%s > ", prompt);
        while (!sc.hasNextInt())
        {
            System.out.print("Input data type error. Try again.");
            System.out.printf("\n%s > ", prompt);
            sc.next();
        }
        return sc.nextInt();
    }
    //Used to prompt for a String Input
    public static String stringInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        String temp;
        char answ;
        do {
            System.out.printf("\n%s > ", prompt);
            temp = sc.nextLine();
            answ = yesNoInput("You have inputted '" + temp + "', is this the correct input? [Y|N]");
        } while (answ=='N');
        return temp;
    }

    //Used to request for an int input between a range
    public static int choiceInput(String prompt, int min, int max) {
        int number = 0;
        do
        {
            number = intInput(prompt);
            if (number < min || number > max)
                System.out.print("Input range exceeded. Try again.");
        } while (number < min || number > max);
        return number;
    }

    public static ListInterface<Integer> multipleChoiceInput(String prompt, int min, int max) {
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        String temp;
        char answ;
        ListInterface<Integer> choices = new DoublyCircularLinkedList<>();
        do {
            do {
                System.out.printf("\n%s > ", prompt);
                temp = sc.nextLine();
                answ = yesNoInput("You have inputted '" + temp + "', is this the correct input? [Y|N]");
            } while (answ == 'N');
            for (String s : temp.trim().split(",")) {
                if (Pattern.matches("[0-9]+", s)) {
                    int num = Integer.parseInt(s);
                    if (num >= min && num <= max) {
                        choices.add(num);
                        validInput = true;
                    } else {
                        System.out.println("One of the inputs exceeded available choices, please try again.");
                        validInput = false;
                        break;
                    }
                } else {
                    System.out.println("Invalid input, please try again.");
                    validInput = false;
                    break;
                }
            }
        } while(!validInput);
        return choices;
    }


}
