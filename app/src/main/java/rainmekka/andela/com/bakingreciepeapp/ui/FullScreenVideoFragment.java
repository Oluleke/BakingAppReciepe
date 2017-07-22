package rainmekka.andela.com.bakingreciepeapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import rainmekka.andela.com.bakingreciepeapp.R;
import rainmekka.andela.com.bakingreciepeapp.data.Reciepe;
import rainmekka.andela.com.bakingreciepeapp.data.Step;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Oluleke on 6/20/2017.
 */

public class FullScreenVideoFragment extends Fragment {

    ArrayList<Step> mStepList = new ArrayList<>();
    private int mReciepeStepClassListIndex=0;
    Step mStep;
    String videoURL;
    Context mContext;
    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady;
    boolean isPlayerPlaying;
    int currentWindow;
    long playbackPosition;
    View rootView;

    boolean isVideoAvailable = true;

    public FullScreenVideoFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if ( videoURL != null){
            isVideoAvailable = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       rootView = inflater.inflate(R.layout.fragment_video_fullscreen, container, false);

        Bundle b = savedInstanceState;

        //ToDo: load video into mediacontroller

        if (isVideoAvailable){
            playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view_fullscreen);
            //playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }else{
            //ToDo: display textview showing Unavailable video/Nothing
        }

//        if ( videoURL != null){
//            isVideoAvailable = true;
//            playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view_fullscreen);
//            //initializePlayer();
//        }else{
//            //ToDO -- Display Image if available or text Showing Video is not available
//        }

//        btn_nextstep.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                FullScreenVideoFragment stepDetailsFragment_new = new FullScreenVideoFragment();
//
//                stepDetailsFragment_new.setReciepeStepList(mStepList);
//                stepDetailsFragment_new.setReciepeStepListIndex(mReciepeStepClassListIndex);
//
//                FragmentManager activity_fm = ((ReciepeDetailActivity)getContext()).getSupportFragmentManager();
//
//                activity_fm.beginTransaction()
//                        .replace(R.id.reciepe_detail_container, stepDetailsFragment_new)
//                        .addToBackStack("stepDetailsFragment")
//                        .commit();
//            }
//        });

        return rootView;

    }

    public void setStepItem(Step step) {
        mStep = step;
    }
    public void setVideoURL(String videourl){
        videoURL = videourl;
    }
    public void setVideoPlayer(SimpleExoPlayer simpleExoPlayerplayer){
        player = simpleExoPlayerplayer;
    }
    public void setContext(Context ctx){
        mContext = ctx;
    }

    private void initializePlayer() {

//        Handler mainHandler = new Handler();
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector =
//                new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        player = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        //get videoURL

        SharedPreferences sharedpref = getContext().
                getSharedPreferences(getString(R.string.full_video_url),MODE_PRIVATE);

        String strVideoUrl = (sharedpref.getString(getString(R.string.full_video_url),""));

        videoURL = (videoURL==null?strVideoUrl:mStep.videoURL);

        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        //String videoUrl = mStepList.get(mReciepeStepClassListIndex).videoURL;
        Uri uri = Uri.parse(videoURL);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        isVideoAvailable=true;
    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }
    @Override
    public void onStart() {
        super.onStart();
        hideSystemUi();
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
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
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
            //go back to source step
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
        return videoURL;
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




