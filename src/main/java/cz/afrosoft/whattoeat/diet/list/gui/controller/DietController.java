package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tomas Rejent
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DietController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietController.class);

    @Autowired
    private MenuController menuController;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Initializing diet controller");
    }

    /* Button actions. */

    @FXML
    public void viewDiet() {

    }

    @FXML
    public void addDiet() {
        LOGGER.debug("Add diet action triggered.");
        menuController.showDietGenerator();
    }

    @FXML
    public void deleteDiet() {

    }
}
