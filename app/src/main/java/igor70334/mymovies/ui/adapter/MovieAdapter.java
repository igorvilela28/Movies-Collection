package igor70334.mymovies.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import igor70334.mymovies.MyApplication;
import igor70334.mymovies.R;
import igor70334.mymovies.data.model.Movie;
import igor70334.mymovies.data.model.MovieDao;
import igor70334.mymovies.ui.activity.MainActivity;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>  {

    private ArrayList<Movie> mMovieList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_movie_poster) SimpleDraweeView posterImg;
        @BindView(R.id.btn_movie_favorite) ImageButton favoriteBttn;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.mContext = context;
        this.mMovieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie movie = mMovieList.get(position);

        // parse the URL
        final Uri uri = Uri.parse(movie.getPoster());

        // Initialize a controller and attach the listener to it.
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();

        holder.posterImg.setController(controller);
        if(movie.isFavorite()) {
            holder.favoriteBttn.setSelected(true);
        }

        holder.favoriteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDao movieDao = MyApplication.get(mContext).getDaoSession().getMovieDao();
                if(movie.isFavorite()) {
                    movieDao.delete(movie);
                    holder.favoriteBttn.setSelected(false);
                    mMovieList.remove(holder.getAdapterPosition());
                    if(mContext instanceof MainActivity) {
                        mMovieList.remove(movie);
                        notifyDataSetChanged();
                    }
                    Toast.makeText(mContext, mContext.getString(R.string.message_removed_from_favorites),
                            Toast.LENGTH_SHORT).show();
                } else {
                    movie.setFavorite(true);
                    movieDao.insertOrReplace(movie);
                    holder.favoriteBttn.setSelected(true);

                    Toast.makeText(mContext, mContext.getString(R.string.message_added_to_favorites),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}