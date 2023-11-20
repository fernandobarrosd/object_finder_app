package br.ifsul.objectfinder_ifsul.design_patterns.factories;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import br.ifsul.objectfinder_ifsul.interfaces.IOnOpenActivityResultLauncher;

public abstract class ActivityResultLauncherFactory {
    public static ActivityResultLauncher<Intent> createCameraLauncher(
            AppCompatActivity activity,
            IOnOpenActivityResultLauncher<Intent> iOnOpenActivityResultLauncher) {
        return activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                assert result.getData() != null;
                if (result.getData().getData() != null) {
                    iOnOpenActivityResultLauncher.onOpenActivityResultLauncher(result.getData());
                }
            }
        });
    }

    public static ActivityResultLauncher<String> createGalleryLauncher(
            AppCompatActivity activity,
            IOnOpenActivityResultLauncher<Uri> iOnOpenActivityResultLauncher
    ) {
        return activity.registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                iOnOpenActivityResultLauncher.onOpenActivityResultLauncher(result);
            }
        });
    }

}
