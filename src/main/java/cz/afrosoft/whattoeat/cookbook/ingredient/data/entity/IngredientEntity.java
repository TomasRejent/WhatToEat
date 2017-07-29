package cz.afrosoft.whattoeat.cookbook.ingredient.data.entity;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity for persisting {@link Ingredient}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "INGREDIENT")
public class IngredientEntity implements Ingredient {

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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final int id) {
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
    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(final IngredientUnit ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    @Override
    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    @Override
    public UnitConversionEntity getUnitConversion() {
        return unitConversion;
    }

    public void setUnitConversion(final UnitConversionEntity unitConversion) {
        this.unitConversion = unitConversion;
    }

    @Override
    public Set<? extends Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(final Set<KeywordEntity> keywords) {
        this.keywords = keywords;
    }
}
