/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.gui.dialog;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldIngredientList;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldRecipeIngredient;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;

import java.util.Collection;

/**
 *
 * @author Tomas Rejent
 */
public class ShoppingListDialog extends Dialog<Void>{

    private static final String TITLE_KEY = "cz.afrosoft.whattoeat.dietview.dialog.shoppping.title";
    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private final OldIngredientList ingredientList = new OldIngredientList();

    public ShoppingListDialog() {
        super();
        this.setTitle(I18n.getText(TITLE_KEY));
        this.setResizable(true);
        this.initModality(Modality.NONE);
        this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        setupLayout();
    }

    public void showShoppingList(final Collection<OldRecipeIngredient> ingredients) {
        ingredientList.setIngredients(ingredients);
        this.show();
    }

    private void setupLayout(){
        ingredientList.setPrefWidth(WIDTH);
        ingredientList.setPrefHeight(HEIGHT);
        this.getDialogPane().setContent(ingredientList);
    }

}
