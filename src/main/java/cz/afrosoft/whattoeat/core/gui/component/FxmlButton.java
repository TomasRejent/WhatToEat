package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Abstract class for creating custom buttons from fxml. Extending class is set as both root and controller of specified
 * fxml.
 *
 * @author Tomas Rejent
 */
abstract class FxmlButton extends Button {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddButton.class);

    /**
     * Loads button definition from fxml. If there is error during fxml loading exception dialog is displayed.
     *
     * @param fxmlPath (NotBlank) Path to fxml file containing definition of button. Root of fxml must be type of
     *                 {@link Button}.
     */
    FxmlButton(final String fxmlPath) {
        Validate.notBlank(fxmlPath);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath), I18n.getResourceBundle());
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            LOGGER.error("Cannot load button component from fxml {}.", fxmlPath, e);
            DialogUtils.showExceptionDialog("Cannot load icon button component.", e);
        }
    }
}