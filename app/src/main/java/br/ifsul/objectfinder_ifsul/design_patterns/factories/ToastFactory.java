package br.ifsul.objectfinder_ifsul.design_patterns.factories;

import android.content.Context;
import android.widget.Toast;

public abstract class ToastFactory {
    public static Toast withLengthLong(Context context, String text) {
        return Toast.makeText(context, text, Toast.LENGTH_LONG);
    }
}
