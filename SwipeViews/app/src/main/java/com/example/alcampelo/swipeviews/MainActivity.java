package com.example.alcampelo.swipeviews;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.Criteria;
import android.location.LocationManager;
import android.content.Context;
import java.util.List;

import com.squareup.picasso.Picasso;


public class MainActivity extends ActionBarActivity implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener,
        LocationListener{

    private String TAG = getClass().getSimpleName();
    private MediaPlayer mp = null;
    private boolean isPlayingAudio = false;
    private boolean isStreamConfigured = false;
    private boolean freezePlayButton = false;
    private boolean startedBuffer = false;
    ProgressDialog mDialog;
    ImageButton playPause;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Getting a reference to the ViewPager defined the layout file */
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();

        /** Instantiating FragmentPagerAdapter */
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(fm);

        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);

        playPause = (ImageButton) findViewById(R.id.play);

        detectPhoneCall();

        configureProgressDialog();

        mLocationManager = (LocationManager)getApplicationContext()
                .getSystemService(LOCATION_SERVICE);

        int oneHour = 36000000;

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,oneHour,
                0, this);

        showWeather();

    }

    public void showWeather() {
        Location l = getLastKnownLocation();

        if(l!=null)
        {
            //get latitude and longitude of the location
            double lng=l.getLongitude();
            double lat=l.getLatitude();

            mLocationManager.removeUpdates(this);

            QueryWeather qw = new QueryWeather(this,getApplicationContext(),lat,lng);
            qw.queryWeather();
        }
        else
        {
            System.out.println("Enable the fucking provider");
        }
    }

    private Location getLastKnownLocation() {
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    public void configureProgressDialog(){
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Sintonizando na rádio");
        mDialog.setCancelable(false);
    }

    public void detectPhoneCall(){
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    pauseAudio();
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    playAudio();
                } /*else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold
                }*/
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    public void pauseAudio(){
         if (mp != null) {
            isPlayingAudio = false;
            mp.pause();
            playPause.setImageResource(R.drawable.play);
        }

    }

    public void playAudio(){
        if (mp != null) {
            isPlayingAudio = true;
            mp.start();
            playPause.setImageResource(R.drawable.pause);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playOrPause(View view) {

        if(!freezePlayButton) {
            if (!isStreamConfigured) {

                mDialog.show();
                Uri myUri = Uri.parse(getResources().getString(R.string.url));
                try {
                    if (mp == null) {
                        this.mp = new MediaPlayer();
                    } else {
                        mp.stop();
                        mp.reset();
                    }

                    freezePlayButton = true;
                    mp.setDataSource(this, myUri); // Go to Initialized state
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.setOnPreparedListener(this);
                    mp.setOnBufferingUpdateListener(this);
                    mp.setOnErrorListener(this);
                    mp.prepareAsync();

                    Log.d(TAG, "LoadClip Done");
                } catch (Throwable t) {
                    Log.d(TAG, t.toString());
                }

                isStreamConfigured = true;
            } else {

                if (isPlayingAudio) {
                    isPlayingAudio = false;
                    mp.pause();
                    playPause.setImageResource(R.drawable.play);
                } else {
                    isPlayingAudio = true;
                    mp.start();
                    playPause.setImageResource(R.drawable.pause);
                }
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "Stream is prepared");
        mp.start();
        isPlayingAudio = true;
        freezePlayButton = false;
        mDialog.dismiss();
        playPause.setImageResource(R.drawable.pause);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            isPlayingAudio = false;
            playPause.setImageResource(R.drawable.play);
        }

    }

    public void onCompletion(MediaPlayer mp) {
        if (mp != null) {
            mp.stop();
            isPlayingAudio = false;
            playPause.setImageResource(R.drawable.play);
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        StringBuilder sb = new StringBuilder();
        sb.append("Media Player Error: ");
        switch (what)
        {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                sb.append("Not Valid for Progressive Playback");
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                sb.append("Server Died");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                sb.append("Unknown");
                break;
            default:
                sb.append(" Non standard (");
                sb.append(what);
                sb.append(")");
        }
        sb.append(" (" + what + ") ");
        sb.append(extra);
        Log.e(TAG, sb.toString());
        freezePlayButton = false;
        return true;
    }

    public void onBufferingUpdate(MediaPlayer mp, int percent) {

        if (!startedBuffer) {
            mDialog.show();
            mDialog.setMessage("Buffering Áudio");
            startedBuffer = true;
            playPause.setImageResource(R.drawable.play);
        }
        else if (percent == 100) {
            mDialog.dismiss();
            playPause.setImageResource(R.drawable.pause);
        }

        Log.d(TAG, "PlayerService onBufferingUpdate : " + percent + "%");
    }

    public void exitApp(View view)
    {
        finish();
       System.exit(0);
    }

    @Override
    public void onLocationChanged(Location location) {
        showWeather();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
           showWeather();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
