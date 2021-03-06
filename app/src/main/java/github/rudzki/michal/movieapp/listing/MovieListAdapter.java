package github.rudzki.michal.movieapp.listing;

import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import github.rudzki.michal.movieapp.R;
import github.rudzki.michal.movieapp.detail.MovieItem;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

    private List<ListingItem> items = Collections.emptyList();

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    private OnMovieItemClickListener onMovieItemClickListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       ListingItem listingItem = items.get(position);
        Glide.with(holder.poster.getContext()).load(listingItem.getPoster()).into(holder.poster);
        holder.titleAndYear.setText(listingItem.getTitle() + " (" + listingItem.getYear() + " )");
        holder.type.setText("typ: " + listingItem.getType());
        holder.itemView.setOnClickListener(v -> {
            if(onMovieItemClickListener != null){
                onMovieItemClickListener.onMovieItemClick(listingItem.getImdbID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ListingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<ListingItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        ImageView poster;
        TextView titleAndYear;
        TextView type;

        public MyViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
}
