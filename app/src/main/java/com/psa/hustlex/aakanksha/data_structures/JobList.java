package com.psa.hustlex.aakanksha.data_structures;

import java.util.Arrays;
import java.util.Iterator;

public class JobList<T> implements JobListInterface<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    // Constructor
    public JobList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // Methods
    public void add(T element) {
        if (size == elements.length) {
            doubleCapacity();
        }
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elements[index] = element;
    }


    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                removeAtIndex(i);
                return true;
            }
        }
        return false;
    }


    // Private methods - for internal workings

    // Remove from index, move trailing elements up by one index
    private void removeAtIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
    }

    // Increase capacity by 2x
    private void doubleCapacity() {
        int newCapacity = 2 * elements.length;
        elements = Arrays.copyOf(elements, newCapacity);
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Implementing Iterable interface for looping implementations

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
