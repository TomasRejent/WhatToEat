package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Component for displaying collection of {@link RecipeLink} in FlowPane layout.
 *
 * @author Tomas Rejent
 */
public class RecipeLinks extends FlowPane {

    private static final String FXML_PATH = "/component/RecipeLinks.fxml";

    private final Label titleLabel = new Label();

    private final StringProperty text = new SimpleStringProperty();

    public RecipeLinks() {
        ComponentUtil.initFxmlComponent(this, FXML_PATH);
        text.addListener(createTextChangeListener());
    }

    private ChangeListener<String> createTextChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (StringUtils.isBlank(newValue)) {
                this.getChildren().remove(titleLabel);
            } else {
                titleLabel.setText(newValue + ":");
                this.getChildren().add(0, titleLabel);
            }
        };
    }

    public void setRecipes(final Collection<? extends RecipeRef> recipes) {
        Validate.notNull(recipes);

        this.getChildren().clear();
        if (StringUtils.isNoneBlank(text.getValue())) {
            this.getChildren().add(titleLabel);
        }
        this.getChildren().addAll(recipes.stream().sorted().map(RecipeLink::new).collect(Collectors.toList()));
    }

    public String getText() {
        return text.get();
    }

    public void setText(final String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }
}
