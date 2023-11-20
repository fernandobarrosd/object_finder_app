package br.ifsul.objectfinder_ifsul.design_patterns.factories;

import android.app.AlertDialog;
import android.content.Context;

public abstract class AlertDialogBuilderFactory {
    public static AlertDialog.Builder withMessageAndText(Context context, String message, String title) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(title);
    }
}
