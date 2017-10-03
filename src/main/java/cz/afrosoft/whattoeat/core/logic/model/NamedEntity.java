package cz.afrosoft.whattoeat.core.logic.model;

/**
 * Marks entity as entity which has user readable name.
 *
 * @author Tomas Rejent
 */
public interface NamedEntity {

    /**
     * @return (NotEmpty) Returns user readable name of entity.
     */
    String getName();

}
