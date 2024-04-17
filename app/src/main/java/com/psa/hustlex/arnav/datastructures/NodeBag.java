package com.psa.hustlex.arnav.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class NodeBag<E> implements Iterable<E>, NodeBagInterface<E> {
    private Node<E> first;
    private int n;

    private static class Node<E> {
        private E item;
        private Node<E> next;
    }

    public NodeBag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void add(E item) {
        Node<E> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<E> {
        private Node<E> current;

        public LinkedIterator(Node<E> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E item = current.item;
            current = current.next;
            return item;
        }
    }
}