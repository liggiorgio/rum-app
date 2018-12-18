package com.liggiorgio.rumapp.news;

import android.os.AsyncTask;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsFetchAsyncTask extends AsyncTask<String, Void, ArrayList<Item>> {

    private AsyncResponse delegate;

    NewsFetchAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<Item> doInBackground(String... urls) {
        // Create URL for request
        URL url;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Establish connection
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Read result
        BufferedReader in;
        StringBuilder reply;
        try {
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            reply = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                reply.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            urlConnection.disconnect();
        }

        // Get items from content
        String start = "<div class=\"category_column_element";
        String end = "<!-- category_column_element -->";

        String content = reply.substring(reply.indexOf(start), reply.lastIndexOf(end));
        content = StringEscapeUtils.unescapeHtml4(content.replaceAll("\\s+", " ").replaceAll("> <", "><"));
        String[] elements = content.split(end);

        // Build single news items
        ArrayList<Item> result = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        String img, ref, title, date, text;

        for (String element : elements) {

            // Link - Title - Timestamp - Text //
            pattern = Pattern.compile("<h3.*><a\\shref=\"(.*?)\".*>(.*?)</a></h3>.*<div.*>(.*?)\\sin.*<p>(.*?)</p>");
            matcher = pattern.matcher(element);
            if (matcher.find()) {
                // Save data to be wrapped
                ref = matcher.group(1).trim();
                title = matcher.group(2).trim();
                date = matcher.group(3).trim();
                text = matcher.group(4).trim();
                pattern = Pattern.compile("<img.*src=\"//(.*?)\".*>");
                matcher = pattern.matcher(element);
                if (matcher.find())
                    img = "http://" + matcher.group(1).trim();
                else
                    img = null;
                result.add(new NewsItem(img, ref, title, date, text));
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> result) {
        delegate.processFinish(result);
    }

}