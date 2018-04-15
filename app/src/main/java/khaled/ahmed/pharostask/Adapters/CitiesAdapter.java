package khaled.ahmed.pharostask.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.R;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public class CitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //for load more data
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private ArrayList<Cities> list;
    private ProgressBar progressBar;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public CitiesAdapter(Context context, ArrayList<Cities> data, RecyclerView recyclerView, ProgressBar progressBar) {
        this.context = context;
        this.list = data;
        this.progressBar = progressBar;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem + visibleThreshold >= 20) {
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.city_list_item, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Cities city = list.get(position);
            Picasso.with(context).load(city.getImageUrl()).into(((ViewHolder) holder).cityImage);
            ((ViewHolder) holder).name.setText(city.getName());
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    /**
     * this method take data from home to set it in adapter
     * in case of first time data we send boolean with false
     * in case of reload we send boolean true to add list to our old list
     * and notify adapter with new data in position that inserted in
     */
    public void setItems(ArrayList<Cities> data, boolean IsAddToList) {
        if (!IsAddToList) {
            this.list = data;
            this.notifyDataSetChanged();
        } else {
            this.list.addAll(data);
            this.notifyItemRangeInserted(list.size(), data.size());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cityImage;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.city_name);
            cityImage = itemView.findViewById(R.id.city_image);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.loading_progressBar);
        }

    }
}
