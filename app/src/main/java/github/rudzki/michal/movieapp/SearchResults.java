package github.rudzki.michal.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {

    @SerializedName("Search")
    private List<ListingItem> items;
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<ListingItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
