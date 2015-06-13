package com.glitterlab.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class CommentAdapter extends ArrayAdapter<InstagramPhotos>

    {




        public CommentAdapter(Context context, List<InstagramPhotos> objects) {
        super(context, android.R.layout.simple_expandable_list_item_1, objects);

    }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        //get the data item for this positions
        InstagramPhotos photo = getItem(position);
        //check we are using recyle view if not the we need to inflate
        if (convertView == null) {
            //create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, parent, false);
        }

        TextView tvCommentText = (TextView) convertView.findViewById(R.id.tvcoment);




            tvCommentText.setText(photo.commenttext);





        return convertView;
    }


    }
