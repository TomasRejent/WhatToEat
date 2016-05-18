/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.gui.controller;

import cz.afrosoft.whattoeat.MainApp;
import cz.afrosoft.whattoeat.ServiceHolder;
import cz.afrosoft.whattoeat.data.DataHolderService;
import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.gui.Labeled;
import cz.afrosoft.whattoeat.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import cz.afrosoft.whattoeat.logic.model.enums.RecipeType;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class RecipeListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeListController.class);
    private static final String KEYWORD_SEPARATOR = ",";

    @FXML
    private TableView<Recipe> recipeTable;
    @FXML
    private TableColumn<Recipe, String> nameColumn;
    @FXML
    private TableColumn<Recipe, String> recipeTypeColumn;
    @FXML
    private TableColumn<Recipe, String> tasteColumn;
    @FXML
    private TableColumn<Recipe, String> preparationTimeColumn;
    @FXML
    private TableColumn<Recipe, String> ratingColumn;
    @FXML
    private TableColumn<Recipe, String> keywordsColumn;


    private final ObservableList<Recipe> tableRowsList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Controller init");
        initColumnsValueFactories();

        DataHolderService dataHolderService = ServiceHolder.getDataHolderService();
        fillRecipeTable(dataHolderService.getRecipes());
    }


    private void fillRecipeTable(Collection<Recipe> recipes){
        tableRowsList.addAll(recipes);
        recipeTable.setItems(tableRowsList);

    }

    private void initColumnsValueFactories(){
        nameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Recipe, String> param) -> new ReadOnlyObjectWrapper<>( param.getValue().getName()));
        recipeTypeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Recipe, String> param) -> getValueFromRecipeTypeSet(param.getValue().getRecipeTypes()));
        tasteColumn.setCellValueFactory((TableColumn.CellDataFeatures<Recipe, String> param) -> getLabel(param.getValue().getTaste()));
        preparationTimeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Recipe, String> param) -> getLabel(param.getValue().getPreparationTime()));
        ratingColumn.setCellValueFactory((TableColumn.CellDataFeatures<Recipe, String> param) -> new ReadOnlyObjectWrapper<>(String.valueOf(param.getValue().getRating())));
        keywordsColumn.setCellValueFactory((TableColumn.CellDataFeatures<Recipe, String> param) -> getValueFromSet(param.getValue().getKeywords()));
    }

    private ObservableValue<String> getLabel(Labeled labeledObject){
        Optional<Labeled> labeledOpt = Optional.ofNullable(labeledObject);
        String text = labeledOpt.map(labeled -> I18n.getText(labeled.getLabelKey())).orElse(I18n.getEmptyValueText());
        return new ReadOnlyObjectWrapper<>(text);
    }

    private ObservableValue<String> getValueFromSet(Set<String> stringSet){
        return new ReadOnlyObjectWrapper<>(StringUtils.join(stringSet, KEYWORD_SEPARATOR));
    }

    private ObservableValue<String> getValueFromRecipeTypeSet(Set<RecipeType> recipeTypeSet){
        return new ReadOnlyObjectWrapper<>(StringUtils.join(recipeTypeSet.stream().map(recipeType -> I18n.getText(recipeType.getLabelKey())).toArray(), KEYWORD_SEPARATOR));
    }

    @FXML
    private void showRecipe(ActionEvent actionEvent){

        Recipe selectedItem = recipeTable.getSelectionModel().getSelectedItem();
        RecipeViewDialog dialog = new RecipeViewDialog();
        dialog.showRecipe(selectedItem);

    }
    
}
