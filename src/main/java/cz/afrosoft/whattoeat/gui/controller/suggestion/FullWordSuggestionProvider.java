/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.gui.controller.suggestion;

import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.util.Collection;
import java.util.Comparator;
import org.controlsfx.control.textfield.AutoCompletionBinding;

/**
 * Suggestion provider which match also when typed text is equal to suggestion option.
 * @author Tomas Rejent
 */
public class FullWordSuggestionProvider extends SuggestionProvider<String> {

    public FullWordSuggestionProvider(final Collection<String> possibleSuggestions) {
        checkNotNull(possibleSuggestions, "Possible suggestions cannot be null.");

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
