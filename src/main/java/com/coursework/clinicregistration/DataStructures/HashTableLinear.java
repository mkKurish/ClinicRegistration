package com.coursework.clinicregistration.DataStructures;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class HashTableLinear<T> {
    Pair[] data;
    Class<T> dataClass;
    Field keyField;
    int size = 100;
    int step = 7;

    private static class Pair<T> {
        T value;
        boolean deleted;

        Pair(T data) {
            this.value = data;
            deleted = false;
        }
    }

    public HashTableLinear(Class<T> dataClass, String keyField) throws NoSuchFieldException {
        this.dataClass = dataClass;
        data = new Pair[size];
        this.keyField = this.dataClass.getDeclaredField(keyField);
        this.keyField.setAccessible(true);
    }

    private <K> int hashOfField(K value) {
        String stringKey = value.toString();
        int acc = 0;
        for (int i = 0; i < stringKey.length(); i++) {
            char curr = stringKey.charAt(i);
            acc += (int) curr;
        }
//        System.out.println("acc: " + acc);
        return acc % size;
    }

    private int hash(T value) throws IllegalAccessException {
        Object keyObject = keyField.get(value).toString();
        return hashOfField(keyObject);
    }

    public boolean add(T value) throws IllegalAccessException {
        int h = hash(value);
        int count = 0;
        while (count < size) {
            if (data[h] == null || data[h].deleted) {
                data[h] = new Pair(value);
//                System.out.println("hash: " + h);
                return true;
            }
            h = (h + step) % size;
            count++;
        }
        return false;
    }

    public <K> T findByKey(K value) throws IllegalAccessException {
        int h = hashOfField(value);
        int count = 0;
        while (count < size) {
            if (data[h] == null) return null;
            if (!data[h].deleted && keyField.get(data[h].value).equals(value)) return (T) data[h].value;
            h = (h + step) % size;
            count++;
        }
        return null;
    }

    public <K> int findHashByKey(K value) throws IllegalAccessException {
        int h = hashOfField(value);
        int count = 0;
        while (count < size) {
            if (data[h] == null) return -1;
            if (!data[h].deleted && keyField.get(data[h].value).equals(value)) return h;
            h = (h + step) % size;
            count++;
        }
        return -1;
    }

    public <K> ArrayList<T> findByOtherField(String field, K value) throws IllegalAccessException, NoSuchFieldException {
        Field currField = this.dataClass.getDeclaredField(field);
        currField.setAccessible(true);
        ArrayList<T> ans = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (data[i] != null && !data[i].deleted && currField.get(data[i].value).equals(value)) {
                ans.add((T) data[i].value);
            }
        }
        return ans;
    }

    public <K> boolean remove(K value) throws IllegalAccessException {
        int h = hashOfField(value);
        int count = 0;
        while (count < size) {
            if (data[h] == null) return false;
            if (!data[h].deleted && keyField.get(data[h].value).equals(value)) {
                data[h].deleted = true;
                data[h].value = null;
                return true;
            }
            h = (h + step) % size;
            count++;
        }
        return false;
    }

    public boolean removeBySubject(T value) throws IllegalAccessException {
        return remove(keyField.get(value));
    }

    public void clear() {
        data = new Pair[size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (data[i] != null && !data[i].deleted) {
                sb.append(data[i].value.toString());
                if (i != size - 1) sb.append("\n");
            }
        }
        return sb.toString();
    }
}
