package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest {

    // Integer comparator for ascending order
    public static class IntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return Integer.compare(a, b);
        }
    }

    // Integer comparator for descending order (reverse)
    public static class ReverseIntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return Integer.compare(b, a);
        }
    }

    // String length comparator
    public static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return Integer.compare(a.length(), b.length());
        }
    }

    // Lexicographic string comparator
    public static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    @Test
    public void testMaxWithEmptyDeque() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        assertNull("Max of empty deque should be null", mad.max());
        assertNull("Max with custom comparator of empty deque should be null",
                mad.max(new ReverseIntegerComparator()));
    }

    @Test
    public void testMaxWithSingleElement() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        mad.addFirst(5);

        assertEquals("Max of single element should be that element",
                Integer.valueOf(5), mad.max());
        assertEquals("Max with custom comparator of single element should be that element",
                Integer.valueOf(5), mad.max(new ReverseIntegerComparator()));
    }

    @Test
    public void testMaxWithMultipleElements() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        mad.addLast(3);
        mad.addLast(1);
        mad.addLast(4);
        mad.addLast(1);
        mad.addLast(5);
        mad.addLast(9);

        assertEquals("Max should be 9", Integer.valueOf(9), mad.max());
    }

    @Test
    public void testMaxWithDifferentComparators() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        mad.addLast(3);
        mad.addLast(1);
        mad.addLast(4);
        mad.addLast(5);

        // Using default comparator (ascending)
        assertEquals("Max with default comparator should be 5",
                Integer.valueOf(5), mad.max());

        // Using reverse comparator
        assertEquals("Max with reverse comparator should be 1",
                Integer.valueOf(1), mad.max(new ReverseIntegerComparator()));
    }

    @Test
    public void testMaxWithStringsByLength() {
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(new StringLengthComparator());
        mad.addLast("hi");
        mad.addLast("hello");
        mad.addLast("programming");
        mad.addLast("a");

        assertEquals("Max by length should be 'programming'",
                "programming", mad.max());
    }

    @Test
    public void testMaxWithStringsByLexicographic() {
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(new StringLengthComparator());
        mad.addLast("apple");
        mad.addLast("banana");
        mad.addLast("cherry");
        mad.addLast("date");

        // Using lexicographic comparator instead of length
        assertEquals("Max lexicographically should be 'date'",
                "date", mad.max(new StringComparator()));

        // Using default length comparator
        assertEquals("Max by length should be 'banana' or 'cherry'",
                true, mad.max().equals("banana") || mad.max().equals("cherry"));
    }

    @Test
    public void testMaxAfterRemoval() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        mad.addLast(1);
        mad.addLast(5);
        mad.addLast(3);

        assertEquals("Initial max should be 5", Integer.valueOf(5), mad.max());

        mad.removeLast(); // removes 3
        assertEquals("Max after removing 3 should still be 5",
                Integer.valueOf(5), mad.max());

        mad.removeFirst(); // removes 1
        assertEquals("Max after removing 1 should still be 5",
                Integer.valueOf(5), mad.max());

        mad.removeFirst(); // removes 5
        assertEquals("Max after removing 5 should be 3... wait, we removed 3 first",
                null, mad.max());
    }

    @Test
    public void testMaxAfterRemovalCorrectSequence() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        mad.addLast(1);
        mad.addLast(5);
        mad.addLast(3);
        mad.addLast(2);

        assertEquals("Initial max should be 5", Integer.valueOf(5), mad.max());

        // Remove elements until max element is removed
        mad.removeLast(); // removes 2
        assertEquals("After removing 2, max should be 5", Integer.valueOf(5), mad.max());

        mad.removeLast(); // removes 3
        assertEquals("After removing 3, max should be 5", Integer.valueOf(5), mad.max());

        mad.removeFirst(); // removes 1
        assertEquals("After removing 1, max should be 5", Integer.valueOf(5), mad.max());

        mad.removeFirst(); // removes 5
        assertNull("After removing 5 (the max), deque should be empty", mad.max());
    }

    @Test
    public void testInheritedFunctionality() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());

        // Test that all ArrayDeque methods still work
        assertTrue("Should be empty initially", mad.isEmpty());
        assertEquals("Size should be 0 initially", 0, mad.size());

        mad.addFirst(1);
        mad.addLast(2);
        assertEquals("Size should be 2", 2, mad.size());
        assertFalse("Should not be empty", mad.isEmpty());

        assertEquals("Get(0) should return 1", Integer.valueOf(1), mad.get(0));
        assertEquals("Get(1) should return 2", Integer.valueOf(2), mad.get(1));

        assertEquals("RemoveFirst should return 1", Integer.valueOf(1), mad.removeFirst());
        assertEquals("RemoveLast should return 2", Integer.valueOf(2), mad.removeLast());
        assertTrue("Should be empty after removing all", mad.isEmpty());
    }

    @Test
    public void testMaxWithDuplicateMaxElements() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntegerComparator());
        mad.addLast(5);
        mad.addLast(3);
        mad.addLast(5);
        mad.addLast(1);
        mad.addLast(5);

        // All three 5's are max, any one is acceptable
        assertEquals("Max should be 5", Integer.valueOf(5), mad.max());
    }
}