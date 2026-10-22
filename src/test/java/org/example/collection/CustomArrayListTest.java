package org.example.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    @Test
    void addAndGetWorksCorrectly() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    void removeShiftsElements() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void capacityGrowsAutomatically() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        assertEquals(100, list.size());
        assertEquals(99, list.get(99));
    }

    @Test
    void getWithInvalidIndexThrowsException() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }
}