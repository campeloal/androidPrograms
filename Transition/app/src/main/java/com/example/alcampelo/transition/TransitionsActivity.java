package com.example.alcampelo.transition;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.TransitionValues;
import android.view.Menu;
import android.view.MenuItem;
import android.transition.Scene;
import android.transition.Transition;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.transition.TransitionManager;


public class TransitionsActivity extends ActionBarActivity {

    //scenes to transition
    private Scene scene1, scene2;
    //transition to move between scenes
    private Transition transition;
    //flag to swap between scenes
    private boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_transitions);

        //get the layout ID
        RelativeLayout baseLayout = (RelativeLayout)findViewById(R.id.base);

        View startView = getLayoutInflater()
                .inflate(R.layout.start_activity_transitions, baseLayout, false);

        View endView = getLayoutInflater()
                .inflate(R.layout.end_activity_transitions,baseLayout,false);

        //create the two scenes
        scene1 = new Scene(baseLayout,startView);
        scene2 = new Scene(baseLayout,endView);

        //create transition, set properties
        transition = new ChangeBounds();
        transition.setDuration(3000);
        transition.setInterpolator(new AccelerateDecelerateInterpolator());

        //initialize flag
        start=true;
    }

    public void changeScene(View v){
        //check flag
        if(start) {
            TransitionManager.go(scene2, transition);
            start=false;
        }
        else {
            TransitionManager.go(scene1, transition);
            start=true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transitions, menu);
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
}
