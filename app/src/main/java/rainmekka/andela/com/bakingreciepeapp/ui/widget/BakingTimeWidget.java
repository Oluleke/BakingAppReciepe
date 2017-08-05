package rainmekka.andela.com.bakingreciepeapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Ingredient;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.ui.ReciepeDetailFragment;
import rainmekka.andela.com.bakingreciepeapp.ui.ReciepeIngredientsActivity;
import rainmekka.andela.com.bakingreciepeapp.ui.ReciepeListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidget extends AppWidgetProvider {
//    RecyclerView recyclerView;
//    Reciepe mReciepe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        ArrayList<Ingredient> mIngredient = new ArrayList<>();

        RemoteViews rv;
        rv = getReciepeGridRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private static RemoteViews getReciepeGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_ingredient_gridview, intent);
         return views;
    }
}

