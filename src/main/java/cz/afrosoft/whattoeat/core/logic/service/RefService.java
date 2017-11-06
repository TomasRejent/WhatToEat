package cz.afrosoft.whattoeat.core.logic.service;

/**
 * Interface for services for handling referenced entities.
 *
 * @param <R> Type of entity reference. Reference is used to represent related entities so cyclic loading is avoided.
 *            Reference provides only limited subset of attributes and does not contain any other related entities.
 *            It always contains id and usually contains name.
 * @param <E> Type of entity.
 */
public interface RefService<R, E> {

    /**
     * Converts entity to reference.
     *
     * @param entity (NotNull) Entity to convert. Entity must have filled id.
     * @return (NotNull) Object which serves as reference to entity.
     */
    R fromEntity(E entity);

    /**
     * Converts reference to entity.
     *
     * @param reference (NotNull) Reference with filled id.
     * @return (NotNull) Entity corresponding to reference.
     */
    E toEntity(R reference);

}
