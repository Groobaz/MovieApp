package github.rudzki.michal.movieapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(year);
        numberPicker.setWrapSelectorWheel(true);
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
