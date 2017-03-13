package github.rudzki.michal.movieapp.listing;

import android.support.v7.view.menu.ShowableListMenu;
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
    private ListenerShowOrHideCounter showOrHideCounter;
    private boolean isCountershown = true;

    public void setShowOrHideCounter(ListenerShowOrHideCounter showOrHideCounter) {
        this.showOrHideCounter = showOrHideCounter;
    }

    public void setCurrentItemListener(CurrentItemListener currentItemListener) {
        this.currentItemListener = currentItemListener;
    }

    private CurrentItemListener currentItemListener;

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

        if(currentItemListener != null){
            currentItemListener.onNewCurrentItem(lastVisibleItem, totalItemsNumber);
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

    @Override
    public  void onScrollStateChanged(RecyclerView recyclerView, int newState){
        super.onScrollStateChanged(recyclerView, newState);
        if(isCountershown && newState == RecyclerView.SCROLL_STATE_IDLE){
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showOrHideCounter.hideCounter();
                    isCountershown = false;
                }
            }, 3000);
        } else if (!isCountershown && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            showOrHideCounter.showCounter();
            isCountershown = true;
        }
    }
}
