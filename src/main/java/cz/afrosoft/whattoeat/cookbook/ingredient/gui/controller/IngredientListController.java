/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.gui.controller;

import cz.afrosoft.whattoeat.ServiceHolder;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRow;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.IngredientInfoService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for List of ingredients view.
 * @author Tomas Rejent
 */
public class IngredientListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientListController.class);

    @FXML
    private TableView<IngredientRow> ingredientTable;
    @FXML
    private TableColumn<IngredientRow, String> nameColumn;
    @FXML
    private TableColumn<IngredientRow, String> unitColumn;
    @FXML
    private TableColumn<IngredientRow, String> conversionColumn;
    @FXML
    private TableColumn<IngredientRow, String> priceColumn;
    @FXML
    private TableColumn<IngredientRow, String> keywordColumn;

    private final ObservableList<IngredientRow> tableRowsList = FXCollections.observableArrayList();

    private final IngredientInfoService ingredientInfoService = ServiceHolder.getIngredientInfoService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        initColumnsValueFactories();
        fillTable();
    }

    private void initColumnsValueFactories(){
        LOGGER.debug("Setting up column value factories for ingredients table.");
        nameColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>( param.getValue().getName()));
        unitColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>(param.getValue().getUnit()));
        conversionColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>(param.getValue().getConversion()));
        priceColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>(param.getValue().getPrice()));
        keywordColumn.setCellValueFactory((param) -> new ReadOnlyObjectWrapper<>(param.getValue().getKeywords()));
    }

    private void fillTable(){
        LOGGER.debug("Filling ingredient table with ingredients.");
        tableRowsList.clear();
        tableRowsList.addAll(ingredientInfoService.getIngredientRows());
        ingredientTable.setItems(tableRowsList);
    }

}
