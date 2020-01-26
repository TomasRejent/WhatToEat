package cz.afrosoft.whattoeat.core.gui;

import org.apache.commons.lang3.Validate;

import java.net.URL;

/**
 * Represent pages(screens) in application. Each contains path to fxml file to render it.
 */
public enum Page {
    /**
     * Root page of application rendered after startup. This page contains menu which allows switching to other pages.
     */
    ROOT("/fxml/Menu.fxml"),

    /* Recipes pages. */

    /**
     * Page with list of authors. Provides crud operations to user.
     */
    AUTHORS("/fxml/AuthorList.fxml"),
    COOKBOOKS("/fxml/CookbookList.fxml"),
    RECIPES("/fxml/RecipeList.fxml"),
    INGREDIENTS("/fxml/IngredientList.fxml"),

    /* Diet pages. */

    DIETS("/fxml/DietList.fxml"),
    DIET_GENERATOR("/fxml/DietGenerator.fxml"),
    DIET_VIEW("/fxml/DietView.fxml"),
    DIET_NUTRITION_PLAN("/fxml/DietNutritionPlan.fxml"),
    ;

    final String fxmlPath;

    /**
     * @param fxmlPath (NotBlank) Path to fxml file for this page.
     */
    Page(final String fxmlPath) {
        Validate.notBlank(fxmlPath);
        this.fxmlPath = fxmlPath;
    }

    /**
     * @return (NotNull) Path to fxml file to render this page.
     */
    public String getFxmlPath() {
        return fxmlPath;
    }

    /**
     * @return (NotNull) URL representing resource of fxml file used to render this page. This can be supplied to {@link javafx.fxml.FXMLLoader}.
     * @throws IllegalStateException When resource for this enum item does not exist.
     */
    public URL toUrlResource() {
        URL resource = Page.class.getResource(getFxmlPath());
        if (resource == null) {
            throw new IllegalStateException(String.format("Cannot find resource with path: %s.", getFxmlPath()));
        }
        return resource;
    }
}
