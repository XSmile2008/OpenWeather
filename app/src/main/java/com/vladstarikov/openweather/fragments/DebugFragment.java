package com.vladstarikov.openweather.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladstarikov.openweather.activities.MainActivity;

/**
 * Created by Starikov on 10.12.15.
 */
public abstract class DebugFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onLowMemory() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onLowMemory()");
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onConfigurationChanged()");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onResume()");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.i(MainActivity.LOG_TAG, "Fragment" + getId() + ".onStart()");
        super.onStart();
    }
}
