package com.glitterlab.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "e0599db985b749419474868d3ba9138d";
    private ArrayList<InstagramPhotos> photos;
    private  InstagramPhotosAdapter aphoto;
    private SwipeRefreshLayout swipeContainer;
    ListView lvPhoto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        //send  out Api request to popular photos

        photos = new ArrayList<>();


        aphoto= new InstagramPhotosAdapter(this,photos);
       lvPhoto= (ListView)findViewById(R.id.lvPhotos);
        lvPhoto.setAdapter(aphoto);
        feachPopularPhotos();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                feachPopularPhotos();
                lvPhoto.setAdapter(aphoto);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }


    public  void feachPopularPhotos(){
       /* URL-
       Client id: e0599db985b749419474868d3ba9138d

                https://api.instagram.com/v1/media/popular?client_id=CLIENT-ID

        type -{ “data” ==> [x]=> “type”}(image or video) }
    url-{ “data” ==> [x]=> “caption”=>”text}
    Caption-{ “data” ==> [x]=> “images”=> “standard_resolution”=> “url }
    Author Name-{ “data” ==> [x]=> “user”=> “username”}
*/

        String url="https://api.instagram.com/v1/media/popular?client_id="+ CLIENT_ID;

        AsyncHttpClient client= new AsyncHttpClient();
        client.get(url,null,new JsonHttpResponseHandler(){


            // onSuccess (worked,200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

               // type -{ “data” ==> [x]=> “type”}(image or video) }
               //  url-{ “data” ==> [x]=> “caption”=>”text}
               //    Caption-{ “data” ==> [x]=> “images”=> “standard_resolution”=> “url }
               //    Author Name-{ “data” ==> [x]=> “user”=> “username”}

                Log.i("DEBUG",response.toString());

                //etrat each of the photo item and  decode item into java object
                JSONArray photosJson=null;
                try {

                    photosJson= response.getJSONArray("data");

                    for(int i=1;i<photosJson.length();i++){
                        //get the json object at the position
                        JSONObject photoJSON=photosJson.getJSONObject(i);
                        //decode the atributes into a data model
                        InstagramPhotos photo= new InstagramPhotos();
                        photo.username= photoJSON.getJSONObject("user").getString("username");

                        photo.userimageUrl=photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.caption= photoJSON.getJSONObject("caption").getString("text");
                        photo.imageurl= photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imagehight=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("height");
                        photo.likecount= photoJSON.getJSONObject("likes").getString("count");
                        photo.commentscount= photoJSON.getJSONObject("comments").getString("count");
                        //add the decoded object to the photos
                        photos.add(photo);
                        swipeContainer.setRefreshing(false);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
                aphoto.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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
