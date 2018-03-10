package cz.afrosoft.whattoeat.diet.list.gui.event;

import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import org.apache.commons.lang3.Validate;

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
