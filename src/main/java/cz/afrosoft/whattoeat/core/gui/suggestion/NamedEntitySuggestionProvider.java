package cz.afrosoft.whattoeat.core.gui.suggestion;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.logic.model.NamedEntity;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public class NamedEntitySuggestionProvider<T extends NamedEntity> extends SuggestionProvider<T> {

    public NamedEntitySuggestionProvider() {
    }

    public NamedEntitySuggestionProvider(final Collection<T> possibleSuggestions) {
        Validate.noNullElements(possibleSuggestions);

        this.addPossibleSuggestions(possibleSuggestions);
    }

    public void setPossibleSuggestions(final Collection<T> possibleSuggestions) {
        Validate.noNullElements(possibleSuggestions);
        clearSuggestions();
        addPossibleSuggestions(possibleSuggestions);
    }

    @Override
    protected Comparator<T> getComparator() {
        return (o1, o2) -> {
            if (o1 == o2) {
                return 0;
            } else {
                return I18n.compareStringsIgnoreCase(
                        Optional.ofNullable(o1).map(NamedEntity::getName).orElse(StringUtils.EMPTY),
                        Optional.ofNullable(o2).map(NamedEntity::getName).orElse(StringUtils.EMPTY)
                );
            }
        };
    }

    @Override
    protected boolean isMatch(final T suggestion, final AutoCompletionBinding.ISuggestionRequest request) {
        final String userTextLower = request.getUserText().toLowerCase();
        final String suggestionStr = suggestion.getName().toLowerCase();
        return suggestionStr.contains(userTextLower);
    }
}
