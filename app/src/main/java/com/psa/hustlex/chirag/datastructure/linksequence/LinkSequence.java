package com.psa.hustlex.chirag.datastructure.linksequence;

import java.lang.reflect.Array;

public class LinkSequence<T>implements LinkSequenceInterface<T>{

    public class Node{

        T data;
        Node next;

        public T getData() {
            return data;
        }

        Node(T dataNode) {
            this.data = dataNode;
        }
    }

//    Declaring the headNode for the LinkSequence.
    private Node seqHead;
//    Declaring the currentSize variable that will keep track of the number of items currently present in the LinkSequence.
    private int currentSize;
//    Declaring and initializing initialized variable, that tracks and validates the initialization of the LinkSequence.
    boolean initialized = false;

//    LinkSequence constructor: Initializes head node to null, currentSize to 0, and sets initialized to true.
    public LinkSequence() {
        seqHead = null;
        currentSize = 0;
        initialized = true;
    }

//    Checks and validates the initialization of the LinkSequence.
    private void checkInitialization(){
        if(!initialized){
            throw new SecurityException("LinkSequence is not properly initialized!");
        }
    }

    /**
     * Adds a new node in the LinkSequence.
     * @param dataNode: The new node data that will be added to the LinkSequence.
     * */
    @Override
    public void add(T dataNode) {
        // TODO Auto-generated method stub
        checkInitialization();
        Node newNode = new Node(dataNode);
        if(this.seqHead == null) {
            this.seqHead = newNode;
            newNode.next = null;
        }else{
            Node temp = this.seqHead;
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = null;
        }
        currentSize++;

    }

    /**
     * Removes the node from the LinkSequence.
     * @param dataNode: Node's data to be removed from the LinkSequence.
     * @return Returns true, if the node with data value is successfully removed from the LinkSequence, else returns false.
     * */
    @Override
    public boolean remove(T dataNode) {
        // TODO Auto-generated method stub
        checkInitialization();
        Node temp = this.seqHead;
        Node prev = null;
        while(temp != null){
            if(temp.data.equals(dataNode)){
                if(prev == null){
                    System.out.println("Removing object first element");
                    this.seqHead = temp.next;
                }else{
                    System.out.println("Removing object any other element in ll");
                    prev.next = temp.next;
                }
                this.currentSize -= 1;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }

    /**
     * Removes the last node from the LinkSequence.
     * @return Returns true, if the last node from the LinkSequence is successfully removed, else returns false.
     * */
    @Override
    public boolean removeLast() {
        // TODO Auto-generated method stub
        checkInitialization();
        if(this.length() == 0){
            return true;
        }
        Node temp = this.seqHead;
        Node prev = null;
        if(this.length() == 1){
            this.seqHead = null;
            this.currentSize -= 1;
            return true;
        }
        while(temp.next != null){
            prev = temp;
            temp = temp.next;
        }
        prev.next = temp.next;
        this.currentSize -= 1;
        return true;

    }

    /**
     * Removes the first node from the LinkSequence.
     * @return Returns true, if the first node from the LinkSequence is successfully removed, else returns false.
     * */
    @Override
    public boolean removeFirst() {
        // TODO Auto-generated method stub
        checkInitialization();
        if(this.seqHead == null){
            return true;
        }
        this.seqHead = this.seqHead.next;
        this.currentSize -= 1;
        return true;

    }

    /**
     * Removes the node at given index from the LinkSequence.
     * @param index: Specify the index to be removed from the LinkSequence.
     * @return Returns true, if the node at specified index is removed successfully, else returns false.
     * */
    @Override
    public boolean removeAt(int index) {
        // TODO Auto-generated method stub
        checkInitialization();
        // Index out of bounds
        if (index < 0 || index >= this.currentSize) {
            return false;
        }

        Node temp = this.seqHead;
        Node prev = null;
        int indexCounter = 0;
        while (temp != null) {
            if (indexCounter == index) {
                if (prev == null) {
                    System.out.println("Removing object at index 0");
                    this.seqHead = temp.next;
                } else {
                    System.out.println("Removing object at index " + index);
                    prev.next = temp.next;
                }
                this.currentSize -= 1;
                return true;
            }
            prev = temp;
            temp = temp.next;
            indexCounter++;
        }
        return false;

    }

    /**
     * Returns the number of nodes present in the LinkSequence.
     * @return Returns the count of number of nodes currently present in the LinkSequence.
     * */
    @Override
    public int length() {
        // TODO Auto-generated method stub
        checkInitialization();
        return currentSize;
    }

    /**
     * Returns the node value present at the specified index.
     * @param index: Specify the index location to retrieve that particular node's data value present at the index in LinkSequence.
     * @return Returns the node's data value present at the specified index.
     * */
    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        checkInitialization();
        if(index >= this.currentSize || this.seqHead == null){
            return null;
        }
        int indexCounter = 0;
        Node temp = this.seqHead;
        while(indexCounter < index){
            temp = temp.next;
            indexCounter++;
        }
        return temp.data;
    }

    /**
     * Checks whether the LinkSequence is empty or not.
     * @return Returns true, if the LinkSequence has no nodes present in it, else returns false.
     * */
    public boolean isEmpty(){
        checkInitialization();
        return currentSize == 0;
    }

    /**
     * Convert the LinkSequence data values into an array and returns it.
     * @return Returns an array of data values present in the LinkSequence.
     * */
    public T[] toArray(){
        checkInitialization();
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(this.seqHead.data.getClass(), currentSize);
        Node temp = this.seqHead;
        int index = 0;
        while(temp != null && index < currentSize){
            arr[index] = temp.data;
            index++;
            temp = temp.next;
        }
        return arr;
    }

    @Override
    public String toString() {
        checkInitialization();
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("[");
        Node temp = this.seqHead;
        if(temp != null){
            strBuff.append(temp.data);
            temp = temp.next;
            while(temp != null){
                strBuff.append(", ");
                strBuff.append(temp.data);
                temp = temp.next;
            }
        }
        strBuff.append("]");
        return strBuff.toString();
    }
}