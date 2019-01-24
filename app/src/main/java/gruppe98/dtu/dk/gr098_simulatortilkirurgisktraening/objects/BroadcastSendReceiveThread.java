package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;

public class BroadcastSendReceiveThread extends Thread {

    private DatagramSocket socket;
    private Handler handler;
    private InetAddress broadcastAddress;
    private final int PORT = 6666;


    public BroadcastSendReceiveThread(Handler handler, InetAddress broadcastAddress) {
        this.handler = handler;
        this.broadcastAddress = broadcastAddress;
    }
    
    public void close() {
        socket.close();
        this.socket = null;
    }

    public void updateBroadcastAddress(InetAddress broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(PORT);
            socket.setBroadcast(true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (socket!=null) {
            read();
        }
    }

    private void read() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            if(buffer.length > 0) {
                handler.obtainMessage(ApplicationSingleton.MESSAGE_READ, buffer.length, -1, buffer).sendToTarget();
            }
        } catch (IOException e) {
            if(socket == null) {
                Log.d("SendReceiveThread", "Socket closed");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void write(byte[] bytes) {
        if(socket != null) {
            WriteMessageAsync writeMessage = new WriteMessageAsync();
            writeMessage.execute(bytes);
        }
    }

    class WriteMessageAsync extends AsyncTask<byte[],String,String> {
        @Override
        protected String doInBackground(byte[]... bytes) {
            for(byte[] msg : bytes) {
                try {
                    DatagramPacket packet = new DatagramPacket(msg, msg.length, broadcastAddress, PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return null;
        }
    }
}
