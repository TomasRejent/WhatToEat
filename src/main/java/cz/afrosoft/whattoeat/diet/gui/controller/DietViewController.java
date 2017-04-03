/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.gui.controller;

import cz.afrosoft.whattoeat.MainApp;
import cz.afrosoft.whattoeat.core.ServiceHolder;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.service.RecipeService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.cookbook.recipe.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.diet.gui.dialog.ShoppingListDialog;
import cz.afrosoft.whattoeat.diet.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Ingredient;
import cz.afrosoft.whattoeat.diet.logic.model.Meal;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class DietViewController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietViewController.class);
    
    @FXML
    private TableView<DayDiet> dietViewTable;
    @FXML
    private TableColumn<DayDiet, String> dayColumn;
    @FXML
    private TableColumn<DayDiet, Meal> breakfastColumn;
    @FXML
    private TableColumn<DayDiet, Meal> morningSnackColumn;
    @FXML
    private TableColumn<DayDiet, Meal> soupColumn;
    @FXML
    private TableColumn<DayDiet, Meal> lunchColumn;
    @FXML
    private TableColumn<DayDiet, Meal> sideDishColumn;
    @FXML
    private TableColumn<DayDiet, Meal> afternoonSnackColumn;
    @FXML
    private TableColumn<DayDiet, Meal> dinnerColumn;
    
    private ObservableList<DayDiet> dayDietList = FXCollections.observableArrayList();
    
    private RecipeViewDialog recipeViewDialog;
    private ShoppingListDialog shoppingListDialog;

    private final RecipeService recipeService;

    public DietViewController() {
        this.recipeService = ServiceHolder.getRecipeService();
    }
  
    public static void showDiet(final Diet diet){
        final FXMLLoader fxmlLoader = new FXMLLoader(DietViewController.class.getResource("/fxml/DietView.fxml"), I18n.getResourceBundle());
        Parent dietViewPane;

        try {
            dietViewPane = fxmlLoader.load();
        } catch (IOException ex) {
            LOGGER.error("Cannot load Diet view pane.", ex);
            return;
        }

        BorderPane rootPane = MainApp.getRootPane();
        rootPane.setCenter(dietViewPane);
        final DietViewController dietViewController = fxmlLoader.getController();
        dietViewController.viewDiet(diet);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recipeViewDialog = new RecipeViewDialog();
        shoppingListDialog = new ShoppingListDialog();
        
        initColumnsValueFactories();
        dietViewTable.getSelectionModel().setCellSelectionEnabled(true);
        dietViewTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        dietViewTable.setItems(dayDietList);

        dietViewTable.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(KeyCode.ESCAPE.equals(event.getCode())){
                    dietViewTable.getSelectionModel().clearSelection();
                }
            }
        });

        dietViewTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends TablePosition> selectionChange) {
                final Set<Integer> selectedRows = new HashSet<>();
                while (selectionChange.next()) {
                    if (selectionChange.wasAdded()) {
                        for (TablePosition<DayDiet, ?> position : selectionChange.getAddedSubList()) {
                            if (position.getTableColumn().equals(dayColumn)) {
                                selectedRows.add(position.getRow());
                            }
                        }
                    }
                }
                
                if(selectedRows.isEmpty()){
                    return;
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final Set<TableColumn<DayDiet, ?>> mealColumns = getMealColumns();
                        for(Integer rowIndex : selectedRows){
                            for(TableColumn<DayDiet, ?> mealColumn : mealColumns){
                                dietViewTable.getSelectionModel().select(rowIndex, mealColumn);
                            }
                        }
                    }
                });
            }
        });
    }    

    private Set<TableColumn<DayDiet, ?>> getMealColumns(){
        final Set<TableColumn<DayDiet, ?>> columns = new HashSet<>(dietViewTable.getColumns());
        columns.remove(dayColumn);
        return columns;
    }

    public void viewDiet(final Diet diet){
        LOGGER.debug("Viewing diet: {}", diet);
        
        dayDietList.clear();
        dayDietList.addAll(diet.getDays());
    }
    
    @FXML
    public void showRecipe(){
        ObservableList<TablePosition> selectedCells = dietViewTable.getSelectionModel().getSelectedCells();
        if(selectedCells.isEmpty()){
            return;
        }
        
        TablePosition<DayDiet, ?> selectedPosition = selectedCells.get(0);
        TableColumn<DayDiet, ?> selectedColumn = selectedPosition.getTableColumn();
        if(dayColumn.equals(selectedColumn)){
            return;
        }
        Meal cellData = (Meal) selectedPosition.getTableColumn().getCellData(selectedPosition.getRow());
        Recipe recipe = recipeService.getRecipeByName(cellData.getRecipeName());
        if(recipe == null){
            return;
        }
        recipeViewDialog.showRecipe(recipe);
    }
    
    @FXML
    public void showShoppingList(){
        ObservableList<TablePosition> selectedCells = dietViewTable.getSelectionModel().getSelectedCells();
        List<Meal> mealList = (List<Meal>) selectedCells.stream().filter(
                tablePosition -> !dayColumn.equals(tablePosition.getTableColumn())
        ).map(
                tablePosition -> getCellData(tablePosition)
        ).collect(Collectors.toList());

        final Map<String, Ingredient> ingredientSumMap = new HashMap<>();
        for(Meal meal : mealList){
            if(meal == null){
                continue;
            }

            final Recipe recipe = recipeService.getRecipeByName(meal.getRecipeName());
            Set<Ingredient> ingredients = recipe.getIngredients();
            for(Ingredient recipeIngredient : ingredients){
                final String ingredientName = recipeIngredient.getName();
                final Ingredient shopingIngredient;
                if(ingredientSumMap.containsKey(ingredientName)){
                    shopingIngredient = ingredientSumMap.get(ingredientName);
                }else{
                    shopingIngredient = new Ingredient(ingredientName, 0);
                    ingredientSumMap.put(ingredientName, shopingIngredient);
                }

                shopingIngredient.setQuantity(shopingIngredient.getQuantity() + recipeIngredient.getQuantity()*meal.getServings());
            }
        }

        shoppingListDialog.showShoppingList(ingredientSumMap.values());
    }
    
    private <T> T getCellData(TablePosition<DayDiet, T> tablePosition){
        T cellData = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
        return cellData;
    }
    
    private void initColumnsValueFactories() {
        dayColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, String> param) -> {
            final LocalDate day = param.getValue().getDay();
            final String dayString = day == null ? StringUtils.EMPTY : day.toString();
            final ReadOnlyObjectWrapper<String> cell = new ReadOnlyObjectWrapper<>(dayString);
            return cell;
        });
        breakfastColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getBreakfast()));
        morningSnackColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getMorningSnack()));
        soupColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getSoup()));
        lunchColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getLunch()));
        sideDishColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getSideDish()));
        afternoonSnackColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getAfternoonSnack()));
        dinnerColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, Meal> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getDinner()));
    }
    
}
