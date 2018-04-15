package khaled.ahmed.pharostask.Presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.RestAPI.APIService;
import khaled.ahmed.pharostask.RestAPI.RetrofitClient;
import khaled.ahmed.pharostask.Utils.SharedData;
import khaled.ahmed.pharostask.Views.HomeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public class HomePresenter {

    private int page;
    private HomeView view;
    private ArrayList<Cities> datalist;
    private Context context;

    public HomePresenter(HomeView view, Context context) {
        this.view = view;
        this.context = context;
        page = 1;
        datalist = new ArrayList<>();
    }

    public void getData() {
        if (checkConnection()) {
            getServerData();
        } else {
            getLocalData();
        }
    }

    /**
     * this method call model to get cities from api server
     */
    public void getServerData() {
        APIService service = RetrofitClient.getService();
        Call<ArrayList<Cities>> call = service.getData(page);
        call.enqueue(new Callback<ArrayList<Cities>>() {
            @Override
            public void onResponse(Call<ArrayList<Cities>> call, Response<ArrayList<Cities>> response) {
                if (response.code() == 200) {
                    if (page == 1) {
                        view.SetDataFirstTime(response.body());
                        page++;
                    } else {
                        view.SetDataReload(response.body());
                        page++;
                    }
                } else {
                    view.ServerError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Cities>> call, Throwable t) {
                view.ServerError();
            }
        });
    }

    /**
     * this method call model to get cities from local in case of no internet connection
     */
    public void getLocalData() {
        datalist = SharedData.getInstance().Edit(context).getCities();
        view.SetDataLocal(datalist);
    }

    /**
     * to save array list of cities to shared preferences
     */
    private void saveLocal() {
        ArrayList<Cities> backUp;
        if (SharedData.getInstance().getCities() != null) {
            backUp = SharedData.getInstance().getCities();
        } else {
            backUp = new ArrayList<>();
        }
        if (page == 1) {
            backUp.clear();
        }
        backUp.addAll(datalist);
        SharedData.getInstance().Edit(context).saveCities(backUp);
    }

    /**
     * this method for check connection if connected then call api
     * else will call data that cashed in shared
     */
    public boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    public int getPage() {
        return page;
    }
}
