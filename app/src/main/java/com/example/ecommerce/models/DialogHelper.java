package com.example.ecommerce.models;

import android.net.Uri;

public class DialogHelper {
    private Uri imageUri;

    public DialogHelper(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
