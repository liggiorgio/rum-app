package com.liggiorgio.rumapp.reader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;
import com.liggiorgio.rumapp.R;

public class ReaderImageGetter implements Html.ImageGetter {
    private Context context;
    private TextView v;

    public ReaderImageGetter(TextView v, Context context) {
        this.v = v;
        this.context = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        // TODO: prevent image deletion due to configuration changes
        LevelListDrawable d = new LevelListDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable empty = ContextCompat.getDrawable(context, R.drawable.ic_broken_image);
            d.addLevel(0, 0, empty);
            assert empty != null;
            d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        }
        new ImageLoadAsyncTask(this, d, v.getRight()).execute("http:" + source);
        return d;
    }

    public void refreshView() {
        v.invalidate();
        v.setText(v.getText());
    }

    public void error() {
        Toast.makeText(context, "Couldn't retrieve image", Toast.LENGTH_SHORT).show();
    }
}
