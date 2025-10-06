/*
  ------------------------------------------------------------------------------------------------
  SUMMARY: GuiTools.java
  ------------------------------------------------------------------------------------------------
  This is a utility class for graphical user interface (GUI) operations. It contains static
  helper methods that can be used anywhere in the app to perform common UI-related tasks.
  The primary purpose of this class is to provide a central place for UI conversion logic.
  ------------------------------------------------------------------------------------------------
 */
package com.example.bluechat.gui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class GuiTools {
    public static int convertDpToPixels(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}