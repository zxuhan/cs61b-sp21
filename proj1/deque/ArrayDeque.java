package deque;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private int capacity;
    private int size;
    private T[] arr;
    private int first;
    private int last;


    public ArrayDeque() {
        capacity = 8;
        size = 0;
        arr = (T[]) new Object[8];
        first = 0;
        last = first;
    }

    @Override
    public void addFirst(T item) {
        if (size == capacity) {
            enlargeArray();
        }

        if (size == 0) {
            first = 0;
            last = 0;
        } else {
            if (first == 0) {
                first = capacity - 1;
            } else {
                first -= 1;
            }
        }

        arr[first] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == capacity) {
            enlargeArray();
        }

        if (size == 0) {
            first = 0;
            last = 0;
        } else {
            if (last == capacity - 1) {
                last = 0;
            } else {
                last += 1;
            }
        }

        arr[last] = item;
        size += 1;
    }

    private void enlargeArray() {
        T[] arrNew = (T[]) new Object[capacity * 2];

        if (first < last) {
            System.arraycopy(arr, first, arrNew, 0, size);
        } else {
            int index = 0;
            for (int i = first; i < capacity; i += 1) {
                arrNew[index] = arr[i];
                index += 1;
            }

            for (int i = 0; i <= last; i += 1) {
                arrNew[index] = arr[i];
                index += 1;
            }
        }
        arr = arrNew;
        first = 0;
        last = first + size - 1;
        capacity *= 2;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StringBuilder sb = new StringBuilder();
        if (first < last) {

            for (int i = first; i <= last; i += 1) {
                if (i == last) {
                    sb.append(arr[i]);
                } else {
                    sb.append(arr[i]);
                    sb.append(" ");
                }
            }

        } else {

            for (int i = first; i < capacity; i += 1) {
                sb.append(arr[i]);
                sb.append(" ");
            }

            for (int i = 0; i <= last; i += 1 ) {
                if (i == last) {
                    sb.append(arr[i]);
                } else {
                    sb.append(arr[i]);
                    sb.append(" ");
                }
            }
        }

        String s = sb.toString();
        System.out.println(s);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (capacity >= 16 && (size - 1) * 4 < capacity) {
            shrinkArray();
        }


        T item = arr[first];
        if (first == capacity - 1) {
            first = 0;
        } else {
            first += 1;
        }
        size -= 1;

        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (capacity >= 16 && (size - 1) * 4 < capacity) {
            shrinkArray();
        }

        T item = arr[last];
        if (last == 0) {
            last = capacity - 1;
        } else {
            last -= 1;
        }
        size -= 1;

        return item;
    }

    private void shrinkArray() {
        T[] arrNew = (T[]) new Object[capacity / 2];

        if (first < last) {
            System.arraycopy(arr, first, arrNew, 0, size);
        } else {
            int index = 0;
            for (int i = first; i < capacity; i += 1) {
                arrNew[index] = arr[i];
                index += 1;
            }

            for (int i = 0; i <= last; i += 1) {
                arrNew[index] = arr[i];
            }
        }
        arr = arrNew;
        first = 0;
        last = first + size - 1;
        capacity /= 2;
    }

    @Override
    public T get(int index) {
        return arr[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new ADequeIterator();
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

    private class ADequeIterator implements Iterator<T> {
        private T[] simpleArray;
        private Iterator<T> itr;
        public ADequeIterator() {
            simpleArray = (T[]) new Object[size];
            if (first <= last) {
                System.arraycopy(arr, first, simpleArray, 0, size);
            } else {
                int index = 0;
                for (int i = first; i < capacity; i += 1) {
                    simpleArray[index] = arr[i];
                    index += 1;
                }

                for (int i = 0; i <= last; i += 1) {
                    simpleArray[index] = arr[i];
                    index += 1;
                }
            }
            itr = Arrays.stream(simpleArray).iterator();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public T next() {
            return itr.next();
        }
    }
}
