package com.psa.hustlex.datastructures;

import java.util.Iterator;

public interface BagInterface<E> {

    boolean isEmpty();

    int size();

    void add(E item);

    Iterator<E> iterator();
}
