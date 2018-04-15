package khaled.ahmed.pharostask.RestAPI;

import java.util.ArrayList;

import khaled.ahmed.pharostask.Objects.Cities;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public interface APIService {

    @GET("cities.json")
    Call<ArrayList<Cities>> getData(@Query("page") int page);

}
