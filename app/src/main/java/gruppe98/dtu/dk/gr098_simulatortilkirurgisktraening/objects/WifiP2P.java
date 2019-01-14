package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;

public class WifiP2P {

    private WifiManager WM;
    private WifiP2pManager WPM;
    private WifiP2pManager.Channel WPMC;
    private BroadcastReceiver BR;
    private IntentFilter IF;
    private WifiP2pManager.PeerListListener PLL;
    private WifiP2pManager.ActionListener AL;
    private WifiP2pManager.ConnectionInfoListener CIL;
    private Handler handler;
    private WifiP2PSendReceiveThread sendReceiveThread;
    private InetAddress broadcastAddress;


    private Context context;

    public WifiP2P(Context context){
        this.context = context;
        checkWifi();
        init();
        enableDiscovery();
    }

    private void init(){
        WPM = (WifiP2pManager) context.getSystemService(context.WIFI_P2P_SERVICE);
        WPMC = WPM.initialize(context,context.getMainLooper(),null);
        AL = createActionListener();
        PLL = createPeerListListener();
        CIL = createConnectionInfoListener();
        BR = new WifiP2PBroadcastReceiver(WPM,WPMC,AL,PLL,CIL);
        handler = createHandler();

        IF = new IntentFilter();
        IF.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        IF.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        IF.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        IF.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    public void checkWifi(){
        WM = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        if(!WM.isWifiEnabled())
            WM.setWifiEnabled(true);
        ((IWifiListener) context).WifiEnabled(true);

    }

    public void registerReciever(Context context) {
        this.context = context;
        context.registerReceiver(BR,IF);
    }

    public void unregisterReciever() {
        context.unregisterReceiver(BR);

    }

    public void enableDiscovery() {
        WPM.discoverPeers(WPMC,AL);
    }

    public void disableDiscovery() {
        WPM.stopPeerDiscovery(WPMC,AL);
    }

    public void connectToDevice(final WifiP2pDevice WPD) {
        WifiP2pConfig WPC = new WifiP2pConfig();
        WPC.deviceAddress = WPD.deviceAddress;

        WPM.connect(WPMC, WPC, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                enableDiscovery();
            }

            @Override
            public void onFailure(int reason) {
            }
        });
    }

    public void forceRequestPeers() {
        ((WifiP2PBroadcastReceiver)BR).forceRequestPeers();
    }

    private WifiP2pManager.ActionListener createActionListener(){
        return new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                ((IWifiListener)context).DiscoveryEnabled(true);
            }

            @Override
            public void onFailure(int reason) {
                ((IWifiListener)context).DiscoveryEnabled(false);
            }
        };
    }

    private WifiP2pManager.PeerListListener createPeerListListener() {
        return new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                List<WifiP2pDevice> listWPD = new ArrayList<>();
                listWPD.addAll(peers.getDeviceList());
                ((IWifiListener)context).ChangesInPeersAvailable(listWPD);
            }
        };
    }

    private WifiP2pManager.ConnectionInfoListener createConnectionInfoListener() {
        return new WifiP2pManager.ConnectionInfoListener(){
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                if(info.groupFormed && sendReceiveThread==null) {
                    enableDiscovery();
                    try {
                        byte[] ba = info.groupOwnerAddress.getAddress();
                        ba[3] = (byte) 255;
                        broadcastAddress = InetAddress.getByAddress(ba);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    sendReceiveThread = new WifiP2PSendReceiveThread(handler,broadcastAddress);
                    sendReceiveThread.start();
                    ((IWifiListener)context).DeviceConnected(info.isGroupOwner, info.groupOwnerAddress.getHostAddress());
                }
            }
        };
    }

    static final int MESSAGE_READ = 1;
    private android.os.Handler createHandler() {
        return new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case MESSAGE_READ:
                        byte[] readBuffer = (byte[]) msg.obj;
                        ((IWifiListener)context).MessageReceived(readBuffer);
                        break;
                }
                return true;
            }
        });
    }

    public void sendMessage(byte[] msg) {
        if(sendReceiveThread != null)
            sendReceiveThread.write(msg);
    }

    public String getMacAddress() {
        return WM.getConnectionInfo().getMacAddress();
    }

}
