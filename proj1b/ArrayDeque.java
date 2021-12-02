public class ArrayDeque<T> implements Deque<T> {
    private int front;
    private int rear;
    private T[] items;

    public ArrayDeque() {
        front = rear = 0;
        items = (T[]) new Object[8];
    }


    /**
     * adds an item of type T to the front of the deque
     */
    @Override
    public void addFirst(T item) {
        if (size() == items.length - 1) {
            resize(2 * items.length);
        }
        front = (front + items.length - 1) % items.length;
        items[front] = item;
    }

    /**
     * adds an item of type T to the back of the deque
     */
    @Override
    public void addLast(T item) {
        if (size() == items.length - 1) {
            resize(2 * items.length);
        }
        items[rear] = item;
        rear = (rear + 1) % items.length;
    }

    /**
     * prints the items in the deque
     */
    @Override
    public void printDeque() {
        for (int i = front;
             i != rear;
             i = (i + 1) % items.length) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    /**
     * removes and returns the item at the front of the deque
     */
    @Override
    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        T ret = items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        if (size() < items.length / 4 && items.length / 2 >= 8) {
            resize(items.length / 2);
        }
        return ret;
    }

    /**
     * removes and returns the item at the back of the deque
     */
    @Override
    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        rear = (rear + items.length - 1) % items.length;
        T ret = items[rear];
        items[rear] = null;
        if (size() < items.length / 4
                && items.length / 2 >= 8) {
            resize(items.length / 2);
        }
        return ret;
    }

    @Override
    public T get(int i) {
        if (i >= size()) {
            return null;
        }
        return items[(front + i) % items.length];
    }

    /**
     * size of deque
     */
    @Override
    public int size() {
        return (rear - front + items.length) % items.length;
    }

    /**
     * @return true if deque is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void resize(int newCap) {
        T[] newData = (T[]) new Object[newCap];
        int m = Math.min(newCap, size());
        for (int i = 0; i < m; i++) {
            newData[i] = items[(front + i) % items.length];
        }
        front = 0;
        rear = m;
        items = newData;
    }
}
