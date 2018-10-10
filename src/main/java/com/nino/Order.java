package com.nino;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ss
 * @date 2018/7/16 20:01
 */
public class Order<E> {

    public static <E extends Comparable<E>> void bubbleSort(E[] list) {
        int length = list.length;
        boolean nextPass = true;
        for (int i = 0; i < length && nextPass; i++) {
            nextPass = false;
            for (int j = 0; j < length - i - 1; j++) {
                if (list[j].compareTo(list[j + 1]) > 0) {
                    E temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    nextPass = true;
                }
            }
        }
        System.out.println(Arrays.toString(list));
    }

    public static <E> void bubbleSort(E[] list, Comparator<? super E> comparator) {
        int length = list.length;
        boolean nextPass = true;
        for (int i = 0; i < length && nextPass; i++) {
            nextPass = false;
            for (int j = 0; j < length - i - 1; j++) {
                if (comparator.compare(list[j], list[j + 1]) > 0) {
                    E temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    nextPass = true;
                }
            }
        }
        System.out.println(Arrays.toString(list));
    }

    public static <E extends Comparable<E>> void mergeSort(E[] list) {
        mergeSortByIndex(list, 0, list.length - 1);
    }

    private static <E extends Comparable<E>> void mergeSortByIndex(E[] list, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSortByIndex(list, low, mid);
            // 右边
            mergeSortByIndex(list, mid + 1, high);
            // 左右归并
            merge(list, low, high);
            System.out.println(Arrays.toString(list));
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Comparable<E>> void merge(E[] a, int low, int high) {
        E[] temp = (E[]) new Comparable[high - low + 1];
        int mid = (low + high) / 2;
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i].compareTo(a[j]) < 0) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖原数组的部分
        for (int k2 = 0; k2 < temp.length; k2++) {
            a[k2 + low] = temp[k2];
        }
    }

    public static <E extends Comparable<E>> void quickSort(E[] list) {
        quickSortByIndex(list, 0, list.length - 1);
    }

    public static <E extends Comparable<E>> void quickSortByIndex(E[] list, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSortByIndex(list, first, pivotIndex - 1);
            quickSortByIndex(list, pivotIndex + 1, last);
        }
    }

    public static <E extends Comparable<E>> int partition(E[] list, int first, int last) {
        // todo 通常取中位数作为元数据
        E pivot = list[first];
        int low = first + 1;
        int high = last;
        while (high > low) {
            // 左边第一个大于主元的元素
            while (low <= high && list[low].compareTo(pivot) <= 0)
                low++;
            // 右边第一个小于主元的元素
            while (low <= high && list[high].compareTo(pivot) > 0)
                high--;
            // swap左右两个元素
            if (high > low) {
                E temp = list[low];
                list[low] = list[high];
                list[high] = temp;
            }
        }

        // 整个数组交换完成以后，再从右边比较过的最后一个元素位置开始，找到第一个比主元小的元素
        while (high > first && list[high].compareTo(pivot) >= 0)
            high--;
        if (pivot.compareTo(list[high]) > 0) {
            // 找到了则交换
            list[first] = list[high];
            list[high] = pivot;
            System.out.println(Arrays.toString(list));
            return high;
        } else {
            // 否则返回主元
            return first;
        }
    }

    public static <E extends Comparable<E>> void heapSort(E[] list) {
        Heap<E> heap = new Heap(list);
        System.out.println(heap);
        for (E e: heap) {
            System.out.println(heap.extractMin());
        }
    }

    public static void main(String[] args) {
        Order.bubbleSort(new String[]{"sa", "va", "ebr", "me", "nbi", "rn"});
        Order.bubbleSort(new String[]{"sa", "va", "ebr", "me", "nbi", "rn"}, Comparator.naturalOrder());
        Order.mergeSort(new String[]{"sa", "va", "ebr", "me", "nbi", "rn"});
        Order.quickSort(new String[]{"sa", "va", "ebr", "me", "nbi", "rn"});
        Order.heapSort(new String[]{"sa", "va", "ebr", "me", "nbi", "rn"});
    }
}