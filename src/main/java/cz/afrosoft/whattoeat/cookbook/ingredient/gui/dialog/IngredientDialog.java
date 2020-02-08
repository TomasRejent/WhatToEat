package cz.afrosoft.whattoeat.cookbook.ingredient.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.NutritionFacts;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.UnitConversion;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientService;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientUpdateObject;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.FloatField;
import cz.afrosoft.whattoeat.core.gui.component.KeywordField;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.titledpane.TitledPaneUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Dialog for adding and editing of ingredients. This dialog also allows to specify conversion rates between ingredient
 * units.
 * <p>
 * This dialog must be modal because controller using it has only one instance of this dialog.
 *
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/IngredientDialog.fxml")
public class IngredientDialog extends Dialog<IngredientUpdateObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDialog.class);

    /**
     * Title message displayed when dialog is in add mode.
     */
    private static final String ADD_TITLE_KEY = "cz.afrosoft.whattoeat.ingredient.dialog.title.add";

    /**
     * Title message displayed when dialog is in edit mode.
     */
    private static final String EDIT_TITLE_KEY = "cz.afrosoft.whattoeat.ingredient.dialog.title.edit";

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<IngredientUnit> unitField;
    @FXML
    private FloatField priceField;
    @FXML
    private KeywordField keywordField;
    @FXML
    private TitledPane unitConversionPane;
    @FXML
    private FloatField gramsPerPieceField;
    @FXML
    private FloatField milliliterPerGramField;
    @FXML
    private FloatField gramsPerPinchField;
    @FXML
    private FloatField gramsPerCoffeeSpoonField;
    @FXML
    private FloatField gramsPerSpoonField;
    @FXML
    private TitledPane nutritionFactsPane;
    @FXML
    private FloatField energyField;
    @FXML
    private FloatField fatField;
    @FXML
    private FloatField saturatedFatField;
    @FXML
    private FloatField carbohydrateField;
    @FXML
    private FloatField sugarField;
    @FXML
    private FloatField proteinField;
    @FXML
    private FloatField saltField;
    @FXML
    private FloatField fiberField;

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private NutritionFactsService nutritionFactsService;

    /**
     * Holds createOrUpdate object when creating or editing ingredient.
     */
    private IngredientUpdateObject ingredientUpdateObject;

    /**
     * Creates new dialog. This constructor must be used only by Spring, because dependencies must be autowired to created instance.
     */
    public IngredientDialog() {
    }

    @PostConstruct
    private void initialize() {
        getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        setResizable(true);
        setHeight(650);
        initModality(Modality.APPLICATION_MODAL);
        setupResultConverter();
        ComboBoxUtils.initLabeledComboBox(unitField, IngredientUnit.values());
        setupKeyNavigation();
    }

    private void setupKeyNavigation(){
        setupFieldKeyVerticalNavigation(energyField, null, fatField);
        setupFieldKeyVerticalNavigation(fatField, energyField, saturatedFatField);
        setupFieldKeyVerticalNavigation(saturatedFatField, fatField, carbohydrateField);
        setupFieldKeyVerticalNavigation(carbohydrateField, saturatedFatField, sugarField);
        setupFieldKeyVerticalNavigation(sugarField, carbohydrateField, proteinField);
        setupFieldKeyVerticalNavigation(proteinField, sugarField, saltField);
        setupFieldKeyVerticalNavigation(saltField, proteinField, fiberField);
        setupFieldKeyVerticalNavigation(fiberField, saltField, null);

    }

    private void setupFieldKeyVerticalNavigation(final Control field, final Control upField, final Control downField){
        field.setOnKeyPressed(event -> {
            if(upField != null && event.getCode() == KeyCode.UP){
                upField.requestFocus();
            }
            if(downField != null && event.getCode() == KeyCode.DOWN){
                downField.requestFocus();
            }
        });
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
        LOGGER.trace("Filling update object: {}", ingredientUpdateObject);
        if (ingredientUpdateObject == null) {
            throw new IllegalStateException("Ingredient createOrUpdate object cannot be null.");
        }

        ingredientUpdateObject.setName(nameField.getText());
        ingredientUpdateObject.setIngredientUnit(unitField.getValue());
        ingredientUpdateObject.setPrice(priceField.getFloatOrZero());
        ingredientUpdateObject.setKeywords(keywordField.getSelectedKeywords());
        ingredientUpdateObject.setUnitConversion(
                gramsPerPieceField.getFloat(),
                milliliterPerGramField.getFloat(),
                gramsPerPinchField.getFloat(),
                gramsPerCoffeeSpoonField.getFloat(),
                gramsPerSpoonField.getFloat()
        );
        ingredientUpdateObject.getNutritionFacts()
            .setEnergy(nutritionFactsService.energyToBase(energyField.getFloat()))
            .setFat(nutritionFactsService.nutritionToBase(fatField.getFloat()))
            .setSaturatedFat(nutritionFactsService.nutritionToBase(saturatedFatField.getFloat()))
            .setCarbohydrate(nutritionFactsService.nutritionToBase(carbohydrateField.getFloat()))
            .setSugar(nutritionFactsService.nutritionToBase(sugarField.getFloat()))
            .setProtein(nutritionFactsService.nutritionToBase(proteinField.getFloat()))
            .setSalt(nutritionFactsService.nutritionToBase(saltField.getFloat()))
            .setFiber(nutritionFactsService.nutritionToBase(fiberField.getFloat()));

        LOGGER.trace("Update object filled to: {}", ingredientUpdateObject);
        return ingredientUpdateObject;
    }

    /**
     * Shows dialog for adding ingredient. This is blocking call. It waits until user close dialog.
     *
     * @return (NotNull) Empty optional if user cancels dialog. Optional with ingredient createOrUpdate object if user submit dialog.
     */
    public Optional<IngredientUpdateObject> addIngredient() {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        ingredientUpdateObject = ingredientService.getCreateObject();
        return showAndWait();
    }

    /**
     * Shows dialog for adding ingredient and prefills name field with specified value. This is blocking call. It waits until user close dialog.
     *
     * @param ingredientName (Nullable) Ingredient name which is prefilled into name field of dialog.
     * @return (NotNull) Empty optional if user cancels dialog. Optional with ingredient createOrUpdate object if user submit dialog.
     */
    public Optional<IngredientUpdateObject> addIngredient(final String ingredientName) {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        nameField.setText(ingredientName);
        ingredientUpdateObject = ingredientService.getCreateObject();
        return showAndWait();
    }

    /**
     * Shows dialog for editing ingredient. This is blocking call. It waits until user close dialog.
     *
     * @param ingredient (NotNull) Ingredient to edit.
     * @return (NotNull) Empty optional if user cancels dialog. Optional with ingredient createOrUpdate object if user submit dialog.
     */
    public Optional<IngredientUpdateObject> editIngredient(final Ingredient ingredient) {
        Validate.notNull(ingredient);
        setTitle(I18n.getText(EDIT_TITLE_KEY));
        prefillDialog(ingredient);
        ingredientUpdateObject = ingredientService.getUpdateObject(ingredient);
        return showAndWait();
    }

    /**
     * Fills dialog fields with data from ingredient.
     *
     * @param ingredient (NotNull)
     */
    private void prefillDialog(final Ingredient ingredient) {
        LOGGER.trace("Filling dialog fields with ingredient: {}.", ingredient);
        Validate.notNull(ingredient);
        nameField.setText(ingredient.getName());
        unitField.getSelectionModel().select(ingredient.getIngredientUnit());
        priceField.setFloat(ingredient.getPrice());
        keywordField.setSelectedKeywords(ingredient.getKeywords());
        Optional<UnitConversion> unitConversionOpt = ingredient.getUnitConversion();
        LOGGER.trace("Filling dialog fields with unit conversion: {}.", unitConversionOpt);
        if (unitConversionOpt.isPresent()) {
            UnitConversion unitConversion = unitConversionOpt.get();
            gramsPerPieceField.setFloat(unitConversion.getGramsPerPiece());
            milliliterPerGramField.setFloat(unitConversion.getMilliliterPerGram());
            gramsPerPinchField.setFloat(unitConversion.getGramsPerPinch());
            gramsPerCoffeeSpoonField.setFloat(unitConversion.getGramsPerCoffeeSpoon());
            gramsPerSpoonField.setFloat(unitConversion.getGramsPerSpoon());
            TitledPaneUtils.setExpandedWithoutAnimation(unitConversionPane, true);
        } else {
            clearUnitConversionFields();
        }
        Optional<NutritionFacts> nutritionFactsOpt = ingredient.getNutritionFacts();
        if(nutritionFactsOpt.isPresent()){
            NutritionFacts nutritionFacts = nutritionFactsOpt.get();
            energyField.setFloat(nutritionFactsService.energyToHumanReadable(nutritionFacts.getEnergy()));
            fatField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getFat()));
            saturatedFatField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getSaturatedFat()));
            carbohydrateField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getCarbohydrate()));
            sugarField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getSugar()));
            proteinField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getProtein()));
            saltField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getSalt()));
            fiberField.setFloat(nutritionFactsService.nutritionToHumanReadable(nutritionFacts.getFiber()));
        } else {
            clearNutritionFactsFields();
        }
    }

    /**
     * Clears are dialog fields or sets them to default values.
     */
    private void clearDialog() {
        LOGGER.trace("Clearing dialog fields.");
        nameField.setText(StringUtils.EMPTY);
        unitField.getSelectionModel().select(IngredientUnit.WEIGHT);
        priceField.setFloat(null);
        keywordField.clearSelectedKeywords();
        clearUnitConversionFields();
        clearNutritionFactsFields();
    }

    /**
     * Clears all fields related to unit conversion and collapses unit conversion panel.
     */
    private void clearUnitConversionFields() {
        LOGGER.trace("Clearing dialog unit conversion fields.");
        gramsPerPieceField.setFloat(null);
        milliliterPerGramField.setFloat(null);
        gramsPerPinchField.setFloat(null);
        gramsPerCoffeeSpoonField.setFloat(null);
        gramsPerSpoonField.setFloat(null);
        TitledPaneUtils.setExpandedWithoutAnimation(unitConversionPane, true);
    }

    private void clearNutritionFactsFields(){
        energyField.setFloat(null);
        fatField.setFloat(null);
        saturatedFatField.setFloat(null);
        carbohydrateField.setFloat(null);
        sugarField.setFloat(null);
        proteinField.setFloat(null);
        saltField.setFloat(null);
        fiberField.setFloat(null);
        TitledPaneUtils.setExpandedWithoutAnimation(nutritionFactsPane, true);
    }
}
