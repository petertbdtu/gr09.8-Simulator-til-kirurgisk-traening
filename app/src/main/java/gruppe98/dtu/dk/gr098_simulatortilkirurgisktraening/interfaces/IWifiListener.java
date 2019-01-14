package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.List;

public interface IWifiListener {
    public void WifiEnabled(boolean b);
    public void P2PEnabled(boolean b);
    public void DiscoveryEnabled(boolean b);
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD);
    public void PeerChosen(WifiP2pDevice WPD);
    public void DeviceConnected(boolean isGroupOwner, String groupOwnerAddress);
    public void DeviceDisconnected();
    public void MessageReceived(String msg);


}
