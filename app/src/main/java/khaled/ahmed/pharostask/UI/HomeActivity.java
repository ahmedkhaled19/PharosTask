package khaled.ahmed.pharostask.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        noData = findViewById(R.id.nodata_txt);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.home_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        initializeAdapter();
        presenter = new HomePresenter(this, HomeActivity.this);
        presenter.getData();
    }

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

    }

    @Override
    public void ServerError() {
        Toast.makeText(this, getString(R.string.servererror), Toast.LENGTH_LONG).show();
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
