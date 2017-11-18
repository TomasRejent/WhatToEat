package cz.afrosoft.whattoeat.cookbook.recipe.gui.table;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientUpdateObject;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.FloatFiled;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

/**
 * Specialized table cell for recipe ingredient quantity. Supports editing. Can be connected to supplier of servings to
 * automatically divide input value. This enables easier modification of recipe ingredients after they was weighted.
 *
 * @author Tomas Rejent
 */
public class IngredientQuantityCell extends TableCell<RecipeIngredientUpdateObject, Float> {

    /**
     * Default value for division supplier.
     */
    private static final Float DEFAULT_SERVINGS = 1F;

    /**
     * Field used for user input when in edit mode.
     */
    private final FloatFiled editField;
    /**
     * Supplier for division value. Used to divide user input before committing edit.
     */
    private final Supplier<Float> servingsSupplier;

    /**
     * Creates new cell with division value of 1, so input value is not changed.
     */
    public IngredientQuantityCell() {
        this(() -> DEFAULT_SERVINGS);
    }

    /**
     * Creates new cell with custom supplier of division value.
     *
     * @param servingsSupplier (NotNull) Supplier of servings value. If it returns null or zero, then default value of 1 is used, so division is not performed.
     */
    public IngredientQuantityCell(final Supplier<Float> servingsSupplier) {
        Validate.notNull(servingsSupplier);

        this.editField = new FloatFiled(FloatFiled.TYPE.NON_NEGATIVE);
        this.servingsSupplier = servingsSupplier;
        setGraphic(editField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        editField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Float servingsInput = servingsSupplier.get();
                float servingsValue = (servingsInput == null) || (servingsInput == 0) ? DEFAULT_SERVINGS : servingsInput.floatValue();
                this.commitEdit(editField.getFloatOrZero() / servingsValue);
            }
        });
    }

    @Override
    public void startEdit() {
        super.startEdit();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        editField.setFloat(getItem());
        editField.selectAll();
        editField.requestFocus();
    }

    @Override
    public void commitEdit(final Float newValue) {
        super.commitEdit(newValue);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(final Float item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(StringUtils.EMPTY);
        } else {
            RecipeIngredientUpdateObject recipeIngredient = getTableView().getItems().get(getIndex());
            setText(String.valueOf(item) + " " + recipeIngredient.getIngredient().map(ingredient -> I18n.getText(ingredient.getIngredientUnit().getLabelKey())).orElse("?"));
        }
    }
}
