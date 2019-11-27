package lt.mif.unit.exercise1;

import java.util.List;
import java.util.Objects;

public class StringUtils {


    /**
     * Join a list of objects into a comma separated list.
     * It should skip the null values.
     *
     * @param objects A list of objects.
     * @return a comma separated list of values1
     * @throws NullPointerException if input is <c>null</c>
     */
    public static String toCommaSeparatedList(List<Object> objects) {
        Objects.requireNonNull(objects);
        StringBuilder joined = new StringBuilder();
        for (Object o : objects) {
            joined.append(o).append(", ");
        }
        joined.setLength(joined.length() - 2); // Remove the last two characters: ' ,'
        return joined.toString();
    }

}
