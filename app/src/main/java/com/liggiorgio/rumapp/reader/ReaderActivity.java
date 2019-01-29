package com.liggiorgio.rumapp.reader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.liggiorgio.rumapp.ParentActivity;
import com.liggiorgio.rumapp.R;

public class ReaderActivity extends ParentActivity {

    private ArticleItem article;
    private ReaderViewModel model;
    private String title, ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve intent extras
        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        ref = intent.getStringExtra("REF");

        // Enable home/up buttons, set title
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(title);

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
        model.getArticle().observe(this, item -> {
            // Update UI
            if (article == null)
                findViewById(R.id.progressBar).setVisibility(View.GONE); // Hide progress bar
            article = item;
            updateView();
        });
    }

    // Notify the user there's no network
    private void notifyNoConnection() {
        Toast.makeText(getApplicationContext(), "Nessuna connessione Internet", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("deprecation")
    private void updateView() {
        // Load text from web page
        ReaderImageGetter imageGetter = new ReaderImageGetter(findViewById(R.id.reader_text), getApplicationContext());
        ((TextView) findViewById(R.id.reader_title)).setText(article.getTitle());
        ((TextView) findViewById(R.id.reader_author)).setText(article.getAuthor());
        ((TextView) findViewById(R.id.reader_categories)).setText(article.getCategories());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView) findViewById(R.id.reader_text)).setText(Html.fromHtml(article.getText(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null));
        } else {
            ((TextView) findViewById(R.id.reader_text)).setText(Html.fromHtml(article.getText(), imageGetter, null));
        }
        (findViewById(R.id.reader_text)).setClickable(true);
        ((TextView) findViewById(R.id.reader_text)).setMovementMethod(LinkMovementMethod.getInstance());
        findViewById(R.id.reader_container).setVisibility(View.VISIBLE);
    }

    // Read current article in the default browser
    public void actionExternal(View v) {
        Intent browserIntent = new Intent();
        browserIntent.setAction(Intent.ACTION_VIEW);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
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
