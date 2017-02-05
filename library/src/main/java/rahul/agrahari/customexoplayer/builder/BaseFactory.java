package rahul.agrahari.customexoplayer.builder;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import rahul.agrahari.customexoplayer.listener.DataTransferlistener;

/**
 * Created by Rahul Agrahari on 1/14/2017.
 */
public class BaseFactory {

    Player mPlayer;

    final int CONNECTION_TIMEOUT = 250000;

    final int READ_TIMEOUT = 250000;


    public BaseFactory(Player player) {
        this.mPlayer = player;
    }

    /**
     * This method load the media file from server or local directory.
     *
     * @param url
     * @return DefaultDataSourceFactory
     */
    public DataSource.Factory buildDataSourceFactory(String url) {
        if (Util.isLocalFileUri(Uri.parse(url))) {
            if (getPermission())
                return buildFileDataSourceFactory();
            else
                return null;
        } else
            return buildHttpDataSourceFactory();
    }

    private boolean getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mPlayer.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (mPlayer.getContext() instanceof Activity)
                    ActivityCompat.requestPermissions((Activity) mPlayer.getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * This method perform to get the media file from network.
     *
     * @return DefaultHttpDataSourceFactory
     */
    public HttpDataSource.Factory buildHttpDataSourceFactory() {
        DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory(getUserAgent(),
                mPlayer.getDataSourceTransfer(), getConnectionTimeout(), getReadTimeout(), true);
        return defaultHttpDataSourceFactory;
    }


    private int getConnectionTimeout() {
        return mPlayer.getConnectionTimeOut() == 0 ? CONNECTION_TIMEOUT : mPlayer.getConnectionTimeOut();
    }

    private int getReadTimeout() {
        return mPlayer.getReadTimeOut() == 0 ? READ_TIMEOUT : mPlayer.getReadTimeOut();
    }

    /**
     * This method load the media file from local.
     *
     * @return FileDataSourceFactory
     */
    public FileDataSourceFactory buildFileDataSourceFactory() {
        if (mPlayer != null && mPlayer.getDataSourceTransfer() != null)
            return new FileDataSourceFactory(mPlayer.getDataSourceTransfer());
        else
            return new FileDataSourceFactory();
    }


    private String getUserAgent() {
        String appname = mPlayer.getAppName() == null || mPlayer.getAppName().isEmpty() ? " CustomExoPlayer" : mPlayer.getAppName();
        return Util.getUserAgent(mPlayer.getContext(), appname);
    }
}
