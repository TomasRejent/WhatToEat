/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.core.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.awt.event.MouseEvent;
import java.util.function.Function;

/**
 *
 * @author Tomas Rejent
 */
public final class KeywordLabelFactory {

    private static final CornerRadii KEYWORD_CORNERS = new CornerRadii(15);
    private static final Border KEYWORD_BORDER = createKeywordBorder();
    private static final Background KEYWORD_BACKGROUND = createKeywordBackground();
    private static final Insets KEYWORD_PADDING = new Insets(2,5,2,5);   

    public static Label createKeywordLabel(final String keyword){
        final Label keywordLabel = new Label(keyword);
        keywordLabel.setPadding(KEYWORD_PADDING);
        keywordLabel.setBorder(KEYWORD_BORDER);
        keywordLabel.setBackground(KEYWORD_BACKGROUND);
        return keywordLabel;
    }

    public static Label createRemovableKeywordLabel(String keyword, Function<String, Void> removeFunction){
        final Label keywordLabel = createKeywordLabel(keyword);

        final Button removeButton = new Button("X");
        removeButton.setBackground(Background.EMPTY);
        removeButton.setFont(Font.font(10));
        removeButton.setOnAction(event -> removeFunction.apply(keywordLabel.getText()));
        keywordLabel.setGraphic(removeButton);
        keywordLabel.setContentDisplay(ContentDisplay.RIGHT);

        return keywordLabel;
    }

    private static Border createKeywordBorder(){
        Border border = new Border(new BorderStroke(Paint.valueOf("gray"), BorderStrokeStyle.SOLID, KEYWORD_CORNERS, BorderWidths.DEFAULT));
        return border;
    }

    private static Background createKeywordBackground(){
        Background background = new Background(new BackgroundFill(Paint.valueOf("rgb(200,200,255)"), KEYWORD_CORNERS, Insets.EMPTY));
        return background;
    }

}
