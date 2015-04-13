package com.example.alcampelo.swipeviews;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Al Campelo on 4/12/2015.
 */
public class QueryWeather {

    private String QUERY_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private double KELVIN_CONSTANT = 273.15;
    private String TEMPERATURE_UNIT = "°C";
    public String imageURL = "http://openweathermap.org/img/w/";
    Context context;
    ActionBarActivity activity;


    public QueryWeather(ActionBarActivity activity, Context context, double latitude, double longitude) {
        this.context = context;
        this.activity = activity;
        QUERY_URL = QUERY_URL + "lat=" + latitude +"&lon=" + longitude;
        System.out.println(" ");
    }

    public void queryWeather(){
        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(QUERY_URL,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject)
                    {
                        JSONObject main = jsonObject.optJSONObject("main");
                        JSONArray weather = jsonObject.optJSONArray("weather");

                        try {
                            String icon = weather.getJSONObject(0).getString("icon");
                            double temperature = main.getDouble("temp");
                            temperature = temperature - KELVIN_CONSTANT;
                            TextView tempText = (TextView) activity.findViewById(R.id.temperature);
                            tempText.setText("" + (int) temperature + TEMPERATURE_UNIT);

                            imageURL = imageURL + icon + ".png";

                            queryWeatherIcon();
                        }
                        catch (JSONException e) {
                            System.out.println("Could not get json temperature or icon");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {

                    }
                });
    }

    public void queryWeatherIcon(){
        ImageView imageView = (ImageView) activity.findViewById(R.id.weather_icon);
        Picasso.with(context).load(imageURL).into(imageView);
    }
}
