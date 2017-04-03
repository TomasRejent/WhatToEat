/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.core.gui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for holding resource bundle and obtaining localized text.
 * @author Tomas Rejent
 */
public final class I18n {

    private static final String TEXTS_BUNDLE_NAME = "text/messages";
    private static final String EMPTY_VALUE_KEY = "cz.afrosoft.whattoeat.value.empty";

    private static ResourceBundle resourceBundle;

    private I18n(){}

    public static void init(String language){
        Locale locale = new Locale(language);
        I18n.resourceBundle = ResourceBundle.getBundle(TEXTS_BUNDLE_NAME, locale);
    }

    /**
     * Get localized text for specified key.
     * @param key
     * @return
     */
    public static String getText(String key){
        return resourceBundle.getString(key);
    }

    /**
     * Gets localized text for representing empty value.
     * @return 
     */
    public static String getEmptyValueText(){
        return getText(EMPTY_VALUE_KEY);
    }

    public static ResourceBundle getResourceBundle(){
        return resourceBundle;
    }

}
