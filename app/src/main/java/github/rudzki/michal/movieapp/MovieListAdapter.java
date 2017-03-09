package github.rudzki.michal.movieapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

    private List<ListingItem> items = Collections.emptyList();

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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ListingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView poster;
        TextView titleAndYear;
        TextView type;

        public MyViewHolder(View itemView){
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
}
