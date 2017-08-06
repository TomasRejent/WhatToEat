package cz.afrosoft.whattoeat.cookbook.cookbook.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookUpdateObject;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CookbookDialog extends CustomDialog<CookbookUpdateObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookDialog.class);
    private static final String DIALOG_FXML = "/fxml/CookbookDialog.fxml";

    public CookbookDialog() {
        super(DIALOG_FXML);
    }
}
