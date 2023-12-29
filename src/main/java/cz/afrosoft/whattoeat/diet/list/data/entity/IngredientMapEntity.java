package cz.afrosoft.whattoeat.diet.list.data.entity;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeIngredientEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.*;
import java.util.Map;

/**
 * Holds data which serves for materialization of abstract recipe ingredients to specific ingredients which can be physically obtained.
 * @author Tomas Rejent
 */
@Entity
@Table(name = "INGREDIENT_MAP")
public class IngredientMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @OneToMany
    private Map<RecipeIngredientEntity, RecipeIngredientEntity> ingredientMap;

    public Integer getId() {
        return id;
    }

    public IngredientMapEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public Map<RecipeIngredientEntity, RecipeIngredientEntity> getIngredientMap() {
        return ingredientMap;
    }

    public IngredientMapEntity setIngredientMap(final Map<RecipeIngredientEntity, RecipeIngredientEntity> ingredientMap) {
        this.ingredientMap = ingredientMap;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("ingredientMap", ingredientMap)
            .toString();
    }
}
