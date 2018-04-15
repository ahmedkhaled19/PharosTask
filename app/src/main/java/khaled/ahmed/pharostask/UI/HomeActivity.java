package khaled.ahmed.pharostask.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.Presenters.HomePresenter;
import khaled.ahmed.pharostask.R;
import khaled.ahmed.pharostask.Views.HomeView;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private HomePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenter(this,HomeActivity.this);
        presenter.getData();
    }

    @Override
    public void SetData(ArrayList<Cities> cities) {

    }

    @Override
    public void SetDataLocal(ArrayList<Cities> cities) {

    }

    @Override
    public void ServerError() {

    }
}
