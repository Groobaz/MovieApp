package github.rudzki.michal.movieapp;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;

    public ListingPresenter() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://omdbapi.com")
                .build();
    }

    public Observable<SearchResults> getDataAsync(String title, int year, String type) {
        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        return retrofit.create(SearchService.class).search(title,
                stringYear,
                type);
    }
}
