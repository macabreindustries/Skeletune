package com.example.skletune;

import android.app.Activity;
import android.view.MenuItem;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomNavHelper {

    public static void setupBottomBar(Activity activity, BottomAppBar bar, FloatingActionButton fab) {

        bar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                navigate(activity, "HOME");
                return true;
            } else if (id == R.id.nav_music) {
                navigate(activity, "MUSIC");
                return true;
            } else if (id == R.id.nav_notifications) {
                navigate(activity, "NOTIFICATIONS");
                return true;
            } else if (id == R.id.nav_profile) {
                navigate(activity, "PROFILE");
                return true;
            }

            return false;
        });

        // FAB
        fab.setOnClickListener(v -> navigate(activity, "FAB_ADD"));
    }

    private static void navigate(Activity activity, String destination) {

        switch (destination) {
            case "HOME":
                // new Intent...
                break;

            case "MUSIC":
                break;

            case "NOTIFICATIONS":
                break;

            case "PROFILE":
                break;

            case "FAB_ADD":
                break;
        }
    }
}
