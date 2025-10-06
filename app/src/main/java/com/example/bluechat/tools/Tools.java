/*
  ------------------------------------------------------------------------------------------------
  SUMMARY: Tools.java
  ------------------------------------------------------------------------------------------------
  This is a utility class that contains general-purpose helper methods used throughout the
  application. Methods in this class are static, meaning they can be called directly without
  creating an instance of the class.
  ------------------------------------------------------------------------------------------------
 */
package com.example.bluechat.tools;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class Tools {
    /**
     * Returns true if the app was granted all the permissions. Otherwise, returns false.
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        // Loop through each permission string passed to the method.
        for (String permission : permissions) {
            // Check if the current permission is granted.
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                // If any permission is not granted, immediately return false.
                return false;
            }
        }
        // If the loop completes, it means all permissions were granted, so return true.
        return true;
    }
}