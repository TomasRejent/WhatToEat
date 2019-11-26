package cz.afrosoft.whattoeat.diet.list.gui.table;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Table cell for displaying date of diet with specific formatting.
 * @author Tomas Rejent
 */
public class DateCell extends TableCell<DayDiet, LocalDate> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM.");

    public DateCell() {
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(final LocalDate item, final boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setText("");
        }else{
            setText(item.format(formatter));
        }
    }
}
