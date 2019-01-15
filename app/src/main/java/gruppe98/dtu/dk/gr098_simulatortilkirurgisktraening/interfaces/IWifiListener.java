package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;

import java.util.List;

public interface IWifiListener {
    public void DiscoveryEnabled(boolean b);
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD);
    public void DeviceConnected();
    public void DeviceDisconnected();
    public void MessageReceived(byte[] msg);
    public void GroupInfoUpdate(WifiP2pGroup WPG);

}
