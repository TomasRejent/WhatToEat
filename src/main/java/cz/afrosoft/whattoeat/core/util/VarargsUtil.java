package cz.afrosoft.whattoeat.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Tomas Rejent
 */
public final class VarargsUtil {

    public static <T> List<T> toList(final T[] array) {
        if (array == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(Arrays.asList(array));
        }
    }

    private VarargsUtil() {
        throw new IllegalStateException("This class cannot be instanced.");
    }
}
