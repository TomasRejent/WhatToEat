package cz.afrosoft.whattoeat.cookbook.ingredient.data.entity;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.core.data.entity.KeywordEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entity for persisting {@link Ingredient}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "INGREDIENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"NAME", "MANUFACTURER"})
})
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UNIT", nullable = false)
    private IngredientUnit ingredientUnit;

    @Column(name = "PRICE")
    private float price;

    @OneToOne(cascade = CascadeType.ALL)
    private UnitConversionEntity unitConversion;

    @OneToOne(cascade = CascadeType.ALL)
    private NutritionFactsEntity nutritionFacts;

    @ManyToMany
    @JoinTable(name = "INGREDIENT_KEYWORDS")
    private Set<KeywordEntity> keywords;

    @Column(name = "GENERAL", columnDefinition = "boolean default false")
    private boolean general;

    @Column(name = "EDIBLE", columnDefinition = "boolean default false")
    private boolean edible;

    @Column(name = "PURCHASABLE", columnDefinition = "boolean default true")
    private boolean purchasable;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @ManyToMany
    @JoinTable(name = "INGREDIENT_SHOPS")
    private Set<ShopEntity> shops;

    @OneToOne
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "PARENT")
    private IngredientEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<IngredientEntity> children;

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

    public NutritionFactsEntity getNutritionFacts(){
        return nutritionFacts;
    }

    public IngredientEntity setNutritionFacts(final NutritionFactsEntity nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
        return this;
    }

    public Set<KeywordEntity> getKeywords() {
        return keywords;
    }

    public IngredientEntity setKeywords(final Set<KeywordEntity> keywords) {
        this.keywords = keywords;
        return this;
    }

    public boolean isGeneral() {
        return general;
    }

    public IngredientEntity setGeneral(final boolean general) {
        this.general = general;
        return this;
    }

    public boolean isEdible() {
        return edible;
    }

    public IngredientEntity setEdible(final boolean edible) {
        this.edible = edible;
        return this;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public IngredientEntity setPurchasable(final boolean purchasable) {
        this.purchasable = purchasable;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public IngredientEntity setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public Set<ShopEntity> getShops() {
        return shops;
    }

    public IngredientEntity setShops(final Set<ShopEntity> shops) {
        this.shops = shops;
        return this;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public IngredientEntity setRecipe(final RecipeEntity recipe) {
        this.recipe = recipe;
        return this;
    }

    public IngredientEntity getParent() {
        return parent;
    }

    public IngredientEntity setParent(final IngredientEntity parent) {
        this.parent = parent;
        return this;
    }

    public Set<IngredientEntity> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
