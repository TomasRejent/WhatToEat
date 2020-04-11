package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.ShopEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.data.repository.ShopRepository;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Shop;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.ShopService;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public Set<Shop> getAllShops() {
        List<ShopEntity> shops = shopRepository.findAll();
        return ConverterUtil.convertToSortedSet(shops, this::entityToShop);
    }

    @Override
    public Shop entityToShop(final ShopEntity shopEntity) {
        return new ShopImpl.Builder(shopEntity.getId())
                .setName(shopEntity.getName())
                .build();
    }

    @Override
    @Transactional
    public Set<Shop> createOrGetByNames(final Set<String> shopNames) {
        return shopNames.stream().map(
                shopName -> Optional.ofNullable(shopRepository.findByName(shopName)).orElseGet(
                        () -> shopRepository.save(new ShopEntity().setName(shopName))
                )
        ).map(this::entityToShop).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public ShopEntity shopToEntity(final Shop shop) {
        Validate.notNull(shop);

        return Optional.ofNullable(shop.getId())
                .map(id -> shopRepository.getOne(id))//id is present, get keyword for this id
                .orElse(//id is null, try to find existing keyword with same name
                        Optional.ofNullable(shopRepository.findByName(shop.getName()))
                                //no similar entity exist, so new is created with this name
                                .orElseGet(() -> shopRepository.save(new ShopEntity().setName(shop.getName())))
                );
    }
}
