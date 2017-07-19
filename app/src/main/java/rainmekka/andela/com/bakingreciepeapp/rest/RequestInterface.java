package rainmekka.andela.com.bakingreciepeapp.rest;

/**
 * Created by Oluleke on 7/14/2017.
 */

import java.util.List;

import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("android-baking-app-json")
     Call<List<Reciepe>> getJSON();
    //public void getRecipes(Call<List<Reciepe>> getJSON);
}
