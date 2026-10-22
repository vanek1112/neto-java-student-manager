package org.example.sort;

import org.example.collection.CustomArrayList;

import java.util.Comparator;

public interface SortAlgorithm<T> {

    void sort(CustomArrayList<T> list, Comparator<? super T> comparator);

    String getName();
}