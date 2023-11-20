package br.ifsul.objectfinder_ifsul.utils;

import android.content.Context;
import android.content.Intent;

import br.ifsul.objectfinder_ifsul.interfaces.IOnCreateIntent;

public abstract class IntentUtils {
    public static void createIntentAndStart(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void createIntentAndStart(Context context, Class<?> cls, IOnCreateIntent iOnCreateIntent) {
        Intent intent = new Intent(context, cls);
        iOnCreateIntent.onCreateIntent(intent);
        context.startActivity(intent);
    }


}
