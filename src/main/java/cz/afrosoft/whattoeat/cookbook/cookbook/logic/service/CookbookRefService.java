package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.core.logic.service.RefService;

/**
 * Provides methods for conversions between {@link cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef} and
 * {@link cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.CookbookEntity}.
 */
public interface CookbookRefService extends RefService<CookbookRef, CookbookEntity> {

}
