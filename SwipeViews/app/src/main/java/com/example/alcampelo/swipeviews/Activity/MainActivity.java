package com.example.alcampelo.swipeviews.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.alcampelo.swipeviews.Adapter.MyFragmentPagerAdapter;
import com.example.alcampelo.swipeviews.R;
import com.example.alcampelo.swipeviews.model.AudioStream;
import com.example.alcampelo.swipeviews.model.FindLocation;


public class MainActivity extends ActionBarActivity {

    AudioStream audioStream;

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

        audioStream = new AudioStream(getApplicationContext(),this);

        FindLocation findLocation = new FindLocation(getApplicationContext(),this);
        findLocation.showWeather();

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

    public void exitApp(View view)
    {
        finish();
       System.exit(0);
    }

    public void playOrPause(View view) {
        audioStream.playOrPauseButtonClicked();
    }
}
