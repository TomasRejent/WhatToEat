package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.io.IOException;

/**
 * Util class for creating custom components. Prevents code duplication of fxml loading.
 *
 * @author Tomas Rejent
 */
final class ComponentUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentUtil.class);

    /**
     * Init specified component as fxml root and controller. Inject are dependencies using spring context.
     *
     * @param component (NotNull) Component to init.
     * @param fxmlPath  (NotBlank) Path to fxml which defines UI of component.
     */
    static void initFxmlComponent(final Node component, final String fxmlPath) {
        Validate.notNull(component);
        Validate.notBlank(fxmlPath);

        Main.getApplicationContext().getAutowireCapableBeanFactory().autowireBeanProperties(component, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
        FXMLLoader loader = new FXMLLoader(component.getClass().getResource(fxmlPath), I18n.getResourceBundle());
        loader.setRoot(component);
        loader.setController(component);

        try {
            loader.load();
        } catch (IOException e) {
            LOGGER.error("Cannot load fxml component from fxml {}.", fxmlPath, e);
            DialogUtils.showExceptionDialog("Cannot load component.", e);
        }
    }

    private ComponentUtil() {
        throw new IllegalStateException("This class cannot be instanced.");
    }
}
