package github.rudzki.michal.movieapp.search;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import github.rudzki.michal.movieapp.R;
import github.rudzki.michal.movieapp.RetrofitProvider;
import github.rudzki.michal.movieapp.listing.ListingActivity;
import github.rudzki.michal.movieapp.listing.ListingItem;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {
    private PosterRecyclerViewAdapter adapter;

    private Map<Integer, String> apiKeaysMap = new HashMap<Integer, String>(){{
        put(R.id.radio_movies, "movie");
        put(R.id.radio_episodes, "episodes");
        put(R.id.radio_games, "game");
        put(R.id.radio_series, "series");
    }};

    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    @BindView(R.id.edit_text)
    TextInputEditText editText;

    @BindView(R.id.search_button)
    ImageView searchButton;

    @BindView(R.id.year_checkbox)
    CheckBox yearCheckBox;

    @BindView(R.id.type_checkbox)
    CheckBox typeCheckBox;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.poster_headerID)
    RecyclerView posterHeaderRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(year);
        numberPicker.setWrapSelectorWheel(true);
        adapter = new PosterRecyclerViewAdapter();
        posterHeaderRecyclerView.setAdapter(adapter);
//        posterHeaderRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        posterHeaderRecyclerView.setLayoutManager(layoutManager);
        posterHeaderRecyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        Retrofit retrofit = retrofitProvider.provideRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search(1, "a*", "2016", null)
                .flatMap(searchResults -> Observable.fromIterable(searchResults.getItems()))
                .map(ListingItem::getPoster)
                .filter(posterUrl -> !"N/A".equalsIgnoreCase(posterUrl))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(this::success, this::error);
    }

        private void success(List<String> list){
            adapter.setUrls(list);
        }

        private void error(Throwable throwable){

        }
        @OnCheckedChanged(R.id.type_checkbox)
        void onTypeChackedboxStateChanged(CompoundButton buttonView, boolean isChecked){
            radioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        }

        @OnCheckedChanged(R.id.year_checkbox)
        void onYearCheckboxStateChanged(CompoundButton buttonView, boolean isChecked){
            numberPicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        }

        @OnClick(R.id.search_button)
        void setSearchButtonClick(){
            int checkedRadioId = radioGroup.getCheckedRadioButtonId();
            int year = yearCheckBox.isChecked() ? numberPicker.getValue() : ListingActivity.NO_YEAR_SELECTED;
            String typKey = typeCheckBox.isChecked() ? apiKeaysMap.get(checkedRadioId) : null;

            startActivity(ListingActivity.createIntent(SearchActivity.this,
                    editText.getText().toString(),
                    year,
                    typKey));
        }
}
