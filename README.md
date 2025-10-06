# BlueChat - Minor Project 🚀

**Status:** In Progress (Mid-Term)

This repository contains the source code for BlueChat, a peer-to-peer chat application for Android that uses Bluetooth Low Energy (BLE) for communication. It is being developed as a minor project for my college course.

The project is based on the [BluetoothCommunicator](https://github.com/niedev/BluetoothCommunicator) library and follows the architecture of its official example application.

-----

## 📋 Current Progress

The foundational structure and core logic of the application have been successfully set up. The following milestones have been completed:

### 1\. Project Setup & Gradle Configuration

  - The project was initialized in Android Studio using the **Empty Views Activity** template with **Java**.
  - The root `build.gradle` has been configured to include the necessary repositories like `jcenter()` and `jitpack.io`.
  - The app-level `build.gradle` has been updated with all required dependencies, including the core `BluetoothCommunicator` library and AndroidX support libraries (`appcompat`, `recyclerview`, etc.).

### 2\. Application Manifest & Permissions

  - The `AndroidManifest.xml` file has been properly configured with all necessary permissions for Bluetooth functionality:
    ```xml
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    ```
  - The manifest is also set up to use a custom `Application` class (`.Global`) and has the `android:largeHeap="true"` attribute for improved performance.

### 3\. Core Architecture & Java Classes

  - A **`Global.java`** class has been created to initialize and manage a global instance of the `BluetoothCommunicator`.
  - The project's code has been organized into a clean package structure:
    ```
    com.example.bluechat
    ├── fragments
    ├── gui
    └── tools
    ```
  - All essential Java classes for the application's logic and UI have been created, including `PairingFragment`, `ConversationFragment`, `MessagesAdapter`, and `PeerListAdapter`.

### 4\. User Interface (XML Layouts)

  - All necessary XML layout files have been created in the `app/src/main/res/layout` directory.
  - This includes layouts for the main activity, fragments, and the components for list items and message bubbles.

-----

## 📝 Next Steps

The following tasks are pending to make the project fully functional:

1.  **Add all resource files**: Populate the `res` directory with the required `colors.xml`, `styles.xml`, and all `drawable` and `anim` files.
2.  **Implement `MainActivity.java`**: Replace the current template `MainActivity.kt` with the final Java code to manage fragment transactions and the main application logic.
3.  **Complete GUI Helper Classes**: Add the code for the remaining `gui` classes (`ButtonSearch.java`, `CustomAnimator.java`, `GuiTools.java`).
4.  **Build and Debug**: Compile the complete project, test the functionality on physical devices, and resolve any bugs.
