package github.rudzki.michal.movieapp.listing;

import java.util.ArrayList;
import java.util.List;

import github.rudzki.michal.movieapp.detail.MovieItem;

/**
 * Created by RENT on 2017-03-15.
 */

public class ResultAgregator {
    private String response;
    private List<ListingItem> movieItems = new ArrayList<>();
    private int totalItemResult;

    public void setMovieItemsResult(int movieItemsResult) {
        this.totalItemResult = movieItemsResult;
    }

    public void addNeItems(List<ListingItem> newItems) {
        movieItems.addAll(newItems);
    }

    public List<ListingItem> getMovieItems() {
        return movieItems;
    }

    public int getTotalItemResult() {
        return totalItemResult;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {

        return response;
    }
}
