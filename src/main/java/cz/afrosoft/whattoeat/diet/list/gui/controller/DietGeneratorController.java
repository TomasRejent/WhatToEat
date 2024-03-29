package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.cookbook.user.lodic.service.UserService;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.MultiSelect;
import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietCreateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author Tomas Rejent
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DietGeneratorController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker fromField;
    @FXML
    private DatePicker toField;
    @FXML
    private MultiSelect<CookbookRef> cookbookFilter;
    @FXML
    private MultiSelect<MealTime> dishesFilter;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ComboBox<User> userField;
    @FXML
    private ComboBox<GeneratorType> generatorField;
    @FXML
    private ScrollPane generatorGuiPane;

    private GeneratorGui<?> generatorGui;

    @Autowired
    private DietService dietService;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private MenuController menuController;
    @Autowired
    private CookbookService cookbookService;
    @Autowired
    private UserService userService;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setupGeneratorComboBox();
        setupUserComboBox();
        cookbookFilter.getItems().addAll(cookbookService.getAllCookbookRefs());
        cookbookFilter.setConverter(ComboBoxUtils.createStringConverter(cookbookFilter, CookbookRef::getName));
        dishesFilter.getItems().addAll(MealTime.values());
        ComboBoxUtils.initLabeledCheckComboBox(dishesFilter);
        dishesFilter.getCheckModel().check(MealTime.BREAKFAST);
        dishesFilter.getCheckModel().check(MealTime.MORNING_SNACK);
        dishesFilter.getCheckModel().check(MealTime.LUNCH);
        dishesFilter.getCheckModel().check(MealTime.AFTERNOON_SNACK);
        dishesFilter.getCheckModel().check(MealTime.DINNER);
    }

    private void setupUserComboBox(){
        userField.getItems().setAll(userService.getAllUsers());
        userField.setConverter(ComboBoxUtils.createStringConverter(userField, User::getName));
    }

    private void setupGeneratorComboBox() {
        ComboBoxUtils.initLabeledComboBox(generatorField, GeneratorType.values());
        generatorField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                generatorGui = generatorService.getGuiForGenerator(newValue);
                generatorGui.getNode().ifPresent(node -> {
                    generatorGuiPane.setContent(node);
                });
            }
        });
    }

    @FXML
    public void generateDiet() {
        DietCreateObject createObject = fillCreateObject(dietService.getCreateObject());
        dietService.create(createObject);
        menuController.showDietList();
    }

    private RecipeFilter createAndFillFilter(){
        return new RecipeFilter.Builder()
                .setCookbooks(cookbookFilter.getValues())
                .build();
    }

    private DietCreateObject fillCreateObject(final DietCreateObject createObject) {
        Validate.notNull(createObject);
        LocalDate from = fromField.getValue();
        LocalDate to = toField.getValue();

        generatorGui.setInterval(from, to);
        generatorGui.setFilter(createAndFillFilter());
        generatorGui.setDishes(dishesFilter.getValues());
        generatorGui.setUser(userField.getValue());
        createObject
            .setName(nameField.getText())
            .setFrom(from)
            .setTo(to)
            .setDescription(descriptionField.getText())
            .setUser(userField.getValue())
            .setGenerator(generatorField.getValue())
            .setGeneratorParams(generatorGui.getParameters());
        return createObject;
    }
}
