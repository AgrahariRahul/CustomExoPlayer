package rahul.agrahari.customexoplayer.listener;

import android.util.Log;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.upstream.DataSpec;

import java.io.IOException;

import rahul.agrahari.customexoplayer.builder.Player;

/**
 * Created by Rahul Agrahari on 1/21/2017.
 */
public class AdaptiveMediaEvent implements AdaptiveMediaSourceEventListener {
    Player mPlayer;
    boolean mAds;
    private final String TAG = this.getClass().getName();

    public AdaptiveMediaEvent(Player player, boolean ads) {
        this.mPlayer = player;
        this.mAds = ads;
    }

    @Override
    public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs) {
    }

    @Override
    public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
    }

    @Override
    public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {
    }

    @Override
    public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {
        if (mAds) {
            if (mPlayer.getAdsloaderlistener() != null)
                mPlayer.getAdsloaderlistener().adsLoadError(TAG + " " + error.getMessage());
        } else {
            if (mPlayer.getPlayerloaderlistener() != null)
                mPlayer.getPlayerloaderlistener().mediaLoadError(TAG + " " + error.getMessage());
        }
    }

    @Override
    public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {
    }

    @Override
    public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaTimeMs) {

    }
}
