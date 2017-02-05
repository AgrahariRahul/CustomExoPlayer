package rahul.agrahari.customexoplayersample;

import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import rahul.agrahari.customexoplayer.builder.Player;
import rahul.agrahari.customexoplayer.view.PlayerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MenuItem checkedMenuItem;
    private final String APP_NAME = "CustomExoPlayerSample";
    private Player player;
    private PlayerView customPlayerView;
    boolean showads;
    Spinner spinUrl;
    Spinner spinAds;
    TextView tvDefaultUrl;
    TextView tvAdsUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        customPlayerView = (PlayerView) findViewById(R.id.customPlayerView);
        spinUrl = (Spinner) findViewById(R.id.spinUrl);
        spinAds = (Spinner) findViewById(R.id.spinAds);
        tvDefaultUrl = (TextView) findViewById(R.id.tvDefaultUrl);
        tvAdsUrl = (TextView) findViewById(R.id.tvAdsUrl);
        findViewById(R.id.btnLoad).setOnClickListener(this);

        spinUrl.setOnItemSelectedListener(defaultOnItemSelectedListener);
        spinAds.setOnItemSelectedListener(adsSpinnerOnItemSelectedListener);

    }

    private Handler handler = new Handler();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.defaultPlay:
                if (checkedMenuItem != item) {
                    if (checkedMenuItem != null)
                        checkedMenuItem.setChecked(false);
                    item.setChecked(true);

                    playMediaDefault(tvDefaultUrl.getText().toString());
                }
                checkedMenuItem = item;
                break;
            case R.id.playwithads:
                if (checkedMenuItem != item) {
                    if (checkedMenuItem != null)
                        checkedMenuItem.setChecked(false);
                    item.setChecked(true);
                    playMediaWithAds(tvDefaultUrl.getText().toString(), tvAdsUrl.getText().toString());
                }
                checkedMenuItem = item;

                break;
            case R.id.playinlooping:
                if (checkedMenuItem != item) {
                    if (checkedMenuItem != null)
                        checkedMenuItem.setChecked(false);
                    item.setChecked(true);
                    playMediaInLoop(tvDefaultUrl.getText().toString(), 2);
                }
                checkedMenuItem = item;
                break;
            case R.id.playinSequentially:
                if (checkedMenuItem != item) {
                    if (checkedMenuItem != null)
                        checkedMenuItem.setChecked(false);
                    item.setChecked(true);
                    String[] pathlist = getDeviceVideo();
                    playMediaSequence((String) tvDefaultUrl.getText().toString(), pathlist);
                }
                checkedMenuItem = item;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String[] getDeviceVideo() {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = new CursorLoader(MainActivity.this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, null).loadInBackground();
        cursor.moveToFirst();

        String[] videoPathList = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            String path = cursor.getString(0);
            videoPathList[i] = path;
            cursor.moveToNext();
        }
        return videoPathList;
    }

    public void playMediaWithAds(String url, String adsurl) {
        showads = true;
        if (player!=null)
            customPlayerView.release();
        player = new Player.Builder(MainActivity.this, url, handler)
                .setPlayerLoaderListener(customPlayerView.getPlayerloaderlistener())
                .setAdsListener(customPlayerView.getAdsloaderlistener())
                .setAppName(APP_NAME)
                .isShowAds(true, customPlayerView.getmPlayAds())
                .setDataSourceTransfer(customPlayerView.getDataSourceTransfer())
                .setAdsUrl(adsurl)
                .build();
        customPlayerView.setPlayer(player);
    }

    public void playMediaDefault(String url) {
        showads = false;
        customPlayerView.release();
        player = new Player.Builder(MainActivity.this, url, handler)
                .setPlayerLoaderListener(customPlayerView.getPlayerloaderlistener())
                .setAppName(APP_NAME)
                .setDataSourceTransfer(customPlayerView.getDataSourceTransfer())
                .build();
        customPlayerView.setPlayer(player);
    }

    public void playMediaInLoop(String url, int loopcount) {
        showads = false;
        customPlayerView.release();
        player = new Player.Builder(MainActivity.this, url, handler)
                .setMediaLoop(true, loopcount)
                .setAppName(APP_NAME)
                .setPlayerLoaderListener(customPlayerView.getPlayerloaderlistener())
                .setDataSourceTransfer(customPlayerView.getDataSourceTransfer())
                .build();
        customPlayerView.setPlayer(player);
    }

    public void playMediaSequence(String url, String[] suburl) {
        showads = false;
        customPlayerView.release();
        player = new Player.Builder(MainActivity.this, url, handler)
                .setConcatenateUrl(suburl)
                .setPlayerLoaderListener(customPlayerView.getPlayerloaderlistener())
                .setAppName(APP_NAME)
                .setDataSourceTransfer(customPlayerView.getDataSourceTransfer())
                .build();
        customPlayerView.setPlayer(player);
    }

    @Override
    public void onClick(View v) {
        if (player != null)
            player.prepareMedia(showads);
    }

    private AdapterView.OnItemSelectedListener defaultOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String url = getResources().getStringArray(R.array.default_play)[position];
            tvDefaultUrl.setText(url);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private AdapterView.OnItemSelectedListener adsSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String url = getResources().getStringArray(R.array.ads_play)[position];
            tvAdsUrl.setText(url);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


}
