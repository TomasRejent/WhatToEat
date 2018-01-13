package cz.afrosoft.whattoeat.core.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Collect immutable set which preserves order of items.
 *
 * @author Tomas Rejent
 */
public class ImmutableLinkedHashSetCollector<T> implements Collector<T, LinkedHashSet<T>, Set<T>> {

    public static <T> ImmutableLinkedHashSetCollector<T> newInstance() {
        return new ImmutableLinkedHashSetCollector<>();
    }

    private ImmutableLinkedHashSetCollector() {
    }

    @Override
    public Supplier<LinkedHashSet<T>> supplier() {
        return LinkedHashSet::new;
    }

    @Override
    public BiConsumer<LinkedHashSet<T>, T> accumulator() {
        return LinkedHashSet::add;
    }

    @Override
    public BinaryOperator<LinkedHashSet<T>> combiner() {
        return (firstSet, secondSet) -> {
            firstSet.addAll(secondSet);
            return firstSet;
        };
    }

    @Override
    public Function<LinkedHashSet<T>, Set<T>> finisher() {
        return Collections::unmodifiableSet;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.noneOf(Characteristics.class);
    }
}
