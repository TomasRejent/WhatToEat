package cz.afrosoft.whattoeat.cookbook.ingredient.data.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * Entity for {@link cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "NUTRITION_FACTS")
public class NutritionFactsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ENERGY")
    private Float energy;

    @Column(name = "FAT")
    private Float fat;

    @Column(name = "SATURATED_FAT")
    private Float saturatedFat;

    @Column(name = "CARBOHYDRATE")
    private Float carbohydrate;

    @Column(name = "SUGAR")
    private Float sugar;

    @Column(name = "PROTEIN")
    private Float protein;

    @Column(name = "SALT")
    private Float salt;

    @Column(name = "FIBER")
    private Float fiber;

    public Integer getId() {
        return id;
    }

    public NutritionFactsEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public Float getEnergy() {
        return energy;
    }

    public NutritionFactsEntity setEnergy(final Float energy) {
        this.energy = energy;
        return this;
    }

    public Float getFat() {
        return fat;
    }

    public NutritionFactsEntity setFat(final Float fat) {
        this.fat = fat;
        return this;
    }

    public Float getSaturatedFat() {
        return saturatedFat;
    }

    public NutritionFactsEntity setSaturatedFat(final Float saturatedFat) {
        this.saturatedFat = saturatedFat;
        return this;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public NutritionFactsEntity setCarbohydrate(final Float carbohydrate) {
        this.carbohydrate = carbohydrate;
        return this;
    }

    public Float getSugar() {
        return sugar;
    }

    public NutritionFactsEntity setSugar(final Float sugar) {
        this.sugar = sugar;
        return this;
    }

    public Float getProtein() {
        return protein;
    }

    public NutritionFactsEntity setProtein(final Float protein) {
        this.protein = protein;
        return this;
    }

    public Float getSalt() {
        return salt;
    }

    public NutritionFactsEntity setSalt(final Float salt) {
        this.salt = salt;
        return this;
    }

    public Float getFiber() {
        return fiber;
    }

    public NutritionFactsEntity setFiber(final Float fiber) {
        this.fiber = fiber;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .toString();
    }
}
