package rahul.agrahari.customexoplayer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSpec;

import rahul.agrahari.customexoplayer.R;
import rahul.agrahari.customexoplayer.builder.Constant;
import rahul.agrahari.customexoplayer.builder.Player;
import rahul.agrahari.customexoplayer.listener.Adsloaderlistener;
import rahul.agrahari.customexoplayer.listener.DataSourceTransfer;
import rahul.agrahari.customexoplayer.listener.Playerloaderlistener;

/**
 * In this class create the Exoplayer View
 * <p/>
 * Created by Rahul Agrahari on 1/22/2017.
 */
public class PlayerView extends FrameLayout {

    private SimpleExoPlayerView simpleExoPlayerView;

    private final int TOP_MARGIN = 10;

    private final int BOTTOM_MARGIN = 10;

    private Constant.PlayAds mPlayAds = Constant.PlayAds.START;

    private Constant.MediaStates mPlayerStates;

    private Constant.AdsStates mAdsStates;

    private ExoPlayer adsExoPlayer;

    private ExoPlayer mediaExoPlayer;

    private boolean isAdsPlay;

    private boolean isMediaPlay;

    private PlayerViewEvent mPlayerViewEvent;

    private final String APP_NAME = "CustomExoPlayer";

    private Player player;

    private ProgressBar progressBar;

    private Button skipButton;

