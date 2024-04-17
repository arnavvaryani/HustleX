package com.psa.hustlex.arnav.datastructures;

import java.util.Iterator;

public interface NodeBagInterface<E> {

    boolean isEmpty();

    int size();

    void add(E item);

    Iterator<E> iterator();
}
