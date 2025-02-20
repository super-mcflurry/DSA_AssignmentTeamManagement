package adt;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

public class ArrayList<T> implements ListInterface<T>, Serializable, Iterable<T> {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Object[initialCapacity];
    }
    
    public void increaseCapacity() {
        T[] oldArray = array;
        int oldSize = oldArray.length;
        array = (T[]) new Object[oldSize * 2];
        for (int i = 0; i < oldSize; i++) {
            array[i] = oldArray[i];
        }
    }

    @Override
    public boolean add(T newEntry) {
        if (isFull()) {
            increaseCapacity();
        }
        array[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean addFront(T element) {
        if (isFull()) {
            increaseCapacity();
        }
        for (int i = numberOfEntries; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = element;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean addBack(T element) {
        if (isFull()) {
            increaseCapacity();
        }
        array[numberOfEntries] = element;
        numberOfEntries++;
        return true;
    }

    @Override
    public T getFront() {
        return null;
    }

    @Override
    public T getBack() {
        return null;
    }

    @Override
    public boolean remove(T element) {
        boolean found = false;
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(element)) {
                found = true;
                for (int j = i; j < numberOfEntries - 1; j++) {
                    array[j] = array[j + 1];
                }
                numberOfEntries--;
                break;
            }
        }
        return found;
    }

    @Override
    public boolean removeFront() {
        if (numberOfEntries == 0) {
            return false;
        }
        for (int i = 0; i < numberOfEntries - 1; i++) {
            array[i] = array[i + 1];
        }
        numberOfEntries--;
        return true;
    }

    @Override
    public boolean removeBack() {
        if (numberOfEntries == 0) {
            return false;
        }
        array[numberOfEntries - 1] = null;
        numberOfEntries--;
        return true;
    }

    @Override
    public boolean removeAtPosition(int position) {
        if (position < 0 || position >= numberOfEntries) {
            return false;
        }

        removeGap(position);

        numberOfEntries--;

        return true;

    }

    @Override
    public boolean update(T oldElement, T newElement) {
        boolean found = false;
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(oldElement)) {
                array[i] = newElement;
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public boolean updateAtPosition(int position, T element) {
        if (position < 0 || position >= numberOfEntries) {
            return false;
        }
        array[position] = element;
        return true;
    }

    @Override
    public T find(T element) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(element)) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public T getPosition(int position) {
        if (position < 0 || position >= numberOfEntries) {
            return null;
        }

        return array[position];
       
    }

    @Override
    public T get(T element) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(element)) {
                return array[i];
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return numberOfEntries;

    }


    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < numberOfEntries); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
            }
        }
        return found;
    }


    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public void reset() {

    }

    @Override
    public void bubbleSort(Comparator<T> comparator) {

    }

    public boolean isFull() {
        return numberOfEntries == array.length;
    }

    @Override
    public String toString() {
        String outputStr = "[ ";
        for (int index = 0; index < numberOfEntries; ++index) {
            if (index == numberOfEntries - 1) {
                outputStr += array[index];
            } else {
                outputStr += array[index] + ", ";
            }
        }
        outputStr += " ]";

        return outputStr;
    }



    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array

        int lastIndex = numberOfEntries - 1;

        for (int index = givenPosition; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    @Override
    public Iterator<T> iterator(){
        return new IteratorForArrayList();
    }

    private class IteratorForArrayList implements Iterator<T>{
        private int nextIndex;

        public IteratorForArrayList(){
            nextIndex=0;
        }

        @Override
        public boolean hasNext(){
            return nextIndex<numberOfEntries;
        }

        @Override
        public T next(){
            if(hasNext()){
                T nextElement=(T) array[nextIndex++];
                return nextElement;
            }else {
                return null;
            }
        }

    }
}