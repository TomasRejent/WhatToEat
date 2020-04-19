package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.FieldLabel;
import cz.afrosoft.whattoeat.core.gui.component.FloatField;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Builder;

import javax.annotation.PostConstruct;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/NutritionCriteriaField.fxml")
public class NutritionCriteriaField extends GridPane implements Builder<NutritionCriteriaField> {

    private final Property<NutritionCriteriaType> typeProperty = new SimpleObjectProperty<>(this, "type", NutritionCriteriaType.ENERGY);
    private final BooleanProperty displayFieldLabels = new SimpleBooleanProperty(this, "displayFieldLabels", true);

    @FXML
    private Label fieldLabel;
    @FXML
    private FieldLabel targetAmountLabel;
    @FXML
    private FloatField targetAmountField;
    @FXML
    private FloatField priorityField;
    @FXML
    private FloatField belowToleranceField;
    @FXML
    private FloatField aboveToleranceField;
    @FXML
    private FieldLabel priorityLabel;
    @FXML
    private FieldLabel belowToleranceLabel;
    @FXML
    private FieldLabel aboveToleranceLabel;

    @PostConstruct
    private void initialize(){
        typeProperty.addListener(createTypeChangeListener());
        updateLabels(getType());
    }

    @Override
    public NutritionCriteriaField build() {
        if(!displayFieldLabels.get()){
            this.getChildren().removeAll(targetAmountLabel, priorityLabel, belowToleranceLabel, aboveToleranceLabel);
        }
        setInitialValues();
        return this;
    }

    private void setInitialValues(){
        targetAmountField.setFloat(getType().getDefaultAmount());
        priorityField.setFloat(getType().getDefaultPriority());
        belowToleranceField.setFloat(getType().getDefaultBelowTolerance());
        aboveToleranceField.setFloat(getType().getDefaultAboveTolerance());
    }

    private ChangeListener<NutritionCriteriaType> createTypeChangeListener(){
        return (observable, oldValue, newValue) -> {
            updateLabels(newValue);
        };
    }

    private void updateLabels(NutritionCriteriaType type){
        String unit = type == NutritionCriteriaType.ENERGY ? "kJ" : "g";
        targetAmountLabel.setText(I18n.getText("cz.afrosoft.whattoeat.diet.generator.nutritionSalvation.targetAmount", unit));
        fieldLabel.setText(I18n.getText(type.getLabelKey()));
    }

    public NutritionCriteria getNutritionCriteria(){
        return new NutritionCriteria(getType(), targetAmountField.getFloatOrZero(), aboveToleranceField.getFloatOrZero(), belowToleranceField.getFloatOrZero(), Math.round(priorityField.getFloatOrZero()));
    }

    public Property<NutritionCriteriaType> typeProperty(){
        return typeProperty;
    }

    public void setType(NutritionCriteriaType type){
        this.typeProperty.setValue(type);
    }

    public NutritionCriteriaType getType(){
        return typeProperty.getValue();
    }

    public BooleanProperty displayFieldLabelsProperty(){
        return displayFieldLabels;
    }

    public Boolean getDisplayFieldLabels(){
        return displayFieldLabels.getValue();
    }

    public void setDisplayFieldLabels(Boolean displayFieldLabels){
        this.displayFieldLabels.setValue(displayFieldLabels);
    }
}
