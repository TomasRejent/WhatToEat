package cz.afrosoft.whattoeat.diet.list.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.gui.component.IntegerField;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealService;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/DayDietDialog.fxml")
public class DayDietDialog extends Dialog<List<MealUpdateObject>> {

    @FXML
    private TextField recipeField;
    @FXML
    private IntegerField servingsField;
    @FXML
    private TableView<MealUpdateObject> mealTable;
    @FXML
    private TableColumn<MealUpdateObject, String> recipeColumn;
    @FXML
    private TableColumn<MealUpdateObject, Integer> servingsColumn;
    @FXML
    private TableColumn<MealUpdateObject, Void> removeColumn;

    @Autowired
    private MealService mealService;

    @PostConstruct
    private void initialize() {
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);

        setupButtons();
        setupResultConverter();
        setupTableColumns();
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);
        DialogUtils.translateButtons(this);
    }

    private void setupResultConverter() {
        this.setResultConverter((buttonType) -> {
            if (ButtonType.FINISH.equals(buttonType)) {
                return fillUpdateObject();
            } else {
                return null;
            }
        });
    }

    private void setupTableColumns() {
        recipeColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(mealUpdateObject -> mealUpdateObject.getRecipe().map(RecipeRef::getName).orElse(StringUtils.EMPTY)));
        servingsColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(mealUpdateObject -> mealUpdateObject.getServings().orElse(0)));
    }

    private List<MealUpdateObject> fillUpdateObject() {
        return mealTable.getItems();
    }

    public Optional<List<MealUpdateObject>> editMeals(List<Meal> meals) {

        return showAndWait();
    }
}
