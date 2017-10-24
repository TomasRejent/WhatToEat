package cz.afrosoft.whattoeat.core.gui.component;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.Validate;

import java.util.regex.Pattern;

/**
 * Base for text fields which allows only input matching specified regexp.
 *
 * @author Tomas Rejent
 */
abstract class RegexRestrictedField extends TextField {

    /**
     * Input validation pattern.
     */
    private final Pattern pattern;

    /**
     * @param validationRegex (NotBlank) Valid regexp string for input validation.
     */
    RegexRestrictedField(final String validationRegex) {
        this(Pattern.compile(validationRegex));
    }

    /**
     * @param pattern (NotNull) Validation pattern for input validation.
     */
    RegexRestrictedField(final Pattern pattern) {
        Validate.notNull(pattern);
        this.pattern = pattern;
        textProperty().addListener(createChangeListener());
    }

    /**
     * Creates change listener which performs validation.
     *
     * @return (NotNull)
     */
    private ChangeListener<String> createChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (!pattern.matcher(newValue).matches()) {
                setText(oldValue);
            }
        };
    }
}
