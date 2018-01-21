package cz.afrosoft.whattoeat.core.gui.component.support;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Factory for FXML {@link Builder}. This factory returns component itself if it implements builder. Otherwise
 * {@link FXMLComponentBuilder} is used to wrap component into builder interface and forward property setting.
 *
 * Component itself is created by Spring, this is why this factory returns wrappers instead real builders for component.
 *
 * If component is not annotated with {@link FXMLComponent} then {@link JavaFXBuilderFactory} is used to create builder.
 *
 * @author Tomas Rejent
 */
@Component
public class FXMLBuilderFactory implements BuilderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLBuilderFactory.class);

    private final JavaFXBuilderFactory defaultFactory = new JavaFXBuilderFactory();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Builder<?> getBuilder(final Class<?> type) {
        if (type.isAnnotationPresent(FXMLComponent.class)) {
            LOGGER.trace("Creating Spring builder for type: {}", type);
            Object component = applicationContext.getBean(type);
            if (Builder.class.isAssignableFrom(type)) {
                return (Builder<?>) component;
            } else {
                return new FXMLComponentBuilder<>(component);
            }
        } else {
            LOGGER.trace("Creating Default builder for type: {}", type);
            return defaultFactory.getBuilder(type);
        }
    }
}
