/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.model;

/**
 *
 * @author afromanius
 */
public interface PieceConversionInfo {

    String getIngredientName();

    int getGramsOfAveragePiece();

    String getText(int pieces);

}
