package rainmekka.andela.com.bakingreciepeapp.data;

/**
 * Created by Oluleke on 7/19/2017.
 */

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.ui.ReciepeDetailActivity;
import rainmekka.andela.com.bakingreciepeapp.ui.StepDetailFragment;


public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {
    private Reciepe mReciepes;
    private ArrayList<Step> mSteps;
    private Context mContext;
    boolean mTwoPaneMode;

    public StepListAdapter(Reciepe reciepes, Context context) {

        this.mReciepes = reciepes;
        this.mContext = context;
        this.mSteps = this.mReciepes.steps;
    }
    public StepListAdapter(ArrayList<Step> steps, Context context) {
        this.mSteps = steps;
        this.mContext = context;
    }

    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_step_list,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txt_step_details.setText(mSteps.get(i).shortDescription);
        if (!mSteps.get(i).thumbnailURL.equals("")){
            //useGlide to set Image Resource
            String imgUrlString =  mSteps.get(i).thumbnailURL;
            Picasso.with(mContext) //Context
                    .load(imgUrlString) //URL/FILE
                    .into(viewHolder.img_stepImg);//an ImageView Object to show the loaded image;
            viewHolder.img_stepImg.setContentDescription(mSteps.get(i).shortDescription);
        }


        //Set onClick Listener for text
        final int index = i;
        viewHolder.txt_step_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setReciepeStepList(mSteps);

                stepDetailFragment.setReciepeStepList(mSteps);
                stepDetailFragment.setReciepeStepListIndex(index);

                FragmentManager activity_fm = ((ReciepeDetailActivity)mContext).getSupportFragmentManager();

                if (mTwoPaneMode){
                    stepDetailFragment.setTwoPaneMode(mTwoPaneMode);
                    activity_fm.beginTransaction()
                            .replace(R.id.step_detail_container, stepDetailFragment)
                            .addToBackStack("stepDetailsFragment_ok")
                            .commit();
                }else{
                    activity_fm.beginTransaction()
                            .replace(R.id.reciepe_detail_container, stepDetailFragment)
                            .addToBackStack("stepDetailsFragment")
                            .commit();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_step_details;
        private ImageView img_stepImg;
        public ViewHolder(View view) {
            super(view);
            txt_step_details = (TextView)view.findViewById(R.id.txt_step_details);
            img_stepImg = (ImageView) view.findViewById(R.id.img_stepImg);
        }
    }

    public void setTwoPaneMode(Boolean twopanemode){
        mTwoPaneMode = twopanemode;
    }

}

