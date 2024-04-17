package com.psa.hustlex.chirag.datastructure.linksequence;
public interface LinkSequenceInterface<T> {

    /**
     * Adds a new node in the LinkSequence.
     * @param newNodeData: The new node data that will be added to the LinkSequence.
     * */
    void add(T newNodeData);

    /**
     * Removes the node from the LinkSequence.
     * @param nodeData: Node's data to be removed from the LinkSequence.
     * @return Returns true, if the node with data value is successfully removed from the LinkSequence, else returns false.
     * */
    boolean remove(T nodeData);

    /**
     * Removes the last node from the LinkSequence.
     * @return Returns true, if the last node from the LinkSequence is successfully removed, else returns false.
     * */
    boolean removeLast();

    /**
     * Removes the first node from the LinkSequence.
     * @return Returns true, if the first node from the LinkSequence is successfully removed, else returns false.
     * */
    boolean removeFirst();

    /**
     * Removes the node at given index from the LinkSequence.
     * @param index: Specify the index to be removed from the LinkSequence.
     * @return Returns true, if the node at specified index is removed successfully, else returns false.
     * */
    boolean removeAt(int index);

    /**
     * Returns the number of nodes present in the LinkSequence.
     * @return Returns the count of number of nodes currently present in the LinkSequence.
     * */
    int length();

    /**
     * Returns the node value present at the specified index.
     * @param index: Specify the index location to retrieve that particular node's data value present at the index in LinkSequence.
     * @return Returns the node's data value present at the specified index.
     * */
    T get(int index);

    /**
     * Checks whether the LinkSequence is empty or not.
     * @return Returns true, if the LinkSequence has no nodes present in it, else returns false.
     * */
    boolean isEmpty();

    /**
     * Convert the LinkSequence data values into an array and returns it.
     * @return Returns an array of data values present in the LinkSequence.
     * */
    T[] toArray();

}
