/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.oldclassesformigrationonly.BasicConversionInfo;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.PieceConversionInfo;

import java.util.List;

/**
 *
 * @author afromanius
 */
public interface PieceConversionService {

    List<BasicConversionInfo> getAll();

    boolean hasPieceConversion(String ingredientName);

    PieceConversionInfo getPieceConversionInfo(String ingredientName);

    int getPieces(String ingredientName, int grams);

    String getPieceText(String ingredientName, int grams);

    void saveOrUpdate(PieceConversionInfo pieceConversionInfo);

    void delete(PieceConversionInfo pieceConversionInfo);
}
