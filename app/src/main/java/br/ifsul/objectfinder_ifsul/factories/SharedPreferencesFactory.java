package br.ifsul.objectfinder_ifsul.factories;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class SharedPreferencesFactory {
    public static SharedPreferences getSharedPreferencesWithPrivateMode(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
