package org.example.sort;

import org.example.collection.CustomArrayList;

import java.util.Comparator;

public class BubbleSortAlgorithm<T> implements SortAlgorithm<T> {

    @Override
    public void sort(CustomArrayList<T> list, Comparator<? super T> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                T a = list.get(j);
                T b = list.get(j + 1);
                if (comparator.compare(a, b) > 0) {
                    list.set(j, b);
                    list.set(j + 1, a);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Сортировка пузырьком";
    }
}