package cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeUpdateObject;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Optional;

/**
 * Dialog for adding and editing of recipes. This dialog also allows to change relation between recipe and cookbooks.
 * <p>
 * This dialog must be modal because controller using it has only one instance of this dialog.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecipeAddDialog extends CustomDialog<RecipeUpdateObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeAddDialog.class);

    private static final String DIALOG_FXML = "/fxml/RecipeAddDialog.fxml";

    /**
     * Title message displayed when dialog is in add mode.
     */
    private static final String ADD_TITLE_KEY = "cz.afrosoft.whattoeat.recipe.add.title";
    /**
     * Title message displayed when dialog is in edit mode.
     */
    private static final String EDIT_TITLE_KEY = "cz.afrosoft.whattoeat.recipe.edit.title";

    private static final String I18N_NAME = "cz.afrosoft.whattoeat.recipes.add.name";
    private static final String I18N_TYPE = "cz.afrosoft.whattoeat.recipes.add.type";
    private static final String I18N_TASTE = "cz.afrosoft.whattoeat.recipes.add.taste";
    private static final String I18N_TIME = "cz.afrosoft.whattoeat.recipes.add.time";
    private static final String I18N_RATING = "cz.afrosoft.whattoeat.recipes.add.rating";
    private static final String I18N_KEYWORDS = "cz.afrosoft.whattoeat.recipes.add.keywords";
    private static final String I18N_INGREDIENTS = "cz.afrosoft.whattoeat.recipes.add.ingredients";
    private static final String I18N_INGREDIENTS_NAME = "cz.afrosoft.whattoeat.recipes.add.ingredients.name";
    private static final String I18N_INGREDIENTS_QUANTITY = "cz.afrosoft.whattoeat.recipes.add.ingredients.quantity";
    private static final String I18N_PREPARATION = "cz.afrosoft.whattoeat.recipes.add.preparation";
    private static final String I18N_SIDE_DISHES = "cz.afrosoft.whattoeat.recipes.add.sideDishes";
    private static final String I18N_SIDE_DISH_NAME = "cz.afrosoft.whattoeat.recipes.add.sideDishes.name";

    private static final int MAX_RATING = 10;

    @FXML
    private GridPane dialogContainer;

    @FXML
    private TextField nameField;

    @Autowired
    private RecipeService recipeService;

    /**
     * Holds createOrUpdate object when creating or editing recipe.
     */
    private RecipeUpdateObject recipeUpdateObject;

    /**
     * Creates new dialog. This constructor must be used only by Spring, because dependencies must be autowired to created instance.
     */
    public RecipeAddDialog() {
        super(DIALOG_FXML);
        getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setupResultConverter();
    }

    /**
     * Creates and sets result converter which handles dialog button events.
     */
    private void setupResultConverter() {
        this.setResultConverter((buttonType) -> {
            if (ButtonType.FINISH.equals(buttonType)) {
                return fillUpdateObject();
            } else {
                return null;
            }
        });
    }

    /**
     * Fills data from fields to createOrUpdate object. Precondition of this method is that {@link #recipeUpdateObject} is not null.
     *
     * @return (NotNull)
     * @throws IllegalStateException If createOrUpdate object does not exist.
     */
    private RecipeUpdateObject fillUpdateObject() {
        if (recipeUpdateObject == null) {
            throw new IllegalStateException("Cookbook createOrUpdate object cannot be null.");
        }

        recipeUpdateObject.setName(nameField.getText());
        //TODO add remaining fields

        return recipeUpdateObject;
    }

    /**
     * Shows dialog for adding recipe. This is blocking call. It waits until user close dialog.
     *
     * @return (NotNull) Empty optional if user cancels dialog. Optional with recipe createOrUpdate object if user submit dialog.
     */
    public Optional<RecipeUpdateObject> addRecipe() {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        recipeUpdateObject = recipeService.getCreateObject();
        return showAndWait();
    }

    /**
     * Clears all dialog fields.
     */
    private void clearDialog() {
        nameField.setText(StringUtils.EMPTY);
        //TODO add remaining fields
    }
}
