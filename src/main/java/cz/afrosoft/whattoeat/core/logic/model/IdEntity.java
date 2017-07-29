package cz.afrosoft.whattoeat.core.logic.model;

/**
 * Represents entity which can be identified by its id.
 *
 * @author Tomas Rejent
 */
public interface IdEntity {

    /**
     * @return Id of entity. Id is unique for entity type.
     */
    Integer getId();

}
