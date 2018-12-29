package com.liggiorgio.rumapp.reader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.liggiorgio.rumapp.ParentActivity;
import com.liggiorgio.rumapp.R;

public class ReaderActivity extends ParentActivity {

    private ArticleItem article;
    private ReaderViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve intent extras
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String ref = intent.getStringExtra("REF");

        // Enable home/up buttons, set title
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(title);

        // Show progress bar at first start
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

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

    @SuppressWarnings("deprecation")
    private void updateView() {
        // Load text from web page
        ((TextView) findViewById(R.id.reader_title)).setText(article.getTitle());
        ((TextView) findViewById(R.id.reader_author)).setText(article.getAuthor());
        ((TextView) findViewById(R.id.reader_categories)).setText(article.getCategories());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView) findViewById(R.id.reader_text)).setText(Html.fromHtml(article.getText(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            ((TextView) findViewById(R.id.reader_text)).setText(Html.fromHtml(article.getText()));
        }
        (findViewById(R.id.reader_text)).setClickable(true);
        ((TextView) findViewById(R.id.reader_text)).setMovementMethod(LinkMovementMethod.getInstance());
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
