package rahul.agrahari.customexoplayer.source;

import android.net.Uri;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.upstream.DataSpec;

import java.io.IOException;
import java.io.InputStream;

import rahul.agrahari.customexoplayer.builder.BaseFactory;
import rahul.agrahari.customexoplayer.builder.Constant;
import rahul.agrahari.customexoplayer.builder.Player;
import rahul.agrahari.customexoplayer.listener.AdaptiveMediaEvent;

/**
 * Created by Rahul Agrahari on 1/21/2017.
 */
public class SsSource {
    private final String TAG = this.getClass().getName();

    private static Player mPlayer;

    private static boolean mAds;

    private static SsSource mInstance;


    private SsSource() {
    }

    public static SsSource getInstance(Player player, boolean ads) {
        mPlayer = player;
        mAds = ads;
        if (mInstance == null) {
            synchronized (SsSource.class) {
                mInstance = new SsSource();
            }
        }
        return mInstance;
    }

    /**
     * In this method get the Smooth Streaming media file format.
     * @param url
     * @return ssMediaSource
     */
    public SsMediaSource getSsMediaSource(String url) {
        Uri uri = Uri.parse(url);
        BaseFactory baseFactory = new BaseFactory(mPlayer);
        SsManifestParser ssManifestParser = new SsManifestParser();
        SsMediaSource ssMediaSource = new SsMediaSource(uri, baseFactory.buildDataSourceFactory(url),
                ssManifestParser, new DefaultSsChunkSource.Factory(baseFactory.buildHttpDataSourceFactory()), Constant.MIN_RETRY_COUNT, Constant.LIVE_PRESENTATION_DELAY, mPlayer.getHandler(), new AdaptiveMediaEvent(mPlayer, mAds));
        return ssMediaSource;
    }


}
