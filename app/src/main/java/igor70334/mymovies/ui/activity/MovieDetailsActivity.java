package igor70334.mymovies.ui.activity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;

import igor70334.mymovies.R;
import igor70334.mymovies.constants.Constants;
import igor70334.mymovies.data.model.Movie;
import igor70334.mymovies.ui.fragment.MovieDetailsFragment;

public class MovieDetailsActivity extends BaseActivity {

    private static final String MOVIE_FRAGMENT_TAG = "fragment_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Movie movie = getIntent().getParcelableExtra(Constants.EXTRAS.EXTRA_MOVIE);
        String title = movie.getTitle().concat(" (").concat(movie.getYear()).concat(")");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(title);
        }
         MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, fragment, MOVIE_FRAGMENT_TAG).commit();


    }
}
