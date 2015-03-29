package com.example.alcampelo.viewtransitionanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Al Campelo on 3/29/2015.
 */
public class DetailActivity extends ActionBarActivity {

    AnimationDrawable hdAnimation;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        imageView = (ImageView) findViewById(R.id.detail_image);
        textView = (TextView) findViewById(R.id.detail_text);

        String text = this.getIntent().getExtras().getString("text");
        int image = this.getIntent().getExtras().getInt("image");

        imageView.setImageResource(image);
        textView.setText(text);

        prepareHumptyDumptyAnimation();
    }

    public void prepareHumptyDumptyAnimation()
    {
       imageView.setImageBitmap(null);
       imageView.setBackgroundResource(R.drawable.activity_animation);

        // Get the background, which has been compiled to an AnimationDrawable object.
        hdAnimation = (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hdAnimation.start();

        ObjectAnimator moveUpAnim = ObjectAnimator.ofFloat(textView,"Y",-20);
        moveUpAnim.setDuration(1000);
        ObjectAnimator moveDownAnim = ObjectAnimator.ofFloat(textView, "Y", 250);
        moveDownAnim.setDuration(2000);
        moveDownAnim.setInterpolator(new BounceInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(moveDownAnim).after(moveUpAnim);
        animatorSet.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_animation, menu);

        return true;
    }

}
