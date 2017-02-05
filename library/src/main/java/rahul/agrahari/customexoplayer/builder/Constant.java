package rahul.agrahari.customexoplayer.builder;

/**
 * Created by Rahul Agrahari on 1/15/2017.
 */
public class Constant {

    public enum PlayAds {
        START
    }

    public enum MediaStates{
        LOAD,PLAY,PAUSE,READY,BUFFER,END;
    }

    public enum AdsStates{
        LOAD,PLAY,PAUSE,READY,BUFFER,END;
    }

    public static final long LIVE_PRESENTATION_DELAY = 5000l;

    public static final int MIN_RETRY_COUNT = 3;

    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE=100;

}
