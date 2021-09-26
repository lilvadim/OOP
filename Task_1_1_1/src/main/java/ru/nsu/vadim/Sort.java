package ru.nsu.vadim;

public class Sort {
    public static class Elem {
        int key;
        int value;
    }
    public static Elem[] heapSort(int[] array){
        Elem[] heapArr = new Elem[array.length];
        int[] size = {0};
        for (int j : array) {
            add(heapArr, size, j, 1);
        }
        Elem[] res = new Elem[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = extractMin(heapArr, size);
        }
        return res;
    }
    private static void swap (Elem[] heapArr, int parentIdx, int childIdx) {
        Elem temp = heapArr[parentIdx];
        heapArr[parentIdx] = heapArr[childIdx];
        heapArr[childIdx] = temp;
    }

    private static void siftUp (Elem[] heapArr, int childIdx) {
        if (childIdx == 0) return;
        int parentIdx = (childIdx - 1) / 2;

        if (heapArr[parentIdx].key > heapArr[childIdx].key) {
            swap(heapArr, parentIdx, childIdx);
            siftUp(heapArr, parentIdx);
        }
    }

    private static void siftDown(Elem[] heapArr, int size, int parentIdx) {
        int childLeft = parentIdx * 2 + 1;
        int childRight = parentIdx * 2 + 2;

        if ((parentIdx >= size - 1) || ((childLeft > size - 1) && (childRight > size - 1))) {
            return;
        }

        if ((childLeft <= size - 1) && (childRight <= size - 1)) {
            int minChildIdx;
            if (heapArr[childLeft].key > heapArr[childRight].key)
                minChildIdx = childRight;
            else
                minChildIdx = childLeft;

            if (heapArr[parentIdx].key > heapArr[minChildIdx].key) {
                swap(heapArr, parentIdx, minChildIdx);
                siftDown(heapArr, size, minChildIdx);
            }
        }
        else {
            int childIdx;
            if (childLeft <= size - 1)
                childIdx = childLeft;
            else childIdx = childRight;

            if (heapArr[parentIdx].key > heapArr[childIdx].key) {
                swap(heapArr, parentIdx, childIdx);
                siftDown(heapArr, size, childIdx);
            }
        }
    }

    public static void add (Elem[] heapArr, int[] size, int key, int value) {
        Elem newElem = new Elem();
        newElem.key = key;
        newElem.value = value;

        heapArr[size[0]++] = newElem;
        siftUp(heapArr, size[0] - 1);
    }

    public static Elem extractMin (Elem[] heapArr, int[] size) {
        Elem res = heapArr[0];
        size[0]--;
        swap(heapArr, 0, size[0]);
        siftDown(heapArr, size[0], 0);
        return res;
    }
}
