package cz.afrosoft.whattoeat.core.gui.component;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author Tomas Rejent
 */
public class IntegerField extends RegexRestrictedField {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[0-9]*$");

    public IntegerField() {
        super(INTEGER_PATTERN);
    }

    public Integer getInteger() {
        String text = getText();
        if (StringUtils.isEmpty(text)) {
            return null;
        } else {
            return Integer.valueOf(text);
        }
    }

    public int getIntOrZero() {
        Integer integer = getInteger();
        if (integer == null) {
            return 0;
        } else {
            return integer;
        }
    }

    public void setInteger(final Integer value) {
        if (value == null) {
            setText(StringUtils.EMPTY);
        } else {
            setText(value.toString());
        }
    }
}
