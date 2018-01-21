package cz.afrosoft.whattoeat.core.gui.component.support;

import javafx.css.Styleable;
import javafx.util.Builder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * Fallback builder for FXML components. This builder implements map so FXML Loader is able to pass any property to it.
 * Some common node attributes like "styleClass" are automatically set to component. If property is not recognized
 * then calling of setter by reflection is attempted.
 *
 * This is not really builder because it gets complete component as parameter. It is rather something between wrapper
 * implementing builder class and proxy for setting properties.
 *
 * @author Tomas Rejent
 */
public class FXMLComponentBuilder<T> extends HashMap<String, String> implements Builder<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLComponentBuilder.class);

    private static final String STYLE_CLASS_PROPERTY = "styleClass";

    private final T component;

    public FXMLComponentBuilder(final T component) {
        Validate.notNull(component);

        this.component = component;
    }

    @Override
    public String put(final String key, final String value) {
        LOGGER.trace("Adding property {} with value {} to component {}.", key, value, component);

        if (STYLE_CLASS_PROPERTY.equals(key) && component instanceof Styleable) {
            ((Styleable) component).getStyleClass().add(value);
        } else {
            LOGGER.warn("Property {} with value {} is not recognized. Trying to use reflection to set it.", key, value);
            try {
                Method method = component.getClass().getMethod("set" + StringUtils.capitalize(key), String.class);
                method.invoke(component, value);
            } catch (NoSuchMethodException e) {
                LOGGER.warn("Setter method for property {} does not exist. Property is skipped.", key);
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.warn("Invocation of setter method for property {} failed. Property is skipped.", key);
            }
        }

        return super.put(key, value);
    }

    @Override
    public T build() {
        LOGGER.trace("Building FXML Custom component of type: {}", component);
        return component;
    }

}
