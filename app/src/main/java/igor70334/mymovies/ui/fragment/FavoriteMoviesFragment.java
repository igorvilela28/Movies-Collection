package igor70334.mymovies.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import igor70334.mymovies.MyApplication;
import igor70334.mymovies.R;
import igor70334.mymovies.constants.Constants;
import igor70334.mymovies.data.model.Movie;
import igor70334.mymovies.data.model.MovieDao;
import igor70334.mymovies.ui.activity.MovieDetailsActivity;
import igor70334.mymovies.ui.adapter.MovieAdapter;
import igor70334.mymovies.utils.ItemClickSupport;
import igor70334.mymovies.utils.VarColumnGridLayoutManager;

/**
 * Fragment used to show the list of Favorite movies
 */
public class FavoriteMoviesFragment extends BaseFragment {

    public static final String LOG_TAG = FavoriteMoviesFragment.class.getSimpleName();

    @BindView(R.id.rv_movie_list) RecyclerView mRecyclerView;
    @BindView(R.id.txt_empty_list) TextView mEmptyListTxt;

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private MovieAdapter mAdapter;

    public FavoriteMoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(LOG_TAG, "onViewCreated");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //default value
        int minWidth = 300;

        //TODO: test this in different devices with different density values
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
        VarColumnGridLayoutManager mLayoutManager = new VarColumnGridLayoutManager(getActivity(),
                minWidth);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieAdapter(getActivity(), mMovieList);
        mRecyclerView.setAdapter(mAdapter);

        //'hack' to click listener on a RecyclerView
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Movie movie = mMovieList.get(position);
                        Intent it = new Intent(getActivity(), MovieDetailsActivity.class);
                        it.putExtra(Constants.EXTRAS.EXTRA_MOVIE, movie);
                        startActivity(it);
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();

        //update the content when starting or returning to the main activity
        mMovieList.clear();
        MovieDao mDao = MyApplication.get(getActivity()).getDaoSession().getMovieDao();
        mMovieList.addAll(mDao.queryBuilder().orderAsc(MovieDao
                                .Properties.MTitle).list());
        setTextResult();
    }

    /**
     * set a log message for the user if's necessary
     */
    private void setTextResult() {
        mRecyclerView.setVisibility(mMovieList.isEmpty() ? View.GONE : View.VISIBLE);
        mEmptyListTxt.setVisibility(mMovieList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
