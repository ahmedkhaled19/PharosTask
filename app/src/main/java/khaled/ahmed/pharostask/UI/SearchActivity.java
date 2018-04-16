package khaled.ahmed.pharostask.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Adapters.CitiesAdapter;
import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.Presenters.SearchPresenter;
import khaled.ahmed.pharostask.R;

public class SearchActivity extends AppCompatActivity implements khaled.ahmed.pharostask.Views.SearchView {

    private static boolean active = false;
    private RecyclerView recyclerView;
    private TextView noResult;
    private CitiesAdapter adapter;
    private SearchView searchView;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.search_recycler);
        noResult = findViewById(R.id.search_text);
        searchView = findViewById(R.id.searchbar);
        initialize();
    }

    private void initialize() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                /**
                 * Get Data Here after submit then return true
                 */
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.search(s);
                return false;
            }
        });
        presenter = new SearchPresenter(this, SearchActivity.this);
        presenter.getData();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * this method for set data in first time when open activity
     * this data came from local storage
     */
    @Override
    public void setData(ArrayList<Cities> list) {
        if (list.isEmpty() && active) {
            if (adapter != null) {
                adapter.clear();
            }
            noResult.setVisibility(View.VISIBLE);
        } else {
            noResult.setVisibility(View.GONE);
            adapter = new CitiesAdapter(this, list, recyclerView, null);
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * this method for set data when user start to search for some thing
     */
    @Override
    public void filterData(ArrayList<Cities> filter) {
        if (filter.isEmpty() && active) {
            noResult.setVisibility(View.VISIBLE);
        } else {
            noResult.setVisibility(View.GONE);
            adapter.setItems(filter, false);
        }
    }
}
