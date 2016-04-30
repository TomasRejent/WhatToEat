/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.gui;

/**
 * Interface which specifies that implementing object has internationalized label accessible by key.
 * @author Tomas Rejent
 */
public interface Labeled {

    /**
     * @return Returns key by which internationalized label can be obtained from ResourceBundle.
     */
    String getLabelKey();

}
