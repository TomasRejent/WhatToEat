package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Component for displaying {@link cz.afrosoft.whattoeat.core.logic.model.Keyword}.
 *
 * @author Tomas Rejent
 */
public class KeywordLabel extends Label {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordLabel.class);

    private static final String FXML_PATH = "/component/KeywordLabel.fxml";

    @FXML
    private RemoveButton removeButton;

    /**
     * Controls visibility od remove button.
     */
    private BooleanProperty removable = new SimpleBooleanProperty(true);

    private final Keyword keyword;

    public KeywordLabel(final Keyword keyword) {
        Validate.notNull(keyword);
        ComponentUtil.initFxmlComponent(this, FXML_PATH);

        this.keyword = keyword;
        setText(keyword.getName());
        removable.addListener(createRemovablePropListener());
        setRemovable(false);
    }

    private ChangeListener<Boolean> createRemovablePropListener() {
        return (observable, oldValue, newValue) -> {
            if (newValue) {
                setGraphic(removeButton);
            } else {
                setGraphic(null);
            }
        };
    }

    /**
     * @return (NotNull) Keyword which is displayed by this component.
     */
    public Keyword getKeyword() {
        return keyword;
    }

    /**
     * @param removable If true label renders remove button. Default value is false.
     */
    public KeywordLabel setRemovable(final boolean removable) {
        this.removable.setValue(removable);
        return this;
    }

    /**
     * @param listener (NotNull) Listener for remove button click event. Listeners receives this component, from which it
     *                 can obtain keyword to remove.
     */
    public void setRemoveListener(final Consumer<KeywordLabel> listener) {
        Validate.notNull(listener);

        removeButton.setOnAction(event -> listener.accept(this));
    }
}
