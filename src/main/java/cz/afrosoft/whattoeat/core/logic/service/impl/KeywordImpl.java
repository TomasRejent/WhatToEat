package cz.afrosoft.whattoeat.core.logic.service.impl;

import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Immutable and comparable implementation of {@link Keyword}. Natural ordering is based
 * on {@link Keyword#getName()}.
 * <p>
 * Is used by {@link KeywordServiceImpl} to provide {@link Keyword} instances for front end.
 *
 * @author Tomas Rejent
 */
final class KeywordImpl implements Keyword {

    private final Integer id;
    private final String name;

    private KeywordImpl(final Integer id, final String name) {
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
    public int compareTo(final Keyword otherKeyword) {
        return KeywordComparator.INSTANCE.compare(this, otherKeyword);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KeywordImpl keyword = (KeywordImpl) o;

        return new EqualsBuilder()
                .append(id, keyword.id)
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

    /**
     * Builder for {@link KeywordImpl} used by {@link KeywordServiceImpl}.
     */
    static final class Builder implements Keyword {

        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public Builder setId(final Integer id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Keyword build() {
            Validate.notNull(id);
            Validate.notBlank(name);

            return new KeywordImpl(id, name);
        }

        @Override
        public int compareTo(final Keyword otherKeyword) {
            return KeywordComparator.INSTANCE.compare(this, otherKeyword);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("id", id)
                    .append("name", name)
                    .toString();
        }
    }
}
