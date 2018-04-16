package khaled.ahmed.pharostask.Presenters;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import khaled.ahmed.pharostask.Objects.Cities;
import khaled.ahmed.pharostask.Utils.SharedData;
import khaled.ahmed.pharostask.Views.SearchView;

/**
 * Created by ah.khaled1994@gmail.com on 4/16/2018.
 */

public class SearchPresenter {

    private SearchView view;
    private Context context;
    private ArrayList<Cities> fullList;
    private ArrayList<Cities> filterList;


    public SearchPresenter(SearchView view, Context context) {
        this.view = view;
        this.context = context;
        fullList = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    /**
     * here we will get data
     * we will sort it if size > 50 because first 50 element already sorted and saved
     * but after 50 i need to sort whole list to make sure that it sorted after load more also
     */
    public void getData() {
        if (SharedData.getInstance().Edit(context).getCities() != null) {
            fullList = SharedData.getInstance().Edit(context).getCities();
            if (fullList.size() > 50)
                fullList = Sort(fullList);
            view.setData(fullList);
        } else {
            view.setData(fullList);
        }
    }

    public void search(String name) {
        filterList.clear();
        if (CheckString(name)) {
            for (int i = 0; i < fullList.size(); i++) {
                if (fullList.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                    filterList.add(fullList.get(i));
                } else if (name.length() - 1 > 1) {
                    if (fullList.get(i).getName().toLowerCase().contains(name.substring(0, name.length() - 1).toLowerCase())) {
                        filterList.add(fullList.get(i));
                    } else if (name.length() - 2 > 1) {
                        if (fullList.get(i).getName().toLowerCase().contains(name.substring(0, name.length() - 2).toLowerCase())) {
                            filterList.add(fullList.get(i));
                        }
                    }
                }
            }
        } else if (name.isEmpty() || name.equals("")) {
            filterList.addAll(fullList);
        }
        view.filterData(filterList);
    }

    private Boolean CheckString(String txt) {
        String regex = "^[a-zA-Z ]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(txt);
        return m.matches();
    }

    private ArrayList<Cities> Sort(ArrayList<Cities> data) {
        Collections.sort(data, new Comparator<Cities>() {
            @Override
            public int compare(Cities cities, Cities t1) {
                return cities.getName().compareToIgnoreCase(t1.getName());
            }
        });
        return data;
    }

}
