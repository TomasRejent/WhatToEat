package cz.afrosoft.whattoeat.core.logic.service;

import cz.afrosoft.whattoeat.core.data.exception.DataLoadException;
import cz.afrosoft.whattoeat.core.data.util.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Implementation of {@link ConfigService} based on property file.
 * Created by afromanius on 2. 4. 2017.
 */
public final class ConfigServiceImpl implements ConfigService{

    private static final String DATA_FILE_ENCODING = "data.file.encoding";
    private static final String DATA_FILE_PRETTY_JSON = "data.file.json.pretty";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);
    private static final ConfigServiceImpl ourInstance = new ConfigServiceImpl();

    public static ConfigServiceImpl getInstance() {
        return ourInstance;
    }

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
    public Charset getDataFileEncoding() {
        final String charsetName = properties.getProperty(DATA_FILE_ENCODING);
        return Charset.forName(charsetName);
    }

    public boolean getPrettyJson(){
        final String prettyJson = properties.getProperty(DATA_FILE_PRETTY_JSON);
        return Boolean.parseBoolean(prettyJson);
    }

    private Properties createDefaultProperties(){
        final Properties defaultProperties = new Properties();
        defaultProperties.setProperty(DATA_FILE_ENCODING, StandardCharsets.UTF_8.name());
        defaultProperties.setProperty(DATA_FILE_PRETTY_JSON, "false");
        return defaultProperties;
    }


}
