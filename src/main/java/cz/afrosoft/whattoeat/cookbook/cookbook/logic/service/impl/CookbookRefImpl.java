package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable implementation of {@link CookbookRef} used to represent related cookbooks in other entities.
 *
 * @author Tomas Rejent
 */
final class CookbookRefImpl implements CookbookRef {

    private final Integer id;
    private final String name;

    CookbookRefImpl(final Integer id, final String name) {
        Validate.notNull(id);
        Validate.notBlank(name);

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
    public int compareTo(final CookbookRef otherCookbook) {
        return CookbookComparator.INSTANCE.compare(this, otherCookbook);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CookbookRefImpl that = (CookbookRefImpl) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
