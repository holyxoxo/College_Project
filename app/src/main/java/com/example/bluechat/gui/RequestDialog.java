/*
  ------------------------------------------------------------------------------------------------
  SUMMARY: RequestDialog.java
  ------------------------------------------------------------------------------------------------
  This class is a helper for creating standardized AlertDialogs throughout the application. It
  simplifies the process of showing confirmation dialogs to the user.

  Key Features:
  - Wraps the standard Android AlertDialog for consistent styling and behavior.
  - Provides two constructors: one for a simple dialog with "Yes/No" buttons, and another
  for a dialog with a custom layout that includes a countdown timer.
  - The timed dialog automatically dismisses itself by "clicking" the negative button
  if the user doesn't respond before the timeout.
  ------------------------------------------------------------------------------------------------
 */
package com.example.bluechat.gui;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.bluechat.R;
import com.example.bluechat.tools.Timer;

public class RequestDialog {
    private final int SHOW_TIMER_SECONDS = 5;
    private TextView time;
    private long timeout = -1;
    private final AlertDialog alertDialog;

    public RequestDialog(Activity context, String message, long timeout, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        final View dialogLayout = context.getLayoutInflater().inflate(R.layout.dialog_connection_request, null);
        TextView textViewMessage = dialogLayout.findViewById(R.id.connection_request_text);
        this.time = dialogLayout.findViewById(R.id.countDown);
        this.timeout = timeout;
        textViewMessage.setText(message);
        //dialog creation
        final AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setView(dialogLayout);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes, positiveListener);
        builder.setNegativeButton(android.R.string.no, negativeListener);

        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public RequestDialog(Activity context, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        //dialog creation
        final AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes, positiveListener);
        builder.setNegativeButton(android.R.string.no, negativeListener);

        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void show() {
        alertDialog.show();
        if (timeout != -1) {
            new Timer(timeout, new Timer.DateCallback() {
                @Override
                public void onTick(int hoursUntilEnd, int minutesUntilEnd, int secondsUntilEnd) {
                    if (secondsUntilEnd <= SHOW_TIMER_SECONDS) {
                        if (time.getVisibility() == View.INVISIBLE) {
                            time.setVisibility(View.VISIBLE);
                        }
                        time.setText(String.valueOf(secondsUntilEnd));
                    }
                }

                @Override
                public void onEnd() {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).performClick();
                }
            }).start();
        }
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onDismissListener) {
        alertDialog.setOnCancelListener(onDismissListener);
    }

    public void cancel() {
        alertDialog.cancel();
    }
}
