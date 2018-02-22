package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;

import java.util.Comparator;

/**
 * @author Tomas Rejent
 */
public enum DietComparator implements Comparator<Diet> {
    INSTANCE;

    @Override
    public int compare(final Diet diet, final Diet otherDiet) {
        if (diet == otherDiet) {
            return 0;
        } else {
            int fromCompare = diet.getFrom().compareTo(otherDiet.getFrom());
            if (fromCompare == 0) {
                int toCompare = diet.getTo().compareTo(otherDiet.getTo());
                if (toCompare == 0) {
                    return I18n.compareStringsIgnoreCase(diet.getName(), otherDiet.getName());
                } else {
                    return toCompare;
                }
            } else {
                return fromCompare;
            }
        }
    }
}
