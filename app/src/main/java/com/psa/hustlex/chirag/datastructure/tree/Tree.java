package com.psa.hustlex.chirag.datastructure.tree;


import com.psa.hustlex.chirag.datastructure.linksequence.LinkSequence;
import com.psa.hustlex.chirag.datastructure.linksequence.LinkSequenceInterface;

import java.util.UUID;

public class Tree<T> implements TreeInterface<T> {

//    Inner class: Defines the Node type in the tree.
    public class Node {
        UUID nodeId; // generate randomly
        String heading; // root, notStarted, inProgress, completed, kanbanTask
        String type; // root[root], status [notStarted, inProgress, completed], tasks
        T kanbanTask;
        LinkSequenceInterface<Node> children;

        public Node(String heading, String type) {
            this.nodeId = UUID.randomUUID();
            this.heading = heading;
            this.type = type;
            this.kanbanTask = null;
            this.children = new LinkSequence<>();
        }

        public Node(String heading, String type, T kanbanTask) {
            this.nodeId = UUID.randomUUID();
            this.heading = heading;
            this.type = type;
            this.kanbanTask = kanbanTask;
            this.children = new LinkSequence<>();
        }

        public T getKanbanTask() {
            return kanbanTask;
        }
    }

//    Declaring the treeRoot node for the tree.
    private Node treeRoot;
//    Declaring the total number of nodes present in the tree.
    private int totalNodes;
//    Declaring and initializing the initialized variable that will be used to track whether the tree is properly initialized or not.
    boolean initialized = false;

//    Tree Constructor: Initializes treeRoot to null, totalNodes equal to 0, and sets initialized variable to true.
    public Tree() {
        treeRoot = null;
        totalNodes = 0;
        initialized = true;
    }



//    Method used to check for whether the tree is initialized properly or not.
    private void checkInitialization(){
        if(!initialized){
            throw new SecurityException("Tree is not properly initialized!");
        }
    }

    /**
     * Creates a new node to be added in the tree.
     * @param heading: Specify the heading for the node.
     * @param type: Specify the type of node.
     * @param kanbanTask: Specify the data of the node to be added in the node.
     * @return Returns the new created node.
     */
    @Override
    public Node createNode(String heading, String type, T kanbanTask) {
        checkInitialization();
        Node newNode;
        if (kanbanTask == null) {
            newNode = new Node(heading, type);
            return newNode;
        }
        newNode = new Node(heading, type, kanbanTask);
        return newNode;
    }

    /**
     * Find the parent of the certain node based on the status value.
     * @param node: To check the node whether the node's status is equal to status mentioned or not.
     * @param status: Under which status should the node be added.
     * @return Returns the parent node under which the node should be added.
     */
    @Override
    public Node findTheParent(Node node, String status){
        checkInitialization();
        if(node.heading.equals("task")){
            return null;
        }
        if(node.heading.equals(status)){
            return node;
        }

        for(int i = 0; i< node.children.length(); i++){
            Node result = findTheParent(node.children.get(i), status);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    /**
     * Add a new node in the tree.
     * @param newNode: Node to be added in the tree.
     * @param status: Under which status (parent node) should the node be added.
     * @return Returns true, if the node is successfully added in the tree, else returns false.
     */
    @Override
    public boolean addNode(Node newNode, String status) {
        checkInitialization();
        // TODO Auto-generated method stub
        Node temp = this.treeRoot;
        if (temp == null) {
            this.treeRoot = newNode;
            totalNodes++;
            return true;
        }
        if (status.isEmpty()) {
            temp.children.add(newNode);
            totalNodes++;
            return true;
        }

        Node parent = findTheParent(this.treeRoot, status);
        if(parent == null){
            return false;
        }
        parent.children.add(newNode);
        totalNodes++;
        return true;
    }

    /**
     * Display the tree structure.
     * @return Returns the tree structure generated using recursion and uses indentation to specify children node and returns the tree structure as a string.
     */
    @Override
    public String displayTree() {
        checkInitialization();
        if (this.treeRoot == null) {
            return "";
        }
        StringBuffer resultStr = new StringBuffer();
        return viewTree(treeRoot, 0, resultStr);
    }

    private String viewTree(Node node, int level, StringBuffer resultStr) {
        checkInitialization();
        StringBuilder treeView = new StringBuilder();
        for (int i = 0; i < level; i++) {
            treeView.append("--|"); // Adjust the number of spaces for indentation to define it as child node in a tree
        }
        String addTask = "";
        if(node.kanbanTask == null){
            addTask = "\n" + treeView.toString() + node.heading;
            resultStr.append(addTask);

        }else{
            addTask = "\n" + treeView.toString() + node.kanbanTask;
            resultStr.append(addTask);
        }
        for ( int i=0; i<node.children.length(); i++) {
            viewTree(node.children.get(i), level + 1, resultStr);
        }
        return resultStr.toString();
    }

    /**
     * Removes/Deletes the node from the tree.
     * @param node: Specify the node to be deleted/removed from the tree.
     * @param status: Specify the parent node's status from under which the node should be removed.
     * @return Returns true if the node is successfully removed, else returns false.
     */
    @Override
    public boolean removeNode(Node node, String status) {
        checkInitialization();
        // TODO Auto-generated method stub
        // this.children.remove(node);
        Node temp = this.treeRoot;
        if (temp == null) {
            return false;
        }
        if (status.isEmpty()) {
            temp.children.remove(node);
            totalNodes -= 1;
            return true;
        }

        Node parent = findTheParent(this.treeRoot, status);
        if(parent == null){
            return false;
        }
        parent.children.remove(node);
        totalNodes -= 1;
        return true;
    }

    /**
     * Transfers a node from one parent node to another parent node.
     * @param node: Specify the node to be transferred.
     * @param fromStatus: Specify the parent node's status from which the node is to be moved from.
     * @param toStatus: Specify the parent node's status to which the node is to be moved to.
     * @return Returns true, if the node is successfully moved from one parent node to another parent node, else returns false.
     */
    @Override
    public boolean transferTo(Node node, String fromStatus, String toStatus){
        checkInitialization();
        return removeNode(node, fromStatus) && addNode(node, toStatus);
    }

    /**
     * Finds the node with specified status and returns all the children of that node.
     * @param status: Specify the status of the node, whose children nodes are to be returned.
     * @return Returns all the children of the parent node with the provided status.
     * */
    @Override
    public LinkSequence<Node> getChildrenOf(String status) {
        checkInitialization();
        // TODO Auto-generated method stub
        LinkSequence<Node> result = new LinkSequence<>();
        Node parent = findTheParent(this.treeRoot, status);
        for(int i=0; i<parent.children.length(); i++){
            result.add(parent.children.get(i));
        }
        return result;
    }

    /**
     * Returns the total number of nodes present in the tree.
     * @return Returns the count of number of nodes currently present in the complete tree.
     */
    @Override
    public int getTotalNodes(){
        checkInitialization();
        return totalNodes;
    }

}