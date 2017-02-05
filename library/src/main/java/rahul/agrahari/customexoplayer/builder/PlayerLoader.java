package rahul.agrahari.customexoplayer.builder;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import rahul.agrahari.customexoplayer.source.BaseSource;

/**
 * Created by Rahul Agrahari on 1/14/2017.
 */
public class PlayerLoader {
    private final String TAG = this.getClass().getName();
    private int individualAllocationSize = 5000;
    private static final int MIN_BUFFER_MS = 1000;
    private static final int MAX_BUFFER_MS = 20000;
    private static final long BUFFER_PLAYBACK = 1000;
    private static final long BUFFER_PLAYBACK_REBUFFER = 1000;
    private Player mPlayer;
    private MediaSource[] mediaList;

    public PlayerLoader(Player player) {
        this.mPlayer = player;
    }


    public void createAds() {
        if (mPlayer.getAdsloaderlistener() == null)
            throw new IllegalArgumentException(TAG + " can't find Adsloaderlistener");

        if (mPlayer.getAdUrl().isEmpty())
            throw new IllegalArgumentException(TAG + " ads url is empty");

        ExoPlayer adExoPlayer = ExoPlayerFactory.newSimpleInstance(mPlayer.getContext(), getTrackSelector(), getLoadControl());
        BaseSource baseSource = BaseSource.getInstance(mPlayer);

        mPlayer.getAdsloaderlistener().adsLoad();
        MediaSource adsSource = baseSource.getMediaSource(true, mPlayer.getAdUrl());
        adExoPlayer.prepare(adsSource);
        mPlayer.getAdsloaderlistener().adsExoPlayer(adExoPlayer);
    }

    public void createMedia() {
        if (mPlayer.getMediaUrl().isEmpty())
            throw new NullPointerException(TAG + " media url is empty.");

        if (mPlayer.getPlayerloaderlistener() == null)
            throw new IllegalArgumentException(TAG + " can't find Playerloaderlistener");


        ExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(mPlayer.getContext(), getTrackSelector(), getLoadControl());
        BaseSource baseSource = BaseSource.getInstance(mPlayer);
        mPlayer.getPlayerloaderlistener().mediaLoad();

        if (mPlayer.getConcatenateUrl() != null && mPlayer.getConcatenateUrl().length > 0) {
            MediaSource[] mediaSourcesArray = getMediaSourcesArray(baseSource, mPlayer.getConcatenateUrl());
            ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(mediaSourcesArray);
            exoPlayer.prepare(concatenatingMediaSource);
        } else {
            if (mPlayer.isLooping()) {
                MediaSource mediaSource = baseSource.getMediaSource(false, mPlayer.getMediaUrl());
                LoopingMediaSource loopingMediaSource = null;
                if (mPlayer.getLoopCount() != 0)
                    loopingMediaSource = new LoopingMediaSource(mediaSource, mPlayer.getLoopCount());
                else
                    loopingMediaSource = new LoopingMediaSource(mediaSource);
                exoPlayer.prepare(loopingMediaSource);
            } else {
                MediaSource mediaSource = baseSource.getMediaSource(false, mPlayer.getMediaUrl());
                exoPlayer.prepare(mediaSource);
            }
        }

        mPlayer.getPlayerloaderlistener().mediaExoPlayer(exoPlayer);

    }


    private TrackSelector getTrackSelector() {
        AdaptiveVideoTrackSelection.Factory adFactory = new AdaptiveVideoTrackSelection.Factory(new DefaultBandwidthMeter());
        return new DefaultTrackSelector(adFactory);
    }


    private LoadControl getLoadControl() {
        return new DefaultLoadControl(new DefaultAllocator(false, individualAllocationSize),
                MIN_BUFFER_MS, MAX_BUFFER_MS, BUFFER_PLAYBACK, BUFFER_PLAYBACK_REBUFFER);
    }

    private MediaSource[] getMediaSourcesArray(BaseSource baseSources, String[] url) {
        mediaList = new MediaSource[url.length];
        for (int i = 0; i < url.length; i++) {
            MediaSource mediaSource = baseSources.getMediaSource(false, url[i]);
            mediaList[i] = mediaSource;
        }
        return mediaList;
    }

}














