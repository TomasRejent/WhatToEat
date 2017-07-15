/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.gui.controller;

import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeAddDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeOld;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.ServiceHolder;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class RecipeListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeListController.class);
    private static final String KEYWORD_SEPARATOR = ",";
    private static final String DELETE_CONFIRM_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.delete.recipe.title";
    private static final String DELETE_CONFIRM_MESSAGE_KEY = "cz.afrosoft.whattoeat.dialog.delete.recipe.messages";
    private final ObservableList<RecipeOld> tableRowsList = FXCollections.observableArrayList();
    private final RecipeService recipeService;
    @FXML
    private TableView<RecipeOld> recipeTable;
    @FXML
    private TableColumn<RecipeOld, String> nameColumn;
    @FXML
    private TableColumn<RecipeOld, String> recipeTypeColumn;
    @FXML
    private TableColumn<RecipeOld, String> tasteColumn;
    @FXML
    private TableColumn<RecipeOld, String> preparationTimeColumn;
    @FXML
    private TableColumn<RecipeOld, String> ratingColumn;
    @FXML
    private TableColumn<RecipeOld, String> keywordsColumn;

    public RecipeListController() {
        this.recipeService = ServiceHolder.getRecipeService();
    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Controller init");
        initColumnsValueFactories();
        setUpTableEvents();
        recipeTable.setItems(tableRowsList);

        refreshRecipeList();
    }


    private void fillRecipeTable(Collection<RecipeOld> recipes) {
        tableRowsList.clear();
        tableRowsList.addAll(recipes);
    }

    private void initColumnsValueFactories(){
        nameColumn.setCellValueFactory((TableColumn.CellDataFeatures<RecipeOld, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        recipeTypeColumn.setCellValueFactory((TableColumn.CellDataFeatures<RecipeOld, String> param) -> getValueFromRecipeTypeSet(param.getValue().getRecipeTypes()));
        tasteColumn.setCellValueFactory((TableColumn.CellDataFeatures<RecipeOld, String> param) -> getLabel(param.getValue().getTaste()));
        preparationTimeColumn.setCellValueFactory((TableColumn.CellDataFeatures<RecipeOld, String> param) -> getLabel(param.getValue().getPreparationTime()));
        ratingColumn.setCellValueFactory((TableColumn.CellDataFeatures<RecipeOld, String> param) -> new ReadOnlyObjectWrapper<>(String.valueOf(param.getValue().getRating())));
        keywordsColumn.setCellValueFactory((TableColumn.CellDataFeatures<RecipeOld, String> param) -> getValueFromSet(param.getValue().getKeywords()));
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

    private final void showRecipe(final RecipeOld recipe) {
        try{
            RecipeViewDialog dialog = new RecipeViewDialog();
            dialog.showRecipe(recipe);
        }catch(Exception e){
            LOGGER.error("Cannot display recipe.", e);
            DialogUtils.showErrorDialog(e.getMessage());
        }
    }



    private final void setUpTableEvents(){
        recipeTable.setRowFactory((tableView) -> {
            TableRow<RecipeOld> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2 && (! row.isEmpty()) ){
                    RecipeOld recipe = row.getItem();
                    showRecipe(recipe);
                }
            });

            return row;
        });
    }

    private RecipeOld getSelectedRecipe() {
        return recipeTable.getSelectionModel().getSelectedItem();
    }

    private void refreshRecipeList(){
        fillRecipeTable(recipeService.getAllRecipes().stream().sorted().collect(Collectors.toList()));
    }

    @FXML
    private void showRecipe(ActionEvent actionEvent){
        RecipeOld selectedItem = getSelectedRecipe();
        showRecipe(selectedItem);
    }

    @FXML
    private void addRecipe(ActionEvent actionEvent){
        RecipeAddDialog recipeAddDialog = new RecipeAddDialog();
        Optional<RecipeOld> newRecipe = recipeAddDialog.showAndWait();
        LOGGER.debug("Returned recipe: {}", newRecipe);
        if(newRecipe.isPresent()){
            recipeService.addRecipe(newRecipe.get());
            refreshRecipeList();
        }
    }

    @FXML
    private void editRecipe(ActionEvent actionEvent){
        RecipeOld selectedRecipe = getSelectedRecipe();
        if(selectedRecipe == null){
            LOGGER.warn("No recipe is selected.");
            return;
        }
        RecipeAddDialog recipeAddDialog = new RecipeAddDialog();
        Optional<RecipeOld> editRecipe = recipeAddDialog.editRecipe(selectedRecipe);
        if(editRecipe.isPresent()){
            recipeService.updateRecipe(editRecipe.get());
            refreshRecipeList();
        }
    }

    @FXML
    private void deleteRecipe(ActionEvent actionEvent){
        LOGGER.debug("Delete recipe action called.");
        RecipeOld selectedRecipe = getSelectedRecipe();
        if(selectedRecipe == null){
            LOGGER.warn("No recipe is selected.");
            return;
        }

        if(DialogUtils.showConfirmDialog(I18n.getText(DELETE_CONFIRM_TITLE_KEY), I18n.getText(DELETE_CONFIRM_MESSAGE_KEY))){
            recipeService.deleteRecipe(selectedRecipe);
            refreshRecipeList();
        }
    }
    
}
