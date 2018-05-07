package cz.afrosoft.whattoeat.core.gui.component;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import org.controlsfx.glyphfont.Glyph;

/**
 * Button which displays specified icon without any text. This button can be created from FXML.
 * Icon is specified by its String name in enum {@link org.controlsfx.glyphfont.FontAwesome}.
 * <p>
 * If button with identical icon is used in multiple places with same semantic then specific button should be
 * created rather than using this one. This one is suitable for one time use of icons so there is no need to create
 * huge amount of button types.
 *
 * @author Tomas Rejent
 */
public class IconButton extends Button {

    private static final String FONT_FAMILY = "FontAwesome";
    private static final String STYLE_CLASS = "icon-button";

    public IconButton(@NamedArg("icon") final String iconName) {
        Glyph glyph = new Glyph(FONT_FAMILY, iconName);
        setGraphic(glyph);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        getStyleClass().add(STYLE_CLASS);
    }
}
