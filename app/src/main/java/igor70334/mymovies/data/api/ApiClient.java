package igor70334.mymovies.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class used to create an instance for a client of the API using the Retrofit library.
 * The deserialization of objects is handled by Gson.
 * More Info at <a href="http://square.github.io/retrofit/">http://square.github.io/retrofit/</a>
 * @author Igor Vilela Damasceno
 * @since 7/25/16
 */
public class ApiClient {

    public static final String BASE_URL = "http://www.omdbapi.com/?";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
