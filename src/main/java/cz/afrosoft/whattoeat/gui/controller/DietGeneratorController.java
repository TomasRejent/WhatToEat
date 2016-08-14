/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.gui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.afrosoft.whattoeat.ServiceHolder;
import cz.afrosoft.whattoeat.gui.I18n;
import cz.afrosoft.whattoeat.gui.dialog.RecipeViewDialog;
import cz.afrosoft.whattoeat.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.logic.generator.Generator;
import cz.afrosoft.whattoeat.logic.model.Diet;
import cz.afrosoft.whattoeat.logic.model.Recipe;
import cz.afrosoft.whattoeat.logic.model.dto.GeneratorParameters;
import cz.afrosoft.whattoeat.logic.services.GeneratorService;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Tomas Rejent
 */
public class DietGeneratorController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietGeneratorController.class);

    private static final String ERROR_MSG_NULL_GENERATOR = "Cannot generate diet when generator is not selected.";

    private static final String INFO_HEADER_DIET_GENERATED_KEY = "cz.afrosoft.whattoeat.diet.generator.dialog.generated.header";

    private final GeneratorService generatorService = ServiceHolder.getGeneratorService();

    @FXML
    private ComboBox<Generator> generatorField;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker fromField;

    @FXML
    private DatePicker toField;

    @FXML
    private CheckBox breakfastField;

    @FXML
    private CheckBox snackField;
    @FXML
    private CheckBox soupField;
    @FXML
    private CheckBox lunchField;
    @FXML
    private CheckBox sideDishField;
    @FXML
    private CheckBox afternoonSnackField;
    @FXML
    private CheckBox dinnerField;

    private final ObservableList<Generator> generatorList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGGER.info("Initializing DietGeneratorController.");

        final Callback<ListView<Generator>, ListCell<Generator>> cellFactory = (ListView<Generator> param) -> createGeneratorCell();

        generatorField.setButtonCell(cellFactory.call(null));
        generatorField.setCellFactory(cellFactory);

        generatorList.addAll(generatorService.getGenerators());
        generatorField.setItems(generatorList);
    }



    private ListCell<Generator> createGeneratorCell(){
        return new ListCell<Generator>() {
            @Override
            protected void updateItem(final Generator generator, final boolean empty) {
                super.updateItem(generator, empty);
                
                if (empty || generator == null) {
                    setText("Empty");
                }else{
                    setText(generator.getName());
                }
            }

        };
    }

    @FXML
    public void generateDiet(ActionEvent actionEvent){
        final Generator generator = generatorField.getValue();
        if(generator == null){
            DialogUtils.showErrorDialog(ERROR_MSG_NULL_GENERATOR);
            LOGGER.error(ERROR_MSG_NULL_GENERATOR);
            return;
        }
        
        Diet generatedDiet = generatorService.generateAndSaveDiet(generator, getGeneratorParameters());
        DietViewController.showDiet(generatedDiet);
    }

    private GeneratorParameters getGeneratorParameters(){
        GeneratorParameters parameters = new GeneratorParameters();
        parameters.setName(nameField.getText());
        parameters.setFrom(fromField.getValue());
        parameters.setTo(toField.getValue());
        parameters.setBreakfast(breakfastField.isSelected());
        parameters.setMorningSnack(snackField.isSelected());
        parameters.setSoup(soupField.isSelected());
        parameters.setLunch(lunchField.isSelected());
        parameters.setSideDish(sideDishField.isSelected());
        parameters.setAfternoonSnack(afternoonSnackField.isSelected());
        parameters.setDinner(dinnerField.isSelected());
        return parameters;
    }

}
