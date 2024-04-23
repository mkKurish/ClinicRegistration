package com.coursework.clinicregistration.DataStructures;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> prev;

    public Node(T data) {
        this.data = data;
    }
}

public class DoubleLinkedList<T> implements Iterable<T> {
    private final Class<T> dataClass;
    private Node<T> head;
    public int length = 0;

    public DoubleLinkedList(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    @Override
    public Iterator<T> iterator() {
        return new DLLIIterator<>(this);
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = this.head;
        if (this.head != null) this.head.prev = newNode;
        this.head = newNode;
        length++;
    }

    public boolean remove(T data) {
        Node<T> curr = this.head;
        while (curr != null && !curr.data.equals(data)) {
            curr = curr.next;
        }
        if (curr == null) return false;
        if (curr.prev != null) curr.prev.next = curr.next;
        if (curr.next != null) curr.next.prev = curr.prev;
        length--;
        return true;
    }

    public <K> boolean removeFirstBy(String fieldName, K value) throws NoSuchFieldException, IllegalAccessException {
        Field keyField = dataClass.getDeclaredField(fieldName);
        keyField.setAccessible(true);
        Node<T> curr = this.head;
        int count = 0;
        while (curr != null && count <= length) {
            if (keyField.get(curr.data).equals(value)) {
                if (curr.equals(head)) head = head.next;
                if (curr.prev != null) curr.prev.next = curr.next;
                if (curr.next != null) curr.next.prev = curr.prev;
                return true;
            }
            curr = curr.next;
            count++;
        }
        return false;
    }

    public <K> int removeAllBy(String fieldName, K value) throws NoSuchFieldException, IllegalAccessException {
        int count = 0;
        while(removeFirstBy(fieldName, value)){
            count++;
        }
        return count;
    }

    public <K> DoubleLinkedList<T> findBy(String fieldName, K value) throws NoSuchFieldException, IllegalAccessException {
        Field keyField = dataClass.getDeclaredField(fieldName);
        keyField.setAccessible(true);
        @SuppressWarnings("unchecked")
        DoubleLinkedList<T> answer = new DoubleLinkedList<>(dataClass);
        Node<T> curr = this.head;
        while (curr != null) {
            if (keyField.get(curr.data).equals(value)) {
                answer.add(curr.data);
            }
            curr = curr.next;
        }
        return answer;
    }

    public void clear() {
        head = null;
        length = 0;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        Node<T> curr = this.head;
        while (curr != null) {
            answer.append(curr.data.toString());
            if (curr.next != null) answer.append("\n");
            curr = curr.next;
        }
        return answer.toString();
    }

    Node<T> getHead() {
        return head;
    }

    public T getFirst(){
        return head != null ? head.data : null;
    }
}

class DLLIIterator<T> implements Iterator<T> {
    Node<T> current;

    public DLLIIterator(DoubleLinkedList<T> list) {
        current = list.getHead();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T data = current.data;
        current = current.next;
        return data;
    }
}