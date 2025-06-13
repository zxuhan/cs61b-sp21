package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comp = c;
    }

    public T max() {
        return max(comp);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        T item = get(0);

        for (int i = 1; i < this.size(); i += 1) {
            T currentItem = get(i);
            if (c.compare(item, currentItem) < 0) {
                item = currentItem;
            }
        }

        return item;
    }


}
