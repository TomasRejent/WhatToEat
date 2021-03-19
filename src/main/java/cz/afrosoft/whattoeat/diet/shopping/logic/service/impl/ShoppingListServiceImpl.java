package cz.afrosoft.whattoeat.diet.shopping.logic.service.impl;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItem;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItems;
import cz.afrosoft.whattoeat.diet.shopping.logic.service.ShoppingListService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Tomas Rejent
 */
@Service
class ShoppingListServiceImpl implements ShoppingListService {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    @Override
    public ShoppingItems createShoppingItems(final Collection<Meal> meals) {
        Validate.notNull(meals);

        ShoppingItemsImpl.Builder builder = new ShoppingItemsImpl.Builder();
        for (Meal meal : meals) {
            if(meal.getRecipe() != null){
                float servings = meal.getServings();
                Recipe recipe = recipeService.getRecipeById(meal.getRecipe().getId());
                Set<RecipeIngredientRef> ingredientRefs = recipe.getIngredients();
                Collection<RecipeIngredient> recipeIngredients = recipeService.loadRecipeIngredients(ingredientRefs);
                for (RecipeIngredient ingredient : recipeIngredients) {
                    builder.addItem(ingredient.getIngredient(), ingredient.getQuantity() * servings);
                }
            } else { // ingredient
                Ingredient ingredient = ingredientService.getById(meal.getIngredient().getId());
                builder.addItem(ingredient, (float) meal.getAmount());
            }
        }
        return builder.build();
    }

    @Override
    public String formatToSimpleText(final ShoppingItems shoppingItems) {
        StringBuilder stringBuilder = new StringBuilder();
        List<ShoppingItem> itemList = new ArrayList<>(shoppingItems.getItemList());
        // TODO create proper keyword sorting mechanism
        itemList.sort(new Comparator<ShoppingItem>() {
            @Override
            public int compare(final ShoppingItem o1, final ShoppingItem o2) {
                Set<Keyword> keywords1 = o1.getKeywords();
                Set<Keyword> keywords2 = o2.getKeywords();
                if (Objects.equals(keywords1, keywords2)) {
                    return I18n.compareStringsIgnoreCase(o1.getName(), o2.getName());
                } else if (keywords1.size() == 1 && keywords2.size() == 1) {
                    return I18n.compareStringsIgnoreCase(keywords1.iterator().next().getName(), keywords2.iterator().next().getName());
                } else {
                    return keywords1.size() - keywords2.size();
                }
            }
        });

        for (ShoppingItem item : itemList) {
            stringBuilder.append(String.format("%s (%s%s) [%s]\n",
                    item.getName(),
                    item.getQuantity(),
                    I18n.getText(item.getUnit().getLabelKey()),
                    StringUtils.join(item.getKeywords().stream().map(Keyword::getName).toArray(), ",")));
        }
        return stringBuilder.toString();
    }
}
