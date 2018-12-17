package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsViewModel extends ViewModel {

    private ArrayList<Item> allNews;
    private MutableLiveData<List<Item>> allNewsLive;

    public LiveData<List<Item>> getNews() {
        if (allNewsLive == null) {
            allNews = new ArrayList<>();
            allNewsLive = new MutableLiveData<>();
            loadNews();
        }
        return allNewsLive;
    }

    private void loadNews() {
        // Do an asynchronous operation to fetch allNewsLive.
        List<Item> updateList = new ArrayList<>();
        String[] titles;

        // Get allNewsLive
        String url = "http://www.reteuniversitariamediterranea.it/page/1/?s";
        new HttpAsyncTask().execute(url);

        /*/ Header
        updateList.add(new SectionItem("In evidenza"));

        // State Name
        updateList.add(new NewsItem("Notizia in evidenza"));

        // Header
        updateList.add(new SectionItem("Ultime allNewsLive"));
        // State Name
        for (int i=0; i<10; i++)
            updateList.add(new NewsItem("Notizia " + (i+1)));

        // Update list
        allNewsLive.setValue(updateList);*/
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            BufferedReader in;
            StringBuilder reply = null;
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

            // Get content items
            String start = "<div class=\"category_column_element";
            String end = "<!-- category_column_element -->";

            String content = reply.substring(reply.indexOf(start), reply.lastIndexOf(end));
            content = StringEscapeUtils.unescapeHtml3(content.replaceAll("\\s+", " ").replaceAll("> <", "><"));
            String[] elements = content.split(end);

            //System.out.println(content);

            //System.out.println("News found: " + elements.length + "\n");

            ArrayList<String> result = new ArrayList<>();

            // Convert to single allNewsLive
            for (String element : elements) {
                Pattern pattern = Pattern.compile("<h3.*><a\\shref=\"(.*?)\".*>(.*?)</a></h3>.*<div.*>(.*?)\\sin.*<p>(.*?)</p>");
                Matcher matcher = pattern.matcher(element);
                if (matcher.find()) {
                    result.add(matcher.group(2).trim());
                    //System.out.println(matcher.group(1).trim());
                    //System.out.println(matcher.group(3).trim());
                    //System.out.println(matcher.group(4).trim());
                } else {
                    System.out.println("No matches");
                }
            /*pattern = Pattern.compile("<img.*src=\"//(.*?)\".*>");
            matcher = pattern.matcher(element);
            if (matcher.find())
                System.out.println("Thumbnail available: http://" + matcher.group(1));
            else
                System.out.println("No images attached");
            System.out.println();*/
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            for (String aResult : result)
                allNews.add(new NewsItem(aResult));

            // Update list
            allNewsLive.setValue(allNews);
        }

    }

}
