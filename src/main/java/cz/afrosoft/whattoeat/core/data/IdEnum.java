package cz.afrosoft.whattoeat.core.data;

import org.apache.commons.lang3.Validate;

/**
 * Marks enum as enum with explicitly specified id. This id must be used to persis enum values in database.
 * This allows change of order for enum items or refactoring their name without breaking database consistency.
 *
 * @author Tomas Rejent
 */
public interface IdEnum {

    /**
     * Converts id of {@link IdEnum} to enum item.
     *
     * @param enumClass (NotNull) Class of enum which item is searched by id.
     * @param id        Id of enum item. Item with this id must exist in specified enumClass.
     * @param <T>       Type of Enum which will be obtained by id. Must implements {@link IdEnum}.
     * @return (NotNull) Enum item of specified class with given id.
     * @throws IllegalArgumentException When specified enum class does not contain item with given id.
     */
    static <T extends IdEnum> T valueOf(final Class<T> enumClass, final int id) {
        Validate.notNull(enumClass, "Cannot obtain enum item for null enumClass.");
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException(String.format("Class[%s] is not enum.", enumClass.getSimpleName()));
        }

        for (T enumItem : enumClass.getEnumConstants()) {
            if (enumItem.getId() == id) {
                return enumItem;
            }
        }

        throw new IllegalArgumentException(String.format("Enum[%s] does not contain item with id[%d].", enumClass.getSimpleName(), id));
    }

    /**
     * @return Id of enum type. Is unique within enum, not globally.
     */
    int getId();
}
