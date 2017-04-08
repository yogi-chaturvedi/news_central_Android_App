package com.newscentral.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.newscentral.model.News;

import java.util.ArrayList;
import java.util.Random;

import newscentral.com.newscentral.R;

/**
 * Created by Yogesh on 06-12-2016.
 */

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<NewsHolder> {

    private Context applicationContext;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    private News[] newsList;

    public TestRecyclerViewAdapter(Context applicationContext, News[] newsList) {
        this.applicationContext = applicationContext;
        this.newsList = newsList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.length;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new NewsHolder(applicationContext, view, newsList);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new NewsHolder(applicationContext, view, newsList);
            }
        }
        TransitionInflater s;
        return null;
    }


    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.title.setText(newsList[position].getTitle());
        holder.description.setText(newsList[position].getDescription());
        holder.publishDate.setText(newsList[position].getPublishedOn());
        // call Animation function
        setAnimation(holder.itemView, position);
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }


}
