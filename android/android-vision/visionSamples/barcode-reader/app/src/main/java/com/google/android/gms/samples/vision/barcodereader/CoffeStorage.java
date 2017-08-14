package com.google.android.gms.samples.vision.barcodereader;

import android.content.SharedPreferences;

public class CoffeStorage {

    private final SharedPreferences preferences;

    public CoffeStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public int get(String clientId) {
        return preferences.getInt(clientId, 0);
    }

    public void set(String clientId, int value) {
        preferences.edit().putInt(clientId, value).commit();
    }
}
