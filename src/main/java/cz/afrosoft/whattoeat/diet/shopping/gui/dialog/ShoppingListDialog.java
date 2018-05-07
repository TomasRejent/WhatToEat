package cz.afrosoft.whattoeat.diet.shopping.gui.dialog;

import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;

import javax.annotation.PostConstruct;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/fxml/ShoppingListDialog.fxml")
public class ShoppingListDialog extends Dialog<Void> {

    @FXML
    private TextArea contentTextArea;

    public ShoppingListDialog() {
        setResizable(true);
        initModality(Modality.NONE);
    }

    @PostConstruct
    private void initialize() {
        setupButtons();
        setupResultConverter();
    }

    private void setupButtons() {
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        DialogUtils.translateButtons(this);
    }

    private void setupResultConverter() {
        this.setResultConverter(buttonType -> null);
    }

    public void showShoppingList(final String shoppingListContent) {
        contentTextArea.setText(shoppingListContent);
        showAndWait();
    }
}
