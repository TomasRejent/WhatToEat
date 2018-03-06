package cz.afrosoft.whattoeat.core.gui.controller;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Page;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 *
 * @author Tomas Rejent
 */
@Controller
public class MenuController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @FXML
    private BorderPane rootPane;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        LOGGER.debug("Initializing MenuController.");
    }

    @FXML
    public void showAuthorList() {
        LOGGER.debug("Switching to Author page.");
        showPage(Page.AUTHORS);
    }

    @FXML
    public void showCookbookList() {
        LOGGER.debug("Switching to Cookbook page.");
        showPage(Page.COOKBOOKS);
    }

    @FXML
    public void showRecipeList() {
        LOGGER.debug("Switching to Recipes page.");
        showPage(Page.RECIPES);
    }
    
    @FXML
    public void showIngredientList() {
        LOGGER.debug("Switching to Ingredients page.");
        showPage(Page.INGREDIENTS);
    }

    @FXML
    public void showDietList() {
        LOGGER.debug("Switching to Diet page.");
        showPage(Page.DIETS);
    }

    @FXML
    public void showDietGenerator() {
        LOGGER.debug("Switching to Diet generator page.");
        showPage(Page.DIET_GENERATOR);
    }

    /**
     * Shows specified page in center of root border pane. If page cannot be loaded by
     * FXML Loader, then label with error message is shown instead of page.
     *
     * @param page (NotNull) Page to show in center of root border pane.
     */
    private void showPage(final Page page) {
        Validate.notNull(page);
        try {
            rootPane.setCenter(Main.loadPage(page));
        } catch (IOException ex) {
            LOGGER.error("Cannot show page: {}", page, ex);
            DialogUtils.showExceptionDialog(
                    I18n.getText("cz.afrosoft.whattoeat.menu.error", page.name()),
                    ex);
        }
    }
}
