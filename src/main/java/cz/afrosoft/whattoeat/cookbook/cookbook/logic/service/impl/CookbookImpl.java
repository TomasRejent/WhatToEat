package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookUpdateObject;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

/**
 * Immutable and comparable implementation of {@link Cookbook}. Natural ordering is based
 * on {@link Cookbook#getName()}.
 * <p>
 * It is used by {@link CookbookServiceImpl} to provide {@link Cookbook} instances for front end.
 *
 * @author Tomas Rejent
 */
final class CookbookImpl implements Cookbook, Comparable<Cookbook> {

    private final Integer id;
    private final String name;
    private final String description;
    private final Set<Author> authors;
    private final Set<Recipe> recipes;

    private CookbookImpl(final Integer id, final String name, final String description, final Set<Author> authors, final Set<Recipe> recipes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.recipes = recipes;
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
    public String getDescription() {
        return description;
    }

    @Override
    public Set<Author> getAuthors() {
        return authors;
    }

    @Override
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public int compareTo(final Cookbook otherCookbook) {
        return CookbookComparator.INSTANCE.compare(this, otherCookbook);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Cookbook)) {
            return false;
        }

        Cookbook cookbook = (Cookbook) o;

        return new EqualsBuilder()
                .append(id, cookbook.getId())
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
     * Builder for {@link CookbookImpl} and also implementation of {@link CookbookUpdateObject}
     * used by {@link CookbookServiceImpl}.
     */
    static final class Builder implements CookbookUpdateObject {

        private Integer id;
        private String name;
        private String description;
        private Set<Author> authors = new HashSet<>();
        private Set<Recipe> recipes = new HashSet<>();

        @Override
        public Integer getId() {
            return id;
        }

        public Builder setId(final Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        @Override
        public Set<Author> getAuthors() {
            return authors;
        }

        public Builder setAuthors(final Set<Author> authors) {
            this.authors = authors;
            return this;
        }

        @Override
        public Set<Recipe> getRecipes() {
            return recipes;
        }

        public Builder setRecipes(final Set<Recipe> recipes) {
            this.recipes = recipes;
            return this;
        }

        Cookbook build() {
            Validate.notNull(id);
            return new CookbookImpl(id, name, description, authors, recipes);
        }

        @Override
        public int compareTo(final Cookbook otherCookbook) {
            return CookbookComparator.INSTANCE.compare(this, otherCookbook);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("id", id)
                    .append("name", name)
                    .append("description", description)
                    .append("authors", authors)
                    .append("recipes", recipes)
                    .toString();
        }
    }
}
