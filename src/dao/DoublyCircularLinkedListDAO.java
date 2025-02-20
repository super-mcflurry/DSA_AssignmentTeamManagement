/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.*;
import adt.*;

/**
 *
 * @author jsony
 */

public class DoublyCircularLinkedListDAO<T> {

    public void saveToFile(DoublyCircularLinkedList<T> list, String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public DoublyCircularLinkedList<T> loadFromFile(String filePath) {
        DoublyCircularLinkedList<T> list = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            list = (DoublyCircularLinkedList<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
        return list;
    }
    
    public void clearData(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Data cleared successfully from " + filePath);
            } else {
                System.out.println("Failed to clear data from " + filePath);
            }
        } else {
            System.out.println("File does not exist: " + filePath);
        }
    }
}

