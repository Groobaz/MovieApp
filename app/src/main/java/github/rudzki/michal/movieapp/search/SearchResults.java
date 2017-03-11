package github.rudzki.michal.movieapp.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import github.rudzki.michal.movieapp.listing.ListingItem;

public class SearchResults {

    @SerializedName("Search")
    private List<ListingItem> items;
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<ListingItem> getItems() {
        return items;
    }

    public void setItems(List<ListingItem> items) {
        this.items = items;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
