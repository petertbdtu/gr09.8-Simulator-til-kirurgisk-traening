package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;

public class WifiP2PBroadcastReceiver extends android.content.BroadcastReceiver {

    WifiP2pManager WPM;
    WifiP2pManager.Channel WPMC;
    WifiP2pManager.PeerListListener PLL;
    WifiP2pManager.ConnectionInfoListener CIL;

    public WifiP2PBroadcastReceiver (WifiP2pManager WPM, WifiP2pManager.Channel WPMC, WifiP2pManager.PeerListListener PLL, WifiP2pManager.ConnectionInfoListener CIL) {
        this.WPM = WPM;
        this.WPMC = WPMC;
        this.PLL = PLL;
        this.CIL = CIL;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            WPM.requestPeers(WPMC,PLL);

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if(WPM == null)
                return;
            WPM.discoverPeers(WPMC,null);
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if(networkInfo.isConnected())
                WPM.requestConnectionInfo(WPMC,CIL);

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            ((IWifiListener)context).SetDeviceName(device.deviceName);
            WPM.discoverPeers(WPMC,null);

        } else {
            Log.d("P2PBroadcastReceiver", "Action returned unknown: " + intent.getAction());
        }
    }
}
