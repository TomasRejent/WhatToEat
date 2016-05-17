/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.gui.view;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import cz.afrosoft.whattoeat.ServiceHolder;
import cz.afrosoft.whattoeat.data.DataHolderService;
import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;
import cz.afrosoft.whattoeat.gui.I18n;

import cz.afrosoft.whattoeat.logic.model.Ingredient;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.enums.IngredientUnit;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javax.swing.DefaultListCellRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Rejent
 */
public final class IngredientList extends ListView<IngredientView>{

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientList.class);

    private final DataHolderService dataHolderService = ServiceHolder.getDataHolderService();

    private final ObservableList<IngredientView> ingredientList = FXCollections.observableList(new LinkedList<IngredientView>());

    public IngredientList() {
        setItems(ingredientList);
        setCellFactory((ListView<IngredientView> param) -> new IngredientViewCell());
    }

    public void setServings(int servings){
        for(IngredientView ingredient : ingredientList){
            ingredient.setServings(servings);
        }
        this.refresh();
    }

    public void setIngredients(Collection<Ingredient> ingredients){
        ingredientList.clear();
        for(Ingredient ingredient : ingredients){
            IngredientInfo ingredientInfo = dataHolderService.getIngredientByName(ingredient.getName());
            if(ingredientInfo == null){
                LOGGER.warn("Ingredient {} has no ingredient info.", ingredient);
                continue;
            }

            ingredientList.add(new IngredientView(ingredient, ingredientInfo));
        }
    }

    private static class IngredientViewCell extends ListCell<IngredientView>{

        private static final double ITEM_ELEMENT_SPACING = 5;

        private final HBox cellBox = new HBox();
        private final Label nameText = new Label();
        private final Label quantityText = new Label();
        private final FlowPane keywordsPane = new FlowPane();

        public IngredientViewCell() {
            keywordsPane.setHgap(ITEM_ELEMENT_SPACING);
            cellBox.getChildren().addAll(quantityText, nameText, keywordsPane);
            cellBox.setSpacing(ITEM_ELEMENT_SPACING);
        }

        @Override
        protected void updateItem(IngredientView item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                updateCellContent(item);
                setGraphic(cellBox);
            }
        }

        private void updateCellContent(IngredientView item){
            quantityText.setText(String.valueOf(item.getQuantity()) + I18n.getText(item.getIngredientUnit().getLabelKey()));
            nameText.setText(item.getName());
            updateKeywords(item.getKeywords());
        }

        private void updateKeywords(Set<String> keywordsSet){
            keywordsPane.getChildren().clear();
            for(String keyword : keywordsSet){
                Label keywordLabel = KeywordLabelFactory.createKeywordLabel(keyword);
                keywordsPane.getChildren().add(keywordLabel);
            }
        }
    }

}
