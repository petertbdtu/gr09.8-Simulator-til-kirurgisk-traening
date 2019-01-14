package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.InsufflatorFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.VisningAfventerFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.InsufflatorSimApp;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class InsufflatorVisningActivity extends AppCompatActivity implements IWifiListener {

    private static final int MY_PERMISSIONS_REQUEST = 1;

    WifiP2P WP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insufflator_visning);

        InsufflatorSimApp.aktivtScenarie = new Scenario();

        Fragment fragment = new VisningAfventerFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();

        checkPermissions();
    }

    private void checkPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.CHANGE_WIFI_STATE
                , Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.CHANGE_NETWORK_STATE
        };
        ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                Toast.makeText(this,"Please restart app, and accept permissions",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        WP = new WifiP2P(this);
        WP.enableDiscovery();
        WP.registerReciever(this);
    }

    @Override
    public void WifiEnabled(boolean b) {
        if(!b)
            Toast.makeText(this,"Make sure wifi is enabled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void P2PEnabled(boolean b) {
        if(!b)
            Toast.makeText(this,"P2P couldn't initialize",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DiscoveryEnabled(boolean b) {
        if(b)
            Toast.makeText(this,"Started Discovery",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Discovery Disabled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) { }

    @Override
    public void PeerChosen(WifiP2pDevice WPD) { }

    @Override
    public void DeviceConnected(boolean isGroupOwner, String groupOwnerAddress) {
        // Display built-in default scenario immediately or wait until receiving?
        Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DeviceDisconnected() { }

    @Override
    public void MessageReceived(byte[] msg) {
        Toast.makeText(this, "Received: "+msg, Toast.LENGTH_SHORT).show();
        // TODO oversæt byte[] til scenarie
        // TODO start insufflatorvisning fragment

        Fragment fragment;
        fragment = new InsufflatorFragment();
        Bundle args = new Bundle();
        args.putBoolean("erInstruktor", false);
        args.putByteArray("scenarieByteArray", msg);
        fragment.setArguments(args);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Bloker tilbagegang med bekræftelse
    }
}
