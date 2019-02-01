package com.liggiorgio.rumapp.about;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

import static android.app.PendingIntent.getActivity;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Restore scrolling Y offset
        if (savedInstanceState != null) {
            int offset = savedInstanceState.getInt(getString(R.string.key_scroll_height));
            ScrollView scroller = findViewById(R.id.about_scroller);
            // TODO: test whether KitKat actually scrolls view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                scroller.setScrollY(offset);
            } else {
                scroller.post(() -> scroller.setScrollY(offset));
            }
        }
    }

    // Save current scrolling Y offset
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int offset = findViewById(R.id.about_scroller).getScrollY();
        savedInstanceState.putInt(getString(R.string.key_scroll_height), offset);

        // Call superclass
        super.onSaveInstanceState(savedInstanceState);
    }

    // Button actions
    public void buttonPress(View v) {
        switch (v.getId()) {
            case R.id.button_call: {
                // Dial telephone number
                Intent callIntent = new Intent();
                callIntent.setAction(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + getString(R.string.about_phone)));
                startActivity(callIntent);
            } break;
            case R.id.button_mail: {
                // Send email
                Intent mailIntent = new Intent();
                mailIntent.setAction(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + getString(R.string.about_email)));
                //mailIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.about_email));
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject));
                mailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_content));
                if (mailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mailIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
                }
            } break;
            case R.id.button_map: {
                // Dial telephone number
                Intent mapIntent = new Intent();
                mapIntent.setAction(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse("geo:?q=" + getString(R.string.about_location_query)));
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
                }
            } break;
            case R.id.button_facebook: {
                // Facebook page
                Intent fbIntent = new Intent();
                fbIntent.setAction(Intent.ACTION_VIEW);
                fbIntent.setData(Uri.parse(getFacebookURL()));
                if (fbIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(fbIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
                }
            } break;
            case R.id.button_twitter: {
                // Twitter profile
                Intent twIntent = new Intent();
                twIntent.setAction(Intent.ACTION_VIEW);
                twIntent.setData(Uri.parse(getTwitterURL()));
                if (twIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(twIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
                }
            } break;
            case R.id.button_instagram: {
                // Instagram profile
                Intent igIntent = new Intent();
                igIntent.setAction(Intent.ACTION_VIEW);
                igIntent.setData(Uri.parse(getInstagramURL()));
                if (igIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(igIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
                }
            } break;
            case R.id.button_youtube: {
                // Youtube channel
                Intent ytIntent = new Intent();
                ytIntent.setAction(Intent.ACTION_VIEW);
                ytIntent.setData(Uri.parse(getYoutubeURL()));
                if (ytIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(ytIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Nessuna applicazione può eseguire l'azione", Toast.LENGTH_SHORT).show();
                }
            } break;
        }
    }

    private String getFacebookURL() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + getString(R.string.facebook_url);
            } else { //older versions of fb app
                return "fb://page/" + getString(R.string.facebook_id);
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            return getString(R.string.facebook_url); //normal web url
        }
    }

    private String getTwitterURL() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            packageManager.getPackageInfo("com.twitter.android", 0);
            return "twitter://user?user_id=" + getString(R.string.twitter_id);
        }
        catch (PackageManager.NameNotFoundException e) {
            return getString(R.string.twitter_url);
        }
    }

    private String getInstagramURL() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            packageManager.getPackageInfo("com.instagram.android", 0);
            return "http://instagram.com/_u/" + getString(R.string.instagram_id);
        }
        catch (PackageManager.NameNotFoundException e) {
            return getString(R.string.instagram_url);
        }
    }

    private String getYoutubeURL() {
        return "http://www.youtube.com/user/" + getString(R.string.youtube_url);
    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getMenuItemId() {
        return 2;
    }
}
