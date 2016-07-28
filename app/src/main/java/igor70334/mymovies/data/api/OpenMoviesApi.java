package igor70334.mymovies.data.api;

import igor70334.mymovies.data.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * interface for the endpoint provided by the OMDb
 * More Info at <a href="http://www.omdbapi.com/">http://www.omdbapi.com/</a>
 * @author Igor Vilela Damasceno
 * @since 7/25/16.
 */
public interface OpenMoviesApi {

    @GET(" ")
    Call<Movie> fetchMovie(@Query("t") String movieName, @Query("plot") String plotLength);
}
