package lt.mif.unit.exercise1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simple unit testing.
 * (https://junit.org/junit5/docs/5.5.2/api/org/junit/jupiter/api/Assertions.html)
 * <p>
 * These tests should check method return values depending on varying parameters.
 */
class StringUtilsTest {






    @Test
    void toCommaSeparatedList() {
        assertEquals("1, 3", StringUtils.toCommaSeparatedList(Arrays.asList(1, 3)));
    }
}