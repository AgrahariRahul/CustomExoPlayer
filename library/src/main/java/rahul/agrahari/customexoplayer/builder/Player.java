package rahul.agrahari.customexoplayer.builder;

import android.content.Context;
import android.os.Handler;

import rahul.agrahari.customexoplayer.listener.Adsloaderlistener;
import rahul.agrahari.customexoplayer.listener.DataSourceTransfer;
import rahul.agrahari.customexoplayer.listener.Playerloaderlistener;
import rahul.agrahari.customexoplayer.builder.Constant.PlayAds;


/**
 * Created by Rahul Agrahari on 12/26/2016.
 */
public class Player {
    private Context context;
    private String mediaUrl = "";
    private int connectionTimeOut;
    private int readTimeOut;
    private Handler handler;
    private String appName = "";
    private String adUrl;
    private boolean showAds;
    private Adsloaderlistener adsloaderlistener;
    private Playerloaderlistener playerloaderlistener;
    private DataSourceTransfer dataSourceTransfer;
    private int minBufferTime;
    private int maxBufferTime;
    private PlayAds playAds;
    private boolean isLooping;
    private int loopCount;
    private String[] concatenateUrl;
    private PlayerLoader playerLoader;


    public String[] getConcatenateUrl() {
        return concatenateUrl;
    }

    public Adsloaderlistener getAdsloaderlistener() {
        return adsloaderlistener;
    }


    public DataSourceTransfer getDataSourceTransfer() {
        return dataSourceTransfer;
    }

    public Playerloaderlistener getPlayerloaderlistener() {
        return playerloaderlistener;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public Context getContext() {
        return context;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }


    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public Handler getHandler() {
        return handler;
    }


    public String getAppName() {
        return appName;
    }


    public String getAdUrl() {
        return adUrl;
    }

    public boolean isShowAds() {
        return showAds;
    }


    public PlayAds getPlayAds() {
        return playAds;
    }

    private Player(Builder builder) {
        this.context = builder.sContext;
        this.mediaUrl = builder.sMediaUrl;
        this.handler = builder.sHandler;
        this.connectionTimeOut = builder.sConnectionTimeOut;
        this.readTimeOut = builder.sReadTimeOut;
        this.appName = builder.sAppName;
        this.adUrl = builder.sAdUrl;
        this.showAds = builder.sShowAds;
        this.playAds = builder.sPlayAds;
        this.adsloaderlistener = builder.sAdsloaderlistener;
        this.playerloaderlistener = builder.sPlayerloaderlistener;
        this.isLooping = builder.sIsLooping;
        this.loopCount = builder.sLoopCount;
        this.concatenateUrl = builder.sConcatenateUrl;
        this.dataSourceTransfer = builder.sDataSourceTransfer;
        playerLoader = new PlayerLoader(this);
    }

    public void prepareMedia(boolean loadAds) {
        if (playerLoader != null)
            if (loadAds) {
                playerLoader.createAds();
            } else {
                playerLoader.createMedia();
            }
    }

    public static class Builder {
        private Context sContext;
        private String sMediaUrl;
        private int sConnectionTimeOut;
        private int sReadTimeOut;
        private Handler sHandler;
        private String sAppName;
        private String sAdUrl;
        private boolean sShowAds;
        private PlayAds sPlayAds;
        private Adsloaderlistener sAdsloaderlistener;
        private Playerloaderlistener sPlayerloaderlistener;
        private boolean sIsLooping;
        private int sLoopCount;
        private String[] sConcatenateUrl;
        private DataSourceTransfer sDataSourceTransfer;


        public Builder(Context context, String url, Handler handler) {
            sContext = context;
            sMediaUrl = url;
            sHandler = handler;
        }

        public Builder setAdsListener(Adsloaderlistener adsloaderlistener) {
            sAdsloaderlistener = adsloaderlistener;
            return this;
        }

        public Builder setDataSourceTransfer(DataSourceTransfer dataSourceTransfer) {
            sDataSourceTransfer = dataSourceTransfer;
            return this;
        }

        public Builder setPlayerLoaderListener(Playerloaderlistener playerloaderlistener) {
            sPlayerloaderlistener = playerloaderlistener;
            return this;
        }

        public Builder setConcatenateUrl(String[] url) {
            sConcatenateUrl = url;
            return this;
        }


        public Builder setAdsUrl(String adsurl) {
            sAdUrl = adsurl;
            return this;
        }

        public Builder isShowAds(boolean isshow, PlayAds playAds) {
            sPlayAds = playAds;
            sShowAds = isshow;
            return this;
        }

        public Builder setMediaLoop(boolean isloop, int count) {
            sIsLooping = isloop;
            sLoopCount = count;
            return this;
        }


        public Builder setConnectionTimeOut(int connectiontimeout) {
            sConnectionTimeOut = connectiontimeout;
            return this;
        }

        public Builder setReadTimeOut(int readtimeout) {
            sReadTimeOut = readtimeout;
            return this;
        }

        public Builder setAppName(String appname) {
            sAppName = appname;
            return this;
        }


        public Player build() {
            return new Player(this);
        }
    }
}
