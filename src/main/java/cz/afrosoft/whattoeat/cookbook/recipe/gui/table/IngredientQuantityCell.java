package cz.afrosoft.whattoeat.cookbook.recipe.gui.table;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeIngredientUpdateObject;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.FloatFiled;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Specialized table cell for recipe ingredient quantity. Supports editing.
 *
 * @author Tomas Rejent
 */
public class IngredientQuantityCell extends TableCell<RecipeIngredientUpdateObject, Float> {

    private final FloatFiled editField;

    public IngredientQuantityCell() {
        this.editField = new FloatFiled(FloatFiled.TYPE.NON_NEGATIVE);
        setGraphic(editField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        editField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.commitEdit(editField.getFloatOrZero());
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
