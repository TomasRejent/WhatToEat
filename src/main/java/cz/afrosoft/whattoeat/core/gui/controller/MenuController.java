package cz.afrosoft.whattoeat.core.gui.controller;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.PAGE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for menu bar. Switches displayed pages according to pressed button.
 * @author Tomas Rejent
 */
@Controller
public class MenuController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @FXML
    private BorderPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        LOGGER.debug("Initializing MenuController.");
    }    
    
    @FXML
    private void showRecipeList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Recipes page.");
        showPage(PAGE.RECIPES);
    }
    
    @FXML
    private void showIngredientList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Ingredients page.");
        showPage(PAGE.INGREDIENTS);
    }

    @FXML
    private void showFoodList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Food list page.");
        showPage(PAGE.FOOD_LIST);
    }
    
    @FXML
    private void showGenerator(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Generator page.");
        showPage(PAGE.GENERATOR);
    }

    /**
     * Shows specified page in center of root border pane. If page cannot be loaded by
     * FXML Loader, then label with error message is shown instead of page.
     *
     * @param page (NotNull) Page to show in center of root border pane.
     */
    private void showPage(final PAGE page) {
        Validate.notNull(page);
        try {
            rootPane.setCenter(Main.loadPage(page));
        } catch (IOException ex) {
            LOGGER.error("Cannot show page: {}", page, ex);
            rootPane.setCenter(new Label(I18n.getText("cz.afrosoft.whattoeat.menu.error", ex.getMessage())));
        }
    }
}
