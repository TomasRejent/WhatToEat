package cz.afrosoft.whattoeat.core.gui.dialog.util;

import cz.afrosoft.whattoeat.core.gui.I18n;
import javafx.application.Platform;
import javafx.scene.control.*;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Optional;

/**
 * Provides methods for showing predefined dialogs.
 *
 * @author Tomas Rejent
 */
public class DialogUtils {

    private static final String APPLY_KEY = "cz.afrosoft.whattoeat.core.dialog.button.apply";
    private static final String OK_KEY = "cz.afrosoft.whattoeat.core.dialog.button.ok";
    private static final String CANCEL_KEY = "cz.afrosoft.whattoeat.core.dialog.button.cancel";
    private static final String CLOSE_KEY = "cz.afrosoft.whattoeat.core.dialog.button.close";
    private static final String YES_KEY = "cz.afrosoft.whattoeat.core.dialog.button.yes";
    private static final String NO_KEY = "cz.afrosoft.whattoeat.core.dialog.button.no";
    private static final String FINISH_KEY = "cz.afrosoft.whattoeat.core.dialog.button.finish";
    private static final String NEXT_KEY = "cz.afrosoft.whattoeat.core.dialog.button.next";
    private static final String PREVIOUS_KEY = "cz.afrosoft.whattoeat.core.dialog.button.previous";

    private static final String ERROR_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.error.title";
    private static final String INFO_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.info.title";
    private static final String CONFIRM_DIALOG_TITLE_KEY = "cz.afrosoft.whattoeat.dialog.confirm.title";
    private static final String EXCEPTION_DIALOG_STACK_TRACE_KEY = "cz.afrosoft.whattoeat.dialog.exception.stacktrace";

    private DialogUtils() {
        throw new IllegalStateException();
    }

    public static void showErrorDialog(final String message){
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        translateButtons(errorDialog);
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
        translateButtons(errorDialog);
        errorDialog.setTitle(I18n.getText(ERROR_DIALOG_TITLE_KEY));
        errorDialog.setHeaderText(message);
        errorDialog.getDialogPane().setPrefWidth(650);
        errorDialog.getDialogPane().setContent(titledPane);
        errorDialog.showAndWait();
    }

    public static void showInfoDialog(final String header, final String message){
        Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
        translateButtons(infoDialog);
        infoDialog.setResizable(true);
        infoDialog.setTitle(I18n.getText(INFO_DIALOG_TITLE_KEY));
        infoDialog.setHeaderText(header);
        infoDialog.setContentText(message);
        infoDialog.showAndWait();
    }

    public static boolean showConfirmDialog(final String header, final String message){
        final Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        translateButtons(confirmDialog);
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

    /**
     * Translate all existing dialog buttons. Translates any type from {@link ButtonType}. This method must be called
     * after buttons are added to dialog.
     */
    public static void translateButtons(final Dialog<?> dialog) {
        translateButton(dialog, ButtonType.APPLY, APPLY_KEY);
        translateButton(dialog, ButtonType.OK, OK_KEY);
        translateButton(dialog, ButtonType.CANCEL, CANCEL_KEY);
        translateButton(dialog, ButtonType.CLOSE, CLOSE_KEY);
        translateButton(dialog, ButtonType.YES, YES_KEY);
        translateButton(dialog, ButtonType.NO, NO_KEY);
        translateButton(dialog, ButtonType.FINISH, FINISH_KEY);
        translateButton(dialog, ButtonType.NEXT, NEXT_KEY);
        translateButton(dialog, ButtonType.PREVIOUS, PREVIOUS_KEY);
    }

    private static void translateButton(final Dialog<?> dialog, final ButtonType type, final String messageKey) {
        Optional.ofNullable(dialog.getDialogPane().lookupButton(type)).ifPresent(node -> ((Button) node).setText(I18n.getText(messageKey)));
    }
}
