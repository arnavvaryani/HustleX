package com.psa.hustlex.aakanksha_jobtracker.data_structures;

public interface JobListInterface<T> extends Iterable<T> {
    //add element to list
    void add(T element);

    //get element at index
    T get(int index);

    //change index of an element
    void set(int index, T element);

    //remove element
    boolean remove(T element);

    //get size
    int size();

    //check if list is empty
    boolean isEmpty();
}
