package com.vladstarikov.openweather.fragments;

import android.os.Bundle;

import io.realm.Realm;

/**
 * Created by Starikov on 11.12.15.
 */
public abstract class RealmFragment extends DebugFragment {

    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getInstance(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    protected Realm getRealm() {
        return realm;
    }
}
