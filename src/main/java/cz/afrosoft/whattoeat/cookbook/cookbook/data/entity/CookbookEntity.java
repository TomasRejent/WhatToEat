package cz.afrosoft.whattoeat.cookbook.cookbook.data.entity;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity for {@link Cookbook}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "COOKBOOK")
public class CookbookEntity implements Cookbook {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinTable(name = "COOKBOOK_RECIPES")
    private Set<RecipeEntity> recipes;

    @ManyToMany(targetEntity = AuthorEntity.class)
    @JoinTable(name = "COOKBOOK_AUTHORS")
    private Set<AuthorEntity> authors;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public Set<? extends Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(final Set<RecipeEntity> recipes) {
        this.recipes = recipes;
    }

    @Override
    public Set<? extends Author> getAuthors() {
        return authors;
    }

    public void setAuthors(final Set<AuthorEntity> authors) {
        this.authors = authors;
    }
}
