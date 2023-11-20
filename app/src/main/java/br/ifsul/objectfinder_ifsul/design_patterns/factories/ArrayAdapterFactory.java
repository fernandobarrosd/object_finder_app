package br.ifsul.objectfinder_ifsul.design_patterns.factories;

import android.content.Context;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;

public abstract class ArrayAdapterFactory {
    public static <T> ArrayAdapter<T> withValues(
            Context context, T[] values, @LayoutRes int layoutRes
    ) {
        ArrayAdapter<T> arrayAdapter = new ArrayAdapter<>(context, layoutRes);
        arrayAdapter.addAll(values);
        return arrayAdapter;
    }
}
