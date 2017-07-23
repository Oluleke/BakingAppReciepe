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
    private boolean mTwoPaneDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepe_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPaneDetail = true;
        }



        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle b = getIntent().getExtras();

            if (b!=null){
                //
               mReciepeItem = b.getParcelable(ReciepeDetailFragment.RECIEPE_ITEM);
                //steps = b.getParcelableArrayList("steps");
            }else{
                //do something else here
            }

            ReciepeDetailFragment fragment = new ReciepeDetailFragment();



            if (mTwoPaneDetail){
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                //Default Step == 0
                ArrayList<Step> mSteps = mReciepeItem.steps;
                stepDetailFragment.setReciepeStepList(mSteps);

                stepDetailFragment.setReciepeStepList(mSteps);
                stepDetailFragment.setReciepeStepListIndex(0);
                stepDetailFragment.setTwoPaneMode(mTwoPaneDetail);

                FragmentManager activity_fm2 = getSupportFragmentManager();

                activity_fm2.beginTransaction()
                        .replace(R.id.step_detail_container, stepDetailFragment)
                        .addToBackStack("stepDetailsFragment_dual_pane")
                        .commit();

                fragment.setReciepeObject(mReciepeItem);
                fragment.setTwoPaneMode(mTwoPaneDetail);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.reciepe_detail_container, fragment)
                        .addToBackStack("reciepe_Detail_containerok")
                        .commit();


            }else {
                fragment.setReciepeObject(mReciepeItem);
                fragment.setTwoPaneMode(mTwoPaneDetail);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.reciepe_detail_container, fragment)
                        .addToBackStack("reciepe_Detail_container")
                        .commit();
//                fragment.setReciepeObject(mReciepeItem);
//                fragment.setTwoPaneMode(mTwoPaneDetail);
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.reciepe_detail_container, fragment)
//                        .addToBackStack("reciepe_Detail_container_dualPane")
//                        .commit();
            }





        }
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
