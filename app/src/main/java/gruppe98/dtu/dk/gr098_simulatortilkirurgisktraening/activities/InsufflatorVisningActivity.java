package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.InsufflatorFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.VisningAfventerFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class InsufflatorVisningActivity extends AppCompatActivity implements IWifiListener {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private WifiP2P wp;

    /////////////////////////////////////////
    //// Activity overrides /////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insufflator_visning);

        ApplicationSingleton.getInstance().aktivtScenarie = new Scenario();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new VisningAfventerFragment())
                .commit();

        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wp != null)
            wp.registerReceiver(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ApplicationSingleton.getInstance().WifiP2P == null) {
                    wp = new WifiP2P(this, false);
                    ApplicationSingleton.getInstance().WifiP2P = wp;
                } else {
                    wp = ApplicationSingleton.getInstance().WifiP2P;
                    wp.registerReceiver(this);
                }
            } else {
                Toast.makeText(this,"Please restart app, and accept permissions",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you want to leave?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApplicationSingleton.getInstance().WifiP2P = wp;
        wp.unRegisterReceiver();
    }

    /////////////////////////////////////////
    //// Activity methods ///////////////////
    /////////////////////////////////////////

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

    /////////////////////////////////////////
    //// WifiP2P overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void DiscoveryEnabled(boolean b) { }

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) { }

    @Override
    public void DeviceConnected() {
        // TODO Display built-in default scenario immediately or wait until receiving? Currently Waiting
        Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show();
        wp.disableDiscovery();
    }

    @Override
    public void DeviceDisconnected() { }

    @Override
    public void MessageReceived(byte[] msg) {
        /*ByteBuffer byteBuffer = ByteBuffer.wrap(msg);

        // MAC address as string from WifiManager with getBytes() is 17 bytes long.
        byte[] targetMACAddr = new byte[17];
        byte[] scenarioByteArray = new byte[1024-17];
        byteBuffer.get(targetMACAddr);
        byteBuffer.get(scenarioByteArray);

        byte[] ourMACAddr = WP.getMacAddress().getBytes();

        //if (ourMACAddr == targetMACAddr) {*/
        Toast.makeText(this,"Message:\n" + msg,Toast.LENGTH_SHORT).show();

        Fragment fragment = new InsufflatorFragment();
        Bundle args = new Bundle();
        args.putBoolean("erInstruktor", false);
        args.putByteArray("scenarieByteArray", msg);
        fragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void GroupInfoUpdate(WifiP2pGroup WPG) { }

}
