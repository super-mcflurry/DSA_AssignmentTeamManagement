package adt;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author LAI GUAN HONG (Whole Group)
 * @param <T>
 */
public class DoublyCircularLinkedList<T> implements ListInterface<T>, Serializable, Iterable<T> {

    private Node head;
    private Node tail;
    private int size;

    public DoublyCircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (contains(element)) {
            return false;
        }
        if (isEmpty()) {
            head = new Node(element);
            head.next = head;
            head.prev = head;
            tail = head;
        } else {
            Node newNode = new Node(element, head, tail);
            tail.next = newNode;
            tail = newNode;
            head.prev = tail;
        }
        size++;
        return true;
    }

    @Override
    public boolean addFront(T element) {
        if (contains(element)) {
            return false;
        }
        if (isEmpty()) {
            return add(element);
        } else {
            Node newNode = new Node(element, head, tail);
            tail.next = newNode;
            head.prev = newNode;
            head = newNode;
            size++;
            return true;
        }
    }

    @Override
    public boolean addBack(T element) {
        if (contains(element)) {
            return false;
        }
        if (isEmpty()) {
            return add(element);
        } else {
            Node newNode = new Node(element, head, tail);
            tail.next = newNode;
            tail = newNode;
            head.prev = tail;
            size++;
            return true;
        }
    }
    @Override
    public T getFront() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    @Override
    public T getBack() {
        if (isEmpty()) {
            return null;
        }
        return tail.data;
    }

    @Override
    public boolean remove(T element) {
        if (isEmpty()) {
            return false;
        }
        Node current = head;
        do {
            if (current.data.equals(element)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                if (current == head) {
                    head = current.next;
                }
                size--;
                return true;
            }
            current = current.next;
        } while (current != head);
        return false; 
    }

    @Override
    public boolean removeFront() {
        if (isEmpty()) {
            return false;
        }
        if (size == 1) {
            reset(); 
            return true;
        }
        tail.next = head.next;
        head.next.prev = tail;
        head = head.next; 
        size--;
        return true;
    }

    @Override
    public boolean removeBack() {
        if (isEmpty()) {
            return false;
        }
        if (head == tail) {
            reset();
            return true;
        }
        tail.prev.next = head;
        head.prev = tail.prev;
        tail = tail.prev;
        size--;
        return true;
    }

    @Override
    public boolean removeAtPosition(int position) {
        if (position < 0 || position >= size) {
            return false;
        }
        if (position == 0) {
            return removeFront();
        } else if (position == size - 1) {
            return removeBack();
        } else {
            Node current = head;
            for (int i = 0; i < position; i++) {
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return true;
        }
    }

    @Override
    public boolean update(T oldElement, T newElement) {
        Node current = head;
        do {
            if (current.data.equals(oldElement)) {
                current.data = newElement;
                return true;
            }
            current = current.next;
        } while (current != head);
        return false;
    }

    @Override
    public boolean updateAtPosition(int position, T element) {
        if (position < 0 || position >= size) {
            return false;
        }
        Node current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        current.data = element;
        return true;
    }

    @Override
    public T find(T element) {
        if (head == null) {
            return null;
        }
        Node current = head;
        do {
            if (current.data.equals(element)) {
                return current.data;
            }
            current = current.next;
        } while (current != head);
        return null;
    }

    @Override
    public T getPosition(int position) {
        if (position < 0 || position >= size) {
            return null;
        }
        Node current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public T get(T element) {
        return find(element);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean contains(T element) {
        return find(element) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void reset() {
        head = null;
        tail = null;
        size = 0;
    }
    @Override
    public void bubbleSort(Comparator<T> comparator) {
        if (isEmpty() || size == 1) {
            return;
        }

        boolean swapped;
        Node current, prev;

        do {
            swapped = false;
            current = head;
            prev = null;

            while (current.next != head) {
                if (comparator.compare(current.data, current.next.data) > 0) {
                    T temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }

                prev = current;
                current = current.next;
            }

            if (comparator.compare(current.data, head.data) < 0) {
                T temp = current.data;
                current.data = head.data;
                head.data = temp;
                swapped = true;
            }

        } while (swapped);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        do {
            sb.append(current.data);
            if (current.next != head) {
                sb.append(", ");
            }
            current = current.next;
        } while (current != head);
        sb.append("]");
        return sb.toString();
    }


    @Override
    public DoublyCircularLinkedListIterator<T> iterator() {
        return new DoublyCircularLinkedListIterator<T>(head);
    }

    private class DoublyCircularLinkedListIterator<T> implements Iterator<T> {
        private Node current ;
        private int index = 0;

        private DoublyCircularLinkedListIterator(Node head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = (T) current.data;
                current = current.next;
                index++;
                return data;
            }
            return null;
        }
    }

    private class Node implements Serializable {

        private T data;
        private Node next;
        private Node prev;

        private Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        private Node(T data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

}
