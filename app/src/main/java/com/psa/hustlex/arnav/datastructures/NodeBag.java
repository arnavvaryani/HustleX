package com.psa.hustlex.arnav.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @param <E> the type of elements contained in the bag
 */
public class NodeBag<E> implements Iterable<E>, NodeBagInterface<E> {
    private Node<E> first;
    private int n;

    /**
     * Helper class to define the node structure for the linked list.
     * Each node holds an item and a reference to the next node.
     */
    private static class Node<E> {
        private E item;
        private Node<E> next;
    }

    /**
     * Constructs an empty NodeBag.
     */
    public NodeBag() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if the bag is empty.
     *
     * @return true if the bag has no elements, false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of elements in the bag.
     *
     * @return the number of elements in the bag
     */
    public int size() {
        return n;
    }

    /**
     * Adds an item to the bag. The item is added at the beginning of the bag.
     *
     * @param item the item to add
     */
    public void add(E item) {
        Node<E> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    /**
     * Returns an iterator over elements of type E in this bag.
     *
     * @return an Iterator over elements of type E
     */
    @Override
    public Iterator<E> iterator() {
        return new LinkedIterator(first);
    }

    /**
     * An iterator over a linked list.
     */
    private class LinkedIterator implements Iterator<E> {
        private Node<E> current;

        /**
         * Constructs an iterator starting from the specified node.
         *
         * @param first the first node in the linked list
         */
        public LinkedIterator(Node<E> first) {
            current = first;
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more elements
         */
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E item = current.item;
            current = current.next;
            return item;
        }
    }
}