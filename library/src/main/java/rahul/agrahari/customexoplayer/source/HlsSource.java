package rahul.agrahari.customexoplayer.source;

import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.upstream.DataSpec;

import java.io.IOException;

import rahul.agrahari.customexoplayer.builder.BaseFactory;
import rahul.agrahari.customexoplayer.builder.Player;
import rahul.agrahari.customexoplayer.listener.AdaptiveMediaEvent;

/**
 * Created by Rahul Agrahari on 1/17/2017.
 */
public class HlsSource {
    private final String TAG = this.getClass().getName();

    private static Player mPlayer;

    private static boolean mAds;

    private static HlsSource mInstance;


    private HlsSource() {
    }

    public static HlsSource getInstance(Player player, boolean ads) {
        mPlayer = player;
        mAds = ads;
        if (mInstance == null) {
            synchronized (HlsSource.class) {
                mInstance = new HlsSource();
            }
        }
        return mInstance;
    }

    /**
     * In this method get the Hls Streaming media file format.
     *
     * @param url
     * @return hlsMediaSource
     */
    public HlsMediaSource getHlsMdiaSource(String url) {
        Uri uri = Uri.parse(url);
        BaseFactory baseFactory = new BaseFactory(mPlayer);
        HlsMediaSource hlsMediaSource = new HlsMediaSource(uri, baseFactory.buildDataSourceFactory(url),
                mPlayer.getHandler(), new AdaptiveMediaEvent(mPlayer, mAds));
        return hlsMediaSource;
    }


}
