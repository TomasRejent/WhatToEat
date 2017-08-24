package cz.afrosoft.whattoeat.cookbook.ingredient.data.entity;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity for persisting {@link Ingredient}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "INGREDIENT")
public class IngredientEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UNIT", nullable = false)
    private IngredientUnit ingredientUnit;

    @Column(name = "PRICE")
    private float price;

    @OneToOne
    private UnitConversionEntity unitConversion;

    @ManyToMany
    @JoinTable(name = "INGREDIENT_KEYWORDS")
    private Set<KeywordEntity> keywords;

    public Integer getId() {
        return id;
    }

    public IngredientEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IngredientEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public IngredientEntity setIngredientUnit(final IngredientUnit ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public IngredientEntity setPrice(final float price) {
        this.price = price;
        return this;
    }

    public UnitConversionEntity getUnitConversion() {
        return unitConversion;
    }

    public IngredientEntity setUnitConversion(final UnitConversionEntity unitConversion) {
        this.unitConversion = unitConversion;
        return this;
    }

    public Set<KeywordEntity> getKeywords() {
        return keywords;
    }

    public IngredientEntity setKeywords(final Set<KeywordEntity> keywords) {
        this.keywords = keywords;
        return this;
    }
}
