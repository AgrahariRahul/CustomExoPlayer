package rahul.agrahari.customexoplayer.source;

import android.net.Uri;

import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;

import rahul.agrahari.customexoplayer.builder.BaseFactory;
import rahul.agrahari.customexoplayer.builder.Constant;
import rahul.agrahari.customexoplayer.builder.Player;
import rahul.agrahari.customexoplayer.listener.AdaptiveMediaEvent;

/**
 * Created by Rahul Agrahari on 1/21/2017.
 */
public class DashSource {
    private static Player mPlayer;
    private static boolean mAds;
    private static DashSource mInstance;
    private final String TAG = this.getClass().getName();


    private DashSource() {
    }

    public static DashSource getInstance(Player player, boolean ads) {
        mPlayer = player;
        mAds = ads;
        if (mInstance == null) {
            synchronized (DashSource.class) {
                mInstance = new DashSource();
            }
        }
        return mInstance;
    }

    /**
     * In this method load the Dash Streaming Media file.
     *
     * @param url
     * @return dashMediaSource
     */
    public DashMediaSource getDashMediaSource(String url) {
        Uri uri = Uri.parse(url);
        DashManifestParser dashManifestParser = new DashManifestParser();
        BaseFactory baseFactory = new BaseFactory(mPlayer);
        DashMediaSource dashMediaSource = new DashMediaSource(uri, baseFactory.buildDataSourceFactory(url),
                dashManifestParser, new DefaultDashChunkSource.Factory(baseFactory.buildHttpDataSourceFactory()), Constant.MIN_RETRY_COUNT, Constant.LIVE_PRESENTATION_DELAY, mPlayer.getHandler(), new AdaptiveMediaEvent(mPlayer, mAds));
        return dashMediaSource;
    }
}
