package cz.afrosoft.whattoeat.cookbook.cookbook.logic.service;

import cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef;
import cz.afrosoft.whattoeat.core.logic.service.RefService;

/**
 * Provides methods for conversions between {@link cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.AuthorRef} and
 * {@link cz.afrosoft.whattoeat.cookbook.cookbook.data.entity.AuthorEntity}.
 */
public interface AuthorRefService extends RefService<AuthorRef, AuthorEntity> {

}
