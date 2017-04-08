package com.newscentral.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.newscentral.model.News;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;
import newscentral.com.newscentral.R;

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final News[] newsList;
    public TextView title, publishDate;
    public JustifiedTextView description;
    private Context context;

    public NewsHolder(Context context, View itemView, News[] newsList) {
        super(itemView);
        this.context = context;
        this.newsList = newsList;
        itemView.setOnClickListener(this);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (JustifiedTextView) itemView.findViewById(R.id.description);
        publishDate = (TextView) itemView.findViewById(R.id.publish_date);

    }

    @Override
    public void onClick(View view) {
    }
}