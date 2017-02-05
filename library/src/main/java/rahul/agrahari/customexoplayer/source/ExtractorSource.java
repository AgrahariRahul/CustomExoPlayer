package rahul.agrahari.customexoplayer.source;

import android.net.Uri;

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

import rahul.agrahari.customexoplayer.builder.BaseFactory;
import rahul.agrahari.customexoplayer.builder.Player;

import static com.google.android.exoplayer2.util.Util.*;

/**
 * Created by Rahul Agrahari on 1/17/2017.
 */
public class ExtractorSource {
    final String TAG = this.getClass().getName();

    static Player mPlayer;

    static boolean mAds;

    static ExtractorSource mInstance;


    private ExtractorSource() {
    }

    public static ExtractorSource getInstance(Player player, boolean ads) {
        mPlayer = player;
        mAds = ads;
        if (mInstance == null) {
            synchronized (ExtractorSource.class) {
                mInstance = new ExtractorSource();
            }
        }
        return mInstance;
    }


    /**
     * In this method load the sample media file like .mp4, .mp3, .mkv, .flv , .ogg etc.
     *
     * @param url
     * @return mediaSource
     */
    public ExtractorMediaSource getExtractorMediaSource(String url) {
        BaseFactory baseFactory = new BaseFactory(mPlayer);
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), baseFactory.buildDataSourceFactory(url),
                new DefaultExtractorsFactory(), mPlayer.getHandler(), new ExtractorEventListener());
        return mediaSource;
    }

    private class ExtractorEventListener implements ExtractorMediaSource.EventListener {
        @Override
        public void onLoadError(IOException error) {
            if (mAds) {
                if (mPlayer.getAdsloaderlistener() != null)
                    mPlayer.getAdsloaderlistener().adsLoadError(TAG + " " + error.getMessage());
            } else {
                if (mPlayer.getPlayerloaderlistener() != null)
                    mPlayer.getPlayerloaderlistener().mediaLoadError(TAG + " " + error.getMessage());
            }
        }
    }
}
