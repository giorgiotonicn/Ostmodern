package com.giorgio.ostmoderntest.controllers;

import android.os.AsyncTask;

import com.giorgio.ostmoderntest.DetailListener;
import com.giorgio.ostmoderntest.MainActivity;
import com.giorgio.ostmoderntest.SetsListener;
import com.giorgio.ostmoderntest.model.Sets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Giorgio on 10/10/15.
 */
public class SetsController {

    private static final String SETS_URL = "/api/sets/";

    private static SetsController instance;
    private ArrayList<Sets> sets;
    private SetsListener listener;
    private DetailListener detailListener;


    public static synchronized SetsController getInstance(){
        if(instance == null){
            instance = new SetsController();
        }
        return instance;
    }

    public SetsController() {
        this.sets = new ArrayList<>();
        new DownloadSets().execute();
    }

    public void setListener(SetsListener setsListener){
        this.listener = setsListener;
    }

    public void setDetailListner(DetailListener detailListner){
        this.detailListener = detailListner;
    }



    /** parse sets json and add every set to sets arrayList */
    private void manageSetsResult(String result) {
        try {
            JSONObject object_result = new JSONObject(result);
            JSONArray objects = object_result.getJSONArray("objects");

            for(int i=0; i<objects.length(); i++){
                JSONObject object = objects.getJSONObject(i);
                Sets set = new Sets();

                if(object.has("title")){
                    set.setTitle(object.getString("title"));
                }
                if(object.has("body")){
                    set.setBody(object.getString("body"));
                }
                if(object.has("film_count")){
                    set.setFilmCount(object.getInt("film_count"));
                }
                if(object.has("items")){
                    set.setItems(object.getJSONArray("items"));
                }
                if(object.has("image_urls")
                        && object.getJSONArray("image_urls").length() > 0
                        && object.getJSONArray("image_urls").get(0) != null){
                    set.setPhotoArray(object.getJSONArray("image_urls"));
                }

                this.sets.add(set);
            }

            listener.onSetsLoaded();

        }catch (JSONException e){
            e.printStackTrace();
            listener.onSetError();
        }
    }

    /** return the sets arrayList */
    public ArrayList<Sets> getSets() {
        return this.sets;
    }

    public void retrieveImageUrl(final int position){
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                URL url;
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                StringBuilder stringBuilder;
                String result = null;

                try {
                    url = new URL(MainActivity.BASE_URL + sets.get(position).getPhotoArray().get(0));
                    urlConnection = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    stringBuilder = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }
                    result = stringBuilder.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null){
                        try{
                            reader.close();
                        }
                        catch (IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                    try {
                        urlConnection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                manageImageResult(position, s);
            }
        }.execute();
    }

    private void manageImageResult(int position, String data){
        try {
            JSONObject imageData = new JSONObject(data);
            if(imageData.has("url")) {
                this.sets.get(position).setImageUrl(imageData.getString("url"));
                this.detailListener.onUrlImageLoaded();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    /** download sets json */
    private class DownloadSets extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            String result = null;

            try {
                url = new URL(MainActivity.BASE_URL + SETS_URL);

                urlConnection = (HttpURLConnection) url.openConnection();

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
                result = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                listener.onSetError();
            } finally {
                if (reader != null){
                    try{
                        reader.close();
                    }
                    catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
                try {
                    urlConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            manageSetsResult(s);
        }
    }

}
