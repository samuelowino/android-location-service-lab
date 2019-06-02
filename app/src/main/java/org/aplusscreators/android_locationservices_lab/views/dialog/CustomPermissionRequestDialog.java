package org.aplusscreators.android_locationservices_lab.views.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

public class CustomPermissionRequestDialog extends Dialog {

    Context context;

    public CustomPermissionRequestDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


}
