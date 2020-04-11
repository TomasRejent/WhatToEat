package cz.afrosoft.whattoeat.cookbook.ingredient.gui.component;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Shop;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.ShopService;
import cz.afrosoft.whattoeat.core.gui.component.support.FXMLComponent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Builder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
@FXMLComponent(fxmlPath = "/component/ShopField.fxml")
public class ShopField extends GridPane implements Builder<ShopField> {

    @FXML
    private ComboBox<String> shopField;
    @FXML
    private FlowPane shopContainer;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ShopService shopService;

    private ObservableSet<String> shopNames = FXCollections.observableSet(new LinkedHashSet<>());

    @PostConstruct
    private void initialize(){
        initShopField();
        shopNames.addListener(createChangeListener());
    }

    private SetChangeListener<String> createChangeListener(){
        return change -> Platform.runLater(() -> {
            if (change.wasRemoved()) {
                processShopRemove(change);
            }
            if (change.wasAdded()) {
                processShopAddition(change);
            }
        });
    }

    private void processShopRemove(final SetChangeListener.Change<? extends String> change) {
        String removedShopName = change.getElementRemoved();
        //update panel with displayed keywords
        shopContainer.getChildren()
                .stream()
                .filter(node ->
                        node instanceof ShopLabel && ((ShopLabel) node).getShopName().equals(removedShopName))
                .forEach(node -> Platform.runLater(() -> shopContainer.getChildren().remove(node)));
    }

    private void processShopAddition(final SetChangeListener.Change<? extends String> change) {
        String addedShopName = change.getElementAdded();
        if(addedShopName == null || addedShopName.isEmpty()){
            return;
        }
        //update panel with displayed keywords
        ShopLabel shopLabel = applicationContext.getBean(ShopLabel.class, addedShopName);
        shopLabel.setRemovable(true);
        shopLabel.setRemoveListener(removedShopLabel -> shopNames.remove(removedShopLabel.getShopName()));
        shopContainer.getChildren().add(shopLabel);
        //update keyword field
        shopField.getSelectionModel().clearSelection();
        shopField.getEditor().setText(StringUtils.EMPTY);
    }

    @Override
    public ShopField build() {
        return this;
    }

    private void initShopField(){
        shopField.setEditable(true);
        shopField.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null && !newValue.isEmpty()) {
                        shopNames.add(newValue);
                    }
                }
        );
        /**
         * Consumes event after enter key is pressed to add keyword. Without this adding of keyword caused submitting of dialog which use keyword field.
         */
        shopField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
            }
        });
        refreshShops();
    }

    public void setSelectedShops(Set<Shop> shops){

        this.shopNames.clear();
        this.shopNames.addAll(shops.stream().map(Shop::getName).collect(Collectors.toSet()));
    }

    public void clearSelectedShops(){
        this.shopNames.clear();
    }

    public void refreshShops(){
        Set<Shop> allShops = shopService.getAllShops();
        shopField.getItems().setAll(allShops.stream().map(Shop::getName).collect(Collectors.toSet()));
    }

    public Set<Shop> createAndGetShops(){
        return shopService.createOrGetByNames(shopNames);
    }
}
