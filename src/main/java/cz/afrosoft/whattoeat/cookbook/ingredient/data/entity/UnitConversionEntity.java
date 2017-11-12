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
public class UnitConversionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getId() {
        return id;
    }

    public UnitConversionEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public Float getGramsPerPiece() {
        return gramsPerPiece;
    }

    public UnitConversionEntity setGramsPerPiece(final Float gramsPerPiece) {
        this.gramsPerPiece = gramsPerPiece;
        return this;
    }

    public Float getMilliliterPerGram() {
        return milliliterPerGram;
    }

    public UnitConversionEntity setMilliliterPerGram(final Float milliliterPerGram) {
        this.milliliterPerGram = milliliterPerGram;
        return this;
    }

    public Float getGramsPerPinch() {
        return gramsPerPinch;
    }

    public UnitConversionEntity setGramsPerPinch(final Float gramsPerPinch) {
        this.gramsPerPinch = gramsPerPinch;
        return this;
    }

    public Float getGramsPerCoffeeSpoon() {
        return gramsPerCoffeeSpoon;
    }

    public UnitConversionEntity setGramsPerCoffeeSpoon(final Float gramsPerCoffeeSpoon) {
        this.gramsPerCoffeeSpoon = gramsPerCoffeeSpoon;
        return this;
    }

    public Float getGramsPerSpoon() {
        return gramsPerSpoon;
    }

    public UnitConversionEntity setGramsPerSpoon(final Float gramsPerSpoon) {
        this.gramsPerSpoon = gramsPerSpoon;
        return this;
    }
}
