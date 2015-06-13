package com.glitterlab.instagramclient;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter  extends ArrayAdapter<InstagramPhotos> {

    //what data do we need from activity
    // context data source
    Context mcontext;

    public InstagramPhotosAdapter(Context context, List<InstagramPhotos> objects) {
        super(context, android.R.layout.simple_expandable_list_item_1, objects);
        mcontext = context;
    }

    //what our item look like
    //use the templates to display this photo

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //get the data item for this positions
        InstagramPhotos photo = getItem(position);
        //check we are using recyle view if not the we need to inflate
        if (convertView == null) {
            //create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        //lookup to populating data(image,caption)
        TextView tvcaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivphoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvcomments = (TextView) convertView.findViewById(R.id.tvComments);
        RoundedImageView ivUserPhoto = (RoundedImageView) convertView.findViewById(R.id.ivProfilePhoto);


        //insert the model data into ech of the view item
        tvUserName.setText(photo.username);
        tvcaption.setText(photo.caption);
        tvLikes.setText("Likes: " + photo.likecount);
        tvcomments.setText("Comments:" + photo.commentscount);
        ivphoto.setImageResource(0);
        ivUserPhoto.setImageResource(0);
        //insert the imageview using piccaso
        Picasso.with(getContext()).load(photo.imageurl).into(ivphoto);
        Picasso.with(getContext()).load(photo.userimageUrl).into(ivUserPhoto);

        ImageView ivCommentView= (ImageView) convertView.findViewById(R.id.ivCommentView);
        ivCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstagramPhotos photo = getItem(position);
                Intent i= new Intent(getContext(),CommentsList.class);
                i.putExtra("media_id",photo.media_id);
                getContext().startActivity(i);
            }
        });


        return convertView;
    }


}
