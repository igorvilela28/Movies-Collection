package igor70334.mymovies.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import igor70334.mymovies.MyApplication;
import igor70334.mymovies.R;
import igor70334.mymovies.constants.Constants;
import igor70334.mymovies.data.model.Movie;
import igor70334.mymovies.data.model.MovieDao;

/**
 * Class to show all details of a movie
 * @author Igor Vilela Damasceno
 * @since 7/26/16.
 */
public class MovieDetailsFragment extends BaseFragment {

    //TODO: change state of views when receive "N/A" result
    //TODO: change color of metascore background
    //TODO: save state of Y position when rotating
    //TODO: Toast should be a Snackbar with an UNDO action

    private static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.txt_movie_rated) TextView mRatedTxt;
    @BindView(R.id.txt_movie_genre) TextView mGenreTxt;
    @BindView(R.id.txt_movie_runtime) TextView mRuntimeTxt;
    @BindView(R.id.txt_movie_plot) TextView mPlotTxt;
    @BindView(R.id.img_movie_poster) SimpleDraweeView mPosterImg;
    @BindView(R.id.btn_movie_favorite) ImageButton mFavoriteBttn;
    @BindView(R.id.btn_imdb_ic) ImageButton mImdbIcBttn;
    @BindView(R.id.txt_imdb_rating) TextView mImdbRatingTxt;
    @BindView(R.id.txt_imdb_votes) TextView mImdbVotesTxt;
    @BindView(R.id.txt_movie_metascore) TextView mMetascoreTxt;
    @BindView(R.id.txt_movie_awards) TextView mAwardsTxt;

    @BindView(R.id.txt_movie_director) TextView mDirectorTxt;
    @BindView(R.id.txt_movie_cast) TextView mCastTxt;
    @BindView(R.id.txt_movie_writers) TextView mWritersText;
    @BindView(R.id.txt_movie_genre2) TextView mGenre2Text;
    @BindView(R.id.txt_movie_language) TextView mLanguageTxt;
    @BindView(R.id.txt_movie_country) TextView mCountryTxt;
    @BindView(R.id.txt_movie_released) TextView mReleasedDateText;

    Movie mMovie;


    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = getArguments().getParcelable(ARG_MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindMovieData();

        mFavoriteBttn.setSelected(mMovie.isFavorite());


        mFavoriteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDao movieDao = MyApplication.get(getActivity()).getDaoSession().getMovieDao();
                if(mMovie.isFavorite()) {
                    mFavoriteBttn.setSelected(true);
                    mMovie.setFavorite(false);
                    movieDao.delete(mMovie);
                    Toast.makeText(getActivity(), getString(R.string.message_removed_from_favorites),
                            Toast.LENGTH_SHORT).show();
                } else {
                    mMovie.setFavorite(true);
                    movieDao.insertOrReplace(mMovie);
                    Toast.makeText(getActivity(), getString(R.string.message_added_to_favorites),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //going to imdb site when click on imdb icon
        mImdbIcBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("www.imdb.com")
                        .appendPath("title")
                        .appendPath(mMovie.getImdbId());

                String url = builder.build().toString();

                final Intent it = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(it);
            }
        });
    }

    private void bindMovieData() {

        mRatedTxt.setText(mMovie.getRated());
        mGenreTxt.setText(mMovie.getGenre());
        mRuntimeTxt.setText(mMovie.getRuntime());
        mPlotTxt.setText(mMovie.getPlot());

        final Uri uri = Uri.parse(mMovie.getPoster());

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();

        mPosterImg.setController(controller);

        mImdbRatingTxt.setText(mMovie.getImdbRating().concat("/10"));
        mImdbVotesTxt.setText(mMovie.getImdbVotes());
        mMetascoreTxt.setText(mMovie.getMetascore());
        mAwardsTxt.setText(mMovie.getAwards());
        mDirectorTxt.setText(mMovie.getDirector());
        mCastTxt.setText(mMovie.getActors());
        mWritersText.setText(mMovie.getWriter());
        mGenre2Text.setText(mMovie.getGenre());
        mLanguageTxt.setText(mMovie.getLanguage());
        mCountryTxt.setText(mMovie.getCountry());
        mReleasedDateText.setText(mMovie.getReleased());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent it = new Intent();
        it.putExtra(Constants.EXTRAS.EXTRA_MOVIE, mMovie);
        getActivity().setResult(Activity.RESULT_OK, it);
    }
}
