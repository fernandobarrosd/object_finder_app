package br.ifsul.objectfinder_ifsul.interfaces;

import android.content.Intent;

@FunctionalInterface
public interface IOnCreateIntent {
    void onCreateIntent(Intent intent);
}