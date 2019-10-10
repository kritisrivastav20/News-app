package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String TAG_LOG = MainActivity.class.getName();
    private static final String GOOGLE_REQUEST_URL =
            "http://content.guardianapis.com/search";
    private NewsAdapter mAdapter;
    TextView emptyText;
    private static final int News_loading = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>());
        listview.setAdapter(mAdapter);
        emptyText = (TextView) findViewById(R.id.empty);
        listview.setEmptyView(emptyText);

        final ConnectivityManager netManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = netManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(News_loading, null, MainActivity.this);
        } else {
            emptyText.setText("No network.");
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentView = mAdapter.getItem(i);
                String url = currentView.geturl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUrl = Uri.parse(GOOGLE_REQUEST_URL);
        Uri.Builder builder = baseUrl.buildUpon();
        builder.appendQueryParameter("order-by", "oldest");
        builder.appendQueryParameter("api-key", "enter your key");
        Log.i(TAG_LOG, "builder URL" + builder.toString());
        String url = builder.toString();

        return new NewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsFeeds) {


        mAdapter.clear();
        if (newsFeeds != null && !newsFeeds.isEmpty()) {
            mAdapter.addAll(newsFeeds);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

}