package com.liggiorgio.rumapp.reader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class ImageLoadAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private final ReaderImageGetter owner;
    private LevelListDrawable urlDrawable;
    private final int right;

    public ImageLoadAsyncTask(ReaderImageGetter owner, LevelListDrawable d, int right) {
        this.owner = owner;
        this.urlDrawable = d;
        this.right = right;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String source = params[0];
        Log.d(TAG, "doInBackground " + source);
        try {
            InputStream is = new URL(source).openStream();
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d(TAG, "onPostExecute urlDrawable " + urlDrawable);
        Log.d(TAG, "onPostExecute bitmap " + bitmap);
        if (bitmap != null) {
            Drawable d = new BitmapDrawable(bitmap);
            int multiplier = right/d.getIntrinsicWidth();
            urlDrawable.addLevel(1, 1, d);
            urlDrawable.setBounds(0, 0, right,d.getIntrinsicHeight()*multiplier);
            urlDrawable.setLevel(1);
            owner.refreshView();
        } else {
            owner.error();
        }
    }
}
