package cz.afrosoft.whattoeat.core.gui.table;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.component.KeywordLabel;
import cz.afrosoft.whattoeat.core.logic.model.Keyword;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.FlowPane;

import java.util.Collection;

/**
 * @author Tomas Rejent
 */
public class KeywordCell<S> extends TableCell<S, Collection<Keyword>> {

    private static final double KEYWORD_SPACING = 2;

    private FlowPane keywordLabelContainer = new FlowPane(KEYWORD_SPACING, KEYWORD_SPACING);

    public KeywordCell() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(keywordLabelContainer);
        keywordLabelContainer.setPrefHeight(getHeight());
    }

    @Override
    protected void updateItem(final Collection<Keyword> item, final boolean empty) {
        keywordLabelContainer.getChildren().clear();

        if (item != null) {
            item.forEach(keyword -> keywordLabelContainer.getChildren().add(
                    Main.getApplicationContext().getBean(KeywordLabel.class, keyword).setRemovable(false)
            ));
        }
    }
}
