/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.gui.controller;

import cz.afrosoft.whattoeat.core.ServiceHolder;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeAddDialog;
import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

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


    private final RecipeService recipeService;

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

        fillRecipeTable(recipeService.getAllRecipes().stream().sorted().collect(Collectors.toList()));
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

    private final void showRecipe( final Recipe recipe){
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
            TableRow<Recipe> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2 && (! row.isEmpty()) ){
                    Recipe recipe = row.getItem();
                    showRecipe(recipe);
                }
            });

            return row;
        });
    }


    @FXML
    private void showRecipe(ActionEvent actionEvent){
        Recipe selectedItem = recipeTable.getSelectionModel().getSelectedItem();
        showRecipe(selectedItem);
    }

    @FXML
    private void addRecipe(ActionEvent actionEvent){
        RecipeAddDialog recipeAddDialog = new RecipeAddDialog();
        Optional<Recipe> newRecipe = recipeAddDialog.showAndWait();
        LOGGER.debug("Returned recipe: {}", newRecipe);
        if(newRecipe.isPresent()){
            recipeService.addRecipe(newRecipe.get());
            tableRowsList.add(0, newRecipe.get());
        }
    }
    
}
