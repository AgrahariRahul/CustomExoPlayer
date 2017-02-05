package rahul.agrahari.customexoplayer.listener;

import com.google.android.exoplayer2.ExoPlayer;

/**
 * Created by Rahul Agrahari on 1/14/2017.
 */
public interface Adsloaderlistener {
    /**
     * Call this method when ads media file start loading.
     */
    void adsLoad();

    /**
     * Call this method when ads file contain some error..
     */
    void adsLoadError(String error);

    /**
     * Call this method when ads file successfully loads.
     * @param adsExoPlayer
     */
    void adsExoPlayer(ExoPlayer adsExoPlayer);
}
