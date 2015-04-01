package com.example.alcampelo.viewtransitionanimation;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ViewAnimationActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mainListView;
    ItemAdapter itemAdapter;
    ArrayList<Item> itemList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        createItems();

        Typeface font = Typeface.createFromAsset(getAssets(), "Snoochie.otf");
        itemAdapter = new ItemAdapter(this,itemList,font);

        // Set the ListView to use the ArrayAdapter
        mainListView = (ListView) findViewById(R.id.main_listview);
        mainListView.setAdapter(itemAdapter);
        mainListView.setOnItemClickListener(this);

        Toolbar actionToolbar = (Toolbar) findViewById(R.id.toolbar);
        actionToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(actionToolbar);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        itemAdapter.notifyDataSetChanged();

    }

    public void createItems()
    {
        Item itemOne = new Item("Frame One",R.drawable.hd_1);
        Item itemTwo = new Item("Frame Two",R.drawable.hd_2);
        Item itemThree = new Item("Frame Three",R.drawable.hd_3);
        Item itemFour = new Item("Frame Four",R.drawable.hd_4);
        Item itemFive = new Item("Frame Five",R.drawable.hd_5);
        Item itemSix = new Item("Frame Six",R.drawable.hd_6);
        Item itemSeven = new Item("Frame Seven",R.drawable.hd_7);
        Item itemEight = new Item("Frame Eight",R.drawable.hd_8);
        Item itemNine = new Item("Frame Nine",R.drawable.hd_9);
        Item itemTen = new Item("Frame Ten",R.drawable.hd_10);
        Item itemEleven = new Item("Frame Eleven",R.drawable.hd_11);
        Item itemTwelve = new Item("Frame Twelve",R.drawable.hd_12);
        Item itemThirteen = new Item("Frame Thirteen",R.drawable.hd_13);
        Item itemFourteen = new Item("Frame Fourteen",R.drawable.hd_14);
        Item itemFifteen = new Item("Frame Fifteen",R.drawable.hd_15);
        Item itemSixteen = new Item("Frame Sixteen",R.drawable.hd_16);


        itemList.add(itemOne);
        itemList.add(itemTwo);
        itemList.add(itemThree);
        itemList.add(itemFour);
        itemList.add(itemFive);
        itemList.add(itemSix);
        itemList.add(itemSeven);
        itemList.add(itemEight);
        itemList.add(itemNine);
        itemList.add(itemTen);
        itemList.add(itemEleven);
        itemList.add(itemTwelve);
        itemList.add(itemThirteen);
        itemList.add(itemFourteen);
        itemList.add(itemFifteen);
        itemList.add(itemSixteen);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_animation, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // create an Intent to take you over to a new DetailActivity
        Intent detailIntent = new Intent(this, DetailActivity.class);

        // pack away the data about the cover
        // into your Intent before you head out
        Item item = itemList.get(position);
        detailIntent.putExtra("text",item.getName());
        detailIntent.putExtra("image",item.getImageResource());

        // start the next Activity using the prepared Intent and transition
        startActivity(detailIntent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }
}
