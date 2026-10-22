package org.example.sort;

import org.example.collection.CustomArrayList;

import java.util.Comparator;

public class QuickSortAlgorithm<T> implements SortAlgorithm<T> {

    @Override
    public void sort(CustomArrayList<T> list, Comparator<? super T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private void quickSort(CustomArrayList<T> list, int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private int partition(CustomArrayList<T> list, int low, int high, Comparator<? super T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(CustomArrayList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    @Override
    public String getName() {
        return "Быстрая сортировка";
    }
}