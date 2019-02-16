package com.liggiorgio.rumapp.prefs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.liggiorgio.rumapp.R;
import com.liggiorgio.rumapp.news.DividerItemDecoration;

public class PreferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView listView = getListView();

        // We're using alternative divider.
        setDivider(null);
        listView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.line_divider)));
    }
}
