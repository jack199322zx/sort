package com.nino;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author ss
 * @date 2018/7/20 10:25
 */
public class Heap<E extends Comparable> implements Iterable<E>{

    private E[] data;
    private int capacity;
    private int count;
    private static int DEFAULT_CAPACITY = 11;

    public Heap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public Heap(int capacity) {
        data = (E[]) new Comparable[capacity];
        this.capacity = capacity;
    }

    public Heap(E[] array) {
        int length = array.length;
        this.capacity = length;
        this.count = length;
        this.data = (E[]) new Comparable[length];
        for (int i = 0; i < length; i++) {
            this.data[i] = array[i];
        }
        for (int i = (this.capacity >>> 1) - 1; i >= 0; i--) {
            shiftDown(i, this.data[i]);
        }
    }

    private void shiftDown(int k, E x) {
        int half = capacity >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            E c = data[child];
            int right = child + 1;
            if (right < capacity &&
                    c.compareTo(data[right]) > 0)
                c = data[child = right];
            if (x.compareTo(c) <= 0)
                break;
            data[k] = c;
            k = child;
        }
        data[k] = x;
    }

    private void swap(E[] list, int k, int j) {
        E temp = list[k];
        list[k] = list[j];
        list[j] = temp;
    }

    public E extractMin(){      //弹出最小值，即根节点
        if (capacity==0) return null;
        int s = --capacity;
        E start = data[0];
        E end = data[s];
        data[s] = null;
        shiftDown(0, end);         //将第一个数移至合适位置,保持最小堆性质
        return start;
    }

    public int size() {
        return this.capacity;
    }
    public boolean isEmpty(){
        return this.capacity == 0;
    }

    @Override
    public String toString() {
        return "Heap{" +
                "data=" + Arrays.toString(data) +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapIterator();
    }

    class HeapIterator implements Iterator<E>{
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        public boolean hasNext() {
            return cursor != count;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            int i = cursor;
            if (i >= count)
                throw new NoSuchElementException();
            Object[] elementData = Heap.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                Heap.this.extractMin();
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
