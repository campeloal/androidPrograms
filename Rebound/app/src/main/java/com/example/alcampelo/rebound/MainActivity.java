package com.example.alcampelo.rebound;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener, SpringListener {

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction

    private ImageView image;
    private SpringSystem mSpringSystem;
    private Spring mSpring;

    private boolean mMovedUp = false;
    private float mOrigY;
    private boolean isScale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.hd);
        image.setOnTouchListener(this);
        mOrigY = image.getY();

        // Create a system to run the physics loop for a set of springs
        SpringSystem springSystem = SpringSystem.create();

        mSpring = springSystem.createSpring();
        mSpring.addListener(this);

        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setSpringConfig(config);

        Toolbar actionToolbar = (Toolbar) findViewById(R.id.toolbar);
        actionToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(actionToolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isScale)
                    mSpring.setEndValue(1f);
                return true;
            case MotionEvent.ACTION_UP:
                if (isScale)
                {
                    mSpring.setEndValue(0f);
                }
                else
                {
                    if (mMovedUp) {
                        mSpring.setEndValue(mOrigY);
                    } else {
                        mOrigY = image.getY();

                        mSpring.setEndValue(mOrigY - 100f);
                    }

                    mMovedUp = !mMovedUp;
                }
                return true;
        }

        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.scale:
                isScale = true;
                return true;

            case R.id.move:
                isScale = false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Overrides of SpringListener interface
    @Override
    public void onSpringUpdate(Spring spring) {
        if (isScale)
        {
            float value = (float) spring.getCurrentValue();
            float scale = 1f - (value * 0.5f);

            image.setScaleX(scale);
            image.setScaleY(scale);
        }
        else
        {
            float value = (float) spring.getCurrentValue();
            image.setY(value);
        }

    }

    @Override
    public void onSpringAtRest(Spring spring) {
    }

    @Override
    public void onSpringActivate(Spring spring) {
    }

    @Override
    public void onSpringEndStateChange(Spring spring) {
    }
}
