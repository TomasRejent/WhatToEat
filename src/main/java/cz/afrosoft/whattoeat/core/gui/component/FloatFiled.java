package cz.afrosoft.whattoeat.core.gui.component;

import javafx.beans.NamedArg;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.regex.Pattern;

/**
 * Text field allowing only input of float values. Accepts both "." and "," for decimal part separation.
 * Empty value is also allowed.
 */
public class FloatFiled extends RegexRestrictedField {

    /**
     * Creates new field and attaches change listener which validates user input. Default type of field is {@link TYPE#ALL}.
     */
    public FloatFiled() {
        this(TYPE.ALL);
    }

    /**
     * Creates new field and attaches change listener which validates user input.
     *
     * @param type (NotNull) Type of validation for field.
     */
    public FloatFiled(@NamedArg("type") final TYPE type) {
        super(type.getPattern());
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
     * @return (NotNull) Returns float value of zero if field is empty.
     */
    public float getFloatOrZero() {
        Float value = getFloat();
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    /**
     * Sets value to field.
     *
     * @param value (Nullable) Value to set
     */
    public void setFloat(final Float value) {
        if (value == null) {
            setText(StringUtils.EMPTY);
        } else {
            setText(value.toString());
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

    /**
     * Possible types of float field.
     */
    public enum TYPE {
        /**
         * Accepts only floats which are not negative ( >= 0).
         */
        NON_NEGATIVE("^$|^([0-9]*[.,])?[0-9]*$"),
        /**
         * Accepts all floats. Positive, negative and zero.
         */
        ALL("^$|^[-]?([0-9]*[.,])?[0-9]*$");

        private final Pattern pattern;

        TYPE(final String regex) {
            Validate.notBlank(regex);
            this.pattern = Pattern.compile(regex);
        }

        /**
         * @return (NotNull) Pattern for validation of corresponding type.
         */
        public Pattern getPattern() {
            return pattern;
        }
    }
}