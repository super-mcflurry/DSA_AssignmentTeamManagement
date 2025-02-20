/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;



/**
 *
 * @author jsony
 */
public class Utility {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void clearScreen() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(250); // Add a small delay to allow the screen to clear
        } catch (AWTException | InterruptedException ex) {
            System.err.println("Failed to clear the screen: " + ex.getMessage());
        }
    }

   
   public static void pressEnterToContinue() {
        System.out.print("\nPress enter to continue...");
        scanner.nextLine();
    }
    
}
