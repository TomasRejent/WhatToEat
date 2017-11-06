package cz.afrosoft.whattoeat.core.logic.model;

/**
 * Represents entity which can be identified by its id.
 *
 * @author Tomas Rejent
 */
public interface IdEntity {

    /**
     * @return (Nullable) Id of entity. Id is unique for entity type. May be null if entity is not persisted.
     */
    Integer getId();

}
