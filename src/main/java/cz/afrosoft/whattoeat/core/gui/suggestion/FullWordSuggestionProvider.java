package cz.afrosoft.whattoeat.core.gui.suggestion;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import org.apache.commons.lang3.Validate;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.Collection;
import java.util.Comparator;

/**
 * Suggestion provider which match also when typed text is equal to suggestion option.
 *
 * @author Tomas Rejent
 */
public class FullWordSuggestionProvider extends SuggestionProvider<String> {

    public FullWordSuggestionProvider(final Collection<String> possibleSuggestions) {
        Validate.noNullElements(possibleSuggestions);

        this.addPossibleSuggestions(possibleSuggestions);
    }

    @Override
    protected Comparator<String> getComparator() {
        return String.CASE_INSENSITIVE_ORDER;
    }

    @Override
    protected boolean isMatch(final String suggestion, final AutoCompletionBinding.ISuggestionRequest request) {
        final String userTextLower = request.getUserText().toLowerCase();
        final String suggestionStr = suggestion.toLowerCase();
        return suggestionStr.contains(userTextLower);
    }

}
