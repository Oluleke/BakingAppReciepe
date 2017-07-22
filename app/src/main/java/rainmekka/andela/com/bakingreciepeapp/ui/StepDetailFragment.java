package rainmekka.andela.com.bakingreciepeapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.data.Step;

/**
 * Created by Oluleke on 6/20/2017.
 */

public class StepDetailFragment extends Fragment {

    public ArrayList<Step> mStepList = new ArrayList<>();
    public int mReciepeStepClassListIndex=0;
    Reciepe mRecipeClass;
    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady;
    boolean isPlayerPlaying;
    int currentWindow;
    long playbackPosition;
    String videoUrl;
    View rootView;

    boolean isVideoAvailable = false;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //mContext = (MainActivity)getContext();

        rootView = inflater.inflate(R.layout.fragment_step_details, container, false);


        TextView txtStepDetails = (TextView) rootView.findViewById(R.id.txt_step_details);

        txtStepDetails.setText(mStepList.get(mReciepeStepClassListIndex).description);

        //txtStepDetails.setText(mRecipeStep.description);

        Button btn_nextstep = (Button)rootView.findViewById(R.id.btn_next_step);

        int nextstep  = mReciepeStepClassListIndex+1;

        String btntext = "Step " + nextstep;

        btn_nextstep.setText(btntext);

        //ToDo: load video into mediacontroller

        if ( !mStepList.get(mReciepeStepClassListIndex).videoURL.isEmpty()){
            isVideoAvailable = true;
            playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
            //initializePlayer();
        }else{
            //ToDO -- Display Image if available or text Showing Video is not available
        }



        //if index is last on list go back to index 0
        if (mReciepeStepClassListIndex+1 < mStepList.size()){

            //load new fragment in detail fragment
            mReciepeStepClassListIndex+=1;


        }else{
            mReciepeStepClassListIndex = 0;
        }

        btn_nextstep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StepDetailFragment stepDetailsFragment_new = new StepDetailFragment();

                stepDetailsFragment_new.setReciepeStepList(mStepList);
                stepDetailsFragment_new.setReciepeStepListIndex(mReciepeStepClassListIndex);

                FragmentManager activity_fm = ((ReciepeDetailActivity)getContext()).getSupportFragmentManager();

                activity_fm.beginTransaction()
                        .replace(R.id.reciepe_detail_container, stepDetailsFragment_new)
                        .addToBackStack("stepDetailsFragment")
                        .commit();
            }
        });

        return rootView;

    }

    public void setReciepeStepList(ArrayList<Step> stepList) {
        mStepList = stepList;
    }
    public void setReciepeStepListIndex(int ReciepeStepClassListIndex) {
        mReciepeStepClassListIndex = ReciepeStepClassListIndex;
    }
    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        videoUrl = mStepList.get(mReciepeStepClassListIndex).videoURL;

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 && isVideoAvailable) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVideoAvailable){
            hideSystemUi();
            if ((Util.SDK_INT <= 23 || player == null)) {
                initializePlayer();
            }
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 && isVideoAvailable) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23 && isVideoAvailable) {
            releasePlayer();
        }
    }
    private void releasePlayer() {
        if (player != null && isVideoAvailable) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
    public void gotoBackground(){
        if (player!=null){
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }

    }
    public void gotoForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }
    public String getVideoUrl(){
        return  videoUrl ;
    }
    public SimpleExoPlayer getPlayer(){
        return  player ;
    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            //Toast.makeText("landscape", Toast.LENGTH_SHORT).show();
//
//
//
//
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            //Toast.makeText("portrait", Toast.LENGTH_SHORT).show();
//        }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        //Load Previous fragment
//
//        ReciepeDetailsFragment reciepeDetailsFragment = new ReciepeDetailsFragment();
//
//        reciepeDetailsFragment.setRecipeClassObject(mRecipeClass);
//
//        FragmentManager mainactivity_fm = ((MainActivity)mContext).getSupportFragmentManager();
//
//        Bundle b = new Bundle();
//
//        b.putParcelable("reciepe_item",mRecipeClass);
//
//        reciepeDetailsFragment.setArguments(b);
//
//        mainactivity_fm.beginTransaction()
//                .replace(R.id.reciepe_list_fragment, reciepeDetailsFragment)
//                .commit();
//
//
//    }


}




