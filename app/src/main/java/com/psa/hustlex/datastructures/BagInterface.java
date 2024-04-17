package com.psa.hustlex.datastructures;

import java.util.Iterator;

/**
 * Interface for a generic bag data structure.
 *
 * @param <E> the type of elements in this bag
 */
public interface BagInterface<E> {

    /**
     * Checks if the bag is empty.
     *
     * @return true if this bag is empty; false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this bag.
     *
     * @return the number of elements in this bag
     */
    int size();

    /**
     * Adds an item to the bag.
     *
     * @param item the item to add to this bag
     */
    void add(E item);

    /**
     * Returns an iterator over elements of this bag.
     *
     * @return an iterator over elements of this bag
     */
    Iterator<E> iterator();
}
