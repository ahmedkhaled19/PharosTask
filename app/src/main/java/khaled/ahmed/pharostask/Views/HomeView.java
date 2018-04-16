package khaled.ahmed.pharostask.Views;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public interface HomeView {


    void SetDataFirstTime(ArrayList<Cities> list);

    void SetDataReload(ArrayList<Cities> list);

    void SetDataLocal(ArrayList<Cities> cities);

    void ServerError();

    void showSnackbar();
}
