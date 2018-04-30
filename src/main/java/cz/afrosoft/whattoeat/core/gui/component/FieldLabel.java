package cz.afrosoft.whattoeat.core.gui.component;

import javafx.scene.control.Label;
import org.apache.commons.lang3.StringUtils;

/**
 * Label which adds colon to end of text when text is not empty and does not already end with colon.
 * This enables using same messages for fields and other label, like table headers, without need to duplicate text itself
 * because of colon and non-colon version.
 *
 * @author Tomas Rejent
 */
public class FieldLabel extends Label {

    private static final String COLON = ":";

    public FieldLabel() {
        textProperty().addListener((observable, oldValue, newValue) -> {
                    if (StringUtils.isNotBlank(newValue) && !newValue.endsWith(COLON)) {
                        setText(newValue + COLON);
                    }
                }
        );
    }
}
