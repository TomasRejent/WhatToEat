package cz.afrosoft.whattoeat.cookbook.recipe.gui.component;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.component.FieldLabel;
import cz.afrosoft.whattoeat.core.gui.component.KeywordField;
import cz.afrosoft.whattoeat.core.gui.component.MultiSelect;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Builder;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/RecipeFilterComponent.fxml")
public class RecipeFilterComponent extends GridPane implements Builder<RecipeFilterComponent> {

    @FXML
    private FieldLabel nameFilterLabel;
    @FXML
    private TextField nameFilter;
    @FXML
    private MultiSelect<CookbookRef> cookbookFilter;
    @FXML
    private MultiSelect<RecipeType> typeFilter;
    @FXML
    private KeywordField keywordField;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CookbookService cookbookService;

    private BooleanProperty nameFieldVisibleProperty = new SimpleBooleanProperty(this, "nameFieldVisible", true);

    @PostConstruct
    private void initialize(){
        cookbookFilter.getItems().addAll(cookbookService.getAllCookbookRefs());
        typeFilter.getItems().addAll(RecipeType.values());
        cookbookFilter.setConverter(ComboBoxUtils.createStringConverter(cookbookFilter, CookbookRef::getName));
        ComboBoxUtils.initLabeledCheckComboBox(typeFilter);
        nameFieldVisibleProperty.addListener(createNameFieldVisibleListener());
    }

    @Override
    public RecipeFilterComponent build() {
        return this;
    }

    public RecipeFilter getRecipeFilter(){
        return new RecipeFilter.Builder()
                .setName(nameFilter.getText())
                .setCookbooks(cookbookFilter.getValues())
                .setType(typeFilter.getValues())
                .setKeywords(keywordField.getSelectedKeywords())
                .build();
    }

    private ChangeListener<Boolean> createNameFieldVisibleListener(){
        return (observable, oldValue, newValue) -> {
            if (BooleanUtils.isTrue(newValue)) {
                nameFilterLabel.setVisible(true);
                nameFilter.setVisible(true);
            } else {
                nameFilterLabel.setVisible(false);
                nameFilter.setVisible(false);
            }
        };
    }

    public boolean isNameFieldVisible() {
        return nameFieldVisibleProperty.get();
    }

    public void setNameFieldVisible(final boolean visible) {
        nameFieldVisibleProperty.set(visible);
    }

    public BooleanProperty nameFieldVisibleProperty() {
        return nameFieldVisibleProperty;
    }

}
