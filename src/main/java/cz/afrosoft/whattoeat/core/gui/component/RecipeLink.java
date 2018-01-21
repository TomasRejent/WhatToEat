package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

/**
 * Component which displays link for one recipe. Link displays name of recipe and when clicked it opens recipe view dialog.
 * <p>
 * This component can use {@link RecipeRef} so recipe is loaded only when link is clicked. Dialog is also created only when clicked.
 *
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/RecipeLink.fxml")
public class RecipeLink extends Label {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeLink.class);

    private final RecipeRef recipe;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RecipeService recipeService;

    public RecipeLink(final RecipeRef recipe) {
        Validate.notNull(recipe);

        this.recipe = recipe;
    }

    @PostConstruct
    private void initialize() {
        setText(recipe.getName());
        initOnClickListener();
    }

    private void initOnClickListener() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            LOGGER.debug("Recipe link clicked. Recipe {}", recipe);
            RecipeViewDialog dialog = applicationContext.getBean(RecipeViewDialog.class);
            Recipe loadedRecipe = recipeService.getRecipeById(recipe.getId());
            dialog.showRecipe(loadedRecipe);
        });
    }

}
