package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

/**
 * Field for input of duration.
 *
 * @author Tomas Rejent
 */
public class DurationField extends RegexRestrictedField {

    private static final String PLACEHOLDER_KEY = "cz.afrosoft.whattoeat.component.durationfields.placeholder";

    private static final String VALIDATION_REGEX = "^$|^0$|^([1-9])+[0-9]*$";

    public DurationField() {
        super(VALIDATION_REGEX);
        setPromptText(I18n.getText(PLACEHOLDER_KEY));
    }

    public Duration getDuration() {
        String textValue = getText();
        if (StringUtils.isEmpty(textValue)) {
            return Duration.ZERO;
        } else {
            return Duration.ofMinutes(Long.parseLong(getText()));
        }
    }

    public void setDuration(final Duration duration) {
        if (duration == null) {
            setText(StringUtils.EMPTY);
        } else {
            setText(String.valueOf(duration.toMinutes()));
        }
    }
}
