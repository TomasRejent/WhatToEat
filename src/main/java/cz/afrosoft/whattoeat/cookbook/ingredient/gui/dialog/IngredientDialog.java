/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.gui.dialog;

import cz.afrosoft.whattoeat.ServiceHolder;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.BasicConversionInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRow;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.PieceConversionInfo;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientInfoService;
import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.gui.KeywordLabelFactory;
import cz.afrosoft.whattoeat.gui.controller.suggestion.FullWordSuggestionProvider;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.geometry.Orientation;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.PrefixSelectionChoiceBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dialog for adding and editing of {@link IngredientInfo}.
 * @author Tomas Rejent
 */
public class IngredientDialog extends Dialog<IngredientRow>{

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDialog.class);

    private static final String I18N_NAME = "cz.afrosoft.whattoeat.ingredients.dialog.name";
    private static final String I18N_UNIT = "cz.afrosoft.whattoeat.ingredients.dialog.unit";
    private static final String I18N_PRICE = "cz.afrosoft.whattoeat.ingredients.dialog.price";
    private static final String I18N_CONVERSION = "cz.afrosoft.whattoeat.ingredients.dialog.conversion";
    private static final String I18N_KEYWORDS = "cz.afrosoft.whattoeat.ingredients.dialog.keywords";

    private final VBox verticalBox = new VBox();
    private final HBox firstRow = new HBox();
    private final HBox secondRow = new HBox();
    private final HBox thirdRow = new HBox();
    private final HBox fourthRow = new HBox();

    private final Label nameLabel = new Label();
    private final TextField nameField = new TextField();
    private final Label unitLabel = new Label();
    private final PrefixSelectionChoiceBox<IngredientUnit> unitField = new PrefixSelectionChoiceBox<>();
    private final Label priceLabel = new Label();
    private final TextField priceField = new TextField();
    private final Label conversionLabel = new Label();
    private final TextField conversionField = new TextField();
    private final Label keywordLabel = new Label();
    private final TextField keywordField = new TextField();
    private final FlowPane keywordsView = new FlowPane(Orientation.HORIZONTAL);
    private final Set<String> keywordSet = new HashSet<>();

    private final IngredientInfoService ingredientInfoService = ServiceHolder.getIngredientInfoService();

    public IngredientDialog() {
        LOGGER.debug("Creating Ingredient add/edit dialog.");
        this.setResizable(true);
        this.initModality(Modality.APPLICATION_MODAL);
        this.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        this.setupLayout();
        this.setupKeywordSuggestion();
        this.setupResultConverter();
    }

    public IngredientRow showForCreate(){
        clearDialog();
        nameField.setEditable(true);
        final Optional<IngredientRow> dialogResult = this.showAndWait();
        return dialogResult.orElse(null);
    }

    public IngredientRow showForEdit(final IngredientRow editRow){
        Validate.notNull(editRow);
        final IngredientInfo ingredientInfo = editRow.getIngredientInfo();
        Validate.notNull(ingredientInfo);

        clearDialog();
        nameField.setText(ingredientInfo.getName());
        nameField.setEditable(false);
        unitField.getSelectionModel().select(ingredientInfo.getIngredientUnit());
        priceField.setText(String.valueOf(ingredientInfo.getPrice()));
        for(String keyword : ingredientInfo.getKeywords()){
            addKeyword(keyword);
        }

        final PieceConversionInfo pieceConversionInfo = editRow.getPieceConversionInfo();
        if(pieceConversionInfo != null){
            conversionField.setText(String.valueOf(pieceConversionInfo.getGramsOfAveragePiece()));
        }

        final Optional<IngredientRow> dialogResult = this.showAndWait();
        return dialogResult.orElse(null);
    }

    private void setupResultConverter(){
        this.setResultConverter((buttonType) -> {
            if(ButtonType.FINISH.equals(buttonType)){
                return fillFromDialog();
            }else{
                return null;
            }
        });
    }

    private void clearDialog(){
        nameField.setText(StringUtils.EMPTY);
        unitField.getSelectionModel().clearSelection();
        priceField.setText(StringUtils.EMPTY);
        conversionField.setText(StringUtils.EMPTY);
        keywordSet.clear();
        keywordsView.getChildren().clear();
    }

    private IngredientRow fillFromDialog(){
        final IngredientInfo ingredientInfo = new IngredientInfo();
        ingredientInfo.setName(nameField.getText());
        ingredientInfo.setIngredientUnit(unitField.getValue());
        ingredientInfo.setPrice(Float.valueOf(priceField.getText()));
        ingredientInfo.setKeywords(keywordSet);

        final PieceConversionInfo pieceConversionInfo = getPieceConversionInfo(ingredientInfo.getName());
        final IngredientRow ingredientRow = new IngredientRow(ingredientInfo, pieceConversionInfo);
        return ingredientRow;
    }

    private PieceConversionInfo getPieceConversionInfo(final String ingredientName){
        final PieceConversionInfo pieceConversionInfo;
        final String conversionString = conversionField.getText();
        if(StringUtils.isNotBlank(conversionString)){
            final int conversion = Integer.valueOf(conversionString);
            pieceConversionInfo = new BasicConversionInfo(ingredientName, conversion);
        }else{
            pieceConversionInfo = null;
        }

        return pieceConversionInfo;
    }

    private void setupLayout(){
        setTextToLabel(nameLabel, I18N_NAME);
        firstRow.getChildren().addAll(nameLabel, nameField);

        setTextToLabel(unitLabel, I18N_UNIT);
        setTextToLabel(priceLabel, I18N_PRICE);
        unitField.getItems().addAll(IngredientUnit.values());
        secondRow.getChildren().addAll(unitLabel, unitField, priceLabel, priceField);

        setTextToLabel(conversionLabel, I18N_CONVERSION);
        thirdRow.getChildren().addAll(conversionLabel, conversionField);

        setTextToLabel(keywordLabel, I18N_KEYWORDS);
        fourthRow.getChildren().addAll(keywordLabel, keywordField);

        verticalBox.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, keywordsView);
        verticalBox.setFillWidth(true);
        this.getDialogPane().setContent(verticalBox);
    }

    private void setTextToLabel(final Label label, final String messageKey){
        label.setText(I18n.getText(messageKey));
    }

    private void addKeyword(final String keyword) {
        keywordField.clear();
        if (keywordSet.contains(keyword)) {
            return;
        }

        keywordSet.add(keyword);
        final Label keywordLabel = KeywordLabelFactory.createKeywordLabel(keyword);
        keywordsView.getChildren().add(keywordLabel);
    }

    private void setupKeywordSuggestion(){
        AutoCompletionBinding<String> autoCompletion = TextFields.bindAutoCompletion(keywordField, new FullWordSuggestionProvider(ingredientInfoService.getAllIngredientKeywords()));

        autoCompletion.setOnAutoCompleted((completionEvent -> {
            final String keyword = completionEvent.getCompletion();
            addKeyword(keyword);
        }));

        keywordField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    keyEvent.consume();
                    addKeyword(keywordField.getText());
                    break;
                default:
                    break;
                }
        });
    }

}
