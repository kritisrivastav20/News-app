package com.example.android.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    public static final String LOG_TAG = AppUtils.class.getSimpleName();

    private static final String ITEMS = "results";
    private static final String TITLE = "webTitle";
    private static final String URL = "webUrl";
    private static final String DATE = "webPublicationDate";

    public static List<News> getNewsinfo(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<News> extractFeatureFromJson(String newsJsonStr) {

        int number = 0;

        ArrayList<News> newsFeeds = new ArrayList<>();

        try {
            JSONObject rootJsonObject = new JSONObject(newsJsonStr);
            JSONObject response = rootJsonObject.getJSONObject("response");
            JSONArray newsJsonObject = response.getJSONArray(ITEMS);

                for (int i = 0; i < newsJsonObject.length(); i++) {

                    number = number + 1;
                    String serialno = Integer.toString(number);

                    JSONObject itemJsonObject = newsJsonObject.getJSONObject(i);
                    String mheading = itemJsonObject.getString(TITLE);
                    String mDate = itemJsonObject.getString(DATE);
                    String murl = itemJsonObject.getString(URL);
                    newsFeeds.add(new News(mheading, mDate,serialno, murl));
                }

        } catch (JSONException e) {
            Log.d(LOG_TAG, e.toString());
        }
        return newsFeeds;
    }
}