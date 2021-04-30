package cz.afrosoft.whattoeat.core.gui.component;

import cz.afrosoft.whattoeat.core.gui.Labeled;
import cz.afrosoft.whattoeat.core.gui.combobox.ComboBoxUtils;
import javafx.scene.control.ComboBox;

import java.util.Optional;

enum BoolOptions implements Labeled {
    YES("cz.afrosoft.whattoeat.boolOption.yes", Boolean.TRUE),
    NO("cz.afrosoft.whattoeat.boolOption.no", Boolean.FALSE),
    NONE("cz.afrosoft.whattoeat.boolOption.none", null);

    private final String labelKey;
    private final Boolean value;

    BoolOptions(final String labelKey, final Boolean value) {
        this.labelKey = labelKey;
        this.value = value;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

    public Boolean getValue(){
        return this.value;
    }
}

/**
 * @author Tomas Rejent
 */
public class BoolCombo extends ComboBox<BoolOptions> {

    public BoolCombo() {
        ComboBoxUtils.initLabeledComboBox(this, BoolOptions.values());
    }

    public Boolean getBoolValue(){
        return Optional.ofNullable(this.getSelectionModel().getSelectedItem()).orElse(BoolOptions.NONE).getValue();
    }
}
