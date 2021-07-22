package com.fadzri.weatherapiapp;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    private final Context context;
    private String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public void getCityID(String cityName, TextView textView){
        String url ="https://www.metaweather.com/api/location/search/?query="+cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray arr) {
                        try {
                            JSONObject jo = arr.getJSONObject(0);
                            String cityName = jo.getString("title");
                            cityID = jo.getString("woeid");
                            textView.setText("City: "+ cityName +"\n"+"ID: "+ cityID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                textView.setText("error");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void getCityForcastByID(String cityID, TextView textView){

        String url ="https://www.metaweather.com/api/location/"+cityID;

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            String arr = "";
                            JSONArray cw = response.getJSONArray("consolidated_weather");
                            for(int i = 0;i < cw.length(); i++){
                                arr += "id : ";
                                arr += cw.getJSONObject(i).getLong("id")+"\n";
                                arr += "weather state name : ";
                                arr += cw.getJSONObject(i).getString("weather_state_name")+"\n";
                                arr += "weather state abbr : ";
                                arr += cw.getJSONObject(i).getString("weather_state_abbr")+"\n";
                                arr += "Created at : ";
                                arr += cw.getJSONObject(i).getString("created")+"\n";
                                arr += "Minimum Temperature : ";
                                arr += cw.getJSONObject(i).getDouble("min_temp")+"\n";
                                arr += "Maximum Temperature : ";
                                arr += cw.getJSONObject(i).getDouble("max_temp")+"\n";
                                arr += "Current Temperature : ";
                                arr += cw.getJSONObject(i).getDouble("the_temp")+"\n";
                                arr += "Wind Speed : ";
                                arr += cw.getJSONObject(i).getDouble("wind_speed")+"\n";
                                arr += "Wind Direction : ";
                                arr += cw.getJSONObject(i).getDouble("wind_direction")+"\n";
                                arr += "Air Pressure : ";
                                arr += cw.getJSONObject(i).getDouble("air_pressure")+"\n";
                                arr += "Humidity : ";
                                arr += cw.getJSONObject(i).getDouble("humidity")+"\n";
                                arr += "Visibility : ";
                                arr += cw.getJSONObject(i).getDouble("visibility")+"\n";
                                arr += "Predictability : ";
                                arr += cw.getJSONObject(i).getDouble("predictability")+"\n\n";
                            }
                            textView.setText(arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCityForcastByName(String cityName, TextView textView){
        getCityID(cityName, textView);
        getCityForcastByID(cityID, textView);
    }
}
