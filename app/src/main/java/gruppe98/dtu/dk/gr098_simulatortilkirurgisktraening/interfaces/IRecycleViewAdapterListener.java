package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces;

import android.net.wifi.p2p.WifiP2pDevice;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;

public interface IRecycleViewAdapterListener {
    void VaelgBrugsscenarie(String Id);
    void SeLog(String Id);
    void PeerChosen(WifiP2pDevice WPD);
    void sendBrugsscenarie(Scenario brugsscencarie);
    void redigerScenarie(String scenarieNavn);
}



