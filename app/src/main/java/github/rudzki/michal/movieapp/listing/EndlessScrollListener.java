package github.rudzki.michal.movieapp.listing;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


/**
 * Created by RENT on 2017-03-11.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private static final double PAGE_SIZE = 10;
    private int totalItemsNumber;
    private  boolean isLoading;
    private OnLoadPageListener listener;

    public EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadPageListener listener){
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int alreadyLoadedItems = layoutManager.getItemCount();
        int currentPage = (int) Math.ceil(alreadyLoadedItems / PAGE_SIZE);
        double numberOfAllPages = Math.ceil(totalItemsNumber / PAGE_SIZE);
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition() + 1;

        if(currentPage < numberOfAllPages && lastVisibleItem == alreadyLoadedItems && !isLoading){
            loadNextPage(++currentPage);
            isLoading = true;
        }

        Log.d("result", "lastVisible" + lastVisibleItem);
    }

    private void loadNextPage(int pageNumber) {
        listener.loadNextPage(pageNumber);
    }

    public void setTotalItemsNumber(int totalItemsNumber) {
        this.totalItemsNumber = totalItemsNumber;
        isLoading = false;
    }
}
