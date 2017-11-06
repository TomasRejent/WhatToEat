/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.core.gui;

import org.apache.commons.lang3.Validate;

import java.text.Collator;
import java.text.MessageFormat;
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
    private static Collator collator;

    private I18n(){}

    public static void init(final String language) {
        Locale locale = new Locale(language);
        I18n.resourceBundle = ResourceBundle.getBundle(TEXTS_BUNDLE_NAME, locale);
        I18n.collator = Collator.getInstance(locale);
    }

    /**
     * Get localized text for specified key.
     * @param key (NotBlank) Key of text.
     * @return (NotNull) Localized text.
     * @throws java.util.MissingResourceException If key is not found in loaded resource bundle.
     */
    public static String getText(final String key) {
        Validate.notBlank(key);
        return resourceBundle.getString(key);
    }

    /**
     * Get localized text for specified key and fill specified parameters to it.
     *
     * @param key    (NotBlank) Key of text.
     * @param params (NotNull) Parameters to fill into message.
     * @return (NotNull) Localized text with filled parameters.
     * @throws java.util.MissingResourceException If key is not found in loaded resource bundle.
     * @see MessageFormat#format(String, Object...).
     */
    public static String getText(final String key, final Object... params) {
        Validate.notBlank(key);
        Validate.notNull(params);

        return MessageFormat.format(getText(key), params);
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

    /**
     * Compares two Strings ignoring case using locale specific collator.
     * @param source (NotNull) First String to compare.
     * @param target (NotNull) Second String to compare.
     * @return Returns an integer less than, equal to or greater than zero depending on whether the source String is less than, equal to or greater than the target String.
     */
    public static int compareStringsIgnoreCase(final String source, final String target){
        Validate.notNull(source);
        Validate.notNull(target);
        return collator.compare(source.toLowerCase(), target.toLowerCase());
    }
}
