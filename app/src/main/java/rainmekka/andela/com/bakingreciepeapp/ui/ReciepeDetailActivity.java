package rainmekka.andela.com.bakingreciepeapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.data.Step;

/**
 * An activity representing a single Reciepe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ReciepeListActivity}.
 */
public class ReciepeDetailActivity extends AppCompatActivity {
    private Reciepe mReciepeItem;
    public ArrayList<Step> steps= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepe_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle b = getIntent().getExtras();

            if (b!=null){
                //
               mReciepeItem = b.getParcelable(ReciepeDetailFragment.RECIEPE_ITEM);
                //steps = b.getParcelableArrayList("steps");
            }else{
                //do somethine else here
            }

            ReciepeDetailFragment fragment = new ReciepeDetailFragment();

            fragment.setReciepeObject(mReciepeItem);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reciepe_detail_container, fragment)
                    .addToBackStack("reciepe_Detail_container")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //

            //getSupportFragmentManager().getBackStackEntryCount()
//            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.reciepe_detail_container);
//            LinearLayout fragment_step_details_container = (LinearLayout)findViewById(R.id.fragment_step_details_container);
//
//            Intent intent;
//
//            if (linearLayout!= null){
//                //load 1st page
//                intent = new Intent(this,ReciepeListActivity.class);
//                NavUtils.navigateUpTo(this, intent);
//
//            }else if(fragment_step_details_container != null){
//                //load list of steps
//                Bundle b = new Bundle();
//                intent = new Intent(this,ReciepeDetailActivity.class);
//                b.putParcelable(ReciepeDetailFragment.RECIEPE_ITEM, mReciepeItem);
//                this.startActivity(intent);
//            }

            //ToDo: handle navigation from Step Detail to Step list or from Step detail to Main list
            Intent intent = new Intent(this,ReciepeListActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
