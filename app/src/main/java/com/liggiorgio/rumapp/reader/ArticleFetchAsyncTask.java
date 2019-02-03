package com.liggiorgio.rumapp.reader;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class ArticleFetchAsyncTask extends AsyncTask<String, Void, ArticleItem> {

    private AsyncResponse delegate;

    ArticleFetchAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArticleItem doInBackground(String... urls) {
        // Create URL for request
        URL url;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
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
            return null;
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
            return null;
        } finally {
            urlConnection.disconnect();
        }

        // Get items from content
        String start = "<div class=\"single_full_news_element";
        String end = "<!-- single_full_news_element -->";

        String content = reply.substring(reply.indexOf(start), reply.lastIndexOf(end));
        content = StringEscapeUtils.unescapeHtml4(content.replaceAll("\\s+", " ").replaceAll("> <", "><"));

        // Build single news items
        Pattern pattern;
        Matcher matcher;
        String title, image, author, text;
        StringBuilder cats;

        // Full article text is parsed
        pattern = Pattern.compile("<h2.*>(.*?)</h2>(<img.*/>)?.*<div class=\"author_wrapper.*>(.*?)\\sin\\s(<a.*</a>)</div>.*</fb:like></div></div>(.*?)<div class=\"addtoany_share_save_container");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            // Save data to be wrapped
            title = matcher.group(1).trim();
            image = matcher.group(2);
            if (image != null)
                image = "http:" + image.substring(image.indexOf("src=\"") + 5, image.indexOf("\" class"));
            author = matcher.group(3).trim();
            String[] cts = matcher.group(4).split(",");
            cats = new StringBuilder(cts[0].replaceAll("</a>", "").replaceAll("<a.*>", "").trim());
            for (int i=1; i<cts.length; i++) {
                cats.append(", ");
                cats.append(cts[i].replaceAll("</a>", "").replaceAll("<a.*>", "").trim());
            }
            text = matcher.group(5).trim();
            return new ArticleItem(title, image, author, cats.toString(), text);
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArticleItem result) {
        delegate.processFinish(result);
    }

}