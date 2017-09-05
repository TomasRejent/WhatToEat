package cz.afrosoft.whattoeat.cookbook.ingredient.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientUpdateObject;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.FloatFiled;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Dialog for adding and editing of ingredients. This dialog also allows to specify conversion rates between ingredient
 * units.
 * <p>
 * This dialog must be modal because controller using it has only one instance of this dialog.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IngredientDialog extends CustomDialog<IngredientUpdateObject> {

    private static final String DIALOG_FXML = "/fxml/IngredientDialog.fxml";

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<IngredientUnit> unitField;
    @FXML
    private FloatFiled priceField;

    @Autowired
    private IngredientService ingredientService;

    private IngredientUpdateObject ingredientUpdateObject;

    public IngredientDialog() {
        super(DIALOG_FXML);
        getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setupResultConverter();
        ComboBoxUtils.initLabeledComboBox(unitField, IngredientUnit.values());
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
     * Fills data from fields to createOrUpdate object. Precondition of this method is that {@link #ingredientUpdateObject} is not null.
     *
     * @return (NotNull)
     * @throws IllegalStateException If createOrUpdate object does not exist.
     */
    private IngredientUpdateObject fillUpdateObject() {
        if (ingredientUpdateObject == null) {
            throw new IllegalStateException("Cookbook createOrUpdate object cannot be null.");
        }


        return ingredientUpdateObject;
    }
}
