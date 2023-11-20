package br.ifsul.objectfinder_ifsul.interfaces;

@FunctionalInterface
public interface IOnOpenActivityResultLauncher<T> {
    void onOpenActivityResultLauncher(T returnData);
}