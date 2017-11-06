package cz.afrosoft.whattoeat.core.gui.dialog.util;

import cz.afrosoft.whattoeat.core.gui.I18n;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Optional;

/**
 * Provides methods for showing predefined dialogs.
 *
 * @author Tomas Rejent
 */
public class DialogUtils {

    private static final String ERROR_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.error.title";
    private static final String INFO_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.info.title";
    private static final String CONFIRM_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.confirm.title";
    private static final String EXCEPTION_DIALOG_STACK_TRACE_KEY = "cz.afrosoft.whattoeat.dialog.exception.stacktrace";

    private DialogUtils() {
        throw new IllegalStateException();
    }

    public static void showErrorDialog(final String message){
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(I18n.getText(ERROR_DIALOG_TITLE_KEY));
        errorDialog.setContentText(message);
        errorDialog.showAndWait();
    }

    /**
     * Shows error dialog with specified message. Contains expandable details with stack trace of specified exception.
     *
     * @param message   (NotNull) Message to display. This string must be already localized. This method does not perform any
     *                  internationalization.
     * @param throwable (NotNull) Throwable from which stack trace is obtained.
     */
    public static void showExceptionDialog(final String message, final Throwable throwable) {
        Validate.notNull(message);
        Validate.notNull(throwable);

        TextArea stackTraceArea = new TextArea(ExceptionUtils.getStackTrace(throwable));
        stackTraceArea.setEditable(false);
        TitledPane titledPane = new TitledPane(I18n.getText(EXCEPTION_DIALOG_STACK_TRACE_KEY), stackTraceArea);
        titledPane.setExpanded(false);
        titledPane.setAnimated(false);
        //This is needed to resize dialog after stack trace is shown/hidden.
        titledPane.expandedProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(
                        () -> titledPane.getScene().getWindow().sizeToScene()
                )
        );

        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(I18n.getText(ERROR_DIALOG_TITLE_KEY));
        errorDialog.setHeaderText(message);
        errorDialog.getDialogPane().setPrefWidth(650);
        errorDialog.getDialogPane().setContent(titledPane);
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
