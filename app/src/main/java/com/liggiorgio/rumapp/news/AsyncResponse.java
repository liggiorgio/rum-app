package com.liggiorgio.rumapp.news;

import java.util.ArrayList;

public interface AsyncResponse {
    void processFinish(ArrayList<Item> output);
}
