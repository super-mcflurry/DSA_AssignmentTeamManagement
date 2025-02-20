/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.Comparator;

/**
 *
 * @author LAI GUAN HONG (Whole Group)
 * @param <T>
 */
public interface ListInterface<T> extends Iterable<T> {

    public boolean add(T element); // Add a new element to the list

    public boolean addFront(T element); // Add a new element at the front of list

    public boolean addBack(T element); // Add a new element at the back of the list

    public T getFront(); // Get the first element

    public T getBack(); // Get the last element

    public boolean remove(T element); // Remove the specific element

    public boolean removeFront(); // Remove the first element

    public boolean removeBack(); // Remove the last element

    public boolean removeAtPosition(int position); // Remove the element at a specific position

    public boolean update(T oldElement, T newElement); // Update an element's data

    public boolean updateAtPosition(int position, T element); // Update an element's data at a specific position

    public T find(T element); // Find the element in the list

    public T getPosition(int position); // Get the element at a specific position in the list

    public T get(T element); // Get the element from the list

    public int getSize(); // Return the size of the list

    public boolean contains(T element); // Check whether the element exists in the list

    public boolean isEmpty(); // Check whether the list is empty

    public void reset(); // Reset the list

    public void bubbleSort(Comparator<T> comparator); // Sort the list using bubble sort

    public String toString(); // Output the elements

}