    private int skipMarginTop;

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        createSurfaceView();
    }

    public void setPlayer(Player player) {
        if (adsExoPlayer != null)
            adsExoPlayer.release();
        if (mediaExoPlayer != null)
            mediaExoPlayer.release();
        this.player = player;
    }

    private void createSurfaceView() {
        simpleExoPlayerView = new SimpleExoPlayerView(getContext());
        simpleExoPlayerView.getOverlayFrameLayout();
        simpleExoPlayerView.setUseArtwork(true);
        LayoutParams surfaceParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        surfaceParams.setMargins(0, TOP_MARGIN, 0, BOTTOM_MARGIN);
        simpleExoPlayerView.setLayoutParams(surfaceParams);
        this.addView(simpleExoPlayerView);
        skipMarginTop = simpleExoPlayerView.getMeasuredHeight() - simpleExoPlayerView.getMeasuredHeight() / 3 + 20;
        initProgress();
        addSkipButton();
    }

    private void initProgress() {
        progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminate(true);
        progressBar.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.progressbar_state));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);

        if (simpleExoPlayerView != null)
            simpleExoPlayerView.getOverlayFrameLayout().addView(progressBar);
        viewProgress(false);
    }

    /**
     * Initialize and add the skip button for ads.
     */
    private void addSkipButton() {
        skipButton = new Button(getContext());
        skipButton.setText("SKIP");
        skipButton.setTextAppearance(getContext(), android.R.style.TextAppearance_DeviceDefault_Small);
        skipButton.setPadding(5, 2, 5, 2);
        skipButton.setTextColor(Color.WHITE);
        skipButton.setBackgroundColor(Color.parseColor("#80000000"));
        skipButton.setOnClickListener(skipAdsListener());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        params.setMargins(0, skipMarginTop, 20, 0);
        skipButton.setLayoutParams(params);
        if (simpleExoPlayerView != null)
            simpleExoPlayerView.getOverlayFrameLayout().addView(skipButton);
        viewSkip(false);
    }

    /**
     * show or hide the progressbar when Exoplayer is buffering
     *
     * @param flag
     */
    private void viewProgress(boolean flag) {
        if (progressBar != null)
            if (flag)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);
    }

    private void viewSkip(boolean flag) {
        if (skipButton != null)
            if (flag)
                skipButton.setVisibility(View.VISIBLE);
            else
                skipButton.setVisibility(View.GONE);
    }

    /**
     * This method return the SimpleExoPlayerView
     *
     * @return simpleExoPlayerView
     */
    public SimpleExoPlayerView getPlayerView() {
        return simpleExoPlayerView;
    }

    /**
     * Initialize the SimpleExoplayerview for playing media file.
     *
     * @param flag
     * @param exoPlayer
     */
    private void playMedia(boolean flag, ExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            if (mPlayerViewEvent == null) {
                mPlayerViewEvent = new PlayerViewEvent();
            }
            isAdsPlay = flag;
            mPlayerViewEvent.setExoPlayer(exoPlayer);
            mPlayerViewEvent.setPlayAds(isAdsPlay);
            exoPlayer.addListener(mPlayerViewEvent);
            simpleExoPlayerView.setPlayer((SimpleExoPlayer) exoPlayer);
            exoPlayer.setPlayWhenReady(true);
            if (isAdsPlay)
                getPlayerView().setUseController(false);
            else
                getPlayerView().setUseController(true);
        }
    }


    /**
     * In this method remove the eventlistener from exoplayer.
     *
     * @param flag
     * @param exoPlayer
     */
    private void removeEvent(boolean flag, ExoPlayer exoPlayer) {
        isAdsPlay = flag;
        if (mPlayerViewEvent != null && exoPlayer != null)
            exoPlayer.removeListener(mPlayerViewEvent);
    }

    /**
     * In this inner class get the  event  of Exoplayer.
     */
    private class PlayerViewEvent implements ExoPlayer.EventListener {
        boolean playads;
        ExoPlayer exoPlayer;


        public PlayerViewEvent() {
        }

        public void setExoPlayer(ExoPlayer exoPlayer) {
            this.exoPlayer = null;
            this.exoPlayer = exoPlayer;
        }

        public void setPlayAds(boolean playads) {
            this.playads = false;
            this.playads = playads;
        }

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case 1:
                    if (playads)
                        mAdsStates = Constant.AdsStates.LOAD;
                    else
                        mPlayerStates = Constant.MediaStates.LOAD;

                    viewProgress(false);
                    break;
                case 2:
                    if (playads)
                        mAdsStates = Constant.AdsStates.BUFFER;
                    else
                        mPlayerStates = Constant.MediaStates.BUFFER;
                    viewProgress(true);
                    break;
                case 3:
                    if (playads) {
                        viewSkip(true);
                        if (playWhenReady)
                            mAdsStates = Constant.AdsStates.PLAY;
                        else
                            mAdsStates = Constant.AdsStates.PAUSE;
                    } else {
                        if (playWhenReady) {
                            mPlayerStates = Constant.MediaStates.PLAY;
                        } else {
                            mPlayerStates = Constant.MediaStates.PAUSE;
                        }
                    }
                    viewProgress(false);
                    break;
                case 4:
                    if (playads) {
                        viewSkip(false);
                        player.prepareMedia(false);
                        mAdsStates = Constant.AdsStates.END;
                        removeEvent(false, adsExoPlayer);
                    } else {
                        mPlayerStates = Constant.MediaStates.END;
                        removeEvent(false, mediaExoPlayer);
                    }
                    break;
            }

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }

        public boolean isAds() {
            return playads;
        }


    }

    public Constant.PlayAds getmPlayAds() {
        return mPlayAds;
    }

    /**
     * This event listener call when media file start transfer from media source path.
     *
     * @return
     */
    public DataSourceTransfer getDataSourceTransfer() {
        return new DataSourceTransfer() {
            @Override
            public void onTransferStart(Object source, DataSpec dataSpec) {
            }

            @Override
            public void onBytesTransferred(Object source, int bytesTransferred) {
            }

            @Override
            public void onTransferEnd(Object source) {
            }
        };
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * This event listener call when ads load or play.
     *
     * @return
     */
    public Adsloaderlistener getAdsloaderlistener() {
        return new Adsloaderlistener() {
            @Override
            public void adsLoad() {
            }

            @Override
            public void adsLoadError(String error) {
                Toast.makeText(getContext(), "Error on loads ads " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void adsExoPlayer(ExoPlayer adsexoplayer) {
                adsExoPlayer = adsexoplayer;
                if (mPlayAds == Constant.PlayAds.START)
                    playMedia(true, adsExoPlayer);
            }
        };
    }

    public Playerloaderlistener getPlayerloaderlistener() {
        return new Playerloaderlistener() {
            @Override
            public void mediaLoad() {

            }

            @Override
            public void mediaLoadError(String message) {
                Toast.makeText(getContext(), "Error on loads  " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void mediaExoPlayer(ExoPlayer mediaexoplayer) {
                mediaExoPlayer = mediaexoplayer;
                playMedia(false, mediaExoPlayer);
            }
        };
    }

    private OnClickListener skipAdsListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                adsExoPlayer.stop();
                viewSkip(false);
                mAdsStates = Constant.AdsStates.END;
                removeEvent(false, adsExoPlayer);
                adsExoPlayer.release();
                player.prepareMedia(false);
            }
        };

    }

    /**
     * This method pause the media file
     */
    public void pause() {
        if (mPlayerViewEvent != null)
            if (mPlayerViewEvent.playads && adsExoPlayer != null)
                adsExoPlayer.setPlayWhenReady(false);
            else if (mediaExoPlayer != null)
                mediaExoPlayer.setPlayWhenReady(false);
    }

    /**
     * This method resume the Exoplayer
     */
    public void resume() {
        if (mPlayerViewEvent != null)
            if (mPlayerViewEvent.playads && adsExoPlayer != null)
                adsExoPlayer.setPlayWhenReady(true);
            else if (mediaExoPlayer != null)
                mediaExoPlayer.setPlayWhenReady(true);
    }

    /**
     * This method release the Exoplayer
     */
    public void release() {
        if (mPlayerViewEvent != null)
            if (mPlayerViewEvent.playads && adsExoPlayer != null) {
                adsExoPlayer.stop();
                adsExoPlayer.release();
            } else if (mediaExoPlayer != null) {
                mediaExoPlayer.stop();
                mediaExoPlayer.release();
            }
    }


}
