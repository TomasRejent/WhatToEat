package cz.afrosoft.whattoeat.cookbook.cookbook.data.entity;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity for {@link Cookbook}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "COOKBOOK")
public class CookbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition = "CLOB")
    private String description;

    @ManyToMany(mappedBy = "cookbooks")
    private Set<RecipeEntity> recipes;

    @ManyToMany(targetEntity = AuthorEntity.class)
    @JoinTable(name = "COOKBOOK_AUTHORS")
    private Set<AuthorEntity> authors;

    public Integer getId() {
        return id;
    }

    public CookbookEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CookbookEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CookbookEntity setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Set<RecipeEntity> getRecipes() {
        return recipes;
    }

    public CookbookEntity setRecipes(final Set<RecipeEntity> recipes) {
        this.recipes = recipes;
        return this;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public CookbookEntity setAuthors(final Set<AuthorEntity> authors) {
        this.authors = authors;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
