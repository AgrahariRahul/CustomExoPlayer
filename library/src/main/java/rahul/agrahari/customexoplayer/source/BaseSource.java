package rahul.agrahari.customexoplayer.source;

import android.nfc.Tag;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.util.Util;

import rahul.agrahari.customexoplayer.builder.Player;

/**
 * Created by Rahul Agrahari on 1/15/2017.
 */
public class BaseSource {
    private final String TAG = this.getClass().getName();

    static Player mPlayer;

    static BaseSource mInstance;

    private BaseSource() {
    }

    public static BaseSource getInstance(Player player) {
        mPlayer = player;
        if (mInstance == null) {
            synchronized (BaseSource.class) {
                if (mInstance == null)
                    mInstance = new BaseSource();
            }
        }
        return mInstance;
    }


    /** In this method get the media source format.
     * @param isAds
     * @param url
     * @return mediaSource
     */
    public MediaSource getMediaSource(boolean isAds, String url) {
        MediaSource mediaSource = null;
        int type = Util.inferContentType(url);
        switch (type) {
            case C.TYPE_DASH:
                mediaSource = DashSource.getInstance(mPlayer, isAds).getDashMediaSource(url);
                break;
            case C.TYPE_HLS:
                mediaSource = HlsSource.getInstance(mPlayer, isAds).getHlsMdiaSource(url);
                break;
            case C.TYPE_SS:
                mediaSource = SsSource.getInstance(mPlayer, isAds).getSsMediaSource(url);
                break;
            case C.TYPE_OTHER:
                mediaSource = ExtractorSource.getInstance(mPlayer, isAds).getExtractorMediaSource(url);
                break;
            default:
                throw new TypeNotPresentException(TAG + " Media Type not supported.", null);

        }
        return mediaSource;
    }
}
