/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.services;

/**
 *
 * @author afromanius
 */
public interface PieceConversionService {

    boolean hasPieceConversion(String ingredientName);

    int getPieces(String ingredientName, int grams);

    String getPieceText(String ingredientName, int grams);

}
