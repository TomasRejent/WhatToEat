package cz.afrosoft.whattoeat.core.gui.component;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Text field allowing only input of float values. Accepts both "." and "," for decimal part separation.
 * Empty value is also allowed.
 */
public class FloatFiled extends TextField {

    private static final String VALIDATION_REGEX = "^$|^[-]?([0-9]*[.,])?[0-9]*$";
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(VALIDATION_REGEX);

    /**
     * Creates new field and attaches change listener which validates user input.
     */
    public FloatFiled() {
        textProperty().addListener(createChangeListener());
    }

    /**
     * Creates change listener which performs validation.
     *
     * @return (NotNull)
     */
    private ChangeListener<String> createChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (!VALIDATION_PATTERN.matcher(newValue).matches()) {
                setText(oldValue);
            }
        };
    }

    /**
     * @return (Nullable) Float value representing user input. If input is empty null is returned.
     */
    public Float getFloat() {
        String text = getText();
        if (StringUtils.isEmpty(text)) {
            return null;
        } else {
            return Float.valueOf(getTextWithoutComma(text));
        }
    }

    /**
     * Replaces all commas with dots so text can be parsed to float.
     *
     * @param text (NotNull) Text for replacement.
     * @return (NotNull) Text without commas.
     */
    private String getTextWithoutComma(final String text) {
        return text.replaceAll(",", ".");
    }
}