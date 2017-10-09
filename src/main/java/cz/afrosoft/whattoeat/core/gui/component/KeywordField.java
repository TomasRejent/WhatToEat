package cz.afrosoft.whattoeat.core.gui.component;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import cz.afrosoft.whattoeat.core.logic.service.KeywordService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * Component for picking keywords.
 *
 * @author Tomas Rejent
 */
public class KeywordField extends GridPane {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordField.class);

    private static final String FXML_PATH = "/component/KeywordField.fxml";

    @FXML
    private ComboBox<Keyword> keywordField;
    @FXML
    private FlowPane keywordContainer;

    /**
     * Observable set of keywords. Keywords in this set are displayed in keyword container.
     */
    private ObservableSet<Keyword> keywords = FXCollections.observableSet(new LinkedHashSet<>());

    @Autowired
    private KeywordService keywordService;

    public KeywordField() {
        ComponentUtil.initFxmlComponent(this, FXML_PATH);
        keywords.addListener(createChangeListener());
        initKeywordField();
    }

    private SetChangeListener<Keyword> createChangeListener() {
        return change -> Platform.runLater(() -> {
            if (change.wasRemoved()) {
                processKeywordRemove(change);
            }
            if (change.wasAdded()) {
                processKeywordAddition(change);
            }
        });
    }

    private void processKeywordRemove(final SetChangeListener.Change<? extends Keyword> change) {
        Keyword removedKeyword = change.getElementRemoved();
        //update panel with displayed keywords
        keywordContainer.getChildren()
                .stream()
                .filter(node ->
                        node instanceof KeywordLabel && ((KeywordLabel) node).getKeyword().equals(removedKeyword))
                .forEach(node -> Platform.runLater(() -> keywordContainer.getChildren().remove(node)));
        //update keyword field, only persisted keywords are returned to options
        if (!keywordField.getItems().contains(removedKeyword) && removedKeyword.getId() != null) {
            keywordField.getItems().add(removedKeyword);
        }
    }

    private void processKeywordAddition(final SetChangeListener.Change<? extends Keyword> change) {
        Keyword addedKeyword = change.getElementAdded();
        //update panel with displayed keywords
        KeywordLabel keywordLabel = keywordService.createKeywordLabel(addedKeyword);
        keywordLabel.setRemovable(true);
        keywordLabel.setRemoveListener(removedKeywordLabel -> keywords.remove(removedKeywordLabel.getKeyword()));
        keywordContainer.getChildren().add(keywordLabel);
        //update keyword field
        keywordField.getSelectionModel().clearSelection();
        keywordField.getEditor().setText(StringUtils.EMPTY);
        keywordField.getItems().remove(addedKeyword);
    }

    private void initKeywordField() {
        keywordField.setConverter(ComboBoxUtils.createAsymmetricStringConverter(Keyword::getName, keywordService::getKeyword));
        keywordField.setEditable(true);
        keywordField.getItems().clear();
        keywordField.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        keywords.add(newValue);
                    }
                }
        );
        /**
         * Consumes event after enter key is pressed to add keyword. Without this adding of keyword caused submitting of dialog which use keyword field.
         */
        keywordField.setOnAction(Event::consume);

        refreshKeywords();
    }

    /**
     * Reloads keywords which are persisted.
     */
    public void refreshKeywords() {
        Set<Keyword> allKeywords = keywordService.getAllKeywords();
        keywordField.getSelectionModel().clearSelection();
        keywordField.getItems().addAll(Sets.difference(allKeywords, keywords));
    }

    /**
     * @return (NotNull)(ReadOnly) Set of selected keywords.
     */
    public Set<Keyword> getSelectedKeywords() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(keywords));
    }

    /**
     * Set selected keywords.
     *
     * @param keywords (NotNull)
     */
    public void setSelectedKeywords(final Collection<Keyword> keywords) {
        Validate.notNull(keywords);

        this.keywords.clear();
        this.keywords.addAll(keywords);
        this.keywordField.getItems().removeAll(keywords);
    }

    /**
     * Clears all selected keywords.
     */
    public void clearSelectedKeywords() {
        keywords.clear();
    }
}
