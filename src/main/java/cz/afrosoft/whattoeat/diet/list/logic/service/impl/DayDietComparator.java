package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import java.util.Comparator;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;

public enum DayDietComparator implements Comparator<DayDietRef> {
    INSTANCE;

    @Override
    public int compare(final DayDietRef dayDiet, final DayDietRef otherDiet) {
        if (dayDiet == otherDiet) {
            return 0;
        } else {
            return dayDiet.getDay().compareTo(otherDiet.getDay());
        }
    }
}
