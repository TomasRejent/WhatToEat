/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.gui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for holding resource bundle and obtaining localized text.
 * @author Tomas Rejent
 */
public final class I18n {

    private static final String TEXTS_BUNDLE_NAME = "text/messages";

    private static ResourceBundle resourceBundle;

    private I18n(){}

    public static void init(String language){
        Locale locale = new Locale(language);
        I18n.resourceBundle = ResourceBundle.getBundle(TEXTS_BUNDLE_NAME, locale);
    }

    public static String getText(String key){
        return resourceBundle.getString(key);
    }

    public static ResourceBundle getResourceBundle(){
        return resourceBundle;
    }

}
