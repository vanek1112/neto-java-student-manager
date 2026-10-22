package org.example.sort;

import org.example.collection.CustomArrayList;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortAlgorithmTest {

    private CustomArrayList<Integer> sampleList() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        int[] values = {5, 3, 8, 1, 9, 2};
        for (int v : values) {
            list.add(v);
        }
        return list;
    }

    @Test
    void bubbleSortSortsAscending() {
        CustomArrayList<Integer> list = sampleList();
        new BubbleSortAlgorithm<Integer>().sort(list, Comparator.naturalOrder());
        int[] expected = {1, 2, 3, 5, 8, 9};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i));
        }
    }

    @Test
    void quickSortSortsDescending() {
        CustomArrayList<Integer> list = sampleList();
        new QuickSortAlgorithm<Integer>().sort(list, Comparator.<Integer>naturalOrder().reversed());
        int[] expected = {9, 8, 5, 3, 2, 1};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i));
        }
    }
}