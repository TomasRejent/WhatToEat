package cz.afrosoft.whattoeat.core.gui.component;

import javafx.scene.control.Button;
import org.apache.commons.lang3.Validate;

/**
 * Abstract class for creating custom buttons from fxml. Extending class is set as both root and controller of specified
 * fxml.
 *
 * @author Tomas Rejent
 */
abstract class FxmlButton extends Button {

    /**
     * Loads button definition from fxml. If there is error during fxml loading exception dialog is displayed.
     *
     * @param fxmlPath (NotBlank) Path to fxml file containing definition of button. Root of fxml must be type of
     *                 {@link Button}.
     */
    FxmlButton(final String fxmlPath) {
        Validate.notBlank(fxmlPath);

        ComponentUtil.initFxmlComponent(this, fxmlPath);
    }
}