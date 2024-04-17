package com.psa.hustlex.datastructures;

import java.util.Iterator;

public interface PriorityQueue<E extends Comparable<E>> {
    Iterator<E> iterator();

    void enqueue(E item);
    E dequeue();

    E peek();
    boolean isEmpty();

    E get(int index);
}

