package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;

public class WifiP2PBroadcastReceiver extends android.content.BroadcastReceiver implements WifiP2pManager.PeerListListener {

    WifiP2pManager WPM;
    WifiP2pManager.Channel WPMC;
    WifiP2pManager.ActionListener AL;
    WifiP2pManager.PeerListListener PLL;
    WifiP2pManager.ConnectionInfoListener CIL;

    public WifiP2PBroadcastReceiver (WifiP2pManager WPM, WifiP2pManager.Channel WPMC, WifiP2pManager.ActionListener AL, WifiP2pManager.PeerListListener PLL, WifiP2pManager.ConnectionInfoListener CIL) {
        this.WPM = WPM;
        this.WPMC = WPMC;
        this.PLL = PLL;
        this.CIL = CIL;
        this.AL = AL;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Get Action
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //This implies the P2P manager is up and running
            } else {
                //This implies the P2P manager is down / not working
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            WPM.requestPeers(WPMC,PLL);

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if(WPM == null)
                return;

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if(networkInfo.isConnected()) {
                WPM.requestConnectionInfo(WPMC,CIL);
            } else {
                ((IWifiListener)context).DeviceDisconnected();
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            WPM.discoverPeers(WPMC,AL);
        }
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {

    }

    public void forceRequestPeers() {
        WPM.requestPeers(WPMC,PLL);
    }
}
