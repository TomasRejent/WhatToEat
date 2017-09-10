package cz.afrosoft.whattoeat.core.gui.titledpane;

import javafx.scene.control.TitledPane;
import org.apache.commons.lang3.Validate;

/**
 * Utils for {@link TitledPane} component.
 *
 * @author Tomas Rejent
 */
public final class TitledPaneUtils {

    /**
     * Set expaded property of specified Title pane. Disable animation before setting this value. Animation value is restored to its previous
     * state after expand is finished.
     *
     * @param pane     (NotNull) Titled pane to expand/collapse.
     * @param expanded True to expand. False to collapse.
     */
    public static void setExpandedWithoutAnimation(final TitledPane pane, final boolean expanded) {
        Validate.notNull(pane);

        boolean animated = pane.isAnimated();
        pane.setAnimated(false);
        pane.setExpanded(expanded);
        pane.setAnimated(animated);
    }

    private TitledPaneUtils() {
        throw new IllegalStateException("This class cannot be instanced.");
    }
}
