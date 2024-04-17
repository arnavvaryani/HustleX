package com.psa.hustlex.arnav.datastructures;

import java.util.Iterator;

public interface HeapPriorityQueueInterface<E extends Comparable<E>> {
    Iterator<E> iterator();

    void enqueue(E item);
    E dequeue();

    E peek();
    boolean isEmpty();

    E get(int index);
}

