package igor70334.mymovies.ui.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import igor70334.mymovies.MyApplication;
import igor70334.mymovies.R;
import igor70334.mymovies.constants.Constants;
import igor70334.mymovies.data.api.ApiClient;
import igor70334.mymovies.data.api.OpenMoviesApi;
import igor70334.mymovies.data.model.Movie;
import igor70334.mymovies.data.model.MovieDao;
import igor70334.mymovies.data.provider.SearchableProvider;
import igor70334.mymovies.ui.adapter.MovieAdapter;
import igor70334.mymovies.utils.ItemClickSupport;
import igor70334.mymovies.utils.VarColumnGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends BaseActivity {

    //TODO: save state of Y position when rotating
    //TODO: should use Fragment
    //TODO: should show a ProgressBar while querying

    private final int REQUEST_MOVIE_DETAILS = 100;
    private final String PLACE_HOLDER_MESSAGE = "ok";

    @BindView(R.id.rv_list)  RecyclerView mRecyclerView;
    @BindView(R.id.cl_search_container)  CoordinatorLayout mCoordinatorLayout;
    @BindColor(R.color.primary_text) int mPrimaryTextColor;

    private List<Movie> mMoviesFromDb;
    private ArrayList<Movie> mMovieQueriedList = new ArrayList<>();
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(getString(R.string.message_search_results));
        }

        MovieDao mDao = MyApplication.get(SearchableActivity.this).getDaoSession().getMovieDao();
        mMoviesFromDb = mDao.queryBuilder().list();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //default value
        int minWidth = 300;

        //'hack' to show properly the grid view in devices with different density value
        float density = getResources().getDisplayMetrics().density;

        if(density == 0.75f) {
            minWidth = 115;
        } else if(density == 1f) {
            minWidth = 130;
        } else if(density == 1.5f) {
            minWidth = 160;
        } else if(density == 2f) {
            minWidth = 240;
        } else if (density == 2.5f) {
            minWidth = 300;
        } else if(density == 3.0f) {
            minWidth = 340;
        } else if (density == 3.5f) {
            minWidth = 360;
        } else if (density == 4f) {
            minWidth = 390;
        }
        VarColumnGridLayoutManager mLayoutManager = new VarColumnGridLayoutManager(this, minWidth);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieAdapter(SearchableActivity.this, mMovieQueriedList);
        mRecyclerView.setAdapter(mAdapter);

        //'hack' to click listener on a RecyclerView
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Movie movie = mMovieQueriedList.get(position);
                        Intent it = new Intent(SearchableActivity.this, MovieDetailsActivity.class);
                        it.putExtra(Constants.EXTRAS.EXTRA_MOVIE, movie);

                        //TODO: should use Observer pattern
                        //this was necessary for get the correct state of favorite button
                        startActivityForResult(it, REQUEST_MOVIE_DETAILS);
                    }
                }
        );

        Intent intent = getIntent();
        handleSearch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == REQUEST_MOVIE_DETAILS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Movie movie = data.getParcelableExtra(Constants.EXTRAS.EXTRA_MOVIE);
                int idx = mMovieQueriedList.indexOf(movie);
                if(idx >= 0) {
                    mMovieQueriedList.get(idx).setFavorite(movie.isFavorite());
                    mAdapter.notifyDataSetChanged();
                }

            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        //avoiding error when rotating the device
        setIntent(intent);
        handleSearch(intent);
    }

    /**
     * handles the search
     * @param intent the intent received for the activity
     */
    private void handleSearch(Intent intent) {

        // Get the intent, verify the action and get the query
        if(Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            filterMovies(query);

            //adding to recent suggestions provider
            SearchRecentSuggestions recentSuggestions = new SearchRecentSuggestions(this,
                    SearchableProvider.AUTHORITY, SearchableProvider.MODE);
            recentSuggestions.saveRecentQuery(query, null);
        }

    }

    /**
     * queries the movie first in database and then in the api
     * @param query the string for the query
     */
    private void filterMovies(String query) {

        mMovieQueriedList.clear();
        query = query.toLowerCase();
        for( int i = 0; i < mMoviesFromDb.size(); i++ ) {
            String title = mMoviesFromDb.get(i).getTitle().toLowerCase();
            if( title.startsWith(query) ||
                    (query.length() >= 3 && title.contains(query))) {
                mMovieQueriedList.add(mMoviesFromDb.get(i));
                mAdapter.notifyDataSetChanged();
            }
        }

        fetchMovie(query);
    }

    /**
     * set a log message for the user if's necessary
     * @param message the log message
     */
    private void setTextResult(String message) {
        mRecyclerView.setVisibility( mMovieQueriedList.isEmpty() ? View.GONE : View.VISIBLE);
        if( mMovieQueriedList.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText(message);
            tv.setTextSize(R.dimen.text_size_medium);
            tv.setTextColor(mPrimaryTextColor);
            tv.setId(R.id.txt_search_empty_result);
            tv.setLayoutParams( new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT )  );
            tv.setGravity(Gravity.CENTER);

            mCoordinatorLayout.addView(tv);
        } else if( mCoordinatorLayout.findViewById(R.id.txt_search_empty_result) != null ) {
            mCoordinatorLayout.removeView(mCoordinatorLayout
                    .findViewById(R.id.txt_search_empty_result));
        }
    }

    /**
     * Query in the OMDB API for the movie
     * @param movieName the query (should be a valid movie name)
     */
    private void fetchMovie(String movieName) {

        movieName = movieName.replaceAll(" ", "+");

        OpenMoviesApi apiService = ApiClient.getClient().
                create(OpenMoviesApi.class);

        Call<Movie> callMovie = apiService.fetchMovie(movieName, "full");
        callMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                if(movie.getResponse().equals("True")) {

                    if(!mMovieQueriedList.contains(movie)) {
                        mMovieQueriedList.add(movie);
                        mAdapter.notifyDataSetChanged();
                        setTextResult(PLACE_HOLDER_MESSAGE);
                    }
                } else {

                    //setting no results log message
                    setTextResult(getString(R.string.message_empty_result));
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                //seting no connection log message
                setTextResult(getString(R.string.message_search_no_connection));
            }
        });
    }




}
