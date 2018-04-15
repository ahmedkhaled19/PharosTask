package khaled.ahmed.pharostask.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public class SharedData {

    private static final SharedData ourInstance = new SharedData();

    /*** shared pref tags*/
    private String CITIES = "CITIES";

    /*** shared pref tools*/
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    private SharedData() {
    }

    public static SharedData getInstance() {
        return ourInstance;
    }

    public SharedData Edit(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("STORAGE", Context.MODE_PRIVATE);
        editor = preferences.edit();
        return ourInstance;
    }

    public void saveCities(ArrayList<Cities> cities) {
        Gson gson = new Gson();
        String userStr = gson.toJson(cities);
        editor.putString(CITIES, userStr);
        editor.apply();
    }

    public ArrayList<Cities> getCities() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Cities>>() {
        }.getType();
        String json = preferences.getString(CITIES, "");
        return gson.fromJson(json, type);
    }

}
