package cz.afrosoft.whattoeat.diet.list.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.cookbook.user.lodic.service.UserService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.FloatField;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DietCopyParams;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealCopyParams;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/DietCopyDialog.fxml")
public class DietCopyDialog extends Dialog<DietCopyParams> {

    @FXML
    private TextField newDietNameField;
    @FXML
    private ComboBox<User> userField;
    @FXML
    private DatePicker startDate;
    @FXML
    private CheckBox copyBreakfastsField;
    @FXML
    private CheckBox copyMorningSnacksField;
    @FXML
    private CheckBox copyLunchField;
    @FXML
    private CheckBox copyAfternoonSnacksField;
    @FXML
    private CheckBox copyDinnersField;
    @FXML
    private CheckBox copyOthersField;
    @FXML
    private FloatField breakfastsServingsField;
    @FXML
    private FloatField morningSnacksServingsField;
    @FXML
    private FloatField lunchServingsField;
    @FXML
    private FloatField afternoonSnacksServingsField;
    @FXML
    private FloatField dinnersServingsField;
    @FXML
    private FloatField othersServingsField;
    @Autowired
    private UserService userService;

    @PostConstruct
    private void initialize(){
        initModality(Modality.APPLICATION_MODAL);
        setTitle(I18n.getText("cz.afrosoft.whattoeat.copyDietDialog.title"));
        setupButtons();
        setupResultConverter();
        setupFields();
    }

    private void setupFields(){
        Button finishButton = (Button) this.getDialogPane().lookupButton(ButtonType.FINISH);
        newDietNameField.setOnKeyTyped(event -> {
            finishButton.setDisable(newDietNameField.getText().length() == 0);
        });
        userField.getItems().setAll(userService.getAllUsers());
        userField.setConverter(ComboBoxUtils.createStringConverter(userField, User::getName));
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);
        DialogUtils.translateButtons(this);
    }

    private void setupResultConverter() {
        this.setResultConverter((buttonType) -> {
            if (ButtonType.FINISH.equals(buttonType)) {
                return getDataFromFields();
            } else {
                return null;
            }
        });
    }

    private DietCopyParams getDataFromFields(){
        DietCopyParams params = new DietCopyParams();
        params.setDietName(newDietNameField.getText());
        params.setUser(userField.getValue());
        params.setStartDate(startDate.getValue());
        params.setBreakfastsParams(new MealCopyParams(copyBreakfastsField.isSelected(), breakfastsServingsField.getFloatOrZero()));
        params.setSnacksParams(new MealCopyParams(copyMorningSnacksField.isSelected(), morningSnacksServingsField.getFloatOrZero()));
        params.setLunchParams(new MealCopyParams(copyLunchField.isSelected(), lunchServingsField.getFloatOrZero()));
        params.setAfternoonSnacksParams(new MealCopyParams(copyAfternoonSnacksField.isSelected(), afternoonSnacksServingsField.getFloatOrZero()));
        params.setDinnersParams(new MealCopyParams(copyDinnersField.isSelected(), dinnersServingsField.getFloatOrZero()));
        params.setOthersParams(new MealCopyParams(copyOthersField.isSelected(), othersServingsField.getFloatOrZero()));
        return params;
    }

    public Optional<DietCopyParams> getDietCopyParams(Diet sourceDiet){
        clearDialog(sourceDiet);
        newDietNameField.requestFocus();
        return showAndWait();
    }

    private void clearDialog(Diet sourceDiet){
        newDietNameField.setText(sourceDiet.getName() + " (2)");
        startDate.setValue(sourceDiet.getFrom());
        copyBreakfastsField.setSelected(false);
        copyMorningSnacksField.setSelected(false);
        copyLunchField.setSelected(true);
        copyAfternoonSnacksField.setSelected(false);
        copyDinnersField.setSelected(false);
        copyOthersField.setSelected(false);
        breakfastsServingsField.setFloat(MealCopyParams.DEFAULT_SERVINGS);
        morningSnacksServingsField.setFloat(MealCopyParams.DEFAULT_SERVINGS);
        lunchServingsField.setFloat(MealCopyParams.DEFAULT_SERVINGS);
        afternoonSnacksServingsField.setFloat(MealCopyParams.DEFAULT_SERVINGS);
        dinnersServingsField.setFloat(MealCopyParams.DEFAULT_SERVINGS);
        othersServingsField.setFloat(MealCopyParams.DEFAULT_SERVINGS);
    }
}
