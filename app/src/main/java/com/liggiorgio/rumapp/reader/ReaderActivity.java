package com.liggiorgio.rumapp.reader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.liggiorgio.rumapp.ParentActivity;
import com.liggiorgio.rumapp.R;

import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ReaderActivity extends ParentActivity {

    private ArticleItem article;
    private ReaderViewModel model;
    private String title, ref;
    private Drawable image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve intent data
        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (Intent.ACTION_MAIN.equals(action)) {
            // Regular intent
            title = intent.getStringExtra("TITLE");
            ref = intent.getStringExtra("REF");
            Log.d(TAG, "Regular in-app reader");
        } else {
            // Intent from opening link
            final List<String> segments = Objects.requireNonNull(intent.getData()).getPathSegments();
            if (segments.size() == 4) {
                // URL has correct format
                ref = intent.getDataString();
            } else {
                // Wrong URL format
                Toast.makeText(getApplicationContext(), "Formato URL errato", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        // Enable home/up buttons, set title
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        if (title != null)
            actionbar.setTitle(title);
        else
            actionbar.setTitle(R.string.loading);

        // Check connectivity status
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            findViewById(R.id.noConnection).setVisibility(View.VISIBLE);
            notifyNoConnection();
        } else {
            // Show progress bar at first start
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }

        // Article article to show
        article = null;

        // Create view model for news list
        model = ViewModelProviders.of(this).get(ReaderViewModel.class);
        model.setUrl(ref);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        model.setImagePref(prefs.getBoolean("prefLoadImages", true));
        model.getArticle().observe(this, item -> {
            // Update UI
            if (article == null)
                findViewById(R.id.progressBar).setVisibility(View.GONE); // Hide progress bar
            article = item;
            updateView();
        });
        model.getDrawable().observe(this, item -> {
            // Update image header
            if (image == null) {
                image = item;
                if (item != null)
                    findViewById(R.id.reader_image).setVisibility(View.VISIBLE);
            }
            updateHeader();
        });

        // Restore scrolling Y offset
        if (savedInstanceState != null) {
            int offset = savedInstanceState.getInt(getString(R.string.key_scroll_height));
            ScrollView scroller = findViewById(R.id.reader_scroller);
            // TODO: test whether KitKat actually scrolls view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                scroller.setScrollY(offset);
            } else {
                scroller.post(() -> scroller.setScrollY(offset));
            }
        }
    }

    // Notify the user there's no network
    private void notifyNoConnection() {
        Toast.makeText(getApplicationContext(), "Nessuna connessione Internet", Toast.LENGTH_SHORT).show();
    }

    private void updateHeader() {
        ((ImageView) findViewById(R.id.reader_image)).setImageDrawable(image);
    }

    @SuppressWarnings("deprecation")
    private void updateView() {
        // Load text from web page
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loadImage = prefs.getBoolean("prefLoadImages", true);
        ReaderImageGetter textGetter = new ReaderImageGetter(findViewById(R.id.reader_text), getApplicationContext(), loadImage);
        Objects.requireNonNull(getSupportActionBar()).setTitle(article.getTitle());
        ((TextView) findViewById(R.id.reader_title)).setText(article.getTitle());
        ((TextView) findViewById(R.id.reader_author)).setText(article.getAuthor());
        ((TextView) findViewById(R.id.reader_categories)).setText(article.getCategories());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView) findViewById(R.id.reader_text)).setText(Html.fromHtml(article.getText(), Html.FROM_HTML_MODE_LEGACY, textGetter, null));
        } else {
            ((TextView) findViewById(R.id.reader_text)).setText(Html.fromHtml(article.getText(), textGetter, null));
        }
        (findViewById(R.id.reader_text)).setClickable(true);
        ((TextView) findViewById(R.id.reader_text)).setMovementMethod(LinkMovementMethod.getInstance());
        findViewById(R.id.reader_container).setVisibility(View.VISIBLE);
    }

    // Read current article in the default browser
    public void actionExternal(View v) {
        Intent browserIntent = new Intent();
        browserIntent.setAction(Intent.ACTION_VIEW);
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        browserIntent.setData(Uri.parse(ref));
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
        }
    }

    // Share the current article with another app
    public void actionShare(View v) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, title + ": " + ref);
        shareIntent.setType("text/plain");
        Intent shareChooser = Intent.createChooser(shareIntent, "Condividi link tramite");
        if (shareChooser.resolveActivity(getPackageManager()) != null) {
            startActivity(shareChooser);
        } else {
            Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
        }
    }

    // Save current scrolling Y offset
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int offset = findViewById(R.id.reader_scroller).getScrollY();
        savedInstanceState.putInt(getString(R.string.key_scroll_height), offset);

        // Call superclass
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                assert upIntent != null;
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    onBackPressed();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reader;
    }
}
