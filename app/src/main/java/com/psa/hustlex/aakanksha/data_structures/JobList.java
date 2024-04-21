package com.psa.hustlex.aakanksha.data_structures;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @param <T> the type of elements in this list
 */
public class JobList<T> implements JobListInterface<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    // Constructor
    public JobList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Adds an element to the end of the list. If the internal array is full, its capacity is doubled.
     *
     * @param element the element to be added to the list
     */
    public void add(T element) {
        if (size == elements.length) {
            doubleCapacity();
        }
        elements[size++] = element;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elements[index] = element;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     *
     * @param element element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                removeAtIndex(i);
                return true;
            }
        }
        return false;
    }


    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index the index of the element to be removed
     */
    private void removeAtIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
    }

    /**
     * Doubles the capacity of the array used to store the elements of the list.
     */
    private void doubleCapacity() {
        int newCapacity = 2 * elements.length;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                return (T) elements[currentIndex++];
            }
        };
    }
}
