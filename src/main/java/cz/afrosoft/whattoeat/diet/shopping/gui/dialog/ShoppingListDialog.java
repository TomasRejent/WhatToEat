package cz.afrosoft.whattoeat.diet.shopping.gui.dialog;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItems;
import cz.afrosoft.whattoeat.diet.shopping.logic.service.ShoppingListService;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/ShoppingListDialog.fxml")
public class ShoppingListDialog extends Dialog<Void> {

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private TextArea contentTextArea;

    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private DietService dietService;

    private List<Diet> diets;

    public ShoppingListDialog() {
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setTitle(I18n.getText("cz.afrosoft.whattoeat.dietview.dialog.shopping.title"));
    }

    @PostConstruct
    private void initialize() {
        setupButtons();
        setupResultConverter();
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        DialogUtils.translateButtons(this);
    }

    private void setupResultConverter() {
        this.setResultConverter(buttonType -> null);
    }

    public void showShoppingList(final List<Diet> diets) {
        if(diets == null){
            return;
        }
        this.diets = diets;
        LocalDate fromDate = diets.stream().min(Comparator.comparing(Diet::getFrom)).map(Diet::getFrom).orElse(null);
        fromDatePicker.setValue(fromDate);
        LocalDate toDate = diets.stream().max(Comparator.comparing(Diet::getTo)).map(Diet::getTo).orElse(null);
        toDatePicker.setValue(toDate);

        updateShoppingList();
        initListeners();
        showAndWait();
    }

    private void initListeners(){
        fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateShoppingList();
        });
        toDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateShoppingList();
        });
    }

    private void updateShoppingList(){
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();
        List<Meal> meals = diets.stream().flatMap(diet -> dietService.getDietMealsInInterval(diet, from, to).stream()).collect(Collectors.toList());
        ShoppingItems shoppingItems = shoppingListService.createShoppingItems(meals);
        String shoppingListText = shoppingListService.formatToSimpleText(shoppingItems);

        contentTextArea.setText(shoppingListText);
    }
}
