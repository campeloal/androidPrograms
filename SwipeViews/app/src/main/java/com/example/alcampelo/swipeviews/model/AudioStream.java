package com.example.alcampelo.swipeviews.model;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageButton;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;

import com.example.alcampelo.swipeviews.R;

/**
 * Created by Al Campelo on 4/13/2015.
 */
public class AudioStream implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnBufferingUpdateListener {

    private String TAG = getClass().getSimpleName();
    private MediaPlayer mp = null;
    private boolean isPlayingAudio = false;
    private boolean isStreamConfigured = false;
    private boolean startedBuffer = false;
    ProgressDialog mDialog;
    ImageButton playPause;
    Context context;
    ActionBarActivity activity;

    public AudioStream(Context context,ActionBarActivity activity){
        this.context = context;
        this.activity = activity;

        playPause = (ImageButton) activity.findViewById(R.id.play);

        detectPhoneCall();

        configureProgressDialog();

        mp = new MediaPlayer();
    }

    public void configureProgressDialog(){
        mDialog = new ProgressDialog(activity);
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
        TelephonyManager mgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
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

    public void playOrPauseButtonClicked() {

        if (!isStreamConfigured) {

            mDialog.show();
            Uri myUri = Uri.parse(activity.getResources().getString(R.string.url));
            try {

                mp.setDataSource(activity, myUri); // Go to Initialized state
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
                pauseAudio();
            } else {
                playAudio();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "Stream is prepared");
        mp.start();
        isPlayingAudio = true;
        mDialog.dismiss();
        playPause.setImageResource(R.drawable.pause);
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


}
