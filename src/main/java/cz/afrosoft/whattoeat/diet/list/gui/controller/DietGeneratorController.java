package cz.afrosoft.whattoeat.diet.list.gui.controller;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietCreateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * @author Tomas Rejent
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DietGeneratorController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker fromField;
    @FXML
    private DatePicker toField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ComboBox<GeneratorType> generatorField;
    @FXML
    private Pane generatorGuiPane;

    private GeneratorGui<?> generatorGui;

    @Autowired
    private DietService dietService;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private MenuController menuController;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setupGeneratorComboBox();
    }

    private void setupGeneratorComboBox() {
        ComboBoxUtils.initLabeledComboBox(generatorField, GeneratorType.values());
        generatorField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                generatorGui = generatorService.getGuiForGenerator(newValue);
                generatorGui.getNode().ifPresent(node -> {
                    generatorGuiPane.getChildren().clear();
                    generatorGuiPane.getChildren().addAll(node);
                });
            }
        });
    }

    @FXML
    public void generateDiet() {
        DietCreateObject createObject = fillCreateObject(dietService.getCreateObject());
        dietService.create(createObject);
        menuController.showDietList();
    }

    private DietCreateObject fillCreateObject(final DietCreateObject createObject) {
        Validate.notNull(createObject);
        LocalDate from = fromField.getValue();
        LocalDate to = toField.getValue();

        generatorGui.setInterval(from, to);
        createObject
            .setName(nameField.getText())
            .setFrom(from)
            .setTo(to)
            .setDescription(descriptionField.getText())
            .setGenerator(generatorField.getValue())
            .setGeneratorParams(generatorGui.getParameters());
        return createObject;
    }
}
