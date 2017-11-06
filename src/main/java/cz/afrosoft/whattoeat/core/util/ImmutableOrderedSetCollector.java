package cz.afrosoft.whattoeat.core.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Collect immutable set with natural ordering of items.
 *
 * @author Tomas Rejent
 */
public class ImmutableOrderedSetCollector<T> implements Collector<T, TreeSet<T>, Set<T>> {

    public static <T> ImmutableOrderedSetCollector<T> newInstance() {
        return new ImmutableOrderedSetCollector<>();
    }

    private ImmutableOrderedSetCollector() {
    }

    @Override
    public Supplier<TreeSet<T>> supplier() {
        return TreeSet::new;
    }

    @Override
    public BiConsumer<TreeSet<T>, T> accumulator() {
        return TreeSet::add;
    }

    @Override
    public BinaryOperator<TreeSet<T>> combiner() {
        return (firstSet, secondSet) -> {
            firstSet.addAll(secondSet);
            return firstSet;
        };
    }

    @Override
    public Function<TreeSet<T>, Set<T>> finisher() {
        return Collections::unmodifiableSet;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }
}
