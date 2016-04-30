/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.gui.controller;

import cz.afrosoft.whattoeat.MainApp;
import cz.afrosoft.whattoeat.gui.I18n;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Alexandra
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void showRecipeList(ActionEvent actionEvent){
        try {
            Parent recipeListPane = FXMLLoader.load(getClass().getResource("/fxml/RecipeList.fxml"), I18n.getResourceBundle());
            BorderPane rootPane = MainApp.getRootPane();
            rootPane.setCenter(recipeListPane);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void showFoodList(ActionEvent actionEvent){
        try {
            Parent foodListPane = FXMLLoader.load(getClass().getResource("/fxml/FoodList.fxml"), I18n.getResourceBundle());
            BorderPane rootPane = MainApp.getRootPane();
            rootPane.setCenter(foodListPane);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
