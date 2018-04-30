package cz.afrosoft.whattoeat.core.gui;

import org.apache.commons.lang3.Validate;

import javafx.scene.Node;

/**
 * @author Tomas Rejent
 */
public final class PageHolder<N extends Node, C> {

    private final N pageNode;
    private final C pageController;

    public PageHolder(final N pageNode, final C pageController) {
        Validate.notNull(pageNode);
        Validate.notNull(pageController);

        this.pageNode = pageNode;
        this.pageController = pageController;
    }

    public N getPageNode() {
        return pageNode;
    }

    public C getPageController() {
        return pageController;
    }
}
