package com.example.alcampelo.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimationActivity extends Activity {


    AnimationDrawable hdAnimation;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        prepareHumptyDumptyAnimation();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hdAnimation.start();
    }

    public void prepareHumptyDumptyAnimation()
    {
        ImageView hd = (ImageView)findViewById(R.id.hd);
        hd.setImageBitmap(null);
        hd.setBackgroundResource(R.drawable.hd_animation);

        // Get the background, which has been compiled to an AnimationDrawable object.
        hdAnimation = (AnimationDrawable) hd.getBackground();
    }

    public void startAnimation(View view) {
        float dest;
        final ImageView aniView = (ImageView) findViewById(R.id.hd);
        final TextView aniTextView = (TextView) findViewById(R.id.textView1);
        switch (view.getId()) {

            case R.id.Button01:
                dest = 360;
                if (aniView.getRotation() == 360)
                {
                    System.out.println(aniView.getAlpha());
                    dest = 0;
                }
                ObjectAnimator animation1 = ObjectAnimator.ofFloat(aniView,
                        "rotation", dest);
                animation1.setDuration(2000);
                animation1.start();
                break;

            case R.id.Button02:
                Paint paint = new Paint();
                float measureTextCenter = paint.measureText(aniTextView.getText()
                        .toString());
                dest = 0 - measureTextCenter;
                if (aniTextView.getX() < 0) {
                    dest = 0;
                }
                ObjectAnimator animation2 = ObjectAnimator.ofFloat(aniTextView,
                        "x", dest);
                animation2.setDuration(2000);
                animation2.start();
                break;

            case R.id.Button03:

                dest = 1;
                if (aniView.getAlpha() > 0) {
                    dest = 0;
                }
                ObjectAnimator animation3 = ObjectAnimator.ofFloat(aniView,
                        "alpha", dest);
                animation3.setDuration(2000);
                animation3.start();
                break;

            case R.id.Button04:

                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(aniView, "alpha",
                        0f);
                fadeOut.setDuration(2000);
                ObjectAnimator mover = ObjectAnimator.ofFloat(aniView,
                        "translationX", -500f, 0f);
                mover.setDuration(2000);
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(aniView, "alpha",
                        0f, 1f);
                fadeIn.setDuration(2000);
                AnimatorSet animatorSet = new AnimatorSet();

                animatorSet.play(mover).with(fadeIn).after(fadeOut);
                animatorSet.start();
                break;

            case R.id.Button05:
                dest = 360;

                if (aniView.getRotation() == 360)
                {
                    dest = 0;
                }

                aniView.animate().rotation(dest).setDuration(2000).withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        aniView.setTranslationY(100);
                    }
                }).setDuration(1000).withEndAction(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        aniView.setTranslationY(-100);
                    }
                }).setDuration(1000);
                break;

            case R.id.Button06:
                Animation anim =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                anim.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        aniTextView.setText("Zoom in started");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        aniTextView.setText("Zoom in ended");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                aniView.startAnimation(anim);
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animation, menu);
        return super.onCreateOptionsMenu(menu);
    }
} 