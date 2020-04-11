package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.ShopEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Shop;

import java.util.Set;

/**
 * @author Tomas Rejent
 */
public interface ShopService {

    Shop entityToShop (ShopEntity shopEntity);

    ShopEntity shopToEntity(Shop shop);

    Set<Shop> createOrGetByNames(Set<String> shopNames);

    Set<Shop> getAllShops();

}
