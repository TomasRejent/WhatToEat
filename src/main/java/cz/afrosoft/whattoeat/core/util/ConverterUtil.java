package cz.afrosoft.whattoeat.core.util;

import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides methods for converting collections with item type S to collection of item type T.
 *
 * @author Tomas Rejent
 */
public final class ConverterUtil {

    /**
     * Converts to set.
     *
     * @param source      (Nullable) Source collection to convert.
     * @param mapFunction (NotNull) Function for mapping source type to target type.
     * @param <S>         Type of item of source set.
     * @param <T>         Type to which source items should be converted.
     * @return (NotNull)(ReadOnly) Empty set if source is empty or null. Unmodifiable set with
     * items converted by map function otherwise.
     */
    public static <S, T> Set<T> convertToSet(final Collection<S> source, final Function<S, T> mapFunction) {
        Validate.notNull(mapFunction);
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(
                    source.stream().map(mapFunction).collect(Collectors.toSet())
            );
        }
    }

    /**
     * Converts to set which preserves order of elements, but does not sort them.
     *
     * @param source      (Nullable) Source collection to convert.
     * @param mapFunction (NotNull) Function for mapping source type to target type.
     * @param <S>         Type of item of source set.
     * @param <T>         Type to which source items should be converted. Must be Comparable so it can be sorted.
     * @return (NotNull)(ReadOnly) Empty set if source is empty or null. Unmodifiable set with
     * items converted by map function otherwise.
     */
    public static <S, T extends Comparable> Set<T> convertToLinkedSet(final Collection<S> source, final Function<S, T> mapFunction) {
        Validate.notNull(mapFunction);
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(
                    source.stream().map(mapFunction).collect(ImmutableLinkedHashSetCollector.newInstance())
            );
        }
    }

    /**
     * Converts to sorted set.
     *
     * @param source      (Nullable) Source collection to convert.
     * @param mapFunction (NotNull) Function for mapping source type to target type.
     * @param <S>         Type of item of source set.
     * @param <T>         Type to which source items should be converted. Must be Comparable so it can be sorted.
     * @return (NotNull)(ReadOnly) Empty set if source is empty or null. Unmodifiable set with
     * items converted by map function otherwise.
     */
    public static <S, T extends Comparable> Set<T> convertToSortedSet(final Collection<S> source, final Function<S, T> mapFunction) {
        Validate.notNull(mapFunction);
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(
                    source.stream().map(mapFunction).collect(ImmutableOrderedSetCollector.newInstance())
            );
        }
    }

    /**
     * Converts to list.
     *
     * @param source      (Nullable) Source collection to convert.
     * @param mapFunction (NotNull) Function for mapping source type to target type.
     * @param <S>         Type of item of source list.
     * @param <T>         Type to which source items should be converted.
     * @return (NotNull)(ReadOnly) Empty list if source is empty or null. Unmodifiable list with
     * items converted by map function otherwise.
     */
    public static <S, T> List<T> convertToList(final Collection<S> source, final Function<S, T> mapFunction) {
        Validate.notNull(mapFunction);
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(
                    source.stream().map(mapFunction).collect(Collectors.toList())
            );
        }
    }

    private ConverterUtil() {
        throw new IllegalStateException("This class cannot be instanced.");
    }

}
