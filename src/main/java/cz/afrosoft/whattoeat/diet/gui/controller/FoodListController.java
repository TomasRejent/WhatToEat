/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.gui.controller;

import cz.afrosoft.whattoeat.diet.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.logic.service.DietService;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.ServiceHolder;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class FoodListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodListController.class);
    private final DietService dietService;
    @FXML
    private TableView<Diet> dietTable;
    @FXML
    private TableColumn<Diet, String> nameColumn;
    @FXML
    private TableColumn<Diet, String> fromColumn;
    @FXML
    private TableColumn<Diet, String> toColumn;
    private ObservableList<Diet> dietList = FXCollections.observableArrayList();

    public FoodListController() {
        this.dietService = ServiceHolder.getDietService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.debug("Initilizing food list controller.");
        setupTableEvents();
        initColumnsValueFactories();

        final Collection<Diet> diets = dietService.getAllDiets();
        LOGGER.debug("Loaded diets: {}", diets);
        dietList.addAll(diets);
        dietTable.setItems(dietList);
    }

    
    private void setupTableEvents (){
        dietTable.setRowFactory((tableView) -> {
            TableRow<Diet> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2 && (! row.isEmpty()) ){
                    Diet diet = row.getItem();
                    showDiet(diet);
                }
            });
            return row;
        });
    }
    
    private void initColumnsValueFactories() {
        nameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Diet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        fromColumn.setCellValueFactory((TableColumn.CellDataFeatures<Diet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getFrom().toString()));
        toColumn.setCellValueFactory((TableColumn.CellDataFeatures<Diet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getTo().toString()));
    }

    
    private void showDiet (Diet diet){
        if(diet == null){
            LOGGER.warn("Cannot dispaly diet when none selected.");
            return;
        }
        
        DietViewController.showDiet(diet);
        
    }
    
        @FXML
    private void viewDiet() {
        LOGGER.debug("Viewing diet");
        final Diet selectedDiet = dietTable.getSelectionModel().getSelectedItem();
        
        showDiet(selectedDiet);
    }
}
