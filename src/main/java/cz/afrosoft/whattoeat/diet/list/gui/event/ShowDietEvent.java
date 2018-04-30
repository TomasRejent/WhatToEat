package cz.afrosoft.whattoeat.diet.list.gui.event;

import org.apache.commons.lang3.Validate;

import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;

/**
 * @author Tomas Rejent
 */
public class ShowDietEvent {

    private final Diet diet;

    public ShowDietEvent(final Diet diet) {
        Validate.notNull(diet);
        this.diet = diet;
    }

    public Diet getDiet() {
        return diet;
    }
}
