/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.oldclassesformigrationonly;

/**
 *
 * @author afromanius
 */
public interface PieceConversionInfo{

    String getIngredientKey();

    int getGramsOfAveragePiece();

    String getText(int pieces);

}
