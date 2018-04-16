package khaled.ahmed.pharostask.Views;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;

/**
 * Created by ah.khaled1994@gmail.com on 4/16/2018.
 */

public interface SearchView {

    void setData(ArrayList<Cities> list);
    void filterData(ArrayList<Cities> filter);
}
