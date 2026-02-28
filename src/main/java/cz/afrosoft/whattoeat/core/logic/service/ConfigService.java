package cz.afrosoft.whattoeat.core.logic.service;

import java.nio.charset.Charset;

/**
 * Service for obtaining configuration.
 * Created by afromanius on 2. 4. 2017.
 */
public interface ConfigService {
    int getMainWindowPositionX();
    int getMainWindowPositionY();
    int getMainWindowWidth();
    int getMainWindowHeight();
}
