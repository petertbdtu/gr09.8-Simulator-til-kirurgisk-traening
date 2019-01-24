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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;

public class WifiP2P {

    private WifiManager WM;
    private WifiP2pManager WPM;
    private WifiP2pManager.Channel WPMC;
    private BroadcastReceiver BR;
    private IntentFilter IF;
    private WifiP2pManager.PeerListListener PLL;
    private WifiP2pManager.ConnectionInfoListener CIL;

    private InetAddress broadcastAddress;
    private String myMacAddress;
    private boolean isGroupOwner;
    private Context context;

    public WifiP2P(Context context, boolean isGroupOwner){
        this.context = context;
        this.isGroupOwner = isGroupOwner;
        checkWifi();
        init();
    }

    private void init(){
        WPM = (WifiP2pManager) context.getSystemService(context.WIFI_P2P_SERVICE);
        WPMC = WPM.initialize(context,context.getMainLooper(),null);
        PLL = createPeerListListener();
        CIL = createConnectionInfoListener();
        BR = new WifiP2PBroadcastReceiver(WPM,WPMC,PLL,CIL);

        IF = new IntentFilter();
        IF.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        IF.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        IF.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        WPM.removeGroup(WPMC, null);

        if(isGroupOwner) {
            WPM.createGroup(WPMC, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    ((IWifiListener)context).OnGroupCreated(true);
                }

                @Override
                public void onFailure(int reason) {
                    ((IWifiListener)context).OnGroupCreated(false);
                }
            });
        }
        context.registerReceiver(BR,IF);
    }


    private void checkWifi(){
        WM = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        if(!WM.isWifiEnabled())
            WM.setWifiEnabled(true);
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
                if (info.groupFormed) {

                    try {
                        byte[] ba = info.groupOwnerAddress.getAddress();
                        ba[3] = (byte) 255;
                        broadcastAddress = InetAddress.getByAddress(ba);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }

                    ((IWifiListener) context).groupFormed(broadcastAddress);
                }
            }
        };
    }

    public void enableDiscovery() {
        WPM.discoverPeers(WPMC,null);
    }

    public void registerReceiver(Context context) {
        this.context.unregisterReceiver(BR);
        this.context = context;
        this.context.registerReceiver(BR,IF);
    }

    public void connectToDevice(final WifiP2pDevice WPD) {
        WifiP2pConfig WPC = new WifiP2pConfig();
        WPC.deviceAddress = WPD.deviceAddress;
        WPM.connect(WPMC, WPC, null);
    }

    public String getMyMacAddress() {
        if(myMacAddress == null)
            try {
                List<NetworkInterface> nFaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nFace : nFaces) {
                    if (nFace.getName().equalsIgnoreCase("p2p0")) {
                        byte[] bm = nFace.getHardwareAddress();
                        if (bm == null)
                            return "";

                        StringBuilder str = new StringBuilder();
                        for (byte b : bm)
                            str.append(String.format("%02X:", b));

                        if (str.length() > 0)
                            str = str.deleteCharAt(str.length() - 1);

                        myMacAddress = str.toString();
                        return myMacAddress;
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        else
            return myMacAddress;
        return "";

    }

    public void cancelConnection(WifiP2pDevice wpd) {
        WPM.cancelConnect(WPMC,null);
    }

    public void close(){
        WPM.cancelConnect(WPMC, null);
        WPM.removeGroup(WPMC,null);
    }

}
