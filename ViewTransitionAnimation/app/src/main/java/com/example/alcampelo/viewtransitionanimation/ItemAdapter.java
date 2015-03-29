package com.example.alcampelo.viewtransitionanimation;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Al Campelo on 3/29/2015.
 */
public class ItemAdapter extends ArrayAdapter<Item>
{
    private Context context;
    private Typeface font;

    private static class ViewHolder
    {
        TextView name;
        ImageView image;
    }
    public ItemAdapter(Context context, ArrayList<Item> items,Typeface tf) {
        super(context, 0, items);
        this.context = context;
        this.font = tf;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);

        ViewHolder viewHolder; // view lookup cache
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.item);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            viewHolder.name.setTypeface(this.font);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(item.getName());
        viewHolder.image.setImageResource(item.getImageResource());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_in);
        animation.setDuration(500);
        convertView.startAnimation(animation);
        // Return the completed view to render on screen
        return convertView;
    }
}
