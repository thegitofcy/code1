package com.cy.core.tree;

/**
 * @program: datastructurev
 * @description: binary tree
 * @author: cy
 */
public class BinaryTree<E extends Comparable<E>> {

    /**
     * binary tree node.
     */
    private class Node {
        public E e;
        public Node left;
        public Node right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    /**
     * binary tree root node.
     */
    private Node root;

    /**
     * binary tree node number.
     */
    private int size;

    /**
     * whether store same element.
     */
    private boolean storeSameElement;

    public BinaryTree() {
        this(false);
    }

    public BinaryTree(boolean storeSameElement) {
        this.root = null;
        this.size = 0;
        this.storeSameElement = storeSameElement;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含元素 e.
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    public boolean contains(Node node, E e) {
        if (null == root) return false;
        else if (e.compareTo(node.e) == 0) return true;
        else if (e.compareTo(node.e) < 0) return contains(node.left, e);
        else return contains(node.right, e);
    }

    /**
     * return min Element.
     */
    public E minimum() {
        if (size == 0) throw new IllegalArgumentException("brinary tree is empty!");
        return minimum(root).e;
    }

    public Node minimum(Node node) {
        if (null == node.left) return node;
        return minimum(node.left);
    }

    /**
     * return max Eelment.
     */
    public E maximum() {
        if (size == 0) throw new IllegalArgumentException("brinary tree is empty!");
        return maximum(root).e;
    }

    public Node maximum(Node node) {
        if (null == node.right) return node;
        return maximum(node.right);
    }


    /**
     * 向二叉树插入元素 e .
     */
    public void add(E e) {
        root = add(root, e);
    }

    /**
     * 递归算法: 向以 node 为根的二分搜索树中插入元素 e, 并返回插入新节点后二分搜索树的根 .
     */
    public Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }
        if (storeSameElement) {
            if (e.compareTo(node.e) == 0) node.left = add(node.left, e);
        }
        if (e.compareTo(node.e) < 0) node.left = add(node.left, e);
        if (e.compareTo(node.e) > 0) node.right = add(node.right, e);
        return node;
    }


    /**
     * 移除最小元素
     */
    public E removeMin() {
        E min = minimum();
        root = removeMin(root);
        return min;
    }

    public Node removeMin(Node node) {
        if (null == node.left) {
            Node rightNode = node.right;
            node.left = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 移除最大元素
     */
    public E removeMax() {
        E maximum = maximum();
        root = removeMax(root);
        return maximum;
    }

    public Node removeMax(Node node) {
        if (null == node.right) {
            Node leftNode = node.left;
            node.right = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 移除指定元素
     */
    public void remove(E e) {
        root = remove(root, e);
    }

    public Node remove(Node node, E e) {
        if (null == node) return null;
        if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else { // e == node.e
            if (null == node.left) { // 只有右子树
                Node nodeRight = node.right;
                node.right = null;
                size--;
                return nodeRight;
            } else if (null == node.right) { // 只有左子树
                Node nodeLeft = node.left;
                node.left = null;
                size--;
                return nodeLeft;
            }
        }
        // node 同时拥有左右子树
        Node successor = minimum(node.right);
        successor.right = removeMin(node.right);
        successor.left = node.left;

        node.left = node.right = null;
        return successor;
    }


    /**
     * 前序遍历
     */
    public void perTraversal() {
        perTraversal(root);
    }

    public void perTraversal(Node node) {
        if (null == node) return;
        System.out.println(node.e);
        perTraversal(node.left);
        perTraversal(node.right);
    }

    /**
     * 中序遍历
     */
    public void inTraversal() {
        inTraversal(root);
    }

    public void inTraversal(Node node) {
        if (null == node) return;
        inTraversal(node.left);
        System.out.println(node.e);
        inTraversal(node.right);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generateBSTString(root, 0, sb);
        return sb.toString();
    }

    /**
     * 生成以 node 为根节点, 深度为 depth 的描述二分树的字符串
     */
    public void generateBSTString(Node node, int depth, StringBuilder sb) {
        if (node == null) {
            sb.append(generateDEpthtring(depth) + "null\n");
            return;
        }
        sb.append(generateDEpthtring(depth) + node.e + "\n");
        generateBSTString(node.left, depth + 1, sb);
        generateBSTString(node.right, depth + 1, sb);
    }

    private String generateDEpthtring(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("=");
        }
        return sb.append(">").toString();
    }

}
