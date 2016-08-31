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
import cz.afrosoft.whattoeat.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.logic.model.DayDiet;
import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.Meal;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
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
    private DataHolderService dataHolderService;
    
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
        dataHolderService = ServiceHolder.getDataHolderService();
        
        initColumnsValueFactories();
        dietViewTable.getSelectionModel().setCellSelectionEnabled(true);
        dietViewTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        dietViewTable.setItems(dayDietList);
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
        
        TablePosition<DayDiet, String> selectedPosition = selectedCells.get(0);
        TableColumn<DayDiet, String> selectedColumn = selectedPosition.getTableColumn();
        if(dayColumn.equals(selectedColumn)){
            return;
        }
        String cellData = selectedPosition.getTableColumn().getCellData(selectedPosition.getRow());
        Recipe recipe = dataHolderService.getRecipeByName(cellData);
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
        
    }
    
    private <T> T getCellData(TablePosition<DayDiet, T> tablePosition){
        T cellData = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
        return cellData;
    }
    
    private void initColumnsValueFactories() {
        LOGGER.debug("Column {}", dayColumn);
        
        dayColumn.setCellValueFactory((TableColumn.CellDataFeatures<DayDiet, String> param) -> {
            final LocalDate day = param.getValue().getDay();
            final String dayString = day == null ? StringUtils.EMPTY : day.toString();
            return new ReadOnlyObjectWrapper<>(dayString);
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
