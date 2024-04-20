package com.coursework.clinicregistration.Algorithms;

import com.coursework.clinicregistration.DataStructures.DoubleLinkedList;

public interface Sorting {
    <T extends Comparable>  DoubleLinkedList<T> sort(DoubleLinkedList<T> list, Class<T> dataClass);
}
