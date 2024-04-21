package com.psa.hustlex.arnav.datastructures;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @param <E> the type of elements in this priority queue, must implement Comparable<E>
 */
public class HeapPriorityQueue<E extends Comparable<E>> implements HeapPriorityQueueInterface<E> {
    private Object[] heap;
    private int size;
    private int capacity;

    /**
     * Constructs a new priority queue with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the priority queue
     * @throws IllegalArgumentException if the initial capacity is less than or equal to zero
     */

    public HeapPriorityQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than zero.");
        }
        this.capacity = initialCapacity;
        this.size = 0;
        this.heap = new Object[capacity];
    }

    /**
     * Adds a new element to the priority queue.
     *
     * @param item the element to add
     * @throws NullPointerException if the item is null
     */
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

    /**
     * Removes and returns the smallest element in the priority queue.
     *
     * @return the smallest element
     * @throws NoSuchElementException if the priority queue is empty
     */
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

    /**
     * Retrieves, but does not remove, the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) heap[index];
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws NoSuchElementException if the priority queue is empty
     */
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

    /**
     * Returns the number of elements in this priority queue.
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Ensures that the heap has enough capacity to add new elements.
     */
    private void ensureCapacity() {
        if (size >= capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
    }

    /**
     * Inserts an element at a specific index in the heap.
     *
     * @param index the index at which the element is to be inserted
     * @param newItem the new item to insert
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws NullPointerException if the newItem is null
     */
    public void enqueueAt(int index, E newItem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (newItem == null) {
            throw new NullPointerException("Cannot enqueue a null item.");
        }

        heap[index] = newItem;

        reheap(index);
    }

    /**
     * Reheaps the heap starting from a specific index.
     *
     * @param index the index from which to start reheaping
     */
    private void reheap(int index) {
        E item = (E) heap[index];

        while (index > 0 && ((E) heap[parent(index)]).compareTo(item) > 0) {
            swap(index, parent(index));
            index = parent(index);
        }
        if (heap[index] == item) {
            heapify(index);
        }
    }

    /**
     * Retrieves, but does not remove, the smallest element in the priority queue.
     *
     * @return the smallest element
     * @throws IllegalStateException if the priority queue is empty
     */
    @Override
    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return (E) heap[0];
    }

    /**
     * Returns true if this priority queue contains no elements.
     *
     * @return true if this priority queue is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Restores the heap property starting from the specified index.
     *
     * @param index the index at which to start the heapify process
     */
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

    /**
     * Returns the parent index of the given child index.
     *
     * @param index the child index
     * @return the parent index
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Returns the left child index of the given parent index.
     *
     * @param index the parent index
     * @return the left child index
     */
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * Returns the right child index of the given parent index.
     *
     * @param index the parent index
     * @return the right child index
     */
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * Swaps the elements at the two given indices in the heap.
     *
     * @param i the first index
     * @param j the second index
     */
    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Provides an iterator over the elements in the heap.
     *
     * @return an Iterator over the elements in the heap
     */
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

    /**
     * Clears all the elements from the priority queue.
     */
    public void clear() {
        size = 0;
    }
}
