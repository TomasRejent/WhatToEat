package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.Main;
import cz.afrosoft.whattoeat.core.gui.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

/**
 * Component displaying splash screen when application is starting. Displays application name and version.
 * Also display randomly chosen tip.
 *
 * @author Tomas Rejent
 */
public class SplashScreen extends VBox {

    private static final String TITLE_KEY = "cz.afrosoft.whattoeat.title";
    private static final String VERSION_KEY = "cz.afrosoft.whattoeat.common.version";
    private static final String TIP_KEY = "cz.afrosoft.whattoeat.splashscreen.tip";
    private static final String LOADING_KEY = "cz.afrosoft.whattoeat.splashscreen.loading";
    private static final String[] TIP_KEYS = new String[]{
            "cz.afrosoft.whattoeat.splashscreen.tip1",
            "cz.afrosoft.whattoeat.splashscreen.tip2",
            "cz.afrosoft.whattoeat.splashscreen.tip3",
    };

    private static final double WIDTH = 400;
    private static final double HEIGHT = 300;

    public SplashScreen() {
        setPrefWidth(WIDTH);
        setPrefHeight(HEIGHT);

        this.setPadding(new Insets(70, 10, 10, 10));
        this.setSpacing(5);

        Label mainLabel = createCenteredLabel(I18n.getText(TITLE_KEY));
        mainLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        Label loadingLabel = createCenteredLabel(I18n.getText(LOADING_KEY));

        Label versionLabel = new Label(I18n.getText(VERSION_KEY));

        Label versionValueLabel = createCenteredLabel(Main.class.getPackage().getImplementationVersion());
        versionValueLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        Label tipLabel = new Label(I18n.getText(TIP_KEY));
        Label tipValueLabel = new Label(getRandomTipText());
        tipValueLabel.setWrapText(true);

        getChildren().addAll(mainLabel, loadingLabel, versionLabel, versionValueLabel, tipLabel, tipValueLabel);
    }

    private Label createCenteredLabel(final String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(WIDTH);
        return label;
    }

    private String getRandomTipText() {
        int index = new Random().nextInt(TIP_KEYS.length);
        return I18n.getText(TIP_KEYS[index]);
    }
}
