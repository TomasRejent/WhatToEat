/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.gui.view;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.KeywordLabelFactory;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author Tomas Rejent
 */
public final class IngredientList extends ListView<IngredientView>{

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientList.class);

    private final PieceConversionService pieceConversionService = ServiceHolder.getPieceConversionService();

    private final ObservableList<IngredientView> ingredientList = FXCollections.observableList(new LinkedList<IngredientView>());

    private final IngredientService ingredientService;

    public IngredientList() {
        setItems(ingredientList);
        setCellFactory((ListView<IngredientView> param) -> new IngredientViewCell(pieceConversionService));
        this.ingredientService = ServiceHolder.getIngredientInfoService();
    }

    public void setServings(int servings){
        for(IngredientView ingredient : ingredientList){
            ingredient.setServings(servings);
        }
        this.refresh();
    }

    public void setIngredients(Collection<OldRecipeIngredient> ingredients) {
        ingredientList.clear();
        for (OldRecipeIngredient ingredient : ingredients) {
            OldIngredient ingredientInfo = ingredientService.getIngredientByKey(ingredient.getIngredientKey());
            if(ingredientInfo == null){
                LOGGER.warn("RecipeIngredient {} has no ingredient info.", ingredient);
                continue;
            }

            ingredientList.add(new IngredientView(ingredient, ingredientInfo));
        }
        Collections.sort(ingredientList);
    }

    public void setIngredientViews(final Collection<IngredientView> ingredients){
        ingredientList.clear();
        ingredientList.addAll(ingredients);
        Collections.sort(ingredientList);
    }

    private static class IngredientViewCell extends ListCell<IngredientView>{

        private static final double ITEM_ELEMENT_SPACING = 5;

        private final HBox cellBox = new HBox();
        private final Label nameText = new Label();
        private final Label quantityText = new Label();
        private final FlowPane keywordsPane = new FlowPane();
        private final PieceConversionService pieceConversionService;

        public IngredientViewCell(PieceConversionService pieceConversionService) {
            keywordsPane.setHgap(ITEM_ELEMENT_SPACING);
            cellBox.getChildren().addAll(quantityText, nameText, keywordsPane);
            cellBox.setSpacing(ITEM_ELEMENT_SPACING);
            this.pieceConversionService = pieceConversionService;
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
            quantityText.setText(getQuantityText(item));
            nameText.setText(item.getName());
            updateKeywords(item.getKeywords());
        }

        private String getQuantityText(IngredientView ingredientView){
            StringBuilder stringBuilder = new StringBuilder();
            String unitLabel = I18n.getText(ingredientView.getIngredientUnit().getLabelKey());
            stringBuilder.append(ingredientView.getQuantity()).append(unitLabel);
            if(pieceConversionService.hasPieceConversion(ingredientView.getName())){
                String pieceInfo = pieceConversionService.getPieceText(ingredientView.getName(), (int) ingredientView.getQuantity());
                stringBuilder.append(" (").append(pieceInfo).append(")");
            }
            return stringBuilder.toString();
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
