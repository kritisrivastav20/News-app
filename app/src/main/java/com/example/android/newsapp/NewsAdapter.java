package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    static class ViewHolder {
        TextView heading;
        TextView date;
        TextView number;
    }

    public NewsAdapter(Context context, List<News> newsFeeds) {
        super(context, 0, newsFeeds);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        News currentNews = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_format, parent, false);
            holder = new ViewHolder();
            holder.heading = (TextView) convertView.findViewById(R.id.newsHeading);
            holder.date = (TextView) convertView.findViewById(R.id.newsDate);
            holder.number = (TextView) convertView.findViewById(R.id.numberId);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.heading.setText(currentNews.getMheading());
        holder.date.setText(currentNews.getmDate());
        holder.number.setText(currentNews.getSerialno());

        return convertView;
    }

}