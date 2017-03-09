package github.rudzki.michal.movieapp;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SearchService {

    @GET("/")
    Observable<SearchResults> search(@Query("s") String title, @Query("y") String year, @Query("type") String type);

}
