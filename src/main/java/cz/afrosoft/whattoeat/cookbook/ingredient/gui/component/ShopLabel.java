package cz.afrosoft.whattoeat.cookbook.ingredient.gui.component;

import cz.afrosoft.whattoeat.core.gui.component.RemoveButton;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.commons.lang3.Validate;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/ShopLabel.fxml")
public class ShopLabel extends Label {

    @FXML
    private RemoveButton removeButton;

    /**
     * Controls visibility of remove button.
     */
    private BooleanProperty removable = new SimpleBooleanProperty(true);

    private final String shopName;

    public ShopLabel(final String shopName) {
        Validate.notBlank(shopName);

        this.shopName = shopName;
    }

    @PostConstruct
    private void initialize(){
        setText(shopName);
        removable.addListener(createRemovablePropListener());
        setRemovable(false);
    }

    private ChangeListener<Boolean> createRemovablePropListener() {
        return (observable, oldValue, newValue) -> {
            if (newValue) {
                setGraphic(removeButton);
            } else {
                setGraphic(null);
            }
        };
    }

    public ShopLabel setRemovable(final boolean removable){
        this.removable.setValue(removable);
        return this;
    }

    public String getShopName(){
        return shopName;
    }

    /**
     * @param listener (NotNull) Listener for remove button click event. Listeners receives this component, from which it
     *                 can obtain keyword to remove.
     */
    public void setRemoveListener(final Consumer<ShopLabel> listener) {
        Validate.notNull(listener);

        removeButton.setOnAction(event -> listener.accept(this));
    }
}
