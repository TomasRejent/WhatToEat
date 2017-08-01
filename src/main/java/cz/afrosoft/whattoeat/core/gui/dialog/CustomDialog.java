package cz.afrosoft.whattoeat.core.gui.dialog;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Support for custom dialog with its content defined in fxml. Sets this class
 * as controller for fxml. Loads fxml and set it as content. {@link cz.afrosoft.whattoeat.core.gui.I18n}
 * is used for localization.
 * <p>
 * Fxml file cannot have controller attribute else dialog cannot be created, because controller is set by
 * this class. Extending class can use {@link org.springframework.stereotype.Controller} annotation to enable autowired
 * components but only in conjunction with {@link org.springframework.context.annotation.Scope} annotation set
 * to {@link org.springframework.beans.factory.config.ConfigurableBeanFactory#SCOPE_PROTOTYPE}. This is needed because
 * Spring application context is created outside JavaFX thread and dialog cannot be created outside it.
 * All other components using such dialog must be also in prototype scope.
 * <p>
 * {@inheritDoc}
 *
 * @author Tomas Rejent
 */
public abstract class CustomDialog<R> extends Dialog<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDialog.class);
    private static final String ERROR_KEY = "cz.afrosoft.whattoeat.core.dialog.error";

    /**
     * Loads dialog content from specified fxml and set this class as its controller.
     * If content cannot be loaded then error is logged and displayed in exception dialog.
     *
     * @param fxmlPath (NotBlank) Path to fxml file with definition of dialog content.
     */
    public CustomDialog(final String fxmlPath) {
        Validate.notBlank(fxmlPath);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath), I18n.getResourceBundle());
        loader.setController(this);
        Node dialogContent;
        try {
            dialogContent = loader.load();
        } catch (IOException e) {
            LOGGER.error("Cannot load custom dialog content for fxml {}.", fxmlPath, e);
            DialogUtils.showExceptionDialog(I18n.getText(ERROR_KEY), e);
            return;
        }

        this.getDialogPane().setContent(dialogContent);
    }
}