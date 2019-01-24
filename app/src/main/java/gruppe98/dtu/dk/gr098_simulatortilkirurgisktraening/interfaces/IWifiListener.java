package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces;

import android.net.wifi.p2p.WifiP2pDevice;

import java.net.InetAddress;
import java.util.List;

public interface IWifiListener {
    void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD);
    void groupFormed(InetAddress broadcastAddress);
    void SetDeviceName(String name);
    void OnGroupCreated(boolean b);
}
