/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.core.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

/**
 *
 * @author Tomas Rejent
 */
public final class KeywordLabelFactory {

    private static final CornerRadii KEYWORD_CORNERS = new CornerRadii(15);
    private static final Border KEYWORD_BORDER = createKeywordBorder();
    private static final Background KEYWORD_BACKGROUND = createKeywordBackground();
    private static final Insets KEYWORD_PADDING = new Insets(2,5,2,5);   

    public static final Label createKeywordLabel(final String keyword){
        final Label keywordLabel = new Label(keyword);
        keywordLabel.setPadding(KEYWORD_PADDING);
        keywordLabel.setBorder(KEYWORD_BORDER);
        keywordLabel.setBackground(KEYWORD_BACKGROUND);
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
