package rainmekka.andela.com.bakingreciepeapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Ingredient;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.rest.RequestInterface;
import rainmekka.andela.com.bakingreciepeapp.ui.NoConnectionActivity;
import rainmekka.andela.com.bakingreciepeapp.ui.ReciepeListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oluleke on 7/29/2017.
 */

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent){
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{


                //private Cursor data = null;
                private Reciepe mReciepe;
                private ArrayList<Ingredient> ing_list = new ArrayList<>();
                private Context mContext;
                private ArrayList<Reciepe> mReciepeList = new ArrayList<>();
                private static final String BASE_URL = "http://go.udacity.com/";

                public GridRemoteViewsFactory (Context applicationContext){
                    mContext = applicationContext;
                }

                @Override
                public int getCount() {
                    return mReciepe == null ? 0 : mReciepe.ingredients.size();
                }

                @Override
                public long getItemId(final int position) {
                    //final int idColumnIndex = data.getColumnIndex(BaseColumns._ID);
                    if (ing_list.get(position) !=null)
                        return position;
                    return position;
                }

                @Override
                public RemoteViews getLoadingView() {
                    return null;
                }

                @Override
                public RemoteViews getViewAt(final int position) {

                    RemoteViews views;
                    Ingredient ing = ing_list.get(position);
                    views = new RemoteViews(mContext.getPackageName(), R.layout.card_ingredients_list);

                    views.setTextViewText(R.id.txt_ingredient_ingredientent,ing.ingredient);
                    views.setTextViewText(R.id.txt_ingredient_quantity,ing.quantity);
                    views.setTextViewText(R.id.txt_ingredient_measure,ing.measure);

                    return views;

                }

                @Override
                public int getViewTypeCount() {
                    return 1;
                }

                @Override
                public boolean hasStableIds() {
                    return true;
                }

                @Override
                public void onCreate() {
                    // Nothing to do
                }

                @Override
                public void onDataSetChanged() {
                    //initialize data here

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RequestInterface request = retrofit.create(RequestInterface.class);
                    Call<List<Reciepe>> call = request.getJSON();
                    call.enqueue(new Callback<List<Reciepe>>() {
                        @Override
                        public void onResponse(Call<List<Reciepe>> call, Response<List<Reciepe>> response) {
                            List <Reciepe> jsonResponse = response.body();
                            mReciepeList = new ArrayList<>(jsonResponse);
                            mReciepe = mReciepeList.get(0);
                            ing_list = mReciepe.ingredients;

                            Log.i("Error","I got in here from Widget");

                        }
                        @Override
                        public void onFailure(Call<List<Reciepe>> call, Throwable t) {
                            Log.d("Error",t.getMessage());
//                            Intent intent = new Intent(getBaseContext(),NoConnectionActivity.class);
//                            intent.putExtra(ERROR_MESSAGE_TEXT,getString(R.string.error_retrieving_data));
//                            startActivity(intent);
                        }


                });
                    mReciepe = mReciepeList.get(0);
                    ing_list = mReciepe.ingredients;
                }

                @Override
                public void onDestroy() {
//                    if (data != null) {
//                        data.close();
//                        data = null;
//                    }
                }
            }





