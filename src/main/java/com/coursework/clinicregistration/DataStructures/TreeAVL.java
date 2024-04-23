package com.coursework.clinicregistration.DataStructures;

import com.coursework.clinicregistration.Algorithms.TextSearch;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TreeAVL<T extends Comparable> {
    private Class<T> dataClass;
    private Field keyField;
    private TreeNode<T> root = null;
    private boolean successRemove = true;

    private class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;
        int height;

        public TreeNode(T data) {
            this.data = data;
        }
    }

    public TreeAVL(Class<T> dataClass, String keyField) throws NoSuchFieldException {
        this.dataClass = dataClass;
        this.keyField = dataClass.getDeclaredField(keyField);
        this.keyField.setAccessible(true);
    }

    private int height(TreeNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private int bFactor(TreeNode<T> node) {
        return height(node.right) - height(node.left);
    }

    private void updateHeight(TreeNode<T> node) {
        int lHeight = height(node.left);
        int rHeight = height(node.right);
        node.height = Math.max(lHeight, rHeight) + 1;
    }

    private TreeNode<T> leftTurn(TreeNode<T> node) {
        TreeNode<T> childR = node.right;
        node.right = childR.left;
        childR.left = node;
        updateHeight(node);
        updateHeight(childR);
        return childR;
    }

    private TreeNode<T> rightTurn(TreeNode<T> node) {
        TreeNode<T> childL = node.left;
        node.left = childL.right;
        childL.right = node;
        updateHeight(node);
        updateHeight(childL);
        return childL;
    }

    private TreeNode<T> balance(TreeNode<T> node) {
        this.updateHeight(node);
        if (bFactor(node) == 2) {
            if (bFactor(node.right) < 0) node.right = rightTurn(node.right);
            return leftTurn(node);
        }
        if (bFactor(node) == -2) {
            if (bFactor(node.left) > 0) node.left = leftTurn(node.left);
            return rightTurn(node);
        }
        return node;
    }

    private TreeNode<T> findNearest(TreeNode<T> node, TreeNode<T> childR) {
        if (childR != null) {
            while (node.left != null) node = node.left;
        } else {
            while (node.right != null) node = node.right;
        }
        return node;
    }

    private TreeNode<T> add(T data, TreeNode<T> node) {
        if (node == null) return new TreeNode<T>(data);
        if (node.data.compareTo(data) > 0) node.left = add(data, node.left);
        else node.right = add(data, node.right);
        return balance(node);
    }

    public boolean add(T data) throws IllegalAccessException {
        if (findByKeyValue(keyField.get(data)) != null) return false;
        root = add(data, root);
        return true;
    }

    private TreeNode<T> findMin(TreeNode<T> node) {
        return node.left != null ? findMin(node.left) : node;
    }

    private TreeNode<T> removeMin(TreeNode<T> node) {
        if (node.left == null) return node.right;
        node.left = removeMin(node.left);
        return balance(node);
    }

    private <K> TreeNode<T> remove(K value, TreeNode<T> node) {
        if (node == null) return null;
        if (node.data.compareTo(value) > 0) node.left = remove(value, node.left);
        else if (node.data.compareTo(value) < 0) node.right = remove(value, node.right);
        else {
            successRemove = true;
            TreeNode<T> l = node.left;
            TreeNode<T> r = node.right;
            if (l == null) return r;
            if (r == null) return l;
            TreeNode<T> min;
            if (height(r) <= height(l)) {
                min = findMin(r);
                min.right = removeMin(r);
                min.left = l;
            } else {
                min = findMin(l);
                min.left = removeMin(l);
                min.right = r;
            }
            return balance(min);
        }
        return balance(node);
    }

    public <K> boolean remove(K value) {
        successRemove = false;
        root = remove(value, root);
        return successRemove;
    }

    public <K> T findByKeyValue(K value) {
        TreeNode<T> current = root;
        while (current != null) {
            if (current.data.compareTo(value) == 0) return current.data;
            if (current.data.compareTo(value) < 0) current = current.right;
            else current = current.left;
        }
        return null;
    }

    private ArrayList<T> findByStringField(String fieldName, String value, TextSearch textSearch, TreeNode<T> node) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<T> answer = new ArrayList<>();
        if (node == null) return answer;
        Field selectedField = dataClass.getDeclaredField(fieldName);
        selectedField.setAccessible(true);
        if (node.left != null){
            ArrayList<T> leftAns = findByStringField(fieldName, value, textSearch, node.left);
            answer.addAll(leftAns);
        }
        if (textSearch.search((String) selectedField.get(node.data), value) > -1){
            answer.add(node.data);
        }
        if (node.right != null){
            ArrayList<T> rightAns = findByStringField(fieldName, value, textSearch, node.right);
            answer.addAll(rightAns);
        }
        return answer;
    }

    public ArrayList<T> findByStringField(String fieldName, String value, TextSearch textSearch) throws NoSuchFieldException, IllegalAccessException {
        return findByStringField(fieldName, value, textSearch, root);
    }

    public void clear() {
        root = null;
    }

    private String toString(TreeNode<T> node) {
        if (node == null) return "";
        StringBuilder sb = new StringBuilder();
        String leftString = toString(node.left);
        String rightString = toString(node.right);
        if (!leftString.isEmpty()) sb.append(leftString).append("\n");
        sb.append(node.data.toString());
        if (!rightString.isEmpty()) sb.append("\n").append(rightString);
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(root);
    }
}
