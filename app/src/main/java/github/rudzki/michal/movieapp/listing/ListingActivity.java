package github.rudzki.michal.movieapp.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import github.rudzki.michal.movieapp.R;
import github.rudzki.michal.movieapp.RetrofitProvider;
import github.rudzki.michal.movieapp.detail.DetailActivity;
import github.rudzki.michal.movieapp.search.SearchResults;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> implements  CurrentItemListener, OnMovieItemClickListener, ListenerShowOrHideCounter {

    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_YEAR = "search_year";
    private static final String SEARCH_TYPE = "search_type";
    public static final int NO_YEAR_SELECTED = -1;
    private EndlessScrollListener endlessScrollListener;

    private MovieListAdapter adapter;

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_view)
    ImageView noInternetImage;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_results)
    FrameLayout noResults;

    @BindView(R.id.counter)
    TextView counter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        if(savedInstanceState == null) {
            RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
            getPresenter().setRetrofit(retrofitProvider.provideRetrofit());
        }
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        String type = getIntent().getStringExtra(SEARCH_TYPE);
        ButterKnife.bind(this);
        adapter = new MovieListAdapter();
        adapter.setOnMovieItemClickListener(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        endlessScrollListener= new EndlessScrollListener(linearLayoutManager, getPresenter());
        recyclerView.addOnScrollListener(endlessScrollListener);
        endlessScrollListener.setCurrentItemListener(this);
        endlessScrollListener.setShowOrHideCounter(this);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startLayout(title, type, year);
            }
        });

        startLayout(title, type, year);
    }

    private void startLayout(String title, String type, int year) {
        getPresenter().getDataAsync(title, year, type)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(this::succes, this::error);
    }

    @OnClick(R.id.no_internet_view)
    public void onNoInternetImageViewClick(View view){
        Toast.makeText(this, "Kliknąłem no internet image view", Toast.LENGTH_LONG).show();
    }

    public  void appendItems(SearchResults searchResults){
        adapter.addItems(searchResults.getItems());
        endlessScrollListener.setTotalItemsNumber(Integer.parseInt(searchResults.getTotalResults()));
    }

    private void error(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    private void succes(SearchResults searchResults) {
        swipeRefreshLayout.setRefreshing(false);
        if("false".equalsIgnoreCase(searchResults.getResponse())){
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResults));
        } else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(swipeRefreshLayout));
            adapter.setItems(searchResults.getItems());
            endlessScrollListener.setTotalItemsNumber(Integer.parseInt(searchResults.getTotalResults()));
        }
    }

    public static Intent createIntent(Context context, String title, int year, String typKey){
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, typKey);
        return intent;
    }

    @Override
    public void onNewCurrentItem(int currentItem, int totalItemsCount) {
        counter.setText(currentItem + "/" + totalItemsCount);
        counter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCounter() {
        counter.animate().translationX(0).start();
    }

    @Override
    public void hideCounter() {
        counter.animate().translationX(counter.getWidth() * 2).start();
    }

    @Override
    public void onMovieItemClick(String imdbID) {
//        Toast.makeText(this, imdbID, Toast.LENGTH_LONG);
        startActivity(DetailActivity.createIntent(this, imdbID));
    }

    /*public void setDataOnUiThread(SearchResults result, boolean isProblemWithInternet) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isProblemWithInternet){
                    viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
                } else {
                    viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
                    adapter.setItems(result.getItems());
                }
            }
        });
    }*/
}
