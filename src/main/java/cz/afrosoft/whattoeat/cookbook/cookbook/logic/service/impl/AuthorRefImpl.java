package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link AuthorRef} used to represent related cookbooks in other entities.
 *
 * @author Tomas Rejent
 */
final class AuthorRefImpl implements AuthorRef {

    private final Integer id;
    private final String name;

    AuthorRefImpl(final Integer id, final String name) {
        Validate.notNull(id);
        Validate.notNull(name);

        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(final AuthorRef otherAuthor) {
        return AuthorComparator.INSTANCE.compare(this, otherAuthor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
