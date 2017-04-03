package cz.afrosoft.whattoeat.core.logic.service;

import java.nio.charset.Charset;

/**
 * Created by afromanius on 2. 4. 2017.
 */
public interface ConfigService {

    /**
     * @return (NotNull) Return charset which should be used for encoding of data files. Default value is {@link java.nio.charset.StandardCharsets#UTF_8}.
     */
    Charset getDataFileEncoding();

}
