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
    private boolean loadImage;

    public ReaderImageGetter(TextView v, Context context, boolean loadImage) {
        this.v = v;
        this.context = context;
        this.loadImage = loadImage;
    }

    @Override
    public Drawable getDrawable(String source) {
        // TODO: prevent image deletion due to configuration changes
        LevelListDrawable d = new LevelListDrawable();
        int resId;
        if (!loadImage) {
            resId = R.drawable.line_divider;
            Drawable empty = ContextCompat.getDrawable(context, resId);
            d.addLevel(0, 0, empty);
            assert empty != null;
            d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
            return d;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            resId = R.drawable.ic_broken_image;
        else
            resId = R.drawable.ic_broken_image_old;
        Drawable empty = ContextCompat.getDrawable(context, resId);
        d.addLevel(0, 0, empty);
        assert empty != null;
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
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
