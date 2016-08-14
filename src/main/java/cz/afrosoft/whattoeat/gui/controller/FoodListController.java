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
import cz.afrosoft.whattoeat.logic.model.Diet;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class FoodListController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodListController.class);

    @FXML
    private TableView<Diet> dietTable;

    @FXML
    private TableColumn<Diet, String> nameColumn;

    @FXML
    private TableColumn<Diet, String> fromColumn;

    @FXML
    private TableColumn<Diet, String> toColumn;

    private ObservableList<Diet> dietList = FXCollections.observableArrayList();

    private final DataHolderService dataHolderService;

    public FoodListController() {
        this.dataHolderService = ServiceHolder.getDataHolderService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.debug("Initilizing food list controller.");

        initColumnsValueFactories();

        final Collection<Diet> diets = dataHolderService.getDiets();
        LOGGER.debug("Loaded diets: {}", diets);
        dietList.addAll(diets);
        dietTable.setItems(dietList);
    }

    private void initColumnsValueFactories() {
        nameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Diet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        fromColumn.setCellValueFactory((TableColumn.CellDataFeatures<Diet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getFrom().toString()));
        toColumn.setCellValueFactory((TableColumn.CellDataFeatures<Diet, String> param) -> new ReadOnlyObjectWrapper<>(param.getValue().getTo().toString()));
    }

    @FXML
    private void viewDiet() {
        LOGGER.debug("Viewing diet");
        final Diet selectedDiet = dietTable.getSelectionModel().getSelectedItem();
        if(selectedDiet == null){
            LOGGER.warn("Cannot dispaly diet when none selected.");
            return;
        }
        
        DietViewController.showDiet(selectedDiet);
    }
}
