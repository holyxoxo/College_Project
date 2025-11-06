# Bluetooth Communication Application

Bluetooth Communication application is a Bluetooth-based Android chat application that enables nearby Android devices to exchange text messages in real time without an internet connection.

## Key Features
- Offline peer-to-peer messaging using Bluetooth and BLE
- Device discovery and secure pairing
- Real-time send / receive using background threads
- Local chat history stored with SQLite
- Simple and responsive UI built with RecyclerView

## Tech Stack
- Java
- Android SDK (Bluetooth + BLE APIs)
- Android Studio
- SQLite (local storage)
- Git

## Project Structure
- app/src/main/java/.../ - application source code  
- app/src/main/res/ - layouts, drawables, strings  
- tools/ - utility classes (e.g., DatabaseHelper, BluetoothService)  
- AndroidManifest.xml - permissions and features  
- build.gradle - build configuration  

3. Open the project in Android Studio.  
4. Create (or update) local.properties with your SDK path if required.  
5. Build and run the app on a Bluetooth-enabled Android device (API 23+ recommended).  

## Usage
- Open BlueChat on two Android devices.  
- Enable Bluetooth and grant required permissions.  
- Use the device discovery screen to find and pair devices.  
- Select a paired device and start sending messages.  
- Chat history is saved locally in SQLite and is viewable in the chat screen.  

## Database
- SQLite database is used to persist messages.  
- Main table: messages  
- Typical fields: msg_id, sender, receiver, message_text, timestamp  
- Database access is handled by DatabaseHelper (in tools/).  

## Future Improvements
- File sharing (images, audio, documents)  
- Group chat and multi-hop message relay via intermediate devices  
- Desktop client for cross-platform communication  
- End-to-end encryption and improved UI  

## Author
Arnav Singh  
