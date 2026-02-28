package cz.afrosoft.whattoeat.core.logic.service;

import cz.afrosoft.whattoeat.core.data.exception.DataLoadException;
import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of {@link ConfigService} based on property file.
 * Created by afromanius on 2. 4. 2017.
 */
@Service
public final class ConfigServiceImpl implements ConfigService{

    private static final String GUI_MAIN_WINDOW_POSITION_X = "gui.main.window.position.x";
    private static final String GUI_MAIN_WINDOW_POSITION_Y = "gui.main.window.position.y";
    private static final String GUI_MAIN_WINDOW_WIDTH = "gui.main.window.width";
    private static final String GUI_MAIN_WINDOW_HEIGHT = "gui.main.window.height";

    private static final int GUI_MAIN_WINDOW_POSITION_X_DEFAULT = 0;
    private static final int GUI_MAIN_WINDOW_POSITION_Y_DEFAULT = 0;
    private static final int GUI_MAIN_WINDOW_WIDTH_DEFAULT = 800;
    private static final int GUI_MAIN_WINDOW_HEIGHT_DEFAULT = 600;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

    private final Properties properties;

    private ConfigServiceImpl() {
        properties = new Properties(createDefaultProperties());

        try (FileInputStream inputStream = new FileInputStream(LocationUtils.getConfigFile())){
            properties.load(inputStream);
        } catch (DataLoadException | IOException ex) {
            LOGGER.warn("Cannot load configuration. Default configuration will be used.", ex);
        }
    }

    @Override
    public int getMainWindowPositionX() {
        final String mainWindowPositionX = properties.getProperty(GUI_MAIN_WINDOW_POSITION_X);
        try {
            return Integer.parseInt(mainWindowPositionX);
        }  catch (NumberFormatException ex) {
            LOGGER.warn("Cannot parse main window position x. Default value will be used.", ex);
            return GUI_MAIN_WINDOW_POSITION_X_DEFAULT;
        }
    }

    @Override
    public int getMainWindowPositionY() {
        final String mainWindowPositionY = properties.getProperty(GUI_MAIN_WINDOW_POSITION_Y);
        try {
            return Integer.parseInt(mainWindowPositionY);
        } catch (NumberFormatException ex) {
            LOGGER.warn("Cannot parse main window position y. Default value will be used.", ex);
            return GUI_MAIN_WINDOW_POSITION_Y_DEFAULT;
        }
    }

    @Override
    public int getMainWindowWidth() {
        final String mainWindowWidth = properties.getProperty(GUI_MAIN_WINDOW_WIDTH);
        try {
            return Integer.parseInt(mainWindowWidth);
        }catch (NumberFormatException ex) {
            LOGGER.warn("Cannot parse main window width. Default value will be used.", ex);
            return GUI_MAIN_WINDOW_WIDTH_DEFAULT;
        }
    }

    @Override
    public int getMainWindowHeight() {
        final String mainWindowHeight = properties.getProperty(GUI_MAIN_WINDOW_HEIGHT);
        try {
            return Integer.parseInt(mainWindowHeight);
        }catch (NumberFormatException ex) {
            LOGGER.warn("Cannot parse main window height. Default value will be used.", ex);
            return GUI_MAIN_WINDOW_HEIGHT_DEFAULT;
        }
    }

    private Properties createDefaultProperties(){
        final Properties defaultProperties = new Properties();
        defaultProperties.setProperty(GUI_MAIN_WINDOW_POSITION_X, String.valueOf(GUI_MAIN_WINDOW_POSITION_X_DEFAULT));
        defaultProperties.setProperty(GUI_MAIN_WINDOW_POSITION_Y, String.valueOf(GUI_MAIN_WINDOW_POSITION_Y_DEFAULT));
        defaultProperties.setProperty(GUI_MAIN_WINDOW_WIDTH, String.valueOf(GUI_MAIN_WINDOW_WIDTH_DEFAULT));
        defaultProperties.setProperty(GUI_MAIN_WINDOW_HEIGHT, String.valueOf(GUI_MAIN_WINDOW_HEIGHT_DEFAULT));
        return defaultProperties;
    }
}
