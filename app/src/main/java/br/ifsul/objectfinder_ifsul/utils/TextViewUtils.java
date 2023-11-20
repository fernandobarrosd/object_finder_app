package br.ifsul.objectfinder_ifsul.utils;

import android.widget.TextView;

public abstract class TextViewUtils {
    public static String convertToString(TextView textView) {
        return textView.getText().toString();
    }
}
