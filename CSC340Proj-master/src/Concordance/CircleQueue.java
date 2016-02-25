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
        if (size > arr.length) size = arr.length;
    }

    public T pop() {
        if (size == 0) return null;
        index = (((index-1) % arr.length) + arr.length) % arr.length;
        size--;
        return arr[index];
    }

    public boolean contains(String s) {
        for (T item : arr) {
            if (item != null && item.equals(s))
                return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == arr.length;
    }

    public String asString() {
        String result = "";
        int position = index;
        int stop = (((index-1) % arr.length) + arr.length) % arr.length;
        while (position != stop) {
            if (arr[position] != null)
                result += arr[position] + " ";
            position = (position + 1) % arr.length;
        }
        return result.trim();
    }

}
