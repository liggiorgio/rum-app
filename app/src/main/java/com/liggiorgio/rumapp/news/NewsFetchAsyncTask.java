package com.liggiorgio.rumapp.news;

import android.os.AsyncTask;
import com.liggiorgio.rumapp.R;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsFetchAsyncTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {

    private AsyncResponse delegate;

    NewsFetchAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... urls) {
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
        ArrayList<NewsItem> result = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        int icon;
        String ref, title, date, text;
        StringBuilder cats;

        // Link - Title - Timestamp - Categories - Text
        for (String element : elements) {
            pattern = Pattern.compile("<h3.*><a\\shref=\"(.*?)\".*>(.*?)</a></h3>.*<div.*>(.*?)\\sin\\s(<a.*</a>)</div>.*<p>(.*?)</p>");
            matcher = pattern.matcher(element);
            if (matcher.find()) {
                // Save data to be wrapped
                ref = matcher.group(1).trim();
                title = matcher.group(2).trim();
                date = matcher.group(3).trim();
                String[] cts = matcher.group(4).split(",");
                cats = new StringBuilder(cts[0].replaceAll("</a>", "").replaceAll("<a.*>", "").trim());
                for (int i=1; i<cts.length; i++) {
                    cats.append(", ");
                    cats.append(cts[i].replaceAll("</a>", "").replaceAll("<a.*>", "").trim());
                }
                text = matcher.group(5).trim();
                icon = computeIcon(cats.toString());
                result.add(new NewsItem(icon, ref, title, date, cats.toString(), text));
            }
        }

        return result;
    }

    private int computeIcon(String c) {
        // No category
        int flag = 0;
        int res = R.drawable.ic_topic_uncategorized;

        // General
        if (c.contains("C.U.S."))
            res = R.drawable.ic_topic_sport;
        if (c.contains("Eventi"))
            res = R.drawable.ic_topic_event;
        if (c.contains("Bandi"))
            res = R.drawable.ic_topic_announcement;
        if (c.contains("Ateneo") || c.contains("Scuole"))
            res = R.drawable.ic_topic_school;
        if (c.contains("E.R.S.U."))
            res = R.drawable.ic_topic_management;

        // Departments
        if (c.contains("Scienze di base e applicate")) {
            res = R.drawable.ic_topic_science; flag++;
        }
        if (c.contains("Politecnica") || c.contains("Ingegneria")) {
            res = R.drawable.ic_topic_engineering; flag++;
        }
        if (c.contains("Scienze giuridiche ed economico-sociali") || c.contains("Giurisprudenza")) {
            res = R.drawable.ic_topic_law; flag++;
        }
        if (c.contains("Scienze umane e del patrimonio culturale")) {
            res = R.drawable.ic_topic_humanities; flag++;
        }
        if (c.contains("Medicina e Chirurgia")) {
            res = R.drawable.ic_topic_medicine; flag++;
        }

        // Characteristic c.contains("")
        if (c.contains("Consiglio degli Studenti") || c.contains("Senato accademico") || (c.contains("Scuole") && (flag>1)))
            res = R.drawable.ic_topic_school;
        if (c.contains("Test di ammissione"))
            res = R.drawable.ic_topic_exam;
        if (c.contains("Accoglienza matricole"))
            res = R.drawable.ic_topic_school;
        if (c.contains("Consiglio di Amministrazione"))
            res = R.drawable.ic_topic_management;
        if (c.contains("In evidenza"))
            res = R.drawable.ic_topic_featured;

        return res;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> result) {
        delegate.processFinish(result);
    }

}