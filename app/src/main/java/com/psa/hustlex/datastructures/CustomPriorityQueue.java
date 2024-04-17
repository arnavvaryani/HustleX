package com.psa.hustlex.datastructures;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
    private Object[] heap;
    private int size;
    private int capacity;

    public CustomPriorityQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than zero.");
        }
        this.capacity = initialCapacity;
        this.size = 0;
        this.heap = new Object[capacity];
    }
    @Override
    public void enqueue(E item) {
        if (item == null) {
            throw new NullPointerException("Attempted to enqueue a null item.");
        }
        ensureCapacity();
        heap[size] = item;
        int current = size;
        size++;
        while (current > 0 && ((E) heap[parent(current)]).compareTo((E) heap[current]) > 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    @Override
    public E dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        E result = (E) heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return result;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) heap[index];
    }

    public E dequeueAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (size == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }

        E removedItem = (E) heap[index];
        heap[index] = heap[size - 1];
        size--;

        if (index == size) {
            return removedItem;
        }

        E item = (E) heap[index];

        heapify(index);

        if (heap[index] == item) {
            while (index > 0 && ((E) heap[parent(index)]).compareTo(item) > 0) {
                swap(index, parent(index));
                index = parent(index);
            }
        }

        return removedItem;
    }

    public int size() {
        return size;
    }
    private void ensureCapacity() {
        if (size >= capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return (E) heap[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void heapify(int index) {
        int left = leftChild(index);
        int right = rightChild(index);
        int smallest = index;

        if (left < size && ((E) heap[left]).compareTo((E) heap[index]) < 0) {
            smallest = left;
        }
        if (right < size && ((E) heap[right]).compareTo((E) heap[smallest]) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) heap[currentIndex++];
            }
        };
    }

    public void clear() {
        size = 0;
    }
}
