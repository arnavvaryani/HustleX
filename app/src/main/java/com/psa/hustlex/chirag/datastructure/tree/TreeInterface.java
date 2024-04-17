package com.psa.hustlex.chirag.datastructure.tree;

import com.psa.hustlex.chirag.datastructure.linksequence.LinkSequence;

public interface TreeInterface<T> {

    /**
     * Find the parent of the certain node based on the status value.
     * @param node: To check the node whether the node's status is equal to status mentioned or not.
     * @param status: Under which status should the node be added.
     * @return Returns the parent node under which the node should be added.
     */
    public Tree<T>.Node findTheParent(Tree<T>.Node node, String status);

    /**
     * Creates a new node to be added in the tree.
     * @param heading: Specify the heading for the node.
     * @param type: Specify the type of node.
     * @param kanbanTask: Specify the data of the node to be added in the node.
     * @return Returns the new created node.
     */
    public Tree<T>.Node createNode(String heading, String type, T kanbanTask);

    /**
     * Add a new node in the tree.
     * @param newNode: Node to be added in the tree.
     * @param status: Under which status (parent node) should the node be added.
     * @return Returns true, if the node is successfully added in the tree, else returns false.
    */
    boolean addNode(Tree<T>.Node newNode, String status);

    /**
     * Removes/Deletes the node from the tree.
     * @param node: Specify the node to be deleted/removed from the tree.
     * @param status: Specify the parent node's status from under which the node should be removed.
     * @return Returns true if the node is successfully removed, else returns false.
     */
    boolean removeNode(Tree<T>.Node node, String status);

    /**
     * Transfers a node from one parent node to another parent node.
     * @param node: Specify the node to be transferred.
     * @param fromStatus: Specify the parent node's status from which the node is to be moved from.
     * @param toStatus: Specify the parent node's status to which the node is to be moved to.
     * @return Returns true, if the node is successfully moved from one parent node to another parent node, else returns false.
     */
    boolean transferTo(Tree<T>.Node node, String fromStatus, String toStatus);

    /**
     * Display the tree.
     * @return Returns the tree structure generated using recursion and uses indentation to specify children node and returns the tree structure as a string.
     */
    String displayTree();

    /**
     * Finds the node with specified status and returns all the children of that node.
     * @param status: Specify the status of the node, whose children nodes are to be returned.
     * @return Returns all the children of the parent node with the provided status.
     * */
    LinkSequence<Tree<T>.Node> getChildrenOf(String status);

    /**
     * Returns the total number of nodes present in the tree.
     * @return Returns the count of number of nodes currently present in the complete tree.
     */
    int getTotalNodes();
}
