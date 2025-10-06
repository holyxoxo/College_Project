// Check this file again in the future
/*
  SUMMARY: ConversationFragment.java
  ------------------------------------------------------------------------------------------------
  This Fragment manages the chat screen of the application. Once a Bluetooth connection is
  established, this screen is displayed, allowing users to send and receive text messages.

  Key Responsibilities:
  - Displaying incoming and outgoing messages in a RecyclerView.
  - Handling user input from an EditText and sending messages via the BluetoothCommunicator.
  - Listening for Bluetooth connection events like message received, connection lost,
  and disconnection.
  - Managing the lifecycle of the Bluetooth callback to prevent memory leaks.
  ------------------------------------------------------------------------------------------------
 */

package com.example.bluechat.fragments;


import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bluetooth.communicator.BluetoothCommunicator;
import com.bluetooth.communicator.Message;
import com.bluetooth.communicator.Peer;
import com.example.bluechat.Global;
import com.example.bluechat.MainActivity;
import com.example.bluechat.R;
import com.example.bluechat.gui.CustomAnimator;
import com.example.bluechat.gui.GuiTools;
import com.example.bluechat.gui.MessagesAdapter;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;


public class ConversationFragment extends Fragment {
    private ProgressBar loading;
    private static final float LOADING_SIZE_DP = 24;
    private EditText editText;
    private AppCompatImageButton sendButton;
    private RecyclerView mRecyclerView;
    protected TextView description;
    private ConstraintLayout constraintLayout;
    private BluetoothCommunicator.Callback communicatorCallback;
    private Global global;
    private MainActivity activity;
    private MessagesAdapter mAdapter;
    private RecyclerView.SmoothScroller smoothScroller;

    public ConversationFragment() {
        // An empty constructor is always needed for fragments.
    }

    /**
     * Called once when the fragment is first created. This is where you initialize
     * essential components that don't rely on the UI being created yet.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback handles all Bluetooth events for this fragment.
        communicatorCallback = new BluetoothCommunicator.Callback() {
            @Override
            public void onConnectionLost(Peer peer) {
                super.onConnectionLost(peer);
                Toast.makeText(activity,"Connection lost, reconnecting...",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionResumed(Peer peer) {
                super.onConnectionResumed(peer);
                Toast.makeText(activity,"Connection resumed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMessageReceived(Message message, int source) {
                super.onMessageReceived(message, source);
                // When a message is received, add it to the adapter to display it on the screen.
                mAdapter.addMessage(message);
                // Automatically scroll to the new message.
                smoothScroller.setTargetPosition(mAdapter.getItemCount() - 1);
                mRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
            }

            @Override
            public void onDisconnected(Peer peer, int peersLeft) {
                super.onDisconnected(peer, peersLeft);
                // If the last peer disconnects, go back to the pairing screen.
                if (peersLeft == 0) {
                    activity.setFragment(MainActivity.DEFAULT_FRAGMENT);
                }
            }
        };
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * It inflates the XML layout file for the conversation screen.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    /**
     * Called immediately after onCreateView() has returned, but before any saved state
     * has been restored in to the view. This is where you get references to all your UI views.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        sendButton = view.findViewById(R.id.button_send);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        description = view.findViewById(R.id.description);
        loading = view.findViewById(R.id.progressBar2);
        constraintLayout = view.findViewById(R.id.container2);
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy
     * is instantiated. This is where you set up the main logic, like setting up the RecyclerView
     * and click listeners.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get references to the MainActivity and the Global application context.
        activity = (MainActivity) requireActivity();
        global = (Global) activity.getApplication();

        // Set up the toolbar.
        Toolbar toolbar = activity.findViewById(R.id.toolbarConversation);
        activity.setActionBar(toolbar);

        // This code ensures the layout doesn't overlap with the status bar.
        WindowInsets windowInsets = activity.getFragmentContainer().getRootWindowInsets();
        if (windowInsets != null) {
            constraintLayout.dispatchApplyWindowInsets(windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), 0));
        }

        // Set up the RecyclerView for displaying messages.
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setStackFromEnd(true); // This makes messages start from the bottom.
        mRecyclerView.setLayoutManager(layoutManager);

        // This helps the RecyclerView scroll smoothly to the latest message.
        smoothScroller = new LinearSmoothScroller(activity) {
            @Override
            protected int calculateTimeForScrolling(int dx) {
                return 100;
            }
        };

        // Initialize the adapter for the RecyclerView.
        mAdapter = new MessagesAdapter(global.getBluetoothCommunicator().getUniqueName(), new MessagesAdapter.Callback() {
            @Override
            public void onFirstItemAdded() {
                // When the first message appears, hide the initial description text.
                description.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Set the click listener for the send button.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if we are connected to any peers.
                if (global.getBluetoothCommunicator().getConnectedPeersList().size() > 0) {
                    // Check if there is text to send.
                    if (editText.getText().length() > 0) {
                        // Create a new Message object.
                        Message message = new Message(global, "m", editText.getText().toString(), global.getBluetoothCommunicator().getConnectedPeersList().get(0));
                        // Send the message using the BluetoothCommunicator.
                        global.getBluetoothCommunicator().sendMessage(message);
                        // Clear the input field.
                        editText.setText("");
                        // Add the message to our own chat list.
                        mAdapter.addMessage(message);
                        // Smooth scroll to the new message.
                        smoothScroller.setTargetPosition(mAdapter.getItemCount() - 1);
                        mRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
                    }
                }
            }
        });
    }

    /**
     * Called when the fragment becomes visible to the user.
     * This is the best place to start listening for Bluetooth events.
     */
    @Override
    public void onResume() {
        super.onResume();
        // Add the callback to start receiving Bluetooth events.
        global.getBluetoothCommunicator().addCallback(communicatorCallback);
    }

    /**
     * Called when the fragment is no longer visible to the user.
     * It's important to stop listening for events here to avoid memory leaks.
     */
    @Override
    public void onPause() {
        super.onPause();
        // Remove the callback to stop receiving Bluetooth events.
        global.getBluetoothCommunicator().removeCallback(communicatorCallback);
    }

    /**
     * Called when the fragment is being destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * A helper method to show a loading animation.
     */
    public void appearLoading() {
        int loadingSizePx = GuiTools.convertDpToPixels(activity, LOADING_SIZE_DP);
        CustomAnimator animator = new CustomAnimator();
        Animator animation = animator.createAnimatorSize(loading, 1, 1, loadingSizePx, loadingSizePx, getResources().getInteger(R.integer.durationShort));
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(loading != null) {
                    loading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animation.start();
    }
}