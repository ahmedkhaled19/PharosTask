package khaled.ahmed.pharostask.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Adapters.CitiesAdapter;
import khaled.ahmed.pharostask.Adapters.OnLoadMoreListener;
import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.Presenters.HomePresenter;
import khaled.ahmed.pharostask.R;
import khaled.ahmed.pharostask.Views.HomeView;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private static boolean active = false;
    private HomePresenter presenter;
    private TextView noData;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CitiesAdapter adapter;
    private ArrayList<Cities> dataList;
    private ImageView search;
    private Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.hometoolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        noData = findViewById(R.id.nodata_txt);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.home_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        search = toolbar.findViewById(R.id.toolbar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
        initializeAdapter();
    }

    /**
     * in this method we initialize adapter
     * then we initialize presenter and get data
     */
    private void initializeAdapter() {
        dataList = new ArrayList<>();
        adapter = new CitiesAdapter(HomeActivity.this, dataList, recyclerView, progressBar);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (presenter.getPage() != 1 && presenter.checkConnection()) {
                    dataList.add(null);
                    adapter.notifyItemInserted(dataList.size() - 1);
                    presenter.getServerData();
                } else {
                    adapter.setLoaded();
                }
            }
        });
        presenter = new HomePresenter(this, HomeActivity.this);
        presenter.getData();
    }

    /**
     * presenter call this method in case of this is first time we get data from server
     */
    @Override
    public void SetDataFirstTime(ArrayList<Cities> list) {
        dataList.clear();
        if (list.isEmpty() && active) {
            progressBar.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.GONE);
            if (snackbar != null) {
                snackbar.dismiss();
            }
            dataList.addAll(list);
            adapter.setItems(dataList, false);
        }
    }

    /**
     * presenter call this method in case of this is reload data from server by paging
     */
    @Override
    public void SetDataReload(ArrayList<Cities> list) {
        if (list.size() == 0) {
            if (dataList.size() != 0) {
                dataList.remove(dataList.size() - 1);
                adapter.notifyItemRemoved(dataList.size());
            }
            adapter.setOnLoadMoreListener(null);
            return;
        }
        dataList.remove(dataList.size() - 1);
        adapter.notifyItemRemoved(dataList.size());
        adapter.setItems(list, true);
        adapter.setLoaded();
    }

    /**
     * set data from local in case of no internet connection
     */
    @Override
    public void SetDataLocal(ArrayList<Cities> cities) {
        dataList = cities;
        if (dataList.isEmpty() || dataList.size() == 0 && active) {
            progressBar.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            adapter.setItems(dataList, false);
        }
    }

    @Override
    public void ServerError() {
        Toast.makeText(this, getString(R.string.servererror), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSnackbar() {
        RelativeLayout layout = findViewById(R.id.relative);
        snackbar = Snackbar
                .make(layout, getString(R.string.nointernet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        presenter.getData();
                    }
                });
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }
}
