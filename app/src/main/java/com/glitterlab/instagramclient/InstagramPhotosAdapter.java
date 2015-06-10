package com.glitterlab.instagramclient;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter  extends ArrayAdapter<InstagramPhotos>{

    //what data do we need from activity
    // context data source

    public InstagramPhotosAdapter(Context context,  List<InstagramPhotos> objects) {
        super(context,android.R.layout.simple_expandable_list_item_1, objects);
    }

    //what our item look like
    //use the templates to display this photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the data item for this positions
        InstagramPhotos photo= getItem(position);
        //check we are using recyle view if not the we need to inflate
        if(convertView == null){
            //create a new view from template
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
        }
        //lookup to populating data(image,caption)
        TextView tvcaption= (TextView)convertView.findViewById(R.id.tvCaption);
        ImageView ivphoto= (ImageView)convertView.findViewById(R.id.ivPhoto);

        //insert the model data into ech of the view item
tvcaption.setText(photo.caption);
        ivphoto.setImageResource(0);
        //insert the imageview using piccaso
        Picasso.with(getContext()).load(photo.imageurl).into(ivphoto);


        return  convertView;
    }
}
