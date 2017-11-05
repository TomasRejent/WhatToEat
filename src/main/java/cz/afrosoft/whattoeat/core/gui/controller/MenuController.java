package cz.afrosoft.whattoeat.core.gui.controller;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Page;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.event.ActionEvent;
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
    private void showAuthorList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Author page.");
        showPage(Page.AUTHORS);
    }

    @FXML
    private void showCookbookList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Cookbook page.");
        showPage(Page.COOKBOOKS);
    }

    @FXML
    private void showRecipeList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Recipes page.");
        showPage(Page.RECIPES);
    }
    
    @FXML
    private void showIngredientList(final ActionEvent actionEvent) {
        LOGGER.debug("Switching to Ingredients page.");
        showPage(Page.INGREDIENTS);
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
