package cz.afrosoft.whattoeat.core.gui;

import cz.afrosoft.whattoeat.core.gui.component.support.FXMLBuilderFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * Factory for creating {@link FXMLLoader} with proper configuration.
 * Loaders created by this factory are supplied with:
 * <ul>
 * <li>I18n Resource bundle</li>
 * <li>Controller factory provided by Spring</li>
 * <li>Builder factory</li>
 * </ul>
 *
 * @author Tomas Rejent
 */
@Component
public class FXMLLoaderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLLoaderFactory.class);

    private final JavaFXBuilderFactory defaultFactory = new JavaFXBuilderFactory();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FXMLBuilderFactory fxmlBuilderFactory;

    /**
     * Converts supplied path to URL and calls {@link #createFXMLLoader(URL)}.
     *
     * @param fxmlPath (NotBlank) Path to fxml file te be parsed by loader.
     * @return (NotNull) Configured FXML Loader.
     * @see #createFXMLLoader(URL)
     */
    public FXMLLoader createFXMLLoader(final String fxmlPath) {
        LOGGER.trace("Creating FXML Loader for path: {}", fxmlPath);
        Validate.notBlank(fxmlPath);
        return createFXMLLoader(FXMLLoaderFactory.class.getResource(fxmlPath));
    }

    /**
     * Creates FXML Loader for specified resource. Resource bundle is added automatically so loader supports internationalization.
     * Loader supports FXML controllers loading from Spring components.
     * It also supports custom components marked with {@link cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent}
     *
     * @param fxmlUrl (NotNull)
     * @return (NotNull) Configured FXML Loader.
     */
    public FXMLLoader createFXMLLoader(final URL fxmlUrl) {
        LOGGER.trace("Creating FXML Loader for url: {}", fxmlUrl);
        Validate.notNull(fxmlUrl);

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl, I18n.getResourceBundle());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        fxmlLoader.setBuilderFactory(fxmlBuilderFactory);
        return fxmlLoader;
    }

}