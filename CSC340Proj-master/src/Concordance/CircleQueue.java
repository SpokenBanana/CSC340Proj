package Concordance;

public class CircleQueue<T> {
    private T[] arr;
    private int index, size;

    public CircleQueue(int size) {
        this.arr = (T[]) new Object[size];
        this.size = 0;
        this.index = 0;
    }

    public void add(T item) {
        arr[index] = item;
        index = (index + 1) % arr.length;
        size++;
    }

    public T pop() {
        if (size == 0) return null;
        index = (((index-1) % arr.length) + arr.length) % arr.length;
        size--;
        return arr[index];
    }

    public boolean contains(String s) {
        for (T item : arr) {
            if (item != null && item.equals(arr))
                return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
