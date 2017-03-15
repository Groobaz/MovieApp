package github.rudzki.michal.movieapp.listing;

import android.app.LauncherActivity;

import java.util.List;

import github.rudzki.michal.movieapp.search.SearchResults;
import github.rudzki.michal.movieapp.search.SearchService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingPresenter extends Presenter<ListingActivity> implements OnLoadPageListener {
    private ResultAgregator resultAgregator = new ResultAgregator();
    private SearchResults searchResultsAllResult;
    private Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;
    private boolean isLoadingFromStart;

    public ListingPresenter() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://omdbapi.com")
                .build();
    }

    public void startLoadingItems(String title, int year, String type) {
        this.type = type;
        this.title = title;
        this.stringYear = year== ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        if(resultAgregator.getMovieItems().size() == 0) {
            loadNextPage(1);
            isLoadingFromStart = true;
        }
       /* else{
            getView().setNewAgregatorResult(resultAgregator);
        }*/
    }

    @Override
    protected void onTakeView(ListingActivity listingActivity) {
        super.onTakeView(listingActivity);
        if(!isLoadingFromStart){
        listingActivity.setNewAgregatorResult(resultAgregator);
        }
    }

    public  void setRetrofit(Retrofit retrofit){
        this.retrofit = retrofit;
    }


    @Override
    public void loadNextPage(int page) {
        isLoadingFromStart = false;
        retrofit.create(SearchService.class).search(page, title, stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResults -> {
                    resultAgregator.addNeItems(searchResults.getItems());
                    resultAgregator.setMovieItemsResult(Integer.parseInt(searchResults.getTotalResults()));

                    getView().setNewAgregatorResult(resultAgregator);
                });
    }

}
