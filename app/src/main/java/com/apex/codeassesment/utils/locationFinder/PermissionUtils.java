package com.apex.codeassesment.utils.locationFinder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.M)
public class PermissionUtils {

    public static boolean hasPermissionGranted(Context context, ArrayList<String> permissions) {
        boolean hasGranted = false;
        for (String permission : permissions) {
            hasGranted = (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
            if (!hasGranted)
                return false;
        }
        return hasGranted;
    }


    /**
     * Check and ask for disabled permissions
     *
     * @param activity    Activity calling the method
     * @param permissions permissions array needed to be checked
     * @param requestCode request code associated with the request call
     * @return flag specifying permission are enabled or not
     */
    public static boolean checkAndRequestPermissions(Activity activity, ArrayList<String> permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        List<String> requiredPerm = new ArrayList<>();
        for (String permission : permissions)
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
                requiredPerm.add(permission);

        if (requiredPerm.size() == 0)
            return true;
        String[] mPermission = new String[requiredPerm.size()];
        mPermission = requiredPerm.toArray(mPermission);
        if (mPermission != null)
            activity.requestPermissions(mPermission, requestCode);
        return false;
    }

}
