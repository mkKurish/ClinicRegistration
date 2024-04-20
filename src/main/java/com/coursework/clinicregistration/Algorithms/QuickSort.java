package com.coursework.clinicregistration.Algorithms;

import com.coursework.clinicregistration.DataStructures.DoubleLinkedList;

import java.util.Iterator;

public class QuickSort implements Sorting{

    @Override
    public <T extends Comparable> DoubleLinkedList<T> sort(DoubleLinkedList<T> source, Class<T> dataClass) {
        if (source.length <= 1) return source;
        Iterator<T> slow = source.iterator();
        Iterator<T> fast = source.iterator();
        boolean stepFlag = true;
        T mid = null;
        while (fast.hasNext()){
            fast.next();
            if (stepFlag) mid = slow.next();
            stepFlag = !stepFlag;
        }

        DoubleLinkedList<T> leftPart = new DoubleLinkedList<>(dataClass);
        DoubleLinkedList<T> rightPart = new DoubleLinkedList<>(dataClass);

        Iterator<T> curr = source.iterator();
        while (curr.hasNext()){
            T currData = curr.next();
            if (currData.compareTo(mid) > 0) leftPart.add(currData);
            if (currData.compareTo(mid) < 0) rightPart.add(currData);
        }
        leftPart = sort(leftPart, dataClass);
        rightPart = sort(rightPart, dataClass);
        leftPart.add(mid);
        curr = rightPart.iterator();
        while (curr.hasNext()){
            leftPart.add(curr.next());
        }
        return leftPart;
    }
}
