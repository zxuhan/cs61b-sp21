package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private Node sentinelFirst;
    private Node sentinelLast;

    public LinkedListDeque() {
        size = 0;
        sentinelFirst = new Node();
        sentinelLast = new Node();
        sentinelFirst.next = sentinelLast;
        sentinelLast.prev = sentinelFirst;
    }

    @Override
    public void addFirst(T item) {
        Node node = new Node(item);
        node.next = sentinelFirst.next;
        node.prev = sentinelFirst;

        sentinelFirst.next.prev = node;
        sentinelFirst.next = node;

        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node node = new Node(item);
        node.prev = sentinelLast.prev;
        node.next = sentinelLast;

        sentinelLast.prev.next = node;
        sentinelLast.prev = node;

        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StringBuilder sb = new StringBuilder();

        Node node = sentinelFirst.next;
        for (int i = 0; i < size; i += 1) {
            if (i == size - 1) {
                sb.append(node.item);
                break;
            }

            sb.append(node.item);
            sb.append(" ");
            node = node.next;
        }

        String s = sb.toString();
        System.out.println(s);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node node = sentinelFirst.next;

        sentinelFirst.next.next.prev = sentinelFirst;
        sentinelFirst.next = sentinelFirst.next.next;

        size -= 1;
        return node.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        Node node = sentinelLast.prev;

        sentinelLast.prev.prev.next = sentinelLast;
        sentinelLast.prev = sentinelLast.prev.prev;

        size -= 1;
        return node.item;
    }

    @Override
    public T get(int index) {
        if (size == 0 || index < 0 || index > size - 1) {
            return null;
        }

        Node curr = sentinelFirst.next;

        while (index > 0) {
            curr = curr.next;
            index -= 1;
        }

        return curr.item;
    }

    public T getRecursive(int index) {
        if (size == 0 || index < 0 || index > size - 1) {
            return null;
        }

        return getHelper(index, sentinelFirst.next);
    }

    private T getHelper(int index, Node node) {
        if (index == 0) {
            return node.item;
        }

        return getHelper(index - 1, node.next);
    }

    @Override
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> obj = (Deque<T>) o;
        if (obj.size() != size) {
            return false;
        }

        Iterator<T> itrObj = obj.iterator();
        Iterator<T> itrThis = iterator();

        while (itrObj.hasNext() && itrThis.hasNext()) {
            if (!itrObj.next().equals(itrThis.next())) {
                return false;
            }
        }

        return true;
    }


    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node() {
            prev = null;
            item = null;
            next = null;
        }

        public Node(T t) {
            prev = null;
            item = t;
            next = null;
        }
    }

    private class LLDequeIterator implements Iterator<T> {
        private int count;
        private Node node;

        public LLDequeIterator() {
            count = 0;
            node = sentinelFirst.next;
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            T t = node.item;
            count += 1;
            node = node.next;
            return t;
        }

    }
}
