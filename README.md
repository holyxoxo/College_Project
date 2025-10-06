# College_Project
BlueChat - Minor Project
Status: In Progress

This repository contains the source code for BlueChat, a minor project for my college course. It is a peer-to-peer chat application for Android that uses Bluetooth Low Energy (BLE) for communication.

The project is based on the BluetoothCommunicator library and follows the architecture of its example application.

Current Progress
The foundational structure and core logic of the application have been successfully set up. The following milestones have been completed:

1. Project Setup & Gradle Configuration
The project was initialized in Android Studio using the Empty Views Activity template with Java as the primary language.

The project-level build.gradle has been configured to use the required Android Gradle Plugin version and includes the necessary repositories like jcenter() and jitpack.io.

The app-level build.gradle has been updated with all the required dependencies for the project, including:

com.github.niedev:BluetoothCommunicator:1.0.6 for the core Bluetooth functionality.

AndroidX libraries such as appcompat, recyclerview, cardview, and constraintlayout for the user interface.

2. Application Manifest & Permissions
The AndroidManifest.xml file has been properly configured with all necessary permissions for Bluetooth communication and device discovery:

android.permission.BLUETOOTH

android.permission.BLUETOOTH_ADMIN

android.permission.ACCESS_FINE_LOCATION

The manifest is also set up to use a custom Application class (.Global) and has the android:largeHeap="true" attribute for improved performance.

3. Core Architecture & Java Classes
A Global.java class has been created to initialize the BluetoothCommunicator instance, ensuring it is globally accessible throughout the app's lifecycle.

The project has been organized into a clean package structure to separate concerns:

com.example.bluechat.fragments

com.example.bluechat.gui

com.example.bluechat.tools

The essential Java classes for the application's logic and UI have been created, including:

Fragments: PairingFragment.java and ConversationFragment.java.

GUI Adapters: MessagesAdapter.java and PeerListAdapter.java.

GUI Helpers: RequestDialog.java.

Tools: Timer.java and Tools.java.

4. User Interface (XML Layouts)
All the necessary XML layout files have been created in the app/src/main/res/layout directory. This includes layouts for:

The main activity (activity_main.xml).

The primary fragments (fragment_pairing.xml, fragment_conversation.xml).

The list components and message bubbles (component_row.xml, component_message_send.xml, component_message_received.xml).

Next Steps
The following tasks are pending to complete the project and make it fully functional:

Add all resource files: Populate the res directory with the required colors, styles, drawables, and animation files from the example project.

Implement MainActivity.java: Replace the current template MainActivity.kt with the final Java code to manage fragment transactions and handle the main application logic.

Complete GUI Helper Classes: Add the code for the remaining gui classes (ButtonSearch.java, CustomAnimator.java, GuiTools.java).

Build and Debug: Compile the complete project, test the functionality on physical devices, and resolve any bugs.
