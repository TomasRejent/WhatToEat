package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.core.gui.I18n;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.regex.Pattern;

/**
 * Field for input of duration.
 *
 * @author Tomas Rejent
 */
public class DurationField extends TextField {

    private static final String PLACEHOLDER_KEY = "cz.afrosoft.whattoeat.component.durationfields.placeholder";

    private static final String VALIDATION_REGEX = "^$|^([1-9])+[0-9]*$";
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(VALIDATION_REGEX);

    private static final String ZERO = "0";

    public DurationField() {
        textProperty().addListener(createChangeListener());
        setPromptText(I18n.getText(PLACEHOLDER_KEY));
    }

    /**
     * Creates change listener which performs validation.
     *
     * @return (NotNull)
     */
    private ChangeListener<String> createChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (StringUtils.isEmpty(newValue) || ZERO.equals(newValue)) {
                setText(StringUtils.EMPTY);
            } else if (!VALIDATION_PATTERN.matcher(newValue).matches()) {
                setText(oldValue);
            }
        };
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
