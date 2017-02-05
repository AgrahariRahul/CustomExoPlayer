package rahul.agrahari.customexoplayer.listener;

import com.google.android.exoplayer2.ExoPlayer;

/**
 * Created by Rahul Agrahari on 1/14/2017.
 */
public interface Playerloaderlistener {

    /**
     * Call this method when  media file start loading.
     */
    void mediaLoad();

    /**
     * Call this method when media file contain some error..
     */
    void mediaLoadError(String message);

    /**
     * Call this method when media file successfully loads.
     *
     * @param mediaexoplayer
     */
    void mediaExoPlayer(ExoPlayer mediaexoplayer);

}
