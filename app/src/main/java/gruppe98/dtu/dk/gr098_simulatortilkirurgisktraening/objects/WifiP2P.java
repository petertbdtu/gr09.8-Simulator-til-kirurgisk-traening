package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
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

    private boolean isGroupOwner;
    private boolean keepDiscoverEnabled;
    private Context context;

    public WifiP2P(Context context, boolean isGroupOwner){
        this.context = context;
        this.isGroupOwner = isGroupOwner;
        keepDiscoverEnabled = false;

        checkWifi();
        init();
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

        if(isGroupOwner) {
            WPM.createGroup(WPMC, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(context,"Group creation succes",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int reason) {
                    Toast.makeText(context,"Group creation failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
        context.registerReceiver(BR,IF);
    }


    public void checkWifi(){
        WM = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        if(!WM.isWifiEnabled())
            WM.setWifiEnabled(true);
    }

    public void enableDiscovery() {
        keepDiscoverEnabled = true;
        WPM.discoverPeers(WPMC,AL);
    }

    public void disableDiscovery() {
        keepDiscoverEnabled = false;
        WPM.stopPeerDiscovery(WPMC,AL);
    }

    public void registerReceiver(Context context) {
        this.context = context;
        this.context.registerReceiver(BR,IF);
    }

    public void unRegisterReceiver() {
        context.unregisterReceiver(BR);
    }

    public void connectToDevice(final WifiP2pDevice WPD) {
        WifiP2pConfig WPC = new WifiP2pConfig();
        WPC.deviceAddress = WPD.deviceAddress;
        WPM.connect(WPMC, WPC, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int reason) {
            }
        });
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
                ((IWifiListener)context).ChangesInPeersAvailable(new ArrayList<>(peers.getDeviceList()));
            }
        };
    }

    private WifiP2pManager.ConnectionInfoListener createConnectionInfoListener() {
        return new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                if (info.groupFormed && sendReceiveThread == null) {
                    try {
                        byte[] ba = info.groupOwnerAddress.getAddress();
                        ba[3] = (byte) 255;
                        broadcastAddress = InetAddress.getByAddress(ba);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    sendReceiveThread = new WifiP2PSendReceiveThread(handler, broadcastAddress);
                    sendReceiveThread.start();
                    if (keepDiscoverEnabled)
                        enableDiscovery();
                    ((IWifiListener) context).DeviceConnected();
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

    public List<WifiP2pDevice> getConnectedDevices() {
        WPM.requestGroupInfo(WPMC, new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup group) {
                ((IWifiListener)context).GroupInfoUpdate(group);
            }
        });

        return null;
    }

    public String getMacAddress() {
        return WM.getConnectionInfo().getMacAddress();
    }

}
