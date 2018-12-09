package com.liggiorgio.rumapp.conts;

import android.os.Bundle;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class ContactsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected int getMenuItemId() {
        return 3;
    }
}
