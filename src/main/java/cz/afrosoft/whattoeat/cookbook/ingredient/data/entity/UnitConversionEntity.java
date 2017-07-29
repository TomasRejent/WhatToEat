package cz.afrosoft.whattoeat.cookbook.ingredient.data.entity;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;

import javax.persistence.*;

/**
 * Entity for {@link UnitConversion}.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "UNIT_CONVERSION")
public class UnitConversionEntity implements UnitConversion {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "GRAMS_PER_PIECE")
    private Float gramsPerPiece;

    @Column(name = "MILLILITER_PER_GRAM")
    private Float milliliterPerGram;

    @Column(name = "GRAMS_PER_PINCH")
    private Float gramsPerPinch;

    @Column(name = "GRAMS_PER_COFFEE_SPOON")
    private Float gramsPerCoffeeSpoon;

    @Column(name = "GRAMS_PER_SPOON")
    private Float gramsPerSpoon;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public Float getGramsPerPiece() {
        return gramsPerPiece;
    }

    public void setGramsPerPiece(final Float gramsPerPiece) {
        this.gramsPerPiece = gramsPerPiece;
    }

    @Override
    public Float getMilliliterPerGram() {
        return milliliterPerGram;
    }

    public void setMilliliterPerGram(final Float milliliterPerGram) {
        this.milliliterPerGram = milliliterPerGram;
    }

    @Override
    public Float getGramsPerPinch() {
        return gramsPerPinch;
    }

    public void setGramsPerPinch(final Float gramsPerPinch) {
        this.gramsPerPinch = gramsPerPinch;
    }

    @Override
    public Float getGramsPerCoffeeSpoon() {
        return gramsPerCoffeeSpoon;
    }

    public void setGramsPerCoffeeSpoon(final Float gramsPerCoffeeSpoon) {
        this.gramsPerCoffeeSpoon = gramsPerCoffeeSpoon;
    }

    @Override
    public Float getGramsPerSpoon() {
        return gramsPerSpoon;
    }

    public void setGramsPerSpoon(final Float gramsPerSpoon) {
        this.gramsPerSpoon = gramsPerSpoon;
    }
}
