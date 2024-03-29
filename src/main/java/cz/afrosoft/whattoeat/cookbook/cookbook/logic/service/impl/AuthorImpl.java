package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorUpdateObject;

/**
 * Immutable and comparable implementation of {@link Author}. Natural ordering is based
 * on {@link Author#getName()}.
 * <p>
 * Is used by {@link AuthorServiceImpl} to provide {@link Author} instances for front end.
 *
 * @author Tomas Rejent
 */
final class AuthorImpl implements Author {

    private final Integer id;
    private final String name;
    private final String email;
    private final String description;
    private final Set<CookbookRef> cookbooks;

    private AuthorImpl(final Integer id, final String name, final String email, final String description, final Set<CookbookRef> cookbooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.cookbooks = Collections.unmodifiableSet(cookbooks);
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
    public String getEmail() {
        return email;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Set<CookbookRef> getCookbooks() {
        return cookbooks;
    }

    @Override
    public int compareTo(final AuthorRef otherAuthor) {
        return AuthorComparator.INSTANCE.compare(this, otherAuthor);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Author)) {
            return false;
        }

        Author author = (Author) o;

        return new EqualsBuilder()
                .append(id, author.getId())
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
     * Builder for {@link AuthorImpl} and also implementation of {@link AuthorUpdateObject}
     * used by {@link AuthorServiceImpl}.
     */
    static final class Builder implements AuthorUpdateObject {

        private final Integer id;
        private String name;
        private String email;
        private String description;
        private Set<CookbookRef> cookbooks = new HashSet<>();

        public Builder() {
            this.id = null;
        }

        public Builder(final Integer id) {
            Validate.notNull(id);
            this.id = id;
        }

        @Override
        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        @Override
        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public Optional<String> getEmail() {
            return Optional.ofNullable(email);
        }

        @Override
        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public Builder setName(final String name) {
            Validate.notBlank(name);
            this.name = name;
            return this;
        }

        @Override
        public Builder setEmail(final String email) {
            this.email = email;
            return this;
        }

        @Override
        public Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder setExistingCookbooks(final Set<CookbookRef> cookbooks) {
            Validate.noNullElements(cookbooks);

            this.cookbooks = cookbooks;
            return this;
        }

        Author build() {
            Validate.notNull(id);
            Validate.notBlank(name);

            return new AuthorImpl(id, name, email, description, cookbooks);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("id", id)
                    .append("name", name)
                    .append("email", email)
                    .append("description", description)
                    .append("cookbooks", cookbooks)
                    .toString();
        }
    }
}
