package cz.afrosoft.whattoeat.core.gui;

/**
 * Specifies that implementing object has internationalized label accessible by key.
 *
 * @author Tomas Rejent
 */
public interface Labeled {

    /**
     * @return Returns key by which internationalized label can be obtained from ResourceBundle.
     */
    String getLabelKey();

}
