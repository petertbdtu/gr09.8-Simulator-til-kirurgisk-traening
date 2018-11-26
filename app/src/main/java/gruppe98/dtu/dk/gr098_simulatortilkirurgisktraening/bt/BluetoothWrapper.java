package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothWrapper {

  // Name for the SDP record when creating server socket
  private static final String NAME = "BluetoothChat";
  // Unique UUID for this application
  private static final UUID MY_UUID = UUID.fromString("5b9f993d-095d-4889-bfc3-f1261e0a2b28");

  public static final String DEVICE_NAME = "device_name";
  public static final String TOAST = "toast";



  private final BluetoothAdapter mAdapter;
  private final Handler mHandler;
  private AcceptThread mAcceptThread;
  private ConnectThread mConnectThread;
  private ConnectedThread mConnectedThread;
  private int mState;

  private interface StateConstants {
    int STATE_NONE = 0;
    int STATE_LISTEN = 1;
    int STATE_CONNECTING = 2;
    int STATE_CONNECTED = 3;
  }

  private interface MessageConstants {
    int MESSAGE_READ = 0;
    int MESSAGE_WRITE = 1;
    int MESSAGE_TOAST = 2;
    int MESSAGE_STATE_CHANGE = 3;
    int MESSAGE_DEVICE_NAME = 4;
  }

  public BluetoothWrapper(Context context, Handler handler) {
    mAdapter = BluetoothAdapter.getDefaultAdapter();
    mState = StateConstants.STATE_NONE;
    mHandler = handler;
  }

  private synchronized void setState(int state) {
    mState = state;
    // Give the new state to the Handler so the UI Activity can update
    mHandler.obtainMessage(MessageConstants.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
  }

  public synchronized int getState() {
    return mState;
  }

  public synchronized void start() {
    // Cancel any thread attempting to make a connection
    if (mConnectThread != null) {
      mConnectThread.cancel();
      mConnectThread = null;
    }
    // Cancel any thread currently running a connection
    if (mConnectedThread != null) {
      mConnectedThread.cancel();
      mConnectedThread = null;
    }
    // Start the thread to listen on a BluetoothServerSocket
    if (mAcceptThread == null) {
      mAcceptThread = new AcceptThread();
      mAcceptThread.start();
    }
    setState(StateConstants.STATE_LISTEN);
  }

  public synchronized void connect(BluetoothDevice device) {
    // Cancel any thread attempting to make a connection
    if (mState == StateConstants.STATE_CONNECTING) {
      if (mConnectThread != null) {
        mConnectThread.cancel();
        mConnectThread = null;
      }
    }
    // Cancel any thread currently running a connection
    if (mConnectedThread != null) {
      mConnectedThread.cancel();
      mConnectedThread = null;
    }
    // Start the thread to connect with the given device
    mConnectThread = new ConnectThread(device);
    mConnectThread.start();
    setState(StateConstants.STATE_CONNECTING);
  }

  public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
    // Cancel the thread that completed the connection
    if (mConnectThread != null) {
      mConnectThread.cancel();
      mConnectThread = null;
    }
    // Cancel any thread currently running a connection
    if (mConnectedThread != null) {
      mConnectedThread.cancel();
      mConnectedThread = null;
    }
    // Cancel the accept thread because we only want to connect to one device
    if (mAcceptThread != null) {
      mAcceptThread.cancel();
      mAcceptThread = null;
    }
    // Start the thread to manage the connection and perform transmissions
    mConnectedThread = new ConnectedThread(socket);
    mConnectedThread.start();
    // Send the name of the connected device back to the UI Activity
    Message msg = mHandler.obtainMessage(MessageConstants.MESSAGE_DEVICE_NAME);
    Bundle bundle = new Bundle();
    bundle.putString(DEVICE_NAME, device.getName());
    msg.setData(bundle);
    mHandler.sendMessage(msg);
    setState(StateConstants.STATE_CONNECTED);
  }

  public synchronized void stop() {
    if (mConnectThread != null) {
      mConnectThread.cancel();
      mConnectThread = null;
    }
    if (mConnectedThread != null) {
      mConnectedThread.cancel();
      mConnectedThread = null;
    }
    if (mAcceptThread != null) {
      mAcceptThread.cancel();
      mAcceptThread = null;
    }
    setState(StateConstants.STATE_NONE);
  }


  public void write(byte[] out) {
    // Create temporary object
    ConnectedThread r;
    // Synchronize a copy of the ConnectedThread
    synchronized (this) {
      if (mState != StateConstants.STATE_CONNECTED) return;
      r = mConnectedThread;
    }
    // Perform the write unsynchronized
    r.write(out);
  }

  private void connectionFailed() {
    setState(StateConstants.STATE_LISTEN);
    // Send a failure message back to the Activity
    Message msg = mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
    Bundle bundle = new Bundle();
    bundle.putString(TOAST, "Unable to connect device");
    msg.setData(bundle);
    mHandler.sendMessage(msg);
  }

  private void connectionLost() {
    setState(StateConstants.STATE_LISTEN);
    // Send a failure message back to the Activity
    Message msg = mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
    Bundle bundle = new Bundle();
    bundle.putString(TOAST, "Device connection was lost");
    msg.setData(bundle);
    mHandler.sendMessage(msg);
  }

  private class AcceptThread extends Thread {
    // The local server socket
    private final BluetoothServerSocket mmServerSocket;

    public AcceptThread() {
      BluetoothServerSocket tmp = null;
      // Create a new listening server socket
      try {
        tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
      } catch (IOException e) {
      }
      mmServerSocket = tmp;
    }

    public void run() {
      setName("AcceptThread");
      BluetoothSocket socket = null;
      // Listen to the server socket if we're not connected
      while (mState != StateConstants.STATE_CONNECTED) {
        try {
          // This is a blocking call and will only return on a
          // successful connection or an exception
          socket = mmServerSocket.accept();
        } catch (IOException e) {
          break;
        }
        // If a connection was accepted
        if (socket != null) {
          synchronized (BluetoothWrapper.this) {
            switch (mState) {
              case StateConstants.STATE_LISTEN:
              case StateConstants.STATE_CONNECTING:
                // Situation normal. Start the connected thread.
                connected(socket, socket.getRemoteDevice());
                break;
              case StateConstants.STATE_NONE:
              case StateConstants.STATE_CONNECTED:
                // Either not ready or already connected. Terminate new socket.
                try {
                  socket.close();
                } catch (IOException e) {
                }
                break;
            }
          }
        }
      }
    }

    public void cancel() {
      try {
        mmServerSocket.close();
      } catch (IOException e) {
      }
    }
  }

  private class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    public ConnectThread(BluetoothDevice device) {
      mmDevice = device;
      BluetoothSocket tmp = null;
      // Get a BluetoothSocket for a connection with the
      // given BluetoothDevice
      try {
        tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
      } catch (IOException e) {
      }
      mmSocket = tmp;
    }

    public void run() {
      setName("ConnectThread");
      // Always cancel discovery because it will slow down a connection
      mAdapter.cancelDiscovery();
      // Make a connection to the BluetoothSocket
      try {
        // This is a blocking call and will only return on a
        // successful connection or an exception
        mmSocket.connect();
      } catch (IOException e) {
        connectionFailed();
        // Close the socket
        try {
          mmSocket.close();
        } catch (IOException e2) {
        }
        // Start the service over to restart listening mode
        BluetoothWrapper.this.start();
        return;
      }
      // Reset the ConnectThread because we're done
      synchronized (BluetoothWrapper.this) {
        mConnectThread = null;
      }
      // Start the connected thread
      connected(mmSocket, mmDevice);
    }

    public void cancel() {
      try {
        mmSocket.close();
      } catch (IOException e) {
      }
    }
  }

  private class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    public ConnectedThread(BluetoothSocket socket) {
      mmSocket = socket;
      InputStream tmpIn = null;
      OutputStream tmpOut = null;
      // Get the BluetoothSocket input and output streams
      try {
        tmpIn = socket.getInputStream();
        tmpOut = socket.getOutputStream();
      } catch (IOException e) {
      }
      mmInStream = tmpIn;
      mmOutStream = tmpOut;
    }

    public void run() {
      byte[] buffer = new byte[1024];
      int bytes;
      // Keep listening to the InputStream while connected
      while (true) {
        try {
          // Read from the InputStream
          bytes = mmInStream.read(buffer);
          // Send the obtained bytes to the UI Activity
          mHandler.obtainMessage(MessageConstants.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
        } catch (IOException e) {
          connectionLost();
          break;
        }
      }
    }

    public void write(byte[] buffer) {
      try {
        mmOutStream.write(buffer);
        // Share the sent message back to the UI Activity
        mHandler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();

      } catch (IOException e) {
      }
    }

    public void cancel() {
      try {
        mmSocket.close();
      } catch (IOException e) {

      }
    }
  }
}
