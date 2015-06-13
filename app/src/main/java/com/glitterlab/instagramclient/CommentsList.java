package com.glitterlab.instagramclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommentsList extends ActionBarActivity {
    private ArrayList<InstagramPhotos> commentlist;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);
        Intent i=getIntent();
        String id= i.getStringExtra("media_id");

        commentlist   = new ArrayList<>();
        feachComments(id);
        adapter=new CommentAdapter(this,commentlist);

        ListView lvcomment= (ListView) findViewById(R.id.lvComments);
        lvcomment.setAdapter(adapter);




    }
    public  void feachComments(String id){

      /*  {
            "meta": {
            "code": 200
        },
            "data": [
            {
                "created_time": "1280780324",
                    "text": "Really amazing photo!",
                    "from": {
                "username": "snoopdogg",
                        "profile_picture": "http://images.instagram.com/profiles/profile_16_75sq_1305612434.jpg",
                        "id": "1574083",
                        "full_name": "Snoop Dogg"
            },
                "id": "420"
            },
            ...
            ]
        }*/

        String url="https://api.instagram.com/v1/media/"+id+"/comments?client_id="+ PhotosActivity.CLIENT_ID;

        AsyncHttpClient client= new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(url, null, new JsonHttpResponseHandler() {


            // onSuccess (worked,200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                Log.i("CommentsResponce", response.toString());

                //etrat each of the photo item and  decode item into java object
                JSONArray commentJson = null;
                InstagramPhotos photo = null;
                try {

                    commentJson = response.getJSONArray("data");

                    for (int i = 1; i < commentJson.length(); i++) {
                        //get the json object at the position
                        JSONObject photoJSON = commentJson.getJSONObject(i);
                        //decode the atributes into a data model
                        photo = new InstagramPhotos();
                        photo.commenttext = photoJSON.getString("text");


                        commentlist.add(photo);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

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
        getMenuInflater().inflate(R.menu.menu_comments_list, menu);
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
