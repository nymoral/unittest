package lt.mif.unit.exercise1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NumberUtils {

    /**
     * Calculate the median of a collection of numbers.
     * Median is a middle element (or an average of the middle two) in a sorted list of number.
     *
     * @param numbers a list of numbers
     * @return median of given numbers
     */
    public static int median(Collection<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Calculation of a median requires at least one number");
        }
        List<Integer> ordered = new ArrayList<>(numbers);
        // It doesn't matter which way the numbers are ordered as the middle element is the same regardless.
        Collections.sort(ordered);
        return ordered.get(ordered.size() / 2);
    }

    /**
     * Calculate an average of a collection of numbers.
     * Average is the sum of all the numbers divided by the number of them.
     *
     * @param numbers a list of numbers
     * @return average of the numbers
     */
    public static int average(Collection<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Calculation of a median requires at least one number");
        }
        int sum = 0;
        for (Integer n : numbers) {
            sum += n;
        }
        return sum / numbers.size();
    }
}
