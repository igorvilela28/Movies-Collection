package igor70334.mymovies.data.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * @author Igor Vilela Damasceno
 * @since 7/27/16.
 */
public class SearchableProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "igor70334.mymovies.data.provider.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchableProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
