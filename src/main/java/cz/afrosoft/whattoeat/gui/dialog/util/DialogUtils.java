/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.gui.dialog.util;

import cz.afrosoft.whattoeat.gui.I18n;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Tomas Rejent
 */
public class DialogUtils {

    private static final String ERROR_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.error.title";
    private static final String INFO_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.info.title";
    private static final String CONFIRM_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.confirm.title";

    private DialogUtils() {
        throw new IllegalStateException();
    }

    public static void showErrorDialog(final String message){
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(I18n.getText(ERROR_DIALOG_TITLE_KEY));
        errorDialog.setContentText(message);
        errorDialog.showAndWait();
    }

    public static void showInfoDialog(final String header, final String message){
        Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
        infoDialog.setResizable(true);
        infoDialog.setTitle(I18n.getText(INFO_DIALOG_TITLE_KEY));
        infoDialog.setHeaderText(header);
        infoDialog.setContentText(message);
        infoDialog.showAndWait();
    }

    public static boolean showConfirmDialog(final String header, final String message){
        final Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        confirmDialog.setTitle(I18n.getText(CONFIRM_DIALOG_TITLE_KEY));
        confirmDialog.setResizable(true);
        confirmDialog.setHeaderText(header);
        Optional<ButtonType> response = confirmDialog.showAndWait();
        if(ButtonType.YES.equals(response.orElse(null))){
            return true;
        }else{
            return false;
        }
    }

}
