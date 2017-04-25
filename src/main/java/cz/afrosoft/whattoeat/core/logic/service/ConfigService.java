package cz.afrosoft.whattoeat.core.logic.service;

import java.nio.charset.Charset;

/**
 * Service for obtaining configuration.
 * Created by afromanius on 2. 4. 2017.
 */
public interface ConfigService {

    /**
     * @return (NotNull) Return charset which should be used for encoding of data files. Default value is {@link java.nio.charset.StandardCharsets#UTF_8}.
     */
    Charset getDataFileEncoding();

    /**
     * @return (NotNull) True if json in data file should be formatted to human readable format.
     */
    boolean getPrettyJson();
}
