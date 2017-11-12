package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;
import java.util.Set;

public final class RecipeFilter {

    private final String name;
    private final Set<CookbookRef> cookbooks;
    private final Set<RecipeType> type;

    private RecipeFilter(final String name, final Set<CookbookRef> cookbooks, final Set<RecipeType> type) {
        this.name = name;
        this.cookbooks = cookbooks;
        this.type = type;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Set<CookbookRef>> getCookbooks() {
        return Optional.ofNullable(cookbooks);
    }

    public Optional<Set<RecipeType>> getType() {
        return Optional.ofNullable(type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("cookbooks", cookbooks)
                .append("type", type)
                .toString();
    }

    public static class Builder {

        private String name;
        private Set<CookbookRef> cookbooks;
        private Set<RecipeType> type;

        public String getName() {
            return name;
        }

        public Builder setName(final String name) {
            if (StringUtils.isBlank(name)) {
                this.name = null;
            } else {
                this.name = name;
            }
            return this;
        }

        public Set<CookbookRef> getCookbooks() {
            return cookbooks;
        }

        public Builder setCookbooks(final Set<CookbookRef> cookbooks) {
            if (cookbooks == null || cookbooks.isEmpty()) {
                this.cookbooks = null;
            } else {
                this.cookbooks = cookbooks;
            }
            return this;
        }

        public Set<RecipeType> getType() {
            return type;
        }

        public Builder setType(final Set<RecipeType> type) {
            if (type == null || type.isEmpty()) {
                this.type = null;
            } else {
                this.type = type;
            }
            return this;
        }

        public RecipeFilter build() {
            return new RecipeFilter(name, cookbooks, type);
        }
    }
}
