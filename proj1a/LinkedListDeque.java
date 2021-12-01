public class LinkedListDeque<T> {

    private Node<T> sentinel;
    private int size;
    private Node<T> last;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<>();
        last = sentinel;
    }

    public LinkedListDeque(LinkedListDeque other) {
        this();
        Node<T> p = other.sentinel;
        while (p.next != null) {
            p = p.next;
            addLast(p.item);
        }
    }

    /**
     * Adds an item of type T to the front of the deque
     *
     * @param item
     */
    public void addFirst(T item) {
        Node<T> p = new Node<>(item, sentinel, sentinel.next);
        if (sentinel.next != null)
            sentinel.next.prev = p;
        sentinel.next = p;
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque
     *
     * @param item
     */
    public void addLast(T item) {
        Node<T> p = new Node<>(item, last, null);
        last.next = p;
        last = p;
        size += 1;
    }

    /**
     * @return true if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        Node<T> p = sentinel;
        while (p.next != null) {
            p = p.next;
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    /**
     * removes the node at the front of the deque
     *
     * @return the item at the front of the deque
     */
    public T removeFirst() {
        if (size == 0) return null;
        size -= 1;
        Node<T> p = sentinel.next;
        sentinel.next = p.next;
        if (p.next != null) {
            p.next.prev = sentinel;
        }
        return p.item;
    }

    /**
     * removes the node at the back of the deque
     *
     * @return the item at the back of the deque
     */
    public T removeLast() {
        if (last == sentinel) return null;
        size -= 1;
        T ret = last.item;
        Node<T> p = last.prev;
        p.next = null;
        last = p;
        return ret;
    }

    /**
     * @param index index of item
     * @return the item at the given index
     */
    public T get(int index) {
        if (index >= size) return null;
        Node<T> p = sentinel.next;
        for (int i = 0; i < index; i++, p = p.next) ;
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size) return null;
        return _getRecursive(sentinel.next, index);
    }

    private T _getRecursive(Node<T> p, int index) {
        if (index == 0) {
            return p.item;
        }
        return _getRecursive(p.next, index - 1);
    }

    private class Node<T> {
        Node<T> prev;
        T item;
        Node<T> next;

        public Node(T item, Node p, Node n) {
            this.item = item;
            prev = p;
            next = n;
        }

        public Node() {
            this(null);
        }

        public Node(T item) {
            this(item, null, null);
        }
    }
}

