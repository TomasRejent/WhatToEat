package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Component for displaying collection of {@link RecipeLink} in FlowPane layout.
 *
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/RecipeLinks.fxml")
public class RecipeLinks extends FlowPane {

    private static final String FXML_PATH = "/component/RecipeLinks.fxml";

    private final Label titleLabel = new Label();

    private final StringProperty text = new SimpleStringProperty();

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void initialize() {
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
        this.getChildren().addAll(recipes.stream().sorted().map(recipeRef -> applicationContext.getBean(RecipeLink.class, recipeRef)).collect(Collectors.toList()));
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
